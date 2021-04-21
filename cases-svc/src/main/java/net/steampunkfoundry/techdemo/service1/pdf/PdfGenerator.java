package net.steampunkfoundry.techdemo.service1.pdf;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public abstract class PdfGenerator {
    private static float INDENT_AFTER_BULLET = 7f;

    public static void drawRect(PDPageContentStream content, float minX, float minY, float maxX, float maxY,
            float lineWidth) throws IOException {
        content.setLineWidth(lineWidth);
        content.addRect(minX, minY, maxX - minX, maxY - minY);
        content.setStrokingColor(Color.BLACK);
        content.stroke();
    }

    public static void drawFilledRect(PDPageContentStream content, float minX, float minY, float maxX, float maxY,
            Color color, float lineWidth) throws IOException {
        content.setLineWidth(lineWidth);
        content.addRect(minX, minY, maxX - minX, maxY - minY);
        content.setNonStrokingColor(color);
        content.fill();
        content.setNonStrokingColor(Color.BLACK);
        content.setStrokingColor(Color.BLACK);
    }

    public static float getTextWidth(PDFont font, float fontSize, String text) throws IOException {
        if (text != null && text.length() > 0) {
            return font.getStringWidth(text) / 1000 * fontSize;
        } else {
            return 0;
        }
    }

    public static float getTextWidth(TextRun run) throws IOException {
        float result = 0;
    
        if (run != null && run.getFont() != null) {
            result = run.getIndent();
            result += getTextWidth(run.getFont(), run.getFontSize(), run.getText());
        }
    
        return result;
    }

    public static float getTextHeight(PDFont font, float fontSize) {
        return (font.getFontDescriptor().getCapHeight()) / 1000 * fontSize;
    }

    public static float getTextHeight(TextRun[] run) {
        return (float) Arrays.stream(run)
                .mapToDouble(r -> (int) (r.getFont().getFontDescriptor().getCapHeight() / 1000f * r.getFontSize()))
                .max().orElse(0);
    }

    public static void drawLeftAlignedText(PDPageContentStream contents, PDFont font, float fontSize, float xoffset,
            float yoffset, String text) throws IOException {
        if (text != null && text.length() > 0) {
            contents.beginText();
            contents.setFont(font, fontSize);
            contents.setLeading(12); // this may need to change later
            contents.newLineAtOffset(xoffset, yoffset);
            contents.showText(text);
            contents.endText();
        }
    }

    public static void drawLeftAlignedText(PDPageContentStream contents, float xoffset, float yoffset,
            TextRun[] textRuns) throws IOException {
        if (textRuns != null && textRuns.length > 0) {
            float cursorPosition = xoffset + textRuns[0].getIndent();
    
            if (textRuns[0].getBullet() != null) {
                float bulletWidth = getTextWidth(textRuns[0].getFont(), textRuns[0].getFontSize(),
                        textRuns[0].getBullet().toString());
    
                cursorPosition -= (bulletWidth + INDENT_AFTER_BULLET);
    
                contents.beginText();
                contents.setFont(textRuns[0].getFont(), textRuns[0].getFontSize());
                contents.setLeading(12); // this may need to change later
                contents.newLineAtOffset(cursorPosition, yoffset);
                contents.showText(textRuns[0].getBullet().toString());
                contents.endText();
    
                cursorPosition += (bulletWidth + INDENT_AFTER_BULLET);
            }
    
            for (int i = 0; i < textRuns.length; i++) {
                TextRun currentRun = textRuns[i];
                float width = getTextWidth(currentRun.getFont(), currentRun.getFontSize(), currentRun.getText());
    
                if (currentRun.getText() != null && currentRun.getText().length() > 0) {
    
                    contents.beginText();
                    contents.setFont(currentRun.getFont(), currentRun.getFontSize());
                    contents.setLeading(12); // this may need to change later
                    contents.newLineAtOffset(cursorPosition, yoffset);
                    contents.showText(currentRun.getText());
                    contents.endText();
    
                    if (currentRun.isUnderlined()) {
                        contents.setLineWidth(0.5f);
                        contents.setStrokingColor(Color.BLACK);
                        contents.moveTo(xoffset, yoffset - 1f);
                        contents.lineTo(xoffset + width, yoffset - 1f);
                        contents.stroke();
                    }
                }
    
                cursorPosition += width;
            }
        }
    }

    public static void drawCenteredText(PDPageContentStream contents, float minX, float maxX, float y, PDFont font,
            Color color, float fontSize, String text) throws IOException {
        if (text != null && text.length() > 0) {
            contents.beginText();
            contents.setFont(font, fontSize);
            contents.setNonStrokingColor(color);

            float width = getTextWidth(font, fontSize, text);
            float midX = (maxX - minX) / 2 + minX;
            float x = midX - (width / 2);

            contents.newLineAtOffset(x, y);
            contents.showText(text);
            contents.endText();
        }
    }

    public static void drawCenteredText(PDPageContentStream contents, float minX, float maxX, float y, PDFont font,
            float fontSize, String text) throws IOException {
        drawCenteredText(contents, minX, maxX, y, font, Color.BLACK, fontSize, text);
    }

    public static void drawRightAlignedText(PDPageContentStream contents, PDFont font, float fontSize, float xoffset,
            float yoffset, String text) throws IOException {
        if (text != null && text.length() > 0) {
            float width = getTextWidth(font, fontSize, text);

            contents.beginText();
            contents.setFont(font, fontSize);
            contents.setLeading(12); // this may need to change later
            contents.newLineAtOffset(xoffset - width, yoffset);
            contents.showText(text);
            contents.endText();
        }
    }

    public static float getTextAreaHeight(PDFont font, float fontSize, float spacing, java.util.List<String> lines) {
        int numLines = lines != null ? lines.size() : 1;
        return getTextHeight(font, fontSize) * numLines + (spacing * (numLines - 1));
    }

    // Draws text in a given area. The text is split into words and allowed to wrap
    // around to the next line. Lines
    // that would otherwise be drawn outside of the text area are ignored. Words
    // that are too long for the text area
    // are drawn; not ignored.
    public static void drawTextArea(PDPageContentStream contents, PDFont font, float fontSize, float minX, float minY,
            float maxX, float maxY, float lineSpacing, String text) throws IOException {
        if (text != null && text.length() > 0) {
            java.util.List<String> lines = splitLines(font, fontSize, maxX - minX, text);

            float textHeight = getTextHeight(font, fontSize);
            float currentLineY = maxY - textHeight;

            for (int i = 0; i < lines.size() && currentLineY >= minY; i++) {
                String currentLine = lines.get(i);

                drawLeftAlignedText(contents, font, fontSize, minX, currentLineY, currentLine);
                currentLineY = currentLineY - lineSpacing - textHeight;
            }
        }
    }

    // Draws text in a given area. The text is split into words and allowed to wrap
    // around to the next line. Lines
    // that would otherwise be drawn outside of the text area are ignored. Words
    // that are too long for the text area
    // are drawn; not ignored.
    public static void drawTextArea(PDPageContentStream contents, float minX, float minY, float maxX, float maxY,
            float lineSpacing, TextRun[] run) throws IOException {
        if (run != null && run.length > 0) {
            java.util.List<TextRun[]> lines = splitLines(run, maxX - minX);
    
            float textHeight = getTextHeight(run);
            float currentLineY = maxY - textHeight;
    
            for (int i = 0; i < lines.size() && currentLineY >= minY; i++) {
                TextRun[] currentLine = lines.get(i);
    
                if (currentLine.length > 0 && currentLine[0].getPrependParagraphBreak()) {
                    currentLineY -= lineSpacing * 2;
                }
    
                drawLeftAlignedText(contents, minX, currentLineY, currentLine);
                currentLineY = currentLineY - lineSpacing - textHeight;
            }
        }
    }

    // Splits a string of text into several lines so that when drawn the words in
    // the text would be wrapped according
    // to the specified width. If the drawn word is longer that the specified width,
    // the word is split so that the
    // word is broken up across multiple lines.
    public static java.util.List<String> splitLines(PDFont font, float fontSize, float width, String text)
            throws IOException {
        java.util.List<String> result = new ArrayList<>();

        if (text != null && text.length() > 0) {
            List<String> words = new LinkedList<>(Arrays.asList(text.split(" ")));
            float cursorX = 0;

            StringBuilder currentLine = new StringBuilder();

            for (int index = 0; index < words.size(); index++) {
                String[] multiLineWord = words.get(index).split("\\r?\\n", 2);
                String currentWord = multiLineWord[0];
                if (currentWord != null) {

                    if (cursorX > 0) {
                        currentWord = " " + currentWord;
                    }

                    float currentWordWidth = getTextWidth(font, fontSize, currentWord);

                    if (cursorX + currentWordWidth < width) {
                        // word fits on the line with room to spare

                        currentLine.append(currentWord);
                        cursorX += currentWordWidth;
                    } else if (cursorX > 0 && currentWordWidth < width) {
                        // word will fit on next line on it's own, simply start a new line
                        // with the current word as the first word of the new line

                        result.add(currentLine.toString());

                        currentLine = new StringBuilder();
                        currentLine.append(currentWord.trim());
                        cursorX = currentWordWidth;
                    } else {
                        // the word is longer than the specified width add the word
                        // character-by-character. if it overflows, continue adding
                        // characters on the next line until the whole word is added

                        for (char letter : currentWord.toCharArray()) {
                            float currentLetterWidth = getTextWidth(font, fontSize, "" + letter);

                            if (cursorX + currentLetterWidth < width) {
                                // theres room on the current line for another character,
                                // simply append the character and advance the cursor

                                currentLine.append(letter);
                                cursorX += currentLetterWidth;
                            } else {
                                // theres no more room on the current line for another character,
                                // start a new line with the current character as the first character

                                // NOTE: if the font is too big, this algorithm will continuously add
                                // lines with only one character

                                result.add(currentLine.toString());

                                currentLine = new StringBuilder();
                                currentLine.append(letter);
                                cursorX = currentLetterWidth;
                            }
                        }
                    }

                    if (multiLineWord.length > 1) {
                        result.add(currentLine.toString().trim());
                        words.add(index + 1, multiLineWord[1]);

                        currentLine = new StringBuilder();
                        cursorX = 0;
                    }
                }
            }

            if (currentLine.length() > 0) {
                result.add(currentLine.toString());
            }
        }

        return result;
    }

    // Splits runs into several lines so that when drawn the words in the runs would
    // be wrapped according
    // to the specified width. If the drawn word is longer that the specified width,
    // the word is split so that the
    // word is broken up across multiple lines.
    public static List<TextRun[]> splitLines(TextRun[] runs, float width) throws IOException {
        java.util.List<TextRun[]> lines = new ArrayList<>();
    
        if (runs != null && runs.length > 0) {
            float cursorX = runs[0].getIndent();
            List<TextRun> currentLine = new ArrayList<>();
            StringBuilder currentTargetRunText = new StringBuilder();
            boolean prependParagraphBreak = false;
            boolean showBullet = false;
            float currentIndent = 0f;
    
            for (int currentSourceRunIndex = 0; currentSourceRunIndex < runs.length; currentSourceRunIndex++) {
                TextRun currentSourceRun = runs[currentSourceRunIndex];
                boolean currentRunIsBeginningOfLine = currentLine.size() == 0;
    
                prependParagraphBreak = currentRunIsBeginningOfLine && currentSourceRun.getPrependParagraphBreak();
                showBullet = currentRunIsBeginningOfLine && currentSourceRun.getBullet() != null;
                currentIndent = currentSourceRun.getIndent();
    
                if (currentSourceRun.getText() != null && currentSourceRun.getText().length() > 0) {
                    List<String> currentSourceRunWords = new LinkedList<>(
                            Arrays.asList(currentSourceRun.getText().split(" ")));
    
                    for (int currentSourceRunWordIndex = 0; currentSourceRunWordIndex < currentSourceRunWords
                            .size(); currentSourceRunWordIndex++) {
                        String[] multiLineWord = currentSourceRunWords.get(currentSourceRunWordIndex).split("\\r?\\n",
                                2);
                        String currentWord = multiLineWord[0];
    
                        if (currentWord != null) {
    
                            if (currentTargetRunText.length() > 0) {
                                currentWord = " " + currentWord;
                            }
    
                            float currentWordWidth = getTextWidth(currentSourceRun.getFont(),
                                    currentSourceRun.getFontSize(), currentWord);
    
                            if (cursorX + currentWordWidth < (width - currentIndent)) {
                                // word fits on the line with room to spare
    
                                currentTargetRunText.append(currentWord);
                                cursorX += currentWordWidth;
                            } else if (cursorX > 0 && currentWordWidth < (width - currentIndent)) {
                                // word will fit on next line on it's own, simply start a new line
                                // with the current word as the first word of the new line
    
                                // create a new run with the current run's text
                                TextRun newRun = new TextRun(currentTargetRunText.toString(),
                                        currentSourceRun.getFont(), currentSourceRun.getFontSize(),
                                        prependParagraphBreak, currentSourceRun.isUnderlined(),
                                        currentRunIsBeginningOfLine ? currentSourceRun.getIndent() : 0,
                                        showBullet ? currentSourceRun.getBullet() : null);
                                prependParagraphBreak = false;
                                showBullet = false;
    
                                // append the run to current line
                                currentLine.add(newRun);
    
                                // convert the list of runs into an array of runs; append to list of lines
                                lines.add(currentLine.toArray(new TextRun[] {}));
    
                                // start a new line list of runs for the current line
                                currentLine = new ArrayList<>();
                                currentTargetRunText = new StringBuilder();
    
                                // add the current word to the current run of text
                                currentTargetRunText.append(currentWord.trim());
                                cursorX = currentWordWidth;
                            } else {
                                // the word is longer than the specified width. add the word
                                // character-by-character. if it overflows, continue adding
                                // characters on the next line until the whole word is added
    
                                for (char letter : currentWord.toCharArray()) {
    
                                    float currentLetterWidth = getTextWidth(currentSourceRun.getFont(),
                                            currentSourceRun.getFontSize(), "" + letter);
    
                                    if (cursorX + currentLetterWidth < (width - currentIndent)) {
                                        // theres room on the current line for another character,
                                        // simply append the character and advance the cursor
    
                                        currentTargetRunText.append(letter);
                                        cursorX += currentLetterWidth;
                                    } else {
                                        // theres no more room on the current line for another character,
                                        // start a new line with the current character as the first character
    
                                        // NOTE: if the font is too big, this algorithm will continuously add
                                        // lines with only one character
    
                                        // create a new run with the current run's text
                                        TextRun newRun = new TextRun(currentTargetRunText.toString(),
                                                currentSourceRun.getFont(), currentSourceRun.getFontSize(),
                                                prependParagraphBreak, currentSourceRun.isUnderlined(),
                                                currentRunIsBeginningOfLine ? currentSourceRun.getIndent() : 0,
                                                showBullet ? currentSourceRun.getBullet() : null);
                                        prependParagraphBreak = false;
                                        showBullet = false;
                                        currentLine.add(newRun);
    
                                        // convert the list of runs into an array of runs; append to list of lines
                                        lines.add(currentLine.toArray(new TextRun[] {}));
    
                                        // start a new line list of runs for the current line
                                        currentLine = new ArrayList<>();
                                        currentTargetRunText = new StringBuilder();
    
                                        // add the letter to the new run
                                        currentTargetRunText.append(letter);
                                        cursorX = currentSourceRun.getIndent() + currentLetterWidth;
                                    }
                                }
                            }
    
                            if (multiLineWord.length > 1) {
                                // encountering a CR/LF
    
                                TextRun newRun = new TextRun(currentTargetRunText.toString(),
                                        currentSourceRun.getFont(), currentSourceRun.getFontSize(),
                                        prependParagraphBreak, currentSourceRun.isUnderlined(),
                                        currentRunIsBeginningOfLine ? currentSourceRun.getIndent() : 0,
                                        showBullet ? currentSourceRun.getBullet() : null);
                                prependParagraphBreak = false;
                                showBullet = false;
                                currentLine.add(newRun);
    
                                // convert the list of runs into an array of runs; append to list of lines
                                lines.add(currentLine.toArray(new TextRun[] {}));
    
                                // ensure the second word is handled
                                currentSourceRunWords.add(currentSourceRunWordIndex + 1, multiLineWord[1]);
    
                                // start a new line list of runs for the current line
                                currentLine = new ArrayList<>();
                                currentTargetRunText = new StringBuilder();
                                cursorX = 0;
                            }
                        }
                    }
                } else {
                    // run is empty, move to the next run
                }
    
                if (currentTargetRunText.length() > 0) {
                    TextRun newRun = new TextRun(currentTargetRunText.toString(), currentSourceRun.getFont(),
                            currentSourceRun.getFontSize(), prependParagraphBreak, currentSourceRun.isUnderlined(),
                            currentSourceRun.getIndent(), showBullet ? currentSourceRun.getBullet() : null);
                    currentLine.add(newRun);
    
                    currentTargetRunText = new StringBuilder();
                }
            }
    
            // convert the list of runs into an array of runs; append to list of lines
            lines.add(currentLine.toArray(new TextRun[] {}));
        }
    
        return lines;
    }

    public static boolean doesTextFit(PDFont font, float fontSize, float minX, float maxX, String text)
            throws IOException {
        java.util.List<String> lines = splitLines(font, fontSize, maxX - minX, text);
        return lines.isEmpty() || lines.size() <= 1;
    }
}
