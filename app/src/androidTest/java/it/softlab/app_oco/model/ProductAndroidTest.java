package it.softlab.app_oco.model;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by claudio on 5/9/17.
 */
@RunWith(AndroidJUnit4.class)
public class ProductAndroidTest {

    @Test
    public void parcelableTest() throws Exception {
        String NAME_1 = "name1";
        String LOCATION_1 = "location1";
        String PRICE_1 = "price1";
        String COUNTRY_1 = "country1";
        
        String NAME_2 = "name2";
        String LOCATION_2 = "location2";
        String PRICE_2 = "price2";
        String COUNTRY_2 = "country2";

        String KEY_TEST = "key_test";

        Bundle bundle = new Bundle();

        Product[] products = {
                new Product(NAME_1,LOCATION_1,PRICE_1,COUNTRY_1),
                new Product(NAME_2,LOCATION_2,PRICE_2,COUNTRY_2),
        };
        bundle.putParcelableArray(KEY_TEST,products);
        Product[] outProducts = (Product[])bundle.getParcelableArray(KEY_TEST);

        assertThat(outProducts[0].getName(),is(products[0].getName()));
        assertThat(outProducts[0].getPrice(),is(products[0].getPrice()));
        assertThat(outProducts[0].getLocation(),is(products[0].getLocation()));
        assertThat(outProducts[0].getCountry(),is(products[0].getCountry()));

        assertThat(outProducts[1].getName(),is(products[1].getName()));
        assertThat(outProducts[1].getPrice(),is(products[1].getPrice()));
        assertThat(outProducts[1].getLocation(),is(products[1].getLocation()));
        assertThat(outProducts[1].getCountry(),is(products[1].getCountry()));
    }

}