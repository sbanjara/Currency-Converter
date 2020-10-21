
package jsu.edu.mcis;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Rates {
    
    public static final String RATE_FILENAME = "rates.csv";
    
    public static List<String[]> getRates(String path) {
        
        StringBuilder s = new StringBuilder();
        List<String[]> data = null;
        String line;
        
        try {
            
            /* Open Rates File; Attach BufferedReader */

            BufferedReader reader = new BufferedReader(new FileReader(path));
            
            /* Get File Data */
            
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
             * Adds rate data to "rates" container and add "date" and "base"
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
            
            /* Parse top-level container to a JSON string */
            
            results = JSONValue.toJSONString(json);
            
        }
        catch (Exception e) { System.err.println( e.toString() ); }
        
        return (results.trim());
        
    }
    
    /*
     * Gets the exchange rates from a database and returns a json string
     */
    
    public static String getRatesAsJson(String code) throws NamingException, SQLException {
        
        String results = "";
        String databaseDate = "";
        Context envContext = null, initContext = null;
        DataSource ds = null;
        Connection connection = null;
        
        long timestamp = System.currentTimeMillis();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(timestamp);
        String date = (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
        
        PreparedStatement pstatement = null;
        ResultSet resultset = null;
        
        String query;
        boolean hasresults;
        
        JSONObject json = new JSONObject();
        JSONObject rates = new JSONObject();
        
        try {
            
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            connection = ds.getConnection();
            
            if (code != null)        
                query = "SELECT * FROM rates WHERE code = ? AND date = ?";
            
            else
                query = "SELECT * FROM rates WHERE date = ?";
            
            pstatement = connection.prepareStatement(query);
            
            if (code != null) {
                pstatement.setString(1, code);
                pstatement.setString(2, date);
            }
            else {
                pstatement.setString(1, date);
            }
            
            hasresults = pstatement.execute();
                
            if ( hasresults ) {
                
                resultset = pstatement.getResultSet();
                
                while (resultset.next()) {
                    
                    String c = resultset.getString("code");
                    double r = resultset.getDouble("rate");
                    databaseDate = resultset.getString("date");
                    rates.put(c, r);
                    
                }
                
            }          
            
            json.put("rates", rates);
            json.put("date", databaseDate);
            json.put("base", "USD");   
                
            results = JSONValue.toJSONString(json);
        
        }
        
        catch (Exception e) {
            System.out.println(e.toString());
        }
        
        finally {
            
            if (resultset != null) { try { resultset.close(); resultset = null; } catch (Exception e) {} }
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (connection != null) { connection.close(); }
            
        }
        
        if ( rates.isEmpty() && databaseDate.isEmpty() )
            return null;
        else 
            return (results.trim());
        
    }
    
    public static void putRatesInDatabase(String jsonString) throws ParseException, SQLException {
        
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
        HashMap<String, Double> rates = (HashMap<String, Double>) jsonObject.get("rates");
        String date = (String) jsonObject.get("date");
        System.out.println(date);
        Set keys = rates.keySet();
        String[] code = new String[keys.size()];
        Double[] rate = new Double[keys.size()];
        
        int count = 0;
        for(Object key: keys){
            String s = (String) key;
            code[count] = s;
            rate[count] = (Double) rates.get(s); 
            count += 1;
        } 
        
        Context envContext = null, initContext = null;
        DataSource ds = null;
        Connection connection = null;
        PreparedStatement pstatement = null;
        String query = null;
        
        try {
            
            envContext = new InitialContext();
            initContext  = (Context)envContext.lookup("java:/comp/env");
            ds = (DataSource)initContext.lookup("jdbc/db_pool");
            connection = ds.getConnection();
            
            query = "INSERT INTO rates(code, rate, date) VALUES(?, ?, ?)";
            pstatement = connection.prepareStatement(query);
            
            for( int i = 0; i < code.length; ++i ) {
                
                pstatement.setString(1, code[i]);
                pstatement.setDouble(2, rate[i]);
                pstatement.setString(3, date);
                pstatement.addBatch();
                
            } 
            
            int[] r = pstatement.executeBatch();
      
        } 
        
        catch (SQLException e) { e.printStackTrace(); } 
        
        catch (NamingException ex) {
            Logger.getLogger(Rates.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        finally {
            
            if (pstatement != null) { try { pstatement.close(); pstatement = null; } catch (Exception e) {} }
            
            if (connection != null) { connection.close(); }
            
        }
        
    }

}