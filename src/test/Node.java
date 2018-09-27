/*
 * Created on May 10, 2005
 */
package test;

import java.util.Vector;

/**
 *
 */
public class Node {

    public String eticheta;

    public Vector adiacenti;

    public Node(String eticheta) {
        this.eticheta = eticheta;
        adiacenti = new Vector();
    }

    public void addAdiacent(Node node, Integer curent, Integer max) {
        NodeWraper wraper = new NodeWraper(node, Boolean.TRUE, curent, max);
        adiacenti.addElement(wraper);
        NodeWraper wraper1 = new NodeWraper(this, Boolean.FALSE, curent, max);
        node.adiacenti.addElement(wraper1);
    }

    public void out(String indent) {
        System.out.println(indent + "Node name: " + eticheta);
        for (int i = 0; i < adiacenti.size(); i++) {
            NodeWraper wraper = (NodeWraper) adiacenti.elementAt(i);
            if (wraper.sens.equals(Boolean.TRUE)) {
                System.out.println(indent + " Current: " + wraper.curentCost);
                System.out.println(indent + " Max: " + wraper.maxCost);
                wraper.node.out(indent + " ");
            }
        }
    }

}