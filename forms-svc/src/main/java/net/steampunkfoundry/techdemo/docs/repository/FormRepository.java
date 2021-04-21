package net.steampunkfoundry.techdemo.docs.repository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import net.steampunkfoundry.techdemo.docs.domain.Form;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@Api(tags = "More Forms")
@RepositoryRestResource(collectionResourceRel = "forms", path = "forms")
public interface FormRepository extends MongoRepository<Form, String> {

    @PreAuthorize("hasAnyAuthority('Applicant','Adjudicator')")
    @ApiOperation(
            value = "Returns a list of forms for the given a-number.",
            notes = "This method returns all Forms for an individual based on Alian Number (aNumber).")
    List<Form> findByAnumber(
            @ApiParam(
                    name = "anumber",
                    type = "Text",
                    value = "Applicant's A-Number (Alien Number).")
            @Param("anumber") String anumber);

    @ApiOperation(
            value = "Returns a paginated list of forms.",
            notes = "This method returns all forms in the system. Typically the fields (for paginating a response) can be ignored.")
    @PreAuthorize("hasAnyAuthority('Adjudicator')")
    Page<Form> findAll(Pageable pageable);

    @PreAuthorize("hasAnyAuthority('Adjudicator','Applicant')")
    @ApiOperation(
            value = "Returns a list of forms for an applicant.",
            notes = "This method returns all Forms for an individual based on Applicant's ID in the system. ID is typically not visible to users.")
    List<Form> findByApplicantId(
            @ApiParam(
                    name = "applicantId",
                    type = "Text",
                    value = "Applicant's system ID")
            @Param("applicantId") String applicantId);

    @PreAuthorize("hasAnyAuthority('Adjudicator','Applicant')")
    @ApiOperation(value = "Returns a form for a given formNumber.")
    Form findByFormNumber(@Param("formNumber") String formNumber);
}