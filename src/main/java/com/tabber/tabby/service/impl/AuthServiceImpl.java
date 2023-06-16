package com.tabber.tabby.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.tabber.tabby.constants.TabbyConstants;
import com.tabber.tabby.entity.UserEntity;
import com.tabber.tabby.exceptions.UnauthorisedException;
import com.tabber.tabby.security.JWTService;
import com.tabber.tabby.service.AuthService;
import com.tabber.tabby.service.UserService;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    JWTService jwtService;

    @Autowired
    UserService userService;

    private static final Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    boolean newUser=false;
    public boolean isNewUser(){
        boolean ans=newUser;
        newUser=false;
        return  ans;
    }
    public String githubLogin(String idTokenString) throws UnauthorisedException {
        String clientId = "4bbf2c8cbb0a20ff5a7c";
        String clientSecret = "2be17edb9a1fc44fadb2df7a753934d5f4b6185e";
        String code = idTokenString;

        String accessToken = getAccessTokenFromGitHub(code, clientId, clientSecret);
        if (accessToken != null) {
            return generateAndSaveGithubUser(accessToken);
        } else {
            throw new UnauthorisedException("Unable to retrieve access token from GitHub");
        }
    }

    private String getAccessTokenFromGitHub(String code, String clientId, String clientSecret) throws UnauthorisedException {
        String apiUrl = "https://github.com/login/oauth/access_token";
        HttpPost request = new HttpPost(apiUrl);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("client_id", clientId));
        params.add(new BasicNameValuePair("client_secret", clientSecret));
        params.add(new BasicNameValuePair("code", code));

        try {
            request.setEntity(new UrlEncodedFormEntity(params));

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                return parseAccessToken(responseBody);
            } else {
                throw new UnauthorisedException("No response received");
            }
        } catch (Exception e) {
            throw new UnauthorisedException("An error occurred while retrieving the access token: " + e.getMessage());
        }
    }

    private static String parseAccessToken(String responseBody) {
        try {
            String[] queryParams = responseBody.split("&");
            for (String param : queryParams) {
                String[] keyValue = param.split("=");
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                if (key.equals("access_token")) {
                    String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                    return value;
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while parsing the access token: " + e.getMessage());
        }
        return null;
    }

    private String generateAndSaveGithubUser(String accessToken) {
        String apiUrl = "https://api.github.com/user";
        HttpGet request = new HttpGet(apiUrl);
        request.addHeader("Authorization", "Bearer " + accessToken);

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);

                String username = jsonNode.get("login").asText();
                String name = jsonNode.get("name").asText();
                String image = jsonNode.get("avatar_url").asText();
                String email = jsonNode.get("email").asText();
                String subject = jsonNode.get("id").asText();

                UserEntity userEntity = userService.getUserFromSub(subject);
                Long userId;
                Date presentDate = new Date();
                if (userEntity == null) {
                    this.newUser=true;
                    userEntity = new UserEntity()
                        .toBuilder()
                        .sub(subject)
                        .email(email)
                        .pictureUrl(image)
                        .locale("en")
                        .name(username)
                        .resumePresent(false)
                        .cookieAccepted(false)
                        .lastLoggedIn(presentDate)
                        .planId(TabbyConstants.LITE_PLAN_ID)
                        .userType("user")
                        .build();
                } else {
                    userEntity.setLastLoggedIn(presentDate);
                }
                userId = userService.save(userEntity);
                String token = jwtService.getJWTToken(userEntity.getSub(), userId, email);
                return token;
            } else {
                System.out.println("No response received.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while fetching user details: " + e.getMessage());
        }
        return null;
    }
    public String login(String idTokenString) throws UnauthorisedException{
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList("954820964588-l8ba8pbqa49riqctv9fa6ckrt1dbul77.apps.googleusercontent.com"))
                .build();
        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(idTokenString);
        }
        catch (Exception ex){
            logger.log(Level.WARNING,"Unable to verify user for identity token :{}",idTokenString);
           throw new UnauthorisedException(ex.toString());
        }
        if(idToken == null )
            throw new UnauthorisedException("Id token is null");

        GoogleIdToken.Payload payload = idToken.getPayload();

        if(!Boolean.valueOf(payload.getEmailVerified()))
            throw new UnauthorisedException("Id token is null");

        logger.log(Level.INFO,"User Info :{}",payload);
        UserEntity userEntity = userService.getUserFromSub(payload.getSubject());
        Long userId ;
        Date presentDate = new Date();
        if(userEntity == null) {
            this.newUser=true;
            userEntity = new UserEntity()
                    .toBuilder()
                    .sub(payload.getSubject())
                    .email(payload.getEmail())
                    .pictureUrl((String) payload.get("picture"))
                    .locale((String) payload.get("locale"))
                    .name((String) payload.get("name"))
                    .resumePresent(false)
                    .cookieAccepted(false)
                    .lastLoggedIn(presentDate)
                    .planId(TabbyConstants.LITE_PLAN_ID)
                    .userType("user")
                    .build();
        }
        else {
            userEntity.setLastLoggedIn(presentDate);
        }
        userId = userService.save(userEntity);
        String token = jwtService.getJWTToken(userEntity.getSub(),userId,payload.getEmail());
        return token;
    }
}
