package net.steampunkfoundry.techdemo.docs.service;

import javax.xml.bind.JAXBException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.request.AddressValidateRequest;
import net.steampunkfoundry.techdemo.docs.request.AddressValidateRequest.Address;
import net.steampunkfoundry.techdemo.docs.response.AddressValidateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UspsClient {

    public static final String ADDRESS_VALIDATE = "Verify";

    private final RestTemplate restTemplate;
    private final String apiConnector = "%s?API=%s&XML=%s";

    @Value("${usps.api}")
    private String uspsApi;

    @Value("${usps.client.key}")
    private String uspsClientKey;

    public ResponseEntity<AddressValidateResponse> validate(Form f) throws JAXBException {
        String address1 = formatEmptyStringIfNull(f.getAddress());
        String address2 = formatEmptyStringIfNull(f.getAddress2());
        String city = formatEmptyStringIfNull(f.getCity());
        String state = formatEmptyStringIfNull(f.getState());
        String zip = formatEmptyStringIfNull(f.getZipCode());
        String zip4 = "";

        Address a = new Address(address1, address2, city, state, zip, zip4);

        return validateAddress(a);
    }

    private ResponseEntity<AddressValidateResponse> validateAddress(Address address) throws JAXBException {
        AddressValidateRequest addressRequest = new AddressValidateRequest();
        addressRequest.setUserId(uspsClientKey);
        addressRequest.setAddress(address);
        String xml = addressRequest.toXml();
        String uri = String.format(apiConnector, uspsApi, ADDRESS_VALIDATE, xml);

        return restTemplate.getForEntity(uri, AddressValidateResponse.class);
    }

    private String formatEmptyStringIfNull(String str) {
        return (str == null ? "" : str);
    }

}
