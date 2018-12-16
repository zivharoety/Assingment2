package bgu.spl.mics.application.passiveObjects;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import static bgu.spl.mics.application.passiveObjects.OrderResult.*;
/**
 * Passive data-object representing the store inventory.
 * It holds a collection of {@link BookInventoryInfo} for all the
 * books in the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */


public class Inventory implements Serializable {
		public Map<String,BookInventoryInfo> map;

		private Inventory(){
			map = new ConcurrentHashMap<>();
		}
	/**
     * Retrieves the single instance of this class.
     */
	private static class InventoryHolder {
		private static Inventory instance = new Inventory();
	}

	public static Inventory getInstance() {
		return InventoryHolder.instance;
	}

	
	/**
     * Initializes the store inventory. This method adds all the items given to the store
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load (BookInventoryInfo[ ] inventory ) {
		for(BookInventoryInfo b : inventory){
			map.put(b.getBookTitle(),b);
		}
	} // initializting once, no need to synchronize
	
	/**
     * Attempts to take one book from the store.
     * <p>
     * @param book 		Name of the book to take from the store
     * @return 	an {@link Enum} with options NOT_IN_STOCK and SUCCESSFULLY_TAKEN.
     * 			The first should not change the state of the inventory while the 
     * 			second should reduce by one the number of books of the desired type.
     */
	public OrderResult take (String book) {
	//	System.out.println("Inventory: Trying to take "+book);
		if(map.get(book) == null)
			return NOT_IN_STOCK;
		// checking whether it is in stock
		if(!map.get(book).getSem().tryAcquire())
			return NOT_IN_STOCK;
		map.get(book).reduceAmount(); // reduce amount
		return SUCCESSFULLY_TAKEN;
	}


	/**
     * Checks if a certain book is available in the inventory.
     * <p>
     * @param book 		Name of the book.
     * @return the price of the book if it is available, -1 otherwise.
     */
	public int checkAvailabiltyAndGetPrice(String book){
	//	System.out.println("Inventory: checking availability "+book);
		if(map.get(book) == null || map.get(book).getAmountInInventory() == 0)
			return -1;
		return map.get(book).getPrice();
	}

	
	/**
     * 
     * <p>
     * Prints to a file name @filename a serialized object HashMap<String,Integer> which is a Map of all the books in the inventory. The keys of the Map (type {@link String})
     * should be the titles of the books while the values (type {@link Integer}) should be
     * their respective available amount in the inventory. 
     * This method is called by the main method in order to generate the output.
     */
	public void printInventoryToFile(String filename) {
		HashMap<String, Integer> finalInventory = new HashMap<>();
		for (String s : map.keySet()) {
			finalInventory.put(s, map.get(s).getAmountInInventory());
		}
		try (FileOutputStream toPrint = new FileOutputStream(new File(filename))) {
			try (ObjectOutputStream toWrite = new ObjectOutputStream(toPrint)) {
				toWrite.writeObject(finalInventory);
			} catch (FileNotFoundException ignord) {

			} catch (IOException ignord) {

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		// to delete
		public BookInventoryInfo[] getBooks() {
		BookInventoryInfo[] toReturn = new BookInventoryInfo[map.size()];
		int i = 0;
		for(String s : map.keySet()){
			toReturn[i] = map.get(s);
			i++;
		}

		return toReturn;
	}

}
