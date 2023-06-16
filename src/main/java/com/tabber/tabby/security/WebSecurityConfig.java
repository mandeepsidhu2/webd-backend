package com.tabber.tabby.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /* -> All the endpoints mentioned here are whitelisted to allow usage without access token('Authorisation').
           -> This filter is set only once when app is started, this filter comes before UsernamePasswordAuthenticationFilter
           and after SecurityContextPersistenceFilter(which is set in JWTAuthorisationFilter file)
           -> For reference https://stackoverflow.com/questions/41480102/how-spring-security-filter-chain-works
           -> If no security context is present(SecurityContextHolder.clearContext() is executed), then that API must be
           whitelisted below, otherwise it won't reach the controller.
           -> For the APIs not listed here security context is present and will be passed to the controller
           -> This use case arises when you need to reach an API controller without authentication(like actuator, ping)
           -> Another use case is to allow only admin users to access admin APIs
         */
        http.csrf().disable()
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/github/login").permitAll()
                .antMatchers(HttpMethod.GET, "/university/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/university/**").permitAll()
                .antMatchers(HttpMethod.GET, "/ping").permitAll()
                .antMatchers(HttpMethod.POST,"/email").permitAll()
                .antMatchers(HttpMethod.GET,"/fe/get").permitAll()
                .antMatchers(HttpMethod.GET,"/website/**").permitAll()
                .antMatchers(HttpMethod.GET,"/website/all").permitAll()
                .antMatchers(HttpMethod.GET,"/user/guest/resume").permitAll()
                .antMatchers(HttpMethod.POST,"/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT,"/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/admin/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET,"/admin_user/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();
    }
}
