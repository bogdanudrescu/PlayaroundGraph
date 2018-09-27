/*
 * Created on Jan 7, 2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.dialogs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import graph.GraphBuilder;
import graph.GraphPanel;
import graph.graphmodel.Edge;
import graph.graphmodel.Node;
import graph.graphview.EdgeX;
import graph.graphview.NodeX;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Properties extends Dialog implements ActionListener {
    /**
     * Color list
     */
    private final Color[] LIST_COLOR = { Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green,
            Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow };

    /**
     * The component's background color
     */
    private final Color BACKGROUND_COLOR = new Color(54, 60, 160);

    /**
     * The component's foreground color
     */
    private final Color FOREGROUND_COLOR = new Color(255, 255, 255);

    /**
     * The label of the node / edge
     */
    private TextField label;

    /**
     * The font of the label
     */
    private Choice fonts;

    /**
     * The size of the label
     */
    private Choice size;

    /**
     * The style of the label
     */
    private Choice style;

    /**
     * The node / edge colors
     */
    private Choice[] colors;

    /**
     * The Ok / Cancel buttons
     */
    private Button[] but;

    /**
     * True if press ok and the node / edge properties must be changed
     */
    private boolean pressOk;

    /**
     * The current node / edge
     */
    private Object object;

    /**
     * The current nodeX / edgeX
     */
    private Object objectX;

    /**
     * Type of the item (NODE / EDGE)
     */
    private int itemType;

    /**
     * Constructor
     *
     */
    public Properties(Frame parent, String title, boolean modal) {
        super(parent, title, modal);

        addWindowListener(new WindowAdapter() {
            /**
             * Close the window (implemented)
             */
            public void windowClosing(WindowEvent e) {
                pressOk = false;
                setVisible(false);
            }
        });

        initComponents();
        addComponents();

        this.setBounds(300, 300, 340, 340);
        setResizable(false);
    }

    /**
     * Init the components
     *
     */
    private void initComponents() {
        label = new TextField();

        fonts = new Choice();
        /* Fonts list */
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        for (int i = 0; i < graphicsEnvironment.getAvailableFontFamilyNames().length; i++) {
            fonts.add(graphicsEnvironment.getAvailableFontFamilyNames()[i]);
        }

        size = new Choice();
        /* Size list */
        for (int i = 8; i <= 18; i++) {
            size.add(new Integer(i).toString());
        }

        style = new Choice();
        style.add("Plain");
        style.add("Bold");
        style.add("Italic");

        colors = new Choice[4];
        colors[0] = new Choice();
        colors[1] = new Choice();
        colors[2] = new Choice();
        colors[3] = new Choice();

        String[] colorsName = { "black", "blue", "cyan", "dark gray", "gray", "green", "light gray", "magenta",
                "orange", "pink", "red", "white", "yellow" };
        /* Colors list */
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < colorsName.length; j++) {
                colors[i].add(colorsName[j]);
            }
        }

        String[] buttonLabel = { "Ok", "Cancel", "Apply", "Default" };

        but = new Button[4];
        for (int i = 0; i < 4; i++) {
            but[i] = new Button(buttonLabel[i]);
            but[i].setBackground(BACKGROUND_COLOR);
            but[i].setForeground(FOREGROUND_COLOR);
            but[i].addActionListener(this);
        }

    }

    /**
     * Add the components to the panel
     *
     */
    private void addComponents() {
        /* Font size and style */
        Panel font = new Panel(new GridBagLayout());
        font.add(fonts);
        font.add(new Panel());
        font.add(size);
        font.add(new Panel());
        font.add(style);

        /* Ok and Cancel buttons */
        Panel defaultButtons = new Panel(new GridLayout(1, 3, 10, 10)) {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }
        };
        defaultButtons.add(but[0]);
        defaultButtons.add(but[1]);
        defaultButtons.add(but[2]);
        defaultButtons.add(but[3]);
        Panel defaultButtonsPanel = new Panel() {
            public Insets getInsets() {
                return new Insets(15, 0, 0, 0);
            }

            public Color getBackground() {
                return BACKGROUND_COLOR;
            }
        };
        defaultButtonsPanel.add(defaultButtons);

        /* Unselected mode colors Choise components */
        Panel unselectedModeColors = new Panel(new GridBagLayout()) {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }
        };
        unselectedModeColors.add(new Label("Fill color") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });
        unselectedModeColors.add(colors[2]);
        unselectedModeColors.add(new Panel());
        unselectedModeColors.add(new Label("Label color") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });
        unselectedModeColors.add(colors[3]);

        /* Selected mode colors Choise components */
        Panel selectedModeColors = new Panel(new GridBagLayout()) {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }
        };
        selectedModeColors.add(new Label("Fill color") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });
        selectedModeColors.add(colors[0]);
        selectedModeColors.add(new Panel());
        selectedModeColors.add(new Label("Label color") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });
        selectedModeColors.add(colors[1]);

        /* Properties panel */
        Panel panel = new Panel(new GridLayout(8, 1, 5, 5)) {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }
        };
        /* Add the label "Label" and set its colors */
        panel.add(new Label("Label") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });

        panel.add(label);

        // /* Auxiliar panel */
        // panel.add(new Panel() {
        // public Color getBackground() {
        // return Color.red;
        // }
        // });

        panel.add(new Label("Label font") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });

        panel.add(font);

        // /* Auxiliar panel */
        // panel.add(new Panel() {
        // public Color getBackground() {
        // return new Color(255, 0, 0);
        // }
        // });

        panel.add(new Label("Deselected Mode") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });

        panel.add(unselectedModeColors);

        // /* Auxiliar panel */
        // panel.add(new Panel() {
        // public Color getBackground() {
        // return new Color(255, 0, 0);
        // }
        // });

        panel.add(new Label("Selected Mode") {
            public Color getBackground() {
                return BACKGROUND_COLOR;
            }

            public Color getForeground() {
                return FOREGROUND_COLOR;
            }
        });

        panel.add(selectedModeColors);

        /* Add to the dialog */
        add(panel, BorderLayout.CENTER);
        add(defaultButtonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Paint the dialog (overwritten)
     */
    public void paint(Graphics g) {
        g.setColor(new Color(54, 60, 160));
        g.fillRect(0, 0, getSize().width, getSize().height);
    }

    /**
     * Apply the properties to the node / edge
     * 
     * @param object
     */
    public void applyPropertiesTo(Object object, Object objectX) {
        if (object instanceof Node || objectX instanceof NodeX) {
            ((Node) object).setLabel(label.getText());

            NodeX nodeX = ((NodeX) objectX);
            nodeX.setFont(new Font(fonts.getSelectedItem(), style.getSelectedIndex(), size.getSelectedIndex() + 8));
            nodeX.setForegroundSelect(LIST_COLOR[colors[0].getSelectedIndex()]);
            nodeX.setLabelSelect(LIST_COLOR[colors[1].getSelectedIndex()]);
            nodeX.setForegroundUnselect(LIST_COLOR[colors[2].getSelectedIndex()]);
            nodeX.setLabelUnselect(LIST_COLOR[colors[3].getSelectedIndex()]);
        }
        if (object instanceof Edge || objectX instanceof EdgeX) {
            ((Edge) object).setLabel(label.getText());

            EdgeX edgeX = ((EdgeX) objectX);
            edgeX.setFont(new Font(fonts.getSelectedItem(), style.getSelectedIndex(), size.getSelectedIndex() + 8));
            edgeX.setForegroundSelect(LIST_COLOR[colors[0].getSelectedIndex()]);
            edgeX.setLabelSelect(LIST_COLOR[colors[1].getSelectedIndex()]);
            edgeX.setForegroundUnselect(LIST_COLOR[colors[2].getSelectedIndex()]);
            edgeX.setLabelUnselect(LIST_COLOR[colors[3].getSelectedIndex()]);
        }
    }

    /**
     * Set the properties of the dialog from the object's properties
     * 
     * @param object
     * @param objectX
     */
    public void setPropertiesFrom(Object object, Object objectX) {
        if (object instanceof Node || objectX instanceof NodeX) {
            label.setText(((Node) object).getLabel());

            NodeX nodeX = ((NodeX) objectX);
            fonts.select(nodeX.getFont().getName());
            size.select(new Integer(nodeX.getFont().getSize()).toString());
            style.select(nodeX.getFont().getStyle());

            /* Set the colors */
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < LIST_COLOR.length; j++) {
                    if (LIST_COLOR[j].equals(nodeX.getColor(i))) {
                        colors[i].select(j);
                        break;
                    }
                }
            }

            this.object = object;
            this.objectX = objectX;
            itemType = GraphPanel.NODE;

            setTitle("Node Properties");
        }
        if (object instanceof Edge || objectX instanceof EdgeX) {
            label.setText(((Edge) object).getLabel());

            EdgeX edgeX = ((EdgeX) objectX);
            fonts.select(edgeX.getFont().getName());
            size.select(new Integer(edgeX.getFont().getSize()).toString());
            style.select(edgeX.getFont().getStyle());

            /* Set the colors */
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < LIST_COLOR.length; j++) {
                    if (LIST_COLOR[j].equals(edgeX.getColor(i))) {
                        colors[i].select(j);
                        break;
                    }
                }
            }

            this.object = object;
            this.objectX = objectX;
            itemType = GraphPanel.NODE;

            setTitle("Edge Properties");
        }

        label.requestFocus();
    }

    /**
     * Press the Ok button
     * 
     * @return
     */
    public boolean isOk() {
        return pressOk;
    }

    /**
     * Get the insets of the dialog (overwritten)
     */
    public Insets getInsets() {
        return new Insets(25, 20, 10, 20);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        /* Press Ok */
        if (e.getActionCommand().equals(but[0].getLabel())) {
            pressOk = true;
            setVisible(false);
        }
        /* Press Cancel */
        else if (e.getActionCommand().equals(but[1].getLabel())) {
            pressOk = false;
            setVisible(false);
        }
        /* Press Apply */
        else if (e.getActionCommand().equals(but[2].getLabel())) {
            applyPropertiesTo(object, objectX);
            ((GraphBuilder) getParent()).getGraph().repaintGraph();
            /* Resize the node */
            if (itemType == GraphPanel.NODE) {
                ((GraphBuilder) getParent()).getGraph().resizeEditNode(new StringBuffer(((Node) object).getLabel()));
            }
        }
        /* Press Default */
        else if (e.getActionCommand().equals(but[3].getLabel())) {
            if (itemType == GraphPanel.NODE) {
                Node node = new Node(Node.LABEL);
                NodeX nodeX = new NodeX(new Rectangle(0, 0, 0, 0));
                nodeX.setForegroundSelect(NodeX.FOREGROUND_SELECT);
                nodeX.setLabelSelect(NodeX.LABEL_SELECT);
                nodeX.setForegroundUnselect(NodeX.FOREGROUND_UNSELECT);
                nodeX.setLabelUnselect(NodeX.LABEL_UNSELECT);
                setPropertiesFrom(node, nodeX);
            } else if (itemType == GraphPanel.NODE) {
                Edge edge = new Edge(Edge.LABEL, null, null);
                EdgeX edgeX = new EdgeX(null, null);
                edgeX.setForegroundSelect(EdgeX.FOREGROUND_SELECT);
                edgeX.setLabelSelect(EdgeX.LABEL_SELECT);
                edgeX.setForegroundUnselect(EdgeX.FOREGROUND_UNSELECT);
                edgeX.setLabelUnselect(EdgeX.LABEL_UNSELECT);
                setPropertiesFrom(edge, edgeX);
            }
        }

    }

}
