package net.steampunkfoundry.techdemo.service1.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Case.Status;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus;
import net.steampunkfoundry.techdemo.service1.dto.CaseHistory;
import net.steampunkfoundry.techdemo.service1.dto.RevisionEntity;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseHistoryService {

    private final AuditReader auditReader;

    public Set<CaseHistory> getCaseHistory(Case kase) {
        String caseNumber = kase.getCaseNumber();
        Set<CaseHistory> history = new HashSet<>();

        /* Iterate through Cases and add to the Case Histories */
        history.addAll(constructCaseHistory(kase));

        /* Iterate through SecurityChecks and add to the Case Histories */
        history.addAll(constructSecurityCheckHistory(kase, caseNumber));

        /* Iterate through Correspondences and add to the Case Histories */
        history.addAll(constructCorrespondenceHistory(kase, caseNumber));

        return history;
    }

    private List<CaseHistory> constructCaseHistory(Case kase) {
        List<CaseHistory> history = new ArrayList<>();
        getAuditQuery(Case.class).getResultList().stream()
                .map(o -> createRevisionMeta((Object[]) o))
                .forEach(revision -> {
                    RevisionEntity rev = (RevisionEntity) revision;
                    Case auditedCase = auditReader.find(Case.class, kase.getId(), rev.getRevisionNumber());
                    if (auditedCase != null) {
                        CaseHistory ch = new CaseHistory(
                                auditedCase.getCaseNumber(),
                                formatCaseCheckEvent(auditedCase.getStatus()),
                                constructDateString(rev.getRevisedDate()),
                                constructTimeString(rev.getRevisedDate()),
                                rev.getRevisedBy(),
                                rev.getRevisionNumber(),
                                rev.getRevisedDate());
                        history.add(ch);
                    }
                });
        return history;
    }

    private List<CaseHistory> constructCorrespondenceHistory(Case kase, String caseNumber) {
        List<CaseHistory> history = new ArrayList<>();
        kase.getCorrespondences().stream().map(Correspondence::getId).forEach(correspondenceId ->
                getAuditQuery(Correspondence.class).getResultList().stream()
                        .map(o -> createRevisionMeta((Object[]) o))
                        .forEach(revision -> {
                            RevisionEntity rev = (RevisionEntity) revision;
                            Correspondence ac = auditReader.find(Correspondence.class, correspondenceId, rev.getRevisionNumber());
                            if (ac != null) {
                                CaseHistory ch = new CaseHistory(
                                        caseNumber,
                                        ac.getDocType(),
                                        constructDateString(rev.getRevisedDate()),
                                        constructTimeString(rev.getRevisedDate()),
                                        rev.getRevisedBy(),
                                        rev.getRevisionNumber(),
                                        rev.getRevisedDate());
                                history.add(ch);
                            }
                        })
        );
        return history;
    }

    private List<CaseHistory> constructSecurityCheckHistory(Case kase, String caseNumber) {
        List<CaseHistory> history = new ArrayList<>();
        kase.getSecurityChecks().stream()
                .map(SecurityCheck::getId)
                .forEach(securityId ->
                        getAuditQuery(SecurityCheck.class).getResultList().stream()
                                .map(o -> createRevisionMeta((Object[]) o))
                                .forEach(revision -> {
                                    RevisionEntity rev = (RevisionEntity) revision;
                                    SecurityCheck sc = auditReader.find(SecurityCheck.class, securityId, rev.getRevisionNumber());
                                    if (sc != null) {
                                        CaseHistory ch = new CaseHistory(
                                                caseNumber,
                                                formatSecurityCheckEvent(sc),
                                                constructDateString(rev.getRevisedDate()),
                                                constructTimeString(rev.getRevisedDate()),
                                                rev.getRevisedBy(),
                                                rev.getRevisionNumber(),
                                                rev.getRevisedDate());
                                        history.add(ch);
                                    }
                                })
        );
        return history;
    }

    private String formatCaseCheckEvent(Status status) {
        String eventType = "Case ";
        switch (status) {
          case VALIDATED:
              return eventType + "Validated";
          case SUBMITTED:
              return eventType + "Submitted";
          case REJECTED:
              return eventType + "Rejected";
          case ACCEPTING:
          default:
              return eventType + "Accepting";
        }
    }

    private String formatSecurityCheckEvent(SecurityCheck sc) {
        String eventType = sc.getSecurityCheckType() + " ";
        switch (sc.getSecurityCheckStatus()) {
          case PENDING:
              return eventType + "Initiated";
          case FAVORABLE:
              return eventType + "Favorable";
          case UNFAVORABLE:
              return eventType + "Unfavorable";
          case INCOMPLETE:
          default:
              return eventType + "Incomplete";
        }
    }

    private AuditQuery getAuditQuery(Class<?> clazz) {
        AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(clazz, false, true);
        q.addProjection(AuditEntity.revisionNumber());
        q.addProjection(AuditEntity.revisionProperty("username"));
        q.addProjection(AuditEntity.revisionProperty("revtstmp"));
        return q;
    }

    private RevisionEntity createRevisionMeta(Object[] objArray) {
        Integer revNumber = objArray[0] != null ? Integer.valueOf(objArray[0].toString()) : null;
        String revisedBy = objArray[1] != null ? objArray[1].toString() : null;
        LocalDateTime revisedDate = objArray[2] != null ? LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.valueOf(objArray[2].toString())), TimeZone.getDefault().toZoneId()) : null;
        return new RevisionEntity(revNumber, revisedBy, revisedDate);
    }

    private String constructTimeString(LocalDateTime lastModified) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return lastModified != null ? lastModified.format(formatter) : "";
    }

    private String constructDateString(LocalDateTime lastModified) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM - dd - yyyy");
        return lastModified != null ? lastModified.format(formatter) : "";
    }

}
