package net.steampunkfoundry.techdemo.service1.service;

import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.FAVORABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckType;
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
public class SecurityCheckClientTest {

    @Autowired
    SecurityCheckClient client;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private RestUtils utils;

    @Test
    public void testTriggerSecurityCheck() {

        SecurityCheck check = new SecurityCheck();
        check.setSecurityCheckStatus(FAVORABLE);
        check.setSecurityCheckType(SecurityCheckType.A_NUMBER);

        ResponseEntity<SecurityCheck[]> responseEntity = new ResponseEntity<>(
                new SecurityCheck[]{check},
                null,
                HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(ArgumentMatchers.anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.any(Class.class)))
                .thenReturn(responseEntity);

        List<SecurityCheck> result = client.triggerSecurityCheck(null, TestUtils.buildForm());
        assertNotNull(result);
        assertTrue(result.size() > 0);
    }

    @Test
    public void testTriggerSecurityCheck_RestTemplateThrows() {

        SecurityCheck check = new SecurityCheck();
        check.setSecurityCheckStatus(FAVORABLE);
        check.setSecurityCheckType(SecurityCheckType.A_NUMBER);

        ResponseEntity<SecurityCheck[]> responseEntity = new ResponseEntity<>(
                new SecurityCheck[]{check},
                null,
                HttpStatus.OK);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(ArgumentMatchers.anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SecurityCheck[]>>any()))
                .thenThrow(RuntimeException.class);

        List<SecurityCheck> result = client.triggerSecurityCheck(null, TestUtils.buildForm());
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SecurityCheck[]>>any());
    }


    @Test
    public void testTriggerSecurityCheck_404_Response() {

        ResponseEntity<SecurityCheck[]> responseEntity = new ResponseEntity<>(
                new SecurityCheck[0],
                null,
                HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "test");

        when(utils.createHeadersWithToken(anyString())).thenReturn(new HttpEntity<>(headers));

        when(restTemplate.exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SecurityCheck[]>>any()))
                .thenReturn(responseEntity);

        List<SecurityCheck> result = client.triggerSecurityCheck(null, TestUtils.buildForm());
        assertNotNull(result);
        assertEquals(0, result.size());

        verify(restTemplate).exchange(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.any(HttpMethod.class),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<SecurityCheck[]>>any());
    }
}
