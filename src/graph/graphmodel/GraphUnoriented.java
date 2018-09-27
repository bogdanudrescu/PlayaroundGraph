/*
 * Created on Dec 5, 2003
 */
package graph.graphmodel;

/**
 * Implements the <code>Graph</code>'s functionality for an unoriented graph
 */
public class GraphUnoriented extends Graph {
    /**
     * Construct an unoriented graph
     *
     */
    public GraphUnoriented() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.logicGraph.Graph#addEdge(graph.logicGraph.Edge)
     */
    public boolean addEdge(Edge edge) {
        if (!edge.getFirstAdjacent().contains(edge.getSeccondAdjacent())
                && !edge.getSeccondAdjacent().contains(edge.getFirstAdjacent())) {
            edges.addElement(edge);
            try {
                edge.getFirstAdjacent().addAdjacent(edge.getSeccondAdjacent());
                edge.getSeccondAdjacent().addAdjacent(edge.getFirstAdjacent());
            } catch (NullPointerException e) {
            }
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
        if (!node0.contains(node1) && !node1.contains(node0)) {
            edges.addElement(new Edge(label, node0, node1));
            node0.addAdjacent(node1);
            node1.addAdjacent(node0);
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
            /* Removes from each other's adjacents, the edge's nodes */
            edge.getFirstAdjacent().removeAdjacent(edge.getSeccondAdjacent());
            edge.getSeccondAdjacent().removeAdjacent(edge.getFirstAdjacent());

            /* Remove the edge from the list */
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
        for (int i = 0; i < node.getAdjacentsNumber(); i++) {
            node.adjacentAt(i).removeAdjacent(node);
        }
        /* Remove the edges who contains the node that will be deleted */
        for (int i = 0; i < edges.size(); i++) {
            if (edgeAt(i).getFirstAdjacent().equals(node)) {// == node) { // ==
                                                            // ? equals
                edges.remove(i);
                i--;
            } else if (edgeAt(i).getSeccondAdjacent().equals(node)) {
                edges.remove(i);
                i--;
            }
        }
        /* Remove the node from the nodes list */
        return nodes.removeElement(node);
    }

}
