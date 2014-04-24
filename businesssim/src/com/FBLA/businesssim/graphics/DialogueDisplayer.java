package com.FBLA.businesssim.graphics;

import com.FBLA.businesssim.util.Dialogue;
import com.FBLA.businesssim.BusinessSim;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Every update method the dialogue in this method gets cleared and then replaced with NPC dialogue
 * Every render method this method displays whatever dialogue it currently has on hand
 * 
 * @author RAPHAEL
 */
public class DialogueDisplayer {
    
    // holds all dialogue to be displayed
    protected static ArrayList<Dialogue> dialogue = new ArrayList<>();
    
    private static final int DIST_BETWEEN_LINES = 25;
    
    public static void addDialogue(Dialogue d) {
        if(d == null) {
            throw new java.lang.NullPointerException("Null dialogue given");
        }
        dialogue.add(d);
    }
    
    /**
     * Removes all dialogue
     */
    public static void clearDialogue() {
        dialogue.clear();
    }
    
    /**
     * This method displays all the texts in their respective (x to iso, y2D to iso) coordinates
     * @param g the Graphics object to write to
     */
    public static void displayDialogue(Graphics g) {
        if (g == null)
            return;
        
        for(Dialogue d : dialogue) {
            drawText(d.text, g, d.x2D - Screen.xOffs, d.y2D - Screen.yOffs);
        }
    }
    
    /**
     * Called by displayDialogue to actually drawString the lines to the Graphics object
     * @param lines
     * @param g 
     */
    private static void drawText(String[] lines, Graphics g, int x, int y) {
        // dont accept bad input
        if (g == null || lines == null) {
            return;
        }
        
        // convert x and y from 2D to Iso coordinates
        int[] coords = Screen.twoDToIso(x, y);
        x = coords[0];
        y = coords[1];
        
        // draw dialog bubble
        int dialogWidth = (int) (350 * BusinessSim.bs.scale);
        int dialogHeight = (int) (DIST_BETWEEN_LINES * lines.length * BusinessSim.bs.scale);
        g.setColor(new Color(0xcc, 0xcc, 0xcc, 150));
        g.fillRoundRect((int) ((x - 5) * BusinessSim.bs.scale), (int) (y * BusinessSim.bs.scale + DIST_BETWEEN_LINES/2), dialogWidth, dialogHeight, 20, 20);
        //g.setColor(Color.BLACK);
        //g.drawRect((int) ((x - 5) * BusinessSim.bs.scale), (int) (y * BusinessSim.bs.scale + DIST_BETWEEN_LINES/2), dialogWidth, dialogHeight);
        
        // draw each line
        for (int i = 0; i < lines.length; i++) {
            // ignore null lines
            if (lines[i] == null) {
                continue;
            }
            
            g.setColor(Color.BLACK);
            g.drawString(lines[i], (int)(x * BusinessSim.bs.scale),(int)(((i + 1) * DIST_BETWEEN_LINES + y) * BusinessSim.bs.scale));
        }
    }
}
