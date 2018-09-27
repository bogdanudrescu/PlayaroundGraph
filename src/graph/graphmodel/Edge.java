/*
 * Created on Dec 2, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.graphmodel;

import java.io.Serializable;

/**
 * Implements the notion of a graph's edge
 */
public class Edge implements Serializable {
    /**
     * The default label
     */
    public static final String LABEL = new String("Edge");

    /**
     * The name of the side
     */
    private String label;

    /**
     * The first adjacent nodes via this side
     */
    private Node node0;

    /**
     * The first adjacent nodes via this side
     */
    private Node node1;

    /**
     * Constructon of the side
     * 
     * @param label
     * @param node0
     * @param node1
     */
    public Edge(String label, Node node0, Node node1) {
        this.label = label;

        this.node0 = node0;
        this.node1 = node1;
    }

    /**
     * Set the side label
     * 
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get the side label
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Add a adjacent vetex to the side
     * 
     * @param node
     */
    public void setFirstAdjacent(Node node) {
        this.node0 = node;
    }

    /**
     * Add a adjacent vetex to the side
     * 
     * @param node
     */
    public void setSeccondAdjacent(Node node) {
        this.node1 = node;
    }

    /**
     * Get the first adjacent node
     * 
     * @return
     */
    public Node getFirstAdjacent() {
        return node0;
    }

    /**
     * Get the first adjacent node
     * 
     * @return
     */
    public Node getSeccondAdjacent() {
        return node1;
    }

}
