package net.steampunkfoundry.techdemo.service1.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable {

    /*
    NOTE Hibernate Envers does NOT keep track of changes to fields in a mapped superclass. However, that's unimportant for these specific fields (created by, modified by, created date, modified date) because Envers
    DOES track who made changes and on what date, but in a separate field in the 'revinfo' table. Bottom line is all of this information is being recorded.

    But be aware that if additional fields are added here, they may not be audited.
    */

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModified;
}
