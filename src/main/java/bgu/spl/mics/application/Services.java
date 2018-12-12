package bgu.spl.mics.application;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.Pair;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.services.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class Services {
    public TimeInput time ;
    public int selling;
    public int inventoryService;
    public int logistics;
    public int resourcesService;
    public Customer[] customers;
    private CountDownLatch countDown;
    public HashMap<Integer, Customer> customerMap;


    public void setCustomers() {
        for (int i = 0; i < customers.length; i++) {
            customers[i].setCustomer();
        }
    }

    public void startProgram(){
        countDown = new CountDownLatch(selling + customers.length+resourcesService+logistics+inventoryService);
        startSelling();
        startApi();
        startInvetoryService();
        startLogistics();
        startResourceService();
        startTime();

    }

    public void startTask(Runnable run,String name){
        Thread r = new Thread(run);
        r.start();
        System.out.println(name+ " started running");
    }

    public void startSelling(){
        for(int i = 0 ; i < selling ; i ++) {
            Runnable run = new SellingService("selling number " + i,countDown);
            startTask(run,((SellingService) run).getName());
        }


    }
    public  void startApi(){
        customerMap = new HashMap<>();
        for(int i = 0 ; i <customers.length;i++){
            LinkedList<Pair> list = null; //customer[i].sortSchdeule();
            Runnable run = new APIService("API number "+i,customers[i],list,countDown);
            startTask(run,((APIService) run).getName());
            customerMap.put(customers[i].getId(),customers[i]);
        }
    }
    public void startInvetoryService(){
        for(int i=0; i<inventoryService ; i++){
            Runnable run = new InventoryService("inventory number "+i,countDown);
            startTask(run,((InventoryService) run).getName());
        }
    }
    public void startLogistics(){
        for(int i=0 ; i < logistics ; i++){
            Runnable run = new LogisticsService("logistics number "+i,countDown);
            startTask(run,((LogisticsService) run).getName());
        }
    }
    public void startResourceService(){
        for (int i = 0 ; i<resourcesService ;i++){
            Runnable run = new ResourceService("resource number "+i,countDown);
            startTask(run,((ResourceService) run).getName());
        }
    }
    public void startTime(){

        Runnable run = new TimeService(time.getSpeed(),time.getDuration(),countDown);
        try {
            countDown.await();
        }
        catch(InterruptedException ignored){}

        startTask(run,((TimeService) run).getName());
    }





}
