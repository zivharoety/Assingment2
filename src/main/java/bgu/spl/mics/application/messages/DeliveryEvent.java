package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;

public class DeliveryEvent implements Event {
    private Customer customer;

    public DeliveryEvent(Customer customer){
        this.customer = customer;
        //System.out.println("new DE for "+customer.getName());
    }

    public Customer getCustomer() {
        return customer;
    }
}
