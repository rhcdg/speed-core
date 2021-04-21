package net.steampunkfoundry.techdemo.docs.service;


import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.steampunkfoundry.techdemo.docs.Application;
import net.steampunkfoundry.techdemo.docs.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.domain.Form.Gender;
import net.steampunkfoundry.techdemo.docs.service.CaseClient.CaseResponse;
import net.steampunkfoundry.techdemo.docs.utils.RestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = SecurityConfig.class)
public class CaseClientTest {

    @Autowired
    CaseClient client;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private RestUtils utils;

    @Test
    public void testSubmitForm() throws JsonProcessingException {

        CaseResponse fr = new CaseResponse(true, "ABCD", "success");

        ResponseEntity<CaseResponse> responseEntity = new ResponseEntity<>(
                fr,
                null,
                HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<CaseResponse>>any()))
                .thenReturn(responseEntity);

        CaseResponse result = client.submitForm(null, createTestForm());
        assertNotNull(result);
        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<CaseResponse>>any());
    }

    @Test
    public void testSubmitForm_404_Response() throws JsonProcessingException {
        CaseResponse fr = new CaseResponse(false, null, "fail");

        ResponseEntity<CaseResponse> responseEntity = new ResponseEntity<>(
                fr,
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<CaseResponse>>any()))
                .thenReturn(responseEntity);

        CaseResponse result = client.submitForm(null, createTestForm());
        assertNotNull(result);

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<CaseResponse>>any());
    }

    private Form createTestForm() {
        Form f = new Form();
        f.setAnumber("A123333");
        f.setFirstName("First");
        f.setLastName("Last");
        f.setGender(Gender.FEMALE);
        f.setApplicantId("applicationId");
        f.setFormNumber("ABCD");
        return f;
    }
}