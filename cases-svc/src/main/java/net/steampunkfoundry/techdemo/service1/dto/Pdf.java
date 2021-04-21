package net.steampunkfoundry.techdemo.service1.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Builder
public class Pdf {

    private String aNumber;
    private String caseNumber;
    private byte[] pdfContent;
    private String pdfDescription;
}
