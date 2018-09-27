/*
 * Created on May 10, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

/**
 * @author Cami
 *
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class Test {

    public static void main(String[] args) {
        Node node1 = new Node("n1");
        Node node2 = new Node("n2");
        Node node3 = new Node("n3");

        node1.addAdiacent(node2, new Integer(0), new Integer(3));
        node1.addAdiacent(node3, new Integer(0), new Integer(4));
        node2.addAdiacent(node3, new Integer(0), new Integer(5));

        node1.out("");
    }
}
