package main.java.bgu.spl.mics.application.passiveObjects;

/**
 * Passive data-object representing a information about a certain book in the inventory.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class BookInventoryInfo {
	private String bookTitle;
	private int price;
	private int amountInInventory;


	public BookInventoryInfo (String bookTitle ,int price, int amountInInventory){
		this.bookTitle=bookTitle;
		this.price=price;
		this.amountInInventory=amountInInventory;
	}

	/**
     * Retrieves the title of this book.
     * <p>
     * @return The title of this book.   
     */
	public String getBookTitle() {
		// TODO Implement this
		return null;
	}

	/**
     * Retrieves the amount of books of this type in the inventory.
     * <p>
     * @return amount of available books.      
     */
	public int getAmountInInventory() {
		// TODO Implement this
		return 0;
	}

	/**
     * Retrieves the price for  book.
     * <p>
     * @return the price of the book.
     */
	public int getPrice() {
		// TODO Implement this
		return 0;
	}

	@/**
	 * Retrieves the quantity for  book in the inventory.
	 * <p>
	 * @return the quantity of the book in the inventory.
	 */
	public int getQuantity(String Title) {
		return -1;
	}
}