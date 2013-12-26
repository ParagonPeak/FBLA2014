package com.FBLA.businesssim.graphics;

/**
 * Sprite purpose: Hold images, animations, and an array for the program.
 * -----
 * @author  Tripp and Raphael
 * @date    11/12/13
 * @update  Wrote/commented class
 * -----
 */
public class Sprite {
    public final int SIZE, W, H;
    private int x, y,//cords of sprite in sprite sheet
            frame = 0;
    private final int MAX_FRAMES;
    public int[] pixels;
    private SpriteSheet sheet;
    public static Sprite[] sprites = new Sprite[0];
    public static Sprite voidSprite = new Sprite(16, 0x00A0FF);
    public static Sprite playerSprite = new Sprite(16,32,0,0,SpriteSheet.characters, 2);
    public static Sprite grass = new Sprite(32, 32, 368, 304, SpriteSheet.sample, 1);
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
        
        SIZE = width * height;
        W = width;
        H = height;
        this.x = x * width;
        this.y = y * height;
        pixels = new int[SIZE * SIZE];
        if(frames < 1) frames = 1;
        MAX_FRAMES = frames - 1;
        sheet = ss;
        load();

        Sprite[] temp = sprites;
        sprites = new Sprite[temp.length + 1];
        System.arraycopy(temp, 0, sprites, 0, temp.length);
        sprites[sprites.length - 1] = this;
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
     * Square Sprite of a solid color
     * @param size the length/height of solid sprite
     * @param color the integer of the color to turn sprite
     */
    public Sprite(int size, int color) {
        MAX_FRAMES = 1;
        W = (int) Math.sqrt(size);
        H = W;  
        SIZE = size;
        pixels = new int[SIZE * SIZE];
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
                pixels[x + y * W] = sheet.pixels[(x + (frame * W) + this.x) + (this.y + y * H)];
            }
        }
    }

    /**
     * Called every update to animate all Sprites, could be made more efficient 
     * to only animate what's on screen. Could be unnecessary.
     */
    public static void update() {
        for (int i = 0; i < sprites.length; i++) {
            sprites[i].animate();
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