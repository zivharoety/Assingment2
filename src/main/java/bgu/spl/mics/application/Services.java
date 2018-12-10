package bgu.spl.mics.application;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.services.*;

public class Services {
    public TimeService time ;
    public int selling;
    public int inventoryService;
    public int logistics;
    public int resourcesService;
    public Customer[] customers;
    public MicroService[] services;

    public void setCustomers() {
        for (int i = 0; i < customers.length; i++) {
            customers[i].setCustomer();
        }
    }
        public void startProgram(){


        for(int i = 0 ; i < selling ; i ++){
            SellingService toRun = new SellingService("selling number "+ i);
            toRun.run();
    }
    for(int i = 0 ; i < inventoryService ; i++){
        InventoryService toRun = new InventoryService("inventory number "+i);
        toRun.run();
    }
    for(int i = 0; i < logistics ; i++){
        LogisticsService toRun = new LogisticsService("logistic number "+i);
        toRun.run();
    }
    for(int i = 0 ; i < resourcesService ; i++){
        ResourceService toRun = new ResourceService("resource number "+i);
        toRun.run();
    }
            time.run();


    }





}
