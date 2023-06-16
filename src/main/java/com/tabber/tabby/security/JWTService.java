package com.tabber.tabby.security;

import com.tabber.tabby.constants.AuthConstants;
import com.tabber.tabby.constants.TabbyConstants;
import com.tabber.tabby.enums.UserRoles;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTService {
    public String getJWTToken(String sub,Long userId,String email) {
        UserRoles userRole = UserRoles.USER;
        if(TabbyConstants.admins.contains(email)){
            userRole = UserRoles.ADMIN;
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList(userRole.name());

        String token = Jwts
                .builder()

                .setId(sub)
                .setSubject(userId.toString())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(SignatureAlgorithm.HS512,
                        AuthConstants.SECRET_KEY.getBytes()).compact();

        return token;
    }
}
