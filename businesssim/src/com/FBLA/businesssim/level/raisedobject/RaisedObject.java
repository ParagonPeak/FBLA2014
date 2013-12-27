package com.FBLA.businesssim.level.raisedobject;

import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;

/**
 *
 * @author RAPHAEL
 */
public class RaisedObject {
    public Sprite sprite;
    
    public static RaisedObject voidObject = new RaisedObject(Sprite.emptySprite);
    public static RaisedObject cubicleSWObject = new RaisedObject(Sprite.cubicleSW);
    public static RaisedObject cubicleSEObject = new RaisedObject(Sprite.cubicleSE);
    
    public static final int voidObjectNum = 0;
    public static final int cubicleSWObjectNum = 1;
    public static final int cubicleSEObjectNum = 2;
    
    public static final int voidObjectRGB = 0xFFFF00FF;
    public static final int cubicleSWObjectRGB = 0xABCDEF;
    public static final int cubicleSEObjectRGB = 0xFEDCBA;
    
    public RaisedObject(Sprite s) {
        sprite = s;
    }
    
    public void render(int x, int y, Screen s)
    {
        s.renderRaisedSprite(x << 5, y << 5, sprite);
    }
}
