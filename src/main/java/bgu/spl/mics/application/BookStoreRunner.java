package bgu.spl.mics.application;

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import bgu.spl.mics.application.passiveObjects.BookInventoryInfo;
/*import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;*/
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


/** This is the Main class of the application. You should parse the input file, 
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class BookStoreRunner {
    public static void main(String[] args) {
     //   JSONParser parser = new JSONParser();
        BookInventoryInfo[] inventoryInfo ;
        Gson gson= new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader("C:\\Users\\Ziv Haroety\\IdeaProjects\\Part2\\json.txt"));
            JsonParser input = gson.fromJson(reader,JsonParser.class);
            Inventory inventory = Inventory.getInstance();
            inventory.load(input.initialInventory);
       //     inventory.load(input.inventoryInfo);
            ResourcesHolder resourceHolder = ResourcesHolder.getInstance();
//            resourceHolder.load(input.vehicles);

        }
        catch(FileNotFoundException i){}

        /*try{
            Object obj = parser.parse(new FileReader("C:\\Users\\Ziv Haroety\\IdeaProjects\\Part2\\json.txt"));
            JSONObject jasonObject = (JSONObject) obj;
            JSONArray inventory = (JSONArray) jasonObject.get("initialInventory");
            inventoryInfo = new BookInventoryInfo[inventory.size()];
            Iterator iter = inventory.iterator();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/




    }
}
