package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;

import java.io.Serializable;

public class FetchVehicle implements Event<Future<DeliveryVehicle>> , Serializable {
    private int distance;

    public FetchVehicle (int distance){
        this.distance = distance;
        //System.out.println("new FV was established");
    }

    public int getDistance() {
        return distance;
    }
}
