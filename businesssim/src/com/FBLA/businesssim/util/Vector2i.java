package com.FBLA.businesssim.util;

/**
 * Vector2i purpose: stores the x and y locations of every 2 is the number of
 * positions on grid ie: x, y, z i stands for integers; could use f,d,etc -----
 *
 * @author Tripp and Raphael
 * @date Dec 25 2013
 * @update Written and commented. Shouldn't need to be changed. -----
 *         added an add method with dx and dy instead of vectors for convenience
 */
public class Vector2i {

    private double x, y;

    public Vector2i() {
        set(x, y);
    }

    public Vector2i(Vector2i v) {
        set(v.x, v.y);
    }

    public Vector2i(double x, double y) {
        set(x, y);
    }

    public Vector2i add(Vector2i v)
    {
        this.x += v.getX();
        this.y += v.getY();
        return this;
    }
    
    public Vector2i add(double dx, double dy)
    {
        this.x += dx;
        this.y += dy;
        return this;
    }
    
    public Vector2i subtract(Vector2i v)
    {
        this.x -= v.getX();
        this.y -= v.getY();
        return this;
    }
    
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
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
