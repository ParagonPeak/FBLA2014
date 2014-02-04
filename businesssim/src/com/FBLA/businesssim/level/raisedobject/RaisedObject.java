package com.FBLA.businesssim.level.raisedobject;

import com.FBLA.businesssim.entity.Entity;
import com.FBLA.businesssim.graphics.Screen;
import com.FBLA.businesssim.graphics.Sprite;
import java.util.HashMap;

/**
 *
 * @author RAPHAEL
 */
public class RaisedObject {
    public Sprite sprite;
    protected final int NUM;
    protected final int RGB;
    
    protected static HashMap raisedObjsNumMap = new HashMap(33);
    protected static HashMap raisedObjsRGBMap = new HashMap(33);
    
    public static RaisedObject voidObject = new RaisedObject(Sprite.emptySprite, 0, 0xffff00ff);
    public static RaisedObject smallWallSWObject        = new RaisedObject(Sprite.smallWallSW       , 1, 0xffff1111);
    public static RaisedObject smallWallSEObject        = new RaisedObject(Sprite.smallWallSE       , 2, 0xffff2222);
    public static RaisedObject smallWallSW2Object       = new RaisedObject(Sprite.smallWallSW2      , 3, 0xffff3333);
    public static RaisedObject smallWallSE2Object       = new RaisedObject(Sprite.smallWallSE2      , 4, 0xffff4444);
    public static RaisedObject bigWallSWObject          = new RaisedObject(Sprite.bigWallSW         , 5, 0xffff5555);
    public static RaisedObject bigWallSEObject          = new RaisedObject(Sprite.bigWallSE         , 6, 0xffff6666);
    public static RaisedObject cornerEObject            = new RaisedObject(Sprite.cornerE           , 7, 0xff11ff11);
    public static RaisedObject cornerSObject            = new RaisedObject(Sprite.cornerS           , 8, 0xff33ff33);
    public static RaisedObject cornerWObject            = new RaisedObject(Sprite.cornerW           , 9, 0xff44ff44);
    public static RaisedObject cornerNObject            = new RaisedObject(Sprite.cornerN           ,10, 0xff55ff55);
    public static RaisedObject tWallSWObject            = new RaisedObject(Sprite.tWallSW           ,11, 0xffddff00);
    public static RaisedObject tWallNWObject            = new RaisedObject(Sprite.tWallNW           ,12, 0xffddff44);
    public static RaisedObject tWallNEObject            = new RaisedObject(Sprite.tWallNE           ,13, 0xffddff66);
    public static RaisedObject tWallSEObject            = new RaisedObject(Sprite.tWallSE           ,14, 0xffddff77);
    public static RaisedObject crossWallObject          = new RaisedObject(Sprite.crossWall         ,15, 0xffddff88);
    public static RaisedObject platformWallSWObject     = new RaisedObject(Sprite.platformWallSW    ,16, 0xff00aaff);
    public static RaisedObject platformWallSEObject     = new RaisedObject(Sprite.platformWallSE    ,17, 0xff44aaff);
    public static RaisedObject cornerPlatformEObject    = new RaisedObject(Sprite.cornerPlatformE   ,18, 0xff44ffff);
    public static RaisedObject cornerPlatformNObject    = new RaisedObject(Sprite.cornerPlatformN   ,19, 0xff55ffff);
    public static RaisedObject cornerPlatformWObject    = new RaisedObject(Sprite.cornerPlatformW   ,20, 0xff66ffff);
    public static RaisedObject tPlatformSWObject        = new RaisedObject(Sprite.tPlatformSW       ,21, 0xffaaaaff);
    public static RaisedObject tPlatformSEObject        = new RaisedObject(Sprite.tPlatformSE       ,22, 0xffbbaaff);
    public static RaisedObject platformCrossObject      = new RaisedObject(Sprite.platformCross     ,23, 0xffccaaff);
    public static RaisedObject crossSidePlatformsObject = new RaisedObject(Sprite.crossSidePlatforms,24, 0xffddaaff);
    public static RaisedObject computerWallSWObject     = new RaisedObject(Sprite.computerWallSW    ,25, 0xff00ccff);
    public static RaisedObject computerWallSEObject     = new RaisedObject(Sprite.computerWallSE    ,26, 0xff44ccff);
    public static RaisedObject deskWallSEObject         = new RaisedObject(Sprite.deskWallSE        ,27, 0xffaaccff);
    public static RaisedObject deskWallSWObject         = new RaisedObject(Sprite.deskWallSW        ,28, 0xffccccff);
    public static RaisedObject raisedCabinetSEObject    = new RaisedObject(Sprite.raisedCabinetSE   ,29, 0xffaaddff);
    public static RaisedObject raisedCabinetSWObject    = new RaisedObject(Sprite.raisedCabinetSW   ,30, 0xffccddff);
    public static RaisedObject paperPlatformSObject     = new RaisedObject(Sprite.paperPlatformS    ,31, 0xffaaeecc);
    public static RaisedObject paperPlatformSWObject    = new RaisedObject(Sprite.paperPlatformSW   ,32, 0xffcceecc);
    public static RaisedObject deskSW                   = new RaisedObject(Sprite.deskSW            ,33, 0xff7f3300);
    public static RaisedObject deskSE                   = new RaisedObject(Sprite.deskSE            ,34, 0xff7f3355);
    public static RaisedObject deskNW                   = new RaisedObject(Sprite.deskNW            ,35, 0xff7f3377);
    public static RaisedObject deskNE                   = new RaisedObject(Sprite.deskNE            ,36, 0xff7f3399);
    public static RaisedObject wallSW                   = new RaisedObject(Sprite.wallSW            ,37, 0xffeeeeee);
    public static RaisedObject wallSE                   = new RaisedObject(Sprite.wallSE            ,38, 0xffeeeecc);
    public static RaisedObject elevatorSW               = new RaisedObject(Sprite.elevatorSW        ,39, 0xffeeee55);
    public static RaisedObject elevatorSE               = new RaisedObject(Sprite.elevatorSE        ,40, 0xffeeee00);
    public static RaisedObject chairObject              = new RaisedObject(Sprite.chair             ,41, 0xff404040);
//    public static RaisedObject chairSEObject    = new RaisedObject(Sprite.chairSE, 33, 0x000000);
//    public static RaisedObject  chairSObject    = new RaisedObject( Sprite.chairS, 34, 0x000000);
//    public static RaisedObject chairSWObject    = new RaisedObject(Sprite.chairSW, 35, 0x000000);
//    public static RaisedObject  chairWObject    = new RaisedObject( Sprite.chairW, 36, 0x000000);
//    public static RaisedObject chairNWObject    = new RaisedObject(Sprite.chairNW, 37, 0x000000);
//    public static RaisedObject  chairNObject    = new RaisedObject( Sprite.chairN, 38, 0x000000);
//    public static RaisedObject chairNEObject    = new RaisedObject(Sprite.chairNE, 39, 0x000000);
//    public static RaisedObject  chairEObject    = new RaisedObject( Sprite.chairE, 40, 0x000000);
    
    public RaisedObject(Sprite s, int number, int rgb) {
        sprite = s;
        NUM = number;
        RGB = rgb;
        raisedObjsNumMap.put((Integer) NUM, this);
        raisedObjsRGBMap.put((Integer) rgb, this);
    }
    
    public static RaisedObject getRaisedObjectFromNumber(int number) {
        if(raisedObjsNumMap.get(number) == null) return (RaisedObject) voidObject;
        return (RaisedObject) raisedObjsNumMap.get(number);
    }
    
    public static RaisedObject getRaisedObjectFromColor(int color) {
        if(raisedObjsRGBMap.get(color) == null) return (RaisedObject) voidObject;
        return (RaisedObject) raisedObjsRGBMap.get(color);
    }
    
    public static String getEntityType(int number)
    {
        if(number < 1 || number > 41) return Entity.class.toString();
        if(number <= 28) return Wall.class.toString();
        if(number <= 30) return Storage.class.toString();
    }
    
    public static int getColor(RaisedObject r) {
        return r.RGB;
    }
    public static int getNum(RaisedObject r) {
        return r.NUM;
    }
    
    public void render(int x, int y, Screen s)
    {
        s.renderRaisedSprite(x << 5, y << 5, sprite);
    }
}
