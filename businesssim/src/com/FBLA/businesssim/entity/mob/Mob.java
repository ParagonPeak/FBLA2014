package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.Entity;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.level.Level;
import com.FBLA.businesssim.util.Node;
import com.FBLA.businesssim.util.Vector2d;

/**
 * Mob Purpose: The template for all moving entities
 *
 * @needs Movement, collision, actions, AI, tracking, stats, etc 
 * -----
 * @author Tripp and Raphael
 * @date Dec 25, 2013
 * @update Created the class and some constructors. 
 *          added collision and move methods
 * -----
 */
public class Mob extends Entity {

    protected double speed = 4;
    protected boolean moving = false;
    
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

//    protected Tile currentTile()
//    {
//        int newX1 = (v.x) >> 4,
//            newY1 = (v.y) >> 4;
//        
//        return com.FBLA.businesssim.level.getTile(newX1, newY1);
//    }
    public void moveToNode(Node end) {
    }

    public void aMove(Node end) {
    }
    
    public boolean collision(double d, boolean isDy) {
        int tileX;
        int tileY;
        if(isDy) {
            tileX = (int) (v.getX()) >> 5;
            tileY = (int) (v.getY() + d) >> 5;
        } else {
            tileX = (int) (v.getX() + d) >> 5;
            tileY = (int) (v.getY()) >> 5;
        }
        if(tileX < 0 || tileX >= BusinessSim.level.width) {
            return true;
        }
        if(tileY < 0 || tileY >= BusinessSim.level.height) {
            return true;
        }
        if(BusinessSim.level.getObject(tileX, tileY).sprite.equals(Sprite.emptySprite)) { // if it's empty
            return false;
        }
        return true; // if it's not empty
    }
    
    public void move(double dx, double dy) {
        v.add(dx, dy);
    }
}
