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
	private int currTick;

	public SellingService(String name) {
		super(name);
		bus = MessageBusImpl.getInstance();
		currTick = 0;
		moneyRegister = MoneyRegister.getInstance();
	}

	@Override
	protected void initialize() {
		subscribeEvent(BookOrderEvent.class , (BookOrderEvent message)->{
			CheckAvailabilityEvent toCheck = new CheckAvailabilityEvent(message.getBookName());
			futureAvailable = sendEvent(toCheck);
			TakeBookEvent toTake = new TakeBookEvent(message.getBookName());
			OrderReceipt receipt = new OrderReceipt(message.getOrderId(),this.getName(),message.getCustomer().getId(),message.getBookName(),
					message.getOrderTick(),currTick);
			boolean gotKey = false;
			if(futureAvailable.get() != -1){
				while(!gotKey) {
					try {
						message.getCustomer().getSem().acquire();
						gotKey = true;
					} catch (InterruptedException igrnored) {
					}
				}
				if (message.getCustomer().getSem().tryAcquire()){
					if(message.getCustomer().getAvailableCreditAmount() <= futureAvailable.get()){
						futureIsTaken = sendEvent(toTake);
						if(futureIsTaken.get() == OrderResult.SUCCESSFULLY_TAKEN){
							moneyRegister.chargeCreditCard(message.getCustomer() , futureAvailable.get());
							receipt.setPrice(futureAvailable.get());
							receipt.setIssuedTick(currTick);
							moneyRegister.file(receipt);
							complete(message,receipt);
						}
					}
					message.getCustomer().getSem().release();
				}
			}
			complete(message , null);

		});
		subscribeBroadcast(Tick.class,(Tick message)->{
			currTick = message.getTick();
		});

	}

}
