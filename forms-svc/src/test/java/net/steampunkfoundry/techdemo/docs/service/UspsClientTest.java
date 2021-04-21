package net.steampunkfoundry.techdemo.docs.service;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.xml.bind.JAXBException;
import net.steampunkfoundry.techdemo.docs.Application;
import net.steampunkfoundry.techdemo.docs.TestUtils;
import net.steampunkfoundry.techdemo.docs.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.response.AddressValidateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class UspsClientTest {
    @Autowired
    UspsClient client;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void testvalidateAddress() throws JAXBException {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(new AddressValidateResponse(), HttpStatus.OK));
        Form form = TestUtils.createTestForm();
        ResponseEntity<AddressValidateResponse> result = client.validate(form);
    }

    @Test
    public void testvalidateAddress2() throws JAXBException {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(new AddressValidateResponse(), HttpStatus.OK));
        ResponseEntity<AddressValidateResponse> result = client.validate(new Form());
    }

    @Test
    public void testvalidateAddress3() throws JAXBException {

        when(restTemplate.getForEntity(anyString(), any())).thenReturn(new ResponseEntity<>(new AddressValidateResponse(), HttpStatus.OK));
        ResponseEntity<AddressValidateResponse> result = client.validate(new Form());
    }

}
