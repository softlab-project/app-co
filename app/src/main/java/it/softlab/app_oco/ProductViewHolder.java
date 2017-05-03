package it.softlab.app_oco;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by claudio on 5/3/17.
 */

public class ProductViewHolder extends RecyclerView.ViewHolder {
    final TextView mNameTextView;
    final TextView mLocationTextView;
    final TextView mPriceTextView;

    public ProductViewHolder(View itemView) {
        super(itemView);
        mNameTextView = (TextView)itemView.findViewById(R.id.tv_item_name);
        mLocationTextView = (TextView)itemView.findViewById(R.id.tv_item_location);
        mPriceTextView = (TextView)itemView.findViewById(R.id.tv_item_price);
    }
}
