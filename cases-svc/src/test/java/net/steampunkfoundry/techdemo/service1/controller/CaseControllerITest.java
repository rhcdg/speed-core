package net.steampunkfoundry.techdemo.service1.controller;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.dto.Adjudicator;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import net.steampunkfoundry.techdemo.service1.repository.CaseRepository;
import net.steampunkfoundry.techdemo.service1.service.AdjudicatorClient;
import net.steampunkfoundry.techdemo.service1.service.FormsClient;
import net.steampunkfoundry.techdemo.service1.service.SecurityCheckClient;
import net.steampunkfoundry.techdemo.service1.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseApplication.class)
@ActiveProfiles("test")
@ContextConfiguration
public class CaseControllerITest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            "hal+json",
            StandardCharsets.UTF_8);
    private final String status = "ACCEPTING";
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CaseRepository repo;

    @MockBean
    private AdjudicatorClient adjudicatorClient;

    @MockBean
    private FormsClient formsClient;

    @MockBean
    private SecurityCheckClient securityCheckClient;

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
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        Map<String, Adjudicator> map = new HashMap<>();
        Adjudicator adj = new Adjudicator("email", "name", "id");
        map.put("id", adj);
        when(adjudicatorClient.getAdjudicators(ArgumentMatchers.any())).thenReturn(map);

        Map<String, Form> formMap = new HashMap<>();
        Form form = new Form();
        formMap.put("id", form);
        when(formsClient.getAllForms(ArgumentMatchers.any())).thenReturn(formMap);
        when(formsClient.getFormsByAnumber(ArgumentMatchers.any(), ArgumentMatchers.anyString()))
                .thenReturn(Arrays.asList(form));
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testFindAll() throws Exception {
        mockMvc.perform(get("/cases/findAll").contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testFindById() throws Exception {
        when(repo.findById(Mockito.any(Long.class))).thenReturn(Optional.of(TestUtils.buildCase()));
        mockMvc.perform(get("/cases/1").contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testFindByStatus() throws Exception {
        mockMvc.perform(get("/cases/findByStatus").param("status", status)
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testFindByAdjudicatorId() throws Exception {
        mockMvc.perform(get("/cases/findByAdjudicatorId").param("adjudicatorId", "1234")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testFindByAdjudicatorIdAndStatus() throws Exception {
        mockMvc.perform(get("/cases/findByAdjudicatorIdAndStatus")
                .param("adjudicatorId", "1234")
                .param("status", status)
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testFindByCaseNumber() throws Exception {
        mockMvc.perform(get("/cases/findByCaseNumber").param("caseNumber", "1234")
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testAssignAdjudicator() throws Exception {
        Case testCase = TestUtils.buildCase();
        testCase.setId(1L);
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);
        when(securityCheckClient
                .triggerSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(TestUtils.buildSecurityCheck()));
        mockMvc.perform(put("/cases/assignAdjudicator")
                .content(json(testCase))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testInitiateSecurityCheck() throws Exception {
        Case testCase = TestUtils.buildCase();
        testCase.setId(1L);
        when(repo.save(Mockito.any(Case.class))).thenAnswer(i -> i.getArguments()[0]);
        when(securityCheckClient
                .triggerSecurityCheck(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(TestUtils.buildSecurityCheck()));
        mockMvc.perform(put("/cases/initiateSecurityCheck")
                .content(json(testCase))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testCreateCase() throws Exception {
        mockMvc.perform(post("/api/case")
                .content(json(TestUtils.buildCase()))
                .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void testGetCaseHistory() throws Exception {
        mockMvc.perform(get("/cases/history").param("caseId", "1").contentType(contentType))
                .andExpect(status().isOk());
    }

    protected String json(final Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter
                .write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
