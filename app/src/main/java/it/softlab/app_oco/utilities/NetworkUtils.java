package it.softlab.app_oco.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by claudio on 5/3/17.
 */

public class NetworkUtils {
    private static final String BASE_URL = "http://svcs.ebay.com/services/search/FindingService/v1";
    public static URL buildUrlWithKeyword(String keyword) {
        Uri queryUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("OPERATION-NAME","findItemsByKeywords")
                .appendQueryParameter("SERVICE-VERSION","1.0.0")
                .appendQueryParameter("SECURITY-APPNAME","ClaudioC-Appco-PRD-369e0185b-a5920f1f")
                .appendQueryParameter("RESPONSE-DATA-FORMAT","JSON")
                .appendQueryParameter("REST-PAYLOAD","")
                .appendQueryParameter("keywords",keyword)
                .appendQueryParameter("paginationInput.entriesPerPage","10")
                .build();

        URL queryURL = null;
        try {
            queryURL = new URL(queryUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("NetworkUtils","Search URL: "+queryURL);

        return queryURL;
    }

    public static String getResponseFromUrl(URL searchURL) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) searchURL.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }
}
