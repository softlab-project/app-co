package it.softlab.app_oco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.utilities.JsonUtils;
import it.softlab.app_oco.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity
implements SharedPreferences.OnSharedPreferenceChangeListener{

    RecyclerView mRecyclerView;
    String mJsonResponse;
    boolean mShowUSA;

    private static final String KEY_JSON = "key_json";
    private static final String USA_SITEID = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_search_result);
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,
                        false);
        mRecyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState!=null) {
            if (savedInstanceState.containsKey(KEY_JSON)) {
                 mJsonResponse = savedInstanceState.getString(KEY_JSON);
                    try {
                        Product[] p = JsonUtils
                                .getSimpleProductDescription(mJsonResponse);
                        ProductAdapter adapter = new ProductAdapter(this);
                        adapter.setProductData(p);
                        mRecyclerView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }

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

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadData() {
        EditText searchProductEditText = (EditText)findViewById(R.id.et_search_product);
        String searchedProduct = searchProductEditText.getText().toString();

        new FetchDataTask().execute(searchedProduct);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_JSON,mJsonResponse);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_search) {
            loadData();
            return true;
        } else if (item.getItemId()==R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
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
            ProductAdapter adapter = new ProductAdapter(getApplicationContext());
            adapter.setProductData(null);
            mRecyclerView.setAdapter(adapter);
        }
    }

    public class FetchDataTask extends AsyncTask<String, Void, Product[]> {

        @Override
        protected Product[] doInBackground(String... strings) {
            Product[] searchedProducts = null;

            if (strings.length == 0) {
                return null;
            }

            String query = strings[0];
            if (mShowUSA) {
                URL searchURL = NetworkUtils.buildUrlWithKeywordAndSiteId(query,USA_SITEID);

                mJsonResponse = null;
                try {
                    mJsonResponse = NetworkUtils.getResponseFromUrl(searchURL);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    searchedProducts = JsonUtils.getSimpleProductDescription(mJsonResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return searchedProducts;

        }

        @Override
        protected void onPostExecute(Product[] p) {
            if (p!=null) {
                ProductAdapter adapter = new ProductAdapter(getApplicationContext());
                adapter.setProductData(p);
                mRecyclerView.setAdapter(adapter);
            }
        }


    }

}
