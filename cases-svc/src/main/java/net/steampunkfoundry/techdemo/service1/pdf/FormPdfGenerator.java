package net.steampunkfoundry.techdemo.service1.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

@Slf4j
public abstract class FormPdfGenerator extends PdfGenerator {
    public static final float LINE_WIDTH = 0.9f;
    public static final PDFont HEADER_FONT = PDType1Font.TIMES_BOLD;
    public static final float HEADER_FONT_SIZE = 6f;
    public static final PDFont BODY_FONT = PDType1Font.TIMES_ROMAN;
    public static final PDFont BOLD_BODY_FONT = PDType1Font.TIMES_BOLD;
    public static final float BODY_FONT_SIZE = 8f;

    protected abstract TextRun[] getBodyText();

    protected abstract TextRun[] getAddressAndContactInfo();

    protected abstract TextRun[] getNoticeType();

    protected abstract String getReceiptNumber();

    protected abstract String getUscisOnlineAccountNumber();

    protected abstract String getCaseType();

    protected abstract String getReceiptDate();

    protected abstract String getPriorityDate();

    protected abstract String getApplicant();

    protected abstract String getAddress();

    protected abstract String getNoticeDate();

    public InputStream generatePdf() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
        PDDocument pdDocument = new PDDocument();

        try {
            PDPage page = new PDPage();

            try (PDPageContentStream contents = new PDPageContentStream(pdDocument, page)) {
                drawPageContents(pdDocument, contents);
            }

            pdDocument.addPage(page);

            pdDocument.setAllSecurityToBeRemoved(true);
            pdDocument.save(outputStream);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(pdDocument);
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }

    private void drawPageContents(PDDocument pdDocument, PDPageContentStream contents) throws IOException {
        // draw big box
        drawRect(contents, 50, 67, 560, 715, LINE_WIDTH);

        drawHeader(contents);
        drawBody(contents);
        drawFooter(pdDocument, contents);
    }

    private void drawHeader(PDPageContentStream contents) throws IOException {
        // draw first row
        drawRect(contents, 50, 695, 177, 715, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 52, 709, "Receipt Number");
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 55, 700, getReceiptNumber());

        drawRect(contents, 177, 695, 305, 715, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 179, 709, "USCIS Online Account Number");
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 184, 700, getUscisOnlineAccountNumber());

        drawRect(contents, 305, 695, 560, 715, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 307, 709, "Case Type");
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 312, 700, getCaseType());

        // draw second row
        drawRect(contents, 50, 665, 177, 695, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 52, 689f, "Receipt Date");
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 55, 680, getReceiptDate());

        drawRect(contents, 177, 665, 305, 695, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 179, 689f, "Priority Date");
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 184, 680, getPriorityDate());

        drawRect(contents, 305, 665, 560, 695, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 307, 689f, "Applicant");
        drawTextArea(contents, BODY_FONT, BODY_FONT_SIZE, 312, 665, 560, 685, 4, getApplicant());

        // draw third row
        drawRect(contents, 50, 645, 177, 665, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 52, 659, "Notice Date");
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 55, 649, getNoticeDate());

        drawRect(contents, 177, 645, 305, 665, LINE_WIDTH);
        drawLeftAlignedText(contents, HEADER_FONT, HEADER_FONT_SIZE, 179, 659, "Page");
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 184, 649, "1 of 1");

        drawRect(contents, 305, 645, 560, 665, LINE_WIDTH);

        // draw fourth row
        drawRect(contents, 50, 550, 360, 645, LINE_WIDTH);
        drawTextArea(contents, BODY_FONT, BODY_FONT_SIZE * 1.2f, 60, 550, 360, 630, 4, getAddress());

        drawRect(contents, 360, 550, 560, 645, LINE_WIDTH);
        drawTextArea(contents, 370, 550, 560, 630, 4, getNoticeType());
    }

    private void drawBody(PDPageContentStream contents) throws IOException {
        drawRect(contents, 50, 135, 560, 550, LINE_WIDTH);
        drawTextArea(contents, 55, 135, 555, 540, 4, getBodyText());
    }

    private void drawFooter(PDDocument currentDocument, PDPageContentStream contents) throws IOException {
        drawRect(contents, 50, 125, 560, 135, LINE_WIDTH);
        drawLeftAlignedText(contents, BODY_FONT, BODY_FONT_SIZE, 55, 127,
                "Please see the additional information on the back. You will be notified separately about any other cases you filed.");

        drawTextArea(contents, 55, 67, 350, 120, 4, getAddressAndContactInfo());

        String receiptNumber = getReceiptNumber();
        byte[] barcode = ImageEncoder.encodeAsBarcode(receiptNumber, 150, 45);
        if (barcode != null) {
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(currentDocument, barcode, receiptNumber);
            contents.drawImage(pdImage, 350, 75, 150, 45);
        }
    }

}
