/*
 * Created on Dec 22, 2003
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
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import graph.graphview.GraphX;

/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class GraphMap extends Panel implements MouseListener, MouseMotionListener {
    /**
     * Width of the mini map panel
     */
    public static int WIDTH = 160;

    /**
     * Height of the mini map panel
     */
    public static int HEIGHT = 160;

    /**
     * Width of the mini map panel
     */
    private int width;

    /**
     * Height of the mini map panel
     */
    private int height;

    /**
     * The graph to draw;
     */
    private GraphX graphX;

    /**
     * Dimension of the map
     */
    private Dimension dim;

    /**
     * The scale of the map
     */
    private int scale;

    /**
     * The image of the mini map
     */
    private Image image;

    /**
     * Work with the scroll rectangle true - pressed, false - released
     */
    private boolean scroll;

    /**
     * The scroll rectangle
     */
    private Rectangle scrollRectangle;

    /**
     * The relative position to the cursor when move the scroll rectangle
     */
    private Point relativePosition;

    /**
     * The origin point of the mini map
     */
    private Point origin;

    /**
     * Constructor
     * 
     * @param graphX
     */
    public GraphMap(GraphX graphX) {
        this.graphX = graphX;

        scroll = false;
        scrollRectangle = new Rectangle();
        relativePosition = new Point();

        dim = new Dimension(0, 0);

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        width = WIDTH;
        height = HEIGHT;

        origin = new Point(0, 0);
    }

    /**
     * Get the width of the mini map
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of the mini map
     */
    public int getHeight() {
        return height;
    }

    /**
     * Init the graphX of the mini map
     * 
     * @param graphX
     */
    public void initMap(GraphX graphX) {
        this.graphX = graphX;
    }

    /**
     * Set the scale of the map
     * 
     * @param scale
     */
    public void setScale(int scale) {
        this.scale = scale;
    }

    /**
     * Get the scroll flag
     * 
     * @return
     */
    public boolean isScroll() {
        return scroll;
    }

    /**
     * Paint the map
     */
    public void paint(Graphics graphics) {
        /* Init the image */
        if (!dim.equals(this.getSize())) {
            dim.width = WIDTH;
            dim.height = HEIGHT;
            image = this.createImage(dim.width, dim.height);
        }

        /* Compute the mini map dimensions */
        int maxSize = getGraphSize().width > getGraphSize().height ? getGraphSize().width : getGraphSize().height;
        int size = getGraphSize().width > getGraphSize().height ? WIDTH : HEIGHT;

        scale = maxSize / size;
        computeScrollRectangle(((GraphBuilder) this.getFrameParent()).getScrollPane());

        /* Compute the mini map height dimension */
        if (getGraphSize().width > getGraphSize().height) {
            width = WIDTH;
            height = getGraphSize().height * WIDTH / getGraphSize().width;

            origin.x = 0;
            origin.y = (HEIGHT - height) / 2;

            image = this.createImage(width, height);
        }
        /* Compute the mini map width dimension */
        else if (getGraphSize().width < getGraphSize().height) {
            width = getGraphSize().width * HEIGHT / getGraphSize().height;
            height = HEIGHT;

            origin.x = (WIDTH - width) / 2;
            origin.y = 0;

            image = this.createImage(width, height);
        }
        /* The mini map's width and height are equals */
        else {
            width = WIDTH;
            height = HEIGHT;
            origin.x = 0;
            origin.y = 0;
        }

        if (scale == 0) {
            return;
        }

        /* Begin to paint */
        Graphics g = image.getGraphics();

        g.setColor(Color.white);
        g.fillRect(0, 0, width - 1, height - 1);

        /* Draw the edges */
        for (int i = 0; i < graphX.edgesNumber(); i++) {
            if (!graphX.edgeAt(i).getState()) {
                g.setColor(graphX.edgeAt(i).getForegroundUnselect());
                Painter.drawEdge(g, graphX.edgeAt(i), scale);
            }
        }
        for (int i = 0; i < graphX.edgesNumber(); i++) {
            if (graphX.edgeAt(i).getState()) {
                g.setColor(graphX.edgeAt(i).getForegroundSelect());
                Painter.drawEdge(g, graphX.edgeAt(i), scale);
            }
        }

        /* Draw the nodes */
        for (int i = 0; i < graphX.nodesNumber(); i++) {
            if (!graphX.nodeAt(i).getState()) {
                g.setColor(graphX.nodeAt(i).getForegroundUnselect());
                Painter.drawNode(g, graphX.nodeAt(i), scale);
            }
        }
        for (int i = 0; i < graphX.nodesNumber(); i++) {
            if (graphX.nodeAt(i).getState()) {
                g.setColor(graphX.nodeAt(i).getForegroundSelect());
                Painter.drawNode(g, graphX.nodeAt(i), scale);
            }
        }

        fitScrollRectangle();

        g.setColor(Color.pink);
        g.drawRect(scrollRectangle.x, scrollRectangle.y, scrollRectangle.width - 1, scrollRectangle.height - 1);

        /* Draw the map margin */
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);

        /* Image to draw the back, too */
        Image img = createImage(WIDTH, HEIGHT);
        img.getGraphics().setColor(Color.white);
        img.getGraphics().fillRect(0, 0, WIDTH, HEIGHT);
        img.getGraphics().drawImage(image, origin.x, origin.y, Color.blue, this);

        graphics.drawImage(img, 0, 0, this);
    }

    /**
     * Update the panel (double buffering)
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * Get the preferred size (overwritten)
     */
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    /**
     * Compute the scroll rectangle
     *
     */
    public void computeScrollRectangle(ScrollPane scrollPane) {
        if (!scroll) {
            scrollRectangle.x = scrollPane.getScrollPosition().x / scale;
            scrollRectangle.y = scrollPane.getScrollPosition().y / scale;
            scrollRectangle.width = scrollPane.getViewportSize().width / scale;
            scrollRectangle.height = scrollPane.getViewportSize().height / scale;
        }
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

    /**
     * Set the relative position
     * 
     * @param p
     */
    private void setRelativePositionTo(Point p) {
        relativePosition = new Point(scrollRectangle.x - p.x, scrollRectangle.y - p.y);
    }

    /**
     * Compute the selected object position
     * 
     * @return
     */
    private void computeRectanglePosition(Point p) {
        scrollRectangle.x = p.x + relativePosition.x;
        scrollRectangle.y = p.y + relativePosition.y;

        fitScrollRectangle();
    }

    /**
     * Fit the scroll rectangle if position < 0
     *
     */
    private void fitScrollRectangle() {
        if (scrollRectangle.x < 0) {
            scrollRectangle.x = 0;
        } else if (scrollRectangle.x > width - scrollRectangle.width) {
            scrollRectangle.x = width - scrollRectangle.width;
        }
        if (scrollRectangle.y < 0) {
            scrollRectangle.y = 0;
        } else if (scrollRectangle.y > height - scrollRectangle.height) {
            scrollRectangle.y = height - scrollRectangle.height;
        }
    }

    /**
     * Get the graphX size
     * 
     * @return
     */
    private Dimension getGraphSize() {
        return ((GraphBuilder) getFrameParent()).getGraph().getPreferredSize();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.
     * MouseEvent)
     */
    public void mouseDragged(MouseEvent e) {
        if (scroll && e.getPoint().x >= origin.x && e.getPoint().y >= origin.y && e.getPoint().x <= origin.x + width
                && e.getPoint().y <= origin.y + height) {
            computeRectanglePosition(e.getPoint());

            ((GraphBuilder) this.getFrameParent()).getScrollPane().setScrollPosition(scrollRectangle.x * scale,
                    scrollRectangle.y * scale);

            repaint();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
     */
    public void mouseMoved(MouseEvent e) {
        Rectangle scrollRectangle = new Rectangle(this.scrollRectangle);
        scrollRectangle.x += origin.x;
        scrollRectangle.y += origin.y;
        if (scrollRectangle.contains(e.getPoint())) {
            this.setCursor(GraphBuilder.hand);
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
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent e) {
        Rectangle scrollRectangle = new Rectangle(this.scrollRectangle);

        /* Set the rectangle's position according to the origin */
        scrollRectangle.x += origin.x;
        scrollRectangle.y += origin.y;

        if (scrollRectangle.contains(e.getPoint())) {
            scroll = true;
            setRelativePositionTo(e.getPoint());
        } else {
            /* Set the rectangle's position according to the mouse position */
            this.scrollRectangle.x = e.getPoint().x - (this.scrollRectangle.width / 2) - origin.x;
            this.scrollRectangle.y = e.getPoint().y - (this.scrollRectangle.height / 2) - origin.y;

            scroll = true;
            setRelativePositionTo(e.getPoint());

            fitScrollRectangle();

            ((GraphBuilder) this.getFrameParent()).getScrollPane().setScrollPosition(this.scrollRectangle.x * scale,
                    this.scrollRectangle.y * scale);

            setCursor(GraphBuilder.hand);
        }

        repaint();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent e) {
        scroll = false;
        fitScrollRectangle();
        repaint();

        ((GraphBuilder) getFrameParent()).getGraph().requestFocus();
    }

}
