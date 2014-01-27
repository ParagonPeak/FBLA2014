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
 * @date   Dec 29, 2013
 * @update  Wrote bulk of code and commented methods for SpriteSheet
 * -----
 */
public class SpriteSheet {

    private String path;
    public final int WIDTH, HEIGHT;
    public int[] pixels;
    public static SpriteSheet player = new SpriteSheet("Resources/Textures/Characters/Player.png");
    public static SpriteSheet huntObj = new SpriteSheet("Resources/Textures/Pickups/Pickups.png");
    //public static SpriteSheet player = new SpriteSheet("Resources/Textures/Characters/RaphSprites/PlayerRaph.png");
    public static SpriteSheet sample = new SpriteSheet("Resources/Textures/Tiles/sample.gif");
    public static SpriteSheet carpet = new SpriteSheet("Resources/Textures/Tiles/office_tiles/office/out_floor/carpetff00ff.png");
    public static SpriteSheet walls = new SpriteSheet("Resources/Textures/Tiles/office_tiles/office/out_walls/cubiclesff00ff.png");
    public static SpriteSheet chair = new SpriteSheet("Resources/Textures/Tiles/office_tiles/office/out_chair/chairff00ffline.png");
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
    public SpriteSheet(String p) {
        path = p;
        WIDTH = load();
        HEIGHT = pixels.length/WIDTH;
    }
    
    /**
     * The load method is used to actually pull the data from files and pack it 
     * into the SpriteSheet objects..
     * Returns width of image
     */
    private int load() {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            int w = image.getWidth();
            int h = image.getHeight();
            pixels = new int[w * h];
            image.getRGB(0, 0, w, h, pixels, 0, w);
            return w;
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(Level.SEVERE, "Could not load SpriteSheet " + path, ex);
        }
        return 0;
    }
}
