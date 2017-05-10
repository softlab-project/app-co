package it.softlab.app_oco.model;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by claudio on 5/4/17.
 */
public class ProductTest {

    @Test
    public void constructorTest_ok() {
        String NAME = "name";
        String LOCATION = "location";
        String PRICE = "price";
        String COUNTRY = "country";
        Product p = new Product(NAME, LOCATION, PRICE, COUNTRY);
        assertThat(p.getName(),is(NAME));
        assertThat(p.getLocation(),is(LOCATION));
        assertThat(p.getPrice(),is(PRICE));
        assertThat(p.getCountry(),is(COUNTRY));
    }

    @Test
    public void constructorTest2_ok() {
        String NAME = "name";
        String LOCATION = "location";
        String PRICE = "price";
        String COUNTRY = "country";
        String GALLERY = "country";
        Product p = new Product(NAME, LOCATION, PRICE, COUNTRY,GALLERY);
        assertThat(p.getName(),is(NAME));
        assertThat(p.getLocation(),is(LOCATION));
        assertThat(p.getPrice(),is(PRICE));
        assertThat(p.getCountry(),is(COUNTRY));
        assertThat(p.getGalleryUrlString(),is(GALLERY));
    }

    @Test
    public void compareTest_1() {
        String PRICE_1 = "10.0";
        String PRICE_2 = "20.0";
        String PRICE_3 = "30.0";
        Product[] products = {
                new Product("", "", PRICE_1, ""),
                new Product("", "", PRICE_2, ""),
                new Product("", "", PRICE_3, ""),
        };
        Arrays.sort(products);
        assertThat(products[0].getPrice(),is(PRICE_1));
        assertThat(products[1].getPrice(),is(PRICE_2));
        assertThat(products[2].getPrice(),is(PRICE_3));

    }

    @Test
    public void compareTest_2() {
        String PRICE_1 = "10.0";
        String PRICE_2 = "20.0";
        String PRICE_3 = "30.0";
        Product[] products = {
                new Product("", "", PRICE_3, ""),
                new Product("", "", PRICE_2, ""),
                new Product("", "", PRICE_1, ""),
        };
        Arrays.sort(products);
        assertThat(products[0].getPrice(),is(PRICE_1));
        assertThat(products[1].getPrice(),is(PRICE_2));
        assertThat(products[2].getPrice(),is(PRICE_3));

    }

    @Test
    public void compareTest_3() {
        String PRICE_1 = "10.0";
        String PRICE_2 = "20.0";
        String PRICE_3 = "30.0";
        Product[] products = {
                new Product("", "", PRICE_3, ""),
                new Product("", "", PRICE_1, ""),
                new Product("", "", PRICE_2, ""),
        };
        Arrays.sort(products);
        assertThat(products[0].getPrice(),is(PRICE_1));
        assertThat(products[1].getPrice(),is(PRICE_2));
        assertThat(products[2].getPrice(),is(PRICE_3));

    }

}
