package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.BusinessSim;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.input.Keyboard;
import com.FBLA.businesssim.util.Vector2d;

/**
 * The player class. This uses player input to move the player around and have
 * player interact with the environment.
 *
 * @author Tripp
 */
public class Player extends Mob {

    private Keyboard keys;
    public boolean actionDown = false;

    /**
     * Constructor of the player
     * @param v the starting vector/position
     * @param k used for movement and interaction
     */
    public Player(Vector2d v, Keyboard k) {
        super(v, "Player");
        keys = k;
        sprite = Sprite.sprites.get(0);
    }

    /**
     * Draws the player
     *
     * @param screen
     */
    public void render(Screen screen) {
        screen.renderPlayer(v.getiX(), v.getiY(), sprite);
    }

    
    /**
     * Uses keyboard to move player around and animate the sprite
     */
    @Override
    public void update() {
        actionDown = false;
        //Allows the player to interact with environment with the action button 
        if (keys.action) {
            actionDown = true;
        }
        //Keeps the player form moving if they are being prompted
        if (BusinessSim.isPrompting) {
            moving = false;
            return;
        }
        double dx = 0, dy = 0;
        if (keys.up) {
            dy -= speed;
            sprite = Sprite.backwardsPlayerSpriteFlip;
        }
        if (keys.down) {
            dy += speed;
            sprite = Sprite.playerSprite;
        }
        if (keys.left) {
            dx -= speed;
            sprite = Sprite.backwardsPlayerSprite;
        }
        if (keys.right) {
            dx += speed;
            sprite = Sprite.playerSpriteFlip;
        }

        //Checks for collision and sets movement
        moving = (dx != 0 || dy != 0);
        if (moving) {
            if (collision(dy, true)) {
                dy = 0;
            }
            if (collision(dx, false)) {
                dx = 0;
            }
            move(dx, dy);
        }
    }
}
