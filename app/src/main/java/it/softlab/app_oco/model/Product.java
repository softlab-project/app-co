package it.softlab.app_oco.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by claudio on 5/3/17.
 */

// DONE (sort-1) implements the comparable interface
public class Product implements Parcelable, Comparable<Product> {
    private final String name;
    private final String location;
    private final String price;
    private final String country;
    private final String galleryUrlString;

    public Product(String name, String location, String price, String country) {
        this(name,location,price,country,"");
    }

    public Product(String name, String location, String price, String country, String galleryUrl) {
        this.name = name;
        this.location = location;
        this.price = price;
        this.country = country;
        this.galleryUrlString = galleryUrl;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getPrice() {
        return price;
    }

    public String getCountry() {
        return country;
    }

    // Parcelable implementation
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(price);
        parcel.writeString(country);
        parcel.writeString(galleryUrlString);
    }
    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>() {
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    private Product(Parcel in) {
        this.name = in.readString();
        this.location = in.readString();
        this.price = in.readString();
        this.country = in.readString();
        this.galleryUrlString = in.readString();

    }

    // DONE (sort-2) implements compareTo
    @Override
    public int compareTo(@NonNull Product product) {
        float otherPrice = Float.valueOf(product.getPrice());
        float thisPrice = Float.valueOf(this.getPrice());

        if (thisPrice>otherPrice) {
            return 1;
        }
        if (thisPrice<otherPrice) {
            return -1;
        }

        return 0;
    }

    public String getGalleryUrlString() {
        return galleryUrlString;
    }
}
