/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.FBLA.businesssim.graphics;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.input.Keyboard;
import com.FBLA.businesssim.level.Level;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author RAPHAEL
 */
public class TextDisplayer {
       
    int index = 0;
    public boolean textRequiresUpdate = false;
    private boolean lastKeyAction = false;
    private ArrayList<String[]> queue = new ArrayList<String[]>();
    private Screen screen;
    
    public TextDisplayer(Screen s) {
        screen = s;
    }
    
    public void updateText(String line) {
        updateText(new String[]{line});
    }

    public void updateText(String[] lines) {
        if (textRequiresUpdate) {
            BusinessSim.bs.currentText = lines;
        } else {
            queue.add(lines);
        }
        textRequiresUpdate = false;
    }

    public Graphics displayText(Keyboard key, Graphics g) {
        return displayText(queue.get(0), key, g);
    }

    /**
     * This method will display a line of text in a text box for the player to
     * read.
     *
     * @param lines is the array of all the text to display.
     * @param key keyboard to check if the key is pressed.
     */
    public Graphics displayText(String[] lines, Keyboard key, Graphics g) {
        //The lines currently on the screen, with a max of three to following convention
        if (g == null || lines == null || key == null || textRequiresUpdate) {
            lastKeyAction = false;
            return g;
        }
        g.setColor(new Color(0xcc, 0xcc, 0xcc, 150));

        int top = 175, bottom = 100, right = screen.width - 75, left = 50;
        g.fillRect(left, top, right, bottom);
        g.setColor(Color.BLACK);
        g.drawRect(left, top, right, bottom);
        g.drawString("Press Space...", right - 60, top + 90);
        String[] displayedLines = new String[3];
        for (int i = index; i < index + 3 && i < lines.length; i++){// : i += (lines.length - index)) {
            if(i < lines.length)
                displayedLines[i - index] = lines[i];
        }
        if (key.inc && !lastKeyAction) {
            index += 3;
            System.out.println("INCREASE"); //Remove in the end
        }
        if (index > lines.length - 3 && (key.inc && !lastKeyAction)) {
            textRequiresUpdate = queue.isEmpty();
            index = 0;
            System.out.println("Waiting for update!"); //Remove in the end
            if (Level.finished[Level.finished.length - 1] && textRequiresUpdate) {
                BusinessSim.bs.setGameState(BusinessSim.gs_startScreen);
            }
            if (!textRequiresUpdate) {
                BusinessSim.bs.currentText = queue.get(0);
                queue.remove(0);
            }
        }
        lastKeyAction = key.inc;
//        drawText(displayedLines, g).dispose();
        return drawText(displayedLines, g);
    }
    
    public Graphics drawText(String[] lines, Graphics g) {
        if (g == null || lines == null) {
            return g;
        }
        for (int i = 0; i < lines.length; i++) {
            if (lines[i] == null) {
                return g;
            }
            g.drawString(lines[i], 60, ((i + 1) * 25 + 175));
        }
        return g;
    }
}