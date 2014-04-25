package com.FBLA.businesssim.graphics;

import java.awt.image.BufferedImage;
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
    public static SpriteSheet penguinGray = new SpriteSheet("/Textures/Characters/Penguins_Gray.png");
    public static SpriteSheet penguinBlue = new SpriteSheet("/Textures/Characters/Penguins_Blue.png");
    public static SpriteSheet background = new SpriteSheet("/Textures/backAndForeground/background.jpg");
    public static SpriteSheet player = new SpriteSheet("/Textures/Characters/Player.png");
    public static SpriteSheet huntObj = new SpriteSheet("/Textures/Pickups/Pickups.png");
    public static SpriteSheet sample = new SpriteSheet("/Textures/Tiles/sample.gif");
    public static SpriteSheet carpet = new SpriteSheet("/Textures/Tiles/office_tiles/office/out_floor/carpetff00ff.png");
    public static SpriteSheet walls = new SpriteSheet("/Textures/Tiles/office_tiles/office/out_walls/cubiclesff00ff.png");
    public static SpriteSheet chair = new SpriteSheet("/Textures/Tiles/office_tiles/office/out_chair/chairff00ffline.png");

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
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(path));
            getClass().getResourceAsStream(path).close();
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
