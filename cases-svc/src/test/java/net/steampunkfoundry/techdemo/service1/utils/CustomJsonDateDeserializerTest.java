package net.steampunkfoundry.techdemo.service1.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import net.steampunkfoundry.techdemo.service1.CaseApplication;
import net.steampunkfoundry.techdemo.service1.config.security.SecurityConfig;
import net.steampunkfoundry.techdemo.service1.dto.Form;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CaseApplication.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = SecurityConfig.class)
public class CustomJsonDateDeserializerTest {

    @Test
    public void testDeserialize_Format1() throws IOException {
        Form actual = new ObjectMapper().readValue("{\"submissionDate\":" + "\"2015-10-07\"" + "}", Form.class);
        assertNotNull(actual.getSubmissionDate());
    }

    @Test
    public void testDeserialize_Format2() throws IOException {
        Form actual = new ObjectMapper().readValue("{\"submissionDate\":" + "\"10/07/2015\"" + "}", Form.class);
        assertNotNull(actual.getSubmissionDate());
    }
}
