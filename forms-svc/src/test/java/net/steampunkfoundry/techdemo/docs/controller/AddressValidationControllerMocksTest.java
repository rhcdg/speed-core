package net.steampunkfoundry.techdemo.docs.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import net.steampunkfoundry.techdemo.docs.Application;
import net.steampunkfoundry.techdemo.docs.TestUtils;
import net.steampunkfoundry.techdemo.docs.enums.ValidationResponseStatus;
import net.steampunkfoundry.techdemo.docs.response.AddressValidateResponse;
import net.steampunkfoundry.techdemo.docs.service.UspsClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class AddressValidationControllerMocksTest {

    @Autowired
    AddressValidationController addressValidationController;

    @MockBean
    private UspsClient client;

    @Test
    public void testValidateAddress() throws Exception {
        when(client.validate(any())).thenReturn(new ResponseEntity<>(new AddressValidateResponse(), HttpStatus.OK));
        AddressValidateResponse result = addressValidationController.validate(TestUtils.createTestForm());
        assertNotNull(result);
        assertEquals(ValidationResponseStatus.SUCCESS, result.getStatus());
    }

    @Test
    public void testValidateAddress_NotFound() throws Exception {
        when(client.validate(any())).thenReturn(new ResponseEntity<>(new AddressValidateResponse(), HttpStatus.NOT_FOUND));
        AddressValidateResponse result = addressValidationController.validate(TestUtils.createTestForm());
        assertNotNull(result);
        assertEquals(ValidationResponseStatus.FAIL, result.getStatus());
    }
}
