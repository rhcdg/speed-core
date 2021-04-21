package net.steampunkfoundry.techdemo.service1.pdf;

public class ApprovalNoticeData extends ReceiptData {
    private String validFrom;
    private String validTo;
    private String consulate;

    public String getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }

    public String getValidTo() {
        return validTo;
    }

    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }

    public String getConsulate() {
        return consulate;
    }

    public void setConsulate(String consulate) {
        this.consulate = consulate;
    }
}
