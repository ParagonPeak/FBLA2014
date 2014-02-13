package com.FBLA.businesssim.level;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.items.HuntObject;
import com.FBLA.businesssim.entity.mob.Player;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.graphics.SpriteSheet;
import com.FBLA.businesssim.graphics.TextDisplayer;
import com.FBLA.businesssim.level.raisedobject.RaisedObject;
import com.FBLA.businesssim.level.tile.Tile;
import com.FBLA.businesssim.util.Vector2d;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Tripp and Raphael
 */
public class Level {

    public int width, height;
    public static int[] tiles; // floor
    public static int[] objects; // walls and other 3d things
    public int spawnX = 0;
    public int spawnY = 0;
//    public Vector2d playerV = new Vector2d(spawnX + (BusinessSim.bs.player.s.W / 2), spawnY + (BusinessSim.bs.player.s.H /2));
    public Vector2d playerV = new Vector2d(0, 128); // why is this here?
    public static Rectangle[] rects;
    public static boolean[] finished = {false, false, false, false, false, false}; // indexes are levels. When level requirements are finished, the thing gets set to true
    public String tilePath;
    public String objPath;
    public int levelNumber; // I put this here in case we want to hardcode some level-specific commands
    public int itemCount = 0;
    public static boolean isNearHunt = false;
    // arrays to store level specific values to make them easier to call and change
    public static final int levelAmount = 6;
    public static final int totalItems = 5;
    public static final String[] levelTilePaths = {"/Textures/Levels/Level0Tiles.png", "/Textures/Levels/Level2Tiles.png", "/Textures/Levels/Level3Tiles.png", "/Textures/Levels/Level4Tiles.png", "/Textures/Levels/Level0Tiles.png", "/Textures/Levels/Level3Tiles.png"};
    public static final String[] levelObjPaths = {"/Textures/Levels/Level0Objects.png", "/Textures/Levels/Level2Objects.png", "/Textures/Levels/Level3Objects.png", "/Textures/Levels/Level4Objects.png", "/Textures/Levels/Level0Objects.png", "/Textures/Levels/Level3Objects.png"};
    public static int[] xOff = {48, 48, 48, 48, 48, 48};
    public static int[] yOff = {128, 128, 128, 128, 128, 128};
    public static HuntObject[][] hunt = new HuntObject[levelTilePaths.length][totalItems];
    public static final String[][] levelMessage = {{"Ground Floor: ", "This is where we conduct job application testing.", "It's normally filled with more people applying."},
        {"Floor 2: ", "We store things here. ", "Luckily for you, it's very organized,", "except for the power cables near the motorized chairs.", "We just can't seem to unplug them."},
        {"Floor 3: ", "This is where we design our glue.", "We don't go ahead with anything until it's prefectly planned out!", "Why the open space, desks, and mini-maze?", "Because we planned it out perfectly that way."},
        {"Floor 4: ", "This floor is where we conduct glue testing.", "Walls and corners are strewn all about!", "Don't get stuck trying to find your way around! Hahahaha!"},
        {"Floor 5: ", "You remember the ground floor? ", "There's a reason it was designed the way it was!", "It was modeled after this floor, which we built first.", "Don't ask how."},
        {"Floor 6: ", "You made it! Congratulations.", "Wondering what to do next?", "Join FBLA!"} //Change?
    };
    
    // [level][pickup#][description line]
    public static final String[][][] pickUpDescriptions = {{{"JOB INTERVIEW",                    "A job interview assesses a job applicants suitability for the job they are applying to.", "In FBLA, the job interview event has two parts: a letter of application, or résumé, and a job application form.", "There is usually an interview, unless you're interviewing to our", "sister organization: Cyberdyne Systems."},
                                                            { "LIFESMARTS",                      "The Lifesmarts competition encourages teams to learn about economics, personal finance, and consumer issues.", "The event requires a quiz and personal finance assessment while", "integrating business knowledge, critical thinking, and teamwork"},
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
                                                            { "MANAGEMENT DECISION MAKING",      "Managers need to make high-quality, instantaneous decision all the time.", "This event examines your management abilities with both an objective test and a case study on a managemtn decision."}, 
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
    
    public static final String[][] level1Questions = loadQuestionsFromFile("/Questions/floor1.txt");
    public static final String[][] level2Questions = loadQuestionsFromFile("/Questions/floor2.txt");
    public static final String[][] level3Questions = loadQuestionsFromFile("/Questions/floor3.txt");
    public static final String[][] level4Questions = loadQuestionsFromFile("/Questions/floor4.txt");
    public static final String[][] level5Questions = loadQuestionsFromFile("/Questions/floor5.txt");
    public static final String[][][] levelQuestions = {level1Questions, level2Questions, level3Questions, level4Questions, level5Questions};

    public Level(String tilePath, String objPath, int number) {
        this.tilePath = tilePath;
        this.objPath = objPath;
        this.levelNumber = number;
        loadLevelTiles();
        loadLevelObjects();
//        loadLevelTiles("Resources/Textures/Levels/ExampleLevelTiles.png");
//        loadLevelObjects("Resources/Textures/Levels/ExampleLevelObjects.png");
    }

    public Level(String tilePath, String objPath, int number, double px, double py) {
        this.tilePath = tilePath;
        this.objPath = objPath;
        this.levelNumber = number;
        loadLevelTiles();
        loadLevelObjects();
        playerV.setX(px);
        playerV.setY(py);
//        loadLevelTiles("Resources/Textures/Levels/ExampleLevelTiles.png");
//        loadLevelObjects("Resources/Textures/Levels/ExampleLevelObjects.png");
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
            //tiles[i] = (int) (Math.random()*3);
            //tiles[i] = Tile.grassTileNum;
            tiles[i] = Tile.getNum(Tile.chkFloorTile);
            objects[i] = (int) (Math.random() * 50);
        }
    }

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
     * Given a file location, returns the lines of text (and the lines of text in those lines of text) in the file
     * First index is question number, Second index is line number
     */
    public static String[][] loadQuestionsFromFile(String path) {
        int QUESTION_AMOUNT = 5; // should always be 5 questions, 5 lines (4 answers and question), hence capitalization for constants
        int LINE_AMOUNT = 5;
        
        String[][] result = new String[QUESTION_AMOUNT][LINE_AMOUNT]; 
        
        try {
            InputStream in = Level.class.getClass().getResourceAsStream(path);
            BufferedReader input = new BufferedReader(new InputStreamReader(in));
            
            for(int i = 0; i < QUESTION_AMOUNT; i++) {
                String wholeLine = input.readLine();
                result[i] = wholeLine.split("#");
            }
            
            return result;
        } catch (Exception e) {
            Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, "Could not load level questions at " + path, e);
        }
        
        return null;
    }

    public void update(Player p) {
        playerV = p.v;
        if (levelNumber < levelAmount - 1) { // if not on last level
            if (!finished[levelNumber]) { // if level not finished
                if (hunt[levelNumber][0] == null) { // ready hunt spots if necessary
                    hunt[levelNumber] = new HuntObject[totalItems];

                    for (int i = 0; i < totalItems; i++) {
                        Vector2d huntSpot = makeHuntSpot();
                        Sprite sprite = Sprite.huntSprite2;
                        Screen sc = BusinessSim.bs.screen;
                        String[] pickupText = pickUpDescriptions[levelNumber][i];
                        String[] questionText = levelQuestions[levelNumber][i];
                        hunt[levelNumber][i] = new HuntObject(huntSpot, sprite, sc, pickupText, questionText);
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
                
                        BusinessSim.bs.td.addLine("Good Job. Now enter the elevator to go to floor #" + (levelNumber + 2));
                        System.out.println("Floor done");
                }
            }
        } else {
            if(finished[levelNumber] && !BusinessSim.bs.td.hasText) {
                BusinessSim.bs.changeGameState(BusinessSim.gs_credit);
            }
            BusinessSim.bs.td.addLines(new String[]{"Thank you, kind applicant!", "Now with all these, we can rule the world!", "How you ask?", "Through the addictiveness of the glue of course!", "We use all these skulls to make our special, patented glue which people won't be able to resist.", "No one can stop us now!", "Now for the final skull..."}, TextDisplayer.TEXT);
            finished[levelNumber] = true;
        }
                    
    }

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

    private void time() {
    }

    public void render(int xPos, int yPos, Screen screen, Player p) {
        screen.setOffset(xPos, yPos);

        //corner pins (tile not pixel array)
        // it's >> 6 and >> 5 since the sprites we use are 64x32 pixels
//        int x0 = (xPos >> 6);
//        int x1 = (xPos + screen.width) >> 6;
//        int y0 = yPos >> 5;
//        int y1 = (yPos + screen.height) >> 5;

//        int[] iso0 = screen.twoDToIso(x0, y0);
//        x0 = iso0[0];
//        y0 = iso0[1];
//        int[] iso1 = screen.twoDToIso(x1, y1);
//        x1 = iso1[0];
//        y1 = iso1[1];

//        int[] twoD0 = screen.isoTo2D(xPos, yPos - screen.height * 2);
//        x0 = (twoD0[0]) >> 5;
//        y0 = (twoD0[1]) >> 5;
//        int[] twoD1 = screen.isoTo2D(xPos, yPos + screen.height * 2);
//        x1 = (twoD1[0]) >> 5;
//        y1 = (twoD1[1]) >> 5;

//        int[] twoD0 = screen.isoTo2D(xPos, yPos - screen.height*2);
//        x0 = (twoD0[0]) >> 5;
//        y0 = (twoD0[1]) >> 5;
//        int[] twoD1 = screen.isoTo2D(xPos + screen.width, yPos + screen.height*2);
//        x1 = (twoD1[0]) >> 5;
//        y1 = (twoD1[1]) >> 5;

        // after multiple attempts at getting isometric corner pins to work I just render the whole level now
        int x0 = ((int) (p.v.getX()) >> 5) - 12;
        int y0 = ((int) (p.v.getY()) >> 5) - 11;
        int x1 = ((int) (p.v.getX()) >> 5) + 18;
        int y1 = ((int) (p.v.getY()) >> 5) + 19;

//        for (int y = y1 - 1; y >= y0; y--) {
//            for (int x = x0; x < x1; x++) {
//                getTile(x, y).render(x, y, screen);
//            }
//        }
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                getTile(x, y).render(x - 1, y - 1, screen); // shifted over 1 to account for raised objects being 1 space higher than they should be
            }
        }
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

        int px = (int) (p.v.getX()) >> 5;
        int py = (int) (p.v.getY()) >> 5;
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                getObject(x, y).render(x, y, screen);
                if (x == px && y == py) {
                    p.render(screen);
                }
            }
        }
    }

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

    public boolean playerNearPickup(Player p) {
//        int x0 = ((int) (p.v.getX()) >> 5) - 2;
//        int y0 = ((int) (p.v.getY()) >> 5) - 2;
//        int x1 = ((int) (p.v.getX()) >> 5) + 2;
//        int y1 = ((int) (p.v.getY()) >> 5) + 2;
//
//        x0 = Math.max(0, x0);
//        y0 = Math.max(0, y0);
//        x1 = Math.min(width - 1, x1);
//        y1 = Math.min(height - 1, y1);

        for (int i = 0; i < hunt[levelNumber].length; i++) {
            double objectDist = hunt[levelNumber][i].v.distFrom(p.v);

            if (objectDist < 250) {
                return true;
            }
        }
        return false;
    }

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
