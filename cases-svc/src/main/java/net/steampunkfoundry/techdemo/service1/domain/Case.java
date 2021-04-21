package net.steampunkfoundry.techdemo.service1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@Audited
@Entity
@Table(name = "cases")
@EntityListeners(AuditingEntityListener.class)
public class Case extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String caseNumber;

    private String formNumber;

    /* this is the adjudicator's AWS cognito ID */
    private String adjudicatorId;

    /* this is the applicant's AWS cognito ID */
    private String applicantId;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String aNumber;

    private Boolean supportingEvidenceValidated;

    private Boolean caseCorrespondenceValidated;

    private Boolean securityCheckValidated;

    private String decisionJustification;

    @NotAudited
    @OneToMany(mappedBy = "parentCase", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Correspondence> correspondences = new HashSet<>();

    @NotAudited
    @OneToMany(mappedBy = "parentCase", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<SecurityCheck> securityChecks = new HashSet<>();

    public enum Status {
        ACCEPTING, SUBMITTED, VALIDATED, REJECTED
    }

}
