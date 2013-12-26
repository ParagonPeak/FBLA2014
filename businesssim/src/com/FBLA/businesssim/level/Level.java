/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.level;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.util.Vector2i;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.SpriteSheet;
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
    public int[] tiles;
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
        for(int i = 0; i < w * h; i++) {
            tiles[i] = (int) (Math.random()*2);
            //tiles[i] = Tile.grassTileNum;
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
    }

    public void update() {
    }

    private void time() {
    }

    public void render(int xPos, int yPos, Screen screen) {
        screen.setOffset(xPos, yPos);

        //corner pins (tile not pixel array)
        // if it's >> 5 the sprites we use should be 32x32 pixels
        int x0 = (xPos >> 5);
        int x1 = (xPos + screen.width) >> 5;
        int y0 = yPos >> 5;
        int y1 = (yPos + screen.height) >> 5;

        for (int y = y0; y <= y1; y++) {
            for (int x = x0; x <= x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }
    }

    public Tile getTile(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.voidTile;
        }

        int spot = tiles[x + y * width];
        
        if(spot == Tile.voidTileNum) return Tile.voidTile;
        if(spot == Tile.grassTileNum) return Tile.grassTile;
//        if ((spot >= 0 && spot < 16) || spot == Tile.col_grass) {
//            return Tile.grass;
//        }
        
        return Tile.voidTile;
    }
}
