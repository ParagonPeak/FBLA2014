package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.util.Vector2d;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
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
    static LineNumberReader reader = null;
    

    /**
     * Constructor for the NPC.
     * This takes a vector and creates an NPC that wanders the building. It is set up
     * to use four different sets of sprites for a variety of NPCs.
     * @param v the vector that the NPC starts at
     */
    public NPC(Vector2d v) {
        super(v, "NPC");
        this.currentSaying = new String[]{""};
        boolean isTux = (Math.random() * 2 < 1) ? true : false;
        boolean isBlue = (Math.random() * 2 < 1) ? true : false;
        if (isBlue && isTux) {
            sprites[0] = Sprite.backwardsPenguinBlueTuxSprite;
            sprites[1] = Sprite.backwardsPenguinBlueTuxSpriteFlip;
            sprites[2] = Sprite.penguinBlueTuxSprite;
            sprites[3] = Sprite.penguinBlueTuxSpriteFlip;
        }
        if (isBlue && !isTux) {
            sprites[0] = Sprite.backwardsPenguinBlueSprite;
            sprites[1] = Sprite.backwardsPenguinBlueSpriteFlip;
            sprites[2] = Sprite.penguinBlueSprite;
            sprites[3] = Sprite.penguinBlueSpriteFlip;
        }
        if (!isBlue && isTux) {
            sprites[0] = Sprite.backwardsPenguinGrayTuxSprite;
            sprites[1] = Sprite.backwardsPenguinGrayTuxSpriteFlip;
            sprites[2] = Sprite.penguinGrayTuxSprite;
            sprites[3] = Sprite.penguinGrayTuxSpriteFlip;
        }
        if (!isBlue && !isTux) {
            sprites[0] = Sprite.backwardsPenguinGraySprite;
            sprites[1] = Sprite.backwardsPenguinGraySpriteFlip;
            sprites[2] = Sprite.penguinGraySprite;
            sprites[3] = Sprite.penguinGraySpriteFlip;
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
                String fileName = "floor" + (floor + 1) + ".txt";
                File file = new File(BusinessSim.class.getResource("/Text/NPC_Sayings/" + fileName).getFile());
                
                // count how many different sayings the floor has
                int sayingsCount = countLines(file);
                
                // set the size of the sayings array for this floor to sayingsCount
                sayings[floor] = new String[sayingsCount][0];
                
                // create a bufferedReader for reading the file's lines
                BufferedReader br = new BufferedReader(new FileReader(file));
                
                // store the sayings in the sayings array
                for(int saying = 0; saying < sayingsCount; saying++) {
                    String line = br.readLine();
                    sayings[floor][saying] = line.split("&");
                }
                
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
    
    public static int countLines(File aFile) throws IOException {
        try {
            reader = new LineNumberReader(new FileReader(aFile));
            while ((reader.readLine()) != null);
            return reader.getLineNumber();
        } catch (Exception ex) {
            return -1;
        } finally { // reminder: finally blocks are run even if return is called first
            if(reader != null) 
                reader.close();
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
        String[] s = sayings[level.levelNumber][(int) (Math.random() * sayings[level.levelNumber].length)];
        if (s == null) {
            return new String[]{"Isn't this the best place ever?!", "", "(Save me...)"};
        }
        return s;
    }

    /**
     * The method that sends the current text bubble to the screen to be displayed
     */
    public void speak() {
//        BusinessSim.bs.screen.speakPrompt(v.getiX(), v.getiY(), speak, BusinessSim.bs.getGraphics());
    }

    /**
     * Call continously in the game loop to allow for the movement, sprite change,
     * and new text displays.
     */
    @Override
    public void update() {
        //changes the direction
        if (System.currentTimeMillis() < timeout) {
            dir = (dir == 0) ? (int) (Math.random() * 4) + 1 : 0;
            timeout = (dir == 0)? System.currentTimeMillis() + 3000 : 500;
        }
        //Keeps the player form moving if they are being prompted
        if (BusinessSim.isPrompting) {
            moving = false;
            return;
        }
        double dx = 0, dy = 0;
        if (dir == 1) {
            dy -= speed;
            sprite = sprites[1];
//            sprite = Sprite.backwardsPlayerSpriteFlip;
        }
        if (dir == 2) {
            dy += speed;
            sprite = sprites[2];
//            sprite = Sprite.playerSprite;
        }
        if (dir == 3) {
            dx -= speed;
            sprite = sprites[0];
//            sprite = Sprite.backwardsPlayerSprite;
        }
        if (dir == 4) {
            dx += speed;
            sprite = sprites[3];
//            sprite = Sprite.playerSpriteFlip;
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

        if (currentSayingTimeout < System.currentTimeMillis() && collidesWith(BusinessSim.bs.player)) {
            currentSaying = getSaying();
            currentSayingTimeout = System.currentTimeMillis() + 1500;
            isSpeaking = true;
        }
        if(currentSayingTimeout > System.currentTimeMillis())
            isSpeaking = false;
        if(isSpeaking)
          speak();
    }

    @Override
    public void render(Screen screen) {
        screen.renderPlayer(v.getiX(), v.getiY(), sprite);
    }
}
