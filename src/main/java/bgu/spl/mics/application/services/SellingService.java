package bgu.spl.mics.application.services;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.messages.CheckAvailabilityEvent;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.passiveObjects.*;

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

	public SellingService(String name) {
		super(name);
	}

	@Override
	protected void initialize() {
		subscribeEvent(BookOrderEvent.class , (BookOrderEvent message)->{
			CheckAvailabilityEvent toCheck = new CheckAvailabilityEvent(message.getBookName());
			TakeBookEvent toTake = new TakeBookEvent(message.getBookName());
			OrderReceipt receipt = new OrderReceipt();
			futureAvailable = sendEvent(toCheck);
			if(futureAvailable.get() != -1){
				synchronized (message.getCustomer()){
					if(message.getCustomer().getAvailableCreditAmount() <= futureAvailable.get()){
						futureIsTaken = sendEvent(toTake);
						if(futureIsTaken.get() == OrderResult.SUCCESSFULLY_TAKEN){
							moneyRegister.chargeCreditCard(message.getCustomer() , futureAvailable.get());
							moneyRegister.file(receipt);
							complete(message,receipt);
						}
					}
				}
			}
			complete(message , null);

		});

	}

	public void setMoneyRegister(MoneyRegister moneyRegister) {
		this.moneyRegister = moneyRegister;
	}
}
