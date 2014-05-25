/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.editor;

import javax.swing.plaf.TextUI;
import javax.swing.text.*;
import java.awt.*;

/**
 *
 * @author nilsschwenkel
 */

/**
 * Simple highlight painter that fills a highlighted area with
 * a solid color.
 * 
 * @author Jan Lolling
 * @link http://www.jug-bb.de/2009/02/jeditorpane-mit-zeilenhervorhebung/
 */
public class LineHighlightPainter extends LayeredHighlighter.LayerPainter {

    private Color color;

    /**
     * Constructs a new highlight painter. If <code>c</code> is null,
     * the JTextComponent will be queried for its selection color.
     *
     * @param c
     *            the color for the highlight
     */
    public LineHighlightPainter(Color c) {

        this.color = c;
    }

    /**
     * Returns the color of the highlight.
     *
     * @return the color
     */
    public Color getColor() {

        return this.color;
    }

    // --- HighlightPainter methods ---------------------------------------
    /**
     * Paints a highlight.
     *
     * @param g
     *            the graphics context
     * @param offs0
     *            the starting model offset &gt;= 0
     * @param offs1
     *            the ending model offset &gt;= offs1
     * @param bounds
     *            the bounding box for the highlight
     * @param c
     *            the editor
     */
    public void paint(Graphics g, int offs0, int offs1, Shape bounds,
            JTextComponent c) {

        final Rectangle alloc = bounds.getBounds();
        try {
            // --- determine locations ---
            TextUI mapper = c.getUI();
            final Rectangle p0 = mapper.modelToView(c, offs0);
            final Rectangle p1 = mapper.modelToView(c, offs1);

            // --- render ---
            if (getColor() == null) {
                g.setColor(c.getSelectionColor());
            } else {
                g.setColor(getColor());
            }
            final Rectangle r = p0.union(p1);
            g.fillRect(r.x, r.y, r.width, r.height);
        } catch (BadLocationException e) {
            // can't render
        }
    }

    // --- LayerPainter methods ----------------------------
    /**
     * Paints a portion of a highlight.
     *
     * @param g
     *            the graphics context
     * @param offs0
     *            the starting model offset &gt;= 0
     * @param offs1
     *            the ending model offset &gt;= offs1
     * @param bounds
     *            the bounding box of the view, which is not
     *            necessarily the region to paint.
     * @param c
     *            the editor
     * @param view
     *            View painting for
     * @return region drawing occured in
     */
    @Override
    public Shape paintLayer(Graphics g, int offs0, int offs1, Shape bounds,
            JTextComponent c, View view) {

        if (getColor() == null) {
            g.setColor(c.getSelectionColor());
        } else {
            g.setColor(getColor());
        }
        // Should only render part of View.
        try {
            // --- determine locations ---
            Shape shape = view.modelToView(
                    offs0,
                    Position.Bias.Forward,
                    offs1,
                    Position.Bias.Backward,
                    bounds);
            Rectangle r = shape instanceof Rectangle ? (Rectangle) shape
                    : shape.getBounds();
            g.fillRect(r.x, r.y, c.getWidth(), r.height);
            return r;
        } catch (BadLocationException e) {
            // can't render
        }
        return null;
    }
}
