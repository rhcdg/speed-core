package net.steampunkfoundry.techdemo.personservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
@Audited
@AuditTable("account_audit_log")
public class Account implements Serializable {

    private static final long serialVersionUID = 2753439961398901747L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    /* this is the unique identifier withinn AWS/Cognito */
    private String usernameUuid;

    @JsonIgnore
    private boolean emailVerified;

    @JsonIgnore
    private Long previousLoginDate;

    @JsonIgnore
    private Long thisLoginDate;

}
