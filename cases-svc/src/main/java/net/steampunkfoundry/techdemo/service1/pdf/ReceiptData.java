package net.steampunkfoundry.techdemo.service1.pdf;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReceiptData {
    private String receiptNumber;
    private String uscisOnlineAccountNumber;
    private String caseType;
    private String receivedDate;
    private String priorityDate;
    private String applicant;
    private String noticeDate;
    private String noticeType;
    private String address;

}
