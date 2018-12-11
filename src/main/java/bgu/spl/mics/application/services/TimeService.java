package bgu.spl.mics.application.services;

import bgu.spl.mics.MessageBus;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.messages.Tick;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private int duration;
	private int speed;

	public TimeService(int speed, int duration) {
		super("time");
		super.bus = MessageBusImpl.getInstance();
		this.duration = duration;
		this.speed = speed;

	}

	@Override
	protected void initialize() {
		for(int i=1 ; i <= duration ; i++){
			sendBroadcast(new Tick(i,duration));
			try {
				Thread.sleep(speed);
			}
			catch(InterruptedException ignored){}
		}
		terminate();
		
	}
}
