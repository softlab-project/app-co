package it.softlab.app_oco.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import it.softlab.app_oco.model.Product;

/**
 * Created by claudio on 5/3/17.
 */

public class JsonUtils {

    public static Product[] getProductDetails(String jsonString) throws JSONException {
        Product[] parsedData = null;

        JSONObject dataJson = new JSONObject(jsonString);
        if (dataJson.has("errorMessage")) {
            return null;
        }

        JSONObject response = dataJson.getJSONArray("findItemsByKeywordsResponse")
                .getJSONObject(0);

        JSONObject searchResult = response.getJSONArray("searchResult")
                .getJSONObject(0);

        JSONArray itemArray = searchResult.getJSONArray("item");

        parsedData = new Product[itemArray.length()];

        for (int i = 0; i < itemArray.length(); i++) {
            JSONObject item = itemArray.getJSONObject(i);

            String title = item.getJSONArray("title").getString(0);
            String location = item.getJSONArray("location").getString(0);
            String price = item.getJSONArray("sellingStatus")
                    .getJSONObject(0)
                    .getJSONArray("currentPrice")
                    .getJSONObject(0)
                    .getString("__value__");
            String country = item.getJSONArray("globalId").getString(0);

            parsedData[i] = new Product(title,location,price,country);
        }


        return parsedData;
    }
}
