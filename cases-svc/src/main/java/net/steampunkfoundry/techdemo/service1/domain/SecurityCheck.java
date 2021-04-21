package net.steampunkfoundry.techdemo.service1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@JsonIgnoreProperties(ignoreUnknown = true, value = {"parentCase"})
@Getter
@Setter
@NoArgsConstructor
@Audited
@Entity
@Table(name = "security_checks")
@EntityListeners(AuditingEntityListener.class)
public class SecurityCheck extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SecurityCheckType securityCheckType;

    @Enumerated(EnumType.STRING)
    private SecurityCheckStatus securityCheckStatus;

    @NotAudited
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case parentCase;

    @NotAudited
    @Column(name = "case_id")
    private Long caseId;

    public enum SecurityCheckStatus {
        PENDING, FAVORABLE, UNFAVORABLE, INCOMPLETE
    }

    public enum SecurityCheckType {
        A_NUMBER, FIRST_LAST_NAME
    }
}
