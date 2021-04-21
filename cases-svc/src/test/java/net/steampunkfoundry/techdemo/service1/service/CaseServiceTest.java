package net.steampunkfoundry.techdemo.service1.service;

import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.FAVORABLE;
import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckType.A_NUMBER;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import javax.persistence.EntityExistsException;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence.DocType;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.exceptions.MissingParameterException;
import net.steampunkfoundry.techdemo.service1.repository.CaseRepository;
import net.steampunkfoundry.techdemo.service1.utils.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = SecurityConfig.class)
public class CaseServiceTest {

    @Autowired
    private CaseService service;

    @MockBean
    private CaseRepository repo;

    @MockBean
    private SecurityCheckClient securityCheckClient;

    @MockBean FormsClient formsClient;

    @Test
    public void testRequestSecurityCheck() {
        final Case testCase = TestUtils.buildCase();
        SecurityCheck check = new SecurityCheck();
        check.setSecurityCheckStatus(FAVORABLE);
        check.setSecurityCheckType(A_NUMBER);

        when(securityCheckClient
                .triggerSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(check));
        when(formsClient.getFormByFormNumber(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(TestUtils.buildForm());
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);
        when(repo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(testCase));

        Case result = service.requestSecurityCheck(null, testCase);
        assertNotNull(result);
        assertEquals(1, result.getSecurityChecks().size());
    }

    @Test
    public void testRequestSecurityCheck_EmptySecurityCheckObject() {
        Case testCase = TestUtils.buildCase();
        testCase.setSecurityChecks(null);
        when(securityCheckClient
                .triggerSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(null);
        when(formsClient.getFormByFormNumber(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(TestUtils.buildForm());
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);
        when(repo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(testCase));

        Case result = service.requestSecurityCheck(null, testCase);
        assertNotNull(result);
        assertEquals(0, result.getSecurityChecks().size());
    }

    @Test
    public void testRequestSecurityCheck_WithAdjudicator() {
        final Case testCase = TestUtils.buildCase();
        testCase.setAdjudicatorId("9876");
        SecurityCheck check = new SecurityCheck();
        check.setSecurityCheckStatus(FAVORABLE);
        check.setSecurityCheckType(A_NUMBER);

        when(securityCheckClient
                .triggerSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(check));
        when(formsClient.getFormByFormNumber(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(TestUtils.buildForm());
        when(repo.findById(1L)).thenReturn(Optional.of(testCase));
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);

        Case assignedCase = service.requestSecurityCheck(null, testCase);
        assertNotNull(assignedCase);
        assertEquals("9876", assignedCase.getAdjudicatorId());
        assertEquals(1, assignedCase.getSecurityChecks().size());
    }

    @Test
    public void testRequestSecurityCheck_PreviousChecks() {
        final Case testCase = TestUtils.buildCase();
        SecurityCheck check = new SecurityCheck();
        check.setSecurityCheckStatus(FAVORABLE);
        check.setSecurityCheckType(A_NUMBER);

        when(securityCheckClient
                .triggerSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(check));
        when(formsClient.getFormByFormNumber(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(TestUtils.buildForm());
        testCase.setSecurityChecks(new HashSet<SecurityCheck>());
        testCase.getSecurityChecks().add(TestUtils.buildSecurityCheck());
        when(repo.findById(1L)).thenReturn(Optional.of(testCase));
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);

        Case result = service.requestSecurityCheck(null, testCase);
        assertNotNull(result);
        assertEquals(2, result.getSecurityChecks().size());
    }

    @Test
    public void testCreateCase() {
        final Case testCase = TestUtils.buildCase();
        SecurityCheck check = new SecurityCheck();
        check.setSecurityCheckStatus(FAVORABLE);
        check.setSecurityCheckType(A_NUMBER);

        when(securityCheckClient
                .triggerSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(check));
        when(formsClient.getFormsByAnumber(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(
                Arrays.asList(TestUtils.buildForm()));
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);

        Case result = service.create(testCase);
        assertNotNull(result);
    }

    @Test
    public void testCreateCase_AlreadyExists() {
        final Case testCase = TestUtils.buildCase();
        testCase.setId(1L);

        when(formsClient.getFormsByAnumber(ArgumentMatchers.any(), ArgumentMatchers.anyString())).thenReturn(
                Arrays.asList(TestUtils.buildForm()));
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);

        Exception exception = assertThrows(EntityExistsException.class, () ->
                service.create(testCase));

        String expectedMessage = "Case already exists";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testCreateCorrespondence() {
        final Case testCase = TestUtils.buildCase();

        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);

        Case result = service.createCorrespondence(testCase, DocType.FEE_RECEIPT);
        assertNotNull(result);
        assertEquals(1, result.getCorrespondences().size());
    }

    @Test
    public void testCreateCorrespondence_NullCase() {
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);

        Case result = service.createCorrespondence(null, DocType.FEE_RECEIPT);
        assertNull(result);
    }
}