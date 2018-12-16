package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.DeliveryEvent;
import bgu.spl.mics.application.messages.Tick;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.Pair;
// import jdk.incubator.http.internal.common.Pair;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;


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
	private LinkedList<Pair> orderSchedule;
	private Customer myCustomer;
	private Future<OrderReceipt> futureOrder;
	private int orderId;
	private CountDownLatch countDown;
	private LinkedList<Pair> currOrder;
	private  boolean toBrake;

	public APIService(String name, Customer c, CountDownLatch countD){
		super(name);
		this.myCustomer = c;
		this.orderSchedule = c.sortAndGetList();
		orderId = 0;
		this.countDown = countD;
		currOrder = new LinkedList<>();
	}



	@Override
	protected void initialize() {
		subscribeBroadcast(Tick.class , (Tick message)->{
			if(message.getTick()==message.getDuration()){
				terminate();
			}
			boolean toRun = true;
			if(orderSchedule.size() > 0) {
				while (toRun && message.getTick() == orderSchedule.getFirst().getSecond()) {
					int curr = message.getTick();
				//	for (Pair toOrder : orderSchedule) {
					Pair toOrder = orderSchedule.removeFirst();
						BookOrderEvent order = new BookOrderEvent(myCustomer, toOrder.getFirst(), toOrder.getSecond(), orderId);
						orderId++;
						futureOrder = sendEvent(order);
					//	System.out.println(getName() + " sent Event" + order.getBookName());
						if (futureOrder.get() != null) {
							DeliveryEvent deliveryEvent = new DeliveryEvent(myCustomer);
							sendEvent(deliveryEvent);
							futureOrder = null;
						}

						if (orderSchedule.size() == 0 || orderSchedule.getFirst().getSecond() != curr)
							toRun =false;
				//	}


				}
			}
		});
		countDown.countDown();

	}

	public LinkedList<Pair> getOrderSchedule(){
		return orderSchedule;
	}

	public Customer getMyCustomer() {
		return myCustomer;
	}


}
