/*
 * Created on Dec 2, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import graph.dialogs.NewGraph;
import graph.dialogs.Properties;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GraphBuilder extends Frame implements ActionListener, ItemListener, AdjustmentListener {

    /**
     * The arrow cursor of the panel
     */
    public static Cursor arrow = new Cursor(Cursor.DEFAULT_CURSOR);

    /**
     * The move cursor of the panel
     */
    public static Cursor move = new Cursor(Cursor.MOVE_CURSOR);

    /**
     * The hand cursor of the panel
     */
    public static Cursor hand = new Cursor(Cursor.HAND_CURSOR);

    /**
     * The resize S - E currsor
     */
    public static Cursor resizeSE = new Cursor(Cursor.SE_RESIZE_CURSOR);

    /**
     * Initial name for the graph
     */
    public static String newGraphName = new String("New Graph");

    /**
     * Name of the frame
     */
    private String title;

    /**
     * State of the current graph (true - saved, false - not saved)
     */
    private String graphName;

    /**
     * The graph panel
     */
    private GraphPanel graph;

    /**
     * The menu bar of the frame
     */
    private MenuBar menuBar;

    /**
     * List of the menus
     */
    private Menu[] menu;

    /**
     * List of menu items
     */
    private MenuItem[] menuItem;

    /**
     * List of check box menu items
     */
    private CheckboxMenuItem[] checkMenuItem;

    /**
     * Dialog for new graph action
     */
    private NewGraph newGraph;

    /**
     * The node / edge properties dialog
     */
    private Properties properties;

    /**
     * Dialog for open graph action
     */
    private FileDialog fileGraph;

    /**
     * The scroll
     */
    private ScrollPane scroll;

    /**
     * The mini map of the graph
     */
    private GraphMap graphMap;

    /**
     * The tools from tool bar (Node, Edge, Path)
     */
    private Checkbox[] tools;

    /**
     * The tools group
     */
    private CheckboxGroup toolsGroup;

    /**
     * Toolbar's buttons
     */
    private Button but;

    /**
     * The tools panel
     */
    private Panel panel;

    /**
     * Default constructor
     *
     */
    public GraphBuilder() {
        newGraph = new NewGraph(this);
        newGraph.setVisible(false);
        newGraph.setModal(true);

        properties = new Properties(this, "Properties", true);
        properties.setVisible(false);

        fileGraph = new FileDialog(this);
        fileGraph.setVisible(false);

        initMenu();
        addComponents();

        this.addWindowListener(new WindowAdapter() {
            /**
             * Close the window (implemented)
             */
            public void windowClosing(WindowEvent e) {
                System.exit(1);
            }
        });

        graphName = newGraphName;
        this.setBounds(100, 100, 640, 480);
        graph.setSize(640, 480);
        title = new String("Graph Editor");
        StringBuffer buffer = new StringBuffer(title);
        buffer.append(" - ");
        buffer.append(graphName);
        this.setTitle(buffer.toString());
    }

    /**
     * Add the components into frame
     *
     */
    private void addComponents() {
        /* Add the scroll graph */
        graph = new GraphPanel(GraphPanel.SELECT_TOOL);

        scroll = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);

        /* Add the adjustment for scroll to know when to paint the mini map */
        Adjustable adjH = scroll.getHAdjustable();
        adjH.addAdjustmentListener(this);
        Adjustable adjV = scroll.getVAdjustable();
        adjV.addAdjustmentListener(this);

        /* Add the graph to the scroll */
        scroll.add(graph);
        this.add(scroll, BorderLayout.CENTER);

        /* Add the mini map */
        graphMap = new GraphMap(graph.getGraphX());

        panel = new Panel(new BorderLayout()) {
            public Color getBackground() {
                return new Color(54, 60, 160);
            }
        };

        /* Init tools */
        Panel toolsPanel = new Panel(new GridLayout(5, 1, 5, 5)) {
            /**
             * Get the panel's insets (overwritten)
             */
            public Insets getInsets() {
                return new Insets(10, 10, 10, 10);
            }

            /**
             * Paint the panel (overwritten)
             */
            public void paint(Graphics g) {
                g.setColor(new Color(54, 60, 160));
                g.fillRect(0, 0, this.getSize().width, this.getSize().height);
            }
        };

        toolsGroup = new CheckboxGroup();
        tools = new Checkbox[4];
        tools[0] = new Checkbox("Select", toolsGroup, true) {
            public Color getBackground() {
                return new Color(54, 60, 160);
            }

            public Color getForeground() {
                return new Color(255, 255, 255);
            }
        };
        tools[1] = new Checkbox("Create Node", toolsGroup, false) {
            public Color getBackground() {
                return new Color(54, 60, 160);
            }

            public Color getForeground() {
                return new Color(255, 255, 255);
            }
        };
        tools[2] = new Checkbox("Create Edge", toolsGroup, false) {
            public Color getBackground() {
                return new Color(54, 60, 160);
            }

            public Color getForeground() {
                return new Color(255, 255, 255);
            }
        };
        tools[3] = new Checkbox("Verify Path", toolsGroup, false) {
            public Color getBackground() {
                return new Color(54, 60, 160);
            }

            public Color getForeground() {
                return new Color(255, 255, 255);
            }
        };

        but = new Button("Delete");
        but.setBackground(new Color(54, 60, 160));
        but.setForeground(new Color(255, 255, 255));

        for (int i = 0; i < 4; i++) {
            tools[i].addItemListener(this);
            toolsPanel.add(tools[i]);
        }

        but.addActionListener(this);
        toolsPanel.add(but);

        /* Add the tools and the map to the west panel */
        panel.add(toolsPanel, BorderLayout.NORTH);
        panel.add(graphMap, BorderLayout.CENTER);

        this.add(panel, BorderLayout.WEST);
    }

    /**
     * Init the menu
     *
     */
    private void initMenu() {
        /* Create the menu */
        menuBar = new MenuBar();

        menu = new Menu[3];
        menu[0] = new Menu("File");
        menu[1] = new Menu("Edit");
        menu[2] = new Menu("View");

        menuItem = new MenuItem[5];
        menuItem[0] = new MenuItem("New");
        menuItem[1] = new MenuItem("Open");
        menuItem[2] = new MenuItem("Save As");
        menuItem[3] = new MenuItem("Save");
        menuItem[4] = new MenuItem("Exit");

        checkMenuItem = new CheckboxMenuItem[5];
        checkMenuItem[0] = new CheckboxMenuItem("Select", true);
        checkMenuItem[1] = new CheckboxMenuItem("Create Node");
        checkMenuItem[2] = new CheckboxMenuItem("Create Edge");
        checkMenuItem[3] = new CheckboxMenuItem("Verify Path");

        checkMenuItem[4] = new CheckboxMenuItem("Tool Bar", true);

        /* Add the components in the menu */
        menuBar.add(menu[0]);
        menuBar.add(menu[1]);
        menuBar.add(menu[2]);

        menu[0].add(menuItem[0]);
        menu[0].addSeparator();
        menu[0].add(menuItem[1]);
        menu[0].add(menuItem[2]);
        menu[0].add(menuItem[3]);
        menu[0].addSeparator();
        menu[0].add(menuItem[4]);

        menu[1].add(checkMenuItem[0]);
        menu[1].add(checkMenuItem[1]);
        menu[1].add(checkMenuItem[2]);
        menu[1].add(checkMenuItem[3]);

        menu[2].add(checkMenuItem[4]);

        this.setMenuBar(menuBar);

        /* Add listeners to the menu */
        for (int i = 0; i < 5; i++) {
            menuItem[i].addActionListener(this);
        }

        for (int i = 0; i < 5; i++) {
            checkMenuItem[i].addItemListener(this);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        /* Create a new graph */
        if (e.getActionCommand().equals(menuItem[0].getLabel())) {
            newGraph.setVisible(true);
            setGraphName(newGraphName);
        }
        /* Open a graph */
        else if (e.getActionCommand().equals(menuItem[1].getLabel())) {
            fileGraph.setMode(FileDialog.LOAD);
            fileGraph.setVisible(true);

            if (fileGraph.getFile() != null) {
                graph.openGraph(fileGraph.getDirectory(), fileGraph.getFile());

                /* Create a new mini map */
                graphMap.initMap(graph.getGraphX());

                /* Set the scroll to the opened graph scroll */
                scroll.setScrollPosition(graph.getScrollRectangle().getLocation());

                /* Set the frame's title */
                setGraphName(fileGraph.getFile());
            }

            setCheckTool(0);
        }
        /* Save the current graph as a new graph file */
        else if (e.getActionCommand().equals(menuItem[2].getLabel())) {
            fileGraph.setMode(FileDialog.SAVE);
            fileGraph.setVisible(true);

            if (fileGraph.getFile() != null) {
                graph.saveGraph(fileGraph.getDirectory(), fileGraph.getFile());
                /* Set the frame's title */
                setGraphName(fileGraph.getFile());
            }
        }
        /* Save the current graph */
        else if (e.getActionCommand().equals(menuItem[3].getLabel())) {
            /* Save the graph as a new graph */
            if (graphName.equals(newGraphName)) {
                fileGraph.setMode(FileDialog.SAVE);
                fileGraph.setVisible(true);

                if (fileGraph.getFile() != null) {
                    graph.saveGraph(fileGraph.getDirectory(), fileGraph.getFile());
                    /* Set the frame's title */
                    setGraphName(fileGraph.getFile());
                }
            }
            /* Save the graph over the existing file */
            else {
                graph.saveGraph(fileGraph.getDirectory(), fileGraph.getFile());
            }
        }
        /* Exit the graph editor */
        else if (e.getActionCommand().equals(menuItem[4].getLabel())) {
            System.exit(1);
        }
        /* Delete the selection */
        else if (e.getActionCommand().equals(but.getLabel())) {
            graph.deleteSelection();
            graph.repaintGraph();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
     */
    public void itemStateChanged(ItemEvent e) {
        /* No tool selected */
        if (e.getItem().equals(checkMenuItem[0].getLabel())) {
            /* Set all states to false */
            for (int i = 0; i < 4; i++) {
                checkMenuItem[i].setState(false);
            }
            graph.setIndexTool(GraphPanel.SELECT_TOOL);
            checkMenuItem[0].setState(true);
            tools[0].setState(true);
        }
        /* Create nodes */
        else if (e.getItem().equals(checkMenuItem[1].getLabel())) {
            /* Set all states to false */
            for (int i = 0; i < 4; i++) {
                checkMenuItem[i].setState(false);
            }
            graph.setIndexTool(GraphPanel.NODE_TOOL);
            checkMenuItem[1].setState(true);
            tools[1].setState(true);
        }
        /* Create sides */
        else if (e.getItem().equals(checkMenuItem[2].getLabel())) {
            /* Set all states to false */
            for (int i = 0; i < 4; i++) {
                checkMenuItem[i].setState(false);
            }
            graph.setIndexTool(GraphPanel.EDGE_TOOL);
            checkMenuItem[2].setState(true);
            tools[2].setState(true);
        }
        /* Create path */
        else if (e.getItem().equals(checkMenuItem[3].getLabel())) {
            /* Set all states to false */
            for (int i = 0; i < 4; i++) {
                checkMenuItem[i].setState(false);
            }
            graph.setIndexTool(GraphPanel.WAY_TOOL);
            checkMenuItem[3].setState(true);
            tools[3].setState(true);
        }
        /* Show or Hide the tool bar */
        else if (e.getItem().equals(checkMenuItem[4].getLabel())) {
            if (checkMenuItem[4].getState() == true) {
                this.add(panel, BorderLayout.WEST);
                this.doLayout();
                scroll.doLayout();
            } else {
                this.remove(panel);
                this.doLayout();
                scroll.doLayout();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.
     * AdjustmentEvent)
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (!graphMap.isScroll() && !graph.isDragScroll()) {
            graphMap.repaint();
        }
    }

    /**
     * Get the mini map (used in GraphPainter to call the repaint of the mini
     * map)
     * 
     * @return
     */
    public GraphMap getMiniMap() {
        return graphMap;
    }

    /**
     * Get the node properties dialog
     * 
     * @return
     */
    public Properties getPropertiesDialog() {
        return properties;
    }

    /**
     * Get the Graph Panel
     * 
     * @return
     */
    public GraphPanel getGraph() {
        return graph;
    }

    /**
     * Get the scroll pane
     * 
     * @return
     */
    public ScrollPane getScrollPane() {
        return scroll;
    }

    /**
     * Set the check menu item with the proper tool
     * 
     * @param index
     */
    public void setCheckTool(int index) {
        /* Set all states to false */
        for (int i = 0; i < 4; i++) {
            checkMenuItem[i].setState(false);
        }
        checkMenuItem[index].setState(true);
        tools[index].setState(true);
    }

    /**
     * Create a new graph
     * 
     * @param type
     */
    public void newGraph(int type) {
        graph.newGraph(type);
    }

    /**
     * Set the graph name and the frame title
     * 
     * @param name
     */
    private void setGraphName(String name) {
        graphName = name;
        StringBuffer buffer = new StringBuffer(title);
        buffer.append(" - ");
        buffer.append(graphName);
        this.setTitle(buffer.toString());
    }

    /**
     * The main method
     * 
     * @param args
     */
    public static void main(String[] args) {
        GraphBuilder app = new GraphBuilder();
        app.setVisible(true);
    }

}
