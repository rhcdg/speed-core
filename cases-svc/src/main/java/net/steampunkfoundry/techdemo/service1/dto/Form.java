package net.steampunkfoundry.techdemo.service1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.steampunkfoundry.techdemo.service1.utils.CustomJsonDateDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Form {

    private String formNumber;

    private String anumber;

    private String firstName;

    private String lastName;

    private String applicationType;

    private String applicantId;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate submissionDate;

    private Status submissionStatus;

    /* Signature is a required field. Signature field should have the typed name and digital signature metadata of the user for an audit log */
    private Object signature;

    private String middleName;

    private String inCareOf;

    private String address;

    private String address2;

    private String city;

    private String state;

    private String zipCode;

    private String postalCode;

    private String province;

    private String country;

    private String countryOfBirth;

    private String countryOfCitizenship;

    private String visaClass;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate dateOfBirth;

    private Gender gender;

    private String ssn;

    private String numberOfTrips;

    private String explanationOfEligibility;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate signatureDate;

    private String phoneNumber;

    private String purposeOfTrip;

    private String countriesVisiting;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate dateOfDeparture;

    private String lengthOfTrip;

    private Boolean inProceedings;

    private Boolean previouslyIssued;

    private String deliveryAddress;

    private String deliveryAddress2;

    private String deliveryCity;

    private String deliveryState;

    private String deliveryZipCode;

    private String deliveryPostalCode;

    private String deliveryProvince;

    private String deliveryCountry;

    private String fieldControlOffice;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate dateFileOpen;

    private String mother;

    private String father;

    private String akaLastName;

    private String akaFirstName;

    private String portofEntry;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private LocalDate dateofEntry;

    private String i94AdministeredNumbered;

    private String passportNumber;

    private String fbiNumber;

    private String driversLicenseNumber;

    private String fingerPrintCode;

    private String identificationFin;

    private Preparer preparer;

    public enum Gender {
        FEMALE, MALE
    }

    public enum Status {
        IN_PROGRESS, SUBMITTED
    }

}
