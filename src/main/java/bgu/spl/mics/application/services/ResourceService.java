package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.FetchVehicle;
import bgu.spl.mics.application.messages.ReleaseVehicle;
import bgu.spl.mics.application.messages.Tick;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import bgu.spl.mics.application.passiveObjects.*;

/**
 * ResourceService is in charge of the store resources - the delivery vehicles.
 * Holds a reference to the {@link ResourcesHolder} singleton of the store.
 * This class may not hold references for objects which it is not responsible for:
 * {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ResourceService extends MicroService{
		private	ResourcesHolder resource;
	public ResourceService(String name) {
		super(name);
		resource = ResourcesHolder.getInstance();
	}

	@Override
	protected void initialize() {
		subscribeEvent(FetchVehicle.class, (FetchVehicle message)->{
			complete(message,resource.acquireVehicle().get());
		});
		subscribeEvent(ReleaseVehicle.class, (ReleaseVehicle message)->{
			resource.releaseVehicle(message.getVehicle());
		});
		subscribeBroadcast(Tick.class , (Tick message)->{
			if(message.getDuration()==message.getTick())
				terminate();
		});
		
	}

}
