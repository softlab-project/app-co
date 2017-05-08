package it.softlab.app_oco;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.softlab.app_oco.model.Product;

/**
 * Created by claudio on 5/3/17.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    private final Context mContext;
    private Product[] mProductData;

    public ProductAdapter(Context context) {
        mContext = context;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_list_item,parent,false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product p = mProductData[position];
        holder.mNameTextView.setText(p.getName());
        holder.mLocationTextView.setText(p.getLocation());
        holder.mPriceTextView.setText(p.getPrice());
    }

    @Override
    public int getItemCount() {
        if (mProductData!=null) {
            return mProductData.length;
        }
        return 0;
    }

    public void setProductData(Product[] p) {
        mProductData = p;
        // TODO (recyclerview-1) add notifyDataSetChanged() to notify recycler view that data is changed

    }
}
