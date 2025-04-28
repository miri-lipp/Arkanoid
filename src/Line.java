import java.util.List;

/**
 * Represents a Line in a two-dimensional coordinate system.
 * This class stores the x1, x2  and y1, y2 coordinates of a point.
 * It provides methods to retrieve and manipulate the point's coordinates.
 */
public class Line {
    public static final double DOUBLE = 1e-10;
    // constructors
    private final double x1;
    private final double y1;
    private final double x2;
    private final double y2;

    /**
     * Line constructor.
     *
     * @param start The start point coordinate of line.
     * @param end   The end point coordinate of line.
     */
    public Line(Point start, Point end) {
        this.x1 = start.getX();
        this.y1 = start.getY();
        this.x2 = end.getX();
        this.y2 = end.getY();
    }

    /**
     * Line constructor.
     *
     * @param x1 the starting x coordinate of line.
     * @param y1 the starting y coordinate of line.
     * @param x2 the ending x coordinate of line.
     * @param y2 the ending y coordinate of line.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    /**
     * Length of line.
     *
     * @return the length of line in double.
     */
    public double length() {
        Point p1 = new Point(this.x1, this.y1);
        Point p2 = new Point(this.x2, this.y2);
        return p2.distance(p1);
    }

    /**
     * middle of line.
     *
     * @return the middle point coordinates of line as a Point class.
     */
    public Point middle() {
        return new Point((this.x1 + this.x2) / 2, (this.y1 + this.y2) / 2);
    }

    /**
     * Starting point.
     *
     * @return checks which coordinate is greater and returns it as a Point class.
     */
    public Point start() {
        if (x1 < x2 || (MathChecker.doubleEquals(x1, x2) && y1 < y2)) {
            return new Point(x1, y1);
        } else {
            return new Point(x2, y2);
        }
    }

    /**
     * Ending point.
     *
     * @return checks which coordinate is smaller and returns it as a Point class.
     */
    public Point end() {
        if (x1 > x2 || (MathChecker.doubleEquals(x1, x2) && y1 > y2)) {
            return new Point(x1, y1);
        } else {
            return new Point(x2, y2);
        }
    }

    /**
     * checks if lines are intersecting.
     *
     * @param other the other line.
     * @return true if lines intersecting, false if not.
     */
    public boolean isIntersecting(Line other) {
        if (this.start().equals(this.end()) || other.start().equals(other.end())) { //if line is a point
            return false;
        }
        if (this.equals(other)) {
            return true;
        }
        Point intersection = calculateIntersectionPoint(other);
        return intersection != null && isWithin(intersection.getX(), intersection.getY(), this)
                && isWithin(intersection.getX(), intersection.getY(), other);
    }

    private Point calculateIntersectionPoint(Line other) {
        double m1 = this.slope();
        double m2 = other.slope();
        double b1 = this.coefficient();
        double b2 = other.coefficient();
        if (MathChecker.doubleEquals(m1, m2)) {
            return null; // parallel (or same line, handled elsewhere)
        }
        double x, y;
        if (Double.isInfinite(m1)) {
            // This line vertical
            x = this.x1;
            y = m2 * x + b2;
        } else if (Double.isInfinite(m2)) {
            // Other line vertical
            x = other.x1;
            y = m1 * x + b1;
        } else {
            x = (b2 - b1) / (m1 - m2);
            y = m1 * x + b1;
        }
        return new Point(x, y);
    }

    /**
     * Checks if two lines are intercepting with the third one.
     *
     * @param other1 first line.
     * @param other2 second line.
     * @return true if intercepting false else.
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /**
     * intersection with.
     *
     * @param other line.
     * @return point class of point in which lines are intercepting. if line is the same, or they are not then null.
     */
    public Point intersectionWith(Line other) {
        if (!isIntersecting(other)) {
            return null;
        }
        if (this.equals(other)) {
            return null;
        }
        return calculateIntersectionPoint(other);
    }

    /**
     * equals.
     *
     * @param other line.
     * @return true if equal else false.
     */
    public boolean equals(Line other) {
        return this.start().equals(other.start()) && this.end().equals(other.end())
                || (this.start().equals(other.end()) && this.end().equals(other.start()));
    }

    /**
     * Counts slope.
     *
     * @return double value of slope.
     */
    private double slope() {
        if (MathChecker.doubleEquals(this.x1, this.x2)) {
            // Vertical line => infinite slope, handled separately
            return Double.POSITIVE_INFINITY;
        }
        return (this.y2 - this.y1) / (this.x2 - this.x1);
    }

    /**
     * Counts coefficient.
     *
     * @return double value of coefficient.
     */
    private double coefficient() {
        if (MathChecker.doubleEquals(this.x1, this.x2)) {
            // Vertical line => no coefficient (y = mx + b does not exist)
            return Double.NaN;
        }
        return this.y1 - slope() * this.x1;
    }

    /**
     * Checks if the point is within the segment of the line.
     *
     * @param x    x coordinate.
     * @param y    coordinate.
     * @param line line.
     * @return true if it is within.
     */
    public boolean isWithin(double x, double y, Line line) {
        double startX = Math.min(line.start().getX(), line.end().getX());
        double endX = Math.max(line.start().getX(), line.end().getX());
        double startY = Math.min(line.start().getY(), line.end().getY());
        double endY = Math.max(line.start().getY(), line.end().getY());
        return (x >= startX - DOUBLE && x <= endX + DOUBLE && y >= startY - DOUBLE && y <= endY + DOUBLE);
    }

    // If this line does not intersect with the rectangle, return null.
    // Otherwise, return the closest intersection point to the
    // start of the line.

    /**
     * Closest intersection point.
     *
     * @param rect rectangle object.
     * @return point with the smallest distance from.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersections = rect.intersectionPoints(this);
        if (intersections.isEmpty()) {
            return null;
        }
        Point closest = rect.intersectionPoints(this).get(0);
        double minDistance = this.start().distance(closest);
        for (Point point : rect.intersectionPoints(this)) {
            if (point == null) {
                continue;
            }
            if (minDistance > this.start().distance(point)) { //if min distance is larger then change the closest point.
                minDistance = this.start().distance(point);
                closest = point;
            }
        }
        return closest;
    }

    /**
     * Checks if the point is inside line.
     *
     * @param point Point object.
     * @return true if point inside false otherwise.
     */
    public boolean isPointInside(Point point) {
        if (!MathChecker.doubleEquals(this.x1, this.x2)) {
            double expectedY = this.slope() * point.getX() + this.coefficient();
            return MathChecker.doubleEquals(point.getY(), expectedY)
                    && point.getX() >= Math.min(this.start().getX(), this.end().getX()) - DOUBLE
                    && point.getX() <= Math.max(this.start().getX(), this.end().getX()) + DOUBLE
                    && point.getY() >= Math.min(this.start().getY(), this.end().getY()) - DOUBLE
                    && point.getY() <= Math.max(this.start().getY(), this.end().getY()) + DOUBLE;
        } else {
            // Vertical line
            return MathChecker.doubleEquals(point.getX(), this.x1)
                    && point.getY() >= Math.min(this.start().getY(), this.end().getY()) - DOUBLE
                    && point.getY() <= Math.max(this.start().getY(), this.end().getY()) + DOUBLE;
        }
    }
}