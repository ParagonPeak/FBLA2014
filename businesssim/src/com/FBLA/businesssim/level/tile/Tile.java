package com.FBLA.businesssim.level.tile;

import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;

/**
 *
 * @author Tripp and Raphael
 */
public class Tile {
    public static int size = 32; 
    public Sprite sprite;
    public static Tile voidTile = new Tile(new Sprite(Tile.size, 0x00ffff));
    public static Tile grassTile = new Tile(Sprite.grass);
    
    public static final int voidTileNum = 0;
    public static final int grassTileNum = 1;
    
    public static final int voidTileRGB = 0xFFFF00FF;
    public static final int grassTileRGB = 0xFF00FF00;
    
    public Tile(Sprite s)
    {
        sprite = s;
    }
    
    public void render(int x, int y, Screen s)
    {
        s.renderSprite(x << 5, y << 5, sprite);
    }
}
