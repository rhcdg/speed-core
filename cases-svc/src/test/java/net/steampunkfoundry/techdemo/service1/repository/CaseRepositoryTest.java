package net.steampunkfoundry.techdemo.service1.repository;

import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckStatus.UNFAVORABLE;
import static net.steampunkfoundry.techdemo.service1.domain.SecurityCheck.SecurityCheckType.A_NUMBER;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Correspondence;
import net.steampunkfoundry.techdemo.service1.domain.SecurityCheck;
import net.steampunkfoundry.techdemo.service1.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CaseRepositoryTest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), "hal+json");
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private CaseRepository caseRepository;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        caseRepository.deleteAll();
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void getAllCaseDetails() throws Exception {
        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        Correspondence c = new Correspondence();
        c.setDocType("FeeReceipt");
        c.setDocumentKey("ABC1234");
        c.setCreatedBy("Test");
        c.setCreatedDate(LocalDateTime.now());
        c.setCaseId(kase.getId());
        kase.getCorrespondences().add(c);

        SecurityCheck s = new SecurityCheck();
        s.setSecurityCheckType(A_NUMBER);
        s.setSecurityCheckStatus(UNFAVORABLE);
        s.setCreatedBy("Test");
        s.setCreatedDate(LocalDateTime.now());
        s.setCaseId(kase.getId());
        kase.getSecurityChecks().add(s);

        caseRepository.save(kase);

        MvcResult result = this.mockMvc.perform(get("/api/data/cases/")).andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$._embedded.cases[0].correspondences[0].docType",
                        is(kase.getCorrespondences().iterator().next().getDocType())))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void createCase() throws Exception {
        final Case kase = TestUtils.buildCase();

        final String p = json(kase);

        this.mockMvc.perform(post("/api/data/cases/").contentType(contentType).content(p))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void searchSingleCase() throws Exception {
        caseRepository.deleteAll();

        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        this.mockMvc.perform(get("/api/data/cases/" + kase.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(contentType));

    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void updateCase() throws Exception {
        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        kase.setStatus(Case.Status.REJECTED);

        final String json = json(kase);

        this.mockMvc.perform(put("/api/data/cases/" + kase.getId()).contentType(contentType).content(json))
                .andExpect(status().isNoContent());

        Case updatedCase = caseRepository.findById(kase.getId()).get();
        assertEquals(Case.Status.REJECTED, updatedCase.getStatus());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void deleteCase() throws Exception {
        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        this.mockMvc.perform(delete("/api/data/cases/" + kase.getId())).andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void findByStatus() throws Exception {
        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        mockMvc.perform(get("/api/data/cases/search/findByStatus").param("status", "ACCEPTING"))
                .andExpect(status().isOk()).andExpect(content().contentType(contentType));
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void findByAdjudicatorId() throws Exception {
        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        mockMvc.perform(get("/api/data/cases/search/findByAdjudicatorId").param("adjudicatorId", "1234"))
                .andExpect(status().isOk()).andExpect(content().contentType(contentType));
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void findByAdjudicatorIdAndStatus() throws Exception {
        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        mockMvc.perform(get("/api/data/cases/search/findByAdjudicatorIdAndStatus").param("adjudicatorId", "1234")
                .param("status", "ACCEPTING")).andExpect(status().isOk()).andExpect(content().contentType(contentType));
    }

    @Test
    @WithMockUser(authorities = "Adjudicator")
    public void findByCaseNumber() throws Exception {
        Case kase = TestUtils.buildCase();
        caseRepository.save(kase);

        mockMvc.perform(get("/api/data/cases/search/findByCaseNumber").param("caseNumber", "2021ABC456"))
                .andExpect(status().isOk()).andExpect(content().contentType(contentType));
    }

    private String json(final Object o) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);

        // Object to JSON in String
        return mapper.writeValueAsString(o);
    }

}
