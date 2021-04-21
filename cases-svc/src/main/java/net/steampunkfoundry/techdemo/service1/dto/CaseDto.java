package net.steampunkfoundry.techdemo.service1.dto;

import lombok.Getter;
import lombok.Setter;
import net.steampunkfoundry.techdemo.service1.domain.Case;

@Getter
@Setter
public class CaseDto extends Case {

    private String adjudicatorName;
    private Form form;
}
