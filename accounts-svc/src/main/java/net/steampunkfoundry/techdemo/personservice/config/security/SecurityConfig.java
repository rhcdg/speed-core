package net.steampunkfoundry.techdemo.personservice.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().and().authorizeRequests()
                .antMatchers("/configuration/ui", "/swagger-ui.html", "/actuator/health", "/swagger-resources/**", "/webjars/springfox-swagger-ui/*", "/v2/api-docs")
                .permitAll()
                .and()
                .authorizeRequests(expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry.anyRequest().authenticated())
                .oauth2ResourceServer().jwt(jwt -> jwt.jwtAuthenticationConverter(grantedAuthoritiesExtractor()));
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(this::convertGroupsToAuthorities);
        return jwtAuthenticationConverter;
    }

    //based on https://kevcodez.de/posts/2020-03-26-secure-spring-boot-app-with-oauth2-aws-cognito/#check-user-groups
    private Collection<GrantedAuthority> convertGroupsToAuthorities(Jwt jwt) {
        return (Set) ((List) jwt.getClaims()
                .getOrDefault("cognito:groups", new ArrayList<>()))
                .stream()
                .map(t -> new SimpleGrantedAuthority((String) t))
                .collect(Collectors.toSet());
    }

}