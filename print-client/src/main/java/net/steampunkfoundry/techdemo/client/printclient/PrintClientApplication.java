package net.steampunkfoundry.techdemo.client.printclient;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * This is the Main Spring Boot Application; it bootstraps the JavaFx Application
 */
@SpringBootApplication
public class PrintClientApplication {

    public static void main(String[] args) {
        Application.launch(FxApplication.class, args);
    }

    /**
     * return the restTemplate used for making rest calls
     *
     * @return the restTemplate used for making rest calls
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
