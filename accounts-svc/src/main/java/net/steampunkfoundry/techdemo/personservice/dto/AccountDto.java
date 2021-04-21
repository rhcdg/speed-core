package net.steampunkfoundry.techdemo.personservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class AccountDto {

    private String email;

    //this is the unique key for AWS cognito
    private String usernameId;

    private String name;

}
