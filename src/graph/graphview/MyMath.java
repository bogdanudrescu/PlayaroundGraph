/*
 * Created on Dec 22, 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package graph.graphview;

import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class MyMath {
    /**
     * Compute the area of a triangle
     * 
     * @param p0
     * @param p1
     * @param p2
     * @return
     */
    public static double area(Point p0, Point p1, Point p2) {
        return ((((double) p1.x - (double) p0.x) * ((double) p2.y - (double) p0.y))
                - (((double) p2.x - (double) p0.x) * ((double) p1.y - (double) p0.y))) / 2;
    }

    /**
     * Verify is the two lines are intersecting (p0, p1 and p2, p3)
     * 
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static boolean intersects(Point p0, Point p1, Point p2, Point p3) {
        if (area(p0, p1, p2) * area(p0, p1, p3) > 0) {
            return false;
        }
        if (area(p2, p3, p0) * area(p2, p3, p1) > 0) {
            return false;
        }

        return true;
    }

    /**
     * Compute the distance between the point (p0) and the edge (p1, p2)
     * 
     * @param p0
     * @param p1
     * @param p2
     * @return
     */
    public static double distance(Point p0, Point p1, Point p) {
        /* p0 - p1 */
        double d = Math.sqrt(Math.pow(p1.x - p0.x, 2) + Math.pow(p1.y - p0.y, 2));
        /* p - p0 */
        double d0 = Math.sqrt(Math.pow(p.x - p0.x, 2) + Math.pow(p.y - p0.y, 2));
        /* p - p1 */
        double d1 = Math.sqrt(Math.pow(p.x - p1.x, 2) + Math.pow(p.y - p1.y, 2));

        /* p0 - proj(p) */
        double dd0 = ((Math.pow(d0, 2) - Math.pow(d1, 2)) / d + d) / 2;
        double dd1 = (d - (Math.pow(d0, 2) - Math.pow(d1, 2)) / d) / 2;
        /* p - proj(p) */
        double dist = Math.sqrt(Math.pow(d0, 2) - Math.pow(dd0, 2));

        return dist;
    }

    /**
     * 
     * @param p0
     * @param p1
     * @return
     */
    public static double distance(Point p0, Point p1) {
        return Math.sqrt(Math.pow(p1.x - p0.x, 2) + Math.pow(p1.y - p0.y, 2));
    }

    /**
     * Compute the middle point of a line (p0, p1)
     * 
     * @param p0
     * @param p1
     * @return
     */
    public static Point middle(Point p0, Point p1) {
        return new Point((p0.x + p1.x) / 2, (p0.y + p1.y) / 2);
    }

    /**
     * Get the angle of two vectors
     * 
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static double angle(Point p0, Point p1, Point p2, Point p3) {
        double x0 = p1.x - p0.x;
        double y0 = p1.y - p0.y;
        double x1 = p3.x - p2.x;
        double y1 = p3.y - p2.y;

        return Math.acos((x0 * x1 + y0 * y1) / (Math.sqrt(x0 * x0 + y0 * y0) * Math.sqrt(x1 * x1 + y1 * y1)));
    }

    /**
     * Compute the vector of the two points
     * 
     * @param p0
     * @param p1
     * @return
     */
    public static Point vector(Point p0, Point p1) {
        return new Point(p1.x - p0.x, p1.y - p0.y);
    }

    /**
     * Verify if the triangle(p1, p2, p3) contains point p0
     * 
     * @param p0
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static boolean triangleContains(Point p0, Point p1, Point p2, Point p3) {
        if (area(p1, p2, p3) < 0 && area(p2, p0, p3) < 0 && area(p0, p1, p3) < 0 && area(p0, p1, p2) < 0) {
            return true;
        } else if (area(p1, p2, p3) > 0 && area(p2, p0, p3) > 0 && area(p0, p1, p3) > 0 && area(p0, p1, p2) > 0) {
            return true;
        }

        return false;
    }

    /**
     * Verify if p is between p0 and p1 wiht an error
     * 
     * @param p
     * @return
     */
    public static boolean pointsContains(Point p0, Point p1, Point p, double error) {
        /* p0 - p1 */
        double d = Math.sqrt(Math.pow(p1.x - p0.x, 2) + Math.pow(p1.y - p0.y, 2));
        /* p - p0 */
        double d0 = Math.sqrt(Math.pow(p.x - p0.x, 2) + Math.pow(p.y - p0.y, 2));
        /* p - p1 */
        double d1 = Math.sqrt(Math.pow(p.x - p1.x, 2) + Math.pow(p.y - p1.y, 2));

        /* p0 - proj(p) */
        double dd0 = ((Math.pow(d0, 2) - Math.pow(d1, 2)) / d + d) / 2;
        double dd1 = (d - (Math.pow(d0, 2) - Math.pow(d1, 2)) / d) / 2;
        /* p - proj(p) */
        double dist = Math.sqrt(Math.pow(d0, 2) - Math.pow(dd0, 2));

        if (dist < error && dd0 < d && dd1 < d) {
            return true;
        }
        return false;
    }

    /**
     * Compute the rectangle of a string with a specific font
     * 
     * @param string
     * @param font
     * @return
     */
    public static Rectangle stringBounds(String string, Font font) {
        Rectangle rectangle = font.getStringBounds(string, new FontRenderContext(new AffineTransform(), false, false))
                .getBounds();
        return rectangle;
    }

}
