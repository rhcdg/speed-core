package net.steampunkfoundry.techdemo.docs.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import net.steampunkfoundry.techdemo.docs.exception.CaseSubmissionFailedException;
import net.steampunkfoundry.techdemo.docs.service.CaseClient;
import net.steampunkfoundry.techdemo.docs.service.CaseClient.CaseResponse;
import net.steampunkfoundry.techdemo.docs.service.FormService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "I-131 Forms")
@ControllerAdvice
@RestController
@RequiredArgsConstructor
public class FormsController {

    private final FormService service;
    private final CaseClient client;

    @ApiOperation(
            value = "Create Form",
            notes = "This method creates an I-131 Form. Fields map to the paper form.")
    @PostMapping(value = "/api/form")
    public @ResponseBody
    Form createForm(@ApiParam(
            name = "form",
            type = "Form object",
            value = "Required fields are aNumber (Alien Number), Last Name, Application Type, address, city, state, zipCode, country, countryOfBirth, visaClass, dateOfBirth and gender. All other fields optional.")
                    @RequestBody @Valid Form form) throws MissingServletRequestParameterException {

        if (form == null) {
            throw new MissingServletRequestParameterException(Form.class.getSimpleName(), "form");
        }

        return service.save(form);
    }

    /* This is the final step in creating a Form. It submits the form to the Case Service */
    @ApiOperation(
            value = "Submit Final Form",
            notes = "This method submits the finished, final form. A case is created in the system for the applicant and this form.")
    @PutMapping(value = "/api/form/submit")
    public @ResponseBody
    Form submitFormToAgency(@ApiParam(
            name = "form",
            type = "Form object",
            value = "Required fields are aNumber (Alien Number), Last Name, Application Type, address, city, state, zipCode, country, countryOfBirth, visaClass, dateOfBirth and gender. All other fields optional.")
                            @RequestBody Form form) throws MissingServletRequestParameterException, CaseSubmissionFailedException, JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (form == null) {
            throw new MissingServletRequestParameterException(Form.class.getSimpleName(), "form");
        }

        CaseResponse response = client.submitForm(auth, form);

        if (response.isSuccess()) {
            return service.finalizeSubmission(form, response);
        }

        throw new CaseSubmissionFailedException(form.getApplicantId(), response);
    }

}
