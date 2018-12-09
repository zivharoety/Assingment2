package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Future;

import java.util.concurrent.*;

/**
 * Passive object representing the resource manager.
 * You must not alter any of the given public methods of this class.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class ResourcesHolder {
	private BlockingQueue<DeliveryVehicle> availableVehicle;
	private Semaphore sema;
	private ConcurrentLinkedQueue<Future<DeliveryVehicle>> waitingQueue;

	private static class ResourceHolderClass {
		private static ResourcesHolder instance = new ResourcesHolder();
	}
	
	/**
     * Retrieves the single instance of this class.
     */
	public static ResourcesHolder getInstance() {
		return ResourceHolderClass.instance;
	}

	private ResourcesHolder(){
		availableVehicle = new LinkedBlockingQueue<>();
		waitingQueue = new ConcurrentLinkedQueue<>();
	}
	
	/**
     * Tries to acquire a vehicle and gives a future object which will
     * resolve to a vehicle.
     * <p>
     * @return 	{@link Future<DeliveryVehicle>} object which will resolve to a 
     * 			{@link DeliveryVehicle} when completed.   
     */
	public Future<DeliveryVehicle> acquireVehicle() {
		Future<DeliveryVehicle> toReturn = new Future();
		if(!sema.tryAcquire()){
			waitingQueue.add(toReturn);
		}
		else {
			toReturn.resolve(availableVehicle.poll());
		}
		return toReturn;
	}
	
	/**
     * Releases a specified vehicle, opening it again for the possibility of
     * acquisition.
     * <p>
     * @param vehicle	{@link DeliveryVehicle} to be released.
     */
	public void releaseVehicle(DeliveryVehicle vehicle) {
		if(waitingQueue.size()>0){
			waitingQueue.poll().resolve(vehicle);
		}
		else{
			sema.release();
		}
	}
	
	/**
     * Receives a collection of vehicles and stores them.
     * <p>
     * @param vehicles	Array of {@link DeliveryVehicle} instances to store.
     */
	public void load(DeliveryVehicle[] vehicles) {
		sema = new Semaphore(vehicles.length);
		for(int i = 0; i < vehicles.length;i++) {
			try {
				availableVehicle.put(vehicles[i]);
			} catch (InterruptedException igrnoerd) {
			}
		}
	}

}
