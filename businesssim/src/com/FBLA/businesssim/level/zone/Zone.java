package com.FBLA.businesssim.level.zone;

import com.FBLA.businesssim.action.Action;
import java.util.ArrayList;

/**
 * Purpose: Identify main class to identify areas -----
 *
 * @author Tripp
 * @date 11/14/13
 * @update Initiate
 */
public class Zone {

    public ArrayList<Action> actions = new ArrayList<Action>();

    public Zone() {
    }

    public Action[] getActions() {
        Action[] a = new Action[actions.size()];
        for (int i = 0; i < a.length; i++) {
            a[i] = actions.get(i);
        }
        return a;
    }
    
    public void act(Action a)
    {
        if(!actions.contains(a))
            return;
        for(Action i: actions)
            if(i.getClass() == a.getClass())
                i.act();
    }
}
