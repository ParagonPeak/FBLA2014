package com.FBLA.businesssim.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Keyboard purpose: Handle user input
 * -----
 * @author  Tripp
 * @date    11/12/13
 * @update  Made the class, but will need updates for more user input
 * -----
 */
public class Keyboard implements KeyListener{
    
    public boolean[] keys = new boolean[120];
    public boolean up, down, right, left, action, escape, inc;
    
    /**
     * Called on every update to make sure data is accurate and easy calling.
     * Used to handle inputs and button presses.
     */
    public void update()
    {
        up = keys[KeyEvent.VK_UP] | keys[KeyEvent.VK_W];
        down = keys[KeyEvent.VK_DOWN] | keys[KeyEvent.VK_S];
        right = keys[KeyEvent.VK_RIGHT] | keys[KeyEvent.VK_D];
        left = keys[KeyEvent.VK_LEFT] | keys[KeyEvent.VK_A];
        action = keys[KeyEvent.VK_SPACE] | keys[KeyEvent.VK_X];
        escape = keys[KeyEvent.VK_ESCAPE];
        inc = keys[KeyEvent.VK_P];
        if(escape) System.exit(3);
    }
    
    /**
     * 
     * @param e The actual Event of key pressed. Used to determine what key was
     *          pressed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    /**
     * When key is pressed, tells the program to turn to true, and records data in array
     * @param e The actual Event of key pressed. Used to determine what key was
     *          pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < keys.length)
            keys[e.getKeyCode()] = true;
    }

    /**
     * When key is released, tells the program to turn to false, and records data in array
     * @param e The actual Event of key pressed. Used to determine what key was
     *          pressed
     */
    @Override
    public void keyReleased(KeyEvent e) {
       if(e.getKeyCode() < keys.length)
            keys[e.getKeyCode()] = false;
    }
    
}
