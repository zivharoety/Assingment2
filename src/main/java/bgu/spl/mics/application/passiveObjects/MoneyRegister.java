package bgu.spl.mics.application.passiveObjects;


import java.io.*;
import java.util.LinkedList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Passive object representing the store finance management. 
 * It should hold a list of receipts issued by the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class MoneyRegister implements Serializable {
	AtomicInteger totalEarning;
	LinkedList<OrderReceipt> recipts;
	AtomicReference<LinkedList<OrderReceipt>> orders;
	/**
     * Retrieves the single instance of this class.
     */
	private MoneyRegister(){
		totalEarning = new AtomicInteger(0);
		recipts = new LinkedList<>();
		orders = new AtomicReference<>();

	}
	private static class MoneyRegisterHolder {
		private static MoneyRegister instance = new MoneyRegister();
	}
	public static MoneyRegister getInstance() {
		//TODO: Implement this
		return MoneyRegisterHolder.instance;
	}
	
	/**
     * Saves an order receipt in the money register.
     * <p>   
     * @param r		The receipt to save in the money register.
     */
	public void file (OrderReceipt r) {
			synchronized (recipts){
				recipts.add(r);
			}
	}
	
	/**
     * Retrieves the current total earnings of the store.  
     */
	public int getTotalEarnings() {
		//TODO: Implement this
		return totalEarning.get();
	}
	
	/**
     * Charges the credit card of the customer a certain amount of money.
     * <p>
     * @param amount 	amount to charge
     */
	public void chargeCreditCard(Customer c, int amount) {
		//System.out.println("Money Register: charging credit card "+c.getName()+" amount: "+amount);
				totalEarning.addAndGet(amount);
				c.reduceCredit(amount);
	}
	
	/**
     * Prints to a file named @filename a serialized object List<OrderReceipt> which holds all the order receipts 
     * currently in the MoneyRegister
     * This method is called by the main method in order to generate the output.. 
     */
	public void printOrderReceipts(String filename) {
		try {
			FileOutputStream toPrint = new FileOutputStream(new File(filename));
			ObjectOutputStream toWrite = new ObjectOutputStream(toPrint);
			toWrite.writeObject(recipts);
			//toWrite.flush();//to check if really necessary.
			toWrite.close();

		} catch (FileNotFoundException ignord) {

		} catch (IOException ignord) {

		}
	}
}
