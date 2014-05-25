/*
 * HighlightDocument.java
 *
 * Created on 27. Februar 2008, 21:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package gui.editor;

import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HighlightDocument extends DefaultStyledDocument {

    private Element rootElement;
    private Iterator<String> iterator;
    private MutableAttributeSet style;
    private String keyword;
    private String text;
    private Color color;
    private Pattern p;
    private Matcher m;
    private Matcher slc;
    private Color commentColor = Color.gray;
    private Pattern singleLineCommentDelimter;
    private static ArrayList<String> instructions = new ArrayList<String>();

    static {
        instructions.add("addwf");
        instructions.add("andwf");
        instructions.add("clrf");
        instructions.add("clrw");
        instructions.add("comf");
        instructions.add("decf");
        instructions.add("decfsz");
        instructions.add("incf");
        instructions.add("incfsz");
        instructions.add("iorwf");
        instructions.add("movf");
        instructions.add("movwf");
        instructions.add("nop");
        instructions.add("rlf");
        instructions.add("rrf");
        instructions.add("subwf");
        instructions.add("swapf");
        instructions.add("xorwf");

        instructions.add("bcf");
        instructions.add("bsf");
        instructions.add("btfsc");
        instructions.add("btfss");

        instructions.add("addlw");
        instructions.add("andlw");
        instructions.add("call");
        instructions.add("clrwdt");
        instructions.add("goto");
        instructions.add("iorlw");
        instructions.add("movlw");
        instructions.add("retfie");
        instructions.add("retlw");
        instructions.add("return");
        instructions.add("sleep");

        instructions.add("sublw");
        instructions.add("xorlw");
    }

    public HighlightDocument() {
        putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");

        rootElement = getDefaultRootElement();

        singleLineCommentDelimter = Pattern.compile(";");

        style = new SimpleAttributeSet();
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        super.insertString(offset, str, attr);
        processChangedLines(offset, str.length());
    }

    public void remove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
        processChangedLines(offset, length);
    }

    public void processChangedLines(int offset, int length)
            throws BadLocationException {

        text = getText(0, getLength());
        highlightString(Color.black, 0, getLength());


        iterator = instructions.iterator();

        while (iterator.hasNext()) {
            keyword = iterator.next();

            color = Color.blue;

            p = Pattern.compile("\\b" + keyword + "\\b");
            m = p.matcher(text);

            while (m.find()) {
                highlightString(color, m.start(), keyword.length());
            }
        }

        slc = singleLineCommentDelimter.matcher(text);

        while (slc.find()) {
            int line = rootElement.getElementIndex(slc.start());
            int endOffset = rootElement.getElement(line).getEndOffset() - 1;

            highlightString(commentColor, slc.start(), (endOffset - slc.start()));
        }
    }

    public void highlightString(Color col, int begin, int length) {
        StyleConstants.setForeground(style, col);

        setCharacterAttributes(begin, length, style, true);
    }
}