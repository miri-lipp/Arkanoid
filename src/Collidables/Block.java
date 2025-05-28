package Collidables;

import GameFlow.HitListener;
import Shapes.Line;
import Shapes.MathChecker;
import Shapes.Point;
import Shapes.Rectangle;
import Sprites.Sprite;
import Sprites.Velocity;
import Sprites.Ball;
import GameFlow.HitNotifier;
import GameEnvironment.Game;
import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Class block implements Collidables.Collidable.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private final List<HitListener> hitListeners = new ArrayList<>();
    private final double width;
    private final double height;
    private final Point upperleft;
    private final Color color;

    /**
     * Collidables.Block constructor.
     * @param width of the rectangle.
     * @param height of the rectangle.
     * @param upperleft Shapes.Point of the rectangle.
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
    public Velocity hit(Point collisionPoint, Velocity currentVelocity, Ball hitter) {
        boolean hitVertical = false;
        boolean hitHorizontal = false;
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();
//        for (Point edge : getCollisionRectangle().getEdges()) { //if hit edge
//            //System.out.println("collision point x: " + collisionPoint.getX() + " y: " + collisionPoint.getY());
//            if (collisionPoint.equals(edge)) {
//                //System.out.println("dx: " + dx + " dy: " + dy);
//                if (dx != 0) {
//                    dx = -dx;
//                } else if (dy != 0) {
//                    dy = -dy;
//                }
//                //System.out.println("New Sprites.Velocity x: " + dx + " y: " + dy);
//                return new Velocity(dx, dy);
//            }
//        }
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
        if (hitHorizontal) { //if horizontal change dy to -dy
            dy = -dy;
        }
        if (hitVertical) { //if vertical change dx to -dx
            dx = -dx;
        }
        if (!ballColorMatch(hitter)) {
            this.notifyHit(hitter);
           // hitter.setColor(this.color);
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

    /**
     * Checks if color of Ball matches color of block.
     * @param ball Ball object.
     * @return true if matches.
     */
    public boolean ballColorMatch(Ball ball) {
        return ball.getColor().equals(this.color);
    }

    /**
     * Removes block from game.
     * @param g Game object.
     */
    public void removeFromGame(Game g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<>(this.hitListeners);
        // Notify all listeners about a hit event:
        if (!listeners.isEmpty()) {
            for (HitListener hl : listeners) {
                hl.hitEvent(this, hitter);
            }
        }
    }

    /**
     * Getter of color.
     * @return color.
     */
    public java.awt.Color getColor() {
        return this.color;
    }
}
