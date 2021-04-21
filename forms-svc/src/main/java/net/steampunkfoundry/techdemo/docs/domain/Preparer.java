package net.steampunkfoundry.techdemo.docs.domain;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Preparer implements Serializable {

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

    private String date;

}
