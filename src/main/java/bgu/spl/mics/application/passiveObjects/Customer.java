package bgu.spl.mics.application.passiveObjects;


import bgu.spl.mics.Pair;

import java.util.Arrays;
import java.util.Comparator;
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

	public LinkedList<Pair> sortAndGetList(){
		Comparator<Order> comp = new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				if(o1.tick>o2.tick)
					return 1;
				if(o1.tick<o2.tick)
					return -1;
				return 0;
			}
		};
		Arrays.sort(orderSchedule , comp);
		LinkedList<Pair> toReturn = new LinkedList<>();
		for(int i=0;i<orderSchedule.length;i++)
			toReturn.addLast(new Pair(orderSchedule[i].bookTitle,orderSchedule[i].tick));
		return toReturn;
	}
	public String getName() {
		return name;
	}

	/**
     * Retrieves the ID of the customer  . 
     */
	public int getId() {
		return id;
	}
	
	/**
     * Retrieves the address of the customer.  
     */
	public String getAddress() {
		return address;
	}
	
	/**
     * Retrieves the distance of the customer from the store.  
     */
	public int getDistance() {
		return distance;
	}

	
	/**
     * Retrieves a list of receipts for the purchases this customer has made.
     * <p>
     * @return A list of receipts.
     */
	public List<OrderReceipt> getCustomerReceiptList() {
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

	public Order[] getOrderSchedule() {
		return orderSchedule;
	}

}
