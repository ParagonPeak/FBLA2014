package com.FBLA.businesssim.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse extends MouseAdapter implements MouseListener, MouseMotionListener {
    
    /**
     * MouseHeld is true when the mouse button is held down
     * MouseClicked is true when the user just pressed and released the mouse button without moving
     */
    public boolean mouseClicked, mouseHeld;
    public boolean lastMouseClicked, lastMouseHeld;
    public int xPos, yPos;

    /**
     * Updates the variables during the update loop in the main game
     * @see BusinessSim
     */
    public void update()  {
        lastMouseClicked = mouseClicked;
        lastMouseHeld = mouseHeld;
        mouseClicked = false;
    }
    
    /**
     * Standard Mouse Listener for a button click
     * @param e actual mouse event
     */
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
        updatePos(e);
    }
    
    /**
     * Standard Mouse Listener for a button click
     * @param e actual mouse event
     */
    public void mousePressed(MouseEvent e) {
        mouseHeld = true;
        mouseClicked = false;
    }

    /**
     * Standard Mouse Listener for a button click
     * @param e actual mouse event
     */
    public void mouseReleased(MouseEvent e) {
        mouseHeld = false;
    }

    /**
     * Standard Mouse Listener for a button click and moving mouse
     * @param e actual mouse event
     */
    public void mouseDragged(MouseEvent e) {
        mouseHeld = true;
        mouseClicked = false;
        updatePos(e);
    }
    
    /**
     * Standard Mouse Listener for movement
     * @param e actual mouse event
     */
    public void mouseMoved(MouseEvent e) {
        mouseClicked = false;
        mouseHeld = false;
        updatePos(e);
    }
    
    /**
     * Updates the Mouse's position
     * @param e actual mouse event
     */
    public void updatePos(MouseEvent e) {
        xPos = e.getX();
        yPos = e.getY();
    }
    
    public void mouseEntered(MouseEvent e) {
        mouseClicked = false;
        mouseHeld = false;
    }

    public void mouseExited(MouseEvent e) {
        mouseClicked = false;
        mouseHeld = false;
    }
}