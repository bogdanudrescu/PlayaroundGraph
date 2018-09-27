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
import java.awt.Rectangle;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NodeTool extends Tool {
    /**
     * Default constructor
     *
     */
    public NodeTool() {
        super();
    }

    /**
     * Constructs the tool
     * 
     * @param p0
     * @param p1
     */
    public NodeTool(Point p0, Point p1) {
        super(p0, p1);
    }

    /**
     * Get the rectangle formed from the two points
     * 
     * @return
     */
    public Rectangle getRectangle() {
        Rectangle position = new Rectangle();

        position.x = point0.x < point1.x ? point0.x : point1.x;
        position.y = point0.y < point1.y ? point0.y : point1.y;
        position.width = Math.abs(point1.x - point0.x);
        position.height = Math.abs(point1.y - point0.y);

        return position;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Tool#draw(java.awt.Graphics)
     */
    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.drawLine(point0.x, point0.y, point0.x, point1.y);
        g.drawLine(point0.x, point1.y, point1.x, point1.y);
        g.drawLine(point1.x, point1.y, point1.x, point0.y);
        g.drawLine(point1.x, point0.y, point0.x, point0.y);
    }

}
