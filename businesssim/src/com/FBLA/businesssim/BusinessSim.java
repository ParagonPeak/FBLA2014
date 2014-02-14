package com.FBLA.businesssim;

import com.FBLA.businesssim.entity.items.HuntObject;
import com.FBLA.businesssim.entity.mob.Player;
import com.FBLA.businesssim.graphics.HUD;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.graphics.TextDisplayer;
import com.FBLA.businesssim.input.Keyboard;
import com.FBLA.businesssim.input.Mouse;
import com.FBLA.businesssim.level.Level;
import static com.FBLA.businesssim.level.Level.hunt;
import com.FBLA.businesssim.sound.MusicPlayer;
import com.FBLA.businesssim.util.Vector2d;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Tripp Weiner and Raphael Rouvinov-Kats To see the full progress of
 * the game build, go to the GitHub repository:
 * @url www.github.com/paragonpeak/FBLA2014
 *
 * Raphael's github:
 * @url www.github.com/coolcade
 *
 * Tripp's github:
 * @url www.github.com/TrippW
 *
 */
public class BusinessSim extends Canvas implements Runnable {

    //These are all the variables we found it neccessary to write our game
    private final int normWidth, normHeight, fullWidth, fullHeight; //These are used in scaling images and changing the game to full screen
    public int height = 500, 
            width = 800,
            FPS = 0, 
            score = 0;;
    private int updates = 0, 
            mainScreenPointerPosition = gs_inGame, 
            elevatorPointer = 0;
    public static final int gs_inGame = 0, 
            gs_about = 1, 
            gs_controls = 2, 
            gs_credit = 3, mspp_quit = 3,
            gs_startScreen = 5;
    public static int gameState = gs_startScreen,
            currentLevel = 0;
    public double scale = 1, 
            fullScale = 1; //Also used with scaling
    private boolean running, 
            newGame = true;
    public boolean actionClicked = false, 
            nearElevator = false;
    private Thread mThread; //Main Game Thread
    
    //*******
    //The bits used to pull the image from the JFrame for editing
    private JFrame frame = new JFrame();
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), 
            screenImage = image;
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    //******
    
    private static String title = "The Little Man", version = " v9.01";
    public String[] startText = {
        "Welcome to the Arctic branch of \"Pleasant Smells\" Glue Company.",
        "This room is used for promising applicants, such as yourself.",
        "(Though you are the only one who applied.)",
        "We want to test the skills you will need to work here.",
        "Please collect 5 FBLA items for us from each floor.", //Change for the question stuff
        "We promise there's meaning to this.",
        "That is all. Penguins out!"};
    public static boolean isPaused = false, loaded = false,
            isFullScreen = false,
            isPrompting = false;
    public static Font tahoma;
    public Keyboard key;
    public Mouse mouse;
    public Screen screen;
    public TextDisplayer td;
    public Player player;
    public Level level;
    public static BusinessSim bs;
    
    
    //The main loop. This starts the game and is used for the frame's set up
    public static void main(String[] args) {
        bs = new BusinessSim();
        bs.frame.setResizable(false);
        bs.frame.setVisible(true);
        bs.frame.setTitle(title + version);
        bs.frame.add(bs);
        bs.frame.pack();
        bs.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        bs.frame.setFocusable(true);
        bs.frame.setLocationRelativeTo(null);
        bs.setFullScreen(isFullScreen);
        bs.start();
    }

    /**
     * The constructor of the game.
     * Used to calculate the full screen numbers and do final set up of the 
     * frame used for the game. It also initializes the key components such as
     * the Keyboard and Mouse interaction and the actual player.
     */
    public BusinessSim() {
        // setCursor(Toolkit.getDefaultToolkit().createCustomCursor(createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16)), new Point(0, 0), ""));
        fullHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        normHeight = height;
        normWidth = width;
        fullScale = fullHeight * 1.0 / normHeight;
        fullWidth = (int) (normWidth * fullScale);
//        System.out.println(normWidth);
//        System.out.println(fullWidth);
        Dimension size = new Dimension((int) (width * scale), (int) (height * scale));
        setPreferredSize(size);

        key = new Keyboard();
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        screen = new Screen(width, height);
        td = new TextDisplayer(screen);
        //The user will be shown this text when first entering the game.
        td.addLines(startText, TextDisplayer.TEXT);
        addKeyListener(key);
        level = new Level(Level.levelTilePaths[0], Level.levelObjPaths[0], 0, Level.xOff[0], Level.yOff[0]);
        player = new Player(level.playerV, screen, key);
        MusicPlayer.init();
    }

    /**
     * Start the thread.
     */
    public synchronized void start() {
        running = true;
        System.out.println("Start");
        mThread = new Thread(this, title + version);
        mThread.start();
    }

    /**
     * Exit and dispose of the thread.
     */
    public synchronized void stop() {
        running = false;
        System.out.println("Stop");
        try {
            mThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(3);
    }

    @Override
    /**
     * Main game loop.
     * Used to update, render the game and record the FPS 
     */
    public void run() {
        long time = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        requestFocus();
        changeGameState();
        changeMusic();

        while (running) {
            long now = System.nanoTime();
            delta += (now - time) / ns;
            time = now;
            while (delta >= 1) {
                update();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                FPS = frames;
//                frame.setTitle(title + version + " | FPS: " + frames + " UPS: " + updates + " px: " + player.v.getX() + " py: " + player.v.getY());
                frame.setTitle(((isPaused) ? "***PAUSED*** " : "") + title + version + " | FPS: " + frames);
                updates = frames = 0;
            }
        }
        stop();
    }

    /**
     * Handles the drawing of images on the screen.
     */
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        int xScroll = (int) (player.v.getX() - screen.width / 2);
        int yScroll = (int) (player.v.getY());

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        // Checks to see if the player is in game, and if it isn't, just draws a
        // premade image and handles input responses.
        if (gameState != gs_inGame && !(screenImage == null)) {
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width - fullWidth;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenWidth + fullWidth, fullHeight);
            g.drawImage(screenImage, (isFullScreen) ? screenWidth : 0, 0, width, height, 0, 0, normWidth, normHeight, null);
            g.setColor(Color.WHITE);
            if (gameState == gs_startScreen) {
                g.fillRect(width - (int) (40 * scale), (int) ((327 + (mainScreenPointerPosition * 43)) * scale), (int) (20 * scale), (int) (15 * scale));
            }
            g.dispose();
            bs.show();
            return;
        }
        
        screen.clear();
        
        //Draws the level around the player
        level.render(xScroll, yScroll, screen, player);
        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        {
            //Antialiasing, makes the lines smoother
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width - fullWidth;
            
            //Draws a pillarbox around the screen if it's in full screen mode to
            //keep the images from looking stretched. Also scales the game screens
            //to fill the actual screen.
            if (isFullScreen) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, screenWidth + fullWidth, height);
                g.drawImage(image, screenWidth, 0, width, height, 0, 0, normWidth, normHeight, null);
            } else {
                g.drawImage(image, 0, 0, null);
            }
            g.setColor(Color.WHITE);

            // Text Displayer
            td.displayText(g);

            // Heads Up Display, shows the overlay of text on the screen for
            // things like FPS. See HUD class for more.
            HUD.displayHUD(g);
            
            //Displays the elevator controls if player is close and interacts with them
            if (nearElevator && isPrompting) g = promptFloorSwitch(g);
            
            //Draws lines to the objects in the game if the player is close enough
            //Makes the game slightly easier if one of the items is hard to find
            g = drawLineToObjects(g);

            //Draws the pause overlay if paused
            if (isPaused) {
                try {
                    g.drawImage((screenImage == null)?screenImage = ImageIO.read(this.getClass().getResourceAsStream("/Textures/Screens/Pause.png")): screenImage, (isFullScreen) ? screenWidth : 0, 0, width, height, 0, 0, normWidth, normHeight, null);
                } catch (IOException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
        }
        g.dispose();
        bs.show();
    }

    /**
     * Draws a line between the player and hunt objects.
     * Takes the current graphics and draws lines between the player and the object
     * if the object is within 300 scaled pixels of the player.
     * @param g is the current graphics to overlay lines on.
     * @return the graphics with the lines overlaid.
     */
    public Graphics2D drawLineToObjects(Graphics2D g) {
        if (Level.hunt[level.levelNumber][0] != null && Level.hunt != null && Level.hunt[level.levelNumber] != null) {
            if (level.playerNearPickup(player)) {
                for (HuntObject hObj : Level.hunt[level.levelNumber]) {
                    if (hObj.v.distFrom(player.v) < 300 && !hObj.isRemoved()) {
                        //Draws a line between items within 300 pixels and the player
                        int x = screen.twoDToIso(hObj.v.getiX() - screen.xOffs, hObj.v.getiY() - screen.yOffs)[0];
                        int y = screen.twoDToIso(hObj.v.getiX() - screen.xOffs, hObj.v.getiY() - screen.yOffs)[1];
                        g.setColor(Color.white);
                        g.drawLine((int) ((player.v.getiX() - screen.xOffs + 15) * scale), (int) ((player.v.getiY() - screen.yOffs + 150) * scale), (int) ((x + 15) * scale), (int) ((y - 30) * scale));
                        g.setColor(Color.black);
                    }
                }
            }
        }
        return g;
    }

    /**
     * Manages the updating of components of the game.
     * Allows for certain aspects to update in an average interval of 60 times 
     * per second (handled in game loop on line 188). Updates items like the mouse
     * and keyboard.
     */
    public void update() {
        mouse.update();
        actionClicked = (key.action && !key.last_action) || mouse.lastMouseClicked;
        
        if(td.updateMultipleChoice(mouse.lastMouseClicked, mouse.mouseHeld, mouse.xPos, mouse.yPos) == TextDisplayer.RIGHT_ANSWER_CLICKED) {
            score += 10;
        }

        // if in game and not paused, do in game stuff
        if (!isPaused && gameState == gs_inGame) {
            player.update();
            Sprite.update();
            level.update(player);
            nearElevator = level.playerNearElevator(player);

            if (isPrompting) {
                updateElevatorPointer();
            }

            // ingame actions
            if (!key.inc && key.last_inc) { // text has priority over action button
                if(!td.inMultipleChoice()) { // can't skip multiple choice
                    td.moveOn();
                }
            }
            else if (actionClicked) {
                // level changing 
                if (Level.isNearHunt) {
                    for (int i = 0; i < hunt[currentLevel].length; i++) {
                        if (!hunt[currentLevel][i].isRemoved()) {
                            if (hunt[currentLevel][i].v.distFrom(player.v) < 50) {
                                hunt[currentLevel][i].event();
                                td.addLine("" + (level.itemCount - 1) + " items left on this floor.");
                            }
                        }
                    }
                } else if (nearElevator && !isPrompting) {
                    isPrompting = true;
                } else if (nearElevator && isPrompting) {
                    if (elevatorPointer != currentLevel) {
                        if (elevatorPointer == 0) {
                            switchLevel(elevatorPointer);
                            MusicPlayer o = new MusicPlayer();
                            o.init();
                            o.changeTrack(5);
                        } else if (Level.finished[elevatorPointer - 1]) {
                            switchLevel(elevatorPointer);
                            MusicPlayer o = new MusicPlayer();
                            o.init();
                            o.changeTrack(5);
                        } else {
                            MusicPlayer o = new MusicPlayer();
                            o.init();
                            o.changeTrack(6); //will be a click noise, show it's not available
                        }
                    } else {
                        isPrompting = false;
                    }
                } else if (false) { // action key should only do one thing at a time, "hence else if"
                }
            }
        }

        key.update();

        // if in the main menu, update the pointer (see line 543)
        if (gameState == gs_startScreen) {
            updateMainPointer();
        }

        // if action is pressed in main menu, decide which action to take 
        if ((actionClicked) || (mouse.lastMouseClicked)) {
            //If in menu and on quit has been selected, exit the game.
            if (mainScreenPointerPosition == mspp_quit) {
                System.exit(3); 
            }
            int localGameState = gameState;
            changeGameState();
            loaded = true;
            if (localGameState != gameState) {
                changeGameState();
                changeMusic();
            }
            loaded = false;
        }

        // Pause the game if 'P' is pressed and the player is in the game.
        if ((key.pause && !key.last_pause) && gameState == gs_inGame) {
            isPaused = !isPaused;
            System.out.println("isPaused = " + isPaused);
        }
    }

    
    /**
     * Set the screen to either full screen or our default size.
     * @param b is the state we want the screen to be as true is full screen and
     *          false is our default size.
     */
    public void setFullScreen(boolean b) {
        frame.setVisible(true);
        frame.dispose();
        frame.setUndecorated(b);
        if (b) {
            frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
            scale = fullScale;
            width = fullWidth;
            height = fullHeight;
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
        } else {
            width = normWidth;
            height = normHeight;
            frame.setPreferredSize(new Dimension(width, height));
            frame.setSize(new Dimension(width, height + 15));
            frame.setLocationRelativeTo(null);
            scale = 1;
        }
        tahoma = new Font("Tahoma", Font.ITALIC, (int) (36 * scale));
        isFullScreen = b;
        frame.addKeyListener(key);
        frame.requestFocus();
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    /**
     * Displays a graphic to have the player decide which floor to visit.
     * Floors unlock as the previous floor is finished. Interacts with Level to 
     * decide which floors to have unlocked. Displays a default prompt if the player
     * has yet to complete floor one
     * @param g the graphic as it stands before the prompt
     * @return the graphic with the prompt now overlaid if the player has completed
     *          floor one, otherwise returns the old prompt.
     */
    private Graphics2D promptFloorSwitch(Graphics2D g) {
        //If there is another prompt up, keeps the prompts from layering.
        if (!isPrompting) {
            return g;
        }
        //Checks if the first floor is done, if it isn't, there isn't 
        if (!Level.finished[0]) {
            td.addLines(new String[]{"Hey, what do you think you're doing?", "You can't possibly think we'd grant you elevator privleges", "without completing the entrance testing, do you?", "Finish this up here before you try again"}, TextDisplayer.TEXT);
            isPrompting = false;
            return g;
        }
        //******Draws the switch board******
        g.setColor(new Color(0x7f, 0x6a, 0, 0xb0));
        int left = (int) (((width - 100 * scale) / 2)),
                top = (int) (((height - 250 * scale) / 2)),
                right = (int) (100 * scale),
                bottom = (int) (150 * scale);
        g.fillRoundRect(left, top, right, bottom, 10, 10);
        g.setColor(Color.black);
        g.setFont(new Font("Tahoma", Font.PLAIN, (int) (12 * scale)));
        top += 15;
        g.drawString("FLOOR", (int) (left + (35 * scale)), top);
        left += 20 * scale;
        for (int i = 0; i < Level.levelAmount; i++) {
            if (i % 2 == 0) {
                top += (30 * scale);
            } else {
                left += (40 * scale);
            }
            g.setColor(Color.BLACK);
            g.fillOval(left, top, (int) (20 * scale), (int) (scale * 20));
            if (i == 0) {
                g.setColor(Color.white);
            } else if (Level.finished[i - 1]) {
                g.setColor(Color.white);
            } else {
                g.setColor(Color.DARK_GRAY);
            }
            if (i == elevatorPointer) {
                g.setColor(Color.yellow);
            }
            g.fillOval((int) (left + (2 * scale)), (int) (top + (2 * scale)), (int) (16 * scale), (int) (16 * scale));
            g.setColor(Color.black);
            g.drawString("" + (i + 1), (float) (left + 7 * scale), (float) (top + 14 * scale));

            if (i % 2 == 1) {
                left -= (40 * scale);
            }
        }
        return g;
    }

/**
 * Switches the floor the player is on.
 * @param levelNum the level that we change to
 */    
    private void switchLevel(int levelNum) {
        currentLevel = elevatorPointer = levelNum;
        level = new Level(Level.levelTilePaths[levelNum], Level.levelObjPaths[levelNum], levelNum, player.v.getX(), player.v.getY());
        td.addLines(Level.levelMessage[currentLevel], TextDisplayer.TEXT);
        isPrompting = false;
    }

    /**
     * Changes the music based on current game state
     */
    private void changeMusic() {
        switch (gameState) {
            case gs_inGame:
                MusicPlayer.mp.changeTrack(1);
                break;
            case gs_credit:
                MusicPlayer.mp.changeTrack(1);
                break;
            default:
                MusicPlayer.mp.changeTrack(0);
        }
    }

    /**
     * Moves the pointer for the elevator prompt to switch floors 
     */
    public void updateElevatorPointer() {
        if (key.up && !key.last_up) {
            if (elevatorPointer > 1) {
                elevatorPointer -= 2; //moves the pointer up one in the grid selection in the same left-right spot.
            } else {
                elevatorPointer += 4; //moves the pointer to the bottom row in the same left-right spot.
            }
        } else if (key.down && !key.last_down) {
            if (elevatorPointer < 4) {
                elevatorPointer += 2; //Moves the pointer down one y spot in the grid selection in same left-right spot.
            } else {
                elevatorPointer %= 2; //Moves the pointer to the top row in the same left-right spot.
            }
        } else if ((key.right && !key.last_right) || (key.left && !key.last_left)) {
            if (elevatorPointer % 2 == 0) {
                elevatorPointer += 1; //Changes the left right position, but won't change the up, down position;
            } else {
                elevatorPointer -= 1; //Changes the left right position, but won't change the up, down position;
            }
        }
    }

    /**
     * Changes the pointer on the main screen.
     * Uses the keyboard and mouse to select options on what screen to move 
     * to or to start the game.
     */
    private void updateMainPointer() {
        if (mouse.xPos > 600 * scale && mouse.yPos > 310 * scale) {
            if (mouse.yPos < 355 * scale) {
                mainScreenPointerPosition = gs_inGame;
            } else if (mouse.yPos < 400 * scale) {
                mainScreenPointerPosition = gs_about;
            } else if (mouse.yPos < 440 * scale) {
                mainScreenPointerPosition = gs_controls;
            } else {
                mainScreenPointerPosition = mspp_quit;
            }
        }
        if ((key.up && !key.last_up) | (key.down && !key.last_down)) {
            if (key.up) {
                mainScreenPointerPosition--;
            }
            if (key.down) {
                mainScreenPointerPosition++;
            }
            if (mainScreenPointerPosition < gs_inGame) {
                mainScreenPointerPosition = mspp_quit;
            } else if (mainScreenPointerPosition > mspp_quit) {
                mainScreenPointerPosition = gs_inGame;
            }
        }
        
        //Changes the volume using left or right.
        if ((key.left && !key.last_left) | (key.right && !key.last_right)) {
            if (key.left) {
                MusicPlayer.mp.decreaseVolume();
            }
            if (key.right) {
                MusicPlayer.mp.increaseVolume();
            }
        }
    }
    
    public void setGameState(int i) {
        gameState = i;
    }

    /**
     * Change the gamestate to a certain state
     * @param gs the state to change to
     */
    public void changeGameState(int gs) {
        gameState = gs;
        changeGameState();
    }

    /**
     * Handles the switching of screens/states
     */
    public void changeGameState() {
        switch (gameState) {
            case gs_inGame:
                newGame = false;
                screenImage = null;
                break;
            case gs_about:
                try {
                    screenImage = ImageIO.read(getClass().getResourceAsStream("/Textures/Screens/About.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                if (actionClicked && !loaded) {
                    gameState = gs_startScreen;
                }
                break;
            case gs_controls:
                try {
                    screenImage = ImageIO.read(getClass().getResourceAsStream("/Textures/Screens/Controls.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                if (actionClicked && !loaded) {
                    gameState = gs_startScreen;
                }

                break;
            case gs_credit:
                break;
            case gs_startScreen:
                try {
                    screenImage = ImageIO.read(getClass().getResourceAsStream("/Textures/Screens/Title.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                if (actionClicked && !loaded) {
                    gameState = mainScreenPointerPosition;
                    mainScreenPointerPosition = gs_inGame;
                }
                if (!newGame) {
                    reset();
                    newGame = true;
                }
                break;
        }
    }

    /**
     * Will be used to reset the game to the starting position so the game
     * can be reset from the beginning to play again
     */
    private void reset() {
        currentLevel = 0;
        for (int i = 0; i < Level.finished.length; i++) {
            Level.finished[i] = false;
        }
        for (int i = 0; i < Level.levelAmount; i++) {
            for (int huntObj = 0; huntObj < Level.hunt[i].length; huntObj++) {
                Level.hunt[i][huntObj] = null;
            }
        }
        level = new Level(Level.levelTilePaths[0], Level.levelObjPaths[0], 0, Level.xOff[0], Level.yOff[0]);
        player.v = new Vector2d(0, 0);
    }

    /**
     * Asks the user if they want to quit to make sure they aren't quitting 
     * on accident during game play
     */
    public void promptExit() {
        if (JOptionPane.showConfirmDialog(null, "Do you really want to quit?", "Quit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(3);
        }
    }
}