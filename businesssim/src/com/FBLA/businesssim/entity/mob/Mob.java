package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.Entity;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.level.Level;
import com.FBLA.businesssim.util.Node;
import com.FBLA.businesssim.util.Vector2d;

/**
 * Mob Purpose: The template for all moving entities
 *
 * @author Tripp and Raphael
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

    public void move(Vector2d vector) {
        v.add(vector);
    }

    public void moveTo(Vector2d vector) {
        moveToVector = vector;
    }
    
    public void moveToNode(Node end) {
    }

    public void aMove(Node end) {
    }

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
        if (tileX < 0 || tileX >= BusinessSim.level.width) {
            return true;
        }
        if (tileY < 0 || tileY >= BusinessSim.level.height) {
            return true;
        }
        if (BusinessSim.level.getObject(tileX, tileY).sprite.equals(Sprite.emptySprite)) { // if it's empty
            return false;
        }
        return true; // if it's not empty
    }

    public void move(double dx, double dy) {
        v.add(dx, dy);
    }    
    @Override
    public void update() {
        if (moveToVector != null) {
            double dx = v.getX() - moveToVector.getX();
            double dy = v.getY() - moveToVector.getY();
            v.setX((dx < 0) ? (dx < speed) ? v.getX() + dx : v.getX() + speed : (dx > -speed) ? v.getX() - dx : v.getX() - speed);
            v.setY((dy < 0) ? (dy < speed) ? v.getY() + dy : v.getY() + speed : (dy > -speed) ? v.getY() - dy : v.getY() - speed);
            if(v.getX() == moveToVector.getX() && v.getY() == moveToVector.getY())
                moveToVector = null;
        }
    }
}