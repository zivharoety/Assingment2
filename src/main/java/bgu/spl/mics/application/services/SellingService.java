package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.CheckAvailabilityEvent;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.messages.Tick;

import java.util.concurrent.CountDownLatch;

/**
 * Selling service in charge of taking orders from customers.
 * Holds a reference to the {@link MoneyRegister} singleton of the store.
 * Handles {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class SellingService extends MicroService{
	private Future<Integer> futureAvailable;
	private Future<OrderResult> futureIsTaken;
	private MoneyRegister moneyRegister;
	private CountDownLatch countDown;
	private int currTick;

	public SellingService(String name, CountDownLatch countD) {
		super(name);
		currTick = 0;
		countDown = countD;
		moneyRegister = MoneyRegister.getInstance();
	}

	@Override
	protected void initialize() {
		//System.out.println(getName()+" subscribing to Time broadcast");
		subscribeBroadcast(Tick.class,(Tick message)->{
			if(message.getTick()==message.getDuration()) {
			//	System.out.println(getName()+" recieved last tick");
				terminate();

			}
			currTick = message.getTick();
		//	System.out.println(getName()+" recieved tick number "+message.getTick());
		});
		//System.out.println(getName()+" started running");
		subscribeEvent(BookOrderEvent.class , (BookOrderEvent message)->{
		//	System.out.println(getName() + "got a Book Order Event");
			CheckAvailabilityEvent toCheck = new CheckAvailabilityEvent(message.getBookName());
			futureAvailable = sendEvent(toCheck);
		//	System.out.println(getName()+" sending future available");
			boolean gotKey = false;
			if(futureAvailable.get()!=null) {
				if (futureAvailable.get() != -1) {
				//	System.out.println(getName() + " service got an answer for future available");
					while (!gotKey) {
						try {
							message.getCustomer().getSem().acquire();
							gotKey = true;
						} catch (InterruptedException igrnored) {
						}
					}
					if (message.getCustomer().getAvailableCreditAmount() >= futureAvailable.get()) {

						TakeBookEvent toTake = new TakeBookEvent(message.getBookName());
						futureIsTaken = sendEvent(toTake);
					//	System.out.println(getName() + " sending future is taken");
						if (futureIsTaken.get() != null) {
							if (futureIsTaken.get() == OrderResult.SUCCESSFULLY_TAKEN) {
				//				System.out.println(getName() + " service got an answer for future is taken");
								OrderReceipt receipt = new OrderReceipt(message.getOrderId(), this.getName(), message.getCustomer().getId(), message.getBookName(),
										message.getOrderTick(), currTick);
								moneyRegister.chargeCreditCard(message.getCustomer(), futureAvailable.get());
								receipt.setPrice(futureAvailable.get());
								receipt.setIssuedTick(currTick);
								message.getCustomer().addOrder(receipt);
								moneyRegister.file(receipt);
					//			System.out.println(getName()+" printing recipt in time "+currTick);
								complete(message, receipt);
								message.getCustomer().getSem().release();
						//		System.out.println(getName()+" realsing "+message.getCustomer().getName()+" money semaphore");
						//			System.out.println("Completed purchase "+message.getBookName());
							} else {
								complete(message, null);
								message.getCustomer().getSem().release();
							//	System.out.println(getName()+" realsing "+message.getCustomer().getName()+" money semaphore");
							}
						} else {
							complete(message, null);
							message.getCustomer().getSem().release();
							//System.out.println(getName()+" realsing "+message.getCustomer().getName()+" money semaphore");
						}
					} else {
						complete(message, null);
						message.getCustomer().getSem().release();
					//	System.out.println(getName()+" realsing "+message.getCustomer().getName()+" money semaphore");
					}
				//	message.getCustomer().getSem().release();
				//	System.out.println(getName()+" realsing "+message.getCustomer().getName()+" money semaphore");
				} else {
					complete(message, null);
				}
			}else
				complete(message,null);


		});

		countDown.countDown();

	}

}
