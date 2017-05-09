package it.softlab.app_oco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.sync.QuerySyncTask;
import it.softlab.app_oco.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    RecyclerView mRecyclerView;
    private boolean mShowUSA;
    private boolean mShowITA;

    Product[] mSearchedProducts;
    private static final String KEY_LIST_DATA = "key_list_data";

    EditText mSearchEditText;
    ProgressBar mLoadingIndicator;
    ProductAdapter mAdapter;


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

        // TODO (recyclerview-2) create an adapter, assign to class field and set to the recyclerview
        mAdapter = new ProductAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_LIST_DATA)) {
                mSearchedProducts = (Product[]) savedInstanceState.getParcelableArray(KEY_LIST_DATA);
                // TODO (recyclerview-3) set new data to adapter and delete setAdapter
                mAdapter.setProductData(mSearchedProducts);
            }
        }

        // TODO (actionsearch-3)  add setOnEditorActionListener on the search edit text view
        // Manage IME_ACTION_SEND and run the query
        mSearchEditText = (EditText) findViewById(R.id.et_search_product);
        mSearchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                                      @Override
                                                      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                          if (actionId == EditorInfo.IME_ACTION_SEND) {
                                                              loadData();
                                                              return true;
                                                          }
                                                          return false;
                                                      }
                                                  });

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        setupSharedPreferences();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mShowUSA = sharedPreferences.getBoolean(
                getString(R.string.pref_show_us_key),
                getResources().getBoolean(R.bool.pref_show_us_default));
        mShowITA = sharedPreferences.getBoolean(
                getString(R.string.pref_show_it_key),
                getResources().getBoolean(R.bool.pref_show_it_default));


        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
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
            // TODO (recyclerview-4) set new data to adapter and delete setAdapter
            mAdapter.setProductData(null);
        } else if (s.equals(getString(R.string.pref_show_it_key))) {
            mShowUSA = sharedPreferences.getBoolean(
                    getString(R.string.pref_show_it_key),
                    getResources().getBoolean(R.bool.pref_show_it_default));
            // TODO (recyclerview-5) set new data to adapter and delete setAdapter
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

        // TODO (progressbar-3) Override onPreExecute and make the progressbar visible and recycleview invisible

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

        // TODO (progressbar-4) onPostExecute make the progressbar invisible and the recycleview visible
        @Override
        protected void onPostExecute(Product[] p) {
            if (p != null) {
                // TODO (recyclerview-6) set new data to adapter and delete setAdapter
                mAdapter.setProductData(p);
                showList();
            }
        }


    }

}
