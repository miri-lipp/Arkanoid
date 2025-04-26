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
        for (Line side : getCollisionRectangle().getSides()) {
            if (side.isPointInside(collisionPoint)) {
                double dx = currentVelocity.getDx();
                double dy = currentVelocity.getDy();
                boolean horizontal = MathChecker.doubleEquals(side.start().getY(), side.end().getY());
                boolean vertical = MathChecker.doubleEquals(side.start().getX(), side.end().getX());
                if (horizontal) {
                    return new Velocity(dx, -dy);
                }
                if (vertical) {
                    return new Velocity(-dx, dy);
                }
            }
        }
        return currentVelocity;
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
