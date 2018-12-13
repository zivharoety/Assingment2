package bgu.spl.mics.application.passiveObjects;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

/**
 * Passive data-object representing a information about a certain book in the inventory.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class BookInventoryInfo implements Serializable {
	private String bookTitle;
	private int price;
	private int amount;
	private Semaphore sem;


	public BookInventoryInfo (String bookTitle ,int price, int amount){
		this.bookTitle=bookTitle;
		this.price=price;
		this.amount=amount;
		//this.sem = new Semaphore(amount);
	}

	/**
     * Retrieves the title of this book.
     * <p>
     * @return The title of this book.   
     */
	public String getBookTitle() {
		return bookTitle;
	}

	/**
     * Retrieves the amount of books of this type in the inventory.
     * <p>
     * @return amount of available books.      
     */
	public int getAmountInInventory() {
		return amount;
	}

	/**
     * Retrieves the price for  book.
     * <p>
     * @return the price of the book.
     */
	public int getPrice() {
		return price;
	}

	public void reduceAmount(){
		amount = amount -1;
	}

	public Semaphore getSem() {
		return sem;
	}

	public void setSemaphore(){
		sem = new Semaphore(this.amount);
	}
}
