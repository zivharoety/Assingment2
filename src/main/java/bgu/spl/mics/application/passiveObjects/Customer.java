package bgu.spl.mics.application.passiveObjects;

import com.sun.tools.corba.se.idl.constExpr.Or;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Passive data-object representing a customer of the store.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Customer {
	private int id;
	private String name;
	private String address;
	private int distance;
	private int creditNumber;
	private int creditAmount;
	private LinkedList<OrderReceipt> myOrders;
	private Semaphore sem;
	private Order[] orderSchedule;
	private creditCard creditCard;
	/**
     * Retrieves the name of the customer.
     */
	public Customer(int id,String name,String address,int distance,creditCard creditCard,Order[] orderSchedule){
		this.id = id;
		this.name = name;
		this.address = address;
		this.distance = distance;
		this.creditCard = creditCard;
		this.orderSchedule = orderSchedule;

	}
	public String getName() {

		return name;
	}

	/**
     * Retrieves the ID of the customer  . 
     */
	public int getId() {
		// TODO Implement this
		return id;
	}
	
	/**
     * Retrieves the address of the customer.  
     */
	public String getAddress() {
		// TODO Implement this
		return address;
	}
	
	/**
     * Retrieves the distance of the customer from the store.  
     */
	public int getDistance() {
		// TODO Implement this
		return distance;
	}

	
	/**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     * @return A list of receipts.
     */
	public List<OrderReceipt> getCustomerReceiptList() {
		// TODO Implement this
		return myOrders;
	}
	
	/**
     * Retrieves the amount of money left on this customers credit card.
     * <p>
     * @return Amount of money left.   
     */
	public int getAvailableCreditAmount() {

		return creditAmount;
	}
	
	/**
     * Retrieves this customers credit card serial number.    
     */
	public int getCreditNumber() {
		// TODO Implement this
		return creditNumber;
	}


	public Semaphore getSem() {
		return sem;
	}

	public void reduceCredit(int amount){
		creditAmount = creditAmount - amount;
	}

	public void setCustomer(){
		this.sem = new Semaphore(1);
		this.creditNumber = this.creditCard.getNumber();
		this.creditAmount = this.creditCard.getAmount();
	}
}
