package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class Tick implements Broadcast {
    private int Tick;
    private int duration;

    public Tick(int tick , int duration){
        this.Tick = tick;
        this.duration = duration;
    }

    public int getTick() {
        return Tick;
    }

    public int getDuration() {
        return duration;
    }
}
