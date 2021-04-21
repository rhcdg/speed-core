package net.steampunkfoundry.techdemo.service1.service;

import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.FAVORABLE;
import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.INCOMPLETE;
import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.PENDING;
import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.UNFAVORABLE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Case.Status;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.dto.CaseHistory;
import net.steampunkfoundry.techdemo.service1.utils.TestUtils;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.query.AuditQuery;
import org.hibernate.envers.query.AuditQueryCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = SecurityConfig.class)
public class CaseHistoryServiceTest {

    @Autowired
    private CaseHistoryService service;

    @MockBean
    private AuditReader auditReader;

    @Test
    public void testGetHistory_Full() {
        final Case kase = createTestCase();
        SecurityCheck s2 = TestUtils.buildSecurityCheck();
        s2.setSecurityCheckStatus(FAVORABLE);
        s2.setCaseId(kase.getId());
        s2.setId(2L);
        kase.getSecurityChecks().add(s2);

        /* Set up test data for Case */
        Integer revisionNumber = 1;
        String revisedBy = "Tester";
        Long revisedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up test data for Correspondence */
        String revisedByCorrespondence = "Tester2";
        Long revisedDateCorrespondence = LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsCorrespondenceArray = new Object[]{revisionNumber, revisedByCorrespondence, revisedDateCorrespondence};
        List<Object> objectsCorrespondence = new ArrayList<>();
        objectsCorrespondence.add(objectsCorrespondenceArray);

        /* Set up test data for SecurityCheck */
        String revisedBySecurityCheck = "Tester3";
        Long revisedDateSecurityCheck = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsSecurityCheckArray = new Object[]{revisionNumber, revisedBySecurityCheck, revisedDateSecurityCheck};
        List<Object> objectsSecurityCheck = new ArrayList<>();
        objectsSecurityCheck.add(objectsSecurityCheckArray);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQueryCase = mock(AuditQuery.class);
        AuditQuery mockAuditQueryC = mock(AuditQuery.class);
        AuditQuery mockAuditQuerySc = mock(AuditQuery.class);

        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(kase);
        when(auditReader.find(Correspondence.class, kase.getCorrespondences().iterator().next().getId(), revisionNumber)).thenReturn(kase.getCorrespondences().iterator().next());
        when(auditReader.find(SecurityCheck.class, kase.getSecurityChecks().iterator().next().getId(), revisionNumber)).thenReturn(kase.getSecurityChecks().iterator().next());
        when(auditReader.find(SecurityCheck.class, kase.getSecurityChecks().iterator().next().getId(), revisionNumber)).thenReturn(kase.getSecurityChecks().iterator().next());

        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQueryCase);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Correspondence.class, false, true)).thenReturn(mockAuditQueryC);
        when(mockAuditQueryCreator.forRevisionsOfEntity(SecurityCheck.class, false, true)).thenReturn(mockAuditQuerySc);
        when(mockAuditQueryCase.getResultList()).thenReturn(objects);
        when(mockAuditQueryC.getResultList()).thenReturn(objectsCorrespondence);
        when(mockAuditQuerySc.getResultList()).thenReturn(objectsSecurityCheck);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertEquals(3, list.size());
    }

    @Test
    public void testGetHistory_CaseAndSecurityCheckOnly() {
        final Case kase = createTestCase();
        kase.setStatus(Status.VALIDATED);
        kase.setCorrespondences(new HashSet<>());
        SecurityCheck sc = kase.getSecurityChecks().iterator().next();
        sc.setSecurityCheckStatus(PENDING);
        sc.setId(2L);
        kase.getSecurityChecks().add(sc);

        /* Set up test data for Case */
        Integer revisionNumber = 1;
        String revisedBy = "Tester";
        Long revisedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up test data for SecurityCheck */
        String revisedBySecurityCheck = "Tester";
        Integer revisionNumberSecurityCheck = 1;
        Long revisedDateSecurityCheck = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsSecurityCheckArray = new Object[]{revisionNumberSecurityCheck, revisedBySecurityCheck, revisedDateSecurityCheck};
        List<Object> objectsSecurityCheck = new ArrayList<>();
        objectsSecurityCheck.add(objectsSecurityCheckArray);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQuery1 = mock(AuditQuery.class);
        AuditQuery mockAuditQuery2 = mock(AuditQuery.class);
        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(kase);
        when(auditReader.find(SecurityCheck.class, sc.getId(), revisionNumber)).thenReturn(sc);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQuery1);
        when(mockAuditQueryCreator.forRevisionsOfEntity(SecurityCheck.class, false, true)).thenReturn(mockAuditQuery2);
        when(mockAuditQuery1.getResultList()).thenReturn(objects);
        when(mockAuditQuery2.getResultList()).thenReturn(objectsSecurityCheck);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertTrue(list.size() > 0);
    }

    @Test
    public void testGetHistory_CaseAndSecurityCheckStatus() {
        final Case kase = createTestCase();
        kase.setStatus(Status.SUBMITTED);
        kase.setCorrespondences(new HashSet<>());
        SecurityCheck sc = kase.getSecurityChecks().iterator().next();
        sc.setSecurityCheckStatus(FAVORABLE);
        sc.setId(2L);
        kase.getSecurityChecks().add(sc);

        /* Set up test data for Case */
        Integer revisionNumber = 1;
        String revisedBy = "Tester";
        Long revisedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up test data for SecurityCheck */
        String revisedBySecurityCheck = "Tester";
        Integer revisionNumberSecurityCheck = 1;
        Long revisedDateSecurityCheck = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsSecurityCheckArray = new Object[]{revisionNumberSecurityCheck, revisedBySecurityCheck, revisedDateSecurityCheck};
        List<Object> objectsSecurityCheck = new ArrayList<>();
        objectsSecurityCheck.add(objectsSecurityCheckArray);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQuery1 = mock(AuditQuery.class);
        AuditQuery mockAuditQuery2 = mock(AuditQuery.class);
        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(kase);
        when(auditReader.find(SecurityCheck.class, sc.getId(), revisionNumber)).thenReturn(sc);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQuery1);
        when(mockAuditQueryCreator.forRevisionsOfEntity(SecurityCheck.class, false, true)).thenReturn(mockAuditQuery2);
        when(mockAuditQuery1.getResultList()).thenReturn(objects);
        when(mockAuditQuery2.getResultList()).thenReturn(objectsSecurityCheck);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertEquals(2, list.size());

    }

    @Test
    public void testGetHistory_CaseAndSecurityCheckStatus_Unfavorable() {
        final Case kase = createTestCase();
        kase.setStatus(Status.SUBMITTED);
        kase.setCorrespondences(new HashSet<>());
        SecurityCheck sc = kase.getSecurityChecks().iterator().next();
        sc.setSecurityCheckStatus(UNFAVORABLE);
        sc.setId(2L);
        kase.getSecurityChecks().add(sc);

        /* Set up test data for Case */
        Integer revisionNumber = 1;
        String revisedBy = "Tester";
        Long revisedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up test data for SecurityCheck */
        String revisedBySecurityCheck = "Tester";
        Integer revisionNumberSecurityCheck = 1;
        Long revisedDateSecurityCheck = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsSecurityCheckArray = new Object[]{revisionNumberSecurityCheck, revisedBySecurityCheck, revisedDateSecurityCheck};
        List<Object> objectsSecurityCheck = new ArrayList<>();
        objectsSecurityCheck.add(objectsSecurityCheckArray);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQuery1 = mock(AuditQuery.class);
        AuditQuery mockAuditQuery2 = mock(AuditQuery.class);
        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(kase);
        when(auditReader.find(SecurityCheck.class, sc.getId(), revisionNumber)).thenReturn(sc);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQuery1);
        when(mockAuditQueryCreator.forRevisionsOfEntity(SecurityCheck.class, false, true)).thenReturn(mockAuditQuery2);
        when(mockAuditQuery1.getResultList()).thenReturn(objects);
        when(mockAuditQuery2.getResultList()).thenReturn(objectsSecurityCheck);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertEquals(2, list.size());

    }

    @Test
    public void testGetHistory_CaseAndSecurityCheckStatus_Incomplete() {
        final Case kase = createTestCase();
        kase.setStatus(Status.SUBMITTED);
        kase.setCorrespondences(new HashSet<>());
        SecurityCheck sc = kase.getSecurityChecks().iterator().next();
        sc.setSecurityCheckStatus(INCOMPLETE);
        sc.setId(2L);
        kase.getSecurityChecks().add(sc);

        /* Set up test data for Case */
        Integer revisionNumber = 1;
        String revisedBy = "Tester";
        Long revisedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up test data for SecurityCheck */
        String revisedBySecurityCheck = "Tester";
        Integer revisionNumberSecurityCheck = 1;
        Long revisedDateSecurityCheck = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsSecurityCheckArray = new Object[]{revisionNumberSecurityCheck, revisedBySecurityCheck, revisedDateSecurityCheck};
        List<Object> objectsSecurityCheck = new ArrayList<>();
        objectsSecurityCheck.add(objectsSecurityCheckArray);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQuery1 = mock(AuditQuery.class);
        AuditQuery mockAuditQuery2 = mock(AuditQuery.class);
        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(kase);
        when(auditReader.find(SecurityCheck.class, sc.getId(), revisionNumber)).thenReturn(sc);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQuery1);
        when(mockAuditQueryCreator.forRevisionsOfEntity(SecurityCheck.class, false, true)).thenReturn(mockAuditQuery2);
        when(mockAuditQuery1.getResultList()).thenReturn(objects);
        when(mockAuditQuery2.getResultList()).thenReturn(objectsSecurityCheck);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertEquals(2, list.size());

    }

    @Test
    public void testGetHistory_NullFields() {
        final Case kase = createTestCase();
        kase.setCorrespondences(new HashSet<>());
        kase.setSecurityChecks(new HashSet<>());

        /* Set up test data for Case */
        Integer revisionNumber = null;
        String revisedBy = null;
        Long revisedDate = null;
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQuery = mock(AuditQuery.class);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(null);
        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQuery);
        when(mockAuditQuery.getResultList()).thenReturn(objects);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertEquals(0, list.size());

    }

    @Test
    public void testGetHistory_NullModifiedDateFields() {
        final Case kase = createTestCase();
        kase.setStatus(Status.REJECTED);
        kase.setCorrespondences(new HashSet<>());
        kase.setSecurityChecks(new HashSet<>());

        /* Set up test data for Case */
        Integer revisionNumber = 1;
        String revisedBy = "Tester";
        Long revisedDate = null;
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQuery = mock(AuditQuery.class);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(kase);
        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQuery);
        when(mockAuditQuery.getResultList()).thenReturn(objects);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertEquals(1, list.size());

    }

    @Test
    public void testGetHistory_NullCorrespondence() {
        final Case kase = createTestCase();

        /* Set up test data for Case */
        Integer revisionNumber = 1;
        String revisedBy = "Tester";
        Long revisedDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] o = new Object[]{revisionNumber, revisedBy, revisedDate};
        List<Object> objects = new ArrayList<>();
        objects.add(o);

        /* Set up test data for Correspondence */
        String revisedByCorrespondence = "Tester2";
        Long revisedDateCorrespondence = LocalDateTime.now().plusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsCorrespondenceArray = new Object[]{revisionNumber, revisedByCorrespondence, revisedDateCorrespondence};
        List<Object> objectsCorrespondence = new ArrayList<>();
        objectsCorrespondence.add(objectsCorrespondenceArray);

        /* Set up test data for SecurityCheck */
        String revisedBySecurityCheck = "Tester3";
        Long revisedDateSecurityCheck = LocalDateTime.now().plusDays(2).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Object[] objectsSecurityCheckArray = new Object[]{revisionNumber, revisedBySecurityCheck, revisedDateSecurityCheck};
        List<Object> objectsSecurityCheck = new ArrayList<>();
        objectsSecurityCheck.add(objectsSecurityCheckArray);

        /* Set up Mocks */
        AuditQueryCreator mockAuditQueryCreator = mock(AuditQueryCreator.class);
        AuditQuery mockAuditQueryCase = mock(AuditQuery.class);
        AuditQuery mockAuditQueryC = mock(AuditQuery.class);
        AuditQuery mockAuditQuerySc = mock(AuditQuery.class);

        when(auditReader.createQuery()).thenReturn(mockAuditQueryCreator);
        when(auditReader.find(Case.class, kase.getId(), revisionNumber)).thenReturn(kase);
        when(auditReader.find(Correspondence.class, kase.getCorrespondences().iterator().next().getId(), revisionNumber)).thenReturn(null);
        when(auditReader.find(SecurityCheck.class, kase.getSecurityChecks().iterator().next().getId(), revisionNumber)).thenReturn(kase.getSecurityChecks().iterator().next());
        when(mockAuditQueryCreator.forRevisionsOfEntity(Case.class, false, true)).thenReturn(mockAuditQueryCase);
        when(mockAuditQueryCreator.forRevisionsOfEntity(Correspondence.class, false, true)).thenReturn(mockAuditQueryC);
        when(mockAuditQueryCreator.forRevisionsOfEntity(SecurityCheck.class, false, true)).thenReturn(mockAuditQuerySc);
        when(mockAuditQueryCase.getResultList()).thenReturn(objects);
        when(mockAuditQueryC.getResultList()).thenReturn(objectsCorrespondence);
        when(mockAuditQuerySc.getResultList()).thenReturn(objectsSecurityCheck);

        Set<CaseHistory> list = service.getCaseHistory(kase);
        assertEquals(2, list.size());
    }

    private Case createTestCase() {
        Case kase = TestUtils.buildCase();
        kase.setId(1L);

        Correspondence c = new Correspondence();
        c.setDocType("FeeReceipt");
        c.setDocumentKey("ABC1234");
        c.setCaseId(kase.getId());
        c.setId(1L);
        kase.getCorrespondences().add(c);

        SecurityCheck s = TestUtils.buildSecurityCheck();
        s.setCaseId(kase.getId());
        s.setId(1L);
        kase.getSecurityChecks().add(s);
        return kase;
    }
}