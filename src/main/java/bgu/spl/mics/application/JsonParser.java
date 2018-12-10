package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.DeliveryVehicle;
import com.google.gson.annotations.SerializedName;

public class JsonParser {

    public BookInventoryInfo[] initialInventory;
    public Resource[] initialResources;
    public Services services ;

    public void setSema(){
        for(int i =0 ; i < initialInventory.length ; i++){
            initialInventory[i].setSemaphore();
        }
    }
}
