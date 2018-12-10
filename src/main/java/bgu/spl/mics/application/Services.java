package bgu.spl.mics.application;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.Pair;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.services.*;

import java.util.LinkedList;

public class Services {
    public TimeService time ;
    public int selling;
    public int inventoryService;
    public int logistics;
    public int resourcesService;
    public Customer[] customers;

    public void setCustomers() {
        for (int i = 0; i < customers.length; i++) {
            customers[i].setCustomer();
        }
    }

    public void startProgram(){
        startSelling();
        startApi();

    }

    public void startTask(Runnable run){
        Thread r = new Thread(run);
        r.start();
    }

    public void startSelling(){
        for(int i = 0 ; i < selling ; i ++) {
            Runnable run = new SellingService("selling number "+i);
            startTask(run);
            startInvetoryService();
            startLogistics();
            startResourceService();
            startTime();
        }
    }
    public  void startApi(){
        for(int i = 0 ; i <customers.length;i++){
            LinkedList<Pair> list = null; //customer[i].sortSchdeule();
            Runnable run = new APIService("API number "+i,customers[i],list);
            startTask(run);
        }
    }
    public void startInvetoryService(){
        for(int i=0; i<inventoryService ; i++){
            Runnable run = new InventoryService("inventory number "+i);
            startTask(run);
        }
    }
    public void startLogistics(){
        for(int i=0 ; i < logistics ; i++){
            Runnable run = new LogisticsService("logistics number "+i);
            startTask(run);
        }
    }
    public void startResourceService(){
        for (int i = 0 ; i<resourcesService ;i++){
            Runnable run = new ResourceService("resource number "+i);
            startTask(run);
        }
    }
    public void startTime(){
        Runnable run = time;
        startTask(run);
    }





}
