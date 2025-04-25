
/**
 * Represents a Line in a two-dimensional coordinate system.
 * This class stores the x1, x2  and y1, y2 coordinates of a point.
 * It provides methods to retrieve and manipulate the point's coordinates.
 */
public class Line {
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
        if (this.x1 > this.x2 && this.y1 > this.y2) {
            return new Point(this.x1, this.y1);
        } else if (this.x1 > this.x2 && this.y1 < this.y2) {
            return new Point(this.x1, this.y2);
        } else if (this.x1 < this.x2 && this.y1 > this.y2) {
            return new Point(this.x2, this.y1);
        }
        return new Point(this.x2, this.y2);
    }

    /**
     * Ending point.
     *
     * @return checks which coordinate is smaller and returns it as a Point class.
     */
    public Point end() {
        if (this.x1 > this.x2 && this.y1 > this.y2) {
            return new Point(this.x2, this.y2);
        } else if (this.x1 > this.x2 && this.y1 < this.y2) {
            return new Point(this.x2, this.y1);
        } else if (this.x1 < this.x2 && this.y1 > this.y2) {
            return new Point(this.x1, this.y2);
        }
        return new Point(this.x1, this.y1);
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
        if (this.parallel(other)) { //lines parallel m = m but b != b so there will never be a point of intersection
            return false;
        }
        if (this.equals(other)) {
            return true;
        }
        double intPointX;
        double intPointY;
        if (this.x1 == this.x2) {
            intPointX = this.x1;
            intPointY = other.slope() * intPointX + other.coefficient();
        } else if (other.x1 == other.x2) {
            intPointX = other.x1;
            intPointY = this.slope() * intPointX + this.coefficient();
        } else if (this.start().equals(other.end()) || this.end().equals(other.start())) {
            return true;
        } else {
            intPointX = (other.coefficient() - this.coefficient()) / (this.slope() - other.slope());
            intPointY = this.slope() * intPointX + this.coefficient();
        }
        return isWithin(intPointX, intPointY, this) && isWithin(intPointX, intPointY, other);
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
        if (isIntersecting(other)) {
            if (this.equals(other)) {
                return null;
            }
            double intPointX = (other.coefficient() - this.coefficient()) / (this.slope() - other.slope());
            double intPointY = this.slope() * intPointX + this.coefficient();
            if (this.x1 == this.x2) {
                return new Point(this.x1, other.slope() * this.x1 + other.coefficient());
            } else if (other.x1 == other.x2) {
                return new Point(other.x1, this.slope() * other.x1 + this.coefficient());
            } else if (this.start().equals(other.end())) {
                return new Point(this.start().getX(), this.start().getY());
            } else if (this.end().equals(other.start())) {
                return new Point(this.end().getX(), this.end().getY());
            }
            return new Point(intPointX, intPointY);
        }
        return null;
    }

    /**
     * equals.
     *
     * @param other line.
     * @return true if equal else false.
     */
    public boolean equals(Line other) {
        if (this.x1 == this.x2 && other.x1 == other.x2) {
            return MathChecker.doubleEquals(this.x1, other.x1)
                    && (isWithin(this.start().getY(), this.start().getY(), other)
                    || isWithin(this.end().getY(), this.end().getY(), other)
                    || isWithin(other.start().getY(), other.start().getY(), this)
                    || isWithin(other.end().getY(), other.end().getY(), this));
        }
        if (this.start().equals(other.end()) || this.end().equals(other.start())) {
            return false;
        }
        return MathChecker.doubleEquals(this.slope(), other.slope())
                && MathChecker.doubleEquals(this.coefficient(), other.coefficient());
    }

    /**
     * Checks if lines are parallel to each other.
     *
     * @param other line.
     * @return true is parallel.
     */
    public boolean parallel(Line other) {
        return MathChecker.doubleEquals(this.slope(), other.slope())
                && !MathChecker.doubleEquals(this.coefficient(), other.coefficient());
    }

    /**
     * Counts slope.
     *
     * @return double value of slope.
     */
    private double slope() {
        return (this.y1 - this.y2) / (this.x1 - this.x2);
    }

    /**
     * Counts coefficient.
     *
     * @return double value of coefficient.
     */
    private double coefficient() {
        return this.y1 - this.slope() * this.x1;
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
        return ((MathChecker.doubleEquals(x, line.end().getX()) || x > line.end().getX()) && (x < line.start().getX()
                || MathChecker.doubleEquals(x, line.start().getX())) && (MathChecker.doubleEquals(y, line.end().getY())
                || y > line.end().getY()) && (y <= line.start().getY()
                || MathChecker.doubleEquals(y, line.start().getY())));
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
        if (rect.intersectionPoints(this).isEmpty()) {
            return null;
        }
        Point closest = rect.intersectionPoints(this).get(0);
        double minDistance = this.start().distance(closest);
        for (Point point : rect.intersectionPoints(this)) {
            if (point == null) {
                continue;
            }
            if (minDistance > point.distance(closest)) { //if min distance is larger then change the closest point.
                minDistance = point.distance(closest);
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
        if (this.x1 != this.x2) {
            // Calculate the y value using the line's equation (y = mx + b)
            double y = this.slope() * point.getX() + this.coefficient();
            // Check if the point's y-coordinate matches the calculated y from the equation
            return MathChecker.doubleEquals(point.getY(), y)
                    && point.getX() >= Math.min(this.start().getX(), this.end().getX())
                    && point.getX() <= Math.max(this.start().getX(), this.end().getX());
        }
        return point.getX() == this.x1 && point.getY() >= Math.min(this.start().getY(), this.end().getY())
                && point.getY() <= Math.max(this.start().getY(), this.end().getY());
    }
}