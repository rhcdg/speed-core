package net.steampunkfoundry.techdemo.service1.pdf;

import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApprovalNoticePdfGenerator extends FormPdfGenerator {
    private ApprovalNoticeData approvalNoticeData;

    public InputStream generateApprovalNoticePdf(ApprovalNoticeData approvalNoticeData) {
        this.approvalNoticeData = approvalNoticeData;
        return super.generatePdf();
    }

    @Override
    protected TextRun[] getBodyText() {

        return new TextRun[] {
                new TextRunBuilder().setFont(BOLD_BODY_FONT).setFontSize(BODY_FONT_SIZE)
                        .setText("We have approved your application for an Advanced Parole Document.  ").build(),
                new TextRunBuilder().setFont(BODY_FONT).setFontSize(BODY_FONT_SIZE).setText(
                        "Your travel document will be mailed to you separately and will show the validity of the document and any travel conditions.\n")
                        .build(),
                new TextRunBuilder().setFont(BOLD_BODY_FONT).setFontSize(BODY_FONT_SIZE).setPrependParagraphBreak(true)
                        .setUnderline(true).setText("How to Use Your Advance Parole Document\n").build(),
                new TextRunBuilder().setFont(BODY_FONT).setFontSize(BODY_FONT_SIZE).setPrependParagraphBreak(true)
                        .setText(
                                "You should take your passport and the Advance Parole Document with you when you leave the U.S. You cannot use the Advance Parole Document in place of your passport. When you return to the U.S., present the Advance Parole Document to the U.S. Customs and Border Protection (CBP) officer who inspects you at the port-of-entry.\n")
                        .build(),
                new TextRunBuilder().setFont(BOLD_BODY_FONT).setFontSize(BODY_FONT_SIZE).setPrependParagraphBreak(true)
                        .setUnderline(true).setText("Before You Leave the United States\n").build(),
                new TextRunBuilder().setFont(BODY_FONT).setFontSize(BODY_FONT_SIZE).setPrependParagraphBreak(true)
                        .setText("Please note the following information:\n").build(),
                new TextRunBuilder().setFont(BODY_FONT).setFontSize(BODY_FONT_SIZE).setPrependParagraphBreak(true)
                        .setIndent(25f).setBullet('•')
                        .setText(
                                "Parole into the United States is not guaranteed. In all cases, a CBP officer must still inspect you at a port of entry to determine whether you are eligible to come into the United States according to the terms of this advance parole. Even though USCIS approved your advance parole, CBP may still refuse to parole you into the United States.\n")
                        .build(),
                new TextRunBuilder().setFont(BODY_FONT).setFontSize(BODY_FONT_SIZE).setIndent(25f).setBullet('•')
                        .setText(
                                "Parole into the United States is not an “admission” into the U.S. If you have a pending Form I-485 and we deny it, you may be subject to removal proceedings for being inadmissible to the U.S.\n")
                        .build(),
                new TextRunBuilder().setFont(BODY_FONT).setFontSize(BODY_FONT_SIZE).setIndent(25f).setBullet('•')
                        .setText(
                                "Unlawful Presence. If you leave the United States after being unlawfully present in the United States, you may be barred from admission even if you obtained advance parole. If you were unlawfully present in the United States for more than 180 days but less than one year and you leave the U.S. voluntarily before the start of removal proceedings, you are inadmissible for three years; if you were unlawfully present for one year or more, you are inadmissible for ten years.\n")
                        .build(),
                new TextRunBuilder().setFont(BOLD_BODY_FONT).setFontSize(BODY_FONT_SIZE)
                        .setText("THIS FORM IS NOT A VISA AND MAY NOT BE USED IN PLACE OF A VISA.\n").build(),
                new TextRunBuilder().setFont(BOLD_BODY_FONT).setFontSize(BODY_FONT_SIZE).setPrependParagraphBreak(true)
                        .setText("NOTICE").build(),
                new TextRunBuilder().setFont(BODY_FONT).setFontSize(BODY_FONT_SIZE).setText(
                        ": Although this application or petition has been approved, USCIS and the U.S. Department of Homeland Security reserve the right to verify this information before and/or after making a decision on your case so we can ensure that you have complied with applicable laws, rules, regulations, and other legal authorities. We may review public information and records, contact others by mail, the internet or phone, conduct site inspections of businesses and residences, or use other methods of verification. We will use the information obtained to determine whether you are eligible for the benefit you seek. If we find any derogatory information, we will follow the law in determining whether to provide you (and the legal representative listed on your Form G-28, if you submitted one) an opportunity to address that information before we make a formal decision on your case or start proceedings.\n")
                        .build(), };
    }

    @Override
    protected TextRun[] getAddressAndContactInfo() {

        return new TextRun[] { new TextRun(
                new StringBuilder().append("Vermont Service Center\n").append("U. S. CITIZENSHIP & IMMIGRATION SVC\n")
                        .append("75 Lower Welden Street\n").append("Saint Albans VT 05479-0001\n\n").toString(),
                BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun("USCIS Contact center:  www.uscis.gov/contactcenter", BOLD_BODY_FONT, BODY_FONT_SIZE, false,
                        false, 0, null) };
    }

    @Override
    protected TextRun[] getNoticeType() {

        return new TextRun[] { new TextRun("Notice Type", BOLD_BODY_FONT, BODY_FONT_SIZE, false, false, 0, null),
                new TextRun(new StringBuilder().append(": Approval Notice\n").append("Valid from ")
                        .append(approvalNoticeData != null && approvalNoticeData.getValidFrom() != null
                                ? approvalNoticeData.getValidFrom()
                                : "")
                        .append(" to ")
                        .append(approvalNoticeData != null && approvalNoticeData.getValidTo() != null
                                ? approvalNoticeData.getValidTo()
                                : "")
                        .append("\n").append("Consulate: ")
                        .append(approvalNoticeData != null && approvalNoticeData.getConsulate() != null
                                ? approvalNoticeData.getConsulate()
                                : "")
                        .toString(), BODY_FONT, BODY_FONT_SIZE, false, false, 0, null) };
    }

    @Override
    protected String getReceiptNumber() {
        return approvalNoticeData != null ? approvalNoticeData.getReceiptNumber() : null;
    }

    @Override
    protected String getUscisOnlineAccountNumber() {
        return approvalNoticeData != null ? approvalNoticeData.getUscisOnlineAccountNumber() : null;
    }

    @Override
    protected String getCaseType() {
        return approvalNoticeData != null ? approvalNoticeData.getCaseType() : null;
    }

    @Override
    protected String getReceiptDate() {
        return approvalNoticeData != null ? approvalNoticeData.getReceivedDate() : null;
    }

    @Override
    protected String getPriorityDate() {
        return approvalNoticeData != null ? approvalNoticeData.getPriorityDate() : null;
    }

    @Override
    protected String getApplicant() {
        return approvalNoticeData != null ? approvalNoticeData.getApplicant() : null;
    }

    @Override
    protected String getAddress() {
        return approvalNoticeData != null ? approvalNoticeData.getAddress() : null;
    }

    @Override
    protected String getNoticeDate() {
        return approvalNoticeData != null ? approvalNoticeData.getNoticeDate() : null;
    }
}
