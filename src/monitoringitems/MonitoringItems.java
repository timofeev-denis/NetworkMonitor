package monitoringitems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
//import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MonitoringItems {
    static final Logger logger = LogManager.getLogger(MonitoringItems.class.getName());
    // LEVEL SRC DATE_START RESULT DURATION INFO
    static final String logFormat = "{}\t{}\t{}";
    public static void main(String[] args) {
        
        checkShare();
        //checkIIS();
        //checkHttp();
    }
    
    public static boolean checkShare() {
        boolean result = false;
        
        try {
            Date date = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss dd.mm.yyyy");
            PrintWriter writer = new PrintWriter(new File("g:\\gas_m\\paip\\CheckShare.txt"));
            writer.println( dt.format( date ) );
            writer.close();
            result = true;
            logger.info(logFormat, "SHARE", "date_start", "OK", dt.format( date ) );
        } catch (FileNotFoundException ex) {
            logger.error("SHARE " + ex.getMessage());
            result = false;
        }
        return result;
    }
    public static boolean checkIIS() {
        boolean result = false;
        String url = "http://spo-cikd/check.asp";
        String charset = "UTF-8";
        try {
            /*
            URLConnection connection = new URL(url).openConnection();
            connection.setRequestProperty("Accept-Charset", charset);
            InputStream response = connection.getInputStream();
            */
            
            InputStream connection = new URL(url).openStream();
            BufferedReader in = new BufferedReader( new InputStreamReader(connection));
            String line;
            while((line = in.readLine()) != null) {
                System.out.println( line );
            }
            in.close();
            result = true;
        } catch (Exception ex) {
            //Logger.getLogger(MonitoringItems.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    public static boolean checkHttp() {
        boolean result = false;
        int status = 0;
        try {

            String url = "http://spo-cikd/check.asp";

            URL obj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
            conn.setReadTimeout(1000);
//            conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
//            conn.addRequestProperty("User-Agent", "Mozilla");
//            conn.addRequestProperty("Referer", "google.com");

            System.out.println("Request URL ... " + url);

            boolean redirect = false;

            // normally, 3xx is redirect
            status = conn.getResponseCode();
            System.out.println("Response Code ... " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer html = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                html.append(inputLine);
            }
            in.close();

            System.out.println("URL Content... \n" + html.toString());
            System.out.println("Done");
            logger.info("HTTP - " + status);
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Error code: " + status);
            logger.warn("HTTP - " + status);
        }
        return result;
    }
}
