package net.steampunkfoundry.techdemo.service1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditorConfig {

    @Bean
    public AuditorAwareImpl auditorProvider() {
        return new AuditorAwareImpl();
    }
}
