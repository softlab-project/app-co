package it.softlab.training.app_oco;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import it.softlab.training.app_oco.model.Product;
import it.softlab.training.app_oco.utilities.JsonUtils;

/**
 * Created by claudio on 5/3/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private static final String TAG = "ProductAdapter";

    private final Context mContext;
    private Product[] mProductData;
    private final ProductAdapterOnClickListener mOnClickListener;

    public ProductAdapter(Context context, ProductAdapterOnClickListener listener) {
        mContext = context;
        mOnClickListener = listener;
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
            case JsonUtils.COUNTRY_US:
                countryResourceId = R.drawable.ic_country_us;
                break;
            case JsonUtils.COUNTRY_IT:
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
        // DONE (recyclerview-1) add notifyDataSetChanged() to notify recycler view that data is changed
        notifyDataSetChanged();
    }

    public interface ProductAdapterOnClickListener {
        void onListItemClick(int position);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mNameTextView;
        final TextView mLocationTextView;
        final TextView mPriceTextView;
        final ImageView mIconView;

        public ProductViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView)itemView.findViewById(R.id.tv_item_name);
            mLocationTextView = (TextView)itemView.findViewById(R.id.tv_item_location);
            mPriceTextView = (TextView)itemView.findViewById(R.id.tv_item_price);
            mIconView = (ImageView) itemView.findViewById(R.id.ic_country_flag);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
