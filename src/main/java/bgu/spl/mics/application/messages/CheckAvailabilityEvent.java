package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class CheckAvailabilityEvent implements Event<Integer> {
    private String bookName;

    public CheckAvailabilityEvent(String book){
        this.bookName=book;
        //System.out.println("new CEA for "+book);
    }

    public String getBookName() {
        return bookName;
    }
}
