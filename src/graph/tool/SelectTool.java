/*
 * Created on Dec 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.tool;

import java.awt.Point;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SelectTool extends NodeTool {
    /**
     * State of multiple selection
     */
    private boolean multipleSelect;

    /**
     * Default constructor
     *
     */
    public SelectTool() {
        this(new Point(-1, -1), new Point(-1, -1));
    }

    /**
     * Constructs the tool
     * 
     * @param p0
     * @param p1
     */
    public SelectTool(Point p0, Point p1) {
        super(p0, p1);

        multipleSelect = false;
    }

    /**
     * Set the multiple selection
     * 
     * @param multipleSelect
     */
    public void setMultiple(boolean multipleSelect) {
        this.multipleSelect = multipleSelect;
    }

    /**
     * Get the multiple selection state
     * 
     * @return
     */
    public boolean isMultiple() {
        return multipleSelect;
    }
}
