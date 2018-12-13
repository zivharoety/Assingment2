package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;

import java.io.Serializable;

public class DeliveryEvent implements Event , Serializable {
    private Customer customer;

    public DeliveryEvent(Customer customer){
        this.customer = customer;
        //System.out.println("new DE for "+customer.getName());
    }

    public Customer getCustomer() {
        return customer;
    }
}
