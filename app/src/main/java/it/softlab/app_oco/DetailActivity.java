package it.softlab.app_oco;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import it.softlab.app_oco.model.Product;

public class DetailActivity extends AppCompatActivity {

    private TextView mNameTextView;
    private TextView mLocationTextView;
    private TextView mPriceTextView;
    private TextView mCountryTextView;
    private ImageView mImageGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mNameTextView = (TextView)findViewById(R.id.tv_detail_name);
        mLocationTextView = (TextView)findViewById(R.id.tv_detail_location);
        mPriceTextView = (TextView)findViewById(R.id.tv_detail_price);
        mCountryTextView = (TextView)findViewById(R.id.tv_detail_country);
        mImageGallery = (ImageView)findViewById(R.id.iv_detail_gallery);

        Bundle bundle = getIntent().getExtras();
        Product product = bundle.getParcelable("data");
        mNameTextView.setText(product.getName());
        mLocationTextView.setText(product.getLocation());
        mPriceTextView.setText(product.getPrice());
        mCountryTextView.setText(product.getCountry());
        mImageGallery.setImageURI(Uri.parse(product.getGalleryUrlString()));

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
