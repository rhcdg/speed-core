package net.steampunkfoundry.techdemo.service1.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.dto.FormResponse;
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
public class FormsClient {

    private final RestTemplate restTemplate;

    private final RestUtils utils;

    @Value("${forms.anumber.endpoint}")
    private String anumberApi;

    @Value("${forms.form.number.endpoint}")
    private String formNumberApi;

    @Value("${forms.endpoint}")
    private String allApi;

    public List<Form> getFormsByAnumber(Authentication auth, String anumber) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + utils.extractToken(auth));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(anumberApi)
                .queryParam("anumber", anumber);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<FormResponse> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    FormResponse.class);
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                return response.getBody().getEmbedded().getForms();
            }
        } catch (Exception e) {
            log.error("Form data could not be fetched for A_Number " + anumber);
        }

        return new ArrayList<>();
    }

    public Form getFormByFormNumber(Authentication auth, String formNumber) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + utils.extractToken(auth));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(formNumberApi)
                .queryParam("formNumber", formNumber);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Form> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    Form.class);
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error("Form data could not be fetched for Form Number " + formNumber);
        }

        return null;
    }

    public Map<String, Form> getAllForms(Authentication auth) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + utils.extractToken(auth));

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(allApi);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<FormResponse> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                FormResponse.class);
        if (HttpStatus.OK.equals(response.getStatusCode())) {
            return response.getBody().getEmbedded().getForms()
                    .stream().collect(Collectors.toMap(Form::getFormNumber, form -> form));
        }

        return new HashMap<>();
    }

}
