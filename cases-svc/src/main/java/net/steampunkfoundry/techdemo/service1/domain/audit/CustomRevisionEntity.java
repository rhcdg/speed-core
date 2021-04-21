package net.steampunkfoundry.techdemo.service1.domain.audit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import net.steampunkfoundry.techdemo.service1.config.listener.CustomRevisionEntityListener;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Getter
@Setter
@Entity
@Table(name = "revinfo")
@RevisionEntity(CustomRevisionEntityListener.class)
public class CustomRevisionEntity {

    @Id
    @GeneratedValue
    @RevisionNumber
    private Integer rev;

    @RevisionTimestamp
    private Long revtstmp;

    private String username;

}
