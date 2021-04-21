package net.steampunkfoundry.techdemo.service1.pdf;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageEncoder {

    public static byte[] encodeAsBarcode(String value, int width, int height) throws IOException {
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix = null;

        if (value != null) {
            bitMatrix = barcodeWriter.encode(value, BarcodeFormat.CODE_128, width, height);
        }

        if (bitMatrix != null) {
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);

            return baos.toByteArray();
        } else {
            return null;
        }
    }
}
