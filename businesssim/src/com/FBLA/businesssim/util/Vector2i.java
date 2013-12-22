package com.FBLA.businesssim.util;

/**
 * Vector2i purpose: stores the x and y locations of every 2 is the number of
 * positions on grid ie: x, y, z i stands for integers; could use f,d,etc -----
 *
 * @author Tripp
 * @date 11/12/13
 * @update Written and commented. Shouldn't need to be changed. -----
 */
public class Vector2i {

    private int x, y;

    public Vector2i() {
        set(x, y);
    }

    public Vector2i(Vector2i v) {
        set(v.x, v.y);
    }

    public Vector2i(int x, int y) {
        set(x, y);
    }

    public Vector2i add(Vector2i v)
    {
        this.x += v.getX();
        this.y += v.getY();
        return this;
    }
    
    public Vector2i subtract(Vector2i v)
    {
        this.x -= v.getX();
        this.y -= v.getY();
        return this;
    }
    
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }
}
