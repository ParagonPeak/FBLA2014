/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.level;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.entity.mob.Player;
import com.FBLA.businesssim.util.Vector2i;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.graphics.SpriteSheet;
import com.FBLA.businesssim.level.raisedobject.RaisedObject;
import com.FBLA.businesssim.level.tile.Tile;
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
    public int[] tiles; // floor
    public int[] objects; // walls and other 3d things
    public int spawnX = 0;
    public int spawnY = 0;
//    public Vector2i playerV = new Vector2i(spawnX + (BusinessSim.bs.player.s.W / 2), spawnY + (BusinessSim.bs.player.s.H /2));
    public Vector2i playerV = new Vector2i(0,0); // why is this here?
    public static Rectangle[] rects;

    public Level(String path) {
        //loadLevel(path);
        generateLevel(20, 20);
    }

    protected void generateLevel(int w, int h) {
        width = w;
        height = h;
        tiles = new int[w*h];
        objects = new int[w*h];
        for(int i = 0; i < w * h; i++) {
            //tiles[i] = (int) (Math.random()*3);
            //tiles[i] = Tile.grassTileNum;
            tiles[i] = Tile.chkFloorTileNum;
            objects[i] = (int) (Math.random() * 10);
        }
    }

    protected void loadLevel(String path) {
        try {
            BufferedImage image = ImageIO.read(new FileInputStream(path));
            width = image.getWidth();
            height = image.getHeight();
            int[] tilePixels = new int[width*height];
            image.getRGB(0, 0, width, height, tilePixels, 0, width);
            
            for(int i = 0; i < tilePixels.length; i++) {
                if(tilePixels[i] == Tile.voidTileRGB) tiles[i] = Tile.voidTileNum;
                else if(tilePixels[i] == Tile.grassTileRGB) tiles[i] = Tile.grassTileNum;
                else tiles[i] = Tile.voidTileNum;
            }
        } catch (IOException ex) {
            Logger.getLogger(SpriteSheet.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // add another try catch here for raisedobjects
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
                getTile(x, y).render(x, y, screen);
            }
        }
        
        for(int y = 0; y < (int) (p.v.getY()) >> 5; y++) {
            
        }
        
        // declared outside loop for speed
//        int[] pCoords = screen.twoDToIso(p.v.getX(), p.v.getY());
//        int px = pCoords[0];
//        int py = pCoords[1];
//        int pw = p.sprite.W;
//        int ph = p.sprite.H;
////        px = (int) (p.v.getX());
////        py = (int) (p.v.getY());
//        
//        double xRange = 1280;
//        double yRange = 640;
//        double minX = -640;
//        double maxX = 640;
//        double minY = 0;
//        double maxY = 640;
//        double xScale = 256;
//        double yScale = 128;
//        int xShift = 200;
//        int yShift = 0;
//        
//        boolean renderPlayer = true;
//        for (int y = y0; y < y1; y++) {
//            for (int x = x0; x < x1; x++) {
//                RaisedObject ro = getObject(x, y);
//                ro.render(x, y, screen);
//                
//                if(true) {
//                    int[] xy = screen.twoDToIso(x << 5, y << 5);
//                    int sx = xy[0];
//                    int sy = xy[1];
////                    sx = x << 5;
////                    sy = y << 5;
//                    int sw = ro.sprite.W;
//                    int sh = ro.sprite.H;
//                    // only render player if they're behind what's being rendered and they're close to what's being rendered
//                    boolean inXBounds = ((px >= sx - 10) &&  (px <= sx + sw + 10)); 
//                    boolean inYBounds = ((py < sy - sh));// &&   (py > sy - sh));
//                    //System.out.println(inXBounds + "\t" + inYBounds + " px: " + px + " py: " + py + " ");
//                    if(!ro.sprite.equals(Sprite.emptySprite)) {
//                        screen.renderSpriteOnScreen((int) ((sx-minX+minX)/xRange*xScale) + xShift, (int) (sy/yRange*yScale) + yShift, Sprite.smallRedSprite);
//                    }
//                    if(inYBounds && inXBounds) {
//                        p.render(screen);
//                    } else {
//                        renderPlayer = false;
//                    }
//                }
//                
//            }
//        }
//        screen.renderSpriteOnScreen((int) ((px-minX+minX)/xRange*xScale) + xShift, (int) (py/yRange*yScale) + yShift, Sprite.smallYellowSprite);
        // x [-640, 640] y [0, 640]
    }

    public Tile getTile(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.voidTile;
        }

        int spot = tiles[x + y * width];
        
        if(spot == Tile.voidTileNum) return Tile.voidTile;
        if(spot == Tile.grassTileNum) return Tile.grassTile;
        if(spot == Tile.chkFloorTileNum) return Tile.chkFloorTile;
        
        return Tile.voidTile;
    }
    
    public RaisedObject getObject(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return RaisedObject.voidObject;
        }

        int spot = objects[x + y * width];
        
        if(spot == RaisedObject.voidObjectNum) return RaisedObject.voidObject;
        if(spot == RaisedObject.cubicleSWObjectNum) return RaisedObject.cubicleSWObject;
        if(spot == RaisedObject.cubicleSEObjectNum) return RaisedObject.cubicleSEObject;
        
        return RaisedObject.voidObject;
    }
}
