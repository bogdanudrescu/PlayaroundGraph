/*
 * Created on Dec 10, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.dialogs;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import graph.GraphBuilder;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NewGraph extends Dialog implements ActionListener {
    /**
     * Check box group for the check boxes
     */
    private CheckboxGroup checkGroup;

    /**
     * Use to set the type of graph
     */
    private Checkbox[] check;

    /**
     * Ok and Cancel buttons
     */
    private Button[] but;

    /**
     * Constructor
     * 
     * @param frame
     */
    public NewGraph(Frame frame) {
        super(frame, "New Graph");

        /* Set the buttons */
        but = new Button[2];
        but[0] = new Button("Ok");
        but[1] = new Button("Cancel");

        but[0].addActionListener(this);
        but[1].addActionListener(this);

        /* Set the check boxes */
        checkGroup = new CheckboxGroup();

        check = new Checkbox[2];
        check[0] = new Checkbox("Unoriented graph", checkGroup, true);
        check[1] = new Checkbox("Oriented graph", checkGroup, false);

        addComponents();

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
            }
        });

        this.setBounds(300, 300, 180, 140);
        this.setResizable(false);
    }

    /**
     * Add the components to the dialog
     *
     */
    private void addComponents() {
        /* Checks */
        Panel panel0 = new Panel(new GridLayout(2, 1, 4, 4));
        panel0.add(check[0]);
        panel0.add(check[1]);

        this.add(panel0, BorderLayout.CENTER);

        /* Buttons */
        Panel panel1 = new Panel(new FlowLayout(1));
        panel1.add(but[0]);
        panel1.add(but[1]);

        this.add(panel1, BorderLayout.SOUTH);
    }

    /**
     * Get the dialog insets (overwrite)
     */
    public Insets getInsets() {
        return new Insets(30, 10, 10, 10);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(but[0].getLabel())) {
            this.setVisible(false);
            int i;
            for (i = 0; i < 2; i++) {
                if (checkGroup.getSelectedCheckbox().equals(check[i])) {
                    break;
                }
            }
            ((GraphBuilder) this.getParent()).newGraph(i);
        } else if (e.getActionCommand().equals(but[1].getLabel())) {
            this.setVisible(false);
        }
    }

}
