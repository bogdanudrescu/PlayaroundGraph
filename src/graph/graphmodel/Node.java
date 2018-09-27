/*
 * Created on Dec 2, 2003
 */
package graph.graphmodel;

import java.io.Serializable;
import java.util.Vector;

/**
 * The implementation of a graph's node
 */
public class Node implements Serializable {
    /**
     * The default label
     */
    public static final String LABEL = new String("Node");

    /**
     * The name of the side
     */
    private String label;

    /**
     * The adjacents nodes
     */
    private Vector adjacents;

    /**
     * The check in state Used in search for a node
     */
    private boolean state;

    /**
     * Constructor of the Node
     * 
     * @param name
     */
    public Node(String label) {
        this.label = label;
        adjacents = new Vector(10);
    }

    /**
     * Add a adjacent node to the adjacent list
     * 
     * @param node
     */
    public void addAdjacent(Node node) {
        adjacents.addElement(node);
    }

    /**
     * Remove a node from the adjacents list
     * 
     * @param node
     */
    public void removeAdjacent(Node node) {
        adjacents.removeElement(node);
    }

    /**
     * Get the node element at the i position in the adjacents list
     * 
     * @param i
     * @return
     */
    public Node adjacentAt(int i) {
        return (Node) adjacents.elementAt(i);
    }

    /**
     * This node contains the in node
     * 
     * @param node
     * @return
     */
    public boolean contains(Node node) {
        return adjacents.contains(node);
    }

    /**
     * Get the adjacents number
     * 
     * @return
     */
    public int getAdjacentsNumber() {
        return adjacents.size();
    }

    /**
     * Set the node label
     * 
     * @param name
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get the node label
     * 
     * @return
     */
    public String getLabel() {
        return label;
    }

    /**
     * Search for the node by adjacents
     * 
     * @param node
     */
    public Vector searchFor(Node node) {
        Vector nodes = new Vector(10);

        /* Add its self */
        if (this.equals(node)) {
            nodes.addElement(this);
            return nodes;
        }

        state = true;

        /* Search for the node in the adjecent list */
        for (int i = 0; i < adjacents.size(); i++) {
            if (adjacentAt(i).equals(node)) {
                nodes.addElement(adjacentAt(i));
            } else if (adjacentAt(i).state == false) {
                /* Add the adjacent's founded nodes */
                Vector adjNodes = adjacentAt(i).searchFor(node);
                if (adjNodes.size() > 0) {
                    nodes.addElement(adjacentAt(i));
                }
                for (int j = 0; j < adjNodes.size(); j++) {
                    nodes.addElement(adjNodes.elementAt(j));
                }
            }
        }

        state = false;

        return nodes;
    }
}
