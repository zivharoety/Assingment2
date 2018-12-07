package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.TicBroadcast;
import bgu.spl.mics.application.passiveObjects.*;
import com.sun.jdi.IntegerValue;
import javafx.util.Pair;

import java.util.LinkedList;
import java.util.List;

/**
 * APIService is in charge of the connection between a client and the store.
 * It informs the store about desired purchases using {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class APIService extends MicroService{
	private LinkedList<Pair<String,Integer>> orderSchedule;
	private Customer myCustomer;
	private Future<OrderReceipt> futureOrder;

	public APIService(String name) {
		super(name);
	}



	@Override
	protected void initialize() {
		subscribeBroadcast(TicBroadcast.class , (TicBroadcast message)->{
			while ( message.getTic() == orderSchedule.getFirst().getValue().intValue()){
				Pair<String,Integer> toOrder = orderSchedule.removeFirst();
				BookOrderEvent order = new BookOrderEvent(myCustomer , toOrder.getKey(),toOrder.getValue().intValue());
				futureOrder = sendEvent(order);
				if(futureOrder.get() != null){
					DeliveryEvent deliveryEvent = new DeliveryEvent(myCustomer);
					sendEvent(deliveryEvent);
				}
			}
		});
	}

	public LinkedList<Pair<String,Integer>> getOrderSchedule(){
		return orderSchedule;
	}

	public void setOrderSchedule(LinkedList<Pair<String,Integer>> orderSchedule) {
		this.orderSchedule = orderSchedule;
	}

	public Customer getMyCustomer() {
		return myCustomer;
	}

	public void setMyCustomer(Customer myCustomer) {
		this.myCustomer = myCustomer;
	}
}
