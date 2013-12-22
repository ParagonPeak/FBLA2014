package com.FBLA.businesssim.entity.mob.stats;

public class Stat {

    protected int val;
    public static final int MAX_VAL = 100, MIN_VAL = -100;

    public Stat() {
        val = 100;
    }

    public Stat(int i) {
        val = i;
    }

    public void add(int i) {
        val += i;
        if (val > MAX_VAL) {
            val = MAX_VAL;
        }
    }

    public Stat changeVal(int i) {
        if (i > MAX_VAL) {
            i = MAX_VAL;
        }
        if (i < MIN_VAL) {
            i = MIN_VAL;
        }
        val = i;
        return this;
    }

    public int getVal() {
        return val;
    }
}
