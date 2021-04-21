package net.steampunkfoundry.techdemo.service1.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import net.steampunkfoundry.techdemo.service1.repository.CaseRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CaseNumberGenerator {

    private final CaseRepository repository;

    public String generateSequence() {

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDateTime = currentDateTime.format(formatter);
        Long id = repository.getCaseNumberId();
        return formattedDateTime + String.format("%05d", id);
    }

}