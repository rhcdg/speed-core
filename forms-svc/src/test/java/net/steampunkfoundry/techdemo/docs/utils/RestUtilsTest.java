package net.steampunkfoundry.techdemo.docs.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import net.steampunkfoundry.techdemo.docs.Application;
import net.steampunkfoundry.techdemo.docs.config.security.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = SecurityConfig.class)
public class RestUtilsTest {

    @Autowired
    RestUtils utils;

    @Test
    public void testCreateHeadersWithToken() {
        String tokenString = "ABC";
        HttpEntity<?> entity = utils.createHeadersWithToken(tokenString);
        assertEquals(Arrays.asList(tokenString), entity.getHeaders().get("Authorization"));
    }

    @Test
    public void testExtractToken() {
        String tokenString = "ABC";

        Map<String, Object> map = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map2.put("tokenValue", tokenString);
        map.put("token", map2);

        ObjectMapper mapper = mock(ObjectMapper.class);
        when(mapper.convertValue(null, Map.class)).thenReturn(map);
        RestUtils restUtils = new RestUtils(mapper);

        assertEquals(tokenString, restUtils.extractToken(null));
    }

}
