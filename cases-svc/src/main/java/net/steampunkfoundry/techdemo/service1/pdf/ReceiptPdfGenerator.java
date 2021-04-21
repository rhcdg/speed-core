package net.steampunkfoundry.techdemo.service1.pdf;

import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceiptPdfGenerator extends FormPdfGenerator {
    private ReceiptData receiptData;

    public InputStream generateReceiptPdf(ReceiptData receiptData) {
        this.receiptData = receiptData;
        return this.generatePdf();
    }

    @Override
    protected TextRun[] getBodyText() {
        TextRun[] bodyText = { new TextRun("Receipt Notice", BOLD_BODY_FONT, BODY_FONT_SIZE, true, false, 0, null),
                new TextRun(
                        " - This notice confirms that USCIS received your application or petition (\"this case\") as shown above.",
                        BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun(
                        "If any of the above information is incorrect, please immediately call 800-375-5283 to let us know.",
                        BOLD_BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun("This will help avoid future problems.\n", BODY_FONT, BODY_FONT_SIZE, false, false, 0,
                        null),
                new TextRun(
                        "This notice does not grant any immigration status or benefit, nor is it evidence that this case is still pending. It only shows that the application or petition was filed on the date shown.\n",
                        BODY_FONT, BODY_FONT_SIZE, true, false, 0, null),
                new TextRun("Processing Time", BOLD_BODY_FONT, BODY_FONT_SIZE, true, false, 0, null),
                new TextRun(
                        " - Processing times vary by case type. You can check our website at www.uscis.gov for our current \"processing times\" for this case type at the particular office to which this case is or becomes assigned. On our website’s \"case status online\" page, you can also view status or sign up to receive free e-mail updates as we complete key processing steps on this case. During most of the time this case is pending, however, our systems will show only that the case has been received, and the processing status will not have changed, because we will be working on other cases that were filed earlier than this one. We will notify you by mail, and show in our systems, when we make a decision on this case or if we need something from you. If you do not receive an initial decision or update from us within our current processing time, check our website or call 800-375-5283. Please save this notice, and any other notice we send you about this case, and please make and keep a copy of any papers you send us by any means, along with any proof of delivery to us.Please have all these papers with you if you contact us about this case.\n",
                        BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun("If this case is an I-130 Petition", BOLD_BODY_FONT, BODY_FONT_SIZE, true, false, 0, null),
                new TextRun(
                        " - Filing and approval of a Form I-130, Petition for Alien Relative, is only the first step in helping a relative immigrate to the United States. The beneficiaries of a petition must wait until a visa number is available before they can take the next step to apply for an immigrant visa or adjustment of status to lawful permanent residence. To best allocate resources, USCIS may wait to process I-130 forms until closer to the time whena visa number will become available, which may be years after the petition was filed. Nevertheless, USCIS processes I-130 forms in time not to delay relatives’ ability to take the next step toward permanent residence once a visa number does become available. If, before final action on the petition, you decide to withdraw your petition, your family relationship with the beneficiary ends, or you become a U.S. citizen, call 800-375-5283.\n",
                        BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun("Applications requiring biometrics", BOLD_BODY_FONT, BODY_FONT_SIZE, true, false, 0, null),
                new TextRun(
                        " - In some types of cases USCIS requires biometrics. In such cases, USCIS will send you a SEPARATE appointment notice with a specific date, time and place for you to go to a USCIS Application Support Center (ASC) for biometrics processing. You must WAIT for that separate appointment notice and take it (NOT this receipt notice) to your ASC appointment along with your photo identification. Acceptable kinds of photo identification are: a passport or national photo identification issued by your country, a drivers license, a military photo identification, or a state-issued photo identification card. If you receive more than one ASC appointment notice, even for different cases, take them both to the first appointment.\n",
                        BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun("If your address changes", BOLD_BODY_FONT, BODY_FONT_SIZE, true, false, 0, null),
                new TextRun(
                        " - If your mailing address changes while your case is pending, call 800-375-5283 or use the \"Online Change of Address\" function on our website. Otherwise, you might not receive notice of our action on this case.\n",
                        BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun(
                        "NOTICE: Pursuant to the terms of the United States Immigration & Nationality Act (INA), the information provided on and in support of applications and petitions is submitted under penalty of perjury. USCIS and the U.S. Department of Homeland Security reserve the right to verify this information before and/or after adjudication to ensure conformity with applicable laws, rules, regulations, and other authorities. Methods used for verifying information may include, but are not limited to, the review of public information and records, contact by correspondence, the internet, or telephone, and site inspections of businesses and residences. Information obtained during the course of verification will be used to determine eligibility for the benefit sought. Applicants, petitioners, and representatives of record will be provided an opportunity to address derogatory information before any formal decision is made and/or proceeding is initiated.",
                        BODY_FONT, BODY_FONT_SIZE, true, false, 0, null) };

        return bodyText;
    }

    @Override
    protected TextRun[] getAddressAndContactInfo() {
        TextRun[] result = {
                new TextRun(
                        new StringBuilder().append("USCIS/National Benefits Center\n").append("P.O. Box 25920\n")
                                .append("Overland Park KS 66225\n\n").toString(),
                        BODY_FONT, BODY_FONT_SIZE * 1.1f, false, false, 0, null),
                new TextRun("Customer Service Telephone: 800-375-5283", BOLD_BODY_FONT, BODY_FONT_SIZE, false, false, 0,
                        null) };

        return result;
    }

    @Override
    protected TextRun[] getNoticeType() {
        TextRun[] result = { new TextRun("Notice Type:  ", HEADER_FONT, BODY_FONT_SIZE * 1.2f, false, false, 0, null),
                new TextRun(
                        new StringBuilder()
                                .append(receiptData != null && receiptData.getNoticeType() != null
                                        ? receiptData.getNoticeType()
                                        : "")
                                .append("\n").append("Amount received: $1000.50 U.S.\n")
                                .append("Section: Alien of Extraordinary Ability,\n").append("Sec.203(b)").toString(),
                        BODY_FONT, BODY_FONT_SIZE * 1.2f, false, false, 0, null), };

        return result;
    }

    @Override
    protected String getReceiptNumber() {
        return receiptData != null ? receiptData.getReceiptNumber() : null;
    }

    @Override
    protected String getUscisOnlineAccountNumber() {
        return receiptData != null ? receiptData.getUscisOnlineAccountNumber() : null;
    }

    @Override
    protected String getCaseType() {
        return receiptData != null ? receiptData.getCaseType() : null;
    }

    @Override
    protected String getReceiptDate() {
        return receiptData != null ? receiptData.getReceivedDate() : null;
    }

    @Override
    protected String getPriorityDate() {
        return receiptData != null ? receiptData.getPriorityDate() : null;
    }

    @Override
    protected String getApplicant() {
        return receiptData != null ? receiptData.getApplicant() : null;
    }

    @Override
    protected String getAddress() {
        return receiptData != null ? receiptData.getAddress() : null;
    }

    @Override
    protected String getNoticeDate() {
        return receiptData != null ? receiptData.getNoticeDate() : null;
    }
}
