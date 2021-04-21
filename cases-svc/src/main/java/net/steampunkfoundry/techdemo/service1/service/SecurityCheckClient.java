package net.steampunkfoundry.techdemo.service1.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.utils.RestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityCheckClient {

    private final RestTemplate restTemplate;
    private final RestUtils utils;

    @Value("${security.check.endpoint}")
    private String securityCheckApi;

    public List<SecurityCheck> triggerSecurityCheck(Authentication auth, Form form) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " +  utils.extractToken(auth));
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(securityCheckApi)
                .queryParam("a_number", form.getAnumber())
                .queryParam("first_name", form.getFirstName())
                .queryParam("last_name", form.getLastName());

        try {
            ResponseEntity<SecurityCheck[]> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    SecurityCheck[].class);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                return Arrays.asList(response.getBody());
            }
        } catch (Exception e) {
            log.error("Security check could not be run for Form Number " + form.getFormNumber());
        }
        return new ArrayList<>();
    }
}
