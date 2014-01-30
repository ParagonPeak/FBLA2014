package com.FBLA.businesssim.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse extends MouseAdapter implements MouseListener {
    
    public boolean mouseClicked, mouseHeld;
    public int xPos, yPos;

    public void mouseClicked(MouseEvent e) {
        mouseClicked = !mouseClicked;
        updatePos(e);
    }

    public void mousePressed(MouseEvent e) {
        mouseHeld = true;
    }

    public void mouseReleased(MouseEvent e) {
        mouseClicked = false;
        mouseHeld = false;
    }

    public void mouseDragged(MouseEvent e) {
        mouseHeld = true;
        updatePos(e);
    }

    public void mouseMoved(MouseEvent e) {
        updatePos(e);
    }

    public void updatePos(MouseEvent e) {
        xPos = e.getX();
        yPos = e.getY();
    }
}