package bgu.spl.mics.application;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

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
        Gson gson= new Gson();

       try {
            JsonReader reader = new JsonReader(new FileReader("/users/studs/bsc/2019/haroetyz/IdeaProjects/Assingment2/json.txt"));
            JsonParser parser = gson.fromJson(reader,JsonParser.class);
            Inventory inventory = Inventory.getInstance();
            parser.setSema();
            inventory.load(parser.initialInventory);
            ResourcesHolder resourceHolder = ResourcesHolder.getInstance();
            resourceHolder.load(parser.initialResources[0].vehicles);
            parser.services.setCustomers();
            parser.services.startProgram();



        } catch (FileNotFoundException i){}


    }

}
