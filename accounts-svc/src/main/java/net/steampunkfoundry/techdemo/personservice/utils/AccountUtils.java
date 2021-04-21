package net.steampunkfoundry.techdemo.personservice.utils;

import net.steampunkfoundry.techdemo.personservice.domain.Account;
import net.steampunkfoundry.techdemo.personservice.dto.AccountDto;

public class AccountUtils {

    public static AccountDto convertAccountToDto(Account account) {
        AccountDto v = new AccountDto();
        v.setEmail(account.getEmail());
        v.setName(createName(account));
        v.setUsernameId(account.getUsernameUuid());
        return v;
    }

    private static String createName(Account account) {
        String firstName = account.getFirstName();
        String lastName = account.getLastName();
        String email = account.getEmail();
        String userId = account.getUsernameUuid();

        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        } else if (email != null) {
            if (email.indexOf("@") > -1) {
                String firstPart = email.substring(0, email.indexOf("@"));
                if (firstPart.indexOf(".") > -1) {
                    return firstPart.replaceAll("\\.", " ");
                } else {
                    return firstPart;
                }
            }
            return email;
        }
        return userId;
    }
}

