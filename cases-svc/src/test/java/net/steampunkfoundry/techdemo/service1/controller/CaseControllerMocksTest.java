package net.steampunkfoundry.techdemo.service1.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence;
import net.steampunkfoundry.techdemo.service1.dto.Adjudicator;
import net.steampunkfoundry.techdemo.service1.dto.CaseDto;
import net.steampunkfoundry.techdemo.service1.dto.CaseHistory;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.dto.Pdf;
import net.steampunkfoundry.techdemo.service1.exceptions.InvalidParameterException;
import net.steampunkfoundry.techdemo.service1.exceptions.MissingParameterException;
import net.steampunkfoundry.techdemo.service1.pdf.ApprovalNoticePdfGenerator;
import net.steampunkfoundry.techdemo.service1.pdf.ReceiptPdfGenerator;
import net.steampunkfoundry.techdemo.service1.repository.CaseRepository;
import net.steampunkfoundry.techdemo.service1.service.AdjudicatorClient;
import net.steampunkfoundry.techdemo.service1.service.CaseHistoryService;
import net.steampunkfoundry.techdemo.service1.service.CaseService;
import net.steampunkfoundry.techdemo.service1.service.FormsClient;
import net.steampunkfoundry.techdemo.service1.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseApplication.class)
@ActiveProfiles("test")
@ContextConfiguration
public class CaseControllerMocksTest {

    CaseController caseController;

    @MockBean
    private CaseService service;

    @MockBean
    private CaseHistoryService caseHistoryService;

    @MockBean
    private CaseRepository repo;

    @MockBean
    private AdjudicatorClient adjudicatorClient;

    @MockBean
    private FormsClient formsClient;

    @MockBean
    private ReceiptPdfGenerator feeAcceptancePdfGenerator;

    @MockBean
    private ApprovalNoticePdfGenerator approvalPdfGenerator;

    @Before
    public void setup() throws Exception {
        caseController = new CaseController(service, caseHistoryService, repo, adjudicatorClient, formsClient,
                feeAcceptancePdfGenerator, approvalPdfGenerator);
    }

    @Test
    public void testCreateCase() throws Exception {
        CaseDto newCase = TestUtils.buildCaseDto();
        newCase.setCaseNumber(null);
        newCase.setId(null);
        when(service.create(ArgumentMatchers.any())).thenReturn(newCase);
        when(service.createCorrespondence(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(newCase);

        Case result = caseController.createCase(newCase);
        assertNotNull(result);
        verify(service).create(ArgumentMatchers.any());
    }

    @Test
    public void testCreateCase_MissingApplicant() throws Exception {
        CaseDto newCase = TestUtils.buildCaseDto();
        newCase.setCaseNumber(null);
        newCase.setId(null);
        newCase.setApplicantId(null);

        Exception exception = assertThrows(MissingParameterException.class, () -> caseController.createCase(newCase));

        String expectedMessage = "'Applicant Id' is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateCase_MissingANumber() throws Exception {
        CaseDto newCase = TestUtils.buildCaseDto();
        newCase.setCaseNumber(null);
        newCase.setId(null);
        newCase.setANumber(null);

        Exception exception = assertThrows(MissingParameterException.class, () -> caseController.createCase(newCase));

        String expectedMessage = "'A-Number' is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testCreateCase_MissingFormNumber() throws Exception {
        CaseDto newCase = TestUtils.buildCaseDto();
        newCase.setCaseNumber(null);
        newCase.setId(null);
        newCase.setFormNumber(null);

        Exception exception = assertThrows(MissingParameterException.class, () -> caseController.createCase(newCase));

        String expectedMessage = "'Form Number' is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindAll() throws Exception {
        List<Case> list = Arrays.asList(TestUtils.buildCase());
        when(repo.findAll()).thenReturn(list);

        List result = caseController.findAll();
        assertTrue(result.size() > 0);
        verify(repo).findAll();
    }

    @Test
    public void testFindById() throws Exception {
        Case testCase = TestUtils.buildCase();
        testCase.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(testCase));

        Case result = caseController.findById(1L);
        assertNotNull(result);
        verify(repo).findById(1L);
    }

    @Test
    public void testFindById_WithAdjudicatorAndForm() throws Exception {
        Case testCase = TestUtils.buildCase();
        testCase.setId(1L);
        when(repo.findById(1L)).thenReturn(Optional.of(testCase));

        Map<String, Adjudicator> map = new HashMap<>();
        String fakeName = "name";
        Adjudicator adj = new Adjudicator("email", fakeName, "id");
        map.put("id", adj);
        when(adjudicatorClient.getAdjudicators(null)).thenReturn(map);

        Map<String, Form> formMap = new HashMap<>();
        Form form = TestUtils.buildForm();
        formMap.put(form.getAnumber(), form);
        when(formsClient.getAllForms(null)).thenReturn(formMap);

        Case result = caseController.findById(1L);
        assertNotNull(result);
        verify(repo).findById(1L);
    }

    @Test
    public void testFindById_NotFound() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> caseController.findById(-1L));
    }

    @Test
    public void testFindByStatus() throws Exception {
        List<Case> list = Arrays.asList(TestUtils.buildCase());
        when(repo.findByStatus(Case.Status.ACCEPTING)).thenReturn(list);

        List result = caseController.findByStatus("ACCEPTING");
        assertTrue(result.size() > 0);
        verify(repo).findByStatus(Case.Status.ACCEPTING);
    }

    @Test
    public void testFindByStatus_InvalidStatus() throws Exception {
        Exception exception = assertThrows(InvalidParameterException.class, () -> caseController.findByStatus("XXXX"));

        String expectedMessage = "Parameter 'status' is invalid.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindByAdjudicatorId() throws Exception {
        List<Case> list = Arrays.asList(TestUtils.buildCase());
        when(repo.findByAdjudicatorId("1234")).thenReturn(list);

        List result = caseController.findByAdjudicatorId("1234");
        assertTrue(result.size() > 0);
        verify(repo).findByAdjudicatorId("1234");
    }

    @Test
    public void testFindByAdjudicatorId_NullAdjudicatorId() throws Exception {
        Exception exception = assertThrows(MissingParameterException.class,
                () -> caseController.findByAdjudicatorId(null));

        String expectedMessage = "'Adjudicator Id' is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindByAdjudicatorIdAndStatus() throws Exception {
        List<Case> list = Arrays.asList(TestUtils.buildCase());
        when(repo.findByAdjudicatorIdAndStatus("1234", Case.Status.ACCEPTING)).thenReturn(list);

        List result = caseController.findByAdjudicatorIdAndStatus("1234", "ACCEPTING");
        assertTrue(result.size() > 0);
        verify(repo).findByAdjudicatorIdAndStatus("1234", Case.Status.ACCEPTING);
    }

    @Test
    public void testFindByAdjudicatorIdAndStatus_InvalidStatus() throws Exception {
        Exception exception = assertThrows(InvalidParameterException.class,
                () -> caseController.findByAdjudicatorIdAndStatus("1234", "XXXX"));

        String expectedMessage = "Parameter 'status' is invalid.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindByAdjudicatorIdAndStatus_NullAdjudicatorId() throws Exception {
        Exception exception = assertThrows(MissingParameterException.class,
                () -> caseController.findByAdjudicatorIdAndStatus(null, "ACCEPTING"));

        String expectedMessage = "'Adjudicator Id' is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindByCaseNumber() throws Exception {
        List<Case> list = Arrays.asList(TestUtils.buildCase());
        when(repo.findByCaseNumber("2021ABC456")).thenReturn(list);

        List result = caseController.findByCaseNumber("2021ABC456");
        assertTrue(result.size() > 0);
        verify(repo).findByCaseNumber("2021ABC456");
    }

    @Test
    public void testFindByCaseNumber_NullCaseNumber() throws Exception {
        Exception exception = assertThrows(MissingParameterException.class,
                () -> caseController.findByCaseNumber(null));

        String expectedMessage = "'Case Number' is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAssignAdjudicator() throws Exception {
        CaseDto assignedCase = TestUtils.buildCaseDto();
        when(service.requestSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(assignedCase);

        Map<String, Form> formMap = new HashMap<>();
        Form form = new Form();
        formMap.put("id", form);
        when(formsClient.getAllForms(any(Authentication.class))).thenReturn(formMap);

        Case result = caseController.assignAdjudicator(assignedCase);
        assertNotNull(result);
        verify(service).requestSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    public void testAssignAdjudicator_NullAdjudicatorId() throws Exception {
        CaseDto assignedCase = TestUtils.buildCaseDto();
        assignedCase.setAdjudicatorId(null);
        Exception exception = assertThrows(MissingParameterException.class,
                () -> caseController.assignAdjudicator(assignedCase));

        String expectedMessage = "'Adjudicator Id' is required.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAssignAdjudicator_CaseNotFound() throws Exception {
        CaseDto assignedCase = TestUtils.buildCaseDto();
        when(service.requestSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(null);

        Case result = caseController.assignAdjudicator(assignedCase);
        assertNull(result);
        verify(service).requestSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    public void testInitiateSecurityCheck() throws Exception {
        CaseDto testCase = TestUtils.buildCaseDto();
        when(service.requestSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(testCase);
        Map<String, Form> formMap = new HashMap<>();
        Form form = new Form();
        formMap.put("id", form);
        when(formsClient.getAllForms(any(Authentication.class))).thenReturn(formMap);

        Case result = caseController.initiateSecurityCheck(testCase);
        assertNotNull(result);
    }

    @Test
    public void testApprove() throws Exception {
        CaseDto testCase = TestUtils.buildCaseDto();
        when(service.createCorrespondence(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(testCase);

        Case result = caseController.approve(testCase);
        assertNotNull(result);
    }

    @Test
    public void testGetHistoryWithAdjudicatorInfo() throws Exception {
        Map<String, Adjudicator> map = new HashMap<>();
        String fakeName = "name";
        Adjudicator adj = new Adjudicator("email", fakeName, "id");
        map.put("id", adj);
        when(adjudicatorClient.getAdjudicators(null)).thenReturn(map);

        Long caseId = 1L;
        Case kase = TestUtils.buildCase();
        kase.setAdjudicatorId("id");
        kase.setId(caseId);
        CaseHistory ch = new CaseHistory(caseId.toString(), "event", "today", "time", "id", 0, LocalDateTime.now());
        when(caseHistoryService.getCaseHistory(kase)).thenReturn(new HashSet<>(Arrays.asList(ch)));

        Optional<Case> optionalCase = Optional.of(kase);
        when(repo.findById(caseId)).thenReturn(optionalCase);

        Set<CaseHistory> result = caseController.getCaseHistory(1L);
        assertEquals(1, result.size());
        assertEquals(fakeName, result.iterator().next().getAdjudicatorName());

        verify(adjudicatorClient).getAdjudicators(null);
        verify(caseHistoryService).getCaseHistory(kase);
        verify(repo).findById(caseId);
    }

    @Test
    public void testGetPrintablePdfs() throws Exception {
        Form form = new Form();
        form.setAddress("test");
        form.setAddress("test");
        form.setCity("test");
        form.setState("MS");
        form.setZipCode("12345");

        Correspondence corr1 = new Correspondence();
        corr1.setLastModified(LocalDateTime.now());
        corr1.setCreatedDate(LocalDateTime.now());
        corr1.setCaseId(1123456L);
        corr1.setDocumentKey("test key");
        corr1.setId(1L);
        corr1.setDocType(Correspondence.DocType.FEE_RECEIPT.toString());

        Correspondence corr2 = new Correspondence();
        corr2.setLastModified(LocalDateTime.now());
        corr2.setCreatedDate(LocalDateTime.now());
        corr2.setCaseId(1123456L);
        corr2.setDocumentKey("test key");
        corr2.setId(2L);
        corr2.setDocType(Correspondence.DocType.APPROVAL_RECEIPT.toString());

        Correspondence corr3 = new Correspondence();
        corr3.setLastModified(LocalDateTime.now());
        corr3.setCreatedDate(LocalDateTime.now());
        corr3.setCaseId(1123456L);
        corr3.setDocumentKey("test key");
        corr3.setId(3L);

        List<Case> list = Arrays.asList(TestUtils.buildCase());
        Case c = list.get(0);
        c.getCorrespondences().add(corr1);
        c.getCorrespondences().add(corr2);
        c.getCorrespondences().add(corr3);
        Map<String, Form> formMap = new HashMap<>();
        formMap.put(c.getANumber(), form);
        when(repo.findAll()).thenReturn(list);
        when(formsClient.getAllForms(any())).thenReturn(formMap);

        InputStream inputStream = new ClassPathResource("/fakepdf/fake.pdf").getInputStream();
        when(approvalPdfGenerator.generateApprovalNoticePdf(any())).thenReturn(inputStream);
        when(feeAcceptancePdfGenerator.generateReceiptPdf(any())).thenReturn(inputStream);
        Set<Pdf> result = caseController.getPrintablePdfs();

        formMap.put(c.getANumber(), null);
        when(formsClient.getAllForms(any())).thenReturn(formMap);
        result = caseController.getPrintablePdfs();
    }
}
