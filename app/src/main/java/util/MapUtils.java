package util;

import android.location.Location;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class MapUtils {

    private static final String API_KEY = "AIzaSyBsY-26loYcr2kpIARp5wTmbExsf-BWC7M";
    private String TAG = "PERSONALLOG";
    private static final String USER_AGENT = "Mozilla/5.0";

    public static Float getDistanceBetween() {
        Location l1 = new Location("My house");
        Location l2 = new Location("QTSC");
        l1.setLatitude(10.7989959);
        l1.setLongitude(106.612418);
        l2.setLatitude(10.8538493);
        l2.setLongitude(106.6261721);
        return l1.distanceTo(l2);
    }

    public static String getDistance() throws IOException {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=10.7994789,106.6114475&destinations=10.8538493,106.6261721";
        return sendRequestGoogleAPI(url);
    }

    private static String sendRequestGoogleAPI(String url) throws MalformedURLException, IOException {

        String key = "&key=" + API_KEY;
        url += key;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        Log.d("PERSONALLOG", "sendRequestGoogleAPI: " + con);

        int responseCode = con.getResponseCode();

        Log.d("PERSONALLOG", "Sending 'GET' request to URL : " + url);
        Log.d("PERSONALLOG", "Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), Charset.forName("UTF-8")));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
