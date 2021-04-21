package net.steampunkfoundry.techdemo.service1.pdf;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFontFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.mock.mockito.MockBean;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class ReceiptPdfGeneratorTest {
    @MockBean
    PDPageContentStream contents;

    ReceiptPdfGenerator pdfGenerator = new ReceiptPdfGenerator();

    @Test
    public void testReceiptGenerator() {
        ReceiptData testData = new ReceiptData();
        testData.setReceiptNumber("A123456");
        testData.setUscisOnlineAccountNumber("B987654");
        testData.setCaseType("I131 - APPLICATION FOR TRAVEL DOCUMENT");
        testData.setReceivedDate("03/31/2021");
        testData.setPriorityDate("03/14/2021");
        testData.setApplicant("Insert Anumber here *\nI131 Receipt Fee Acceptance");
        testData.setNoticeDate("03/01/2021");
        testData.setNoticeType("Receipt Notice");
        testData.setAddress("I131 Recpt Fee Accepted\nc/o Insert Here *\nInsert Here Address*");

        runReceiptPdfGenerator(testData, "test.pdf");
    }

    @Test
    public void testReceiptGenerator1() {
        ReceiptData testData = new ReceiptData();
        testData.setReceiptNumber("A123456");
        testData.setUscisOnlineAccountNumber("B987654");
        testData.setCaseType("I131 - APPLICATION FOR TRAVEL DOCUMENT");
        testData.setReceivedDate("03/31/2021");
        testData.setPriorityDate("03/14/2021");
        testData.setApplicant("Insert Anumber here *\\nI131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here"
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here " + "I131 Receipt Fee AcceptanceInsert Anumber here "
                + "I131 Receipt Fee AcceptanceInsert Anumber here ");
        testData.setNoticeDate("03/01/2021");
        testData.setNoticeType("Receipt Notice");
        testData.setAddress("I131 Recpt Fee Accepted\nc/o Insert Here *\nInsert Here Address*");

        runReceiptPdfGenerator(testData, "test.pdf");
    }

    @Test
    public void testReceiptGenerator2() {
        ReceiptData testData = new ReceiptData();
        testData.setReceiptNumber("A123456");
        testData.setUscisOnlineAccountNumber("B987654");
        testData.setCaseType("I131 - APPLICATION FOR TRAVEL DOCUMENT");
        testData.setReceivedDate("03/31/2021");
        testData.setPriorityDate("03/14/2021");
        testData.setApplicant("testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
        testData.setNoticeDate("03/01/2021");
        testData.setNoticeType("Receipt Notice");
        testData.setAddress("I131 Recpt Fee Accepted\nc/o Insert Here *\nInsert Here Address*");

        runReceiptPdfGenerator(testData, "test.pdf");
    }

    @Test
    public void testReceiptGenerator3() {
        ReceiptData testData = new ReceiptData();
        testData.setReceiptNumber("A123456");
        testData.setUscisOnlineAccountNumber("B987654");
        testData.setCaseType("I131 - APPLICATION FOR TRAVEL DOCUMENT");
        testData.setReceivedDate("03/31/2021");
        testData.setPriorityDate("03/14/2021");
        testData.setApplicant("testtes ttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest"
                + " testtesttesttesttesttesttesttesttesttesttesttest testtesttesttesttesttesttesttest testtesttesttes t");
        testData.setNoticeDate("03/01/2021");
        testData.setNoticeType("Receipt Notice");
        testData.setAddress("I131 Recpt Fee Accepted\nc/o Insert Here *\nInsert Here Address*");

        runReceiptPdfGenerator(testData, "test.pdf");
    }

    @Test
    public void nullTest() {
        ReceiptData testData = new ReceiptData();
        testData.setReceiptNumber(null);
        testData.setUscisOnlineAccountNumber(null);
        testData.setCaseType(null);
        testData.setReceivedDate(null);
        testData.setPriorityDate(null);
        testData.setApplicant(null);
        testData.setNoticeDate(null);
        testData.setNoticeType(null);
        testData.setAddress(null);

        runReceiptPdfGenerator(testData, "nulltest.pdf");
    }

    @Test
    public void drawCenteredText1() {
        try {
            contents = new PDPageContentStream(new PDDocument(), new PDPage());
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), Color.black, 4f,
                    "test");
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), Color.black, 4f,
                    "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), Color.black, 4f,
                    null);
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), Color.black, 4f, "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void drawCenteredText2() {
        try {
            contents = new PDPageContentStream(new PDDocument(), new PDPage());
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), 4f, "test");
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), 4f,
                    "testtesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttesttest");
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), 4f, null);
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), 4f, "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void drawRightAlignedText() {
        try {
            contents = new PDPageContentStream(new PDDocument(), new PDPage());
            pdfGenerator.drawRightAlignedText(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, "test");
            pdfGenerator.drawRightAlignedText(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, null);
            pdfGenerator.drawRightAlignedText(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void drawFilledRectTest() {
        try {
            contents = new PDPageContentStream(new PDDocument(), new PDPage());
            pdfGenerator.drawFilledRect(contents, 4f, 4f, 4f, 4f, Color.black, 4f);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void drawCenteredTextTest() {
        try {
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), Color.black, 4f,
                    "test");
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), Color.black, 4f, "");
            pdfGenerator.drawCenteredText(contents, 4f, 4f, 4f, PDFontFactory.createDefaultFont(), Color.black, 4f,
                    null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void getTextWidthTest() {

        try {
            pdfGenerator.getTextWidth(new TextRun("test", PDFontFactory.createDefaultFont(), 8f, true, true, 4f,
                    Character.MAX_HIGH_SURROGATE));
            pdfGenerator.getTextWidth(new TextRun("", PDFontFactory.createDefaultFont(), 8f, true, true, 4f,
                    Character.MAX_HIGH_SURROGATE));
            pdfGenerator.getTextWidth(new TextRun("test", null, 8f, true, true, 4f, Character.MAX_HIGH_SURROGATE));
            pdfGenerator.getTextWidth(null);
            pdfGenerator.getTextWidth(PDFontFactory.createDefaultFont(), 4f, "test");
            pdfGenerator.getTextWidth(PDFontFactory.createDefaultFont(), 4f, null);
            pdfGenerator.getTextWidth(PDFontFactory.createDefaultFont(), 4f, "");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void drawLeftAlignedTextTest() {
        try {
            pdfGenerator.drawLeftAlignedText(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, null);
            pdfGenerator.drawLeftAlignedText(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, "");
            pdfGenerator.drawLeftAlignedText(contents, 4f, 4f, null);
            TextRun[] runs = new TextRun[0];
            pdfGenerator.drawLeftAlignedText(contents, 4f, 4f, runs);
            TextRun[] runs1 = new TextRun[1];
            TextRun run = new TextRun("", null, 8f, true, true, 4f, Character.MAX_HIGH_SURROGATE);
            runs1[0] = run;
            pdfGenerator.drawLeftAlignedText(contents, 4f, 4f, runs1);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void splitLinesTest() {
        try {
            pdfGenerator.splitLines(null, 4f);
            TextRun[] runs = new TextRun[0];
            pdfGenerator.splitLines(runs, 4f);
            TextRun[] runs1 = new TextRun[1];
            TextRun run = new TextRun("", null, 8f, true, true, 4f, Character.MAX_HIGH_SURROGATE);
            runs1[0] = run;
            pdfGenerator.splitLines(runs1, 4f);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void drawTextAreaTest() {
        try {
            pdfGenerator.drawTextArea(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, 4f, 4f, 4f, "test");
            pdfGenerator.drawTextArea(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, 4f, 4f, 4f, "");
            pdfGenerator.drawTextArea(contents, PDFontFactory.createDefaultFont(), 4f, 4f, 4f, 4f, 4f, 4f, null);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getTextAreaHeightTest() {
        try {
            pdfGenerator.getTextAreaHeight(PDFontFactory.createDefaultFont(), 4f, 4f, null);
            List<String> lines = new ArrayList<>();
            pdfGenerator.getTextAreaHeight(PDFontFactory.createDefaultFont(), 4f, 4f, lines);
            lines.add("asdfasadfasf");
            pdfGenerator.getTextAreaHeight(PDFontFactory.createDefaultFont(), 4f, 4f, lines);
            lines.add("");
            pdfGenerator.getTextAreaHeight(PDFontFactory.createDefaultFont(), 4f, 4f, lines);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void doesTextFitTest() {
        try {
            pdfGenerator.doesTextFit(PDFontFactory.createDefaultFont(), 4f, 4f, 4f, "test");
            pdfGenerator.doesTextFit(PDFontFactory.createDefaultFont(), 4f, 4f, 4f, null);
            pdfGenerator.doesTextFit(PDFontFactory.createDefaultFont(), 4f, 4f, 4f, "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    public void nullTest2() {
        runReceiptPdfGenerator(null, "nulltest2.pdf");
    }

    private void runReceiptPdfGenerator(ReceiptData receiptData, String filename) {
        File testFile = new File(filename);

        InputStream initialStream = pdfGenerator.generateReceiptPdf(receiptData);
        Boolean savedFile = true;
        try (OutputStream outStream = new FileOutputStream(testFile)) {

            while (initialStream.available() > 0) {
                byte[] buffer = new byte[500];
                initialStream.read(buffer);

                outStream.write(buffer);
            }
        } catch (FileNotFoundException ex) {
            // this will typically happen when the tests are running in Jenkins
            savedFile = false;
        } catch (IOException ex) {
            Assert.assertTrue(false);
            savedFile = false;
        }

        if (savedFile) {
            log.info("Saved file to:  " + testFile.getAbsolutePath());
        } else {
            log.info("Test passed but was unable to save file locally.");
        }
    }
}
