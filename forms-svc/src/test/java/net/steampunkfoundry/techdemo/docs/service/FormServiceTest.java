package net.steampunkfoundry.techdemo.docs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class FormServiceTest {

    @Autowired
    FormService formService;
    @MockBean
    ValidatingMongoEventListener validator;
    @MockBean
    private FormRepository repo;
    @MockBean
    private FormNumberGenerator sequenceGenerator;

    @WithMockUser(authorities = {"Applicant"})
    @Test
    public void testCreateForm() throws Exception {
        Form f = new Form();
        f.setSubmissionStatus(Status.SUBMITTED);
        f.setAnumber("A123456789");
        SupportDocument sd = new SupportDocument();
        f.getSupportDocument().add(sd);

        when(repo.insert(f)).thenReturn(f);
        when(sequenceGenerator.generateSequence(Form.SEQUENCE_NAME)).thenReturn("Fake");
        Form savedForm = formService.save(f);
        assertNotNull(savedForm);
        assertEquals("New form should automatically get 'in progress status", Status.IN_PROGRESS, savedForm.getSubmissionStatus());
        assertNotNull(savedForm.getFormNumber());

        verify(repo).insert(f);
        verify(sequenceGenerator).generateSequence(any());
        verifyNoMoreInteractions(repo);
        verifyNoMoreInteractions(sequenceGenerator);
    }

    @WithMockUser(authorities = {"Applicant"})
    @Test
    public void testSave() throws Exception {
        Form f = new Form();
        f.setSubmissionStatus(Status.SUBMITTED);
        f.setFormNumber("ABC");
        f.setAnumber("A123456789");

        when(repo.insert(f)).thenReturn(f);
        Form savedForm = formService.save(f);
        assertEquals("Existing form status should not default to In Progress.", Status.SUBMITTED, savedForm.getSubmissionStatus());

        verify(repo).insert(f);
        verifyNoMoreInteractions(repo);
        verifyNoMoreInteractions(sequenceGenerator);
    }

    @WithMockUser(authorities = {"Applicant"})
    @Test
    public void testANumber_ShouldBe10Characters() throws Exception {
        Form f = new Form();
        f.setAnumber("A1234567");

        when(sequenceGenerator.generateSequence(Form.SEQUENCE_NAME)).thenReturn("Fake");
        when(repo.insert(f)).thenReturn(f);
        Form savedForm = formService.save(f);
        assertEquals(10, savedForm.getAnumber().length());
        String resultingNumber = savedForm.getAnumber().substring(1);
        assertEquals("0", resultingNumber.substring(0, 1));
        assertEquals("0", resultingNumber.substring(1, 2));
        assertEquals("1", resultingNumber.substring(2, 3));

        verify(repo).insert(f);
        verify(sequenceGenerator).generateSequence(any());
        verifyNoMoreInteractions(repo);
        verifyNoMoreInteractions(sequenceGenerator);
    }

    @WithMockUser(authorities = {"Applicant"})
    @Test
    public void testANumber_8Characters() throws Exception {
        Form f = new Form();
        f.setAnumber("A12345678");

        when(sequenceGenerator.generateSequence(Form.SEQUENCE_NAME)).thenReturn("Fake");
        when(repo.insert(f)).thenReturn(f);
        Form savedForm = formService.save(f);
        assertEquals(10, savedForm.getAnumber().length());
    }
}
