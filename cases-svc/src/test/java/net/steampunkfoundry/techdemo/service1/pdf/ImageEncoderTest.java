package net.steampunkfoundry.techdemo.service1.pdf;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ImageEncoderTest {

    @Test
    void encodeAsBarcode1() {
        ImageEncoder encoder = new ImageEncoder();
        try {
            encoder.encodeAsBarcode("test", 5, 5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void encodeAsBarcode2() {
        ImageEncoder encoder = new ImageEncoder();
        try {
            encoder.encodeAsBarcode("test", -1, 5);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void encodeAsBarcode3() {
        ImageEncoder encoder = new ImageEncoder();
        try {
            encoder.encodeAsBarcode("test", 5, -1);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Test
    void encodeAsBarcode4() {
        ImageEncoder encoder = new ImageEncoder();
        try {
            encoder.encodeAsBarcode("!@#$%^&*()_++", 5, -1);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}
