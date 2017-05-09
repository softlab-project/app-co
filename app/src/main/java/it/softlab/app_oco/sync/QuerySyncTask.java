package it.softlab.app_oco.sync;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.utilities.JsonUtils;
import it.softlab.app_oco.utilities.NetworkUtils;

/**
 * Created by claudio on 5/9/17.
 */

public class QuerySyncTask {
    public static Product[] queryProducts(String query,
                                          String[] siteIdArray,
                                          boolean[] showSiteIdArray) {
        List<Product> productList = new ArrayList<>();

        for (int i = 0; i < siteIdArray.length; i++) {
            boolean showSiteId = showSiteIdArray[i];
            if (showSiteId)
            {
                String siteId = siteIdArray[i];
                Product[] searchedProductsCountry;

                searchedProductsCountry = searchProductsByKeywordAndSiteID(query, siteId);
                if (searchedProductsCountry != null) {
                    productList.addAll(Arrays.asList(searchedProductsCountry));
                }
            }
        }

        Product[] searchedProducts = productList.toArray(new Product[productList.size()]);

        // TODO (sort-3) sort the array before returning it
        Arrays.sort(searchedProducts);
        return searchedProducts;
    }

    private static Product[] searchProductsByKeywordAndSiteID(String query, String siteIdUs) {
        Product[] searchedProducts = null;
        URL searchURL = NetworkUtils.buildUrlWithKeywordAndSiteId(query, siteIdUs);

        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.getResponseFromUrl(searchURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            searchedProducts = JsonUtils.getProductDetails(jsonResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchedProducts;
    }
}
