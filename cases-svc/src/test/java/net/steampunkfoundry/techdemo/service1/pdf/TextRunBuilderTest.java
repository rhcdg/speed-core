package net.steampunkfoundry.techdemo.service1.pdf;

import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.jupiter.api.Test;

class TextRunBuilderTest {

    public static final PDFont BOLD_BODY_FONT = PDType1Font.TIMES_BOLD;
    public static final float BODY_FONT_SIZE = 8f;

    @Test
    void builder() {
        TextRunBuilder builder = new TextRunBuilder().setFont(BOLD_BODY_FONT).setFontSize(BODY_FONT_SIZE)
                .setText("We have approved your application for an Advanced Parole Document.  ");

        builder.setBullet('c');
        builder.setFont(BOLD_BODY_FONT);
        builder.setFontSize(BODY_FONT_SIZE);
        builder.setIndent(4f);
        builder.setUnderline(false);
        builder.setPrependParagraphBreak(false);
        builder.setText("test");

        builder.getBullet();
        builder.getFont();
        builder.getFontSize();
        builder.getIndent();
        builder.isPrependParagraphBreak();
        builder.isUnderline();
        builder.getText();

    }

}
