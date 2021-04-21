package net.steampunkfoundry.techdemo.personservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import net.steampunkfoundry.techdemo.personservice.dto.AccountDto;
import net.steampunkfoundry.techdemo.personservice.repository.AccountRepository;
import net.steampunkfoundry.techdemo.personservice.utils.AccountUtils;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Accounts")
@ControllerAdvice
@RestController
public class AccountController extends BaseController {

    @Autowired
    private AccountRepository repo;

    @ApiOperation(
            value = "Get Accounts",
            notes = "This method returns account information (name, email, id) for all users.")
    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    @GetMapping(value = "/accounts")
    public @ResponseBody
    List<AccountDto> getAccounts() {
        return StreamSupport.stream(repo.findAll().spliterator(), false).map(AccountUtils::convertAccountToDto).collect(Collectors.toList());
    }

    @ApiOperation(
            value = "Get Account",
            notes = "This method returns account information (name, email, id) for a single user.")
    @PreAuthorize("hasAnyAuthority('Adjudicator','Applicant')")
    @GetMapping(value = "/account")
    public @ResponseBody
    AccountDto getAccount(
            @ApiParam(
                    name = "validatorUserId",
                    type = "Text",
                    value = "The ID of a user (Adjudicator or Applicant), typically obtained from a user's authentication token")
            @RequestParam String validatorUserId) {
        Optional<AccountDto> result = repo.findByUsernameUuidIgnoreCase(Encode.forHtml(validatorUserId)).map(AccountUtils::convertAccountToDto);
        if (result.isPresent()) {
            return result.get();
        }
        throw new ResourceNotFoundException();
    }

}
