package net.steampunkfoundry.techdemo.docs.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.domain.Form.ApplicationType;
import net.steampunkfoundry.techdemo.docs.domain.SupportDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@DataMongoTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class FormRepositoryTest {

    private final SupportDocument adventure = new SupportDocument("Adventure");
    private final Form testForm = new Form();
    @Autowired
    FormRepository repo;
    @MockBean
    RestTemplate rt;

    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
        testForm.setFirstName("Test");
        testForm.setLastName("abc123");
        testForm.setApplicationType(ApplicationType.ONE_E);
        testForm.setAnumber("A12345");
        testForm.setFormNumber("ABCDXYZ");

        testForm.getSupportDocument().add(adventure);
        repo.save(testForm);
    }

    @Test
    public void saveForm() {
        Form testForm2 = new Form();
        testForm2.setFirstName("Test2");
        testForm2.setLastName("def234");
        testForm2.setApplicationType(ApplicationType.ONE_E);
        Form saved = repo.save(testForm2);
        assertNotNull(saved);
        assertNotNull(testForm2.getId());
        assertNotNull(testForm2.getId());
    }

    @Test
    public void updateForm() {
        assertNotNull(testForm.getId());
        assertNotNull(testForm.getId());
        assertNotNull(testForm.getSupportDocument().get(0));
        SupportDocument horror = new SupportDocument("Horror");
        testForm.getSupportDocument().add(horror);
        String hex = testForm.getId().toHexString();
        Form saved = repo.save(testForm);
        assertNotNull(saved);
        assertEquals(hex, saved.getId().toHexString());
        assertTrue(saved.getSupportDocument().size() == 2);
    }

    @Test
    public void findByAnumber() {
        List<Form> forms = repo.findByAnumber("A12345");
        assertTrue("Forms list should not be empty", forms.size() > 0);
    }

    @Test
    public void findByFormNumber() {
        Form form = repo.findByFormNumber("ABCDXYZ");
        assertNotNull("Form should not be empty", form);
    }
}