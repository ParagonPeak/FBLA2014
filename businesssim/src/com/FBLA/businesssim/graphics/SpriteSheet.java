package com.FBLA.businesssim.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * SpriteSheet Purpose: handle initial loading of outside images.
 * ----
 * @author  Tripp and Raphael
 * @date   Dec 26, 2013
 * @update  Wrote bulk of code and commented methods for SpriteSheet
 * -----
 */
public class SpriteSheet {

    private String path;
    public final int WIDTH, HEIGHT;
    public int[] pixels;
    public static SpriteSheet characters = new SpriteSheet("Resources/Textures/Characters/Player.png",128, 64);
    public static SpriteSheet sample = new SpriteSheet("Resources/Textures/Tiles/sample.gif", 736, 544);
    public static SpriteSheet carpet = new SpriteSheet("Resources/Textures/Tiles/office_tiles/office/out_floor/carpetff00ff.png", 256, 128);
    public static SpriteSheet walls = new SpriteSheet("Resources/Textures/Tiles/office_tiles/office/out_walls/cubicles3.png", 256, 1024);
    //Example for loading a spritesheet. All should be static
    //public static SpriteSheet tiles = new SpriteSheet("textures/spritesheet.png", 256);

    /**
     * This creates an instance of a SpriteSheet, taking the path data, size in
     * pixels, and creating a storage area for the pixels until loading at the
     * end.
     *
     * @param p is the path that must be followed to find the resources
     * @param size the literal pixel size. This method only works with a square
     * spritesheet for a size.
     */
    public SpriteSheet(String p, int size) {
        path = p;
        WIDTH = size;
        HEIGHT = size;
        pixels = new int[size * size];
        load();
    }

    /**
     * This creates an instance of a SpriteSheet, taking the path data, height and width in
     * pixels, and creating a storage area for the pixels until loading at the
     * end.
     *
     * @param p is the path that must be followed to find the resources
     * @param width the width in pixels of the sprite sheet. 
     * @param height the height in pixels of the sprite sheet.
     */
    public SpriteSheet(String p, int width, int height)
    {
        path = p;
        WIDTH = width;
        HEIGHT = height;
        pixels = new int[width * height];
        load();
    }
    
    /**
     * The load method is used to actually pull the data from files and pack it 
     * into the SpriteSheet objects..
     */
    private void load() {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, pixels, 0, w);
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
