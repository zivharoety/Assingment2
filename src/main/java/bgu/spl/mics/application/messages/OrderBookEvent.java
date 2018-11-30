package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;

public class OrderBookEvent implements Event<OrderReceipt> {
    private Customer customer;
    private String bookName;
    private int tic;

    public OrderBookEvent(Customer customer , String book , int tic){
        this.bookName = book;
        this.customer = customer;
        this.tic = tic;
    }


    public Customer getCustomer() {
        return customer;
    }

    public String getBookName() {
        return bookName;
    }

    public int getTic() {
        return tic;
    }
    }
