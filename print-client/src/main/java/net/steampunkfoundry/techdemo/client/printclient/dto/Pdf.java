package net.steampunkfoundry.techdemo.client.printclient.dto;

import lombok.EqualsAndHashCode;

/**
 * Holds PDF info that comes back from the case service.  Even though we're using Lombok we needed
 * to explicitly write the getters here because the JavaFX PropertyValueFactory doesn't play well
 * with Lombok.
 */
@EqualsAndHashCode
public class Pdf {

    private String anumber;
    private String caseNumber;
    private byte[] pdfContent;
    private String pdfDescription;

    /**
     * return alien number
     *
     * @return alien number
     */
    public String getAnumber() {
        return anumber;
    }

    /**
     * return case number
     *
     * @return case number
     */
    public String getCaseNumber() {
        return caseNumber;
    }

    /**
     * return pdf content
     *
     * @return pdf content
     */
    public byte[] getPdfContent() {
        return pdfContent;
    }

    /**
     * return pdf description
     *
     * @return pdf description
     */
    public String getPdfDescription() {
        return pdfDescription;
    }
}
