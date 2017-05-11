package it.softlab.app_oco;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.receivers.ConnectionBroadcastReceiver;
import it.softlab.app_oco.sync.QuerySyncTask;
import it.softlab.app_oco.utilities.JsonUtils;
import it.softlab.app_oco.utilities.NetworkUtils;
import it.softlab.app_oco.utilities.NotificationUtils;
import it.softlab.app_oco.utilities.ReminderUtils;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "MainActivity";
    RecyclerView mRecyclerView;
    private boolean mShowUSA;
    private boolean mShowITA;

    Product[] mSearchedProducts;
    private static final String KEY_LIST_DATA = "key_list_data";

    EditText mSearchEditText;
    ProgressBar mLoadingIndicator;
    ProductAdapter mAdapter;
    ConnectionBroadcastReceiver mBroadcastReceiver;

    SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_search_result);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
        mRecyclerView.setLayoutManager(layoutManager);

        // DONE (recyclerview-2) create an adapter, assign to class field and set to the recyclerview
        mAdapter = new ProductAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_LIST_DATA)) {
                mSearchedProducts = (Product[]) savedInstanceState.getParcelableArray(KEY_LIST_DATA);
                // DONE (recyclerview-3) set new data to adapter and delete setAdapter
                mAdapter.setProductData(mSearchedProducts);
            }
        }

        // DONE (actionsearch-3)  add setOnEditorActionListener on the search edit text view
        // Manage IME_ACTION_SEARCH and run the query
        mSearchEditText = (EditText) findViewById(R.id.et_search_product);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                                      @Override
                                                      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                          if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                                                              loadData();
                                                              return true;
                                                          }
                                                          return false;
                                                      }
                                                  });

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        setupSharedPreferences();

        // TODO remove, debug only
        Product product = new Product("iPhone","Roma","100", JsonUtils.COUNTRY_IT,"http://thumbs4.ebaystatic.com/m/mNTFKaH9UkE_OlLXo1mbljA/140.jpg");
//        NotificationUtils.priceChangedNotification(this,product);

//        startDetailActivity(product);

    }

    private void startDetailActivity(Product product) {
        Intent intent = new Intent(this,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailActivity.KEY_DATA,product);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        mBroadcastReceiver  = new ConnectionBroadcastReceiver();
        registerReceiver(mBroadcastReceiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setupSharedPreferences() {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mShowUSA = mSharedPreferences.getBoolean(
                getString(R.string.pref_show_us_key),
                getResources().getBoolean(R.bool.pref_show_us_default));
        mShowITA = mSharedPreferences.getBoolean(
                getString(R.string.pref_show_it_key),
                getResources().getBoolean(R.bool.pref_show_it_default));


        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadData() {
        String searchedProduct = mSearchEditText.getText().toString();

        new FetchDataTask().execute(searchedProduct);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArray(KEY_LIST_DATA,mSearchedProducts);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            loadData();
            return true;
        } else if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals(getString(R.string.pref_show_us_key))) {
            mShowUSA = sharedPreferences.getBoolean(
                    getString(R.string.pref_show_us_key),
                    getResources().getBoolean(R.bool.pref_show_us_default));
            // DONE (recyclerview-4) set new data to adapter and delete setAdapter
            mAdapter.setProductData(null);
        } else if (s.equals(getString(R.string.pref_show_it_key))) {
            mShowITA = sharedPreferences.getBoolean(
                    getString(R.string.pref_show_it_key),
                    getResources().getBoolean(R.bool.pref_show_it_default));
            // DONE (recyclerview-5) set new data to adapter and delete setAdapter
            mAdapter.setProductData(null);
        }
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showList() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public class FetchDataTask extends AsyncTask<String, Void, Product[]> {

        // DONE (progressbar-3) Override onPreExecute and make the progressbar visible and recycleview invisible

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading();
        }

        @Override
        protected Product[] doInBackground(String... strings) {

            if (strings.length == 0) {
                return null;
            }

            String queryString = strings[0];
            String[] siteIdArray = {NetworkUtils.SITEID_US,NetworkUtils.SITEID_IT};
            boolean[] showSiteIdArray = {mShowUSA,mShowITA};

            mSearchedProducts = QuerySyncTask.queryProducts(queryString,
                    siteIdArray,
                    showSiteIdArray);

            return mSearchedProducts;

        }

        // DONE (progressbar-4) onPostExecute make the progressbar invisible and the recycleview visible
        @Override
        protected void onPostExecute(Product[] p) {
            if (p != null) {
                // DONE (recyclerview-6) set new data to adapter and delete setAdapter
                mAdapter.setProductData(p);
                showList();

                if (mSearchedProducts!=null && mSearchedProducts.length > 0) {
                    String lowerPrice = mSearchedProducts[0].getPrice();
                    String queryString = mSearchEditText.getText().toString();
                    mSharedPreferences.edit()
                            .putString(getString(R.string.pref_search_string_key), queryString)
                            .putString(getString(R.string.pref_lower_price_key), lowerPrice)
                            .apply();
                    ReminderUtils.schedulePriceCheckReminder(MainActivity.this);
                    Log.d(TAG, "onPostExecute: ok");
                }
            }
        }


    }

}
