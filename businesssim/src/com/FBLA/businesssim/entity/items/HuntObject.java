/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.entity.items;

import com.FBLA.businesssim.entity.Entity;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.util.Vector2d;

/**
 *
 * @author Tripp
 */
public class HuntObject extends Entity {
    
    Screen screen;

    public HuntObject(Vector2d v, Sprite s, Screen sc) {
        super(v);
        sprite = s;
        screen = sc;
    }

    public HuntObject(int x, int y, Sprite s, Screen sc) {
        this(new Vector2d(x, y), s, sc);
    }
    
    
    public void render()
    {
        screen.renderRaisedMob(v.getiX(), v.getiY(), sprite);
    }
    
    public void event()
    {
        remove();
    }
}
