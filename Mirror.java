/*
 * This program gathers data from desired websites, formats them into HTTP and 
 * creates .txt files with the HTTP inside the document.
 * 
 * These files are to be read in by JS from the mirror website hosted on an
 * Apache webserver on the Raspberry Pi. 
 */

package mirror;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * @author Duncan McFarland
 */

public class Mirror {
    
    
    /**
     * This method visits South Paris' weather.gov website and writes the data
     * from the forecast into a txt document in HTTP format. 
    */
    public static void getWeather() {
        //Initialize strings to hold gathered data
        String curWordLabel = "";
        String curWordText = "";
        
        //Encapsulate everything in a try/catch to handle errors
        try {
            
            //Create a document that contains the HTTP contents of the website I want.
            Document weather = Jsoup.connect("https://forecast.weather.gov/MapClick.php?lat=44.22334200000006&lon=-70.51227849999998#.XGiH0rh7mUm").get();
            
            //Use CSS selectors to select the elements from the document I want.
            //These selectors use the CSS syntax.
            Elements weatherText = weather.select(".forecast-text");
            Elements weatherLabel = weather.select(".forecast-label");
            
            //Create the file I would like to create.
            File newFile = new File("weather.txt");
            
            //If it already exists, create a fresh copy.
            if(newFile.exists()) {
                newFile.delete();
                try {
                    newFile.createNewFile();
                } catch (IOException e) {
                    System.out.println("Error creating file.");
                }
            }
            
            //Create a filewriter that writes to the file created above.
            BufferedWriter out = new BufferedWriter(new FileWriter("weather.txt", true));
            
            //Begin writing to the file using HTTP syntax.
            out.write("<p>");
            out.newLine();
            
            //Assign the values of the elements selected above to string
            //variables one at a time. Elements are selected by their position
            //from first instance to last. Once elements are stored into
            //a variable, write that text to the file using HTTP formatting
            //and create a new line for the next round through instances of
            //that element.
            for (int i=0; i < 4; i++) {
                curWordLabel = weatherLabel.get(i).text();
                curWordText = weatherText.get(i).text();
                out.write("<span id = \"Label\">" + curWordLabel + "</span>" + ": " + "<span id = \"text\">" + curWordText + "</span>" + "<br><br>");
                out.newLine();
            }
            
            //Close the 'p' tag from above.
            out.write("</p>");
            
            //Close the buffered file writer.
            out.close();
            
        } catch (IOException e){
            System.out.println("Something went wrong when fetching the weather.");
        }        
    }
    
    
    public static void getOnion() {
    
    
}
    
    public static void main(String[] args) throws IOException {
        
        getWeather();
        
    }
    
}
