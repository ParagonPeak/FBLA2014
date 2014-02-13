/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.graphics;

import com.FBLA.businesssim.BusinessSim;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author RAPHAEL and Tripp
 */
public class TextDisplayer {
       
    public static final int MAX_LINES = 6;
    private String[] currentText; // hold the String[] being displayed on the screen at the moment
    int index = 0; // index you're at in currentText since the String[] can have more than MAX_LINES amout of lines
    public boolean hasText = false; // true if currentText has text
    private ArrayList<String[]> queue = new ArrayList<>(); // hold the String[]s that will be put into currentText
    
    public static final boolean MULTIPLE_CHOICE = true;
    public static final boolean TEXT = false;
    public boolean currentTextType = false;
    private ArrayList<Boolean> isMC = new ArrayList<>();
    
    private int mouseX, mouseY;
    private boolean mouseWasClicked;
    
    public static final int WRONG_ANSWER_CLICKED = 0;
    public static final int RIGHT_ANSWER_CLICKED = 1;
    public static final int NO_ANSWER_CLICKED = -1;
    private int hoveredOverOption = -1;
    private int heldOption = -1;
    
    private Screen screen;
    private double scale;
    
    public TextDisplayer(Screen s) {
        screen = s;
    }
    
    /**
     * Helper method for addLines
     * Adds a single line to the queue/dialog box
     * @param line line to add to the dialog box
     */
    public void addLine(String line) {
        addLines(new String[]{line}, TEXT);
    }
    
    /**
     * Add lines to be displayed
     * If nothing is currently displayed, immediately goes to the dialog box
     * Else it goes into the queue
     * @param lines 
     */
    public void addLines(String[] lines, boolean lineType) {
        if (hasText) {
            queue.add(lines);
            isMC.add(lineType);
        } else {
            currentText = lines;
            if(lineType == MULTIPLE_CHOICE) {
                currentTextType = MULTIPLE_CHOICE;
            }
        }
        hasText = true;
    }
    
    /**
     * Moves on to the next line in the current text or the
     * next set of lines in the queue
     */
    public void moveOn() {
        index += MAX_LINES;
        // System.out.println("INCREASE"); //Remove in the end
        
        if(currentText != null && index >= currentText.length) {
            currentText = null;
            hasText = false;
            index = 0;
            if(!queue.isEmpty()) {
                currentText = queue.get(0);
                queue.remove(0);
                currentTextType = isMC.get(0);
                isMC.remove(0);
                hasText = true;
            }
        }
    }
    
    /**
     * @return whether currentText is a multiple choice questions
     */
    public boolean inMultipleChoice() {
        return currentTextType;
    }
    
    /**
     * Updates mouse data
     * Check whether mouse is over a question/isHeld
     * Checks whether an answer was clicked and if so then whether it was width
     * 
     * @return whether an answer was clicked and if so then whether it was width or wrong
     */
    public int updateMultipleChoice(boolean mouseWasClicked, boolean mouseIsHeld, int mouseX, int mouseY) {
        // we want the class to have its own store of these for proper updating
        this.mouseWasClicked = mouseWasClicked;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        
        int boxRight = (int) (scale * screen.width - 75) - ((BusinessSim.isFullScreen)?60:0), 
            boxLeft = (int) (scale * 50) + ((BusinessSim.isFullScreen)? 60:0);
        // int stringX = 60 + ((BusinessSim.isFullScreen)? 80:0); 
        
        // determine what's hovered over/held
        hoveredOverOption = -1;
        heldOption = -1;
        if(inMultipleChoice() && currentText != null && currentText.length != 0 && mouseX > boxLeft && mouseX < boxRight) {
            for(int i = 2; i < Math.min(MAX_LINES, currentText.length); i++) {
                int stringY = (int)(((i + 1) * 25 + 175) * scale);
                if(mouseY < stringY && mouseY > stringY - 20 * scale) {
                    hoveredOverOption = i;
                    if(mouseIsHeld) {
                        heldOption = i;
                    }
                    break;
                }
            }
        }
        
        if(mouseWasClicked && hoveredOverOption != -1) {
            moveOn();
            if(hoveredOverOption == 2) { // because there's nothing wrong with every width answer being "A", right?
                return RIGHT_ANSWER_CLICKED;
            } 
            return WRONG_ANSWER_CLICKED;
        }
        
        return NO_ANSWER_CLICKED;
    }
    
    /**
     * Takes a lines array and turns it into one that has the lines 
     * from [index] to [index + maxLength] and doesn't go out of array bounds
     */
    private String[] subTextArray(String[] lines, int index, int maxLength) {
        int linesToCopy = Math.min(maxLength, lines.length - index);
        if(linesToCopy <= 0) {
            String[] empty = {""};
            return empty;
        }
        String[] text = new String[linesToCopy];
        System.arraycopy(lines, index, text, 0, linesToCopy);
        return text;
    }
    
    /**
     * Displays the current text
     * @param g Graphics object to draw to
     */
    public void displayText(Graphics g) {
        // String[] text = subTextArray(currentText, index, MAX_LINES);
        displayText(currentText, index, g);
    }

    /**
     * Displays lines of text in a dialog box for a player to read
     * @param lines contains the lines the player will read
     * @param index index of the lines you want to start reading from
     * @param g Graphics object to draw to
     */
    private void displayText(String[] lines, int index, Graphics g) {
        if (g == null || lines == null || lines.length == 0) {
            return;
        }
        
        scale = BusinessSim.bs.scale;
        // Font tahoma = new Font("Tahoma", Font.PLAIN, (int) (24 * scale));
        
        int left = (int) (scale * 50) + ((BusinessSim.isFullScreen)? 60:0),
            top = (int) (scale * 175), 
            width = (int) (scale * screen.width - 75) - ((BusinessSim.isFullScreen)?60:0),
            height = (int) (scale * 100 * MAX_LINES / 3.5);
        
        // draw the dialog box and "Press Space"
        g.setColor(new Color(0xcc, 0xcc, 0xcc, 150));
        g.fillRect(left, top, width, height);
        g.setColor(Color.BLACK);
        g.drawRect(left, top, width, height);
        
        
        if(inMultipleChoice()) {
            g.drawString("Click an answer", width - ((BusinessSim.isFullScreen)?0:60), top + (int) (90 * scale));
        } else {
            g.drawString("Press Space...", width - ((BusinessSim.isFullScreen)?0:60), top + (int) (90 * scale));
        }
        
        String[] text = subTextArray(lines, index, MAX_LINES);
        drawText(text, g);
    }
    
    /**
     * Called by displayText to actually drawString the lines to the Graphics object
     * @param lines
     * @param g 
     */
    private void drawText(String[] lines, Graphics g) {
        if (g == null || lines == null) {
            return;
        }
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) {
                continue;
            }
            g.setColor(Color.BLACK);
            if(inMultipleChoice() && hoveredOverOption == i) {
                g.setColor(Color.GREEN);
                if(heldOption == i) {
                    g.setColor(Color.BLUE);
                }
            }
            g.drawString(lines[i], 60 + ((BusinessSim.isFullScreen)? 80:0),(int)(((i + 1) * 25 + 175) * scale));
        }
    }
}
