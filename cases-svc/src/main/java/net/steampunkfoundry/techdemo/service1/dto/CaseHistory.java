package net.steampunkfoundry.techdemo.service1.dto;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@RequiredArgsConstructor
public class CaseHistory {

    @EqualsAndHashCode.Include
    private final String caseNumber;
    @EqualsAndHashCode.Include
    private final String event;
    private final String dateStarted;
    private final String time;
    private final String adjudicator;
    private final int revNumber;
    private final LocalDateTime modDate;
    @Setter
    private String adjudicatorName;

}
