package com.FBLA.businesssim.level.tile;

import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;

/**
 *
 * @author Tripp
 */
public class Tile {
    public static int size = 1 << 8; 
    public static Tile voidTile = new Tile(new Sprite(Tile.size, 0x00ffff));
    
    public Tile(Sprite s)
    {
    
    }
    
    public void render(int x, int y, Screen s)
    {
    
    }
}
