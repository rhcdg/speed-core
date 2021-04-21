package net.steampunkfoundry.techdemo.service1.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.service1.dto.Adjudicator;
import net.steampunkfoundry.techdemo.service1.utils.RestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdjudicatorClient {
    private final RestTemplate restTemplate;

    @Value("${accounts.adjudicators.endpoint}")
    private String api;

    private final RestUtils utils;

    public Map<String, Adjudicator> getAdjudicators(Authentication auth) {

        try {
            ResponseEntity<Adjudicator[]> response = restTemplate.exchange(
                    api,
                    HttpMethod.GET,
                    utils.createHeadersWithToken("bearer " + utils.extractToken(auth)),
                    Adjudicator[].class);

            if (HttpStatus.OK.equals(response.getStatusCode())) {
                return Arrays.stream(response.getBody())
                        .collect(Collectors.toMap(Adjudicator::getUsernameUuid, adjudicator -> adjudicator));
            }
        } catch (Exception e) {
            log.error("Adjudicator data could not be fetched");
        }

        return new HashMap<>();
    }

}
