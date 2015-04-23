package monitoringitems;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MonitoringItems {

    public static void main(String[] args) {
        //checkShare();
        checkIIS();
    }
    
    public static boolean checkShare() {
        boolean result = false;
        
        try {
            SimpleDateFormat dt = new SimpleDateFormat("HH:mm:ss dd.mm.yyyy");
            PrintWriter writer = new PrintWriter(new File("g:\\gas_m\\paip\\CheckShare.txt"));
            writer.println( dt.format( new Date() ) );
            writer.close();
            result = true;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MonitoringItems.class.getName()).log(Level.SEVERE, null, ex);
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
}
