package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;


public class ReleaseVehicle implements Event<Boolean> {
   private DeliveryVehicle vehicle;

    public ReleaseVehicle(DeliveryVehicle vehicle){
        this.vehicle = vehicle;
        //System.out.println("Release vehicle event "+vehicle.getLicense());
    }

    public DeliveryVehicle getVehicle() {
        return vehicle;
    }

}
