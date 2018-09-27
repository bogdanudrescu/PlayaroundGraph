/*
 * Created on Dec 12, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.graphview;

import java.awt.Point;
import java.io.Serializable;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NodesWaysX implements Serializable {
    /**
     * First node of the way
     */
    private NodeX node0;

    /**
     * Seccond node of the way
     */
    private NodeX node1;

    /**
     * The snap point of the graphic representation
     */
    private Point snapPoint;

    /**
     * Existing state
     */
    private boolean state;

    /**
     * Constructor
     * 
     * @param node0
     * @param node1
     */
    public NodesWaysX(NodeX node0, NodeX node1) {
        this.node0 = node0;
        this.node1 = node1;
    }

    /**
     * Set the first node
     * 
     * @param node0
     */
    public void setFirstNode(NodeX node0) {
        this.node0 = node0;
    }

    /**
     * Get the first node
     * 
     * @return
     */
    public NodeX getFirstNode() {
        return node0;
    }

    /**
     * Set the seccond node
     * 
     * @param node1
     */
    public void setSeccondNode(NodeX node1) {
        this.node1 = node1;
    }

    /**
     * Get the seccond node
     * 
     * @return
     */
    public NodeX getSeccondNode() {
        return node1;
    }

    /**
     * Set the snap point
     * 
     * @param snapPoint
     */
    public void setSnapPoint(Point snapPoint) {
        this.snapPoint = snapPoint;
    }

    /**
     * Get the snap point
     * 
     * @return
     */
    public Point getSnapPoint() {
        return snapPoint;
    }

    /**
     * Set the state of the object
     * 
     * @param state
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Get the state of the object
     * 
     * @return
     */
    public boolean getState() {
        return state;
    }

    /**
     * Verify if the point is on the ways representation
     * 
     * @param p
     * @return
     */
    public boolean waysContains(Point p) {
        Point p0 = getFirstNode().getResizePosition();
        Point p1 = getSeccondNode().getResizePosition();
        Point p2 = new Point(p1.x, p0.y);

        return MyMath.pointsContains(p0, p2, p, 3d) || MyMath.pointsContains(p1, p2, p, 3d);
    }

}
