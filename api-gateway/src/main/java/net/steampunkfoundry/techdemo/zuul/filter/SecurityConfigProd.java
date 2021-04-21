package net.steampunkfoundry.techdemo.zuul.filter;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Profile("prod")
public class SecurityConfigProd extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().and().authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/*", "/**").permitAll()
                .antMatchers("/actuator/health")
                .permitAll().and()
                .authorizeRequests(
                        expressionInterceptUrlRegistry -> expressionInterceptUrlRegistry
                                .anyRequest().authenticated())
                .oauth2ResourceServer().jwt();
    }
}
