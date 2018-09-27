/*
 * Created on Dec 5, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph;

import java.awt.Point;
import java.awt.Rectangle;

import graph.graphmodel.Edge;
import graph.graphmodel.Node;
import graph.graphmodel.Way;
import graph.graphview.EdgeX;
import graph.graphview.NodeX;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface GraphEditor {

    /**
     * Add a node to the graph
     * 
     * @param rectangle
     */
    public void addNode(Rectangle rectangle);

    /**
     * Delete the selected node from the graph
     *
     */
    public void deleteNode();

    /**
     * Add an edge to the list
     * 
     * @param edge
     * @param edgeX
     */
    public void addEdge(Edge edge, EdgeX edgeX);

    /**
     * Add an edge to the graph
     * 
     * @param node0
     * @param node1
     */
    public void addEdge(NodeX nodeX0, NodeX nodeX1);

    /**
     * Add an edge to the graph
     * 
     * @param label
     * @param nodeX0
     * @param nodeX1
     */
    public void addEdge(String label, NodeX nodeX0, NodeX nodeX1);

    /**
     * Delete the selected edge from the graph
     *
     */
    public void deleteEdge();

    /**
     * Add a way between the node0 and node1
     * 
     * @param nodeX0
     * @param nodeX1
     */
    public boolean addWay(NodeX nodeX0, NodeX nodeX1);

    /**
     * Delete the way
     * 
     * @param nodesWaysX
     */
    public void deleteWays();

    /**
     * Get the similar node of the nodeX
     * 
     * @param nodeX
     * @return
     */
    public Node nodeOf(NodeX nodeX);

    /**
     * Get the similar nodeX of node
     * 
     * @param node
     * @return
     */
    public NodeX nodeXOf(Node node);

    /**
     * Get the similar edge of the edgeX
     * 
     * @param edgeX
     * @return
     */
    public Edge edgeOf(EdgeX edgeX);

    /**
     * Get the similar edgeX of the edge
     * 
     * @param edge
     * @return
     */
    public EdgeX edgeXOf(Edge edge);

    /**
     * Set the ways edges state to true
     * 
     */
    public void selectWays();

    /**
     * Select the way
     *
     */
    public void selectWay(Way way);

    /**
     * Compute the label's bounds of the node
     * 
     * @param node
     * @param nodeX
     * @return
     */
    public Rectangle getLabelBounds(Node node, NodeX nodeX);

    /**
     * Compute the label's bounds of the edgs
     * 
     * @param edge
     * @param edgeX
     * @return
     */
    public Rectangle getLabelBounds(Edge edge, EdgeX edgeX);

    /**
     * Get the edge that contains the point
     * 
     * @param p
     * @return
     */
    public EdgeX edgeFromLabel(Point p);

    /**
     * Put the selected nodes in front of the others
     *
     */
    public void putSelectionInFront();

    /**/
}
