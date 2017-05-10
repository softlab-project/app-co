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
    private String NAME_1 = "name1";
    private String LOCATION_1 = "location1";
    private String PRICE_1 = "price1";
    private String COUNTRY_1 = "country1";
    private String GALLERY_1 = "gallery1";

    private String NAME_2 = "name2";
    private String LOCATION_2 = "location2";
    private String PRICE_2 = "price2";
    private String COUNTRY_2 = "country2";
    private String GALLERY_2 = "gallery2";

    private String KEY_TEST = "key_test";

    @Test
    public void parcelableTest() throws Exception {
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

    @Test
    public void parcelableTest2() throws Exception {


        Bundle bundle = new Bundle();

        Product[] products = {
                new Product(NAME_1,LOCATION_1,PRICE_1,COUNTRY_1,GALLERY_1),
                new Product(NAME_2,LOCATION_2,PRICE_2,COUNTRY_2,GALLERY_2),
        };
        bundle.putParcelableArray(KEY_TEST,products);
        Product[] outProducts = (Product[])bundle.getParcelableArray(KEY_TEST);

        assertThat(outProducts[0].getName(),is(products[0].getName()));
        assertThat(outProducts[0].getPrice(),is(products[0].getPrice()));
        assertThat(outProducts[0].getLocation(),is(products[0].getLocation()));
        assertThat(outProducts[0].getCountry(),is(products[0].getCountry()));
        assertThat(outProducts[0].getGalleryUrlString(),is(products[0].getGalleryUrlString()));

        assertThat(outProducts[1].getName(),is(products[1].getName()));
        assertThat(outProducts[1].getPrice(),is(products[1].getPrice()));
        assertThat(outProducts[1].getLocation(),is(products[1].getLocation()));
        assertThat(outProducts[1].getCountry(),is(products[1].getCountry()));
        assertThat(outProducts[1].getGalleryUrlString(),is(products[1].getGalleryUrlString()));
    }

}