package net.steampunkfoundry.techdemo.personservice.domain;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "inlineRole", types = {Account.class})
public interface InlineRole {

    Long getId();

    String getFirstName();

    String getLastName();

    String getEmail();

    String getUsername();

    String getUsernameUuid();

    boolean getEmailVerified();

    Long getPreviousLoginDate();

    Long getThisLoginDate();
}
