package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.FetchVehicle;
import bgu.spl.mics.application.messages.ReleaseVehicle;
import bgu.spl.mics.application.messages.Tick;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import bgu.spl.mics.application.passiveObjects.*;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

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
		private CountDownLatch countDown;
		private static AtomicInteger resourceCounter = new AtomicInteger(0);
		private static ConcurrentLinkedQueue<Future<DeliveryVehicle>> waitingFuture = new ConcurrentLinkedQueue<>();

	public ResourceService(String name, CountDownLatch countD) {
		super(name);
		countDown = countD;
		resource = ResourcesHolder.getInstance();
		resourceCounter.getAndIncrement();
	}

	@Override
	protected void initialize() {
		subscribeEvent(FetchVehicle.class, (FetchVehicle message)->{
			Future<DeliveryVehicle> toReturn = resource.acquireVehicle();
			if(!toReturn.isDone()){
				waitingFuture.add(toReturn);
			}
			complete(message,toReturn);
		});
		subscribeEvent(ReleaseVehicle.class, (ReleaseVehicle message)->{
			resource.releaseVehicle(message.getVehicle());
		});
		subscribeBroadcast(Tick.class , (Tick message)->{
			if(message.getDuration()==message.getTick()){
				if(resourceCounter.get() == 1){
					while(!waitingFuture.isEmpty()){
						Future<DeliveryVehicle> temp = waitingFuture.poll();
						if(!temp.isDone())
							temp.resolve(null);
					}

				}
				resourceCounter.decrementAndGet();
				terminate();
			}

		});
		countDown.countDown();
		
	}

}
