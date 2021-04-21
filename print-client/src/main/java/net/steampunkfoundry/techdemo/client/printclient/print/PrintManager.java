package net.steampunkfoundry.techdemo.client.printclient.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.stereotype.Component;

@Component
public class PrintManager {

    /**
     * print the provided PDF to the default printer.
     *
     * @param pdfBytes bytes to print
     * @return the name of the printer the document was printed to.
     * @throws PrinterException printerException
     * @throws IOException      IOException
     */
    public String print(byte[] pdfBytes) throws PrinterException, IOException {
        PDDocument document = PDDocument.load(pdfBytes);
        //takes standard printer defined by OS
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        job.setPageable(new PDFPageable(document));
        job.setPrintService(printService);
        job.print();
        return printService.getName();
    }
}
