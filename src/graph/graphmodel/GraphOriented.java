/*
 * Created on Dec 5, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.graphmodel;

import java.util.Vector;

/**
 * Implements the <code>Graph</code>'s functionality for an oriented graph
 */
public class GraphOriented extends Graph {
    /**
     * Ways list between two nodes
     */
    protected NodesWays nodesWays;

    /**
     * Construct an unoriented graph
     *
     */
    public GraphOriented() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#addEdge(graph.logicGraph.Edge)
     */
    public boolean addEdge(Edge edge) {
        if (!edge.getFirstAdjacent().contains(edge.getSeccondAdjacent())) {
            edges.addElement(edge);
            edge.getFirstAdjacent().addAdjacent(edge.getSeccondAdjacent());
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#addEdge(graph.logicGraph.Node,
     * graph.logicGraph.Node)
     */
    public boolean addEdge(Node node0, Node node1) {
        return addEdge("Edge", node0, node1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#addEdge(java.lang.String,
     * graph.logicGraph.Node, graph.logicGraph.Node)
     */
    public boolean addEdge(String label, Node node0, Node node1) {
        if (node0.equals(node1)) {
            return false;
        }

        if (!node0.contains(node1)) {
            edges.addElement(new Edge(label, node0, node1));
            node0.addAdjacent(node1);
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#addNode()
     */
    public void addNode() {
        addNode("Node");
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#addNode(graph.logicGraph.Node)
     */
    public void addNode(Node node) {
        nodes.addElement(node);
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#addNode(java.lang.String)
     */
    public void addNode(String label) {
        nodes.addElement(new Node(label));
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#deleteEdge(graph.logicGraph.Edge)
     */
    public boolean deleteEdge(Edge edge) {
        if (edges.contains(edge)) {
            /* Remove the seccond node from the first's adjacents list */
            edge.getFirstAdjacent().removeAdjacent(edge.getSeccondAdjacent());

            return edges.removeElement(edge);
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#deleteNode(graph.logicGraph.Node)
     */
    public boolean deleteNode(Node node) {
        /* Remove the node from the adjacent nodes adjacent list */
        for (int i = 0; i < nodesNumber(); i++) {
            nodeAt(i).removeAdjacent(node);
        }
        /* Remove the edges who contains the node that will be deleted */
        for (int i = 0; i < edges.size(); i++) {
            if (((Edge) edges.elementAt(i)).getFirstAdjacent().equals(node)) {
                // == node) { // == ? equals
                edges.remove(i);
                i--;
            } else if (((Edge) edges.elementAt(i)).getSeccondAdjacent().equals(node)) {
                edges.remove(i);
                i--;
            }
        }
        /* Remove the node from the nodes list */
        return nodes.removeElement(node);
    }

    /**
     * Add an way from node0 to node1
     * 
     * @param node0
     * @param node1
     * @return
     */
    public boolean addWays(Node node0, Node node1) {
        if (node0.equals(node1)) {
            return false;
        }

        Vector list = node0.searchFor(node1);

        int index = 0;

        /* The way that will be insert */
        Vector vector = new Vector(10);
        if (list.size() > 0) {
            vector.addElement(node0);
        }

        nodesWays = new NodesWays(node0, node1);
        Way way = new Way();
        Edge edge;

        while (index < list.size()) {
            edge = getEdge((Node) vector.lastElement(), (Node) list.elementAt(index));

            /* Verify if the way + edge exist */
            if (edge != null) {
                /* Add the new edge */
                way.addEdge(edge);
                if (nodesWays.waysContains(way)) {
                    edge = null;
                    /* Remove the new edge */
                    way.getEdges().removeElementAt(way.edgesNumber() - 1);
                }
            }

            if (edge != null) {
                vector.addElement(list.elementAt(index));

                /* Add a new way when reach the node1 */
                if (list.elementAt(index).equals(node1)) {

                    /* The way that will be added */
                    Way away = new Way();
                    /* Copy the way in the away */
                    for (int i = 0; i < way.edgesNumber(); i++) {
                        away.addEdge(way.edgeAt(i));
                    }
                    nodesWays.addWay(away);
                }
                index++;
            }
            /* Remove the nodes until a new way can be build */
            else {
                vector.removeElementAt(vector.size() - 1);
                way.getEdges().removeElementAt(way.edgesNumber() - 1);
            }
        }

        /* Add the nodesWay to the list */
        if (list.size() > 0) {
            return true;
        }

        return false;
    }

    /**
     * Delete a ways from the list
     * 
     * @param ways
     * @return
     */
    public void deleteWays() {
        try {
            nodesWays.deleteWays();
        } catch (NullPointerException e) {
        }
    }

    /**
     * Get the ways vector
     * 
     * @return
     */
    public NodesWays getWays() {
        return nodesWays;
    }

    /**
     * Get the way at i position
     * 
     * @param i
     * @return
     */
    public Way wayAt(int i) {
        return nodesWays.wayAt(i);
    }

    /**
     * Get the index of the way
     * 
     * @param ways
     */
    public int getWayIndex(Way way) {
        return nodesWays.getWays().indexOf(way);
    }

    /**
     * Get the number of wayes
     * 
     * @return
     */
    public int waysNumber() {
        return nodesWays.waysNumber();
    }

    public void out() {
        for (int i = 0; i < nodesNumber(); i++) {
            System.out.print(nodeAt(i).getLabel() + ": ");
            for (int j = 0; j < nodeAt(i).getAdjacentsNumber(); j++) {
                System.out.print(nodeAt(i).adjacentAt(j).getLabel() + ", ");
            }
            // System.out.println("---------------");
            System.out.println("");
        }
        System.out.println("---------------");
        for (int i = 0; i < edgesNumber(); i++) {
            System.out.print(edgeAt(i).getLabel() + ":");
            System.out.print(edgeAt(i).getFirstAdjacent().getLabel() + ", ");
            System.out.print(edgeAt(i).getSeccondAdjacent().getLabel() + "\n");
        }
    }

}
