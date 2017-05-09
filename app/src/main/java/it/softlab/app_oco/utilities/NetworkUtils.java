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

    private static final String GLOBALID_US = JsonUtils.COUNTRY_US;
    private static final String GLOBALID_IT = JsonUtils.COUNTRY_IT;
    public static final String SITEID_US = "0";
    public static final String SITEID_IT = "101";

    private static final String KEY_OPERATION = "OPERATION-NAME";
    private static final String KEY_SERVICE = "SERVICE-VERSION";
    private static final String KEY_SECURITY = "SECURITY-APPNAME";
    private static final String KEY_RESPONSE_FORMAT = "RESPONSE-DATA-FORMAT";
    private static final String KEY_REST_PAYLOAD = "REST-PAYLOAD";
    private static final String KEY_KEYWORDS = "keywords";
    private static final String KEY_PAGINATION = "paginationInput.entriesPerPage";
    private static final String KEY_SITEID = "siteId";
    private static final String KEY_GLOBALID = "GLOBAL-ID";

    private static final String VALUE_OPERATION = "findItemsByKeywords";
    private static final String VALUE_SERVICE = "1.0.0";
    private static final String VALUE_SECURITY = "ClaudioC-Appco-PRD-369e0185b-a5920f1f";
    private static final String VALUE_RESPONSE_FORMAT = "JSON";
    private static final String VALUE_REST_PAYLOAD = "";
    private static final String VALUE_PAGINATION = "10";

    public static URL buildUrlWithKeyword(String keyword) {
        return buildUrlWithKeywordAndSiteId(keyword,SITEID_US);
    }

    public static URL buildUrlWithKeywordAndSiteId(String keyword, String siteId) {
        // see here: http://developer.ebay.com/DevZone/finding/CallRef/findItemsByKeywords.html#sort
        Uri queryUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(KEY_OPERATION,VALUE_OPERATION)
                .appendQueryParameter(KEY_SERVICE,VALUE_SERVICE)
                .appendQueryParameter(KEY_SECURITY,VALUE_SECURITY)
                .appendQueryParameter(KEY_RESPONSE_FORMAT,VALUE_RESPONSE_FORMAT)
                .appendQueryParameter(KEY_REST_PAYLOAD,VALUE_REST_PAYLOAD)
                .appendQueryParameter(KEY_KEYWORDS,keyword)
                .appendQueryParameter(KEY_PAGINATION,VALUE_PAGINATION)
                .appendQueryParameter(KEY_SITEID,siteId)
                .appendQueryParameter(KEY_GLOBALID,siteIdToGlobalId(siteId))
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
