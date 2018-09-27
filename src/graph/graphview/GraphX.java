/*
 * Created on Dec 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.graphview;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Vector;

import graph.GraphPanel;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GraphX implements Serializable {
    /**
     * Nodes list
     */
    private Vector nodes;

    /**
     * Edges list
     */
    private Vector edges;

    /**
     * The list of ways between two nodes
     */
    private NodesWaysX nodesWays;

    /**
     * The index of the selected node
     */
    private int selNode;

    /**
     * The graph dimension
     */
    private Dimension dimension;

    /**
     * Get the minim point
     */
    private Point minPoint;

    /**
     * Get the maxim point
     */
    private Point maxPoint;

    /**
     * Construct the graph
     *
     */
    public GraphX() {
        nodes = new Vector(10);
        edges = new Vector(10);

        dimension = new Dimension();
        minPoint = new Point();
        maxPoint = new Point();
    }

    /**
     * Add a new edge
     * 
     * @param edgeX
     */
    public void addEdge(EdgeX edgeX) {
        edges.addElement(edgeX);
    }

    /**
     * Add a edge to the sides list and between two nodes
     * 
     * @param node0
     * @param node1
     */
    public void addEdge(NodeX node0, NodeX node1) {
        if (node0.equals(node1)) {
            return;
        }
        EdgeX edge = new EdgeX(node0, node1);
        edges.addElement(edge);
    }

    /**
     * Add a node to the nodes list
     * 
     * @param rectangle
     */
    public void addNode(Rectangle rectangle) {
        nodes.addElement(new NodeX(rectangle));
    }

    /**
     * Add a node to the nodes list
     * 
     * @param node
     */
    public void addNode(NodeX node) {
        nodes.addElement(node);
    }

    /**
     * Delete a side from the list and remove the adjacents between its two
     * nodes
     * 
     * @param side
     */
    public boolean deleteEdge(EdgeX edge) {
        return edges.removeElement(edge);
    }

    /**
     * Delete a node from the list, from its adjacents lists and delete the
     * sides hwo lies on it
     * 
     * @param node
     */
    public boolean deleteNode(NodeX node) {
        /* Remove the edges conected to the node */
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).getFirstAdjacent().equals(node)) {
                deleteEdge(edgeAt(i));
                i--;
            } else if (edgeAt(i).getSeccondAdjacent().equals(node)) {
                deleteEdge(edgeAt(i));
                i--;
            }
        }

        return nodes.removeElement(node);
    }

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
    public NodeX nodeAt(int i) {
        try {
            return (NodeX) nodes.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
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
     * Check if the point is over the nodes (only one node is enough)
     * 
     * @return
     */
    public boolean nodesContains(Point p) {
        for (int i = nodesNumber() - 1; i >= 0; i--) {
            if (nodeAt(i).getState() == true) {
                if (nodeAt(i).rectangleContains(p)) {
                    return true;
                }
            }
        }
        for (int i = nodesNumber() - 1; i >= 0; i--) {
            if (nodeAt(i).getState() == false) {
                if (nodeAt(i).rectangleContains(p)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verify if the resize rectangle contains the point
     * 
     * @param p
     * @return
     */
    public boolean nodesResizeContains(Point p) {
        Rectangle rectangle = new Rectangle(0, 0, 0, 0);
        if (isOneNodeSelected()) {
            rectangle = getSelectedNode().getRectangle();
        }
        for (int i = nodesNumber() - 1; i >= 0; i--) {
            if (nodeAt(i).getState() == true) {
                if (nodeAt(i).getResizeRectangle().contains(p)) {
                    return true;
                }
            }
        }
        for (int i = nodesNumber() - 1; i >= 0; i--) {
            if (nodeAt(i).getState() == false) {
                if (nodeAt(i).getResizeRectangle().contains(p)) {
                    /* There is one node selected */
                    if ((!rectangle.contains(p) && rectangle.width != 0) || rectangle.width == 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if a rectangle intersect the authers nodes's rectangles
     * 
     * @param rectangle
     * @return
     */
    public boolean nodesIntersect(Rectangle rectangle) {
        for (int i = 0; i < nodesNumber(); i++) {
            if (nodeAt(i).getRectangle().intersects(rectangle)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the given node is over the others
     * 
     * @param node
     * @return
     */
    public boolean nodesIntersect(NodeX node) {
        for (int i = 0; i < nodesNumber(); i++) {
            if (!nodeAt(i).equals(node)) {
                if (nodeAt(i).getRectangle().intersects(node.getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Get the node that contains the point
     * 
     * @param p
     * @return
     */
    public NodeX getNode(Point p) {
        /* First search for selected nodes */
        for (int i = nodesNumber() - 1; i >= 0; i--) {
            if (nodeAt(i).getState() == true) {
                if (nodeAt(i).rectangleContains(p)) {
                    return nodeAt(i);
                }
            }
        }
        /* Secrch for deselected nodes */
        for (int i = nodesNumber() - 1; i >= 0; i--) {
            if (nodeAt(i).getState() == false) {
                if (nodeAt(i).rectangleContains(p)) {
                    return nodeAt(i);
                }
            }
        }

        return null;
    }

    /**
     * Get the index of the node
     * 
     * @param node
     */
    public int getNodeIndex(NodeX node) {
        return nodes.indexOf(node);
    }

    /**
     * Get the index of the edge
     * 
     * @param edge
     * @return
     */
    public int getEdgeIndex(EdgeX edge) {
        return edges.indexOf(edge);
    }

    /**
     * Verify if the edge formed from node0 and node1 exist in the list This
     * verification can't be done with the edge because the edges can be
     * different inside except the nodes
     * 
     * @param node0
     * @param node1
     * @return
     */
    public boolean isEdgeInList(NodeX node0, NodeX node1) {
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).getFirstAdjacent().equals(node0) && edgeAt(i).getSeccondAdjacent().equals(node1)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the selected nodes
     * 
     * @return
     */
    public Vector getSelectedNodes() {
        Vector selNodes = new Vector(10);
        for (int i = 0; i < nodes.size(); i++) {
            if (nodeAt(i).getState() == true) {
                selNodes.addElement(nodeAt(i));
            }
        }
        return selNodes;
    }

    /**
     * Get the selected node
     * 
     * @return
     */
    public NodeX getSelectedNode() {
        Vector selNodes = new Vector(10);
        for (int i = 0; i < nodes.size(); i++) {
            if (nodeAt(i).getState() == true) {
                return nodeAt(i);
            }
        }
        return null;
        // return nodeAt(selNode);
    }

    /**
     * Get the selected node index
     * 
     * @return
     */
    public Vector getSelectedNodesIndex() {
        Vector selNodes = new Vector(10);
        for (int i = 0; i < nodes.size(); i++) {
            if (nodeAt(i).getState() == true) {
                selNodes.addElement(new Integer(i));
            }
        }
        return selNodes;
    }

    /**
     * Verify if there is any node selected
     * 
     * @return
     */
    public boolean isAnyNodeSelected() {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodeAt(i).getState() == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Select the node with point in it
     * 
     * @param p
     */
    public int selectNode(Point p) {
        NodeX nodeX = getNode(p);

        int index = getNodeIndex(nodeX);

        /* If the node is selected do not change enything */
        if (nodeX.getState()) {
            return index;
        }

        nodeX.setState(true);

        return index;
    }

    /**
     * Select the node
     * 
     * @param p
     */
    public int selectNode(NodeX nodeX) {
        int index = getNodeIndex(nodeX);

        /* If the node is selected do not change enything */
        if (nodeX.getState()) {
            return index;
        }

        nodeX.setState(true);
        return index;
    }

    /**
     * Deselect the node
     * 
     * @param nodeX
     */
    public int deselectNode(NodeX nodeX) {
        int index = getNodeIndex(nodeX);
        nodeX.setState(false);
        return index;
    }

    /**
     * Select the nodes that intersects the rectangle
     * 
     * @param rectangle
     */
    public void selectNodes(Rectangle rectangle) {
        for (int i = 0; i < nodesNumber(); i++) {
            if (rectangle.intersects(nodeAt(i).getRectangle())) {
                nodeAt(i).setState(true);
            }
        }
    }

    /**
     * Select the edges that have the both nodes selected
     *
     */
    public void selectEdgesFromNodes() {
        deselectEdges();
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).getFirstAdjacent().getState() == true && edgeAt(i).getSeccondAdjacent().getState() == true) {
                edgeAt(i).setState(true);
            }
        }
    }

    /**
     * Deselect all the nodes and edges
     *
     */
    public void selectAll() {
        selectNodes();
        selectEdges();
    }

    /**
     * Deselect all the nodes
     *
     */
    public void selectNodes() {
        for (int i = 0; i < nodesNumber(); i++) {
            nodeAt(i).setState(true);
        }
    }

    /**
     * Deselect all the edges
     *
     */
    private void selectEdges() {
        for (int i = 0; i < edgesNumber(); i++) {
            edgeAt(i).setState(true);
        }
    }

    /**
     * Deselect all the nodes and edges
     *
     */
    public void deselectAll() {
        deselectNodes();
        deselectEdges();
    }

    /**
     * Deselect all the nodes
     *
     */
    public void deselectNodes() {
        for (int i = 0; i < nodesNumber(); i++) {
            nodeAt(i).setState(false);
        }
    }

    /**
     * Deselect all the edges
     *
     */
    public void deselectEdges() {
        for (int i = 0; i < edgesNumber(); i++) {
            edgeAt(i).setState(false);
        }
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
    public EdgeX edgeAt(int i) {
        try {
            return (EdgeX) edges.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
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
     * Verify if the edges contains the point
     * 
     * @param p
     * @return
     */
    public boolean edgesContains(Point p, boolean arrow) {
        if (edgesNumber() == 0) {
            return false;
        }
        for (int i = edgesNumber() - 1; i >= 0; i--) {
            if (edgeAt(i).edgeContains(p) || edgeAt(i).rectangleContains(p)) {
                return true;
            }
            // if(arrow && edgeAt(i).arrowContains(p)) {
            // System.out.println("arrow");
            // return true;
            // }
        }
        return false;
    }

    /**
     * Verify if there is any edge selected Return false if the ways are sets
     * the true
     * 
     * @return
     */
    public boolean isAnyEdgeSelected() {
        try {
            if (nodesWays.getState() == true) {
                return false;
            }
        } catch (NullPointerException e) {
        }

        for (int i = 0; i < edges.size(); i++) {
            if (edgeAt(i).getState() == true) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the node that contains the point
     * 
     * @param p
     * @return
     */
    public EdgeX getEdge(Point p) {
        for (int i = edgesNumber() - 1; i >= 0; i--) {
            if (edgeAt(i).edgeContains(p) || edgeAt(i).setCurrentPoint(p)) {
                return edgeAt(i);
            }
        }
        return null;
    }

    /**
     * Get the selected node
     * 
     * @return
     */
    public EdgeX getSelectedEdge() {
        for (int i = 0; i < edges.size(); i++) {
            if (edgeAt(i).getState() == true) {
                return edgeAt(i);
            }
        }
        return null;
    }

    /**
     * Get the selected node index
     * 
     * @return
     */
    public int getSelectedEdgeIndex() {
        for (int i = 0; i < edges.size(); i++) {
            if (edgeAt(i).getState() == true) {
                return i;
            }
        }
        return -1;
    }

    // /**
    // * Select the edge with point p
    // * @param p
    // */
    // public void selectEdge(Point p) {
    // deselectAll();
    // getEdge(p).setState(true);
    // }

    /**
     * Select the edge and return if selected or not
     * 
     * @param p
     * @return
     */
    public boolean selectEdgeX(Point p) {
        for (int i = edgesNumber() - 1; i >= 0; i--) {
            if (edgeAt(i).edgeContains(p)) {
                deselectAll();
                edgeAt(i).setState(true);
                return true;
            }
        }
        return false;
    }

    /**
     * Verify if is just one node selected
     * 
     * @return
     */
    public boolean isOneNodeSelected() {
        int numbers = 0;
        for (int i = 0; i < nodesNumber(); i++) {
            if (nodeAt(i).getState() == true) {
                numbers++;
            }
        }

        if (numbers == 1) {
            return true;
        }
        return false;
        // Big stupid thing I've made:
        // /* one node case */
        // if(numbers == 1 && nodesNumber() == 1) {
        // return true;
        // }
        // /* many nodes case */
        // if(numbers == 0 || numbers == nodesNumber()) {
        // return false;
        // }
        // return true;
    }

    /**
     * Verify if is just one node selected
     * 
     * @return
     */
    public boolean isOneEdgeSelected() {
        int numbers = 0;
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).getState() == true) {
                numbers++;
            }
        }
        /* one node case */
        if (numbers == 1 && edgesNumber() == 1) {
            return true;
        }
        /* many nodes case */
        if (numbers == 0 || numbers == edgesNumber()) {
            return false;
        }
        return true;
    }

    /**
     * Get the nodes ways list
     * 
     * @return
     */
    public NodesWaysX getWays() {
        return nodesWays;
    }

    /**
     * Add a new ways
     * 
     * @param nodesWays
     */
    public void setWays(NodesWaysX nodesWays) {
        this.nodesWays = nodesWays;
        this.nodesWays.setState(true);
    }

    /**
     * Add a new ways
     * 
     * @param node0
     * @param node1
     */
    public void setWays(NodeX node0, NodeX node1) {
        this.nodesWays = new NodesWaysX(node0, node1);
        this.nodesWays.setState(true);
    }

    /**
     * Delete the ways from the list
     * 
     * @param nodesWays
     * @return
     */
    public void deleteWays() {
        deselectAll();
        try {
            this.nodesWays.setState(false);
        } catch (NullPointerException e) {
        }
    }

    /**
     * Check if the point is over the split points of the edges
     * 
     * @param p
     * @return
     */
    public boolean overSplitPoints(Point p) {
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).setCurrentPoint(p)) {
                // edgeAt(i).setNoCurrentPoint();
                return true;
            }
            // edgeAt(i).setNoCurrentPoint();
        }
        return false;
    }

    /**
     * Compute and get the graph's dimension
     * 
     * @return
     */
    public void computeDimension() {
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;

        /* Get the minim and maxim from nodes point */
        for (int i = 0; i < nodesNumber(); i++) {
            if (nodeAt(i).getRectangle().x < minX) {
                minX = nodeAt(i).getRectangle().x;
            } else if (nodeAt(i).getRectangle().x + nodeAt(i).getRectangle().width > maxX) {
                maxX = nodeAt(i).getRectangle().x + nodeAt(i).getRectangle().width;
            }

            if (nodeAt(i).getRectangle().y < minY) {
                minY = nodeAt(i).getRectangle().y;
            } else if (nodeAt(i).getRectangle().y + nodeAt(i).getRectangle().height > maxY) {
                maxY = nodeAt(i).getRectangle().y + nodeAt(i).getRectangle().height;
            }
        }
        /* Get the minim and maxim from edge's split points */
        for (int i = 0; i < edgesNumber(); i++) {
            for (int j = 0; j < edgeAt(i).splitPointsNumber(); j++) {
                if (edgeAt(i).splitPointAt(j).x < minX) {
                    minX = edgeAt(i).splitPointAt(j).x;
                } else if (edgeAt(i).splitPointAt(j).x > maxX) {
                    maxX = edgeAt(i).splitPointAt(j).x;
                }

                if (edgeAt(i).splitPointAt(j).y < minY) {
                    minY = edgeAt(i).splitPointAt(j).y;
                } else if (edgeAt(i).splitPointAt(j).y > maxY) {
                    maxY = edgeAt(i).splitPointAt(j).y;
                }
            }
        }

        /* Move the coordinates of the nodes */
        for (int i = 0; i < nodesNumber(); i++) {
            if (nodeAt(i).getState() == true) {
                /* Go out at left / up */
                if (minX < 0) {
                    nodeAt(i).getRectangle().x -= minX;
                }
                if (minY < 0) {
                    nodeAt(i).getRectangle().y -= minY;
                }
                /* Go out at right / down */
                if (maxX > GraphPanel.MAX_WIDTH) {
                    nodeAt(i).getRectangle().x -= maxX - GraphPanel.MAX_WIDTH;
                }
                if (maxY > GraphPanel.MAX_HEIGHT) {
                    nodeAt(i).getRectangle().y -= maxY - GraphPanel.MAX_HEIGHT;
                }
            }
        }

        /* Move the coordinates of the edges split points */
        for (int i = 0; i < edgesNumber(); i++) {
            if (edgeAt(i).getState() == true) {
                for (int j = 0; j < edgeAt(i).splitPointsNumber(); j++) {
                    /* Go out at left / up */
                    if (minX < 0) {
                        edgeAt(i).splitPointAt(j).x -= minX;
                    }
                    if (minY < 0) {
                        edgeAt(i).splitPointAt(j).y -= minY;
                    }
                    /* Go out at right / down */
                    if (maxX > GraphPanel.MAX_WIDTH) {
                        edgeAt(i).splitPointAt(j).x -= maxX - GraphPanel.MAX_WIDTH;
                    }
                    if (maxY > GraphPanel.MAX_HEIGHT) {
                        edgeAt(i).splitPointAt(j).y -= maxY - GraphPanel.MAX_HEIGHT;
                    }
                }
            }
        }

        dimension.width = maxX;// - minX;
        dimension.height = maxY;// - minY;

        minPoint.x = minX;
        minPoint.y = minY;
        maxPoint.x = maxX;
        maxPoint.y = maxY;
    }

    /**
     * Get the graph dimension
     * 
     * @return
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**
     * Get the minim point
     * 
     * @return
     */
    public Point getMinPoint() {
        return minPoint;
    }

    /**
     * Get the maxim Point
     * 
     * @return
     */
    public Point getMaxPoint() {
        return maxPoint;
    }

}
