package it.softlab.app_oco.utilities;

import org.json.JSONException;
import org.junit.Test;

import it.softlab.app_oco.model.Product;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by claudio on 5/4/17.
 */
public class JsonUtilsTest {
    @Test
    public void getProductDetails_ok() throws Exception {
        String jsonString = "{\"findItemsByKeywordsResponse\":[{\"ack\":[\"Success\"],\"version\":[\"1.13.0\"],\"timestamp\":[\"2017-05-04T07:26:05.829Z\"],\"searchResult\":[{\"@count\":\"2\",\"item\":[{\"itemId\":[\"232307236997\"],\"title\":[\"Flyzone Switch 2-in-1 Trainer\\/Sport Ready-To-Fly RTF Radio Controlled Airplane \"],\"globalId\":[\"EBAY-US\"],\"primaryCategory\":[{\"categoryId\":[\"182182\"],\"categoryName\":[\"Airplanes\"]}],\"galleryURL\":[\"http:\\/\\/thumbs2.ebaystatic.com\\/m\\/m2xdyro5bn2Wyr2G9hQFZrQ\\/140.jpg\"],\"viewItemURL\":[\"http:\\/\\/www.ebay.com\\/itm\\/Flyzone-Switch-2-in-1-Trainer-Sport-Ready-To-Fly-RTF-Radio-Controlled-Airplane-\\/232307236997\"],\"paymentMethod\":[\"PayPal\"],\"autoPay\":[\"false\"],\"postalCode\":[\"84116\"],\"location\":[\"Salt Lake City,UT,USA\"],\"country\":[\"US\"],\"shippingInfo\":[{\"shippingServiceCost\":[{\"@currencyId\":\"USD\",\"__value__\":\"0.0\"}],\"shippingType\":[\"Free\"],\"shipToLocations\":[\"US\"],\"expeditedShipping\":[\"false\"],\"oneDayShippingAvailable\":[\"false\"],\"handlingTime\":[\"1\"]}],\"sellingStatus\":[{\"currentPrice\":[{\"@currencyId\":\"USD\",\"__value__\":\"249.99\"}],\"convertedCurrentPrice\":[{\"@currencyId\":\"USD\",\"__value__\":\"249.99\"}],\"sellingState\":[\"Active\"],\"timeLeft\":[\"P15DT11H51M26S\"]}],\"listingInfo\":[{\"bestOfferEnabled\":[\"false\"],\"buyItNowAvailable\":[\"false\"],\"startTime\":[\"2017-04-19T19:17:31.000Z\"],\"endTime\":[\"2017-05-19T19:17:31.000Z\"],\"listingType\":[\"FixedPrice\"],\"gift\":[\"false\"]}],\"returnsAccepted\":[\"true\"],\"condition\":[{\"conditionId\":[\"1000\"],\"conditionDisplayName\":[\"New\"]}],\"isMultiVariationListing\":[\"false\"],\"topRatedListing\":[\"false\"]},{\"itemId\":[\"162500282584\"],\"title\":[\"Single Engine Airplane\"],\"globalId\":[\"EBAY-MOTOR\"],\"primaryCategory\":[{\"categoryId\":[\"63677\"],\"categoryName\":[\"Single Engine Airplanes\"]}],\"galleryURL\":[\"http:\\/\\/thumbs1.ebaystatic.com\\/m\\/m3xoiLxj9RqddP9OwmxOpOg\\/140.jpg\"],\"viewItemURL\":[\"http:\\/\\/www.ebay.com\\/itm\\/Single-Engine-Airplane-\\/162500282584\"],\"paymentMethod\":[\"MOCC\",\"CashInPerson\"],\"autoPay\":[\"false\"],\"postalCode\":[\"54665\"],\"location\":[\"Viroqua,WI,USA\"],\"country\":[\"US\"],\"shippingInfo\":[{\"shippingServiceCost\":[{\"@currencyId\":\"USD\",\"__value__\":\"0.0\"}],\"shippingType\":[\"FreePickup\"],\"shipToLocations\":[\"US\",\"CA\",\"GB\",\"AU\"],\"expeditedShipping\":[\"false\"],\"oneDayShippingAvailable\":[\"false\"]}],\"sellingStatus\":[{\"currentPrice\":[{\"@currencyId\":\"USD\",\"__value__\":\"84900.0\"}],\"convertedCurrentPrice\":[{\"@currencyId\":\"USD\",\"__value__\":\"84900.0\"}],\"sellingState\":[\"Active\"],\"timeLeft\":[\"P5DT7H12M2S\"]}],\"listingInfo\":[{\"bestOfferEnabled\":[\"true\"],\"buyItNowAvailable\":[\"false\"],\"startTime\":[\"2017-05-02T14:38:07.000Z\"],\"endTime\":[\"2017-05-09T14:38:07.000Z\"],\"listingType\":[\"FixedPrice\"],\"gift\":[\"false\"]}],\"returnsAccepted\":[\"false\"],\"condition\":[{\"conditionId\":[\"3000\"],\"conditionDisplayName\":[\"Used\"]}],\"isMultiVariationListing\":[\"false\"],\"topRatedListing\":[\"false\"]}]}],\"paginationOutput\":[{\"pageNumber\":[\"1\"],\"entriesPerPage\":[\"2\"],\"totalPages\":[\"195196\"],\"totalEntries\":[\"390391\"]}],\"itemSearchURL\":[\"http:\\/\\/www.ebay.com\\/sch\\/i.html?_nkw=airplane&_ddo=1&_ipg=2&_pgn=1\"]}]}";
        Product[] products = JsonUtils.getProductDetails(jsonString);
        assertThat(products.length,is(2));
    }

    @Test
    public void getProductDetails_error() throws Exception {
        String jsonString = "{\"errorMessage\":[{\"error\":[{\"errorId\":[\"2000\"],\"domain\":[\"CoreRuntime\"],\"severity\":[\"Error\"],\"category\":[\"Request\"],\"message\":[\"Service operation findItemsByKewords is unknown\"],\"subdomain\":[\"Inbound_Meta_Data\"],\"parameter\":[{\"@name\":\"Param1\",\"__value__\":\"findItemsByKewords\"}]}]}]}";
        Product[] products = JsonUtils.getProductDetails(jsonString);
        assertThat(products,nullValue());
    }

    @Test
    public void getProductDetails_empty() throws Exception {
        String jsonString = "";
        Product[] products = null;
        try {
            products = JsonUtils.getProductDetails(jsonString);
        } catch (JSONException e) {
            assertThat(products,nullValue());
            return;
        }
        fail("JSON exception not catched");
    }

}