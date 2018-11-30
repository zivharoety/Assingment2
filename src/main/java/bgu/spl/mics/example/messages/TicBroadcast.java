package bgu.spl.mics.example.messages;

import bgu.spl.mics.Broadcast;

public class TicBroadcast implements Broadcast {
    private int Tic;

    public TicBroadcast (int tic){
        this.Tic = tic;
    }

    public int getTic() {
        return Tic;
    }
}
