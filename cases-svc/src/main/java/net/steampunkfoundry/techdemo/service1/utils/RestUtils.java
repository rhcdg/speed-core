package net.steampunkfoundry.techdemo.service1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestUtils {

    private final ObjectMapper mapper;

    public HttpEntity<?> createHeadersWithToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        return new HttpEntity<>(headers);
    }

    public String extractToken(Authentication auth) {
        Map<String, Object> map = mapper.convertValue(auth, Map.class);
        return (String) ((Map) map.get("token")).get("tokenValue");
    }

}
