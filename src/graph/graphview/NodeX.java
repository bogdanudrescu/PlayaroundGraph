/*
 * Created on Dec 4, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.graphview;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class NodeX implements Serializable {
    /**
     * The default font
     */
    public static final Font FONT = new Font("Arial", Font.BOLD, 12);

    /**
     * The default foreground unselect color
     */
    public static final Color FOREGROUND_UNSELECT = Color.blue;

    /**
     * The default label unselect color
     */
    public static final Color LABEL_UNSELECT = Color.orange;

    /**
     * The default foreground select color
     */
    public static final Color FOREGROUND_SELECT = Color.orange;

    /**
     * The default label select color
     */
    public static final Color LABEL_SELECT = Color.blue;

    /**
     * Minim width of the node
     */
    private int minWidth;

    /**
     * Minim height of the height
     */
    private int minHeight;

    /**
     * The rectangle that defines the node position
     */
    private Rectangle rectangle;

    /**
     * Selected (true) or unselected (false)
     */
    private boolean state;

    /**
     * The resize flag
     */
    private boolean resize;

    /**
     * Temp widht of the rectangle
     */
    private int width;

    /**
     * Temp height of the rectangle
     */
    private int height;

    /**
     * Substract relative positin from position of the drag tool (select tool -
     * first point) to compute the selected object origin
     */
    private Point relativePosition;

    /**
     * The font for the label
     */
    private Font font;

    /**
     * The foreground color when selected node
     */
    private Color foregroundSelect;

    /**
     * The label color when selected node
     */
    private Color labelSelect;

    /**
     * The foreground color when unselected node
     */
    private Color foregroundUnselect;

    /**
     * The label color when unselected node
     */
    private Color labelUnselect;

    /**
     * Construct a node
     * 
     * @param name
     * @param rectangle
     * @param state
     */
    public NodeX(Rectangle rectangle) {
        minWidth = 70;
        minHeight = 30;

        if (rectangle.width < minWidth) {
            rectangle.width = minWidth;
        }
        if (rectangle.height < minHeight) {
            rectangle.height = minHeight;
        }

        this.rectangle = rectangle;
        this.state = false;

        relativePosition = new Point();

        font = FONT;
        foregroundUnselect = FOREGROUND_UNSELECT;
        labelUnselect = LABEL_UNSELECT;
        foregroundSelect = FOREGROUND_SELECT;
        labelSelect = LABEL_SELECT;
    }

    /**
     * Set the minim width of the node's rectangle
     * 
     * @param width
     */
    public void setMinWidth(int width) {
        this.minWidth = width;
    }

    /**
     * Get the minim width of the node's rectangle
     * 
     * @return
     */
    public int getMinWidth() {
        return minWidth;
    }

    /**
     * Set the minim height of the node's rectangle
     * 
     * @param width
     */
    public void setMinHeight(int height) {
        this.minHeight = height;
    }

    /**
     * Get the minim height of the node's rectangle
     * 
     * @return
     */
    public int getMinHeight() {
        return minHeight;
    }

    /**
     * Set the font
     * 
     * @param font
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * Get the font
     * 
     * @return
     */
    public Font getFont() {
        return font;
    }

    /**
     * Set the rectangle position
     * 
     * @param rectangle
     */
    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    /**
     * Get the node rectangle
     * 
     * @return
     */
    public Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Set the rectangle position
     * 
     * @param p
     */
    public void setRectanglePosition(Point p) {
        rectangle.x = p.x;
        rectangle.y = p.y;
    }

    /**
     * Get the rectangle top - left position
     * 
     * @return
     */
    public Point getPosition0() {
        return new Point(rectangle.x, rectangle.y);
    }

    /**
     * Get the rectangle buttom - right position
     * 
     * @return
     */
    public Point getPosition1() {
        return new Point(rectangle.x, rectangle.y + rectangle.height);
    }

    /**
     * Get the rectangle buttom - right position
     * 
     * @return
     */
    public Point getPosition2() {
        return new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
    }

    /**
     * Get the rectangle buttom - right position
     * 
     * @return
     */
    public Point getPosition3() {
        return new Point(rectangle.x + rectangle.width, rectangle.y);
    }

    /**
     * Get the resize rectangle in right - down corner
     * 
     * @return
     */
    public Rectangle getResizeRectangle() {
        return new Rectangle(rectangle.x + rectangle.width - 15, rectangle.y + rectangle.height - 15, 15, 15);
    }

    /**
     * Get the resize's rectangle position
     * 
     * @return
     */
    public Point getResizePosition() {
        return new Point(rectangle.x + rectangle.width - 15, rectangle.y + rectangle.height - 15);
    }

    /**
     * Set the state of the object
     * 
     * @param state
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Get the state of the object
     * 
     * @return
     */
    public boolean getState() {
        return state;
    }

    /**
     * Get the node center (visual stye) Used in drawSide method
     * 
     * @return
     */
    public Point getCenter() {
        Point center = new Point();
        center.x = rectangle.x + rectangle.width / 2;
        center.y = rectangle.y + rectangle.height / 2;
        return center;
    }

    /**
     * Get the name position
     * 
     * @return
     */
    public Point getNamePosition() {
        return new Point(rectangle.x + 5, rectangle.y + 15);
    }

    /**
     * Verify if the point in contained in the rectangle
     * 
     * @param p
     * @return
     */
    public boolean rectangleContains(Point p) {
        return rectangle.contains(p);
    }

    /**
     * Set the relative position
     * 
     * @param p
     */
    public void setRelativePositionTo(Point p) {
        relativePosition = new Point(rectangle.x - p.x, rectangle.y - p.y);
    }

    /**
     * Set the relative position
     * 
     * @param p
     */
    public void setRelativePosition(Point p) {
        relativePosition = p;
        width = rectangle.width;
        height = rectangle.height;
    }

    /**
     * Get the relative position
     * 
     * @return
     */
    public Point getRelativePosition() {
        return relativePosition;
    }

    /**
     * Compute the selected object position
     * 
     * @return
     */
    public void computeRectanglePosition(Point p) {
        rectangle.x = p.x + relativePosition.x;
        rectangle.y = p.y + relativePosition.y;

        // fitRectangle();
    }

    /**
     * Fit the scroll rectangle if position < 0
     *
     */
    private void fitRectangle() {
        if (rectangle.x < 0) {
            rectangle.x = 0;
        } else if (rectangle.x > 1920 - rectangle.width) {
            rectangle.x = 1920 - rectangle.width;
        }
        if (rectangle.y < 0) {
            rectangle.y = 0;
        } else if (rectangle.y > 1920 - rectangle.height) {
            rectangle.y = 1920 - rectangle.height;
        }
    }

    /**
     * Set the resize mode
     * 
     * @param resize
     */
    public void setResize(boolean resize) {
        this.resize = resize;
    }

    /**
     * Get the resize mode
     * 
     * @return
     */
    public boolean isResize() {
        return resize;
    }

    /**
     * Compute the selected object position
     * 
     * @return
     */
    public void computeRectangleDimension(Point p) {
        // System.out.println(relativePosition.x + ", " + relativePosition.y);

        rectangle.width = width + p.x - relativePosition.x;
        if (rectangle.width < minWidth) {
            rectangle.width = minWidth;
        }

        rectangle.height = height + p.y - relativePosition.y;
        if (rectangle.height < minHeight) {
            rectangle.height = minHeight;
        }
    }

    /**
     * Get the foreground color for selected mode
     * 
     * @return
     */
    public Color getForegroundSelect() {
        return foregroundSelect;
    }

    /**
     * Set the foreground color for selected mode
     * 
     * @param foregroundSelect
     */
    public void setForegroundSelect(Color foregroundSelect) {
        this.foregroundSelect = foregroundSelect;
    }

    /**
     * Get the label color for selected mode
     * 
     * @return
     */
    public Color getLabelSelect() {
        return labelSelect;
    }

    /**
     * Set the label color for selected mode
     * 
     * @param labelSelect
     */
    public void setLabelSelect(Color labelSelect) {
        this.labelSelect = labelSelect;
    }

    /**
     * Get the foreground color for unselected mode
     * 
     * @return
     */
    public Color getForegroundUnselect() {
        return foregroundUnselect;
    }

    /**
     * Set the foreground color for unselected mode
     * 
     * @param foregroundUnselect
     */
    public void setForegroundUnselect(Color foregroundUnselect) {
        this.foregroundUnselect = foregroundUnselect;
    }

    /**
     * Get the label color for unselected mode
     * 
     * @return
     */
    public Color getLabelUnselect() {
        return labelUnselect;
    }

    /**
     * Set the label color for unselected mode
     * 
     * @param labelUnselect
     */
    public void setLabelUnselect(Color labelUnselect) {
        this.labelUnselect = labelUnselect;
    }

    /**
     * Get the index color (fs, ls, fu, lu)
     * 
     * @param index
     * @return
     */
    public Color getColor(int index) {
        switch (index) {
            case 0:
                return getForegroundSelect();
            case 1:
                return getLabelSelect();
            case 2:
                return getForegroundUnselect();
            case 3:
                return getLabelUnselect();
        }

        return null;
    }

}
