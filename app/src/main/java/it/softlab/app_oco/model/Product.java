package it.softlab.app_oco.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by claudio on 5/3/17.
 */

// TODO (sort-1) implements the comparable interface Done

public class Product implements Parcelable ,Comparable<Product> {
    private final String name;
    private final String location;
    private final String price;

    public Product(String name, String location, String price) {
        this.name = name;
        this.location = location;
        this.price = price;
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

    }

    @Override
    public int compareTo(@NonNull Product o) {
       return Double.compare(Double.parseDouble(this.price),Double.parseDouble(o.getPrice()));

    }
    // TODO (sort-2) implements compareTo Done
}
