package net.steampunkfoundry.techdemo.service1.pdf;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class TextRun {
    private final String text;
    private final PDFont font;
    private final float fontSize;
    private final boolean prependParagraphBreak;
    private final boolean underline;
    private final float indent;
    private final Character bullet;

    public TextRun(String text, PDFont font, float fontSize, boolean prependParagraphBreak, boolean underline,
            float indent, Character bullet) {
        this.text = text;
        this.font = font;
        this.fontSize = fontSize;
        this.prependParagraphBreak = prependParagraphBreak;
        this.underline = underline;
        this.indent = indent;
        this.bullet = bullet;
    }

    public String getText() {
        return text;
    }

    public PDFont getFont() {
        return font;
    }

    public float getFontSize() {
        return fontSize;
    }

    public boolean getPrependParagraphBreak() {
        return prependParagraphBreak;
    }

    public boolean isUnderlined() {
        return underline;
    }

    public float getIndent() {
        return indent;
    }

    public Character getBullet() {
        return bullet;
    }
}
