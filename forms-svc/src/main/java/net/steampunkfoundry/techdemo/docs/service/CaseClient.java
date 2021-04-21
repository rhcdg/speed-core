package net.steampunkfoundry.techdemo.docs.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.utils.RestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaseClient {

    private final RestTemplate restTemplate;

    private final RestUtils utils;

    @Value("${cases.endpoint}")
    private String caseApi;

    public CaseResponse submitForm(Authentication auth, Form f) throws JsonProcessingException {
        String applicantId = f.getApplicantId();
        String formNumber = f.getFormNumber();
        String anumber = f.getAnumber();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "bearer " + utils.extractToken(auth));

        Case acase = new Case(anumber, applicantId, formNumber);

        HttpEntity<?> entity = new HttpEntity(acase, headers);

        ResponseEntity<CaseResponse> response = restTemplate.exchange(
                caseApi, HttpMethod.POST, entity, CaseResponse.class);


        if (HttpStatus.OK.equals(response.getStatusCode())) {

            return new CaseResponse(true, response.getBody().getCaseNumber() + "", "Success");

        }
        return new CaseResponse(false, null, "Submission failed.");
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @ToString
    @RequiredArgsConstructor
    @Getter
    public static class CaseResponse {
        private final boolean success;
        private final String caseNumber;
        private final String msg;
    }

    @ToString
    @Getter
    @RequiredArgsConstructor
    public static class Case {
        private final String aNumber;
        private final String applicantId;
        private final String formNumber;
    }
}
