package com.FBLA.businesssim.util;

/**
 * Vector2d purpose: stores the x and y locations of every 2 is the number of
 * positions on grid ie: x, y, z i stands for integers; could use f,d,etc -----
 *
 * @author Tripp and Raphael
 * @date Dec 25 2013
 * @update Written and commented. Shouldn't need to be changed. -----
 *         added an add method with dx and dy instead of vectors for convenience
 */
public class Vector2d {

    private double x, y;

    public Vector2d() {
        set(x, y);
    }

    public Vector2d(Vector2d v) {
        set(v.x, v.y);
    }

    public Vector2d(double x, double y) {
        set(x, y);
    }

    public Vector2d add(Vector2d v)
    {
        this.x += v.getX();
        this.y += v.getY();
        return this;
    }
    
    public Vector2d add(double dx, double dy)
    {
        this.x += dx;
        this.y += dy;
        return this;
    }
    
    public Vector2d subtract(Vector2d v)
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
    
    public int getiX() {
        return (int) (x);
    }

    public int getiY() {
        return (int) (y);
    }

    public Vector2d setX(double x) {
        this.x = x;
        return this;
    }

    public Vector2d setY(double y) {
        this.y = y;
        return this;
    }
    
    public double distFrom(Vector2d v) {
        return Math.sqrt((x - v.x)*(x - v.x) + (y - v.y)*(y - v.y));
    }
}
