package net.steampunkfoundry.techdemo.docs;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.domain.Form.ApplicationType;
import net.steampunkfoundry.techdemo.docs.domain.Form.Gender;
import net.steampunkfoundry.techdemo.docs.domain.Preparer;

public class TestUtils {

    public static Form createTestForm() {
        //populating required fields...
        Form form = new Form();
        form.setZipCode("ZipCode");
        form.setCountry("Country");
        form.setGender(Gender.FEMALE);
        form.setAddress("Address");
        form.setCity("City");
        form.setAnumber("A123456789");
        form.setDateOfBirth("01/01/1980");
        form.setState("State");
        form.setVisaClass("VisaClass");
        form.setCountryOfBirth("CountryOfBirth");
        //and other fields...
        form.setFirstName("A");
        form.setLastName("123");
        form.setApplicationType(ApplicationType.ONE_A);
        form.setPreparer(TestUtils.createTestPreparer());
        form.setAddress2("address2");

        return form;
    }

    public static Preparer createTestPreparer() {
        Preparer p = new Preparer();
        p.setAddress("Address");
        p.setAddress2("Line 2");
        p.setBusinessName("Test Business");
        p.setCity("My City");
        p.setCountry("U.S.");
        p.setDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        p.setEmail("email@test.com");
        p.setPhone("504-555-1212");
        p.setZipCode("70119");
        p.setState("LA");
        p.setSignature("A Tester");
        return p;
    }
}
