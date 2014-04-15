package com.FBLA.businesssim.graphics;

/**
 * This class holds the information for snippet of dialogue: the text and its coordinates
 * 
 * @author RAPHAEL
 */
public class Dialogue {
    
    public String[] text;
    public int x2D, y2D, xIso, yIso;
    
    /**
     * @param text The array of lines in the dialogue
     * @param x2D The x position of the dialogue in terms of non-isometric coordinates
     * @param y2D The y position of the dialogue in terms of non-isometric coordinates
     */
    public Dialogue(String[] text, int x2D, int y2D) {
        if(text == null) {
            throw new java.lang.NullPointerException("Null dialogue given");
        }
        this.text = text;
        this.x2D = x2D;
        this.y2D = y2D;
        int[] iso = Screen.twoDToIso(x2D, y2D);
        xIso = iso[0];
        yIso = iso[1];
    }
}
