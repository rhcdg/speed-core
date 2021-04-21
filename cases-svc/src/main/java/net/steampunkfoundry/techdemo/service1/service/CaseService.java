package net.steampunkfoundry.techdemo.service1.service;

import static net.steampunkfoundry.techdemo.service1.domain.Case.Status.ACCEPTING;

import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence.DocType;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.repository.CaseRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CaseService {

    private final CaseRepository repository;

    private final SecurityCheckClient securityCheckClient;

    private final FormsClient formsClient;

    private final CaseNumberGenerator sequenceGenerator;

    public Case create(Case newCase) throws EntityExistsException {
        if (newCase.getId() != null) {
            throw new EntityExistsException("Case already exists");
        }
        newCase.setStatus(ACCEPTING);
        newCase.setCaseNumber(sequenceGenerator.generateSequence());

        return repository.save(newCase);
    }

    public Case createCorrespondence(Case caseObject, DocType docType) {
        if (caseObject != null) {
            Correspondence correspondence = new Correspondence();
            correspondence.setDocType(docType.toString());
            correspondence.setDocumentKey("0");
            correspondence.setCaseId(caseObject.getId());
            caseObject.getCorrespondences().add(correspondence);

            return repository.save(caseObject);
        }
        return null;
    }

    public Case requestSecurityCheck(Authentication auth, Case caseUpdate) {
        if (caseUpdate.getSecurityChecks() == null) {
            caseUpdate.setSecurityChecks(new HashSet<>());
        }

        Form form = formsClient.getFormByFormNumber(auth, caseUpdate.getFormNumber());
        if (form != null) {
            List<SecurityCheck> checks = securityCheckClient.triggerSecurityCheck(auth, form);
            if (checks != null) {
                for (SecurityCheck check : checks) {
                    check.setCaseId(caseUpdate.getId());
                    caseUpdate.getSecurityChecks().add(check);
                }
            }
        }
        repository.save(caseUpdate);

        return caseUpdate;
    }
}
