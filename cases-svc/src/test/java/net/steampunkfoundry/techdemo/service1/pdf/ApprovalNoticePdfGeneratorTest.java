package net.steampunkfoundry.techdemo.service1.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class ApprovalNoticePdfGeneratorTest {
    ApprovalNoticePdfGenerator pdfGenerator = new ApprovalNoticePdfGenerator();

    @Test
    public void testApprovalNoticeGenerator() {
        ApprovalNoticeData testData = new ApprovalNoticeData();
        testData.setReceiptNumber("IOE513000014");
        testData.setUscisOnlineAccountNumber("B987654");
        testData.setCaseType("I131 - APPLICATION FOR TRAVEL DOCUMENT");
        testData.setReceivedDate("03/31/2021");
        testData.setPriorityDate("03/14/2021");
        testData.setApplicant("Insert Anumber here *\nI131 Receipt Fee Acceptance");
        testData.setNoticeDate("03/01/2021");
        testData.setNoticeType("Approval Notice");
        testData.setAddress("I131 Recpt Fee Accepted\nc/o Insert Here *\nInsert Here Address*");
        testData.setValidFrom("03/12/2021");
        testData.setValidTo("03/28/2021");
        testData.setConsulate("Consulate Value!");

        runReceiptPdfGenerator(testData, "approvalnotice.pdf");
    }

    @Test
    public void nullTest() {
        ApprovalNoticeData testData = new ApprovalNoticeData();
        testData.setReceiptNumber(null);
        testData.setUscisOnlineAccountNumber(null);
        testData.setCaseType(null);
        testData.setReceivedDate(null);
        testData.setPriorityDate(null);
        testData.setApplicant(null);
        testData.setNoticeDate(null);
        testData.setNoticeType(null);
        testData.setAddress(null);
        testData.setValidFrom(null);
        testData.setValidTo(null);
        testData.setConsulate(null);

        runReceiptPdfGenerator(testData, "approval nulltest.pdf");
    }

    @Test
    public void nullTest2() {
        runReceiptPdfGenerator(null, "approval nulltest2.pdf");
    }

    private void runReceiptPdfGenerator(ApprovalNoticeData approvalNoticeData, String filename) {
        File testFile = new File(filename);

        InputStream initialStream = pdfGenerator.generateApprovalNoticePdf(approvalNoticeData);
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
            log.error(ex.getMessage(), ex);
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
