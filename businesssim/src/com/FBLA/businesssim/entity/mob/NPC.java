package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.graphics.Dialogue;
import com.FBLA.businesssim.graphics.DialogueDisplayer;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.util.Vector2d;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Used as a random mob to wander the halls with interactive text
 */
public class NPC extends Mob {

    public static String sayings[][][] = new String[6][0][0];
    private long timeout = 0, currentSayingTimeout = System.currentTimeMillis();
    private int dir = 0;
    private boolean isSpeaking = false;
    private String[] currentSaying;
    private Sprite[] sprites = new Sprite[4];
    

    /**
     * Constructor for the NPC.
     * This takes a vector and creates an NPC that wanders the building. It is set up
     * to use four different sets of sprites for a variety of NPCs.
     * @param v the vector that the NPC starts at
     */
    public NPC(Vector2d v) {
        super(v, "NPC");
        
        if(sayings[0].length == 0) {
            init();
        }
        
        this.currentSaying = new String[]{""};
        boolean isTux = (Math.random() * 2 < 1) ? true : false;
        boolean isBlue = (Math.random() * 2 < 1) ? true : false;
        if (isBlue && isTux) {
            sprites[0] = Sprite.backwardsPenguinBlueTuxSprite;
            sprites[1] = Sprite.backwardsPenguinBlueTuxSpriteFlip;
            sprites[2] = Sprite.penguinBlueTuxSprite;
            sprites[3] = Sprite.penguinBlueTuxSpriteFlip;
            sprite = sprites[0];
        }
        if (isBlue && !isTux) {
            sprites[0] = Sprite.backwardsPenguinBlueSprite;
            sprites[1] = Sprite.backwardsPenguinBlueSpriteFlip;
            sprites[2] = Sprite.penguinBlueSprite;
            sprites[3] = Sprite.penguinBlueSpriteFlip;
            sprite = sprites[0];
        }
        if (!isBlue && isTux) {
            sprites[0] = Sprite.backwardsPenguinGrayTuxSprite;
            sprites[1] = Sprite.backwardsPenguinGrayTuxSpriteFlip;
            sprites[2] = Sprite.penguinGrayTuxSprite;
            sprites[3] = Sprite.penguinGrayTuxSpriteFlip;
            sprite = sprites[0];
        }
        if (!isBlue && !isTux) {
            sprites[0] = Sprite.backwardsPenguinGraySprite;
            sprites[1] = Sprite.backwardsPenguinGraySpriteFlip;
            sprites[2] = Sprite.penguinGraySprite;
            sprites[3] = Sprite.penguinGraySpriteFlip;
            sprite = sprites[0];
        }
    }

    /**
     * Initializes the class
     */
    public static void init() {
        readFiles();
    }

    /**
     * Standard file reader This method pulls the 6 text files from the
     * storyboard folder, reads them, then stores all their data into an array
     * that can be accessed later by NPC's
     */
    public static void readFiles() {
        for (int floor = 0; floor < 6; floor++) {
            try {
                // get the floor's sayings file
                String fileName = "/Text/NPC_Sayings/floor" + (floor + 1) + ".txt";
                InputStream in = com.FBLA.businesssim.level.Level.class.getClass().getResourceAsStream(fileName);
                
                // create a bufferedReader for reading the file's lines
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                
                ArrayList<String[]> floorsayings = new ArrayList<>(4); // because every level has at least 4 sayings
                
                // store the sayings in the sayings array
                String line = br.readLine();
                while(line != null) {
                    floorsayings.add(line.split("&"));
                    line = br.readLine();
                }
                
                sayings[floor] = floorsayings.toArray(new String[floorsayings.size()][]);
                
                br.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error opening npc file");
                Logger.getLogger(NPC.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("Error closing npc file or reading a line");
                Logger.getLogger(NPC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

   // /*
   //  * Expands the array to avoid an OutOfBounds error
   //  */
   // public static void expandSayingsArray(int level) {
   //     String[][][] temp = sayings.clone();
   //     sayings = new String[6][sayings[level].length + 1][0];
   //     for (int first = 0; first < temp.length; first++) {
   //         System.arraycopy(temp[first], 0, sayings[first], 0, temp[first].length);
   //     }
   // }

    /**
     * Sets the script from an array of text
     *
     * @param speak What we want the character to say
     */
    public void setSpeak(String[] text) {
        currentSaying = text;
    }

    /**
     * Loads text from a specific file
     *
     * @param url Name of the file
     */
    public String[] getSaying() {
        String[] s = sayings[BusinessSim.currentLevel][(int) (Math.random() * sayings[BusinessSim.currentLevel].length)];
        if (s == null) {
            return new String[]{"Isn't this the best place ever?!", "", "(Save me...)"};
        }
        return s;
    }

    /**
     * The method that sends the current text bubble to the screen to be displayed
     */
    public void speak() {
        Dialogue saying = new Dialogue(currentSaying, this.v.getiX(), this.v.getiY());
        DialogueDisplayer.addDialogue(saying);
    }

    /**
     * Call continously in the game loop to allow for the movement, sprite change,
     * and new text displays.
     */
    @Override
    public void update() {
        //changes the direction
        if (System.currentTimeMillis() > timeout) {
            dir = (dir == 0) ? (int) (Math.random() * 4) + 1: 0;
            timeout = (dir == 0)? System.currentTimeMillis() + 1000 : System.currentTimeMillis() + (int) (Math.random() * 1000);
        }
        //Keeps the player from moving if they are being prompted
        if (BusinessSim.isPrompting) {
            moving = false;
            return;
        }
        double dx = 0, dy = 0;
        
        //Up
        if (dir == 1) {
            dy -= speed;
            //Backwards Flipped Sprite
            sprite = sprites[1];
        }
        
        //Down
        if (dir == 2) {
            dy += speed;
            //Forward Sprite
            sprite = sprites[2];
        }
        
        //Left
        if (dir == 3) {
            dx -= speed;
            //Backwards Sprite
            sprite = sprites[0];
        }
        
        //Right
        if (dir == 4) {
            dx += speed;
            //Forwards Flipped Sprite
            sprite = sprites[3];
        }

        //Checks for collision and sets movement
        moving = (dx != 0 || dy != 0);
        if (moving) {
            if (collision(dy, true)) {
                dy = 0;
            }
            if (collision(dx, false)) {
                dx = 0;
            }
            move(dx, dy);
        }

        //If the player collides with the NPC and they aren't speaking already
        //if (currentSayingTimeout < System.currentTimeMillis() && collidesWith(BusinessSim.bs.player)) {
        if (currentSayingTimeout < System.currentTimeMillis() && true) {
            //Picks one of the sayings for that floor at random
            currentSaying = getSaying();
            
            //Sets the time they can talk to to 5 seconds
            currentSayingTimeout = System.currentTimeMillis() + 5000;
            isSpeaking = true;
        }
        
        //Checks if the current saying has timed out
        if(currentSayingTimeout > System.currentTimeMillis())
            isSpeaking = false;
        
        if(isSpeaking)
          speak();  //Sends the current saying to the speech bubble object to be shown in game
    }

    @Override
    public void render(Screen screen) {
        screen.renderRaisedMob(v.getiX(), v.getiY(), sprite);
    }
}
