package bgu.spl.mics.application;

import java.io.*;
import java.util.*;

import bgu.spl.mics.application.passiveObjects.*;
/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static bgu.spl.mics.application.Services.Print;


/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
        String args0 = args[0];
 /*       String args1 = args[1];
        String args2 = args[2];
        String args3 = args[3];
        String args4 = args[4]; */

        Gson gson = new Gson();
        JsonParser toPrint = null;
        try {
            JsonReader reader = new JsonReader(new FileReader(args[0]));
            JsonParser parser = gson.fromJson(reader, JsonParser.class);
            Inventory inventory = Inventory.getInstance();
            parser.setSema();
            inventory.load(parser.initialInventory);
            ResourcesHolder resourceHolder = ResourcesHolder.getInstance();
            resourceHolder.load(parser.initialResources[0].vehicles);
            parser.services.setCustomers();
            parser.services.startProgram();
            toPrint = parser;
            for(Thread t :parser.services.threadList) {
               try {
                    t.join();
                } catch (InterruptedException ignored) {

                }
            }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }

      /*      try (FileOutputStream temp1 = new FileOutputStream(new File(args1))) {
                try (ObjectOutputStream customers = new ObjectOutputStream(temp1)) {
                    customers.writeObject(toPrint.services.customerMap);

                } catch (FileNotFoundException e) {
                } catch (IOException e) {
                }
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Inventory.getInstance().printInventoryToFile(args2);
            MoneyRegister.getInstance().printOrderReceipts(args3);


            try (FileOutputStream temp2 = new FileOutputStream(new File(args4))) {
                try (ObjectOutputStream moneyRegisterPrinter = new ObjectOutputStream(temp2)) {
                    moneyRegisterPrinter.writeObject(MoneyRegister.getInstance());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/

        System.out.println(toPrint.services.books2string(Inventory.getInstance().getBooks()));
        System.out.println(toPrint.services.receipts2string(MoneyRegister.getInstance().getRecipts()));
        System.out.println(toPrint.services.customers2string(toPrint.services.customers));
        System.out.println("Total earning "+MoneyRegister.getInstance().getTotalEarnings());
/*
        int numOfTest = Integer.parseInt(args[0].replace(new File(args[0]).getParent(), "").replace("/", "").replace(".json", ""));
        String dir = new File(args[1]).getParent() + "/" + numOfTest + " - ";
        Customer[] customers1 = customers2Print.values().toArray(new Customer[0]);
        Arrays.sort(customers1, Comparator.comparing(Customer::getName));
        String str_custs = Arrays.toString(customers1);
        str_custs = str_custs.replaceAll(", ", "\n---------------------------\n").replace("[", "").replace("]", "");
        Print(str_custs, dir + "Customers");

        String str_books = Arrays.toString(books.toArray());
        str_books = str_books.replaceAll(", ", "\n---------------------------\n").replace("[", "").replace("]", "");
        Print(str_books, dir + "Books");

        List<OrderReceipt> receipts_lst = MoneyRegister.getInstance().getOrderReceipts();
        receipts_lst.sort(Comparator.comparing(OrderReceipt::getOrderId));
        receipts_lst.sort(Comparator.comparing(OrderReceipt::getOrderTick));
        OrderReceipt[] receipts = receipts_lst.toArray(new OrderReceipt[0]);
        String str_receipts = Arrays.toString(receipts);
        str_receipts = str_receipts.replaceAll(", ", "\n---------------------------\n").replace("[", "").replace("]", "");
        Print(str_receipts, dir + "Receipts");

        Print(MoneyRegister.getInstance().getTotalEarnings() + "", dir + "Total");

*/

        }
    }



