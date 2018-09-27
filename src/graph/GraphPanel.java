/*
 * Created on Dec 2, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

import graph.graphmodel.Edge;
import graph.graphmodel.Graph;
import graph.graphmodel.GraphOriented;
import graph.graphmodel.GraphUnoriented;
import graph.graphmodel.Node;
import graph.graphmodel.Way;
import graph.graphview.EdgeX;
import graph.graphview.GraphX;
import graph.graphview.MyMath;
import graph.graphview.NodeX;
import graph.tool.EdgeTool;
import graph.tool.NodeTool;
import graph.tool.SelectTool;
import graph.tool.WayTool;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GraphPanel extends Panel
        implements GraphEditor, MouseListener, MouseMotionListener, ActionListener, KeyListener, Serializable {

    /**
     * The min width of the panel
     */
    public final static int MIN_WIDTH = 960;

    /**
     * The min width of the panel
     */
    public final static int MAX_WIDTH = 1920;

    /**
     * The min width of the panel
     */
    public final static int MIN_HEIGHT = 960;

    /**
     * The min width of the panel
     */
    public final static int MAX_HEIGHT = 1920;

    /**
     * Index for select tool (virtual tool)
     */
    public final static int SELECT_TOOL = 0;

    /**
     * Index for create node tool
     */
    public final static int NODE_TOOL = 1;

    /**
     * Index for create edge tool
     */
    public final static int EDGE_TOOL = 2;

    /**
     * Index for create edge tool
     */
    public final static int WAY_TOOL = 3;

    // /**
    // * Index for create split points
    // * (this tool doesn't exist - only the index is important)
    // */
    // public final static int SPLIT_TOOL = 4;

    /**
     * Index of the unoriented graph
     */
    public final static int GRAPH_UNORIENTED = 0;

    /**
     * Index of the oriented graph
     */
    public final static int GRAPH_ORIENTED = 1;

    /**
     * Click on a node
     */
    public final static int NODE = 1;

    /**
     * Click on an edge
     */
    public final static int EDGE = 2;

    /**
     * The logic design of the graph
     */
    private Graph graph;

    /**
     * The graphic design of the graph
     */
    private GraphX graphX;

    /**
     * Image of the panel
     */
    private Image image;

    /**
     * The image graphics
     */
    private Graphics g1;

    /**
     * Dimension of the panel
     */
    private Dimension dim;

    /**
     * The select tool
     */
    private SelectTool selectTool;

    /**
     * The node tools
     */
    private NodeTool nodeTool;

    /**
     * The edge tools
     */
    private EdgeTool edgeTool;

    /**
     * The way tool
     */
    private WayTool wayTool;

    /**
     * The index of the tool (0, 1) (-1 if no tool)
     */
    private int indexTool;

    /**
     * Keep the ctrl key state
     */
    private boolean ctrlPressed;

    /**
     * Keep the ctrl key state
     */
    private boolean shiftPressed;

    /**
     * Drag multiple selection - true
     */
    private boolean dragMultiple;

    /**
     * True if the selected nodes are moving Set true in pressedMouse and set
     * false in releasedMouse
     */
    private boolean moveingGraph;

    /**
     * The edit state of the node's and edge's label
     */
    private boolean editNode;

    /**
     * The edit state of the node's and edge's label
     */
    private boolean editEdge;

    /**
     * The node that will have the label changed
     */
    private NodeX nodeEdit;

    /**
     * The edge that will have the label changed
     */
    private EdgeX edgeEdit;

    /**
     * The editing buffer
     */
    private StringBuffer buffer;

    /**
     * The visible rectangle of the panel (Origin point - (0, 0))
     */
    private Rectangle scrollRectangle;

    /**
     * The state of scrolling on mouse drag
     */
    private boolean dragScroll;

    /**
     * The node options menu
     */
    private PopupMenu nodePopupMenu;

    /**
     * The menu items of the node
     */
    private MenuItem[] nodeMenuItem;

    /**
     * The edge options menu
     */
    private PopupMenu edgePopupMenu;

    /**
     * The menu items of the node
     */
    private MenuItem[] edgeMenuItem;

    /**
     * Appear when right click on way
     */
    private PopupMenu wayPopupMenu;

    /**
     * The menu items of the popup menu
     */
    private MenuItem[] wayMenuItem;

    /**
     * The type of the selected item(s) Multiple selection - itemType = NODE
     */
    private int itemType;

    /**
     * The location of the edge popup to know where to create the split point
     */
    private Point splitPointLocation;

    /**
     * Default constructor
     *
     */
    public GraphPanel(int tool) {
        super();
        graph = new GraphOriented();
        graphX = new GraphX();

        dim = new Dimension(-1, -1);

        selectTool = new SelectTool();
        nodeTool = new NodeTool();
        edgeTool = new EdgeTool();
        wayTool = new WayTool();

        indexTool = tool;

        ctrlPressed = false;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);

        /* Init menus */
        initNodePopup();
        initEdgePopup();
        initWayPopup();

        editNode = false;
        editEdge = false;
        buffer = new StringBuffer();

        scrollRectangle = new Rectangle();
    }

    /**
     * Init the popups for the nodes
     *
     */
    private void initNodePopup() {
        nodeMenuItem = new MenuItem[3];
        nodeMenuItem[0] = new MenuItem("Select All");
        nodeMenuItem[1] = new MenuItem("Edit Label");
        nodeMenuItem[2] = new MenuItem("Properties");

        nodePopupMenu = new PopupMenu();

        nodePopupMenu.add(nodeMenuItem[0]);
        nodePopupMenu.addSeparator();
        nodePopupMenu.add(nodeMenuItem[1]);
        nodePopupMenu.add(nodeMenuItem[2]);

        nodePopupMenu.addActionListener(this);

        this.add(nodePopupMenu);
    }

    /**
     * Init the popups for the edges
     *
     */
    private void initEdgePopup() {
        edgeMenuItem = new MenuItem[4];
        edgeMenuItem[0] = new MenuItem("Select All");
        edgeMenuItem[1] = new MenuItem("Create Split Point");
        edgeMenuItem[2] = new MenuItem("Edit Label");
        edgeMenuItem[3] = new MenuItem("Properties");

        edgePopupMenu = new PopupMenu();

        edgePopupMenu.add(edgeMenuItem[0]);
        edgePopupMenu.addSeparator();
        edgePopupMenu.add(edgeMenuItem[1]);
        edgePopupMenu.add(edgeMenuItem[2]);
        edgePopupMenu.add(edgeMenuItem[3]);

        edgePopupMenu.addActionListener(this);

        this.add(edgePopupMenu);
    }

    /**
     * Init the popup of the way
     *
     */
    private void initWayPopup() {
        wayMenuItem = new MenuItem[5];
        wayMenuItem[0] = new MenuItem("Minim path");
        wayMenuItem[1] = new MenuItem("Maxim path");
        wayMenuItem[2] = new MenuItem("Next path");
        wayMenuItem[3] = new MenuItem("Prev path");
        wayMenuItem[4] = new MenuItem("Select all");

        /* Init the popup */
        wayPopupMenu = new PopupMenu();

        wayPopupMenu.add(wayMenuItem[0]);
        wayPopupMenu.add(wayMenuItem[1]);
        wayPopupMenu.addSeparator();
        wayPopupMenu.add(wayMenuItem[2]);
        wayPopupMenu.add(wayMenuItem[3]);
        wayPopupMenu.addSeparator();
        wayPopupMenu.add(wayMenuItem[4]);

        wayPopupMenu.addActionListener(this);

        this.add(wayPopupMenu);
    }

    /**
     * Set a new graph
     *
     */
    public void newGraph(int type) {
        graph.getNodes().removeAllElements();
        graph.getEdges().removeAllElements();

        graphX.getNodes().removeAllElements();
        graphX.getEdges().removeAllElements();

        switch (type) {
            case GRAPH_UNORIENTED:
                graph = new GraphUnoriented();
                break;
            case GRAPH_ORIENTED:
                graph = new GraphOriented();
                break;
        }

        repaintGraph();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent e) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
     */
    public void keyTyped(KeyEvent e) {
        // System.out.println("Type: " + e);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        if (indexTool == SELECT_TOOL && !editEdge && !editNode) {
            if (graphX.overSplitPoints(e.getPoint())) {
                this.setCursor(GraphBuilder.move);
            } else if (graphX.nodesResizeContains(e.getPoint()) && !ctrlPressed) {
                this.setCursor(GraphBuilder.resizeSE);
            } else if (graphX.nodesContains(e.getPoint())
                    || graphX.edgesContains(e.getPoint(), graph instanceof GraphOriented)
                    || edgeFromLabel(e.getPoint()) != null) {
                this.setCursor(GraphBuilder.hand);
            } else {
                /* Set no split point for the edges */
                for (int i = 0; i < graphX.edgesNumber(); i++) {
                    graphX.edgeAt(i).setNoCurrentPoint();
                }
                this.setCursor(GraphBuilder.arrow);
            }
        } else {
            this.setCursor(GraphBuilder.arrow);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getModifiers() >= MouseEvent.BUTTON1_MASK && !ctrlPressed) {
            /* Edit a node's label */
            if (graphX.isOneNodeSelected()) {
                nodeEdit = graphX.getSelectedNode();
                // if(getLabelBounds(nodeOf(nodeEdit),
                // nodeEdit).contains(e.getPoint())) {
                if (nodeEdit.getRectangle().contains(e.getPoint())) {
                    buffer.setLength(0);
                    buffer.append(nodeOf(nodeEdit).getLabel());
                    editNode = true;
                }
            }
            /* Edit a edge's label */
            else if (graphX.isOneEdgeSelected()) {
                edgeEdit = graphX.getSelectedEdge();
                if (getLabelBounds(edgeOf(edgeEdit), edgeEdit).contains(e.getPoint())
                        || edgeEdit.edgeContains(e.getPoint())) {
                    buffer.setLength(0);
                    buffer.append(edgeOf(edgeEdit).getLabel());
                    editEdge = true;
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        /* Return if a label is editing */
        if (editNode || editEdge) {
            return;
        }

        /* Work directly with a tool */
        switch (indexTool) {
            case SELECT_TOOL:
                pressedSelect(e);
                break;
            case NODE_TOOL:
                pressedNode(e);
                break;
            case EDGE_TOOL:
                pressedEdge(e);
                break;
            case WAY_TOOL:
                pressedWay(e);
                break;
        }

        repaintGraph();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.
     * MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        dragScroll = false;
        /* Return if a label is editing */
        if (editNode || editEdge) {
            return;
        }

        /* Set the scroll rectangle */
        scrollRectangle.setLocation(scrollPane().getScrollPosition());
        scrollRectangle.setSize(scrollPane().getViewportSize());

        if (!scrollRectangle.contains(e.getPoint())) {
            /* Scroll position */
            Point position = scrollPane().getScrollPosition();

            /* Right */
            if (position.x + scrollPane().getViewportSize().width < e.getPoint().x) {
                position.x = e.getPoint().x - scrollPane().getViewportSize().width;
            }
            /* Down */
            if (position.y + scrollPane().getViewportSize().height < e.getPoint().y) {
                position.y = e.getPoint().y - scrollPane().getViewportSize().height;
            }
            /* Left */
            if (e.getPoint().x < scrollPane().getScrollPosition().x) {
                position.x = e.getPoint().x;
            }
            /* Up */
            if (e.getPoint().y < scrollPane().getScrollPosition().y) {
                position.y = e.getPoint().y;
            }

            dragScroll = true;
            scrollPane().setScrollPosition(position);
        }

        switch (indexTool) {
            case SELECT_TOOL:
                draggedSelect(e);
                break;
            case NODE_TOOL:
                draggedNode(e);
                break;
            case EDGE_TOOL:
                draggedEdge(e);
                break;
            case WAY_TOOL:
                draggedWay(e);
                break;
        }

        repaintGraph();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        /* Return if a label is editing */
        if (editNode || editEdge) {
            return;
        }

        switch (indexTool) {
            case SELECT_TOOL:
                releasedSelect(e);
                break;
            case NODE_TOOL:
                releasedNode(e);
                break;
            case EDGE_TOOL:
                releasedEdge(e);
                break;
            case WAY_TOOL:
                releasedWay(e);
                break;
        }

        graphX.computeDimension();

        Point point = scrollPane().getScrollPosition();

        int height = (int) scrollPane().getViewportSize().getHeight();
        int width = (int) scrollPane().getViewportSize().getWidth();

        if (graphX.getDimension().width > width) {
            point.x -= graphX.getMinPoint().x;
        }
        if (graphX.getDimension().height > height) {
            point.y -= graphX.getMinPoint().y;
        }

        scrollPane().setScrollPosition(point);
        scrollPane().doLayout();

        dragScroll = false;

        repaintGraph();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
     */
    public void keyPressed(KeyEvent e) {
        // System.out.println(e.getKeyCode());
        /* Press shift */
        if (e.getKeyCode() == 16) {
            shiftPressed = true;
        }

        /* Edit the label */
        if (editNode || editEdge) {
            /* Fill the buffer with letters */
            if (e.getKeyCode() >= 65 && e.getKeyCode() <= 90) {
                /* Upper Case */
                if (shiftPressed) {
                    buffer.append((char) e.getKeyCode());
                }
                /* Lower Case */
                else {
                    buffer.append((char) (e.getKeyCode() + 32));
                }
            }
            /* The others characters */
            else if (e.getKeyCode() >= 32 && e.getKeyCode() <= 126) {
                // TODO not necessairly but, put the shift
                buffer.append((char) e.getKeyCode());
            }
            /* Backspace */
            else if (e.getKeyCode() == 8 && buffer.length() > 0) {
                buffer.setLength(buffer.length() - 1);
            }
            /* Enter */
            else if (e.getKeyCode() == 10) {
                if (editNode) {
                    nodeOf(nodeEdit).setLabel(buffer.toString());
                    editNode = false;
                } else if (editEdge) {
                    edgeOf(edgeEdit).setLabel(buffer.toString());
                    editEdge = false;
                }
            } else if (e.getKeyCode() == 27) {
                if (editNode) {
                    editNode = false;
                } else if (editEdge) {
                    editEdge = false;
                }
            }

            /* Resize the node */
            if (editNode) {
                resizeEditNode(buffer);
            }

            repaintGraph();
            return;
        }

        switch (e.getKeyCode()) {
            case 17: // Ctrl
                ctrlPressed = true;
                break;
            case 127: // Delete
                deleteSelection();
                break;
            case 78: // N
                indexTool = NODE_TOOL;
                ((GraphBuilder) getFrameParent()).setCheckTool(1);
                break;
            case 69: // E
                indexTool = EDGE_TOOL;
                ((GraphBuilder) getFrameParent()).setCheckTool(2);
                break;
            case 83: // S
                indexTool = SELECT_TOOL;
                ((GraphBuilder) getFrameParent()).setCheckTool(0);
                break;
            case 87: // W
                if (ctrlPressed) {
                    NodeX nodeX = graphX.getSelectedNode();
                    graphX.deselectAll();
                    graphX.nodeAt((graphX.getNodeIndex(nodeX) + 1) % graphX.nodesNumber()).setState(true);
                }
                break;
            case 80: // P
                indexTool = WAY_TOOL;
                ((GraphBuilder) getFrameParent()).setCheckTool(3);
                break;
            case 68: // D
                if (ctrlPressed) {
                    graphX.deselectAll();
                }
                break;
            case 65: // A
                if (ctrlPressed) {
                    graphX.selectNodes();
                    graphX.selectEdgesFromNodes();
                }
                break;
            case 37: // Left
                // TODO Move the selection
                break;
            case 39: // Right

                break;
            case 38: // Up

                break;
            case 40: // Down

                break;

        }

        repaintGraph();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 16: // Shift
                shiftPressed = false;
                break;
            case 17: // Ctrl
                ctrlPressed = false;
                break;
        }

        repaintGraph();
    }

    /**
     * Paint the panel
     */
    public void paint(Graphics g) {

        /* Init the image */
        if (!dim.equals(this.getSize())) {
            dim = this.getSize();
            image = this.createImage(dim.width, dim.height);
            g1 = image.getGraphics();

            this.requestFocus();
        }

        g1.setColor(Color.white);
        g1.fillRect(0, 0, dim.width, dim.height);

        putSelectionInFront();

        /* Draw the edges */
        for (int i = 0; i < graph.edgesNumber(); i++) {
            if (graphX.edgeAt(i).getState() == false) {
                Painter.drawEdge(g1, graph.edgeAt(i), graphX.edgeAt(i), graph instanceof GraphOriented, !editEdge,
                        edgeEdit);
            }
        }
        /* Draw the selected edges */
        for (int i = 0; i < graph.edgesNumber(); i++) {
            if (graphX.edgeAt(i).getState() == true) {
                Painter.drawEdge(g1, graph.edgeAt(i), graphX.edgeAt(i), graph instanceof GraphOriented, !editEdge,
                        edgeEdit);
            }
        }
        /* Draw the nodes */
        for (int i = 0; i < graph.nodesNumber(); i++) {
            if (graphX.nodeAt(i).getState() == false) {
                Painter.drawNode(g1, graph.nodeAt(i), graphX.nodeAt(i), !editNode, nodeEdit);
            }
        }
        /* Draw the selected nodes */
        for (int i = 0; i < graph.nodesNumber(); i++) {
            if (graphX.nodeAt(i).getState() == true) {
                Painter.drawNode(g1, graph.nodeAt(i), graphX.nodeAt(i), !editNode, nodeEdit);
            }
        }

        /* Draw the edit buffer */
        if (editNode) {
            Point p = nodeEdit.getNamePosition();
            g1.setColor(Color.magenta);
            g1.setFont(nodeEdit.getFont());
            g1.drawString(buffer.toString(), p.x, p.y);
            /* Draw the line cursor */
            Rectangle label = MyMath.stringBounds(buffer.toString(), nodeEdit.getFont());
            g1.drawLine(nodeEdit.getRectangle().x + label.x + label.width + 5, nodeEdit.getRectangle().y + label.y + 14,
                    nodeEdit.getRectangle().x + label.x + label.width + 5,
                    nodeEdit.getRectangle().y + label.y + label.height + 12);
        } else if (editEdge) {
            Point p = edgeEdit.getNamePosition();
            g1.setColor(Color.magenta);
            g1.setFont(edgeEdit.getFont());
            g1.drawString(buffer.toString(), p.x, p.y);
            /* Draw the line cursor */
            Rectangle label = MyMath.stringBounds(buffer.toString(), edgeEdit.getFont());
            g1.drawLine(edgeEdit.getNamePosition().x + label.x + label.width, edgeEdit.getNamePosition().y + label.y,
                    edgeEdit.getNamePosition().x + label.x + label.width,
                    edgeEdit.getNamePosition().y + label.y + label.height - 1);
        }

        /* Draw the ways between two nodes */
        if (graph instanceof GraphOriented) {
            try {
                /* Draw the ways normal */
                if (graphX.getWays().getState() == true) {
                    Painter.drawWay(g1, ((GraphOriented) graph).getWays(), graphX.getWays());
                }
            } catch (NullPointerException e) {
            }
        }

        /* Draw the tool */
        switch (indexTool) {
            case SELECT_TOOL:
                if (selectTool.isMultiple() && selectTool.getDrawState()) {
                    selectTool.draw(g1);
                }
                break;
            case NODE_TOOL:
                if (nodeTool.getDrawState()) {
                    nodeTool.draw(g1);
                }
                break;
            case EDGE_TOOL:
                if (edgeTool.getDrawState()) {
                    edgeTool.draw(g1);
                }
                break;
            case WAY_TOOL:
                if (wayTool.getDrawState()) {
                    wayTool.draw(g1);
                }
                break;
        }

        g.drawImage(image, 0, 0, this);
    }

    /**
     * Update the panel (double buffering)
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Mouse pressed when select tool
     * 
     * @param e
     */
    private void pressedSelect(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            /* Click on a node */
            if (graphX.nodesContains(e.getPoint())) {
                /* Resize the selected node */
                if (graphX.isOneNodeSelected() && !ctrlPressed
                        && graphX.getSelectedNode().getResizeRectangle().contains(e.getPoint())) {
                    NodeX nodeX = graphX.getSelectedNode();
                    nodeX.setResize(true);
                    nodeX.setState(true);
                    nodeX.setRelativePosition(e.getPoint());
                }
                /* Click to resize a node */
                else if (graphX.getNode(e.getPoint()).getResizeRectangle().contains(e.getPoint()) && !ctrlPressed) {
                    /* Put the node over the others */
                    graphX.selectNode(e.getPoint());

                    graphX.deselectAll();
                    graphX.getNode(e.getPoint()).setResize(true);
                    graphX.getNode(e.getPoint()).setState(true);
                    graphX.getNode(e.getPoint()).setRelativePosition(e.getPoint());

                    selectTool.setMultiple(false);
                    selectTool.reset();
                }
                /* Click to move (select) a node or mode nodes */
                else {
                    NodeX nodeX;
                    if ((nodeX = graphX.getNode(e.getPoint())) != null) {
                        if (ctrlPressed == false && nodeX.getState() == false) {
                            graphX.deselectAll();
                        }
                        /* Deselect the node */
                        if (ctrlPressed == true && nodeX.getState() == true) {
                            graphX.deselectNode(nodeX);
                            graphX.selectEdgesFromNodes();
                        }
                        /* Select the node */
                        else {
                            // graphX.deselectEdges();
                            graphX.selectNode(nodeX);
                            graphX.selectEdgesFromNodes();
                        }

                        /* Set the relative position at the selected nodes */
                        for (int i = 0; i < graphX.nodesNumber(); i++) {
                            if (graphX.nodeAt(i).getState()) {
                                moveingGraph = true;
                                graphX.nodeAt(i).setRelativePositionTo(e.getPoint());
                            }
                        }
                        for (int i = 0; i < graphX.edgesNumber(); i++) {
                            if (graphX.edgeAt(i).getState()) {
                                graphX.edgeAt(i).setRelativePositionsTo(e.getPoint());
                            }
                        }

                        dragMultiple = false;
                    }
                }
            }
            /* Click on an edge */
            else if ((graphX.edgesContains(e.getPoint(), graph instanceof GraphOriented)
                    || graphX.overSplitPoints(e.getPoint()) || edgeFromLabel(e.getPoint()) != null)
                    && ctrlPressed == false) {
                deleteWays();

                EdgeX edgeX = graphX.getEdge(e.getPoint());
                if (edgeX != null) {
                    graphX.deselectAll();
                    edgeX.setState(true);
                } else {
                    try {
                        edgeX = edgeFromLabel(e.getPoint());
                        graphX.deselectAll();
                        edgeX.setState(true);
                    } catch (NullPointerException e1) {
                    }
                }
                // /* Set the split point if pressed on it */
                // graphX.getSelectedEdge().selectSplitPoint(e.getPoint());
            }
            /* Click on background */
            else {
                selectTool.setMultiple(true);
                selectTool.setFirstPoint(e.getPoint());
                /* Deselect all the nodes and edges */
                if (ctrlPressed == false) {
                    graphX.deselectAll();
                    /* Delete the ways */
                    deleteWays();
                }
            }
        } else if (e.getModifiers() >= MouseEvent.BUTTON3_MASK) {
            NodeX nodeX = graphX.getNode(e.getPoint());
            EdgeX edgeX = graphX.getEdge(e.getPoint());

            if (nodeX != null) {
                itemType = NODE;

                /* Select the node */
                graphX.deselectAll();
                nodeX.setState(true);

                nodePopupMenu.show(this, e.getPoint().x, e.getPoint().y);
            } else if (edgeX != null) {
                /* Select the edge */
                graphX.deselectAll();
                edgeX.setState(true);

                if (ctrlPressed) {
                    edgeX.deleteSplitPoint(e.getPoint());
                } else {
                    itemType = EDGE;
                    edgePopupMenu.show(this, e.getPoint().x, e.getPoint().y);
                    splitPointLocation = e.getPoint();
                }

                // System.out.println(edgePopupMenu);
                // if(ctrlPressed) {
                // edgeX.deleteSplitPoint(e.getPoint());
                // }
                // else {
                // edgeX.addSplitPoint(e.getPoint());
                // }
            }
        }
    }

    /**
     * Mouse dragged when select tool
     * 
     * @param e
     */
    private void draggedSelect(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            /* Rebuild the selected area */
            if (selectTool.isMultiple()) {
                selectTool.setSeccondPoint(e.getPoint());
            } else if (ctrlPressed == false) {
                /* Nodes (Resize & Move) */
                try {
                    /* Resize the selected node (throw NullPointerException) */
                    if (graphX.isOneNodeSelected() && graphX.getSelectedNode().isResize()) {
                        graphX.getSelectedNode().computeRectangleDimension(e.getPoint());
                    }
                    /* Move the selected nodes */
                    else {
                        if (moveingGraph) {
                            for (int i = 0; i < graphX.nodesNumber(); i++) {
                                if (graphX.nodeAt(i).getState()) {
                                    graphX.nodeAt(i).computeRectanglePosition(e.getPoint());
                                }
                            }
                            for (int i = 0; i < graphX.edgesNumber(); i++) {
                                if (graphX.edgeAt(i).getState()) {
                                    graphX.edgeAt(i).computePositions(e.getPoint());
                                }
                            }
                            graphX.computeDimension();
                        }
                        dragMultiple = true;
                    }
                } catch (NullPointerException e1) {
                }

                /* Move an edge */
                EdgeX edgeX = graphX.getSelectedEdge();

                /* Move a split point */
                if (!ctrlPressed) {
                    if (this.getCursor().equals(GraphBuilder.move)) {
                        edgeX.changeSplitPoint(e.getPoint());
                    }
                }

                boolean move = false;
                try {
                    move = graphX.getWays().getState();
                } catch (NullPointerException e1) {
                }
                if (edgeX != null && move == false) {
                    if (edgeX.isMoving() == false && moveingGraph == false) {
                        edgeX.setMoveState(true);
                        edgeX.setPointToMove(e.getPoint());
                    }
                    edgeX.setPoint(e.getPoint());
                }
            }
        } else if (e.getModifiers() >= MouseEvent.BUTTON3_MASK) {
        }
    }

    /**
     * Mouse released when edge tool
     * 
     * @param e
     */
    private void releasedSelect(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            /* End the move (do not need any recourse) or resize the node */
            try {
                /* End the move of the edge. Set the new adjacents */
                if (graphX.isAnyEdgeSelected() && ctrlPressed == false && moveingGraph == false) {
                    EdgeX edgeX = graphX.getSelectedEdge();
                    if (graphX.nodesContains(e.getPoint())) {
                        NodeX nodeX = graphX.getNode(e.getPoint());

                        boolean exist = false;
                        /* Verify if the edge exist */
                        if (edgeX.getPointToMove() == EdgeX.FIRST_POINT) {
                            for (int i = 0; i < graphX.edgesNumber(); i++) {
                                if (graphX.isEdgeInList(nodeX, edgeX.getSeccondAdjacent()) == true) {
                                    exist = true;
                                    break;
                                }
                            }
                        } else if (edgeX.getPointToMove() == EdgeX.SECCOND_POINT) {
                            for (int i = 0; i < graphX.edgesNumber(); i++) {
                                if (graphX.isEdgeInList(edgeX.getFirstAdjacent(), nodeX) == true) {
                                    exist = true;
                                    break;
                                }
                            }
                        }
                        /* Set the new edge only if it doesn't exist */
                        if (!edgeX.getFirstAdjacent().equals(nodeX) && !edgeX.getSeccondAdjacent().equals(nodeX)
                                && exist == false) {
                            Edge edge = edgeOf(edgeX);

                            deleteEdge(); // Delete the selected edge

                            if (edgeX.getPointToMove() == EdgeX.SECCOND_POINT) {
                                edge.setSeccondAdjacent(nodeOf(graphX.getNode(e.getPoint())));
                                edgeX.setSeccondAdjacent(graphX.getNode(e.getPoint()));
                                addEdge(edge, edgeX);
                            } else if (edgeX.getPointToMove() == EdgeX.FIRST_POINT) {
                                edge.setFirstAdjacent(nodeOf(graphX.getNode(e.getPoint())));
                                edgeX.setFirstAdjacent(graphX.getNode(e.getPoint()));
                                addEdge(edge, edgeX);
                            }

                            edgeX = graphX.edgeAt(graphX.edgesNumber() - 1);
                            edgeX.setState(true);
                        }
                    }
                    edgeX.setPointToMove(EdgeX.NONE_POINT);
                    edgeX.setMoveState(false);
                }
                /* End the resize of the node */
                else if (graphX.isOneNodeSelected()) {
                    if (graphX.getSelectedNode().isResize()) {
                        graphX.getSelectedNode().setResize(false);
                    }
                }
                /* Select only one node from the multiple selection */
                else {
                    if (ctrlPressed == false && dragMultiple == false) {
                        graphX.deselectAll();
                        graphX.selectNode(e.getPoint());
                    }
                }
            } catch (NullPointerException e1) {
            }
            /* Select multiple nodes */
            if (selectTool.isMultiple()) {
                selectTool.setSeccondPoint(e.getPoint());
                graphX.selectNodes(selectTool.getRectangle());
                graphX.selectEdgesFromNodes();
            }

            selectTool.setMultiple(false);
            selectTool.reset();

            moveingGraph = false;

            /* Set the cursor */
            if (graphX.nodesResizeContains(e.getPoint()) && graphX.isOneNodeSelected() && !ctrlPressed) {
                this.setCursor(GraphBuilder.resizeSE);
            } else if (graphX.overSplitPoints(e.getPoint())) {
                this.setCursor(GraphBuilder.move);
            } else if (graphX.nodesContains(e.getPoint())
                    || graphX.edgesContains(e.getPoint(), graph instanceof GraphOriented)) {
                this.setCursor(GraphBuilder.hand);
            } else {
                /* Set no split point for the edges */
                for (int i = 0; i < graphX.edgesNumber(); i++) {
                    graphX.edgeAt(i).setNoCurrentPoint();
                }
                this.setCursor(GraphBuilder.arrow);
            }

        } else if (e.getModifiers() >= MouseEvent.BUTTON3_MASK) {
            try {
                graphX.getSelectedEdge().setNoCurrentPoint();
            } catch (NullPointerException e1) {
            }
        }
    }

    /**
     * Mouse pressed when node tool
     * 
     * @param e
     */
    private void pressedNode(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            /* Set the first point in the tool */
            nodeTool.setFirstPoint(e.getPoint());
        }
    }

    /**
     * Moude dragged when node tool
     * 
     * @param e
     */
    private void draggedNode(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            nodeTool.setSeccondPoint(e.getPoint());
        }
    }

    /**
     * Mouse released when node tool
     * 
     * @param e
     */
    private void releasedNode(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            nodeTool.setSeccondPoint(e.getPoint());
            /*
             * Verify if the pressed was on backgroun and if the released is on
             * background
             */
            if (nodeTool.getDrawState() == true) {// &&
                nodeTool.setSeccondPoint(e.getPoint());
                addNode(nodeTool.getRectangle());
            }
            /* Reset the tool in any case */
            nodeTool.reset();
        }
    }

    /**
     * Mouse pressed when edge tool
     * 
     * @param e
     */
    private void pressedEdge(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            /* Click on a node */
            if (graphX.nodesContains(e.getPoint()) == true) {
                /* Set the first point in the tool */
                edgeTool.setFirstPoint(e.getPoint());
            }
        }
    }

    /**
     * Moude dragged when edge tool
     * 
     * @param e
     */
    private void draggedEdge(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            edgeTool.setSeccondPoint(e.getPoint());
        }
    }

    /**
     * Mouse released when edge tool
     * 
     * @param e
     */
    private void releasedEdge(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            if (edgeTool.getDrawState() == true && graphX.nodesContains(e.getPoint()) == true) {
                edgeTool.setSeccondPoint(e.getPoint());

                addEdge(graphX.getNode(edgeTool.getFirstPoint()), graphX.getNode(edgeTool.getSeccondPoint()));
            }
            /* Reset the tool in any case */
            edgeTool.reset();
        }
    }

    /**
     * Mouse pressed when edge tool
     * 
     * @param e
     */
    private void pressedWay(MouseEvent e) {
        if (graph instanceof GraphUnoriented) {
            return;
        }
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            /* Delete the previous ways */
            graphX.deleteWays();

            /* Click on a node */
            if (graphX.nodesContains(e.getPoint()) == true) {
                ((GraphOriented) graph).deleteWays();
                /* Set the first point in the tool */
                wayTool.setFirstPoint(e.getPoint());
            }
        } else if (e.getModifiers() >= MouseEvent.BUTTON3_MASK) {
            try {
                if (graphX.getWays().getState() == true) {
                    wayPopupMenu.show(this, e.getPoint().x, e.getPoint().y);
                }
            } catch (NullPointerException e1) {
            }
        }
    }

    /**
     * Moude dragged when edge tool
     * 
     * @param e
     */
    private void draggedWay(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            wayTool.setSeccondPoint(e.getPoint());
        }
    }

    /**
     * Mouse released when edge tool
     * 
     * @param e
     */
    private void releasedWay(MouseEvent e) {
        if (e.getModifiers() >= MouseEvent.BUTTON1_MASK) {
            if (wayTool.getDrawState() == true && graphX.nodesContains(e.getPoint()) == true) {
                wayTool.setSeccondPoint(e.getPoint());

                if (addWay(graphX.getNode(wayTool.getFirstPoint()), graphX.getNode(wayTool.getSeccondPoint()))) {
                    selectWays();
                }

            }
            /* Reset the tool in any case */
            wayTool.reset();
        }
    }

    /**
     * Delete the selected nodes and edges
     *
     */
    public void deleteSelection() {
        /* Delete the node */
        try {
            deleteNode();
        } catch (NullPointerException e1) {
        }
        /* Delete the edge */
        try {
            deleteEdge();
        } catch (NullPointerException e2) {
        }
        /* Reload the scroll */
        scrollPane().doLayout();
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#addEdge(graph.logicGraph.Edge, graph.drawGraph.EdgeX)
     */
    public void addEdge(Edge edge, EdgeX edgeX) {
        /* Add a new edge only if the two nodes doesn't contains each other */
        if (graph.addEdge(edge.getLabel(), edge.getFirstAdjacent(), edge.getSeccondAdjacent())) {
            graphX.addEdge(edgeX);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#addEdge(graph.drawGraph.NodeX, graph.drawGraph.NodeX)
     */
    public void addEdge(NodeX nodeX0, NodeX nodeX1) {
        /* The node of the new edge */
        Node node0 = graph.nodeAt(graphX.getNodeIndex(nodeX0));
        Node node1 = graph.nodeAt(graphX.getNodeIndex(nodeX1));
        /* Add a new edge only if the two nodes doesn't contains each other */
        if (graph.addEdge(node0, node1)) {
            graphX.addEdge(nodeX0, nodeX1);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#addEdge(java.lang.String, graph.drawGraph.NodeX,
     * graph.drawGraph.NodeX)
     */
    public void addEdge(String label, NodeX nodeX0, NodeX nodeX1) {
        /* The node of the new edge */
        Node node0 = graph.nodeAt(graphX.getNodeIndex(nodeX0));
        Node node1 = graph.nodeAt(graphX.getNodeIndex(nodeX1));
        /* Add a new edge only if the two nodes doesn't contains each other */
        if (graph.addEdge(label, node0, node1)) {
            graphX.addEdge(nodeX0, nodeX1);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#addNode(java.awt.Rectangle)
     */
    public void addNode(Rectangle rectangle) {
        graph.addNode();
        graphX.addNode(rectangle);
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#deleteEdge()
     */
    public void deleteEdge() {
        Edge edge = graph.edgeAt(graphX.getSelectedEdgeIndex());
        graphX.deleteEdge(graphX.getSelectedEdge());
        graph.deleteEdge(edge);
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#deleteNode()
     */
    public void deleteNode() {
        int nodesNumber = graphX.nodesNumber();
        Node node;
        for (int i = 0; i < nodesNumber; i++) {
            if (graphX.nodeAt(i).getState()) {
                node = graph.nodeAt(graphX.getNodeIndex(graphX.nodeAt(i)));
                graphX.deleteNode(graphX.nodeAt(i));
                graph.deleteNode(node);
                nodesNumber--;
                i--;
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#addWay(graph.drawGraph.NodeX, graph.drawGraph.NodeX)
     */
    public boolean addWay(NodeX nodeX0, NodeX nodeX1) {
        Node node0 = nodeOf(nodeX0);
        Node node1 = nodeOf(nodeX1);

        if (graph instanceof GraphOriented) {
            if (((GraphOriented) graph).addWays(node0, node1)) {
                graphX.setWays(nodeX0, nodeX1);
                return true;
            }
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#deleteWay(graph.drawGraph.NodesWaysX)
     */
    public void deleteWays() {
        if (graph instanceof GraphOriented) {
            ((GraphOriented) graph).deleteWays();
            graphX.deleteWays();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#nodeOf(graph.drawGraph.NodeX)
     */
    public Node nodeOf(NodeX nodeX) {
        return graph.nodeAt(graphX.getNodeIndex(nodeX));
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#nodeXOf(graph.logicGraph.Node)
     */
    public NodeX nodeXOf(Node node) {
        return graphX.nodeAt(graph.getNodeIndex(node));
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#edgeOf(graph.drawGraph.EdgeX)
     */
    public Edge edgeOf(EdgeX edgeX) {
        return graph.edgeAt(graphX.getEdgeIndex(edgeX));
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#edgeXOf(graph.logicGraph.Edge)
     */
    public EdgeX edgeXOf(Edge edge) {
        return graphX.edgeAt(graph.getEdgeIndex(edge));
    }

    /**
     * Set the index tool
     * 
     * @param index
     */
    public void setIndexTool(int index) {
        this.indexTool = index;
    }

    /**
     * Get the index tool
     * 
     * @return
     */
    public int getIndexTool() {
        return indexTool;
    }

    /**
     * Write the graph into a file
     * 
     * @param fileName
     */
    public void saveGraph(String dirName, String fileName) {
        try {
            if (fileName.indexOf('.') < 0) {
                StringBuffer buffer = new StringBuffer(fileName);
                buffer.append(".gpx");
                fileName = buffer.toString();
            }
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dirName + fileName));

            /* Type of the graph */
            if (graph instanceof GraphUnoriented) {
                out.writeInt(GRAPH_UNORIENTED);
            } else if (graph instanceof GraphOriented) {
                out.writeInt(GRAPH_ORIENTED);
            }
            /* Graph */
            out.writeObject(graph);
            out.writeObject(graphX);

            /* Scroll position */
            out.writeObject(scrollRectangle);

            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Read a graph from a file
     * 
     * @param fileName
     */
    public void openGraph(String dirName, String fileName) {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(dirName + fileName));

            /* Type of the graph */
            int type = in.readInt();

            /* Graph */
            switch (type) {
                case GRAPH_UNORIENTED:
                    this.graph = (GraphUnoriented) in.readObject();
                    break;
                case GRAPH_ORIENTED:
                    this.graph = (GraphOriented) in.readObject();
                    break;
            }
            this.graphX = (GraphX) in.readObject();

            /* Scroll Rectangle */
            this.scrollRectangle = (Rectangle) in.readObject();

            in.close();

            repaintGraph();
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        indexTool = SELECT_TOOL;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        /* Edge's create split point */
        if (e.getActionCommand().equals(edgeMenuItem[1].getLabel())) {
            graphX.getSelectedEdge().addSplitPoint(splitPointLocation);
        }
        /* Select all the nodes & edges */
        else if (e.getActionCommand().equals(nodeMenuItem[0].getLabel())) {
            graphX.selectNodes();
            graphX.selectEdgesFromNodes();
        }
        /* Edit label */
        else if (e.getActionCommand().equals(nodeMenuItem[1].getLabel())) {
            if (itemType == NODE) {
                nodeEdit = graphX.getSelectedNode();
                buffer.setLength(0);
                buffer.append(nodeOf(nodeEdit).getLabel());
                editNode = true;
            } else if (itemType == EDGE) {
                edgeEdit = graphX.getSelectedEdge();
                buffer.setLength(0);
                buffer.append(edgeOf(edgeEdit).getLabel());
                editEdge = true;
            }
        }
        /* Properties */
        else if (e.getActionCommand().equals(nodeMenuItem[2].getLabel())) {
            if (itemType == NODE) {
                nodeEdit = graphX.getSelectedNode();

                ((GraphBuilder) getFrameParent()).getPropertiesDialog().setPropertiesFrom(nodeOf(nodeEdit), nodeEdit);
            } else if (itemType == EDGE) {
                edgeEdit = graphX.getSelectedEdge();

                ((GraphBuilder) getFrameParent()).getPropertiesDialog().setPropertiesFrom(edgeOf(edgeEdit), edgeEdit);
            }

            ((GraphBuilder) getFrameParent()).getPropertiesDialog().setVisible(true);

            if (((GraphBuilder) getFrameParent()).getPropertiesDialog().isOk()) {
                if (itemType == NODE) {
                    ((GraphBuilder) getFrameParent()).getPropertiesDialog().applyPropertiesTo(nodeOf(nodeEdit),
                            nodeEdit);
                    resizeEditNode(new StringBuffer(nodeOf(nodeEdit).getLabel()));
                } else if (itemType == EDGE) {
                    ((GraphBuilder) getFrameParent()).getPropertiesDialog().applyPropertiesTo(edgeOf(edgeEdit),
                            edgeEdit);
                    resizeEditNode(new StringBuffer(edgeOf(edgeEdit).getLabel()));
                }
            }
        }

        /* Edit the path's popup actions */
        try {
            if (graphX.getWays().getState() == true) {
                if (e.getActionCommand().equals(wayMenuItem[0].getLabel())) {
                    selectWay(((GraphOriented) graph).getWays().getMinWay());
                } else if (e.getActionCommand().equals(wayMenuItem[1].getLabel())) {
                    selectWay(((GraphOriented) graph).getWays().getMaxWay());
                } else if (e.getActionCommand().equals(wayMenuItem[2].getLabel())) {
                    selectWay(((GraphOriented) graph).getWays().getNextWay());
                } else if (e.getActionCommand().equals(wayMenuItem[3].getLabel())) {
                    selectWay(((GraphOriented) graph).getWays().getPrevWay());
                } else if (e.getActionCommand().equals(wayMenuItem[4].getLabel())) {
                    selectWays();
                }
            }
        } catch (NullPointerException e1) {
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#selectWays(graph.logicGraph.NodesWays,
     * graph.drawGraph.NodesWaysX)
     */
    public void selectWays() {
        EdgeX edgeX;

        graphX.deselectAll();
        for (int i = 0; i < ((GraphOriented) graph).getWays().waysNumber(); i++) {
            for (int j = 0; j < ((GraphOriented) graph).getWays().wayAt(i).edgesNumber(); j++) {
                edgeX = edgeXOf(((GraphOriented) graph).getWays().wayAt(i).edgeAt(j));
                edgeX.setState(true);
                edgeX.getFirstAdjacent().setState(true);
                edgeX.getSeccondAdjacent().setState(true);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#selectWay(null)
     */
    public void selectWay(Way way) {
        if (way != null) {
            EdgeX edgeX;

            graphX.deselectAll();
            for (int j = 0; j < way.edgesNumber(); j++) {
                edgeX = edgeXOf(way.edgeAt(j));
                edgeX.setState(true);
                edgeX.getFirstAdjacent().setState(true);
                edgeX.getSeccondAdjacent().setState(true);
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#computeLabelBounds(graph.logicGraph.Edge,
     * graph.drawGraph.EdgeX)
     */
    public Rectangle getLabelBounds(Edge edge, EdgeX edgeX) {
        Rectangle rectangle = edgeX.getFont()
                .getStringBounds(edge.getLabel(), new FontRenderContext(new AffineTransform(), false, false))
                .getBounds();
        Point p = edgeX.getNamePosition();
        rectangle.x = p.x;
        rectangle.y = p.y - rectangle.height;
        return rectangle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.Edit#computeLabelBounds(graph.logicGraph.Node,
     * graph.drawGraph.NodeX)
     */
    public Rectangle getLabelBounds(Node node, NodeX nodeX) {
        Rectangle rectangle = nodeX.getFont()
                .getStringBounds(node.getLabel(), new FontRenderContext(new AffineTransform(), false, false))
                .getBounds();
        Point p = nodeX.getNamePosition();
        rectangle.x = p.x;
        rectangle.y = p.y - rectangle.height;
        return rectangle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.GraphEditor#edgeFrom(java.awt.Point)
     */
    public EdgeX edgeFromLabel(Point p) {
        Rectangle labelRectangle;
        for (int i = 0; i < graphX.edgesNumber(); i++) {
            labelRectangle = getLabelBounds(graph.edgeAt(i), graphX.edgeAt(i));
            if (labelRectangle.contains(p)) {
                return graphX.edgeAt(i);
            }
        }
        return null;
    }

    /**
     * Resize the edit node
     * 
     * @param buffer
     */
    public void resizeEditNode(StringBuffer buffer) {
        if (nodeEdit != null) {
            Rectangle rectangle = MyMath.stringBounds(buffer.toString(), nodeEdit.getFont());
            if (rectangle.width > nodeEdit.getRectangle().width - 41) {
                nodeEdit.getRectangle().width = rectangle.width + 41;
            }
            nodeEdit.setMinWidth(rectangle.width + 41);
        }
    }

    /**
     * Get the graphX of the graph (used to draw the graph in the mini map)
     * 
     * @return
     */
    public GraphX getGraphX() {
        return graphX;
    }

    /**
     * Get the scroll rectangle
     * 
     * @return
     */
    public Rectangle getScrollRectangle() {
        return scrollRectangle;
    }

    /**
     * Get the drag scroll state
     * 
     * @return
     */
    public boolean isDragScroll() {
        return dragScroll;
    }

    /**
     * Get the preferred size (overwritten)
     */
    public Dimension getPreferredSize() {
        Dimension dimension = graphX.getDimension();

        int width = dimension.width;
        int height = dimension.height;

        int i;

        for (i = 0; width > i * ((GraphBuilder) getFrameParent()).getMiniMap().getWidth(); i++) {
        }
        width = i * ((GraphBuilder) getFrameParent()).getMiniMap().getWidth();

        for (i = 0; height > i * ((GraphBuilder) getFrameParent()).getMiniMap().getHeight(); i++) {
        }
        height = i * ((GraphBuilder) getFrameParent()).getMiniMap().getHeight();

        /* Size conditions */
        if (width < MIN_WIDTH) {
            width = MIN_WIDTH;
        } else if (width > MAX_WIDTH) {
            width = MAX_WIDTH;
        }
        /* Size conditions */
        if (height < MIN_HEIGHT) {
            height = MIN_HEIGHT;
        } else if (height > MAX_HEIGHT) {
            height = MAX_HEIGHT;
        }

        // System.out.println(width + " / " + height);

        return new Dimension(width, height);
    }

    /**
     * Repaint the panel (overwritten)
     */
    public void repaintGraph() {
        this.repaint();
        ((GraphBuilder) getFrameParent()).getMiniMap().repaint();
    }

    /**
     * Get the parent scroll pane
     * 
     * @return
     */
    private ScrollPane scrollPane() {
        return (ScrollPane) this.getParent();
    }

    /**
     * Get the parent frame
     */
    private Frame getFrameParent() {
        Frame parent;// = new Frame();
        Container container = this;

        while (!(container.getParent() instanceof Frame)) {
            container = container.getParent();
        }
        parent = (Frame) container.getParent();

        return parent;
    }

    /*
     * (non-Javadoc)
     * 
     * @see graph.GraphEditor#putSelectionInFront()
     */
    public void putSelectionInFront() {
        Vector nodes = new Vector(10);
        Vector nodesX = new Vector(10);
        /* Remove the nodes */
        for (int i = 0; i < graphX.nodesNumber(); i++) {
            /* Change the node position */
            if (graphX.nodeAt(i).getState() == true) {
                nodes.addElement(nodeOf(graphX.nodeAt(i)));
                nodesX.addElement(graphX.nodeAt(i));
                graph.getNodes().removeElement(nodeOf(graphX.nodeAt(i)));
                graphX.getNodes().removeElement(graphX.nodeAt(i));
                i--;
            }
        }
        /* Add the nodes */
        for (int i = 0; i < nodes.size(); i++) {
            graph.getNodes().addElement(nodes.elementAt(i));
            graphX.getNodes().addElement(nodesX.elementAt(i));
        }
    }

}