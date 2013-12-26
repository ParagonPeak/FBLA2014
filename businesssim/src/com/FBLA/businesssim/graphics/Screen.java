package com.FBLA.businesssim.graphics;

/**
 * Class purpose: Create the bulk of the screen that has to load/render sprites
 * -----
 * @author    Tripp
 * @date      11/12/2013
 * @update    Wrote the bulk of code and commented in method uses. Still need to 
 *            create Tile class. Will be changed to use higher res rendering.
 * -----
 */
public class Screen {
    public int width, height; //of screen
    public int[] pixels;
    public int mapwidth = 64; //image file should be 64 px by 64 px
    private int mapsize = mapwidth * mapwidth;
    public int[] tiles = new int[mapsize]; //64 tiles in both directions
    public int xOffs, yOffs; //position to look at

    
    /**
     * Creates a Screen object, used to fill the GUI that presents the graphics
     * of our game.
     * @param w the width of the screen
     * @param h the height of the screen
     */
    public Screen(int w, int h) {
        width = w;
        height = h;
        pixels = new int[w * h];
    }

    //Commented out until Tile class is written
    /**
     * Renders a tile to the screen. 
     * 
     * @param xp the X Position of the tile's left most pixel
     * @param yp the Y position of the tile's top most pixel
     * @param t the image that will be drawn
     */
//    public void renderTile(int xp, int yp, Tile t) {
//        int size = t.sprite.SIZE; //used for the array to draw image
//        //positions become relative to where the user is looking
//        xp -= xOffs; 
//        yp -= yOffs;
//
//        for (int y = 0; y < size; y++) {
//            int ya = y + yp; // absolute position
//            for (int x = 0; x < size; x++) {
//                int xa = x + xp;
//                if (xa < 0 - size || xa >= width || ya < 0 || ya >= height) {
//                    break;
//                }
//                if (xa < 0) {
//                    xa = 0;
//                }
//                pixels[xa + (ya * width)] = t.sprite.pixels[x + (y * size)];
//            }
//        }
//    }

    /**
     * Renders a player to their relative position.
     * @param xp The left most pixel location of the object
     * @param yp The upper most pixel location of the object
     * @param sprite The image to be drawn
     */
    public void renderPlayer(int xp, int yp, Sprite sprite) {
        int w = sprite.W;
        int h = sprite.H;
        xp -= xOffs;
        yp -= yOffs;
        for (int y = 0; y < h; y++) {
            int ya = y + yp; // absolute position
            for (int x = 0; x < w; x++) {
                int xa = x + xp;
                if (xa < 0 - w || xa >= width || ya < 0 || ya >= height) {
                    break;
                }
                if (xa < 0) {
                    xa = 0;
                }
                if (sprite.pixels[x + (y * w)] != 0xffFF00FF) {
                    pixels[xa + (ya * width)] = sprite.pixels[x + (y * w)];
                }
            }
        }
    }

    /**
     * Sets the offsets of the world so that a player can manipulate the virtual
     * world
     * @param xOffs The offset in the x direction
     * @param yOffs The offset in the y direction
     */
    public void setOffset(int xOffs, int yOffs) {
        this.xOffs = xOffs;
        this.yOffs = yOffs;
    }

    /**
     * This method allows for the screen to be cleared, leaving no odd streaking
     * while rendering.
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = 0;
        }
    }
    
    /**
     * xp and yp are the Sprite's position on the screen, not the map, and are without offset
     */
    public void renderSprite(int xp, int yp, Sprite s) {
        int w = s.W;
        int h = s.H;
        xp -= xOffs;
        yp -= yOffs;
        for (int y = 0; y < h; y++) {
            int ya = y + yp; // absolute position
            for (int x = 0; x < w; x++) {
                int xa = x + xp;
                if (xa < 0 - w || xa >= width || ya < 0 || ya >= height) {
                    break;
                }
                if (xa < 0) {
                    xa = 0;
                }
                if (s.pixels[x + (y * w)] != 0xffFF00FF) {
                    pixels[xa + (ya * width)] = s.pixels[x + (y * w)];
                }
            }
        }
    }
}
