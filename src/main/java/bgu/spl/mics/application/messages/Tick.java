package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class Tick implements Broadcast {
    private int Tick;

    public Tick(int tick){
        this.Tick = tick;
    }

    public int getTick() {
        return Tick;
    }
}
