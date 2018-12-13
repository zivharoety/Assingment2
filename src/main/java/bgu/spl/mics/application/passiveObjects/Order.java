package bgu.spl.mics.application.passiveObjects;

import java.io.Serializable;

public class Order implements Serializable {
   public String bookTitle;
   public int tick;

    public Order(String bookTitleitle,int tick){
        this.bookTitle = bookTitle;
        this.tick = tick;
    }
}
