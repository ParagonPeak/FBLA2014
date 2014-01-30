package com.FBLA.businesssim;

import com.FBLA.businesssim.entity.items.HuntObject;
import com.FBLA.businesssim.entity.mob.Player;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.input.Keyboard;
import com.FBLA.businesssim.input.Mouse;
import com.FBLA.businesssim.level.Level;
import com.FBLA.businesssim.sound.MusicPlayer;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.MemoryImageSource;
import java.io.File;
import java.io.FileInputStream;
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

    private final int normWidth, normHeight, fullWidth, fullHeight;
    public int height = 500;
    public int width = 800;
    private int updates = 0;
    public double scale = 1, fullScale = 1;
    public Keyboard key;
    public Mouse mouse;
    public boolean mouseIsClicked = false, last_mouseIsClicked = false;
    public Screen screen;
    private boolean running;
    private Thread mThread;
    private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB), screenImage = image;
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private static String title = "The Little Man", version = " 8.2 alpha";
    private JFrame frame = new JFrame();
    public Player player;
    public static BusinessSim bs;
    public static Level level;
    public static int currentLevel = 0;
    public String[] currentText = {"Welcome to the Arctic branch of \"Pleasant Smells\" glue company.", "This room is used for promising applicants, such as yourself.", "(Though you are the only one who applied)", "We want to test the skills you will need to work here.", "Please collect 5 FBLA items for us from each floor.", "We promise there's meaning to this.", "*Heh*", "That is all. Penguins out!"};
    public boolean isPaused = false, loaded = false, isFullScreen = false;
    ;
    public static final int gs_inGame = 0;
    public static final int gs_about = 1;
    public static final int gs_controls = 2;
    public static final int gs_credit = 3, mspp_quit = 3;
    public static final int gs_startScreen = 5;
    public static int gameState = gs_startScreen;
    private int mainScreenPointerPosition = gs_inGame;
    private boolean nearElevator = false;
    private Font tahoma = new Font("Tahoma", Font.ITALIC, 36);

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
        bs.setFullScreen(true);
        bs.start();
    }

    public BusinessSim() {
        // setCursor(Toolkit.getDefaultToolkit().createCustomCursor(createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16)), new Point(0, 0), ""));
        fullHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        normHeight = height;
        normWidth = width;
        System.out.println(fullHeight);
        System.out.println(normHeight);
        fullScale = fullHeight * 1.0 / normHeight;
        System.out.println(fullScale);
        fullWidth = (int) (normWidth * fullScale);
        System.out.println(normWidth);
        System.out.println(fullWidth);
        Dimension size = new Dimension((int) (width * scale), (int) (height * scale));
        setPreferredSize(size);
        
        key = new Keyboard();
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
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

//                frame.setTitle(title + version + " | FPS: " + frames + " UPS: " + updates + " px: " + player.v.getX() + " py: " + player.v.getY());
                frame.setTitle(((isPaused) ? "***PAUSED*** " : "") + title + version + " | FPS: " + frames + " Floor: " + (currentLevel + 1) + " Pickups left here: " + level.itemCount);
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
        if (gameState != gs_inGame && !(screenImage == null)) {
            Graphics g = bs.getDrawGraphics();
//            g.drawImage(screenImage, 0, 0, null);
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width - fullWidth;
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, screenWidth + fullWidth, fullHeight);
            g.drawImage(screenImage, (isFullScreen)? screenWidth :0, 0, width, height, 0, 0, normWidth, normHeight, null);
            g.setColor(Color.WHITE);
            if (gameState == gs_startScreen) {
                g.fillRect(width - (int)(40 * scale), (int) ((327 + (mainScreenPointerPosition * 43))* scale), (int)(20 * scale), (int) (15 * scale));
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
//            g.drawImage(image, 0, 0, null);
            int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width - fullWidth;
            if (isFullScreen) {
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, screenWidth + fullWidth, height);
//                g.fillRect(width, 0, screenWidth, height);
                g.drawImage(image, screenWidth, 0, width, height, 0, 0, normWidth, normHeight, null);
            } else {
                g.drawImage(image, 0, 0 , null);
            }
            g.setColor(Color.WHITE);
//            g.drawString("X: " + (int) (player.v.getX()) + "\n Y: " + (int) (player.v.getY()), 50, 250);
            g = screen.displayText(currentText, key, g);

            if (nearElevator) {
                g.setColor(Color.WHITE);
                g.setFont(tahoma);
                if (level.finished[currentLevel] && currentLevel != Level.levelAmount) {
                    g.drawString("Press X to continue", 10, 50);
                } else if (currentLevel == 0) {
                    g.drawString("Not ready to advance", 10, 50);
                } else {
                    g.drawString("Press X for the first level", 10, 50);
                }
            }

            if (Level.hunt[level.levelNumber][0] != null && Level.hunt != null && Level.hunt[level.levelNumber] != null) {
                if (level.playerNearPickup(player)) {
                    for (HuntObject hObj : Level.hunt[level.levelNumber]) {
                        if (hObj.v.distFrom(player.v) < 300 && !hObj.isRemoved()) {
                            //Implement later
                            //Draws a line between items within 300 pixels and the player
                            int x = screen.twoDToIso(hObj.v.getiX() - screen.xOffs, hObj.v.getiY() - screen.yOffs)[0];
                            int y = screen.twoDToIso(hObj.v.getiX() - screen.xOffs, hObj.v.getiY() - screen.yOffs)[1];
                            g.setColor(Color.white);
                            g.drawLine((int) ((player.v.getiX() - screen.xOffs + 15) * fullScale), (int) ((player.v.getiY() - screen.yOffs + 150) * fullScale), (int) ((x + 15) * fullScale), (int) ((y - 30) * fullScale));
                            g.setColor(Color.black);
                        }
                    }
                }
            }
        }
        g.dispose();
        bs.show();
    }

    public void update() {
        mouse.update();
        // if in game and not paused, do in game stuff
        if (!isPaused && gameState == gs_inGame) {
            player.update();
            Sprite.update();
            level.update(player);
            nearElevator = level.playerNearElevator(player);
            // ingame actions
            if (key.action & !key.last_action) {
                // level changing 
                if (nearElevator) {
                    switchToNextAvailableLevel();
                    MusicPlayer o = new MusicPlayer();
                    o.init();
                    o.changeTrack(5);
                } else if (false) { // action key should only do one thing at a time, "hence else if"
                }
            }
        }

        key.update();

        // if in the main menu and a key is pressed, update
        if (gameState == gs_startScreen) {

            if (mouse.xPos > 600 * scale && mouse.yPos > 310 * scale){
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
            if ((key.up & !key.last_up) | (key.down & !key.last_down)) {
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

            if ((key.left & !key.last_left) | (key.right & !key.last_right)) {
                if (key.left) {
                    MusicPlayer.mp.decreaseVolume();
                }
                if (key.right) {
                    MusicPlayer.mp.increaseVolume();
                }
            }
        }

        // if action is pressed in main menu. inc key/mouse don't actually make this do stuff yet for some reason
        if ((key.action && !key.last_action)) {// || (key.inc && !key.last_inc) || (!mouseIsClicked && last_mouseIsClicked)) {
            if (mainScreenPointerPosition == mspp_quit) {
                System.exit(3); // if in menu and on quit, quit
            }
            int gameState = BusinessSim.gameState;
            changeGameState();
            loaded = true;
            if ((key.action && !key.last_action) && gameState != BusinessSim.gameState) {
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

    public void setFullScreen(boolean b) {
        frame.setVisible(false);
        frame.dispose();
        frame.setUndecorated(b);
        if (b) {
            frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
            scale = fullScale;
            width = fullWidth;
            height = fullHeight;
            frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            frame.setResizable(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setLocationRelativeTo(null);
        } else {
            frame.setPreferredSize(new Dimension(width, height));
            scale = 1;
        }
        isFullScreen = b;
        frame.setVisible(true);
    }

    private void switchToNextAvailableLevel() {
        if (!(currentLevel == level.levelAmount - 1) && level.finished[currentLevel]) { // move up a level if finished and not on last level
            currentLevel++;
        } else if (currentLevel == 0) { // do nothing if you're on level 0 and not finished
            return;
        } else { // go back to level 0 if you can't move up and aren't on level 0
            currentLevel = 0;
        }
//        level = new Level(Level.levelTilePaths[currentLevel], Level.levelObjPaths[currentLevel], currentLevel, Level.xOff[currentLevel], Level.yOff[currentLevel]);
//         player = new Player(level.playerV, screen, key);
        level = new Level(Level.levelTilePaths[currentLevel], Level.levelObjPaths[currentLevel], currentLevel, player.v.getX(), player.v.getY());
        screen.updateText(Level.levelMessage[currentLevel]);
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

    private void updatePointer() {
        if (key.left) {
            MusicPlayer.mp.decreaseVolume();
        }
        if (key.right) {
            MusicPlayer.mp.increaseVolume();
        }
        if (key.up) {
            mainScreenPointerPosition--;
        }
        if (key.down) {
            mainScreenPointerPosition++;
        }
        if (mainScreenPointerPosition < gs_inGame) {
            mainScreenPointerPosition = mspp_quit;
        }
        if (mainScreenPointerPosition > mspp_quit) {
            mainScreenPointerPosition = gs_inGame;
        }
    }

    public void setGameState(int i) {
        gameState = i;
    }

    public void changeGameState() {
        switch (gameState) {
            case gs_inGame:
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
                if (key.action & !key.last_action & !loaded) {
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
                if (key.action & !key.last_action & !loaded) {
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
                if (key.action & !key.last_action & !loaded) {
                    gameState = mainScreenPointerPosition;
                    mainScreenPointerPosition = gs_inGame;
                }
                break;
        }
    }

    public void promptExit() {
        if (JOptionPane.showConfirmDialog(null, "Do you really want to quit?", "Quit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            System.exit(3);
        }
    }
}
