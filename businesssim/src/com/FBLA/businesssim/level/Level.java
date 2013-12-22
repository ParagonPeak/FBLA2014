/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.level;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.util.Vector2i;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.level.tile.Tile;
import java.awt.Rectangle;

/**
 *
 * @author Tripp
 */
public class Level {

    public int width, height;
    public int[] tiles;
    public int spawnX = 0;
    public int spawnY = 0;
//    public Vector2i playerV = new Vector2i(spawnX + (BusinessSim.bs.player.s.W / 2), spawnY + (BusinessSim.bs.player.s.H /2));
    public Vector2i playerV = new Vector2i(0,0);
    public static Rectangle[] rects;

    public Level(String path) {
        loadLevel(path);
        generateLevel();
    }

    protected void generateLevel() {
    }

    protected void loadLevel(String path) {
    }

    public void update() {
    }

    private void time() {
    }

    public void render(int xPos, int yPos, Screen screen) {
        screen.setOffset(xPos, yPos);

        //corner pins
        int x0 = (xPos >> 5);
        int x1 = (xPos + screen.width) >> 5;
        int y0 = yPos >> 5;
        int y1 = (yPos + screen.height) >> 5;

        x1++;
        y1++;

        for (int y = y0; y < y1; y++) {
            for (int x = x0; x < x1; x++) {
                getTile(x, y).render(x, y, screen);
            }
        }
    }

    public Tile getTile(int x, int y) {

        if (x < 0 || y < 0 || x >= width || y >= height) {
            return Tile.voidTile;
        }

        int spot = tiles[x + y * width];

//        if ((spot >= 0 && spot < 16) || spot == Tile.col_grass) {
//            return Tile.grass;
//        }
        
        return Tile.voidTile;
    }
}
