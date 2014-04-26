package com.FBLA.businesssim.level;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.items.HuntObject;
import com.FBLA.businesssim.entity.mob.NPC;
import com.FBLA.businesssim.entity.mob.Player;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.graphics.SpriteSheet;
import com.FBLA.businesssim.graphics.TextDisplayer;
import com.FBLA.businesssim.level.raisedobject.RaisedObject;
import com.FBLA.businesssim.level.tile.Tile;
import com.FBLA.businesssim.util.Vector2d;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Tripp and Raphael
 */
public class Level {

    public int width, height; // stores how many tiles the level has in each direction
    public static int[] tiles; // floor
    public static int[] objects; // walls and other "3d" things
    public String tilePath; // location of the level's tile file
    public String objPath; // location of the level's objects file
    
    public Vector2d playerV = new Vector2d(48, 128); // default location for player
    public static boolean isNearHunt = false; // true if player is near one of the objects they're hunting for
    public static boolean lastLevelText = false;
    // variables to store level variables that will change
    public static boolean[] finished = {false, false, false, false, false, false}; // indexes are levels. When level requirements are finished, the thing gets set to true
    public int levelNumber; // I put this here in case we want to hardcode some level-specific commands
    public int itemCount = 0;
    
    // variables/constants to store level specific constants to make them easier to call
    public static final int levelAmount = 6;
    public static final int totalItems = 5; // amount of HuntObjects in a level
    public static final String[] levelTilePaths = {"/Textures/Levels/Level1Tiles.png", "/Textures/Levels/Level2Tiles.png", "/Textures/Levels/Level3Tiles.png", "/Textures/Levels/Level4Tiles.png", "/Textures/Levels/Level5Tiles.png", "/Textures/Levels/Level6Tiles.png"};
    public static final String[] levelObjPaths = {"/Textures/Levels/Level1Objects.png", "/Textures/Levels/Level2Objects.png", "/Textures/Levels/Level3Objects.png", "/Textures/Levels/Level4Objects.png", "/Textures/Levels/Level5Objects.png", "/Textures/Levels/Level6Objects.png"};
    public static int[] xOff = {48, 48, 48, 48, 48, 48}; // default spawns for each level
    public static int[] yOff = {128, 128, 128, 128, 128, 128};
    public static HuntObject[][] hunt = new HuntObject[levelTilePaths.length][totalItems]; // stores all HuntObjects by level and index
    // messages displayed when you start a floor
    public static final String[][] levelMessage = {
        {"Ground Floor: ", "This is where we conduct job application testing.", "It's normally filled with more people applying."},
        {"Floor 2: ", "We store things here. ", "Luckily for you, it's very organized,", "except for the power cables near the motorized chairs.", "We just can't seem to unplug them."},
        {"Floor 3: ", "This is where we design our glue.", "We don't go ahead with anything until it's prefectly planned out!", "Why the open space, desks, and mini-maze?", "Because we planned it out perfectly that way."},
        {"Floor 4: ", "This floor is where we conduct glue testing.", "Walls and corners are strewn all about!", "Don't get stuck trying to find your way around! Hahahaha!"},
        {"Floor 5: ", "You remember the ground floor? ", "There's a reason it was designed the way it was!", "It was modeled after this floor, which we built first.", "Don't ask how."},
        {"Floor 6: ", "You made it! Congratulations.", "Wondering what to do next?", "Join FBLA!"} //Change?
    };
    // [level][pickup#][description line]
    public static final String[][][] pickUpDescriptions = {{{"JOB INTERVIEW",                    "A job interview assesses a job applicants suitability for the job they are applying to.", "In FBLA, the job interview event has two parts: a letter of application, or résumé, and a job application form.", "There is usually an interview, unless you're interviewing to our", "sister organization: Cyberdyne Systems."},
                                                            { "LIFE SMARTS",                     "The Lifesmarts competition encourages teams to learn about economics, personal finance, and consumer issues.", "The event requires a quiz and personal finance assessment while", "integrating business knowledge, critical thinking, and teamwork"},
                                                            { "INTRO TO BUSINESS",               "In this event, students learn about businesses from an aggregate and individual perspective,", "taking an objective test with topics ranging from", "business communication to technology and beyond."},
                                                            { "BUSINESS COMMUNICATIONS",         "The art of communication is vital for a thriving business.", "This event tests your English in everything from proofreading to oral communication."},
                                                            { "BUSINESS MATH",                   "A business cannot thrive without math.", "This event tests your basic business math concepts from fractions to simple interest to", "knowing that in bookkeeping, parentheses around a number means it's negative."}},
                                                         {  {"SPREADSHEET APPLICATIONS",         "Spreadsheets are necessary in all aspects of business:", "They turn data into important information for your business.", "This event tests your spreadsheet capabilities both on paper and computer."},
                                                            { "COMPUTER APPLICATIONS",           "A modern business uses computer applications in all of its daily aspects.", "This event tests your computer knowledge from terminology to e-mail to word processing to", "database applications."}, 
                                                            { "PUBLIC SPEAKING",                 "Effective oral communication in public settings is a daily part of business activity.", "This event tests your ability to make a speech on one of the goals of FBLA,", "expressing thoughts in logical, interseting ways."}, 
                                                            { "ACCOUNTING",                      "Financial record-keeping is important for business activity.", "This event tests your knowledge and abilities in the field of accounting."}, 
                                                            { "COMPUTER PROBLEM SOLVING",        "Computers are integral to businesses, and as such, so is computer problem solving.", "This event tests competencies in a wide range of topics, from", "security and operating systems to networks and printers."}},
                                                         {  {"DIGITAL DESIGN AND PROMOTION",     "Businesses have always used professional, recognizable logos to further their ends.", "This event requires an individual or team to create and present a \"digital design\", or logo."},
                                                            { "DESKTOP APPLICATION PROGRAMMING", "Desktop applications allow businesses to run smoother, making programs do the heavy work in implementing changes.", "In this event, a program must be made that can effectively serve a purpose such as signing people up for a conference"},
                                                            { "BUSINESS PRESENTATION",           "Presentations are vital to business operations, as they inform businesspeople.", "This event tasks you with creating an informative multimedia presentation on a topic, e.g. the Affordable Care Act."},
                                                            { "WORD PROCESSING",                 "Documents are staples of business, and effective word processing is effective business.", "This event tests you knowledge and abilities in creating all types of documents, ", "from memos to tables to reports."},
                                                            { "PUBLIC SERVICE ANNOUNCEMENT",     "Public Service Announcements help raise awareness on social issues.", "This exciting FBLA event requires a 30 second PSA on a specific topic,", "raising awareness of it and affecting viewers' attitudes toward it."}},
                                                         {  {"BANKING AND FINANCIAL SYSTEMS",    "Successful business ownership and management requires understanfding of financial institutions.", "This event requires takers to both take a test and ", "develop a case study on a problem", "in the banking or financial business community."}, 
                                                            { "MANAGEMENT DECISION MAKING",      "Managers need to make high-quality, instantaneous decision all the time.", "This event examines your management abilities with both an objective test and a case study on a management decision."}, 
                                                            { "BUSINESS ETHICS",                 "Ethical (moral) decisions are necessary for business and beyond.", "This event requires a case study into an ethical dilemna a business could have."}, 
                                                            { "AMERICAN ENTERPRISE PROJECT",     "This event encourages knowledge of the American Enterpries System.", "It requires an FBLA Chapter to create a program educating the school or community about American Enterprise."}, 
                                                            { "WEB SITE DESIGN",                 "Websites are integral to effective businesses.", "This event requires a specified business website to be submitted, judged, and presented."}}, 
                                                         {  {"ENTREPENEURSHIP",                  "Entrepeneurs establish and manage businesses.", "This event requires a team to present an interactive case-study on an issue that", "an entrepeneur might face."}, 
                                                            { "ECONOMICS",                       "Businesses often need to apply macro and micro-economic principles to their businesses.", "This event tests you on your familiarity with economics concepts."}, 
                                                            { "GLOBAL BUSINESS",                 "Understanding global business, from culture to laws, is important to many businesses.", "This event tests a team's knowledge and abilities in the field of global business."}, 
                                                            { "BUSINESS LAW",                    "Businesses need to understand legal boundaries.", "This event tests your knowledge in that field, from liabilities to contracts and more."}, 
                                                            { "FUTURE BUSINESS LEADER",          "This event honors outstanding FBLA members, based on", "their leadership, participation in FBLA, and knowledge.", "This event consists of a submission of a letter of application and résumé, an objective test, and an interview"}},
        {{""}} // no level 6 pickups
    }; 
//    // questions lists. questions displayed when HuntObjects are picked up
//    public static final String[][][][] levelQuestions = {Question.getLevelQuestions(1), 
//                                                         Question.getLevelQuestions(2), 
//                                                         Question.getLevelQuestions(3), 
//                                                         Question.getLevelQuestions(4), 
//                                                         Question.getLevelQuestions(5)};

    public static int defaultNPCAmount = 15;
    
    // stores all NPCs that are in the level
    ArrayList<NPC> npcArray = new ArrayList<>();
    
    // stores NPCs by coordinate
    // Why? To render NPCs properly
    // How to use:
    // The Integer coordinate is (x + y * width)
    // The ArrayList<NPC> Value stores all NPCs at that coordinate
    // Whenever NPCs move their key in HashMap changes
    // To get an NPC from a given tile you simply do npcMap.get(x + y * width) and the return ArrayList contains all NPCs at that point
    HashMap<Integer, ArrayList<NPC>> npcMap = new HashMap<>();
    
    /**
     * Creates a level from the given tiles/objects
     * @param number is the floor number - 1, aka level number
     */
    public Level(String tilePath, String objPath, int number) {
        this.tilePath = tilePath;
        this.objPath = objPath;
        this.levelNumber = number;
        loadLevelTiles();
        loadLevelObjects();
        
        addNPCs(defaultNPCAmount);
    }

    /**
     * Creates a level from the given tiles/objects
     * Gives the player a starting Vector2d of (px, py)
     * @param number is the floor number - 1, aka level number
     */
    public Level(String tilePath, String objPath, int number, double px, double py) {
        this.tilePath = tilePath;
        this.objPath = objPath;
        this.levelNumber = number;
        loadLevelTiles();
        loadLevelObjects();
        playerV.setX(px);
        playerV.setY(py);
        
        addNPCs(defaultNPCAmount);
    }

    /**
     * Generates a random level, used for testing
     *
     * @param w width of generated level (tile amount)
     * @param h height of generated level (tile amount)
     */
    protected void generateLevel(int w, int h) {
        width = w;
        height = h;
        tiles = new int[w * h];
        objects = new int[w * h];
        for (int i = 0; i < w * h; i++) {
            tiles[i] = Tile.getNum(Tile.chkFloorTile);
            objects[i] = (int) (Math.random() * 50);
        }
    }
    
    public void addNPCs(int amount) {
        for(int i = 0; i < amount; i++) {
            double npcX, npcY;
            Vector2d npcVector;
            RaisedObject objAtXY;
            
            // get random positions for the NPC until one of them is unoccupied (null or voidObject)
            do {
                npcX = Math.random() * (width << 5);
                npcY = Math.random() * (height << 5);
                npcVector = new Vector2d(npcX, npcY);
                objAtXY = getObject((int) (npcX) >> 5, (int) (npcY) >> 5);
            } while(objAtXY != null && objAtXY != RaisedObject.voidObject);
            
            npcArray.add(new NPC(npcVector)); // add the new NPC
        }
    }

    /**
     * Loads the level tiles at tilePath into tiles[]
     */
    protected void loadLevelTiles() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(tilePath));
            width = image.getWidth();
            height = image.getHeight();
            tiles = new int[width * height];
            int[] tilePixels = new int[width * height];
            image.getRGB(0, 0, width, height, tilePixels, 0, width);

            for (int i = 0; i < tilePixels.length; i++) {
                Tile t = Tile.getTileFromColor(tilePixels[i]);
                if (t != null) {
                    tiles[i] = Tile.getNum(t);
                } else {
                    tiles[i] = Tile.getNum(Tile.voidTile);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, "Could not load level tiles", ex);
        }
    }

    /**
     * Loads the level objects at objPath into objects[]
     */
    protected void loadLevelObjects() {
        try {
            BufferedImage image = ImageIO.read(getClass().getResourceAsStream(objPath));
            width = image.getWidth();
            height = image.getHeight();
            objects = new int[width * height];
            int[] objPixels = new int[width * height];
            image.getRGB(0, 0, width, height, objPixels, 0, width);

            for (int i = 0; i < objPixels.length; i++) {
                RaisedObject r = RaisedObject.getRaisedObjectFromColor(objPixels[i]);
                if (r != null) {
                    objects[i] = RaisedObject.getNum(r);
                } else {
                    objects[i] = RaisedObject.getNum(RaisedObject.voidObject);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, "Could not load level objects", ex);
        }
    }
    
    

    /**
     * Basically updates the player's relationship with the HuntObjects
     *  creates HuntObject first time a level is started
     *  knows when you're near a HuntObject 
     *  knows when you got all the HntObjects
     * When everything is collected and you're on the 6th floor displays the end text
     */
    public void update(Player p) {
        playerV = p.v;
        
        // update NPCs
        npcMap.clear();
        for(NPC n : npcArray) {
            n.update();
            int coord = (n.v.getiX() >> 5) + (n.v.getiY() >> 5) * width; // x + y * width
            
            // check if there's an NPC at that coordinate
            // if there is, add n to the ArrayList for that coordinate
            // if there isn't, make an ArrayList for that coordinate and add n
            if(npcMap.containsKey(coord)) {
                npcMap.get(coord).add(n);
            } else {
                ArrayList<NPC> coordArrayList = new ArrayList<>();
                coordArrayList.add(n);
                npcMap.put(coord, coordArrayList);
            }
        }
        
        if (levelNumber < levelAmount - 1) { // if not on last level
            if (!finished[levelNumber]) { // if level not finished
                if (hunt[levelNumber][0] == null) { // ready hunt spots if necessary
                    hunt[levelNumber] = new HuntObject[totalItems];

                    for (int i = 0; i < totalItems; i++) {
                        Vector2d huntSpot = makeHuntSpot();
                        Sprite sprite = Sprite.huntSprite2;
                        Screen sc = BusinessSim.bs.screen;
                        String[] pickupText = pickUpDescriptions[levelNumber][i];
                        //String[] questionText = levelQuestions[levelNumber][i];
                        hunt[levelNumber][i] = new HuntObject(huntSpot, sprite, sc, pickupText, levelNumber);//, questionText);
                    }
                }

                isNearHunt = false;
                // check if you're near hunt spots
                for (int i = 0; i < hunt[levelNumber].length; i++) {
                    if (!hunt[levelNumber][i].isRemoved()) {
                        if (hunt[levelNumber][i].v.distFrom(p.v) < 50) {
                            isNearHunt = true;
                        }
                    }
                }

                // if you have all hunt spots null, you're done
                finished[levelNumber] = true;
                for (int i = 0; i < hunt[levelNumber].length; i++) {
                    if (!hunt[levelNumber][i].isRemoved()) {
                        finished[levelNumber] = false;
                        break;
                    }
                }
                if (finished[levelNumber]) {
                        BusinessSim.bs.score += 20;
                        BusinessSim.bs.td.addLine("Good Job. Now enter the elevator to go to floor #" + (levelNumber + 2));
                }
            }
        } else {
            if(!lastLevelText)
            {
            BusinessSim.bs.td.addLines(new String[]{"Thank you, kind applicant!",
                "Now with your skills, we can rule the world!", 
                "How you ask?", 
                "With the glue of course!", 
                "We can use your skills to make our special, patented glue which people won't be able to resist!", 
                "No one can stop us now!"}, TextDisplayer.TEXT);
            BusinessSim.bs.td.addLines(new String[]{"","","Game over", "Join FBLA and have a great time!","To play again, return to the main menu from the pause screen"}, TextDisplayer.TEXT);
            lastLevelText = true;
            }
            finished[levelNumber] = true;
        }
                    
    }

    
    /**
     * Creates a location for a HuntObject to go that isn't too close to the player or colliding with something
     * @return 
     */
    public Vector2d makeHuntSpot() {
        boolean ready = false;
        Vector2d coords = new Vector2d(0, 0);
        while (!ready) {
            coords.setX((int) (Math.random() * width) * 32);
            coords.setY((int) (Math.random() * height) * 32);
            if (coords.distFrom(playerV) > 32 * 4 && getObject(coords.getiX() >> 5, coords.getiY() >> 5) == RaisedObject.voidObject) { // away from objects and player
                ready = true;
            }
        }
        return coords;
    }

    /**
     * Draws the level onto the screen
     */
    public void render(int xPos, int yPos, Screen screen, Player p) {
        screen.setOffset(xPos, yPos);

        // after multiple attempts at getting isometric corner pins to work I just hardcoded in the values
        int x0 = ((int) (p.v.getX()) >> 5) - 12;
        int y0 = ((int) (p.v.getY()) >> 5) - 11;
        int x1 = ((int) (p.v.getX()) >> 5) + 18;
        int y1 = ((int) (p.v.getY()) >> 5) + 19;

        // draw tiles
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                getTile(x, y).render(x - 1, y - 1, screen); // shifted over 1 to account for raised objects being 1 space higher than they should be
            }
        }
        // draw HuntObjects
        if(levelNumber < hunt.length) {
            if (hunt != null && hunt[levelNumber] != null && hunt[levelNumber][0] != null) {
                for (int i = 0; i < hunt[levelNumber].length; i++) {
                    itemCount = 0;
                    for (int j = 0; j < totalItems; j++) {
                        if (!hunt[levelNumber][j].isRemoved()) {
                            int x = hunt[levelNumber][j].v.getiX();
                            int y = hunt[levelNumber][j].v.getiX();
                            if (BusinessSim.gameState == BusinessSim.gs_inGame) {
                                hunt[levelNumber][j].render();
                            }
                            itemCount++;
                        }
                    }
                }
            }
        }
        
        // draw Objects, NPCs, and Player
        int px = (int) (p.v.getX()) >> 5;
        int py = (int) (p.v.getY()) >> 5;
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                // render object
                getObject(x, y).render(x, y, screen);
                
                // render NPCs
                ArrayList<NPC> npcsAtXY = npcAt(x, y);
                if(npcsAtXY != null) {
                    for(NPC n : npcsAtXY) {
                        n.render(screen);
                    }
                }
                
                // render player
                if (x == px && y == py) {
                    p.render(screen);
                }
            }
        }
    }
    
    /**
     * @param x x-coordinate of the tile the NPCs are on
     * @param y y-coordinate of the tile the NPCs are on
     * @return an ArrayList with every NPC on that tile
     */
    public ArrayList<NPC> npcAt(int x, int y) {
        return npcMap.get(x + y * width);
    }

    /**
     * @return tile in tiles[] at x + y * width
     */
    public Tile getTile(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.emptyTile;
        }

        int spot = tiles[x + y * width];

        Tile t = Tile.getTileFromNumber(spot);

        if (t != null) {
            return t;
        } else {
            return Tile.voidTile;
        }
    }

    
    /**
     * @return RaisedObject in objects[] at x + y * width
     */
    public RaisedObject getObject(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return RaisedObject.voidObject;
        }

        int spot = objects[x + y * width];

        RaisedObject r = RaisedObject.getRaisedObjectFromNumber(spot);

        if (r != null) {
            return r;
        } else {
            return RaisedObject.voidObject;
        }
    }

    /**
     * @return whether player is within 250 units of a HnutObject
     */
    public boolean playerNearPickup(Player p) {
        for (int i = 0; i < hunt[levelNumber].length; i++) {
            double objectDist = hunt[levelNumber][i].v.distFrom(p.v);

            if (objectDist < 250) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return whether any of the objects close to the player are elevators
     */
    public boolean playerNearElevator(Player p) {

        int x0 = ((int) (p.v.getX()) >> 5) - 2;
        int y0 = ((int) (p.v.getY()) >> 5) - 2;
        int x1 = ((int) (p.v.getX()) >> 5) + 2;
        int y1 = ((int) (p.v.getY()) >> 5) + 2;

        x0 = Math.max(0, x0);
        y0 = Math.max(0, y0);
        x1 = Math.min(width - 1, x1);
        y1 = Math.min(height - 1, y1);

        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {

                int e1 = RaisedObject.getNum(RaisedObject.elevatorSE);
                int e2 = RaisedObject.getNum(RaisedObject.elevatorSW);
                int spot = objects[x + y * width];

                if (spot == e1 || spot == e2) {
                    return true;
                }
            }
        }
        return false;
    }
}
