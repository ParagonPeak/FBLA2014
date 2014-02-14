package com.FBLA.businesssim.level.tile;

import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import java.util.HashMap;

public class Tile {
    public static int width = 64;
    public static int height = 32;
    public Sprite sprite;
    protected final int NUM;
    protected final int RGB;
    protected static HashMap tilesNumMap = new HashMap(3);
    protected static HashMap tilesRGBMap = new HashMap(3);
    public static Tile voidTile = new Tile(Sprite.voidSprite, 0, 0xff00a0cc);
    public static Tile emptyTile = new Tile(Sprite.emptySprite, 1, 0xffff00ff);
    public static Tile chkFloorTile = new Tile(Sprite.checkerboardFloor, 3, 0xff404040);
    public static Tile solidLightFloorTile = new Tile(Sprite.solidLightFloor, 4, 0xffa9aebc);
    public static Tile solidDarkFloorTile = new Tile(Sprite.solidDarkFloor, 5, 0xff686e7f);

    /**
     * Constructs the floor tiles of the map
     * @param s the sprite that represents the tile
     * @param number The number representing the tile
     * @param color  The color representing the tile
     */
    public Tile(Sprite s, int number, int color) {
        sprite = s;
        NUM = number;
        RGB = color;
        tilesNumMap.put((Integer) NUM, this);
        tilesRGBMap.put((Integer) RGB, this);
    }

    public static Tile getTileFromNumber(int number) {
        return (Tile) tilesNumMap.get(number);
    }

    /**
     * Get the Tile that is represented by a color in maps
     * @param color to retrieve tile of
     * @return the tile
     */
    public static Tile getTileFromColor(int color) {
        return (Tile) tilesRGBMap.get(color);
    }

    /**
     * Get the color of a tile
     * @param t the tile
     * @return the color in hexadecimal
     */
    public static int getColor(Tile t) {
        return t.RGB;
    }

    /**
     * Get the number position of the tile
     * @param t the tile
     * @return the number representing the tile
     */
    public static int getNum(Tile t) {
        return t.NUM;
    }

    /**
     * Renders the tile in the game
     * @param x X position
     * @param y Y position
     * @param s the sprite to draw
     */
    public void render(int x, int y, Screen s) {
        s.renderSprite(x << 5, y << 5, sprite);
    }
}
