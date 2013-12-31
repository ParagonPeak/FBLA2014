package com.FBLA.businesssim.graphics;

import com.FBLA.businesssim.input.Keyboard;
import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class purpose: Create the bulk of the screen that has to load/render sprites
 * ----- <<<<<<< HEAD @a
 *
 *
 * uthor Tripp and Raphael
 * @date Dec 29, 2013
 * @update Wrote the bulk of code and commented in method uses. Still need to
 * create Tile class. Will be changed to use higher res rendering. ----- =======
 *
 * @author Tripp and Raphael
 * @date Dec 26, 2013
 * @update Wrote the bulk of code and commented in method uses. Still need to
 * create Tile class. Will be changed to use higher res rendering. ----- >>>>>>>
 * Adding Text
 */
public class Screen {

    public int width, height; //of screen
    public int[] pixels;
    public int mapwidth = 64; //image file should be 64 px by 64 px
    private int mapsize = mapwidth * mapwidth;
    public int[] tiles = new int[mapsize]; //64 tiles in both directions
    public int xOffs, yOffs; //position to look at
    public Graphics g;

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
        renderRaisedSprite(xp, yp, sprite);
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
        for (int i = 0; i < pixels.length; i++) {
            //pixels[i] = 0x000000;
            pixels[i] = Sprite.voidSprite.pixels[0]; // figured it should be cleared with the same color as the voidSprite
        }
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
    public int[] isoTo2D(double x, double y) {
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
    public int[] twoDToIso(double x, double y) {
        int[] iso = {(int) (x - y), (int) ((x + y) / 2)};
        return iso;
    }
    /**
     * This method will display a line of text in a text box for the player to
     * read.
     *
     * @param lines is the array of all the text to display.
     * @param key keyboard to check if the key is pressed.
     */
    int index = 0;
    boolean textRequiresUpdate = false;
    private boolean lastKeyAction = false;

    public Graphics displayText(String[] lines, Keyboard key, Graphics g) {
        //The lines currently on the screen, with a max of three to following convention
        if (g == null || lines == null || key == null || textRequiresUpdate) {
            lastKeyAction = false;
            return g;
        }
        g.setColor(Color.gray);
        g.fillRect(25, 375, width - 50, height - 400);
        g.setColor(Color.BLACK);
        String[] displayedLines = new String[3];
        for (int i = index; i < index + 3 && i < lines.length; i++) {
            displayedLines[i - index] = lines[i];
        }
        if (!lastKeyAction && key.action) {
            index++;
            System.out.println("INCREASE");
        }
        lastKeyAction = key.action;
        if (index >= lines.length - 3) {
            textRequiresUpdate = true;
            index = 0;
            System.out.println("Waiting for update!");
        }
        return drawText(displayedLines, g);
    }

    public Graphics drawText(String[] lines, Graphics g) {
        if (g == null || lines == null) {
            return g;
        }
//        System.out.println(lines.length);
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) {
                return g;
            }
            g.drawString(lines[i], 30, ((i + 1) * 25 + 375));
        }
        return g;
    }

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
}
