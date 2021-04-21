package net.steampunkfoundry.techdemo.docs.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.enums.ValidationResponseStatus;
import net.steampunkfoundry.techdemo.docs.response.AddressValidateResponse;
import net.steampunkfoundry.techdemo.docs.service.UspsClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Address Validation")
@RequiredArgsConstructor
@RestController
public class AddressValidationController {

    private final UspsClient client;

    @ApiOperation(
            value = "Validate Address",
            notes = "This method validates an address and returns a suggested standardized address.")
    @PostMapping(value = "/api/address/validate")
    public @ResponseBody
    AddressValidateResponse validate(
            @ApiParam(
                    name = "form",
                    type = "Form object",
                    value = "Required fields are address, city, state, zip. Address2 is optional. All other fields ignored.")
            @RequestBody
                    Form form) throws JAXBException {
        ResponseEntity<AddressValidateResponse> response = client.validate(form);
        if (response.getStatusCode() == HttpStatus.OK) {
            response.getBody().setStatus(ValidationResponseStatus.SUCCESS);
            return response.getBody();
        } else {
            return new AddressValidateResponse();
        }
    }
}
