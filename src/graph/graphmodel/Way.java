/*
 * Created on Dec 11, 2003
 */
package graph.graphmodel;

import java.io.Serializable;
import java.util.Vector;

/**
 * A logical way from a <code>Node</code> to other. The representation is made
 * through the <code>Edge</code>s
 */
public class Way implements Serializable {
    /**
     * The edges that forms the way
     */
    private Vector edges;

    /**
     * Constructor
     *
     */
    public Way() {
        edges = new Vector(10);
    }

    /**
     * Get the edges's vector
     * 
     * @return
     */
    public Vector getEdges() {
        return edges;
    }

    /**
     * Get the edges number
     * 
     * @return
     */
    public int edgesNumber() {
        return edges.size();
    }

    /**
     * Get the edge at index i
     * 
     * @param i
     * @return
     */
    public Edge edgeAt(int i) {
        try {
            return (Edge) edges.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }

    /**
     * Add a edge to the arc's list
     * 
     * @param edge
     */
    public void addEdge(Edge edge) {
        edges.addElement(edge);
    }

}
