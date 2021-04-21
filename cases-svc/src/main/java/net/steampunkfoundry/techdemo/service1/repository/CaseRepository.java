package net.steampunkfoundry.techdemo.service1.repository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import net.steampunkfoundry.techdemo.service1.domain.Case;
import net.steampunkfoundry.techdemo.service1.domain.Case.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@Api
@RepositoryRestResource
public interface CaseRepository extends PagingAndSortingRepository<Case, Long> {

    @PreAuthorize("hasAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Status", notes = "This method returns a list of all cases for a given status.")
    List<Case> findByStatus(
            @ApiParam(
                    name = "status",
                    type = "String",
                    value = "Case current status")
                    Status status);

    @PreAuthorize("hasAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Adjudicator", notes = "This method returns a list of all cases for a given adjudicator.")
    List<Case> findByAdjudicatorId(
            @ApiParam(
                    name = "adjudicatorId",
                    type = "String",
                    value = "Id of the adjudicator assigned to the case")
                    String adjudicatorId);

    @PreAuthorize("hasAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Applicant", notes = "This method returns a list of all cases for a given applicant.")
    List<Case> findByApplicantId(
            @ApiParam(
                    name = "applicantId",
                    type = "String",
                    value = "Id of the case applicant")
                    String applicantId);

    @PreAuthorize("hasAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Adjudicator and Status",
            notes = "This method returns a list of all cases for a given adjudicator and status.")
    List<Case> findByAdjudicatorIdAndStatus(
            @ApiParam(
                    name = "adjudicatorId",
                    type = "String",
                    value = "Id of the adjudicator assigned to the case")
                    String adjudicatorId,
            @ApiParam(
                    name = "status",
                    type = "String",
                    value = "Case current status")
                    Status status);

    @PreAuthorize("hasAuthority('Adjudicator')")
    @ApiOperation(value = "Find By Case Number",
            notes = "This method returns a list of all cases for a given case number.")
    List<Case> findByCaseNumber(
            @ApiParam(
                    name = "caseNumber",
                    type = "String",
                    value = "Case number assigned at case creation")
                    String caseNumber);

    @Query(value = "SELECT nextval('case_num_seq')", nativeQuery = true)
    @PreAuthorize("hasAnyAuthority('Applicant', 'Adjudicator')")
    @ApiOperation(value = "Generate an id value", hidden = true)
    Long getCaseNumberId();

}