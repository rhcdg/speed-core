package net.steampunkfoundry.techdemo.docs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import net.steampunkfoundry.techdemo.docs.TestUtils;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.domain.Form.Status;
import net.steampunkfoundry.techdemo.docs.domain.SupportDocument;
import net.steampunkfoundry.techdemo.docs.repository.FormRepository;
import net.steampunkfoundry.techdemo.docs.service.CaseClient.CaseResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FormServiceSubmitTest {

    @Autowired
    FormService formService;
    @MockBean
    ValidatingMongoEventListener validator;
    @Autowired
    private FormRepository repo;
    @MockBean
    private FormNumberGenerator sequenceGenerator;

    @WithMockUser(authorities = {"Applicant"})
    @Test
    public void testANumber_TestSubmit() throws Exception {
        Form f = TestUtils.createTestForm();
        String testCaseNum = "ABC";

        Form savedForm = formService.finalizeSubmission(f, new CaseResponse(true, testCaseNum, "success"));

        assertEquals(Status.SUBMITTED, savedForm.getSubmissionStatus());
        assertNotNull(savedForm.getSubmissionDate());
        assertEquals(testCaseNum, savedForm.getCaseNumber());

    }
}
