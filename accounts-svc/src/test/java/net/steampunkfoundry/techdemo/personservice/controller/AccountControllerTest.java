package net.steampunkfoundry.techdemo.personservice.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import javax.transaction.Transactional;
import net.steampunkfoundry.techdemo.personservice.domain.Account;
import net.steampunkfoundry.techdemo.personservice.dto.AccountDto;
import net.steampunkfoundry.techdemo.personservice.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AccountControllerTest {

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), "hal+json",
            StandardCharsets.UTF_8);
    @Autowired
    AccountController accountController;
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private AccountRepository repo;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCLogin() throws Exception {
        this.mockMvc.perform(
                get("/api/data/accounts/"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"Adjudicator"})
    @Test
    public void testFindByUsernameUuidIgnoreCase() throws Exception {
        this.mockMvc.perform(
                get("/api/data/accounts/search/findByUsernameUuidIgnoreCase?usernameUuid=198ab2cb-43a6-4010-ad05-a948c09bb68b")
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"Adjudicator"})
    @Test
    public void testGetAdjudicators() throws Exception {
        this.mockMvc.perform(
                get("/api/accounts/"))
                .andExpect(status().isOk());
    }

    @WithMockUser(authorities = {"Adjudicator"})
    @Test
    public void testGetAdjudicators_NameTest() throws Exception {
        Account p = new Account();
        p.setUsernameUuid("testuser1");
        p.setUsername("1231");
        p.setEmail("test1.part1@test.com");

        Account p2 = new Account();
        p2.setFirstName("first2");
        p2.setUsernameUuid("testuser2");
        p2.setUsername("1232");
        p2.setEmail("test.part2@test.com");

        Account p3 = new Account();
        p3.setLastName("last3");
        p3.setUsernameUuid("testuser3");
        p3.setUsername("1233");
        p3.setEmail("test.part3@test.com");

        Account p4 = new Account();
        p4.setUsernameUuid("testuser4");
        p4.setUsername("1234");
        p4.setEmail("testpart4@test.com");

        Account p5 = new Account();
        p5.setUsernameUuid("testuser5");
        p5.setUsername("1235");
        p5.setEmail("invalid email");

        Account p6 = new Account();
        p6.setUsernameUuid("testuser6");
        p6.setUsername("1236");

        List<Account> people = Arrays.asList(p, p2, p3, p4, p5, p6);

        repo.saveAll(people);
        List<AccountDto> list = accountController.getAccounts();
        assertTrue(list.size() > 5);
    }

    @WithMockUser(authorities = {"Adjudicator"})
    @Test
    public void testGetAdjudicator_ById_AdjudicatorRole() throws Exception {
        testGetAdjudicator_ById();
    }

    @WithMockUser(authorities = {"Applicant"})
    @Test
    public void testGetAdjudicator_ById_ApplicantRole() throws Exception {
        testGetAdjudicator_ById();
    }

    @WithMockUser(authorities = {"ReadOnly"})
    @Test
    public void testGetAdjudicator_ById_InvalidRole() throws Exception {
        assertThrows(AccessDeniedException.class, () -> {
            testGetAdjudicator_ById();
        });
    }

    @WithMockUser(authorities = {"Adjudicator"})
    @Test
    public void testGetAdjudicator_ById_DoesNotExist() throws Exception {
        assertThrows(ResourceNotFoundException.class, () -> {
            accountController.getAccount("notARealPerson");
        });
    }

    private void testGetAdjudicator_ById() throws Exception {
        Account p = new Account();
        String userId = "testuser123445";
        p.setUsernameUuid(userId);
        p.setUsername("1231");
        p.setEmail("test1.part1@test.com");

        repo.save(p);
        AccountDto accountDto = accountController.getAccount(userId);
        assertNotNull(accountDto);
    }

}
