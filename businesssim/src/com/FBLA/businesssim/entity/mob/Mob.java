package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.util.Node;
import com.FBLA.businesssim.util.Vector2i;
import com.FBLA.businesssim.entity.Entity;

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
    public Mob(Vector2i v) {
        super(v, "Mob");
    }

    /**
     * @see Entity
     */
    public Mob(Vector2i v, String n) {
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

    public void move(Vector2i vector) {
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
        return false;
    }
    
    public void move(double dx, double dy) {
        v.add(dx, dy);
    }
}
