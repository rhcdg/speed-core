package net.steampunkfoundry.techdemo.service1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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

@JsonIgnoreProperties(value = {"parentCase"})
@Getter
@Setter
@NoArgsConstructor
@Audited
@Entity
@Table(name = "correspondences")
@EntityListeners(AuditingEntityListener.class)
public class Correspondence extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String docType;

    /* AWS S3 unique identifier */
    private String documentKey;

    @NotAudited
    @Column(name = "case_id")
    private Long caseId;

    @NotAudited
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "case_id", insertable = false, updatable = false)
    private Case parentCase;

    public enum DocType {
        FEE_RECEIPT, APPROVAL_RECEIPT
    }
}
