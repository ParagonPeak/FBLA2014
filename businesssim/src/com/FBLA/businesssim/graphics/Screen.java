package com.FBLA.businesssim.graphics;

import com.FBLA.businesssim.BusinessSim;
import java.awt.Graphics;

/**
 * Class purpose: Create the bulk of the screen that has to load/render sprites
 */
public class Screen {

    public int width, height; //of screen
    public int[] pixels;
    public int mapwidth = 64; //image file should be 64 px by 64 px
    private int mapsize = mapwidth * mapwidth;
    public int[] tiles = new int[mapsize]; //64 tiles in both directions
    public static int xOffs, yOffs; //position to look at    

    /**
     * Creates a Screen object, used to fill the GUI that presents the graphics
     * of our game.
     *
     * @param w the width of the screen
     * @param h the height of the screen
     */
    public Screen(int w, int h) {
        width = w;
        height = h;
        pixels = new int[w * h];
    }

    /**
     * Renders a player in the middle
     *
     * @param xp The left most pixel location of the object
     * @param yp The upper most pixel location of the object
     * @param sprite The image to be drawn
     */
    public void renderPlayer(int xp, int yp, Sprite sprite) {
        renderRaisedMob(xp, yp, sprite);
    }

    /**
     * Sets the offsets of the world so that a player can manipulate the virtual
     * world
     *
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
        int voidColor = Sprite.voidSprite.pixels[0];// + (int) ((System.currentTimeMillis() >> 3) % 255); // figured it should be cleared with the same color as the voidSprite
        for (int i = 0; i < pixels.length; i++) {
            //pixels[i] = 0x000000;
            pixels[i] = voidColor;
        }
    }
    
    /**
     * Renders the background sprite
     * Called after clear
     */
    public void renderBackground() {
        Sprite bgSprite = Sprite.background;
        int bgScale = 3;
        int bgWidth = bgSprite.W * bgScale;
        int bgHeight = bgSprite.H * bgScale;
        renderScaledSprite(-bgWidth/2 + 50 + BusinessSim.bs.level.levelNumber * 30, 50 + BusinessSim.bs.level.levelNumber * 30, bgSprite, bgScale);
    }

    /**
     * @param xp is the Sprite's X position on the screen, not the map, and are
     * without offset
     * @param yp is the Sprite's Y position on the screen, not the map, and are
     * without offset
     */
    public void renderSprite(int xp, int yp, Sprite s) {
        int w = s.W;
        int h = s.H;
        xp -= xOffs;
        yp -= yOffs;

        int[] iso = twoDToIso(xp, yp);
        xp = iso[0];
        yp = iso[1];

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
                int color = s.pixels[x + (y * w)];
                if (color != 0xffFF00FF) {
                    pixels[xa + (ya * width)] = color;
                }
            }
        }
    }
    
    /**
     * Like sprite.render but renders a sprite with an integer scale
     * Used for the background rendering
     * @param xp is the Sprite's X position on the screen, not the map, and are
     * without offset
     * @param yp is the Sprite's Y position on the screen, not the map, and are
     * without offset
     */
    public void renderScaledSprite(int xp, int yp, Sprite s, int scale) {
        int w = s.W;
        int h = s.H;
        xp -= xOffs;
        yp -= yOffs;

        int[] iso = twoDToIso(xp, yp);
        xp = iso[0];
        yp = iso[1];

        for (int y = 0; y < h; y++) {
            int ya = y*scale + yp; // absolute position
            for (int x = 0; x < w; x++) {
                int xa = x*scale + xp;
                if (xa < -scale || xa >= width || ya < -scale || ya >= height) {
                    continue;
                }
                if (xa < 0) {
                    xa = 0;
                }
                int color = s.pixels[x + (y * w)];
                if (color != 0xffFF00FF) {
                    for(int sx = 0; sx < scale; sx++) { // scalex
                        for(int sy = 0; sy < scale; sy++) {
                            int xsp = xa + sx;
                            int ysp = ya + sy;
                            if(xsp < 0 || xsp >= width || ysp < 0 || ysp >= height) {
                                continue;
                            }
                            pixels[xa + sx + ((ya + sy) * width)] = color;
                        }
                    }
                }
            }
        }
    }

    /**
     * Renders a sprite as one on the screen not on the level, could be used for
     * GUI
     */
    public void renderSpriteOnScreen(int x, int y, Sprite s) {
        renderSprite(x + xOffs, y + yOffs, s);
    }

    /**
     * For use in translating mouse clicks in isometric view to 2d coordinates
     *
     * @param x
     * @param y
     * @return
     */
    public static int[] isoTo2D(double x, double y) {
        int[] twoD = {(int) ((2 * y + x) / 2), (int) ((2 * y - x) / 2)};
        return twoD;
    }

    /**
     * Used in rendering to adjust normal top-down coordinates to ones used in
     * isometric rendering
     *
     * @param x
     * @param y
     * @return
     */
    public static int[] twoDToIso(double x, double y) {
        int[] iso = {(int) (x - y), (int) ((x + y) / 2)};
        return iso;
    }

    public void speak(int x, int y, String[] prompt, Graphics g) {
    }

    /**
     * Renders in Isometric cords and sprite moved up by the sprite height 
     * @param xp the Non-Isometric X position
     * @param yp the non-isometric Y position
     * @param s the sprite to be rendered
     */
    public void renderRaisedSprite(int xp, int yp, Sprite s) {
        int w = s.W;
        int h = s.H;
        xp -= xOffs;
        yp -= yOffs;

        int[] iso = twoDToIso(xp, yp);
        xp = iso[0];
        yp = iso[1];

        for (int y = 0; y < h; y++) {
            int ya = y + yp - h; // absolute position
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
    /**
     * Renders in Isometric cords and sprite moved up by the sprite height
     * This version slides the sprite up 20 to compensate for the base of the tiles
     * @param xp the Non-Isometric X position
     * @param yp the non-isometric Y position
     * @param s the sprite to be rendered
     */
    public void renderRaisedMob(int xp, int yp, Sprite s) {
        int w = s.W;
        int h = s.H;
        xp -= xOffs;
        yp -= yOffs;

        int[] iso = twoDToIso(xp, yp);
        xp = iso[0];
        yp = iso[1] - 20;

        for (int y = 0; y < h; y++) {
            int ya = y + yp - h; // absolute position
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
