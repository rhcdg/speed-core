package net.steampunkfoundry.techdemo.service1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Preparer {

    private String lastName;
    private String firstName;
    private String businessName;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private String postalCode;
    private String province;
    private String country;
    private String phone;
    private String phoneExt;
    private String email;
    private String signature;
    private LocalDate date;

}