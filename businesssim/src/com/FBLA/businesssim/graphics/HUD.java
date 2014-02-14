package com.FBLA.businesssim.graphics;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.level.Level;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Displays the overhead information such as floor number and score
 */
public class HUD {

    public static void displayHUD(Graphics g) {
        double scale = BusinessSim.bs.scale;
        Font tahoma = new Font("Tahoma", Font.PLAIN, (int) (24 * scale));
        Font courier = new Font("Courier new", Font.PLAIN, (int) (24 * scale));

        // draw blue bar/black outline on top
        g.setColor(new Color(0x55, 0x55, 0xcc, 100));
        if (BusinessSim.isFullScreen) {
            g.fillRect(140, 0, BusinessSim.bs.width - 140, BusinessSim.bs.height / 14);
            g.setColor(Color.BLACK);
            g.drawRect(137, -3, BusinessSim.bs.width - 140, BusinessSim.bs.height / 14 + 3);
        } else {
            g.fillRect(0, 0, BusinessSim.bs.width, BusinessSim.bs.height / 14);
            g.setColor(Color.BLACK);
            g.drawRect(-3, -3, BusinessSim.bs.width, BusinessSim.bs.height / 14 + 3);
        }

        // FPS Counter
        g.setColor(Color.WHITE);
        g.setFont(courier);
        g.drawString("FPS: " + BusinessSim.bs.FPS, (int) ((BusinessSim.bs.screen.width - 120) * scale), (int) (25 * scale));

        g.setFont(tahoma);

        // If near elevator, lets you know you can use it
        if (BusinessSim.bs.nearElevator) {
            g.setColor(new Color(0xcc, 0xff, 0xcc, 200));
            if (BusinessSim.bs.currentLevel == 0 && !BusinessSim.bs.level.finished[BusinessSim.bs.currentLevel]) {
                g.drawString("Not ready to advance", (int) (3 * scale)+ ((BusinessSim.isFullScreen) ? 140:0), (int) (24 * scale));
            } else {
                g.drawString("Press X for elevator panel", (int) (3 * scale)+ ((BusinessSim.isFullScreen) ? 140:0), (int) (24 * scale));
            }
        } else if (Level.isNearHunt) { // if you're near a pickup, lets you know you can pick it up
            g.setColor(new Color(0xdd, 0xff, 0xcc, 200));
            g.drawString("Press X to collect", (int) (3 * scale)+ ((BusinessSim.isFullScreen) ? 140:0), (int) (24 * scale));
        } else { // if nothing special's going on, show the floor you're on / pickups left
            // floor
            g.setColor(new Color(0xdd, 0xff, 0xaa, 200));
            g.drawString("Floor: " + (BusinessSim.bs.currentLevel + 1), (int) (3 * scale) + ((BusinessSim.isFullScreen) ? 140:0), (int) (25 * scale));

            // pickups left
            g.setColor(new Color(0xff, 0xff, 0xaa, 200));
            g.drawString("Pickups left here: " + BusinessSim.bs.level.itemCount, (int) (100 * scale)+ ((BusinessSim.isFullScreen) ? 140:0), (int) (25 * scale));
            
            // score
            g.setColor(new Color(0xff, 0xff, 0xcc, 200));
            g.drawString("Score: " + BusinessSim.bs.score, (int) (350 * scale)+ ((BusinessSim.isFullScreen) ? 140:0), (int) (25 * scale));
        }
    }
}
