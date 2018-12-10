package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.FetchVehicle;
import bgu.spl.mics.application.messages.ReleaseVehicle;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.messages.DeliveryEvent;

/**
 * Logistic service in charge of delivering books that have been purchased to customers.
 * Handles {@link DeliveryEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LogisticsService extends MicroService {
			private Future<DeliveryVehicle> future;
	public LogisticsService(String name) {
		super(name);
		// TODO Implement this
	}

	@Override
	protected void initialize() {
		subscribeEvent(DeliveryEvent.class, (DeliveryEvent message) -> {
			FetchVehicle toSend = new FetchVehicle(message.getCustomer().getDistance());
			Future<DeliveryVehicle> future = sendEvent(toSend);
			future.get().deliver(message.getCustomer().getAddress(),message.getCustomer().getDistance());
			ReleaseVehicle toRelease = new ReleaseVehicle(future.get());
			sendEvent(toRelease);
		});
		// TODO Implement this
		
	}

}
