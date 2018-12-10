package bgu.spl.mics.application;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import bgu.spl.mics.application.services.*;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
        Services start = null;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("/Users/guyofeck/Documents/spl/Assingment2.rar/Assingment2/json.txt"));
            JsonParser parser = gson.fromJson(reader, JsonParser.class);
            Inventory inventory = Inventory.getInstance();
            parser.setSema();
            inventory.load(parser.initialInventory);
            ResourcesHolder resourceHolder = ResourcesHolder.getInstance();
            resourceHolder.load(parser.initialResources[0].vehicles);
            parser.services.setCustomers();
            start = parser.services;
         /*   t = parser.services.time;
            sell = parser.services.selling;
            inventoryService = parser.services.inventoryService;
            logistics = parser.services.logistics;
            resourcesService = parser.services.resourcesService;
            customers = parser.services.customers;*/


        } catch (FileNotFoundException i){}
        System.out.println("hey");
    /*
        public void setUp(){

        for(int i = 0 ; i < sell ; i ++){
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


    }
    */





    }

}
