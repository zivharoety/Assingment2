package bgu.spl.mics.application;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.Pair;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;
import bgu.spl.mics.application.services.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class Services implements Serializable {
    public LinkedList<Thread> threadList;
    public Thread timeServiceThread;
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
        threadList = new LinkedList<>();
        startSelling();
        startApi();
        startInvetoryService();
        startLogistics();
        startResourceService();
        startTime();

    }

    public void startTask(Runnable run,String name){
        Thread r = new Thread(run);
        threadList.add(r);
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
            Runnable run = new APIService("API number "+i,customers[i],countDown);
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
        /*timeServiceThread = new Thread(run);
        timeServiceThread.start();*/
        startTask(run,((TimeService) run).getName());
    }


    public static String customers2string(Customer[] customers) {
        String str = "";
        for (Customer customer : customers)
            str += customer2string(customer) + "\n---------------------------\n";
        return str;
    }

    public static String customer2string(Customer customer) {
        String str = "id    : " + customer.getId() + "\n";
        str += "name  : " + customer.getName() + "\n";
        str += "addr  : " + customer.getAddress() + "\n";
        str += "dist  : " + customer.getDistance() + "\n";
        str += "card  : " + customer.getCreditNumber() + "\n";
        str += "money : " + customer.getAvailableCreditAmount();
        return str;
    }

    public static String books2string(BookInventoryInfo[] books) {
        String str = "";
        for (BookInventoryInfo book : books)
            str += book2string(book) + "\n---------------------------\n";
        return str;
    }

    public static String book2string(BookInventoryInfo book) {
        String str = "";
        str += "title  : " + book.getBookTitle() + "\n";
        str += "amount : " + book.getAmountInInventory() + "\n";
        str += "price  : " + book.getPrice();
        return str;
    }


    public static String receipts2string(OrderReceipt[] receipts) {
        String str = "";
        for (OrderReceipt receipt : receipts)
            str += receipt2string(receipt) + "\n---------------------------\n";
        return str;
    }
    public static String receipt2string(OrderReceipt receipt) {
        String str = "";
        str += "customer   : " + receipt.getCustomerId() + "\n";
        str += "order tick : " + receipt.getOrderTick() + "\n";
        str += "id         : " + receipt.getOrderId() + "\n";
        str += "price      : " + receipt.getPrice() + "\n";
        str += "seller     : " + receipt.getSeller();
        return str;
    }

    public static void Print(String str, String filename) {
        try {
            try (PrintStream out = new PrintStream(new FileOutputStream(filename))) {
                out.print(str);
            }
        } catch (IOException e) {
            System.out.println("Exception: " + e.getClass().getSimpleName());
        }
    }







}
