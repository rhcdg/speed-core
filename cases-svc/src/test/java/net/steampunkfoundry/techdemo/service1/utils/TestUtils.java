package net.steampunkfoundry.techdemo.service1.utils;

import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.PENDING;
import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckType.A_NUMBER;

import java.time.LocalDateTime;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.dto.CaseDto;
import net.steampunkfoundry.techdemo.service1.dto.Form;


public class TestUtils {

    public static Case buildCase() {
        final Case testCase = new Case();
        testCase.setAdjudicatorId("1234");
        testCase.setApplicantId("9876");
        testCase.setCaseNumber("2021ABC456");
        testCase.setFormNumber("1");
        testCase.setANumber("A123456789");
        testCase.setStatus(Case.Status.ACCEPTING);
        testCase.setCreatedBy("Test");
        testCase.setCreatedDate(LocalDateTime.now());

        return testCase;
    }

    public static Case buildDeniedCase() {
        final Case testCase = new Case();
        testCase.setAdjudicatorId("12345");
        testCase.setCaseNumber("2021ABA456");
        testCase.setFormNumber("1");
        testCase.setStatus(Case.Status.SUBMITTED);
        testCase.setCreatedBy("Test2");
        testCase.setCreatedDate(LocalDateTime.now());

        return testCase;
    }

    public static SecurityCheck buildSecurityCheck() {
        SecurityCheck check = new SecurityCheck();
        check.setSecurityCheckType(A_NUMBER);
        check.setSecurityCheckStatus(PENDING);

        return check;
    }

    public static CaseDto buildCaseDto() {
        final CaseDto testCase = new CaseDto();
        testCase.setId(1L);
        testCase.setAdjudicatorId("1234");
        testCase.setApplicantId("9876");
        testCase.setCaseNumber("2021ABC456");
        testCase.setFormNumber("1");
        testCase.setStatus(Case.Status.ACCEPTING);
        testCase.setCreatedBy("Test");
        testCase.setANumber("A123456789");
        testCase.setCreatedDate(LocalDateTime.now());

        return testCase;
    }

    public static Form buildForm() {
        final Form form = new Form();
        form.setFormNumber("1");
        form.setAnumber("A123456789");
        form.setFirstName("Test");
        form.setLastName("Tester");

        return form;
    }
}
