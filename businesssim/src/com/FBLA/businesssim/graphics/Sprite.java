package com.FBLA.businesssim.graphics;

import com.FBLA.businesssim.BusinessSim;
import java.util.ArrayList;

/**
 * Sprite purpose: Hold images, animations, and an array for the program.
 * -----
 * @author  Tripp and Raphael
 * @date    Dec 29, 2013
 * -----
 */
public class Sprite {
    public final int W, H;
    private int xInSheet, yInSheet,//coords of sprite in sprite sheet
            frame = 0;
    private boolean flipped = false;
    private final int MAX_FRAMES;
    public int[] pixels;
    private SpriteSheet sheet;
    public static ArrayList<Sprite> sprites = new ArrayList<>();
    public static Sprite voidSprite = new Sprite(32, 32, 0x121212);
    public static Sprite smallRedSprite = new Sprite(2, 2, 0xff0000);
    public static Sprite smallYellowSprite = new Sprite(2, 2, 0xffff00);
    public static Sprite emptySprite = new Sprite(32, 32, 0xFFFF00FF);
    public static Sprite playerSprite = new Sprite(32,64,0,0,SpriteSheet.player, 4, 8);
    public static Sprite playerSpriteFlip = new Sprite(playerSprite);
    public static Sprite backwardsPlayerSprite = new Sprite(32,64,0,1,SpriteSheet.player, 4, 8);
    public static Sprite backwardsPlayerSpriteFlip = new Sprite(backwardsPlayerSprite);
    
    public static Sprite huntSprite1 = new Sprite(32,64,0,0,SpriteSheet.huntObj, 8);
    public static Sprite huntSprite2 = new Sprite(32,64,0,1,SpriteSheet.huntObj, 8);
    
    public static Sprite penguinBlueTuxSpriteFlip = new Sprite(32,64,0,0,SpriteSheet.penguinBlue, 4, 8);
    public static Sprite penguinBlueTuxSprite = new Sprite(penguinBlueTuxSpriteFlip);
    public static Sprite backwardsPenguinBlueTuxSpriteFlip = new Sprite(32,64,1,0,SpriteSheet.penguinBlue, 4, 8);
    public static Sprite backwardsPenguinBlueTuxSprite = new Sprite(backwardsPenguinBlueTuxSpriteFlip);
    
    public static Sprite penguinBlueSpriteFlip = new Sprite(new Sprite(32,64,2,0,SpriteSheet.penguinBlue, 4, 8));
    public static Sprite penguinBlueSprite = new Sprite(penguinBlueSpriteFlip);
    public static Sprite backwardsPenguinBlueSpriteFlip = new Sprite(new Sprite(32,64,3,0,SpriteSheet.penguinBlue, 4, 8));
    public static Sprite backwardsPenguinBlueSprite = new Sprite(backwardsPenguinBlueSpriteFlip);
    
    public static Sprite penguinGrayTuxSpriteFlip = new Sprite(32,64,0,0,SpriteSheet.penguinGray, 4, 8);
    public static Sprite penguinGrayTuxSprite = new Sprite(penguinGrayTuxSpriteFlip);
    public static Sprite backwardsPenguinGrayTuxSpriteFlip = new Sprite(32,64,1,0,SpriteSheet.penguinGray, 4, 8);
    public static Sprite backwardsPenguinGrayTuxSprite = new Sprite(backwardsPenguinGrayTuxSpriteFlip);
    
    public static Sprite penguinGraySpriteFlip = new Sprite(new Sprite(32,64,2,0,SpriteSheet.penguinGray, 4, 8));
    public static Sprite penguinGraySprite = new Sprite(penguinGraySpriteFlip);
    public static Sprite backwardsPenguinGraySpriteFlip = new Sprite(new Sprite(32,64,3,0,SpriteSheet.penguinGray, 4, 8));
    public static Sprite backwardsPenguinGraySprite = new Sprite(backwardsPenguinGraySpriteFlip);
    
//    public static Sprite grass = new Sprite(32, 32, 352/32, 320/32, SpriteSheet.sample, 1);
    
    public static Sprite checkerboardFloor = new Sprite(64, 32, 1, 2, SpriteSheet.carpet, 1);
    public static Sprite solidLightFloor = new Sprite(64, 32, 0, 0, SpriteSheet.carpet, 1);
    public static Sprite solidDarkFloor = new Sprite(64, 32, 3, 3, SpriteSheet.carpet, 1);
    
    public static Sprite smallWallSW        = new Sprite(64, 128, 0, 0, SpriteSheet.walls, 1);
    public static Sprite smallWallSE        = new Sprite(64, 128, 1, 0, SpriteSheet.walls, 1);
    public static Sprite smallWallSW2       = new Sprite(64, 128, 2, 0, SpriteSheet.walls, 1);
    public static Sprite smallWallSE2       = new Sprite(64, 128, 3, 0, SpriteSheet.walls, 1);
    public static Sprite bigWallSW          = new Sprite(64, 128, 0, 1, SpriteSheet.walls, 1);
    public static Sprite bigWallSE          = new Sprite(64, 128, 1, 1, SpriteSheet.walls, 1);
    public static Sprite cornerE            = new Sprite(64, 128, 2, 1, SpriteSheet.walls, 1);
    public static Sprite cornerS            = new Sprite(64, 128, 3, 1, SpriteSheet.walls, 1);
    public static Sprite cornerW            = new Sprite(64, 128, 0, 2, SpriteSheet.walls, 1);
    public static Sprite cornerN            = new Sprite(64, 128, 1, 2, SpriteSheet.walls, 1);
    public static Sprite tWallSW            = new Sprite(64, 128, 2, 2, SpriteSheet.walls, 1);
    public static Sprite tWallNW            = new Sprite(64, 128, 3, 2, SpriteSheet.walls, 1);
    public static Sprite tWallNE            = new Sprite(64, 128, 0, 3, SpriteSheet.walls, 1);
    public static Sprite tWallSE            = new Sprite(64, 128, 1, 3, SpriteSheet.walls, 1);
    public static Sprite crossWall          = new Sprite(64, 128, 2, 3, SpriteSheet.walls, 1);
    public static Sprite platformWallSW     = new Sprite(64, 128, 3, 3, SpriteSheet.walls, 1);
    public static Sprite platformWallSE     = new Sprite(64, 128, 0, 4, SpriteSheet.walls, 1);
    public static Sprite cornerPlatformE    = new Sprite(64, 128, 1, 4, SpriteSheet.walls, 1);
    public static Sprite cornerPlatformN    = new Sprite(64, 128, 2, 4, SpriteSheet.walls, 1);
    public static Sprite cornerPlatformW    = new Sprite(64, 128, 3, 4, SpriteSheet.walls, 1);
    public static Sprite tPlatformSW        = new Sprite(64, 128, 0, 5, SpriteSheet.walls, 1);
    public static Sprite tPlatformSE        = new Sprite(64, 128, 1, 5, SpriteSheet.walls, 1);
    public static Sprite platformCross      = new Sprite(64, 128, 2, 5, SpriteSheet.walls, 1);
    public static Sprite crossSidePlatforms = new Sprite(64, 128, 3, 5, SpriteSheet.walls, 1);
    public static Sprite computerWallSW     = new Sprite(64, 128, 0, 6, SpriteSheet.walls, 1);
    public static Sprite computerWallSE     = new Sprite(64, 128, 1, 6, SpriteSheet.walls, 1);
    public static Sprite deskWallSE         = new Sprite(64, 128, 2, 6, SpriteSheet.walls, 1);
    public static Sprite deskWallSW         = new Sprite(64, 128, 3, 6, SpriteSheet.walls, 1);
    public static Sprite raisedCabinetSE    = new Sprite(64, 128, 0, 7, SpriteSheet.walls, 1);
    public static Sprite raisedCabinetSW    = new Sprite(64, 128, 1, 7, SpriteSheet.walls, 1);
    public static Sprite paperPlatformS     = new Sprite(64, 128, 2, 7, SpriteSheet.walls, 1);
    public static Sprite paperPlatformSW    = new Sprite(64, 128, 3, 7, SpriteSheet.walls, 1);
    public static Sprite deskSW             = new Sprite(64, 128, 0, 8, SpriteSheet.walls, 1);
    public static Sprite deskSE             = new Sprite(64, 128, 1, 8, SpriteSheet.walls, 1);
    public static Sprite deskNE             = new Sprite(64, 128, 2, 8, SpriteSheet.walls, 1);
    public static Sprite deskNW             = new Sprite(64, 128, 3, 8, SpriteSheet.walls, 1);
    public static Sprite wallSW             = new Sprite(64, 128, 0, 9, SpriteSheet.walls, 1);
    public static Sprite wallSE             = new Sprite(64, 128, 1, 9, SpriteSheet.walls, 1);
    public static Sprite cornerWall         = new Sprite(64, 128, 1, 10, SpriteSheet.walls, 1);
    public static Sprite elevatorSW         = new Sprite(64, 128, 2, 9, SpriteSheet.walls, 1);
    public static Sprite elevatorSE         = new Sprite(64, 128, 3, 9, SpriteSheet.walls, 1);
    public static Sprite chair              = new Sprite(64, 77, 0, 0, SpriteSheet.chair, 8, 10); // we'll have to make chairs into entities
    
    private byte update;
    private byte animationSpeed = 20;
    
    
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
    
    public Sprite(int width, int height, int x, int y, SpriteSheet ss, int frames, int animSpeed)
    {
        this(width, height, x, y, ss, frames);
        animationSpeed = (byte) animSpeed;
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
    
    public Sprite(int size, int x, int y, SpriteSheet ss, int frames, int animSpeed)
    {
        this(size, size, x, y, ss, frames, animSpeed);
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
     * Loads a horizontally flipped version of s
     * @param s Sprite to flip
     */
    public Sprite(Sprite s) {
        animationSpeed = s.animationSpeed;
        W = s.W;
        H = s.H;
        pixels = new int[W*H];
        MAX_FRAMES = s.MAX_FRAMES;
        sheet = s.sheet;
        xInSheet = s.xInSheet;
        yInSheet = s.yInSheet;
        flipped = true;
        loadFlippedSprite();
        sprites.add(this);
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
     * This creates a sprite from a file indicated by p
     * Besides not requiring an already made SpriteSheet object it's like all the other constructors
     */
    public Sprite(int width, int height, int x, int y, String p, int frames) {
//        W = width;
//        H = height;
        MAX_FRAMES = frames - 1;
//        pixels = new int[W*H];
        sheet = new SpriteSheet(p);
        W = sheet.WIDTH;
        H = sheet.HEIGHT;
        pixels = new int[W*H];
        xInSheet = x * W;
        yInSheet = y * H;        
        load();
        sprites.add(this);
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
    private void loadFlippedSprite() {
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                pixels[(W - 1 - x) + y * W] = sheet.pixels[(x + (frame * W) + this.xInSheet) + (this.yInSheet + y)*sheet.WIDTH]; // should frames be frames - 1? if changed, max frames constructor -1ing should change.
            }
        }
    }

    /**
     * Called every update to animate all Sprites, could be made more efficient 
     * to only animate what's on screen. Could be unnecessary.
     */
    public static void update() {
        for (int i = 0; i < sprites.size(); i++) {
            if(sprites.get(i) == Sprite.backwardsPenguinBlueSprite || 
                    sprites.get(i) == Sprite.backwardsPenguinBlueSpriteFlip || 
                    sprites.get(i) == Sprite.penguinBlueSpriteFlip || 
                    sprites.get(i) == Sprite.penguinBlueSprite || 
                    sprites.get(i) == Sprite.backwardsPenguinGraySprite || 
                    sprites.get(i) == Sprite.backwardsPenguinGraySpriteFlip || 
                    sprites.get(i) == Sprite.penguinGraySprite || 
                    sprites.get(i) == Sprite.penguinGraySpriteFlip)
            {
                //Code to animate each individual penguin
            }
            else if(sprites.get(i) == Sprite.playerSprite ||
                    sprites.get(i) == Sprite.playerSpriteFlip ||
                    sprites.get(i) == Sprite.backwardsPlayerSprite||
                    sprites.get(i) == Sprite.backwardsPlayerSpriteFlip)
            {
                sprites.get(i).animate(BusinessSim.bs.player.isMoving());
            }
            else
                    sprites.get(i).animate();
            }
        }

    /**
     * Animates a sprite by switching frames. Switches up to 3 times per second.
     */
    private void animate() {
        if(++update % animationSpeed == 0)
        {
            if (frame < MAX_FRAMES) {
                frame++;
            } else {
                frame = 0;
            }
            update = 0;
        } 
        if(flipped) {
            loadFlippedSprite();
        } else {
            load();
        }
    }
    
    /**
     * Animates a sprite that is capable of moving. Default frame 0 if not
     * moving. Can make the character "skip" if pressed fast enough. Animates
     * at about 8 frames per second
     * @param moving Tells if character should use walking animation
     */
    public void animate(boolean moving) 
    {
        if(++update % animationSpeed == 0)
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
        if(flipped) {
            loadFlippedSprite();
        } else {
            load();
        }
    }
}