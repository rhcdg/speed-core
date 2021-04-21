package net.steampunkfoundry.techdemo.docs.service;

import io.micrometer.core.instrument.util.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.domain.Form.Status;
import net.steampunkfoundry.techdemo.docs.repository.FormRepository;
import net.steampunkfoundry.techdemo.docs.service.CaseClient.CaseResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormService {

    private static final int A_NUMBER_LENGTH = 10;
    private final FormRepository repo;
    private final FormNumberGenerator sequenceGenerator;

    @PreAuthorize("hasAnyAuthority('Applicant')")
    public Form save(Form form) {

        form.setAnumber(padAnumber(form.getAnumber()));

        if (form.getFormNumber() == null) {
            form.setObjectId(null);
            form.setSubmissionStatus(Status.IN_PROGRESS);
            form.setFormNumber(sequenceGenerator.generateSequence(Form.SEQUENCE_NAME));
            form.getSupportDocument().stream().filter(t -> StringUtils.isEmpty(t.getDateSubmitted())).forEach(t -> t.setDateSubmitted(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))));
        }
        return repo.insert(form);
    }

    @PreAuthorize("hasAnyAuthority('Applicant')")
    public Form finalizeSubmission(Form form, CaseResponse response) {

        form.setSubmissionStatus(Status.SUBMITTED);
        form.setSubmissionDate(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        form.setCaseNumber(response.getCaseNumber());

        return repo.save(form);

    }

    private String padAnumber(String anumber) {
        int desiredLength = A_NUMBER_LENGTH - 1;

        String prefix = anumber.substring(0, 1);
        String number = anumber.substring(1);

        /* if the aNumber if less than 9 digits after the A, left pad it. */
        if (number.trim().length() < desiredLength) {
            return prefix + padLeftZeros(number, desiredLength);
        }
        return anumber;
    }

    private String padLeftZeros(String inputString, int length) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);

        return sb.toString();
    }
}
