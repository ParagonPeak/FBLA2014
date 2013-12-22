package com.FBLA.businesssim.level.zone;

import com.FBLA.businesssim.action.Action;
import com.FBLA.businesssim.action.Pee;
import com.FBLA.businesssim.action.Talk;

/**
 * Purpose: Identify toilet as a zone
 * @see Zone
 * -----
 * @author  Tripp
 * @date    11/14/13
 * @update  Initiate
 */
public class Bath extends Zone{
    
    public Bath()
    {
        actions.add(new Talk());
        actions.add(new Pee());
    }
    
}
