package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class CheckAvailabilityEvent implements Event<Integer> {
    private String bookName;

    CheckAvailabilityEvent(String book){
        this.bookName=book;
    }

    public String getBookName() {
        return bookName;
    }
}
