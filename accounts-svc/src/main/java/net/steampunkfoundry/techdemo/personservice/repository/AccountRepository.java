package net.steampunkfoundry.techdemo.personservice.repository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Optional;
import net.steampunkfoundry.techdemo.personservice.domain.Account;
import net.steampunkfoundry.techdemo.personservice.domain.InlineRole;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Api(tags = "Accounts", hidden = true)
@RepositoryRestResource(collectionResourceRel = "accounts", path = "accounts", excerptProjection = InlineRole.class)
public interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

    /**
     * Exposes API /api/data/accounts/search/findByUsernameUuidIgnoreCase?usernameUuid={value}
     *
     * @param usernameUuid usernameUuid to search for
     * @return
     */
    Optional<Account> findByUsernameUuidIgnoreCase(@Param("usernameUuid") String usernameUuid);
}
