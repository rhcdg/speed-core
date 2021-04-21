package net.steampunkfoundry.techdemo.docs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuditorConfig {

    @Bean
    public AuditorAwareImpl auditorProvider() {
        return new AuditorAwareImpl();
    }
}
