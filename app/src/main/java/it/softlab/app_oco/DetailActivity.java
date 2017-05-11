package it.softlab.app_oco;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.sync.ImageSyncTask;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY_DATA = "data";

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
        Product product = bundle.getParcelable(KEY_DATA);
        mNameTextView.setText(product.getName());
        mLocationTextView.setText(product.getLocation());
        mPriceTextView.setText(product.getPrice());
        mCountryTextView.setText(product.getCountry());

        String galleryUrlString = product.getGalleryUrlString();
        if (galleryUrlString!=null && !galleryUrlString.isEmpty()) {

            new FetchImageTask().execute(galleryUrlString);
        }

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

    private class FetchImageTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            if (strings.length == 0) {
                return null;
            }
            String galleryUrlString = strings[0];

            return ImageSyncTask.fetchImage(galleryUrlString);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                mImageGallery.setImageBitmap(bitmap);
            }
        }
    }
}
