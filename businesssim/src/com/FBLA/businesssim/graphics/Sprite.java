package com.FBLA.businesssim.graphics;

import java.util.ArrayList;

/**
 * Sprite purpose: Hold images, animations, and an array for the program.
 * -----
 * @author  Tripp and Raphael
 * @date    Dec 25, 2013
 * @update  Wrote/commented class
 * -----
 */
public class Sprite {
    public final int W, H;
    private int xInSheet, yInSheet,//coords of sprite in sprite sheet
            frame = 0;
    private final int MAX_FRAMES;
    public int[] pixels;
    private SpriteSheet sheet;
    public static ArrayList<Sprite> sprites = new ArrayList<>();
    public static Sprite voidSprite = new Sprite(32, 32, 0x00A0CC);
    public static Sprite playerSprite = new Sprite(32,64,0,0,SpriteSheet.characters, 4);
    public static Sprite grass = new Sprite(32, 32, 352/32, 320/32, SpriteSheet.sample, 1);
    private byte update;
    
    
    /** 
     * Creates a Sprite object, which allows easy access to drawn images to be 
     * used as characters, NPC, or Entities later in the game.
     * @param width The literal width of the Sprite in pixels
     * @param height The literal height of the Sprite in pixels
     * @param x The left most pixel position, used to find cord
     * @param y The top most pixel position, used to find cord
     * @param ss SpriteSheet that contains the desired image to be pulled
     * @param frames How long animations are for said Sprite. (Min 1)
     */
    public Sprite(int width, int height, int x, int y, SpriteSheet ss, int frames) {
        W = width;
        H = height;
        this.xInSheet = x * width;
        this.yInSheet = y * height;
        pixels = new int[W * H];
        if(frames < 1) frames = 1;
        MAX_FRAMES = frames - 1;
        sheet = ss;
        load();
        sprites.add(this);
    }

    /**
     * Creates a square sprite
     * @param size The height/length in pixels of the Sprite
     * @param x X position in Sprite Sheet
     * @param y Y position in Sprite Sheet
     * @param ss The Sprite Sheet that the sprite is pulled from
     * @param frames If animated, frames above 1, minimum 1 frame (not animated)
     */
    public Sprite(int size, int x, int y, SpriteSheet ss, int frames)
    {
        this(size, size, x, y, ss, frames);
    }
    
    /**
     * Sprite of a solid color
     * @param color the integer of the color to turn sprite
     */
    public Sprite(int width, int height, int color) {
        MAX_FRAMES = 1 - 1;
        W = width;
        H = height;  
        pixels = new int[W * H];
        setColor(color);
    }

    /**
     * Set the color of sprite to a solid color
     * @param color color to fill array with (makes solid color)
     */
    private void setColor(int color) {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = color;
        }
    }

    /**
     * Pulls the image from the sprite sheet
     */
    private void load() {
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                pixels[x + y * W] = sheet.pixels[(x + (frame * W) + this.xInSheet) + (this.yInSheet + y)*sheet.WIDTH]; // should frames be frames - 1? if changed, max frames constructor -1ing should change.
            }
        }
    }

    /**
     * Called every update to animate all Sprites, could be made more efficient 
     * to only animate what's on screen. Could be unnecessary.
     */
    public static void update() {
        for (int i = 0; i < sprites.size(); i++) {
                sprites.get(i).animate();
            }
        }

    /**
     * Animates a sprite by switching frames. Switches up to 3 times per second.
     */
    private void animate() {
        if(++update % 20 == 0)
        {
            if (frame < MAX_FRAMES) {
                frame++;
            } else {
                frame = 0;
            }
            update = 0;
        } 
        load();
    }
    
    /**
     * Animates a sprite that is capable of moving. Default frame 0 if not
     * moving. Can make the character "skip" if pressed fast enough. Animates
     * at about 8 frames per second
     * @param moving Tells if character should use walking animation
     */
    public void animate(boolean moving) 
    {
        if(++update % 8 == 0)
        {
            if (moving && (frame < MAX_FRAMES)) 
            {
                frame++;
            } else {
                frame = 0;
            }
            update = 0;
        }
        if (!moving) {
            frame = 0;
        }
        load();
    }
}