package gui.editor;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;

public class LNEditorPane extends JEditorPane {

    private final Color LINE_HIGHLIGHT_COLOR = new Color(230, 230, 250);
    private Highlighter.HighlightPainter painter;
    private Object highlight;
    private int currentLine = 0;
    private int lineHeight = 0;
    private ArrayList<Integer> breakpoints = new ArrayList<Integer>();

    public LNEditorPane(MouseInputListener listener) {
        this();
        addMouseListener(listener);
    }

    public LNEditorPane() {
        setEditorKit(new NumberedEditorKit());
        setFont(new Font("Monospaced", Font.PLAIN, 15));
        this.painter = new LineHighlightPainter(LINE_HIGHLIGHT_COLOR);
        setVisible(true);
    }

    public int getCurrentLine() {
        return currentLine;
    }

    public void setCurrentLine(int currentLine) {
        this.currentLine = currentLine;
        highlightLine();
    }

    private void highlightLine() {

        if (this.highlight != null) {
            this.getHighlighter().removeHighlight(this.highlight);
            this.highlight = null;
        }

        final Element root = this.getDocument().getDefaultRootElement();
        final Element elem = root.getElement(currentLine);
        int start = elem.getStartOffset();
        int end = elem.getEndOffset();
        this.setCaretPosition(start);
        try {
            this.highlight = this.getHighlighter().addHighlight(start,
                    end,
                    this.painter);
            this.repaint();
        } catch (BadLocationException ex) {
        }
    }

    class NumberedEditorKit extends StyledEditorKit {

        @Override
        public ViewFactory getViewFactory() {
            return new NumberedViewFactory();
        }

        @Override
        public Document createDefaultDocument() {
            return new HighlightDocument();
        }
    }

    class NumberedViewFactory implements ViewFactory {

        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new NumberedParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }
            // default to text display
            return new LabelView(elem);
        }
    }

    final class NumberedParagraphView extends ParagraphView {

        public short NUMBERS_WIDTH = 50;
        public short MINIMUM_DISPLAY_DIGITS = 3;

        public NumberedParagraphView(Element e) {
            super(e);
            short top = 5;
            short left = 0;
            short bottom = 5;
            short right = 0;
            this.setInsets(top, left, bottom, right);
        }

        @Override
        protected void setInsets(short top, short left, short bottom,
                short right) {
            super.setInsets(top, (short) (left + NUMBERS_WIDTH),
                    bottom, right);
        }

        @Override
        public void paintChild(Graphics g, Rectangle r, int n) {
            super.paintChild(g, r, n);
            int previousLineCount = getPreviousLineCount();
            int nr = previousLineCount + n + 1;
            int numberX = r.x - getLeftInset();
            int numberY = r.y + r.height - 2;
            // TODO richtig runden (nicht +1)
            lineHeight = numberY / nr + 1;
            g.drawString(formatLine(nr), numberX, numberY);
        }

        private String formatLine(int nr) {
            StringBuilder ret = new StringBuilder(Integer.toString(nr));
            while (ret.length() != MINIMUM_DISPLAY_DIGITS + 3) {
                ret.insert(0, " ");
            }
            if (breakpoints.contains(Integer.valueOf(nr))) {
                ret.delete(1, 2);
                ret.insert(1, "x");
            }
            return ret.toString();
        }

        public int getPreviousLineCount() {
            int lineCount = 0;
            View parent = this.getParent();
            int count = parent.getViewCount();
            for (int i = 0; i < count; i++) {
                if (parent.getView(i) == this) {
                    break;
                } else {
                    lineCount += parent.getView(i).getViewCount();
                }
            }
            return lineCount;
        }
    }

    public int getLineHeight() {
        return lineHeight;
    }
    
    public boolean isBreakPoint(int line){
        return breakpoints.contains(line);
    }

    public void addBreakPoint(int line) {
        breakpoints.add(line);
    }

    public void removeBreakPoint(int line) {
        breakpoints.remove(breakpoints.indexOf(line));
    }
}
