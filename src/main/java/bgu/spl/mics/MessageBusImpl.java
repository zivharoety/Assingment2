package bgu.spl.mics;
import java.util.*;
import java.util.concurrent.*;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
    private ConcurrentHashMap<Event, Future> futureMap;
    private ConcurrentHashMap<Class<? extends Event>, LinkedList> eventTypeQueue;//to implement RoundedQueue!!!
    private ConcurrentHashMap<MicroService, BlockingDeque<Message>> microQueue;
    private ConcurrentHashMap<Class<? extends Broadcast>, LinkedList<MicroService>> broadcastTypeList;


    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
        //adding m to MicroMap & providing the lambada and calling the function m.subscribeEvent.
        synchronized (eventTypeQueue) {
            if (!eventTypeQueue.contains(type))
                eventTypeQueue.put(type, new LinkedList());
        }
         synchronized (eventTypeQueue.get(type)){
        eventTypeQueue.get(type).addLast(m);
    }

}



	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
        if(!broadcastTypeList.contains(type)) {
            synchronized (broadcastTypeList) {
                broadcastTypeList.put(type, new LinkedList());
            }
        }
        broadcastTypeList.get(type).addFirst(m);

    }


	@Override
	public <T> void complete(Event<T> e, T result) {
		futureMap.get(e).resolve(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		synchronized ((broadcastTypeList.get(b.getClass()))){
		    for(MicroService m : broadcastTypeList.get(b.getClass())){
		      try {
                  microQueue.get(m).putFirst(b);
              }
              catch(InterruptedException ignored){
		          //// to implement
                  }
            }
        }

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> toReturn = new Future<>();
		futureMap.put(e,toReturn); // mapping the future to the event map
		synchronized (eventTypeQueue.get(e.getClass())) {
            MicroService temp;
            temp = (MicroService) eventTypeQueue.get(e.getClass()).removeFirst();
            try {
                microQueue.get(temp).put(e);
            }
            catch (InterruptedException ignored){
                /// to understand what we need to do
            }

            eventTypeQueue.get(e.getClass()).addLast(temp);

        }
		return toReturn;
	}

	@Override
	public void register(MicroService m) {
        BlockingDeque<Message> toAdd = new LinkedBlockingDeque<>();
        microQueue.put(m,toAdd);


	    // we think it's done

	}

	@Override
	public void unregister(MicroService m) {
		// searching for each MessageClass that the micro service is subscribed to and remove it. need to add field that saves the subscribed Event\ brodcast

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		return microQueue.get(m).take() ;

	}

	

}
