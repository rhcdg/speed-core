package net.steampunkfoundry.techdemo.service1.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.service1.dto.Adjudicator;
import net.steampunkfoundry.techdemo.service1.utils.RestUtils;
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
public class AdjudicatorClientTest {

    @Autowired
    AdjudicatorClient client;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private RestUtils utils;

    @Test
    public void testGetAdjudicators() {

        Adjudicator adj = new Adjudicator();
        adj.setEmail("email");
        adj.setName("testy");
        adj.setUsernameUuid("abc");

        ResponseEntity<Adjudicator[]> responseEntity = new ResponseEntity<>(
                new Adjudicator[]{adj},
                null,
                HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Adjudicator[]>>any()))
                .thenReturn(responseEntity);

        Map result = client.getAdjudicators(null);
        assertNotNull(result);
        assertEquals(1, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Adjudicator[]>>any());
    }

    @Test
    public void testGetAdjudicators_404_Response() {

        ResponseEntity<Adjudicator[]> responseEntity = new ResponseEntity<>(
                new Adjudicator[0],
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Adjudicator[]>>any()))
                .thenReturn(responseEntity);

        Map result = client.getAdjudicators(null);
        assertEquals(0, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Adjudicator[]>>any());
    }

    @Test
    public void testGetAdjudicators_RestTemplateThrows() {

        ResponseEntity<Adjudicator[]> responseEntity = new ResponseEntity<>(
                new Adjudicator[0],
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Adjudicator[]>>any()))
                .thenThrow(RuntimeException.class);

        Map result = client.getAdjudicators(null);
        assertEquals(0, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<Adjudicator[]>>any());
    }
}