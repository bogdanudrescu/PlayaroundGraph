/*
 * Created on Dec 12, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.tool;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WayTool extends Tool {
    /**
     * Default constructor
     *
     */
    public WayTool() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.tool.Tool#draw(java.awt.Graphics)
     */
    public void draw(Graphics g) {
        g.setColor(Color.green);
        g.drawLine(point0.x, point0.y, point1.x, point0.y);
        g.drawLine(point1.x, point0.y, point1.x, point1.y);
    }

}
