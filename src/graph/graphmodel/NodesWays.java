/*
 * Created on Dec 12, 2003
 */
package graph.graphmodel;

import java.io.Serializable;
import java.util.Vector;

/**
 * The list of <code>Way</code>s computed for two <code>Node</code>s
 */
public class NodesWays implements Serializable {
    /**
     * The first node of the way
     */
    private Node node0;

    /**
     * The seccond node of the way
     */
    private Node node1;

    /**
     * All the ways between node0 and node1 The ways are representing by edges
     * vectors (This vector will have vectors in it)
     */
    private Vector ways;

    /**
     * Index of the current way
     */
    private int indexWay;

    /**
     * Construct the nodes ways
     * 
     * @param node0
     * @param node1
     */
    public NodesWays(Node node0, Node node1) {
        this.node0 = node0;
        this.node1 = node1;

        ways = new Vector(10);

        indexWay = 0;
    }

    /**
     * Set the first node
     * 
     * @param node
     */
    public void setFirstNode(Node node) {
        this.node0 = node;
    }

    /**
     * Get the first node
     * 
     * @return
     */
    public Node getFirstNode() {
        return node0;
    }

    /**
     * Get the seccond node
     * 
     * @return
     */
    public Node getSeccondNode() {
        return node1;
    }

    /**
     * Set the seccond node
     * 
     * @param node
     */
    public void setSeccondNode(Node node) {
        this.node1 = node;
    }

    /**
     * Add a way to the ways list
     * 
     * @param way
     */
    public void addWay(Way way) {
        ways.addElement(way);
    }

    /**
     * Get the ways number
     * 
     * @return
     */
    public int waysNumber() {
        return ways.size();
    }

    /**
     * Get the ways vector
     * 
     * @return
     */
    public Vector getWays() {
        return ways;
    }

    /**
     * Get the way at index i
     * 
     * @param i
     * @return
     */
    public Way wayAt(int i) {
        try {
            return (Way) ways.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }

    /**
     * Verify if the ways contains the edge
     * 
     * @param edge
     * @return
     */
    public boolean waysContains(Edge edge) {
        for (int i = 0; i < waysNumber(); i++) {
            for (int j = 0; j < wayAt(i).edgesNumber(); j++) {
                if (wayAt(i).edgeAt(j).equals(edge)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verify if the ways contains the way as first part of them
     * 
     * @param way
     * @return
     */
    public boolean waysContains(Way way) {
        for (int i = 0; i < waysNumber(); i++) {
            int index = 0;
            for (int j = 0; j < way.edgesNumber(); j++) {
                if (way.edgeAt(j).equals(wayAt(i).edgeAt(j))) {
                    index++;
                }
            }
            if (index == way.edgesNumber()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Delete the ways
     *
     */
    public void deleteWays() {
        ways.removeAllElements();
    }

    /**
     * Get the size of the minimum way
     * 
     * @return
     */
    private int getMinSize() {
        int size = 0;
        if (waysNumber() > 0) {
            size = wayAt(0).edgesNumber();
        }
        for (int i = 1; i < waysNumber(); i++) {
            if (size > wayAt(i).edgesNumber()) {
                size = wayAt(i).edgesNumber();
            }
        }

        return size;
    }

    /**
     * Get the size of the maximum way
     * 
     * @return
     */
    private int getMaxSize() {
        int size = 0;
        if (waysNumber() > 0) {
            size = wayAt(0).edgesNumber();
        }
        for (int i = 1; i < waysNumber(); i++) {
            if (size < wayAt(i).edgesNumber()) {
                size = wayAt(i).edgesNumber();
            }
        }

        return size;
    }

    /**
     * Get the minimum way
     * 
     * @return
     */
    public Way getMinWay() {
        int minSize = getMinSize();
        if (minSize == 0) {
            return null;
        }

        for (int i = 0; i < waysNumber(); i++) {
            indexWay++;
            if (indexWay == waysNumber()) {
                indexWay = 0;
            }

            if (wayAt(indexWay).edgesNumber() == minSize) {
                break;
            }
        }

        indexWay = indexWay % waysNumber();
        return wayAt(indexWay);
    }

    /**
     * Get the maximum way
     * 
     * @return
     */
    public Way getMaxWay() {
        int maxSize = getMaxSize();
        if (maxSize == 0) {
            return null;
        }

        for (int i = 0; i < waysNumber(); i++) {
            indexWay++;
            if (indexWay == waysNumber()) {
                indexWay = 0;
            }

            if (wayAt(indexWay).edgesNumber() == maxSize) {
                break;
            }
        }

        indexWay = indexWay % waysNumber();
        return wayAt(indexWay);
    }

    /**
     * Get the next way
     * 
     * @return
     */
    public Way getNextWay() {
        if (waysNumber() > 0) {
            indexWay++;
            indexWay = indexWay % waysNumber();
            return wayAt(indexWay);
        }
        return null;
    }

    /**
     * Get the next way
     * 
     * @return
     */
    public Way getPrevWay() {
        if (waysNumber() > 0) {
            indexWay--;
            if (indexWay == -1) {
                indexWay = waysNumber() - 1;
            }
            return wayAt(indexWay);
        }
        return null;
    }

}
