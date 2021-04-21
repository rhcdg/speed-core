package net.steampunkfoundry.techdemo.service1.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.dto.Form.Gender;
import net.steampunkfoundry.techdemo.service1.dto.FormResponse;
import net.steampunkfoundry.techdemo.service1.dto.FormsEmbeddedListJson;
import net.steampunkfoundry.techdemo.service1.utils.RestUtils;
import net.steampunkfoundry.techdemo.service1.utils.TestUtils;
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
@SpringBootTest(classes = CaseApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = SecurityConfig.class)
public class FormsClientTest {

    @Autowired
    FormsClient client;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private RestUtils utils;

    @Test
    public void testGetFormsByAnumber() {

        Form f = new Form();
        f.setAnumber("A123333");
        f.setFirstName("First");
        f.setLastName("Last");
        f.setGender(Gender.FEMALE);

        Form f2 = new Form();
        f2.setAnumber("A123334");
        f2.setFirstName("Test");
        f2.setLastName("User");
        f2.setGender(Gender.MALE);

        FormResponse fr = new FormResponse();
        FormsEmbeddedListJson embedded = new FormsEmbeddedListJson();
        embedded.setForms(Arrays.asList(f, f2));
        fr.setEmbedded(embedded);

        ResponseEntity<FormResponse> responseEntity = new ResponseEntity<>(
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
                ArgumentMatchers.<Class<FormResponse>>any()))
                .thenReturn(responseEntity);

        List<Form> result = client.getFormsByAnumber(null, "anumber");
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any());
    }

    @Test
    public void testGetFormsByAnumber_404_Response() {

        ResponseEntity<FormResponse> responseEntity = new ResponseEntity<>(
                new FormResponse(),
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any()))
                .thenReturn(responseEntity);

        List<Form> result = client.getFormsByAnumber(null, "anumber");
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any());
    }

    @Test
    public void testGetFormsByAnumber__RestTemplateThrows() {

        ResponseEntity<FormResponse> responseEntity = new ResponseEntity<>(
                new FormResponse(),
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any()))
                .thenThrow(RuntimeException.class);

        List<Form> result = client.getFormsByAnumber(null, "anumber");
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any());
    }

    @Test
    public void testGetFormsByFormNumber() {

        Form f = new Form();
        f.setAnumber("A123333");
        f.setFormNumber("ABC1234");
        f.setFirstName("First");
        f.setLastName("Last");
        f.setGender(Gender.FEMALE);

        ResponseEntity<Form> responseEntity = new ResponseEntity<>(
                f,
                null,
                HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Form>>any()))
                .thenReturn(responseEntity);

        Form result = client.getFormByFormNumber(null, f.getFormNumber());
        assertNotNull(result);

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Form>>any());
    }

    @Test
    public void testGetFormsByFormNumber_404_Response() {

        ResponseEntity<Form> responseEntity = new ResponseEntity<>(
                TestUtils.buildForm(),
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Form>>any()))
                .thenReturn(responseEntity);

        Form result = client.getFormByFormNumber(null, "formNumber");
        assertNull(result);

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Form>>any());
    }

    @Test
    public void testGetFormsByFormNumber_RestTemplateThrows() {

        ResponseEntity<Form> responseEntity = new ResponseEntity<>(
                TestUtils.buildForm(),
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Form>>any()))
                .thenThrow(RuntimeException.class);

        Form result = client.getFormByFormNumber(null, "formNumber");
        assertNull(result);

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Form>>any());
    }

    @Test
    public void testGetAllForms() {

        Form f = new Form();
        f.setAnumber("A123333");
        f.setFormNumber("ABC123");
        f.setFirstName("First");
        f.setLastName("Last");
        f.setGender(Gender.FEMALE);

        Form f2 = new Form();
        f2.setAnumber("A123334");
        f.setFormNumber("XYZ987");
        f2.setFirstName("Test");
        f2.setLastName("User");
        f2.setGender(Gender.MALE);

        FormResponse fr = new FormResponse();
        FormsEmbeddedListJson embedded = new FormsEmbeddedListJson();
        embedded.setForms(Arrays.asList(f, f2));
        fr.setEmbedded(embedded);

        ResponseEntity<FormResponse> responseEntity = new ResponseEntity<>(
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
                ArgumentMatchers.<Class<FormResponse>>any()))
                .thenReturn(responseEntity);

        Map<String, Form> result = client.getAllForms(null);
        assertNotNull(result);
        assertEquals(2, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any());
    }

    @Test
    public void testGetAllForms_404_Response() {

        ResponseEntity<FormResponse> responseEntity = new ResponseEntity<>(
                new FormResponse(),
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any()))
                .thenReturn(responseEntity);

        Map<String, Form> result = client.getAllForms(null);
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<FormResponse>>any());
    }
}