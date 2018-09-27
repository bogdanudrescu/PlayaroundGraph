/*
 * Created on Dec 2, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EdgeTool extends Tool {
    /**
     * Default constructor
     *
     */
    public EdgeTool() {
        super();
    }

    /**
     * Constructs the tool
     * 
     * @param p0
     * @param p1
     */
    public EdgeTool(Point p0, Point p1) {
        super(p0, p1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Tool#draw(java.awt.Graphics)
     */
    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.drawLine(point0.x, point0.y, point1.x, point1.y);
    }

}
