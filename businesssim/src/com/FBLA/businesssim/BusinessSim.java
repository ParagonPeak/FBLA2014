/*
 *
 * 
 * What we'll need:
 * 
 * Entity
 * -Player
 * -CoWorkers**
 * -Boss**
 * 
 * Zoning
 * -Zone areas for tasks
 * --Work
 * --Break
 * --Bathroom
 * --None (halls)
 * 
 * Tasks/needs for player
 * -Blader
 * -Social
 * -Work
 * -Office standings
 * 
 * FBLA Things
 * -Find Tech Competitions
 * --Figure out how to implement them into the game
 * -Make it fun
 * -???
 * -Profit
 * 
 * Player
 * -Controllable entity
 * -Switch to four degrees of movement instead of 8?
 * 
 * AI
 * -A* Path finding
 * -Determine if social behavior is appropiate
 * -Entities with (**) need AI
 * 
 */
package com.FBLA.businesssim;

import com.FBLA.businesssim.entity.mob.Player;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.input.Keyboard;
import com.FBLA.businesssim.level.Level;
import com.FBLA.businesssim.sound.MusicPlayer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.MemoryImageSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * @author Tripp Weiner and Raphael Rouvinov-Kats To see the full progress of
 * the game build, go to
 * @url www.github.com/paragonpeak/FBLA2014 Raphael's github:
 * www.github.com/coolcade283
 *
 * Tripp's github: www.github.com/TrippW
 *
 */
public class BusinessSim extends Canvas implements Runnable {

    public int width = 800, height = 500;
    private int updates = 0;
    public byte scale = 1;
    public Keyboard key;
    public Screen screen;
    private boolean running;
    private Thread mThread;
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), screenImage = image;
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private static String title = "The Little Man", version = " 0.1 alpha";
    private JFrame frame = new JFrame();
    public Player player;
    public static BusinessSim bs;
    public static Level level;
    public static int currentLevel = 0;
    String[] test = {"Test1", "Test 2", "Test 3", "Replace", "Test11", "Test 32", "Test 73", "Replace"};
    public boolean isPaused = false, loaded = false;
    public static final int gs_inGame = 0;
    public static final int gs_about = 1;
    public static final int gs_controls = 2;
    public static final int gs_credit = 3;
    public static final int gs_startScreen = 5;
    public static int gameState = gs_startScreen;

    //Starts the game, used for frame set up
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

        bs.start();
    }

    public BusinessSim() {
        setCursor(Toolkit.getDefaultToolkit().createCustomCursor(createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16)), new Point(0, 0), ""));
        Dimension size = new Dimension(width * scale, height * scale);
        setPreferredSize(size);
        key = new Keyboard();
        screen = new Screen(width, height);
        addKeyListener(key);
//        level = new Level("level/Floor1.png");
        level = new Level(Level.levelTilePaths[0], Level.levelObjPaths[0], 0, Level.xOff[0], Level.yOff[0]);
        player = new Player(level.playerV, screen, key);
        MusicPlayer.init();
    }

    //start applet
    public synchronized void start() {
        running = true;
        System.out.println("Start");
        mThread = new Thread(this, title + version);
        mThread.start();
    }

    //exit applet
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

                frame.setTitle(title + version + " | FPS: " + frames + " UPS: " + updates);
                updates = frames = 0;
            }
        }
        stop();
    }
    
    private Font tahoma = new Font("Tahoma", Font.ITALIC, 36);
    
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        int xScroll = (int) (player.v.getX() - screen.width / 2);
        int yScroll = (int) (player.v.getY());

        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        if (gameState != gs_inGame && !screenImage.equals(null)) {
            Graphics g = bs.getDrawGraphics();
            g.drawImage(screenImage, 0, 0, null);
            g.setColor(Color.WHITE);
            if (gameState == gs_startScreen) {
                g.fillRect(760, 327 + (mainScreenPointerPosition * 43), 20, 15);
            }
            g.dispose();
            bs.show();
            return;
        }
        
        screen.clear();
        level.render(xScroll, yScroll, screen, player);
        //player.render(screen); // player.render() called by level.render() because Sprite ordering
        // screen.renderSpriteOnScreen(0, 0, Sprite.grass); // example of what renderSpriteOnScreen does

        System.arraycopy(screen.pixels, 0, pixels, 0, pixels.length);

        Graphics g = bs.getDrawGraphics();
        {
            g.drawImage(image, 0, 0, null);
            g.setColor(Color.WHITE);
//            g.drawString("X: " + (int) (player.v.getX()) + "\n Y: " + (int) (player.v.getY()), 50, 250);
            g = screen.displayText(test, key, g);
            
            if(level.playerNearElevator()) {
                g.setColor(Color.WHITE);
                g.setFont(tahoma);
                if(level.finished[currentLevel] && currentLevel != Level.levelAmount) {
                    g.drawString("Press X or Space to continue", 10, 50);
                } else {
                    g.drawString("Press X or Space for the first level", 10, 50);
                }
            }
        }
        g.dispose();
        bs.show();
    }
    
    public void update() {
        // if in game and not paused, do in game stuff
        if (!isPaused && gameState == gs_inGame) {
            player.update();
            Sprite.update();
            level.update();
            
            if(level.playerNearElevator()) {
                
            }
            
            // ingame actions
            if (key.action & !key.last_action) {
                
                // level changing 
                if(level.playerNearElevator()) {
                    switchToNextAvailableLevel();
                }
            }
        }
        
        key.update();
        
        // if in the main menu and a key is pressed, update
        if (gameState == gs_startScreen) {
            if ((key.up & !key.last_up) | (key.down & !key.last_down) | (key.left & !key.last_left) | (key.right & !key.last_right)) {
                updatePointer();
            }
        }
        
        // if action key is pressed
        if (key.action & !key.last_action) {
            if (mainScreenPointerPosition == 3) {
                System.exit(3); // if in menu and on quit, quit
            }
            int gameState = BusinessSim.gameState;
            changeGameState();
            loaded = true;
            if (key.action & !key.last_action & gameState != BusinessSim.gameState) {
                changeGameState();
                changeMusic();
            }
            loaded = false;
            System.out.println("action");
        }
        
        // pause
        if ((key.pause && !key.last_pause) && gameState == gs_inGame) {
            isPaused = !isPaused;
            System.out.println("isPaused = " + isPaused);
        }
    }
    
    private void switchToNextAvailableLevel() {
        if(!(currentLevel == level.levelAmount - 1) && level.finished[currentLevel]) { // move up a level if finished and not on last level
            currentLevel++;
        } else if(currentLevel == 0) { // do nothing if you're on level 0 and not finished
            return;
        } else { // go back to level 0 if you can't move up and aren't on level 0
            currentLevel = 0;
        }
        level = new Level(Level.levelTilePaths[currentLevel], Level.levelObjPaths[currentLevel], currentLevel, Level.xOff[currentLevel], Level.yOff[currentLevel]);
        // player = new Player(level.playerV, screen, key);
    }

    private void changeMusic() {
//        if(isPaused) return;
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
    private int mainScreenPointerPosition = 0;

    private void updatePointer() {
        if(key.left)
            MusicPlayer.mp.decreaseVolume();
        if(key.right)
            MusicPlayer.mp.increaseVolume();
        if (key.up) {
            mainScreenPointerPosition--;
        }
        if (key.down) {
            mainScreenPointerPosition++;
        }
        if (mainScreenPointerPosition < 0) {
            mainScreenPointerPosition = 3;
        }
        if (mainScreenPointerPosition > 3) {
            mainScreenPointerPosition = 0;
        }
    }

    public void changeGameState() {
        switch (gameState) {
            case gs_inGame:
                screenImage = null;
                break;
            case gs_about:
                try {
                    screenImage = ImageIO.read(new FileInputStream("Resources/Textures/Screens/About.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                if (key.action & !key.last_action & !loaded) {
                    gameState = gs_startScreen;
                }
                break;
            case gs_controls:
                try {
                    screenImage = ImageIO.read(new FileInputStream("Resources/Textures/Screens/Controls.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                if (key.action & !key.last_action & !loaded) {
                    gameState = gs_startScreen;
                }

                break;
            case gs_credit:
                break;
            case gs_startScreen:
                try {
                    screenImage = ImageIO.read(new FileInputStream("Resources/Textures/Screens/Title.png"));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BusinessSim.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
                if (key.action & !key.last_action & !loaded) {
                    gameState = mainScreenPointerPosition;
                    mainScreenPointerPosition = 0;
                }
                break;
        }
    }
}
