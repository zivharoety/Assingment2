package bgu.spl.mics.application;

import java.io.*;
import java.util.*;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;


/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {

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
            }}catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try (FileOutputStream temp1 = new FileOutputStream(new File(args[1]))) {
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

            Inventory.getInstance().printInventoryToFile(args[2]);
            MoneyRegister.getInstance().printOrderReceipts(args[3]);

            try (FileOutputStream temp2 = new FileOutputStream(new File(args[4]))) {
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

