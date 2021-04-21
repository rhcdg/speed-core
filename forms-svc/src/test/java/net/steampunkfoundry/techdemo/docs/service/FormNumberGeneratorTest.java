package net.steampunkfoundry.techdemo.docs.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import net.steampunkfoundry.techdemo.docs.domain.DatabaseSequence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FormNumberGeneratorTest {
    @Autowired
    private FormNumberGenerator formNumberGenerator;

    @Test
    public void testCreateSequence() throws Exception {

        String expectedResultPrefix = LocalDateTime.now().getYear() + "_" + FormNumberGenerator.FORM_TYPE + "_";

        DatabaseSequence counter = new DatabaseSequence();
        counter.setSeq(1L);

        String result = formNumberGenerator.generateSequence("test");
        assertNotNull(result);
        assertEquals(expectedResultPrefix, result.substring(0, expectedResultPrefix.length()));
    }
}
