
package jsu.edu.mcis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.parser.ParseException;

public class Latest extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException, SQLException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
           
            String s = Rates.getRatesAsJson( request.getParameter("code"));
            
            if (s == null) {
                
                String line;
                BufferedReader reader;
                StringBuffer responseContent;
                responseContent = new StringBuffer();
                HttpURLConnection connection = null;
                String exchangeratesurl = "https://api.exchangeratesapi.io/latest?base=USD";

                URL url = new URL(exchangeratesurl);
                connection = (HttpURLConnection) url.openConnection();

                //Request set up
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);

                int status = connection.getResponseCode();

                if(status > 299) {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    while( (line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();
                }

                else {

                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while( (line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    reader.close();

                }

                out.println(responseContent.toString());
             
                try {
                    Rates.putRatesInDatabase(responseContent.toString());
                } 
                catch (ParseException ex) {
                    Logger.getLogger(Latest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            else {
                out.println(s);
            }
         
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(Latest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Latest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(Latest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Latest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
