
package jsu.edu.mcis;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Rates {
    
    public static final String RATE_FILENAME = "rates.csv";
    
    public static List<String[]> getRates(String path) {
        
        StringBuilder s = new StringBuilder();
        List<String[]> data = null;
        String line;
        
        try {
            
            /* OpenS Rates File; Attach BufferedReader */

            BufferedReader reader = new BufferedReader(new FileReader(path));
            
            /* Gets File Data */
            
            while((line = reader.readLine()) != null) {
                s.append(line).append('\n');
            }
            
            reader.close();
            
            /* Attach CSVReader; Parse File Data to List */
            
            CSVReader csvreader = new CSVReader(new StringReader(s.toString()));
            data = csvreader.readAll();
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return List */
        
        return data;
        
    }
    
    public static String getRatesAsTable(List<String[]> csv) {
        
        StringBuilder s = new StringBuilder();
        String[] row;
        
        try {
            
            /* Create Iterator */
            
            Iterator<String[]> iterator = csv.iterator();
            
            /* Create HTML Table */
            
            s.append("<table>");
            
            while (iterator.hasNext()) {
                
                /* Create Row */
            
                row = iterator.next();
                s.append("<tr>");
                
                for (int i = 0; i < row.length; ++i) {
                    s.append("<td>").append(row[i]).append("</td>");
                }
                
                /* Close Row */
                
                s.append("</tr>");
            
            }
            
            /* Close Table */
            
            s.append("</table>");
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        /* Return Table */
        
        return (s.toString());
        
    }
    
    public static String getRatesAsJson(List<String[]> csv) {
        
        String results = "";
        String[] row;
        
        try {
            
            /* Create Iterator */
            
            Iterator<String[]> iterator = csv.iterator();
            
            /* Create JSON Containers */
            
            JSONObject json = new JSONObject();
            JSONObject rates = new JSONObject();            
            
            /* 
             * Adds rate data to "rates" container and adds "date" and "base"
             * values to "json" container. 
             */
            
            String[] header = iterator.next();
            while( iterator.hasNext() ) {
                
                row = iterator.next();
                rates.put(row[1], Double.parseDouble(row[2]));
                
            }
            
            json.put("rates", rates);
            json.put("base", "USD");
            json.put("date", "2019-09-20");
            
            /* Parses top-level container to a JSON string */
            
            results = JSONValue.toJSONString(json);
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        return (results.trim());
        
    }

}