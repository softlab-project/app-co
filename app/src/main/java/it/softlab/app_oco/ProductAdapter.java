package it.softlab.app_oco;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.softlab.app_oco.model.Product;

/**
 * Created by claudio on 5/3/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private static final String TAG = "ProductAdapter";

    private final Context mContext;
    private Product[] mProductData;

    public ProductAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_list_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product p = mProductData[position];
        holder.mNameTextView.setText(p.getName());
        holder.mLocationTextView.setText(p.getLocation());
        holder.mPriceTextView.setText(p.getPrice());
        int countryFlagResourceId = countryToResourceId(p.getCountry());
        holder.mIconView.setImageResource(countryFlagResourceId);
    }

    private int countryToResourceId(String country) {
        int countryResourceId;
        switch (country) {
            case "EBAY-US":
                countryResourceId = R.drawable.ic_country_us;
                break;
            case "EBAY-IT":
                countryResourceId = R.drawable.ic_country_italy;
                break;
            default:
                Log.e(TAG, "Unknown country: " + country);
                countryResourceId = R.drawable.ic_country_unknown;
        }
        return countryResourceId;
    }

    @Override
    public int getItemCount() {
        if (mProductData != null) {
            return mProductData.length;
        }
        return 0;
    }

    public void setProductData(Product[] p) {
        mProductData = p;
        // TODO (recyclerview-1) add notifyDataSetChanged() to notify recycler view that data is changed
        notifyDataSetChanged();
    }
}
