
package jsu.edu.mcis;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.HttpURLConnection;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Latest extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
           
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
           
        }
        
        catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
