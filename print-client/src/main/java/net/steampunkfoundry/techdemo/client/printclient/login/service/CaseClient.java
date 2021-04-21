package net.steampunkfoundry.techdemo.client.printclient.login.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.steampunkfoundry.techdemo.client.printclient.dto.Pdf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * CaseClient is used to make a rest call to the case service and retrieve the list of PDFs that are
 * ready to print from the case service.
 */
@Service
public class CaseClient {

    private final RestTemplate restTemplate;

    @Value("${case.getPdfs.endpoint}")
    private String getPdfApi;

    public CaseClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    /**
     * @return set of PDFS that are available to print from the Case Service
     */
    public List<Pdf> getPdfs() {
        HttpHeaders headers = new HttpHeaders();
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder
                .getContext().getAuthentication();
        headers.set("Authorization",
                "Bearer " + authentication.getToken().getTokenValue());
        Logger.getLogger(CaseClient.class.getName())
                .log(Level.INFO, "jwt token: " + authentication.getToken().getTokenValue());
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getPdfApi);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Set<Pdf>> responseEntity =
                restTemplate
                        .exchange(
                                getPdfApi,
                                HttpMethod.GET,
                                entity,
                                new ParameterizedTypeReference<Set<Pdf>>() {
                                }
                        );
        List<Pdf> pdfs = new ArrayList<Pdf>();
        pdfs.addAll(responseEntity.getBody());
        pdfs.sort(Comparator.comparing(Pdf::getCaseNumber).thenComparing(Pdf::getAnumber)
                .thenComparing(Pdf::getPdfDescription));
        return pdfs;
    }

}
