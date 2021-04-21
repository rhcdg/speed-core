package net.steampunkfoundry.techdemo.service1.pdf;

import org.apache.pdfbox.pdmodel.font.PDFont;

public class TextRunBuilder {
    private String text;
    private PDFont font;
    private float fontSize;
    private boolean prependParagraphBreak;
    private boolean underline;
    private float indent;
    private Character bullet;

    public TextRun build() {
        return new TextRun(text, font, fontSize, prependParagraphBreak, underline, indent, bullet);
    }

    public String getText() {
        return text;
    }

    public TextRunBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public PDFont getFont() {
        return font;
    }

    public TextRunBuilder setFont(PDFont font) {
        this.font = font;
        return this;
    }

    public float getFontSize() {
        return fontSize;
    }

    public TextRunBuilder setFontSize(float fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public boolean isPrependParagraphBreak() {
        return prependParagraphBreak;
    }

    public TextRunBuilder setPrependParagraphBreak(boolean prependParagraphBreak) {
        this.prependParagraphBreak = prependParagraphBreak;
        return this;
    }

    public boolean isUnderline() {
        return underline;
    }

    public TextRunBuilder setUnderline(boolean underline) {
        this.underline = underline;
        return this;
    }

    public float getIndent() {
        return indent;
    }

    public TextRunBuilder setIndent(float indent) {
        this.indent = indent;
        return this;
    }

    public Character getBullet() {
        return bullet;
    }

    public TextRunBuilder setBullet(Character bullet) {
        this.bullet = bullet;
        return this;
    }
}
