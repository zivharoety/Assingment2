package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderResult;

import java.io.Serializable;

public class TakeBookEvent implements Event<OrderResult> , Serializable {
    private String bookName;

    public TakeBookEvent(String book){
        this.bookName=book;
        //System.out.println("new Take book event for "+book);
    }

    public String getBookName() {
        return bookName;
    }
}

