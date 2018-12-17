package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

import java.io.Serializable;

public class CheckAvailabilityEvent implements Event<Integer> , Serializable {
    private String bookName;

    public CheckAvailabilityEvent(String book){
        this.bookName=book;
    }

    public String getBookName() {
        return bookName;
    }
}
