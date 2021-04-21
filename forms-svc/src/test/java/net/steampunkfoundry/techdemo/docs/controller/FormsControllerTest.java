package net.steampunkfoundry.techdemo.docs.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.exception.CaseSubmissionFailedException;
import net.steampunkfoundry.techdemo.docs.service.CaseClient;
import net.steampunkfoundry.techdemo.docs.service.CaseClient.CaseResponse;
import net.steampunkfoundry.techdemo.docs.service.FormService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.MissingServletRequestParameterException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FormsControllerTest {

    @Autowired
    FormsController formsController;
    @MockBean
    private FormService service;

    @MockBean
    private CaseClient client;

    @Test
    public void testCreateForm() throws Exception {
        Form f = new Form();

        when(service.save(f)).thenReturn(f);
        assertNotNull(formsController.createForm(f));
    }

    @Test
    public void testCreateForm_NullForm() throws Exception {
        assertThrows(MissingServletRequestParameterException.class, () -> {
            formsController.createForm(null);
        });
    }

    @Test
    public void testSubmitForm() throws Exception {
        Form f = new Form();
        CaseResponse response = new CaseResponse(true, "ABC", "success");
        when(client.submitForm(null, f)).thenReturn(response);
        when(service.finalizeSubmission(f, response)).thenReturn(f);
        assertNotNull(formsController.submitFormToAgency(f));
    }

    @Test
    public void testCSubmitForm_NullForm() throws Exception {
        assertThrows(MissingServletRequestParameterException.class, () -> {
            formsController.submitFormToAgency(null);
        });
    }

    @Test
    public void testCSubmitForm_FailedSubmission() throws Exception {
        Form f = new Form();
        CaseResponse response = new CaseResponse(false, null, "Failed test");
        when(client.submitForm(null, f)).thenReturn(response);

        assertThrows(CaseSubmissionFailedException.class, () -> {
            formsController.submitFormToAgency(f);
        });
    }

}
