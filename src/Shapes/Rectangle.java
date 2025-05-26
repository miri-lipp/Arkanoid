package Shapes;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Shapes.Rectangle.
 */
public class Rectangle {
    private final double width;
    private final double height;
    private final Point upperleft;
    // Create a new rectangle with location and width/height.

    /**
     * Shapes.Rectangle constructor.
     * @param upperLeft point of rectangle.
     * @param width width of rectangle.
     * @param height height of rectangle.
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperleft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.

    /**
     * List of intersection points.
     * @param line Shapes.Line object.
     * @return list of intersection points.
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        List<Point> intersectionPoints = new ArrayList<Point>();
        for (Line side : getSides()) {
            if (line.isIntersecting(side)) {
                intersectionPoints.add(line.intersectionWith(side));
            }
        }
        return intersectionPoints;
    }

    // Return the width and height of the rectangle

    /**
     * Getter of width.
     * @return width double.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * List of edge points of rectangle.
     * @return list of Edge points of rectangle.
     */
    public List<Point> getEdges() {
        return List.of(new Point(this.upperleft.getX(), this.upperleft.getY()),
                new Point(this.upperleft.getX() + this.width, this.upperleft.getY()),
                new Point(this.upperleft.getX(), this.upperleft.getY() + this.height),
                new Point(this.upperleft.getX() + this.width, this.upperleft.getY() + this.height));
    }

    /**
     * List of sides of the rectangle.
     * @return list of lines of the rectangle.
     */
    public List<Line> getSides() {
        return List.of(new Line(getEdges().get(0), getEdges().get(1)),
                new Line(getEdges().get(1), getEdges().get(3)),
                new Line(getEdges().get(3), getEdges().get(2)),
                new Line(getEdges().get(2), getEdges().get(0)));
    }

    /**
     * Getter of height.
     * @return height double.
     */
    public double getHeight() {
        return this.height;
    }

    // Returns the upper-left point of the rectangle.

    /**
     * Getter of starting point.
     * @return Shapes.Point.
     */
    public Point getUpperLeft() {
        return this.upperleft;
    }

//    public String toString() {
//        return getClass().getName()
//                + "[x=" + this.upperleft.getX() +
//                ",y=" + this.upperleft.getY() +
//                ",w=" + width +
//                ",h=" + height + "]";
//    }
}
