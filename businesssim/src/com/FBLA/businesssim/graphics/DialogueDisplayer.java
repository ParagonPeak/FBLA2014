package com.FBLA.businesssim.graphics;

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
        
        g.setColor(Color.BLACK);
        
        for(Dialogue d : dialogue) {
            drawText(d.text, g, d.xIso, d.yIso);
        }
    }
    
    /**
     * Called by displayDialogue to actually drawString the lines to the Graphics object
     * @param lines
     * @param g 
     */
    private static void drawText(String[] lines, Graphics g, int x, int y) {
        if (g == null || lines == null) {
            return;
        }
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) {
                continue;
            }
            g.setColor(Color.BLACK);
            g.drawString(lines[i], (int)(x * ((BusinessSim.isFullScreen)? 7.0/3.0 : 1.0)),(int)(((i + 1) * 25 + y) * BusinessSim.bs.scale));
        }
    }
}
