package net.steampunkfoundry.techdemo.service1.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RevisionEntity {

    private final Integer revisionNumber;
    private final String revisedBy;
    private final LocalDateTime revisedDate;

}
