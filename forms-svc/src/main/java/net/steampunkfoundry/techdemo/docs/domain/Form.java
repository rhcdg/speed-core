package net.steampunkfoundry.techdemo.docs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.index.Indexed;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Form implements Persistable<ObjectId> {

    @Transient
    public static final String SEQUENCE_NAME = "form_number_sequence";

    @Id
    @Getter(AccessLevel.NONE)
    @JsonProperty("_id")
    private ObjectId objectId;

    @Indexed(unique = true)
    private String formNumber;

    private String caseNumber;

    @NotNull
    @Size(min = 7, max = 10, message = "The 'A_Number' must begin with the letter A, followed by 7-9 digits")
    @Indexed
    private String anumber;

    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private ApplicationType applicationType;

    /* this is the applicant's AWS cognito ID; '@CreatedBy' ensures it gets populated upon creation. */
    @CreatedBy
    private String applicantId;

    private String submissionDate;

    private Status submissionStatus;

    private String middleName;

    private String inCareOf;

    @NotNull
    private String address;

    private String address2;

    @NotNull
    private String city;

    @NotNull
    private String state;

    @NotNull
    private String zipCode;

    private String postalCode;

    private String province;

    @NotNull
    private String country;

    @NotNull
    private String countryOfBirth;

    private String countryOfCitizenship;

    @NotNull(message = "Class of Admission is required.")
    private String visaClass;

    @NotNull
    private String dateOfBirth;

    @NotNull
    private Gender gender;

    private String ssn;

    private String numberOfTrips;

    private String explanationOfEligibility;

    private String phoneNumber;

    private String purposeOfTrip;

    private String countriesVisiting;

    private String dateOfDeparture;

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

    private String dateFileOpen;

    private String mother;

    private String father;

    private String akaLastName;

    private String akaFirstName;

    private String portofEntry;

    private String dateofEntry;

    private String i94AdministeredNumbered;

    private String passportNumber;

    private String fbiNumber;

    private String driversLicenseNumber;

    private String fingerPrintCode;

    private String identificationFin;

    private String signature;

    private String signatureDate;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    private Preparer preparer;

    @JsonInclude
    private List<SupportDocument> supportDocument = new ArrayList<>();

    @JsonProperty("id")
    public String getIdString() {
        return objectId != null ? objectId.toString() : null;
    }

    @Override
    @JsonIgnore
    public ObjectId getId() {
        return objectId;
    }

    @Override
    public boolean isNew() {
        return objectId == null;
    }

    public enum Gender {
        FEMALE,
        MALE
    }

    public enum Status {
        IN_PROGRESS,
        SUBMITTED
    }

    public enum ApplicationType {
        ONE_A,
        ONE_B,
        ONE_C,
        ONE_D,
        ONE_E,
        ONE_F,
        ONE_G
    }
}
