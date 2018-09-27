/*
 * Created on Dec 2, 2003
 */
package graph.graphmodel;

import java.io.Serializable;
import java.util.Vector;

/**
 * The abstractization for a graph structure
 */
public abstract class Graph implements Serializable {

    /**
     * Nodes list
     */
    protected Vector nodes;

    /**
     * Edges list
     */
    protected Vector edges;

    /**
     * Construct the graph
     *
     */
    protected Graph() {
        nodes = new Vector(10);
        edges = new Vector(10);
    }

    /**
     * Add a edge to the edges list and between two nodes
     * 
     * @param node0
     * @param node1
     */
    public abstract boolean addEdge(Node node0, Node node1);

    /**
     * Add a edge to the edges list and between two nodes
     * 
     * @param node0
     * @param node1
     */
    public abstract boolean addEdge(String label, Node node0, Node node1);

    /**
     * Add a edge to the edges list
     * 
     * @param edge
     */
    public abstract boolean addEdge(Edge edge);

    /**
     * Add a node to the nodes list
     *
     */
    public abstract void addNode();

    /**
     * Add a node to the nodes list
     * 
     * @param label
     */
    public abstract void addNode(String label);

    /**
     * Add a node to the nodes list
     * 
     * @param node
     */
    public abstract void addNode(Node node);

    /**
     * Deletes a side from the list and remove the adjacents between its two
     * nodes
     * 
     * @param side
     */
    public abstract boolean deleteEdge(Edge edge);

    /**
     * Delete a node from the list, from its adjacents lists and delete the
     * sides hwo lies on it
     * 
     * @param node
     */
    public abstract boolean deleteNode(Node node);

    /**
     * Get the nodes vector
     * 
     * @return
     */
    public Vector getNodes() {
        return nodes;
    }

    /**
     * Get the nod at i position
     * 
     * @param i
     * @return
     */
    public Node nodeAt(int i) {
        try {
            return (Node) nodes.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }

    /**
     * Get the index of the node
     * 
     * @param node
     */
    public int getNodeIndex(Node node) {
        return nodes.indexOf(node);
    }

    /**
     * Get the number of nodes
     * 
     * @return
     */
    public int nodesNumber() {
        return nodes.size();
    }

    /**
     * Get the edges vector
     * 
     * @return
     */
    public Vector getEdges() {
        return edges;
    }

    /**
     * Get the edge at i position
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
     * Get the index of the edge
     * 
     * @param edge
     * @return
     */
    public int getEdgeIndex(Edge edge) {
        return edges.indexOf(edge);
    }

    /**
     * Get the number of edges
     * 
     * @return
     */
    public int edgesNumber() {
        return edges.size();
    }

    /**
     * Verify if the edge formed from node0 and node1 exist
     * 
     * @return
     */
    public boolean edgeExist(Node node0, Node node1) {
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).getFirstAdjacent().equals(node0) && edgeAt(i).getSeccondAdjacent().equals(node1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the edge between the node0 and node1
     * 
     * @param node0
     * @param node1
     * @return
     */
    public Edge getEdge(Node node0, Node node1) {
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).getFirstAdjacent().equals(node0) && edgeAt(i).getSeccondAdjacent().equals(node1)) {
                return edgeAt(i);
            }
        }
        return null;
    }

}
