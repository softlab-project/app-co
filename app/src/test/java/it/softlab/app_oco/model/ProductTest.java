package it.softlab.app_oco.model;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by claudio on 5/4/17.
 */
public class ProductTest {

    @Test
    public void constructorTest() {
        String NAME = "name";
        String LOCATION = "location";
        String PRICE = "price";
        Product p = new Product(NAME, "location", "price");
        assertThat(p.getName(),is(NAME));
        assertThat(p.getLocation(),is(LOCATION));
        assertThat(p.getPrice(),is(PRICE));
    }


}
