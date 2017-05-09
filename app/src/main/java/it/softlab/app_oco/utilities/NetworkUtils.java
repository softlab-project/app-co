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
    public static final String BASE_URL = "http://svcs.ebay.com/services/search/FindingService/v1";

    private static final String GLOBALID_US = "EBAY-US";
    private static final String GLOBALID_IT = "EBAY-IT";
    public static final String SITEID_US = "0";
    public static final String SITEID_IT = "101";

    public static URL buildUrlWithKeyword(String keyword) {
        return buildUrlWithKeywordAndSiteId(keyword,SITEID_US);
    }

    public static URL buildUrlWithKeywordAndSiteId(String keyword, String siteId) {
        // see here: http://developer.ebay.com/DevZone/finding/CallRef/findItemsByKeywords.html#sort
        Uri queryUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter("OPERATION-NAME","findItemsByKeywords")
                .appendQueryParameter("SERVICE-VERSION","1.0.0")
                .appendQueryParameter("SECURITY-APPNAME","ClaudioC-Appco-PRD-369e0185b-a5920f1f")
                .appendQueryParameter("RESPONSE-DATA-FORMAT","JSON")
                .appendQueryParameter("REST-PAYLOAD","")
                .appendQueryParameter("keywords",keyword)
                .appendQueryParameter("paginationInput.entriesPerPage","10")
                .appendQueryParameter("siteId",siteId)
                .appendQueryParameter("GLOBAL-ID",siteIdToGlobalId(siteId))
                //.appendQueryParameter("sortOrder","PricePlusShippingLowest")
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

    private static String siteIdToGlobalId(String siteId) {
        String globalId = "";
        switch (siteId) {
            case SITEID_US: globalId = GLOBALID_US;break;
            case SITEID_IT: globalId = GLOBALID_IT;break;
        }
        return globalId;
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
