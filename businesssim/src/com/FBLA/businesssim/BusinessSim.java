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
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
 * @url www.github.com/paragonpeak/FBLA2014 
 * Raphael's github: www.github.com/coolcade283
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
        level = new Level("level/Floor1.png");
        player = new Player(level.playerV, screen, key);
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
                g.fillRect(770, 344 + (mainScreenPointerPosition * 43), 20, 15);
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
        }
        g.dispose();
        bs.show();
    }

    public void update() {
        if (!isPaused && gameState == gs_inGame) {
            player.update();
            Sprite.update();
            level.update();
        }
        key.update();
        if ((key.up & !key.last_up) | (key.down & !key.last_down) | (key.left & !key.last_left) | (key.right & !key.last_right) | (key.action & !key.last_action)) {
            int gameState = BusinessSim.gameState;
            changeGameState();
            loaded = true;
            if(key.action & !key.last_action & gameState != BusinessSim.gameState)
                changeGameState();
            loaded = false;
        }

        if ((key.pause && !key.last_pause) && gameState == gs_inGame) {
            isPaused = !isPaused;
            System.out.println("isPaused = " + isPaused);
        }
        if (key.inc && !key.last_inc) {
            test = new String[++gameState];
            for (int i = 0; i < test.length; i++) {
                test[i] = "";
                for (int b = 0; b < 50; b++) {
                    test[i] += "" + (char) ((int) (Math.random() * 500));
                }
            }
            screen.textRequiresUpdate = false;
        }
    }
    private int mainScreenPointerPosition = 0;

    public void changeGameState() {
        System.out.println(gameState);
        switch (gameState) {
            case gs_inGame:
                screenImage = null;
                break;
            case gs_about:
                try {
                    screenImage = ImageIO.read(new FileInputStream("Resources/Textures/Screens/OtherFront.png"));
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
                    screenImage = ImageIO.read(new FileInputStream("Resources/Textures/Screens/OtherFront.png"));
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

                if (key.up) {
                    mainScreenPointerPosition--;
                }
                if (key.down) {
                    mainScreenPointerPosition++;
                }
                if (mainScreenPointerPosition < 0) {
                    mainScreenPointerPosition = 2;
                }
                if (mainScreenPointerPosition > 2) {
                    mainScreenPointerPosition = 0;
                }
                if (key.action & !key.last_action & !loaded) {
                    gameState = mainScreenPointerPosition;
                    mainScreenPointerPosition = 0;
                }
                break;
        }
    }
}
