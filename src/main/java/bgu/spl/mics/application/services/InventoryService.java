package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.CheckAvailabilityEvent;
import bgu.spl.mics.application.messages.TakeBookEvent;
import bgu.spl.mics.application.messages.Tick;
import bgu.spl.mics.application.passiveObjects.*;
/**
 * InventoryService is in charge of the book inventory and stock.
 * Holds a reference to the {@link Inventory} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */

public class InventoryService extends MicroService{
		private Inventory inventory;
	public InventoryService(String name) {
		super(name);
		inventory = Inventory.getInstance();

		// TODO Implement this
	}

	@Override
	protected void initialize() {
		subscribeEvent(CheckAvailabilityEvent.class,(CheckAvailabilityEvent message)->{
			complete(message,inventory.checkAvailabiltyAndGetPrice(message.getBookName()));
		});
		subscribeEvent(TakeBookEvent.class,(TakeBookEvent message)->{
			complete(message,inventory.take(message.getBookName()));
		});
		subscribeBroadcast(Tick.class , (Tick message)->{
			if(message.getDuration()==message.getTick())
				terminate();
		});
		
	}

}
