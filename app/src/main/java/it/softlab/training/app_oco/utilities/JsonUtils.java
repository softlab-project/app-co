package it.softlab.training.app_oco.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.softlab.training.app_oco.model.Product;

/**
 * Created by claudio on 5/3/17.
 */

public class JsonUtils {

    private static final String KEY_ERROR = "errorMessage";

    private static final String KEY_OPERATION = "findItemsByKeywordsResponse";
    private static final String KEY_SEARCH = "searchResult";
    private static final String KEY_ITEM = "item";

    private static final String KEY_TITLE = "title";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_SELLING_STATUS = "sellingStatus";
    private static final String KEY_CURRENT_PRICE = "currentPrice";
    private static final String KEY_VALUE = "__value__";
    private static final String KEY_GLOBALID = "globalId";
    private static final String KEY_GALLERY = "galleryURL";

    public static final String COUNTRY_US = "EBAY-US";
    public static final String COUNTRY_IT = "EBAY-IT";

    public static Product[] getProductDetails(String jsonString) throws JSONException {
        Product[] parsedData = null;

        JSONObject dataJson = new JSONObject(jsonString);
        if (dataJson.has(KEY_ERROR)) {
            return null;
        }

        JSONObject response = dataJson.getJSONArray(KEY_OPERATION)
                .getJSONObject(0);

        JSONObject searchResult = response.getJSONArray(KEY_SEARCH)
                .getJSONObject(0);

        JSONArray itemArray = searchResult.getJSONArray(KEY_ITEM);

        parsedData = new Product[itemArray.length()];

        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);

            String title = item.getJSONArray(KEY_TITLE).getString(0);
            String location = item.getJSONArray(KEY_LOCATION).getString(0);
            String price = item.getJSONArray(KEY_SELLING_STATUS)
                    .getJSONObject(0)
                    .getJSONArray(KEY_CURRENT_PRICE)
                    .getJSONObject(0)
                    .getString(KEY_VALUE);
            String country = item.getJSONArray(KEY_GLOBALID).getString(0);
            String galleryUrl = item.getJSONArray(KEY_GALLERY).getString(0);

            parsedData[i] = new Product(title,location,price,country,galleryUrl);
        }


        return parsedData;
    }
}
