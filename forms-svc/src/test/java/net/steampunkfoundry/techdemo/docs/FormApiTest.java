package net.steampunkfoundry.techdemo.docs;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import net.steampunkfoundry.techdemo.docs.config.AuditorAwareImpl;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.domain.Form.ApplicationType;
import net.steampunkfoundry.techdemo.docs.domain.SupportDocument;
import net.steampunkfoundry.techdemo.docs.repository.FormRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

/**
 * @author jared.ladner
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class FormApiTest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            "hal+json");

    private final List<Form> forms = new ArrayList<>();

    @MockBean
    AuditorAware auditorAware;

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private FormRepository formRepository;

    @BeforeEach
    void setUp() {
        auditorAware = mock(AuditorAwareImpl.class);
        when(auditorAware.getCurrentAuditor()).thenReturn(Optional.of("Test"));
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        formRepository.deleteAll();

        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        Form d = TestUtils.createTestForm();
        this.forms.add(formRepository.save(d));
    }

    @Test
    @WithMockUser(authorities = {"Adjudicator"})
    public void getAllDocuments() throws Exception {
        this.mockMvc.perform(get("/api/data/forms/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.forms[0].firstName", is("A")));
    }

    @Test
    public void searchSingleDocument() throws Exception {
        this.mockMvc.perform(get("/api/data/forms/"
                + this.forms.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._links.self.href",
                        containsString(this.forms.get(0).getIdString())))
                .andExpect(jsonPath("$.firstName", is("A")));
    }

    @WithMockUser(authorities = {"Applicant"})
    @Test
    public void createDocument() throws Exception {
        SupportDocument author1 = new SupportDocument("Charles");
        SupportDocument author2 = new SupportDocument("George");

        ArrayList<SupportDocument> supportDocuments = new ArrayList<>();
        supportDocuments.add(author1);
        supportDocuments.add(author2);

        Form form = TestUtils.createTestForm();
        form.setFirstName("B");
        form.setLastName("2k3j1");
        form.setApplicationType(ApplicationType.ONE_B);
        form.setApplicantId("ABCDE");

        form.setSupportDocument(supportDocuments);

        String d = json(form);

        this.mockMvc.perform(post("/api/data/forms/")
                .contentType(contentType)
                .content(d))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/api/data/forms/search/findByApplicantId")
                .param("applicantId", "ABCDE"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.forms[0].firstName", is("B")))
                .andExpect(jsonPath("$._embedded.forms[0].supportDocument[0].name", is("Charles")))
                .andExpect(jsonPath("$._embedded.forms[0].supportDocument[1].name", is("George")))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void updateDocument() throws Exception {
        Form testD = forms.get(0);
        testD.setFirstName("D");
        testD.setPreparer(TestUtils.createTestPreparer());
        SupportDocument sd = new SupportDocument("tester");
        testD.getSupportDocument().add(sd);
        String p = json(testD);

        this.mockMvc.perform(put("/api/data/forms/"
                + this.forms.get(0).getId())
                .contentType(contentType)
                .content(p))
                .andExpect(status().isNoContent());

        this.mockMvc.perform(get("/api/data/forms/"
                + this.forms.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._links.self.href",
                        containsString(this.forms.get(0).getIdString())))
                .andExpect(jsonPath("$.firstName", is("D")));
    }

    @Test
    public void deleteDocument() throws Exception {
        this.mockMvc.perform(delete("/api/data/forms/"
                + this.forms.get(0).getId()))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(authorities = {"Applicant"})
    @Test(expected = NestedServletException.class)
    public void createDocument_InvalidField() throws Exception {

        Form form = TestUtils.createTestForm();
        form.setFirstName("B");
        form.setLastName("2k3j1");
        form.setApplicationType(ApplicationType.ONE_B);
        form.setApplicantId("ABCDE");
        //invalid a_number
        form.setAnumber("A1234567890");

        String d = json(form);

        this.mockMvc.perform(post("/api/data/forms/")
                .contentType(contentType)
                .content(d))
                .andReturn();
    }

    protected String json(final Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter
                .write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
