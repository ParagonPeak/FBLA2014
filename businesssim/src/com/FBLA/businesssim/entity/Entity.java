package com.FBLA.businesssim.entity;

import com.FBLA.businesssim.util.Vector2i;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.level.Level;
import java.awt.Rectangle;

/**
 * Entity Purpose: Base stamp for all mobs and items.
 * @needs collision, event, sprites
 * -----
 *
 * @author Tripp
 * @date 11/12/13
 * @update Class created. Basic variables and constructor made.
 * -----
 */
public class Entity {

    public Vector2i v;
    public String name = "";
    protected boolean removed = false;
    public Sprite sprite;
    protected Level level;
    protected Rectangle colBox;
    
    /**
     * Constructor for Entities. Names Entity, set Vector2i
     * @param v 
     * @param name
     */
    public Entity(Vector2i v, String name) {
        this.v = v;
        this.name = name;
    }

    /**
     * Constructor for Entity. Names Entity, converts cords to Vector2i
     * @param x XPosition for entity
     * @param y YPosition for entity
     * @param name The name of Entity (debugging)
     */
    public Entity(int x, int y, String name) {
        this(new Vector2i(x, y), name);
    }
    
    /**
     * Constructor for Entity. Converts cords to vector and sends a default name
     * @param x
     * @param y 
     */
    public Entity(int x, int y)
    {
        this(new Vector2i(x,y),"D_Name");
    }
    
    /**
     * Constructor for Entity. Adds default name.
     * @param v 
     */
    public Entity(Vector2i v)
    {
        this(v,"D_Name");
    }
    
    /**
     * Set the name later, if applicable
     * @param n the name of the entity
     */
    public void setName(String n)
    {
        name = n;
    }
    
    public void update() {
    }

    public void render(Screen screen) {
    }

    public void remove() {
        removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }
    
    protected void setColBox(int x, int y, int size)
    {
        colBox.setBounds(x, y, size, size);
    }
    
    /**
     * Needed to use the A* path finding method.
     * Assigns the entity a level to find possible paths;
     * @param level the current level the entity exists on.
     */
    public void init(Level level)
    {
        this.level = level;
    }
}
