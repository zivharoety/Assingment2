package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import java.io.Serializable;

public class JsonParser implements Serializable {

    public BookInventoryInfo[] initialInventory;
    public Resource[] initialResources;
    public Services services ;

    public void setSema(){
        for(int i =0 ; i < initialInventory.length ; i++){
            initialInventory[i].setSemaphore();
        }
    }
}
