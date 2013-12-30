/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.level;

import com.FBLA.businesssim.entity.mob.Player;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.SpriteSheet;
import com.FBLA.businesssim.level.raisedobject.RaisedObject;
import com.FBLA.businesssim.level.tile.Tile;
import com.FBLA.businesssim.util.Vector2i;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
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
//    public Vector2i playerV = new Vector2i(spawnX + (BusinessSim.bs.player.s.W / 2), spawnY + (BusinessSim.bs.player.s.H /2));
    public Vector2i playerV = new Vector2i(0, 128); // why is this here?
    public static Rectangle[] rects;

    public Level(String path) {
//        generateLevel(20, 20);
        loadLevelTiles("Resources/Textures/Levels/ExampleLevelTiles.png");
        loadLevelObjects("Resources/Textures/Levels/ExampleLevelObjects.png");
//        generateLevel(40, 40);
    }

    protected void generateLevel(int w, int h) {
        width = w;
        height = h;
        tiles = new int[w*h];
        objects = new int[w*h];
        for(int i = 0; i < w * h; i++) {
            //tiles[i] = (int) (Math.random()*3);
            //tiles[i] = Tile.grassTileNum;
            tiles[i] = Tile.getNum(Tile.chkFloorTile);
            objects[i] = (int) (Math.random() * 50);
        }
    }
    
    protected void loadLevelTiles(String path) {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            tiles = new int[width * height];
            int[] tilePixels = new int[width*height];
            image.getRGB(0, 0, width, height, tilePixels, 0, width);
            
            for(int i = 0; i < tilePixels.length; i++) {
                Tile t = Tile.getTileFromColor(tilePixels[i]);
                if(t != null) {
                    tiles[i] = Tile.getNum(t);
                } else {
                    tiles[i] = Tile.getNum(Tile.voidTile);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, "Could not load level tiles", ex);
        }
    }
    
    protected void loadLevelObjects(String path) {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            objects = new int[width * height];
            int[] objPixels = new int[width*height];
            image.getRGB(0, 0, width, height, objPixels, 0, width);
            
            for(int i = 0; i < objPixels.length; i++) {
                RaisedObject r = RaisedObject.getRaisedObjectFromColor(objPixels[i]);
                if(r != null) {
                    objects[i] = RaisedObject.getNum(r);
                } else {
                    objects[i] = RaisedObject.getNum(RaisedObject.voidObject);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, "Could not load level objects", ex);
        }
    }

    public void update() {
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
        int x0 = 0;
        int y0 = 0;
        int x1 = width;
        int y1 = height;
        
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
        int px = (int) (p.v.getX()) >> 5;
        int py = (int) (p.v.getY()) >> 5;
        for (int x = x0; x < x1; x++) {
            for (int y = y0; y < y1; y++) {
                getObject(x, y).render(x, y, screen);
                if(x == px && y == py) {
                    p.render(screen);
                }
            }
        }
    }

    public Tile getTile(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.voidTile;
        }

        int spot = tiles[x + y * width];
        
        Tile t = Tile.getTileFromNumber(spot);
        
        if(t != null) {
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
        
        if(r != null) {
            return r;
        } else {
            return RaisedObject.voidObject;
        }
    }
}
