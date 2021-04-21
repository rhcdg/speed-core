package net.steampunkfoundry.techdemo.service1.config.listener;


import net.steampunkfoundry.techdemo.service1.domain.audit.CustomRevisionEntity;
import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CustomRevisionEntityListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        CustomRevisionEntity revEntity = (CustomRevisionEntity) revisionEntity;
        revEntity.setUsername(SecurityContextHolder.getContext().getAuthentication().getName() != null ? SecurityContextHolder.getContext().getAuthentication().getName() : "System");
    }
}
