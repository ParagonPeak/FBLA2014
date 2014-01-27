package com.FBLA.businesssim.entity.mob;

import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import com.FBLA.businesssim.input.Keyboard;
import com.FBLA.businesssim.util.Vector2d;

public class Player extends Mob{
    
    private Screen screen;
    private Keyboard keys;
    public boolean actionDown = false;
    
    public Player(Vector2d v, Screen sc, Keyboard k)
    {
        super(v,"Player");
        keys = k;
        screen = sc;
        sprite = Sprite.sprites.get(0);
    }
    
    public void render(Screen screen)
    {
        screen.renderPlayer(v.getiX(), v.getiY(), sprite);
    }
    
    public void update() {
        actionDown = false;
        if(keys.action) {
            actionDown = true;
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
//        if(++ups % 6 == 0)
//        for(Enemy e: JFTC.enemies)
//        {
//            if(colBox.intersects(e.colBox))
//                if(e.getClass().equals(King.class))
//                {e.health--;}
//                else if(e.getClass().equals(Enemy.class))
//                {
//                    
//                }
//        }
    }
}
