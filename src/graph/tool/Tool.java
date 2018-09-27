/*
 * Created on Dec 2, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.tool;

import java.awt.Graphics;
import java.awt.Point;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public abstract class Tool {
    /**
     * The first point who defined the tool
     */
    protected Point point0;

    /**
     * The seccond point who defined the tool
     */
    protected Point point1;

    /**
     * Default constructor
     *
     */
    protected Tool() {
        this(new Point(-1, -1), new Point(-1, -1));
    }

    /**
     * Constructor
     * 
     * @param p0
     * @param p1
     */
    protected Tool(Point p0, Point p1) {
        this.point0 = p0;
        this.point1 = p1;
    }

    /**
     * Get the first point at the i position
     * 
     * @return
     */
    public Point getFirstPoint() {
        return point0;
    }

    /**
     * Get the first point at the i position
     * 
     * @return
     */
    public Point getSeccondPoint() {
        return point1;
    }

    /**
     * Set the point at i position
     * 
     * @param p
     */
    public void setFirstPoint(Point p) {
        this.point0 = p;
    }

    /**
     * Set the point at i position
     * 
     * @param p
     */
    public void setSeccondPoint(Point p) {
        // if(p.x < 0 || p.y < 0) {
        // return;
        // }

        this.point1 = p;
    }

    /**
     * Get the draw state according to the tool points
     * 
     * @return
     */
    public boolean getDrawState() {
        if (point0.x == -1 || point0.y == -1 || point1.x == -1 || point1.y == -1) {
            return false;
        }
        return true;
    }

    /**
     * Reset the points
     *
     */
    public void reset() {
        point0.x = -1;
        point0.y = -1;
        point1.x = -1;
        point1.y = -1;
    }

    /**
     * Draw the tool
     * 
     * @param g
     */
    public abstract void draw(Graphics g);

}
