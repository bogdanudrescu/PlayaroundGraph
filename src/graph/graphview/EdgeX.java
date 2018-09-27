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
import java.util.Vector;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class EdgeX implements Serializable {
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
     * First index point of the edge
     */
    public static final int FIRST_POINT = 0;

    /**
     * Seccond index point of the edge
     */
    public static final int SECCOND_POINT = 1;

    /**
     * First index point of the edge
     */
    public static final int MIDDLE_POINT = 2;

    /**
     * First index point of the edge
     */
    public static final int NONE_POINT = -1;

    /**
     * First Node
     */
    private NodeX node0;

    /**
     * Seccond node
     */
    private NodeX node1;

    /**
     * Selected (true) or unselected (false)
     */
    private boolean state;

    /**
     * Move state: move (true) or not move (false)
     */
    private boolean moveState;

    /**
     * The point that will be moved
     */
    private int pointToMove;

    /**
     * The point for moving the edge
     */
    private Point p;

    /**
     * The split point
     */
    private Vector splitPoints;

    /**
     * The index of the current Point
     */
    private int currentIndexPoint;

    /**
     * The relative positions for each split point
     */
    private Vector relativePositions;

    /**
     * The arrow poligon
     */
    int[] table;

    /**
     * The edge's label font
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
     * Construct a graphic edge
     * 
     * @param name
     * @param node0
     * @param node1
     * @param state
     */
    public EdgeX(NodeX node0, NodeX node1) {
        this.node0 = node0;
        this.node1 = node1;

        this.state = false;

        pointToMove = NONE_POINT;

        table = new int[6];

        splitPoints = new Vector(10);

        relativePositions = new Vector(10);

        font = FONT;
        foregroundUnselect = FOREGROUND_UNSELECT;
        labelUnselect = LABEL_UNSELECT;
        foregroundSelect = FOREGROUND_SELECT;
        labelSelect = LABEL_SELECT;
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
     * Set the first adjacent
     * 
     * @param node
     */
    public void setFirstAdjacent(NodeX node) {
        node0 = node;
    }

    /**
     * Get the first adjacent node
     * 
     * @return
     */
    public NodeX getFirstAdjacent() {
        return node0;
    }

    /**
     * Get the first edge's point
     * 
     * @return
     */
    public Point getFirstPoint() {
        if (pointToMove == FIRST_POINT) {
            return p;
        }

        Point p1 = node0.getCenter();
        Point p0 = new Point();

        if (splitPoints.size() > 0) {
            p0 = getFirstSplitPoint();
        } else {
            p0 = node1.getCenter();
        }

        /* Verify the intersection for every line of the rectangle */
        if (MyMath.intersects(p0, p1, node0.getPosition0(), node0.getPosition1())) {
            return intersection(p0, p1, node0.getPosition0(), node0.getPosition1());
        } else if (MyMath.intersects(p0, p1, node0.getPosition1(), node0.getPosition2())) {
            return intersection(p0, p1, node0.getPosition1(), node0.getPosition2());
        } else if (MyMath.intersects(p0, p1, node0.getPosition2(), node0.getPosition3())) {
            return intersection(p0, p1, node0.getPosition2(), node0.getPosition3());
        } else if (MyMath.intersects(p0, p1, node0.getPosition3(), node0.getPosition0())) {
            return intersection(p0, p1, node0.getPosition3(), node0.getPosition0());
        }
        return node0.getCenter();
    }

    /**
     * Get the seccond edge's point
     * 
     * @return
     */
    public Point getSeccondPoint() {
        if (pointToMove == SECCOND_POINT) {
            return p;
        }

        Point p1 = node1.getCenter();
        Point p0 = new Point();

        if (splitPoints.size() > 0) {
            p0 = getLastSplitPoint();
        } else {
            p0 = node0.getCenter();
        }

        /* Verify the intersection for every line of the rectangle */
        if (MyMath.intersects(p0, p1, node1.getPosition0(), node1.getPosition1())) {
            return intersection(p0, p1, node1.getPosition0(), node1.getPosition1());
        } else if (MyMath.intersects(p0, p1, node1.getPosition1(), node1.getPosition2())) {
            return intersection(p0, p1, node1.getPosition1(), node1.getPosition2());
        } else if (MyMath.intersects(p0, p1, node1.getPosition2(), node1.getPosition3())) {
            return intersection(p0, p1, node1.getPosition2(), node1.getPosition3());
        } else if (MyMath.intersects(p0, p1, node1.getPosition3(), node1.getPosition0())) {
            return intersection(p0, p1, node1.getPosition3(), node1.getPosition0());
        }
        return node1.getCenter();
    }

    /**
     * Set the seccond adjacent
     * 
     * @param node
     */
    public void setSeccondAdjacent(NodeX node) {
        node1 = node;
    }

    /**
     * Get the first adjacent node
     * 
     * @return
     */
    public NodeX getSeccondAdjacent() {
        return node1;
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
     * Get the name position
     * 
     * @return
     */
    public Point getNamePosition() {
        Point p0 = getFirstPoint();
        Point p1 = getSeccondPoint();

        if (splitPoints.size() > 1) {
            p0 = splitPointAt(splitPoints.size() / 2 - 1);
            p1 = splitPointAt(splitPoints.size() / 2);
        } else if (splitPoints.size() > 0) {
            p0 = splitPointAt(0);
        }

        return MyMath.middle(p0, p1);
    }

    /**
     * Get a rectangle with edge's selectable area
     * 
     * @return
     */
    public Rectangle getRectangle() {
        Point p = getNamePosition();
        return new Rectangle(p.x, p.y - 11, 28, 14);
    }

    /**
     * Get the position of the rectangle
     * 
     * @return
     */
    public Point getRectanglePosition() {
        Rectangle rectangle = getRectangle();
        return new Point(rectangle.x, rectangle.y);
    }

    /**
     * Set the move state
     * 
     * @param moveState
     */
    public void setMoveState(boolean moveState) {
        this.moveState = moveState;
    }

    /**
     * Get the move state
     * 
     * @return
     */
    public boolean isMoving() {
        return moveState;
    }

    /**
     * Set the index of the point to be moved
     * 
     * @param index
     */
    public void setPointToMove(int index) {
        pointToMove = index;
    }

    /**
     * Set the point that will be moved by pressing the edge on point p
     * 
     * @param p
     */
    public void setPointToMove(Point p) {
        Point p0 = getFirstPoint();
        Point p1 = getSeccondPoint();

        Point p0m = new Point();
        Point p1m = new Point();

        if (splitPoints.size() > 0) {
            p0m = getFirstSplitPoint();
            p1m = getLastSplitPoint();
        } else {
            p0m = MyMath.middle(p0, p1);
            p1m = MyMath.middle(p0, p1);
        }

        double l;

        Point p0f = new Point();
        l = 20d / MyMath.distance(p0, p0m);
        p0f.x = (int) ((1 - l) * p0.x + l * p0m.x);
        p0f.y = (int) ((1 - l) * p0.y + l * p0m.y);

        Point p1f = new Point();
        l = 20d / MyMath.distance(p1, p1m);
        p1f.x = (int) ((1 - l) * p1.x + l * p1m.x);
        p1f.y = (int) ((1 - l) * p1.y + l * p1m.y);

        if (MyMath.pointsContains(p0, p0f, p, 3d)) {
            pointToMove = FIRST_POINT;
        } else if (MyMath.pointsContains(p1, p1f, p, 3d)) {
            pointToMove = SECCOND_POINT;
        }
    }

    /**
     * Get the point that will be moved
     * 
     * @return
     */
    public int getPointToMove() {
        return pointToMove;
    }

    /**
     * Set the pixel where the edge will be moved
     * 
     * @param p
     */
    public void setPoint(Point p) {
        this.p = p;
    }

    /**
     * Verify if the edge contains the point p
     * 
     * @param p
     * @return
     */
    public boolean edgeContains(Point p) {
        if (splitPoints.size() > 0) {
            if (MyMath.pointsContains(node0.getCenter(), splitPointAt(0), p, 3d)) {
                return true;
            }
            for (int i = 0; i < splitPoints.size() - 1; i++) {
                if (MyMath.pointsContains(splitPointAt(i), splitPointAt(i + 1), p, 3d)) {
                    return true;
                }
            }
            if (MyMath.pointsContains(getLastSplitPoint(), node1.getCenter(), p, 3d)) {
                return true;
            }
        } else if (MyMath.pointsContains(node0.getCenter(), node1.getCenter(), p, 3d)) {
            return true;
        }

        return false;
    }

    /**
     * Get the subedge index that contains the point
     * 
     * @param p
     * @return
     */
    private int getSubedgeIndex(Point p) {
        if (splitPoints.size() > 0) {
            if (MyMath.pointsContains(node0.getCenter(), splitPointAt(0), p, 3d)) {
                return 0;
            }
            for (int i = 0; i < splitPoints.size() - 1; i++) {
                if (MyMath.pointsContains(splitPointAt(i), splitPointAt(i + 1), p, 3d)) {
                    return i + 1;
                }
            }
            if (MyMath.pointsContains(getLastSplitPoint(), node1.getCenter(), p, 3d)) {
                return splitPoints.size();
            }
        } else if (MyMath.pointsContains(node0.getCenter(), node1.getCenter(), p, 3d)) {
            return 0;
        }

        return -1;
    }

    /**
     * Verify if the edge's rectangle contains the point
     * 
     * @param p
     * @return
     */
    public boolean rectangleContains(Point p) {
        return getRectangle().contains(p);
    }

    /**
     * Verify if the arrow contains the point
     * 
     * @param p
     * @return
     */
    public boolean arrowContains(Point p) {
        Point p0 = new Point();
        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();

        p0.x = table[0];
        p0.y = table[1];
        p1.x = table[2];
        p1.y = table[3];
        p2.x = table[4];
        p2.y = table[5];

        // for(int i = 0; i < 8; i++) {
        // System.out.print(table[i] + " ");
        // }
        // System.out.println();

        // TODO Triangle not good

        if (MyMath.triangleContains(p, p0, p1, p2) && MyMath.triangleContains(p, p2, p3, p0)) {
            return true;
        }

        return false;
    }

    /**
     * Get the arrow location
     * 
     * @return
     */
    public int[] getArrowPoints() {
        Point p0 = node0.getCenter();
        Point p1 = node1.getCenter();

        if (splitPoints.size() > 0) {
            p0 = getLastSplitPoint();
        }

        /* Verify the intersection for every line of the rectangle */
        if (MyMath.intersects(p0, p1, node1.getPosition0(), node1.getPosition1())) {
            computeArrowPoints(p0, p1, node1.getPosition0(), node1.getPosition1());
        } else if (MyMath.intersects(p0, p1, node1.getPosition1(), node1.getPosition2())) {
            computeArrowPoints(p0, p1, node1.getPosition1(), node1.getPosition2());
        } else if (MyMath.intersects(p0, p1, node1.getPosition2(), node1.getPosition3())) {
            computeArrowPoints(p0, p1, node1.getPosition2(), node1.getPosition3());
        } else if (MyMath.intersects(p0, p1, node1.getPosition3(), node1.getPosition0())) {
            computeArrowPoints(p0, p1, node1.getPosition3(), node1.getPosition0());
        } else {
            for (int i = 0; i < 6; i++) {
                table[i] = -1;
            }
        }

        return table;
    }

    /**
     * Compute the arrow points
     * 
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     */
    private void computeArrowPoints(Point p0, Point p1, Point p2, Point p3) {
        Point pm = MyMath.middle(p2, p3);
        double angle = MyMath.angle(pm, p1, p0, p1);

        /* Rectangle's center and rectangle's margin */
        double dist1 = MyMath.distance(p1, pm);
        /* Mragin's middle and intersection point */
        double dist2 = Math.tan(angle) * dist1;
        /* Distance between the rectangle's center and the intersection point */
        double dist0 = Math.sqrt(dist1 * dist1 + dist2 * dist2);

        double dist = MyMath.distance(p0, p1);

        double l = dist0 / dist;

        /* First point.x */
        table[0] = (int) ((1 - l) * p1.x + l * p0.x);
        /* First point.y */
        table[1] = (int) ((1 - l) * p1.y + l * p0.y);

        l = (dist0 + 15) / dist;

        int px = (int) ((1 - l) * p1.x + l * p0.x);
        int py = (int) ((1 - l) * p1.y + l * p0.y);

        double aangle = 0.5d;

        table[2] = table[0] + (int) (Math.cos(aangle) * (px - table[0]) + Math.sin(aangle) * (py - table[1]));
        table[3] = table[1] + (int) (-Math.sin(aangle) * (px - table[0]) + Math.cos(aangle) * (py - table[1]));

        table[4] = table[0] + (int) (Math.cos(-aangle) * (px - table[0]) + Math.sin(-aangle) * (py - table[1]));
        table[5] = table[1] + (int) (-Math.sin(-aangle) * (px - table[0]) + Math.cos(-aangle) * (py - table[1]));
    }

    /**
     * Compute the intersection point of the lines (p0, p1), (p2, p3)
     * 
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    private Point intersection(Point p0, Point p1, Point p2, Point p3) {
        // Point pm = MyMath.middle(p2, p3);
        // double angle = MyMath.angle(pm, p1, p0, p1);
        //
        // /* Rectangle's center and rectangle's margin */
        // double dist1 = MyMath.distance(p1, pm);
        // /* Mragin's middle and intersection point */
        // double dist2 = Math.tan(angle) * dist1;
        // /* Distance between the rectangle's center and the intersection point
        // */
        // double dist0 = Math.sqrt(dist1 * dist1 + dist2 * dist2);
        //
        // double dist = MyMath.distance(p0, p1);
        //
        // double l = dist0 / dist;
        //
        // return new Point((int)((1 - l) * p1.x + l * p0.x),
        // (int)((1 - l) * p1.y + l * p0.y));

        Point pm = MyMath.middle(p2, p3);
        double angle = MyMath.angle(pm, p1, p0, p1);

        /* Rectangle's center and rectangle's margin */
        double dist1 = MyMath.distance(p1, pm);
        /* Mragin's middle and intersection point */
        double dist2 = Math.tan(angle) * dist1;
        /* Distance between the rectangle's center and the intersection point */
        double dist0 = Math.sqrt(dist1 * dist1 + dist2 * dist2);

        double dist = MyMath.distance(p0, p1);

        double l = dist0 / dist;

        int x = (int) ((1 - l) * p1.x + l * p0.x);
        int y = (int) ((1 - l) * p1.y + l * p0.y);

        return new Point(x, y);
    }

    /**
     * Add a split point
     * 
     * @param p
     */
    public void addSplitPoint(Point p) {
        if (!setCurrentPoint(p)) {
            currentIndexPoint = getSubedgeIndex(p);
            splitPoints.insertElementAt(p, currentIndexPoint);
        }
    }

    /**
     * Select a split point
     * 
     * @param p
     */
    public void selectSplitPoint(Point p) {
        currentIndexPoint = getSubedgeIndex(p);
    }

    /**
     * Delete the split point
     * 
     * @param p
     */
    public void deleteSplitPoint(Point p) {
        if (setCurrentPoint(p)) {
            deleteSplitPoint();
        }
    }

    /**
     * Delete the current point
     *
     */
    public void deleteSplitPoint() {
        if (currentIndexPoint >= 0 && currentIndexPoint < splitPoints.size()) {
            splitPoints.removeElementAt(currentIndexPoint);
        }
    }

    /**
     * Change the current point's position
     * 
     * @param p
     */
    public void changeSplitPoint(Point p) {
        try {
            splitPoints.setElementAt(p, currentIndexPoint);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }

    /**
     * Get the split point at the index
     * 
     * @param i
     * @return
     */
    public Point splitPointAt(int i) {
        try {
            return ((Point) splitPoints.elementAt(i));
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }

    /**
     * Get the split points number
     * 
     * @return
     */
    public int splitPointsNumber() {
        return splitPoints.size();
    }

    /**
     * Get the first split point
     * 
     * @return
     */
    public Point getFirstSplitPoint() {
        return (Point) splitPoints.firstElement();
    }

    /**
     * Get the last split point
     * 
     * @return
     */
    public Point getLastSplitPoint() {
        return (Point) splitPoints.lastElement();
    }

    /**
     * Set the currentIndexPoint index
     * 
     * @param p
     */
    public boolean setCurrentPoint(Point p) {
        for (int i = 0; i < splitPoints.size(); i++) {
            if (MyMath.distance(p, splitPointAt(i)) < 6d) {
                currentIndexPoint = i;
                return true;
            }
        }
        return false;
    }

    /**
     * There is no split point to be moved
     *
     */
    public void setNoCurrentPoint() {
        currentIndexPoint = -1;
    }

    /**
     * Set the relative position
     * 
     * @param p
     */
    public void setRelativePositionsTo(Point p) {
        relativePositions.removeAllElements();
        for (int i = 0; i < splitPoints.size(); i++) {
            relativePositions.addElement(new Point(splitPointAt(i).x - p.x, splitPointAt(i).y - p.y));
        }
    }

    private Point relativePosition(int i) {
        try {
            return (Point) relativePositions.elementAt(i);
        } catch (ArrayIndexOutOfBoundsException e) {
        }
        return null;
    }

    /**
     * Compute the selected object position
     * 
     * @return
     */
    public void computePositions(Point p) {
        for (int i = 0; i < splitPoints.size(); i++) {
            splitPoints.setElementAt(new Point(p.x + relativePosition(i).x, p.y + relativePosition(i).y), i);
        }

        // fitPositions();
    }

    /**
     * Fit the scroll rectangle if position < 0
     *
     */
    private void fitPositions() {
        for (int i = 0; i < splitPoints.size(); i++) {
            if (splitPointAt(i).x < 0) {
                splitPointAt(i).x = 0;
            } else if (splitPointAt(i).x > 1920) {
                splitPointAt(i).x = 1920;
            }
            if (splitPointAt(i).y < 0) {
                splitPointAt(i).y = 0;
            } else if (splitPointAt(i).y > 1920) {
                splitPointAt(i).y = 1920;
            }
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
