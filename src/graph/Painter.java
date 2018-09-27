/*
 * Created on Dec 2, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import graph.graphmodel.Edge;
import graph.graphmodel.Node;
import graph.graphmodel.NodesWays;
import graph.graphview.EdgeX;
import graph.graphview.NodeX;
import graph.graphview.NodesWaysX;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Painter {
    /**
     * Draw a node unselected
     * 
     * @param g
     * @param node
     */
    public static void drawNode(Graphics g, Node node, NodeX nodeX, boolean drawString, NodeX nodeEdit) {
        /* Node's colors */
        Color foreground = new Color(0);
        Color label = new Color(0);

        if (nodeX.getState() == true) {
            foreground = nodeX.getForegroundSelect();
            label = nodeX.getLabelSelect();
        } else {
            foreground = nodeX.getForegroundUnselect();
            label = nodeX.getLabelUnselect();
        }

        /* Draw the node rectangle */
        g.setColor(foreground);
        g.fill3DRect(nodeX.getRectangle().x, nodeX.getRectangle().y, nodeX.getRectangle().width,
                nodeX.getRectangle().height, true);
        /* Draw the node name */
        if (drawString || !nodeX.equals(nodeEdit)) {
            g.setColor(label);
            g.setFont(nodeX.getFont());
            g.drawString(node.getLabel(), nodeX.getNamePosition().x, nodeX.getNamePosition().y);
        }
        /* Draw the resize location */
        g.setColor(label);
        int[] x = { nodeX.getResizePosition().x, nodeX.getResizePosition().x + 12, nodeX.getResizePosition().x + 12 };
        int[] y = { nodeX.getResizePosition().y + 12, nodeX.getResizePosition().y + 12, nodeX.getResizePosition().y };

        g.fillPolygon(x, y, 3);
    }

    // /**
    // * Draw a node selected
    // * @param g
    // * @param node
    // */
    // public static void drawNodeSelected(Graphics g, Node node, NodeX nodeX,
    // boolean drawString, NodeX nodeEdit) {
    // /* Draw the node rectangle */
    // g.setColor(Color.blue);
    // g.fill3DRect(nodeX.getRectangle().x,
    // nodeX.getRectangle().y,
    // nodeX.getRectangle().width,
    // nodeX.getRectangle().height,
    // false);
    // /* Draw the node name */
    // if(drawString || !nodeX.equals(nodeEdit)) {
    // g.setColor(Color.green);
    // g.setFont(nodeX.getFont());
    // g.drawString(node.getLabel(), nodeX.getNamePosition().x,
    // nodeX.getNamePosition().y);
    // }
    // /* Draw the resize location */
    // g.setColor(Color.green);
    // int[] x = {nodeX.getResizePosition().x, nodeX.getResizePosition().x + 12,
    // nodeX.getResizePosition().x + 12};
    // int[] y = {nodeX.getResizePosition().y + 12, nodeX.getResizePosition().y
    // + 12,
    // nodeX.getResizePosition().y};
    //
    // g.fillPolygon(x, y, 3);
    // }

    /**
     * Draw a edge unselected
     * 
     * @param g
     * @param side
     */
    public static void drawEdge(Graphics g, Edge edge, EdgeX edgeX, boolean arrow, boolean drawString, EdgeX edgeEdit) {
        /* Node's colors */
        Color foreground = new Color(0);
        Color label = new Color(0);

        if (edgeX.getState() == true) {
            foreground = edgeX.getForegroundSelect();
            label = edgeX.getLabelSelect();
        } else {
            foreground = edgeX.getForegroundUnselect();
            label = edgeX.getLabelUnselect();
        }

        /* Draw the edge line */
        Point p0 = edgeX.getFirstPoint();
        Point p1 = edgeX.getSeccondPoint();
        Point p2 = edgeX.getNamePosition();
        g.setColor(foreground);
        if (edgeX.splitPointsNumber() > 0) {
            g.drawLine(p0.x, p0.y, edgeX.getFirstSplitPoint().x, edgeX.getFirstSplitPoint().y);
            /* Draw the inner lines */
            if (edgeX.splitPointsNumber() > 1) {
                for (int i = 0; i < edgeX.splitPointsNumber() - 1; i++) {
                    g.fillOval(edgeX.splitPointAt(i).x - 3, edgeX.splitPointAt(i).y - 3, 6, 6);
                    g.drawLine(edgeX.splitPointAt(i).x, edgeX.splitPointAt(i).y, edgeX.splitPointAt(i + 1).x,
                            edgeX.splitPointAt(i + 1).y);
                }
            }

            g.fillOval(edgeX.getLastSplitPoint().x - 3, edgeX.getLastSplitPoint().y - 3, 6, 6);
            g.drawLine(p1.x, p1.y, edgeX.getLastSplitPoint().x, edgeX.getLastSplitPoint().y);
        } else {
            g.drawLine(p0.x, p0.y, p1.x, p1.y);
        }
        /* Draw the arrow */
        if (arrow && !edgeX.isMoving()) {
            int[] x = new int[3];
            int[] y = new int[3];
            int[] z = edgeX.getArrowPoints();
            for (int i = 0; i < 6; i += 2) {
                x[i / 2] = z[i];
                y[i / 2] = z[i + 1];
            }
            g.setColor(foreground);
            g.fillPolygon(x, y, 3);
        }
        /* Draw the name */
        if (drawString || !edgeX.equals(edgeEdit)) {
            g.setColor(label);
            g.setFont(edgeX.getFont());
            g.drawString(edge.getLabel(), p2.x, p2.y);
        }
    }

    // /**
    // * Draw a edge selected
    // * @param g
    // * @param side
    // */
    // public static void drawEdgeSelected(Graphics g, Edge edge, EdgeX edgeX,
    // boolean arrow, boolean drawString, EdgeX edgeEdit) {
    // Point p0 = edgeX.getFirstPoint();
    // Point p1 = edgeX.getSeccondPoint();
    // Point p2 = edgeX.getNamePosition();
    // /* Draw the edge line */
    // g.setColor(Color.red);//new Color(0, 0, 178));
    // if(edgeX.splitPointsNumber() > 0) {
    // g.drawLine(p0.x, p0.y, edgeX.getFirstSplitPoint().x,
    // edgeX.getFirstSplitPoint().y);
    // /* Draw the inner lines */
    // if(edgeX.splitPointsNumber() > 1) {
    // for(int i = 0 ; i < edgeX.splitPointsNumber() - 1; i++) {
    // g.fillOval(edgeX.splitPointAt(i).x - 3, edgeX.splitPointAt(i).y - 3, 6,
    // 6);
    // g.drawLine(edgeX.splitPointAt(i).x, edgeX.splitPointAt(i).y,
    // edgeX.splitPointAt(i + 1).x, edgeX.splitPointAt(i + 1).y);
    // }
    // }
    //
    // g.fillOval(edgeX.getLastSplitPoint().x - 3, edgeX.getLastSplitPoint().y -
    // 3, 6, 6);
    // g.drawLine(p1.x, p1.y, edgeX.getLastSplitPoint().x,
    // edgeX.getLastSplitPoint().y);
    // }
    // else {
    // g.drawLine(p0.x, p0.y, p1.x, p1.y);
    // }
    // /* Draw the arrow */
    // if(arrow && !edgeX.isMoving()) {
    // int[] x = new int[3];
    // int[] y = new int[3];
    // int[] z = edgeX.getArrowPoints();
    // for(int i = 0; i < 6; i += 2) {
    // x[i / 2] = z[i];
    // y[i / 2] = z[i + 1];
    // }
    // g.setColor(Color.red);
    // g.fillPolygon(x, y, 3);
    // }
    // /* Draw the name */
    // if(drawString || !edgeX.equals(edgeEdit)) {
    // g.setColor(new Color(0, 128, 0));
    // g.setFont(edgeX.getFont());
    // g.drawString(edge.getLabel(), p2.x, p2.y);
    // }
    // }

    /**
     * Draw the way indicator normal
     * 
     * @param g
     * @param ways
     * @param waysX
     */
    public static void drawWay(Graphics g, NodesWays ways, NodesWaysX waysX) {
        Point p0 = waysX.getFirstNode().getResizePosition();
        Point p1 = waysX.getSeccondNode().getResizePosition();

        g.setColor(Color.magenta);// new Color(191, 127, 63));
        g.drawLine(p0.x, p0.y, p1.x, p0.y);
        g.drawLine(p1.x, p0.y, p1.x, p1.y);

        g.fillOval(p0.x - 5, p0.y - 5, 10, 10);
        g.fillOval(p1.x - 5, p1.y - 5, 10, 10);
    }

    /**
     * Draw the node; Called in the Mini Map
     * 
     * @param g
     * @param nodeX
     * @param scale
     */
    public static void drawNode(Graphics g, NodeX nodeX, int scale) {
        g.fill3DRect(nodeX.getRectangle().x / scale, nodeX.getRectangle().y / scale, nodeX.getRectangle().width / scale,
                nodeX.getRectangle().height / scale, true);
    }

    /**
     * Draw the edge; Called in Mini Map
     * 
     * @param g
     * @param edgeX
     * @param scale
     */
    public static void drawEdge(Graphics g, EdgeX edgeX, int scale) {
        Point p0 = edgeX.getFirstPoint();
        Point p1 = edgeX.getSeccondPoint();

        /* Draw the edge line */
        if (edgeX.splitPointsNumber() > 0) {
            g.drawLine(p0.x / scale, p0.y / scale, edgeX.getFirstSplitPoint().x / scale,
                    edgeX.getFirstSplitPoint().y / scale);
            /* Draw the inner lines */
            if (edgeX.splitPointsNumber() > 1) {
                for (int i = 0; i < edgeX.splitPointsNumber() - 1; i++) {
                    g.drawLine(edgeX.splitPointAt(i).x / scale, edgeX.splitPointAt(i).y / scale,
                            edgeX.splitPointAt(i + 1).x / scale, edgeX.splitPointAt(i + 1).y / scale);
                }
            }

            g.drawLine(p1.x / scale, p1.y / scale, edgeX.getLastSplitPoint().x / scale,
                    edgeX.getLastSplitPoint().y / scale);
        } else {
            g.drawLine(p0.x / scale, p0.y / scale, p1.x / scale, p1.y / scale);
        }

    }

}
