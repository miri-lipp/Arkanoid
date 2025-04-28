import biuoop.DrawSurface;

import java.awt.Color;

/**
 * Class block implements Collidable.
 */
public class Block implements Collidable, Sprite {
    private final double width;
    private final double height;
    private final Point upperleft;
    private final Color color;
    static final int RADIUS = 5;

    /**
     * Block constructor.
     * @param width of the rectangle.
     * @param height of the rectangle.
     * @param upperleft Point of the rectangle.
     * @param color color of block.
     */
    public Block(double width, double height, Point upperleft, Color color) {
        this.width = width;
        this.height = height;
        this.upperleft = upperleft;
        this.color = color;
    }
    @Override
    public void timePassed() {
        return;
    }

    /**
     * Draws Blocks.
     * @param surface Drawsurface object.
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(Color.BLACK);
        surface.drawRectangle((int) this.upperleft.getX(), (int) this.upperleft.getY(),
                (int) this.width, (int) this.height);
        surface.setColor(this.color);
        surface.fillRectangle((int) this.upperleft.getX(), (int) this.upperleft.getY(),
                (int) this.width, (int) this.height);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(this.upperleft, this.width, this.height);
    }

    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        boolean hitVertical = false;
        boolean hitHorizontal = false;
        //checks if is hit one of the sides of the rectangle.
        for (Line side : getCollisionRectangle().getSides()) {
            if (side.isWithin(collisionPoint.getX(), collisionPoint.getY(), side)) {
                boolean horizontal = MathChecker.doubleEquals(side.start().getY(), side.end().getY());
                boolean vertical = MathChecker.doubleEquals(side.start().getX(), side.end().getX());
                if (horizontal) {
                    hitHorizontal = true;
                }
                if (vertical) {
                    hitVertical = true;
                }
            }
        }
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
        if (hitHorizontal) { //if horizontal change dy to -dy
            dy = -dy;
        }
        if (hitVertical) { //if vertical change dx to -dx
            dx = -dx;
        }
        return new Velocity(dx, dy);
    }

    /**
     * Adding block to game environment.
     * @param g game object.
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
}
