package net.steampunkfoundry.techdemo.service1.config;

import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(SecurityCheck.class);
        config.exposeIdsFor(Correspondence.class);
        config.exposeIdsFor(Case.class);
    }
}
