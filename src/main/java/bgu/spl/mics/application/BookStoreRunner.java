package bgu.spl.mics.application;

import java.io.*;
import bgu.spl.mics.application.passiveObjects.*;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;



/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
        String args0 = args[0];
        String args1 = args[1];
        String args2 = args[2];
        String args3 = args[3];
        String args4 = args[4];

        Gson gson = new Gson();
        JsonParser toPrint = null;
        try {
            JsonReader reader = new JsonReader(new FileReader(args0));
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

           try (FileOutputStream temp1 = new FileOutputStream(new File(args1))) {
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
            }
        }
    }



