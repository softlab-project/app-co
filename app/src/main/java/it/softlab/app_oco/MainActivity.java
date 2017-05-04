package it.softlab.app_oco;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.utilities.JsonUtils;
import it.softlab.app_oco.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    String mJsonResponse;

    private static final String KEY_JSON = "key_json";

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
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchDataTask extends AsyncTask<String, Void, Product[]> {

        @Override
        protected Product[] doInBackground(String... strings) {
            Product[] searchedProducts = null;

            if (strings.length == 0) {
                return null;
            }

            String query = strings[0];
            URL searchURL = NetworkUtils.buildUrlWithKeyword(query);

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
