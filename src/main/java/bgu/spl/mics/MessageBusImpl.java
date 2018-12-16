package bgu.spl.mics;
import bgu.spl.mics.application.messages.BookOrderEvent;
import bgu.spl.mics.application.passiveObjects.Customer;

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
    private ConcurrentHashMap<MicroService, BlockingQueue<Message>> microQueue;
    private ConcurrentHashMap<Class<? extends Broadcast>, LinkedList<MicroService>> broadcastTypeList;
    //private ConcurrentHashMap<Class<? extends Broadcast>, ConcurrentLinkedQueue<MicroService>> broadcastTypeList;
    private ConcurrentHashMap<MicroService, LinkedList<Class<? extends Event<?>>>> microRegisterEvent;
    private ConcurrentHashMap<MicroService, LinkedList<Class<? extends Broadcast>>> microRegisterBroad;
    //private static MessageBusImpl instance = new MessageBusImpl();


    private MessageBusImpl() {
        futureMap = new ConcurrentHashMap<>();
        eventTypeQueue = new ConcurrentHashMap<>();
        microQueue = new ConcurrentHashMap<>();
        broadcastTypeList = new ConcurrentHashMap<>();
        microRegisterEvent = new ConcurrentHashMap<>();
        microRegisterBroad = new ConcurrentHashMap<>();

    }

    private static class MessageBusHolder {
        private static MessageBusImpl instance = new MessageBusImpl();
    }

    public static MessageBusImpl getInstance() {
        return MessageBusHolder.instance;
    }


    @Override
    public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
        //adding m to MicroMap & providing the lambada and calling the function m.subscribeEvent.
        synchronized (eventTypeQueue) {
            if (eventTypeQueue.get(type) == null) {
                eventTypeQueue.put(type, new LinkedList());
            }
        }
        synchronized (eventTypeQueue.get(type)) {
            eventTypeQueue.get(type).addLast(m);
        }
        microRegisterEvent.get(m).add(type);

    }


    @Override
    public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
        //  System.out.println(m.getName()+" is subscrobong to "+type);
        synchronized (broadcastTypeList) {
            if (broadcastTypeList.get(type) == null) {
                broadcastTypeList.put(type, new LinkedList());
                //     broadcastTypeList.put(type, new ConcurrentLinkedQueue<>());
            }
        }
        synchronized (broadcastTypeList.get(type)) {
            broadcastTypeList.get(type).add(m);
        }
        microRegisterBroad.get(m).add(type);
    }


    @Override
    public <T> void complete(Event<T> e, T result) {
        futureMap.get(e).resolve(result);
        //futureMap.remove(e);

    }

    @Override
    public void sendBroadcast(Broadcast b) {
      //  synchronized (broadcastTypeList.get(b.getClass())) {
          final List<MicroService> taskList = new ArrayList<MicroService>(broadcastTypeList.get(b.getClass()));
            synchronized (taskList) {
                for (MicroService m : taskList) {
                    microQueue.get(m).add(b);
                }
            }
    //    }

        /*   synchronized ((broadcastTypeList.get(b.getClass()))) {
            if (broadcastTypeList.get(b.getClass()) == null) {
                System.out.println("return because null");
                return;
            }
            for (MicroService m : broadcastTypeList.get(b.getClass())) {

                //System.out.println(broadcastTypeList.get(b.getClass()).size());
                for (int i = broadcastTypeList.get(b.getClass()).size() - 1; i > -1; i--) {   //MicroService m : broadcastTypeList.get(b.getClass())) {
                    if (broadcastTypeList.get(b.getClass()) == null || broadcastTypeList.get(b.getClass()).size() == 0) {
                        System.out.println("class is null");
                    //    return;
                    }
                    if (broadcastTypeList.get(b.getClass()).get(i) == null) {
                        System.out.println(m.getName()+ " Micro is null");
                      //  return;
                    }
                    if (microQueue.get(broadcastTypeList.get(b.getClass()).get(i)) == null) {
                        System.out.println("Micro Queue is null");
                        //return;
                    }
                    try {
                        microQueue.get(broadcastTypeList.get(b.getClass()).get(i)).put(b);
                    } catch (InterruptedException ignored) {
                        ///
                    }
                }
            }

        }*/
}


    @Override
    public <T> Future<T> sendEvent(Event<T> e) {
        Future<T> toReturn = new Future<>();
        futureMap.put(e, toReturn);
        synchronized (eventTypeQueue.get(e.getClass())) {
            if (eventTypeQueue.get(e.getClass()) == null || eventTypeQueue.get(e.getClass()).isEmpty()) {
                complete(e, null);
            } else {
                MicroService temp = (MicroService) eventTypeQueue.get(e.getClass()).removeFirst();
                if (microQueue.get(temp) == null)
                    complete(e, null);
                else {
                    synchronized (microQueue.get(temp)) {
                        try {
                            microQueue.get(temp).put(e);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                        eventTypeQueue.get(e.getClass()).addLast(temp);
                    }

                }
            }
            return toReturn;

        }
    }

        /* Future<T> toReturn = new Future<>();
        futureMap.put(e, toReturn);// mapping the future to the event map
        MicroService temp = null;
        try {
            synchronized (eventTypeQueue.get(e.getClass())) {
                if(eventTypeQueue.get(e.getClass()) == null || eventTypeQueue.get(e.getClass()).isEmpty()){
                    complete(e,null);
                }
                else {
                        temp = (MicroService) eventTypeQueue.get(e.getClass()).removeFirst();
                }
            }
                try {
                    microQueue.get(temp).put(e);
                } catch (InterruptedException ignored) {
                    /// to understand what we need to do
                }
            //    catch (NullPointerException ignored){}

            }
         catch (NullPointerException exc) {
            return null;
        }
    synchronized ((eventTypeQueue.get(e.getClass()))) {
        eventTypeQueue.get(e.getClass()).addLast(temp);
    }
        return toReturn; */


    @Override
    public void register(MicroService m) {
        BlockingDeque<Message> toAdd = new LinkedBlockingDeque<>();
        LinkedList<Class<? extends Event<?>>> mySubEvent = new LinkedList<>();
        LinkedList<Class<? extends Broadcast>> mySubBroad = new LinkedList<>();
        microQueue.put(m, toAdd);
        microRegisterEvent.put(m, mySubEvent);
        microRegisterBroad.put(m,mySubBroad);


        // we think it's done

    }

    @Override
    public void unregister(MicroService m) {
        for(int i=0 ; i < microRegisterEvent.get(m).size(); i ++){
            eventTypeQueue.get( microRegisterEvent.get(m).get(i)).remove(m);
        }
        for(int i=0 ; i < microRegisterBroad.get(m).size(); i ++){
            broadcastTypeList.get( microRegisterBroad.get(m).get(i)).remove(m);
        }
        LinkedList<Event<?>> toResolve = new LinkedList<>();
        synchronized (microQueue.get(m)) {
            for (Message mes : microQueue.get(m)) {
                if (mes instanceof Event<?>) {
                    toResolve.add((Event<?>) mes);
                }
            }
            for (Event<?> res : toResolve) {
                complete(res, null);
            }
        }

    }

    @Override
    public Message awaitMessage(MicroService m) throws InterruptedException {
        Message toReturn = null;
        if(microQueue.get(m)== null) {
            register(m);
        }
            try {
                toReturn = microQueue.get(m).take();
            }  catch (InterruptedException inter) {
                //wait();
                inter.printStackTrace();
            }

        return toReturn;

    }


}
