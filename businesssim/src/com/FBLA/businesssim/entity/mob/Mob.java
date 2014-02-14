package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.Entity;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.util.Node;
import com.FBLA.businesssim.util.Vector2d;

/**
 * Mob Purpose: The template for all moving entities
 */
public class Mob extends Entity {

    protected double speed = 4;
    protected boolean moving = false;
    protected Vector2d moveToVector = null;
    
    /**
     * @see Entity
     */
    public Mob(Vector2d v) {
        super(v, "Mob");
    }

    /**
     * @see Entity
     */
    public Mob(Vector2d v, String n) {
        super(v, "Mob: " + n);
    }

    /**
     * @see Entity
     */
    public Mob(int x, int y, String n) {
        super(x, y, "Mob: " + n);
    }

    /**
     * @see Entity
     */
    public Mob(int x, int y) {
        super(x, y, "Mob");
    }
    
    /**
     * Moves the player by the vector sent to it.
     * @param vector 
     */
    public void move(Vector2d vector) {
        v.add(vector);
    }

    /**
     * Sets a vector to be moved to
     * @param vector The vector the mob wants to move to.
     */
    public void moveTo(Vector2d vector) {
        moveToVector = vector;
    }
    
    public void moveToNode(Node end) {
    }

    public void aMove(Node end) {
    }

    /**
     * checks to see if the mob is colliding with another object
     * @param d how much the mob is changing on x or y
     * @param isDy tells if handling x or y
     * @return if there is collision
     */
    public boolean collision(double d, boolean isDy) {
        int tileX;
        int tileY;
        if (isDy) {
            tileX = (int) (v.getX()) >> 5;
            tileY = (int) (v.getY() + d) >> 5;
        } else {
            tileX = (int) (v.getX() + d) >> 5;
            tileY = (int) (v.getY()) >> 5;
        }
        if (tileX < 0 || tileX >= BusinessSim.bs.level.width) {
            return true;
        }
        if (tileY < 0 || tileY >= BusinessSim.bs.level.height) {
            return true;
        }
        if (BusinessSim.bs.level.getObject(tileX, tileY).sprite.equals(Sprite.emptySprite)) { // if it's empty
            return false;
        }
        return true; // if it's not empty
    }

    /**
     * Move the mob by x and y to a new vector
     * @param dx change in x
     * @param dy change in y
     */
    public void move(double dx, double dy) {
        v.add(dx, dy);
    }    
    
    @Override
    /**
     * Currently just used to move a mob to a certain vector
     */
    public void update() {
        if (moveToVector != null) {
            double dx = v.getX() - moveToVector.getX();
            double dy = v.getY() - moveToVector.getY();
            v.setX(dx < 0 ? v.getX() - Math.min(dx, speed): v.getX() + Math.max(dx, -speed));
            v.setY(dy < 0 ? v.getY() - Math.min(dy, speed): v.getY() + Math.max(dy, -speed));
            if(v.getX() == moveToVector.getX() && v.getY() == moveToVector.getY())
                moveToVector = null;
        }
    }
    
    /**
     * Tells if the mob is changing it's x or y position
     * @return 
     */
    public boolean isMoving()
    {
        return moving;
    }
}