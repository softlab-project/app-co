package it.softlab.app_oco;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    }

    public void searchProduct(View view) {
        EditText searchProductEditText = (EditText)findViewById(R.id.et_search_product);
        String searchedProduct = searchProductEditText.getText().toString();

        loadData(searchedProduct);

    }

    private void loadData(String searchProduct) {
        new FetchDataTask().execute(searchProduct);
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

            String jsonSearchResult = null;
            try {
                jsonSearchResult = NetworkUtils.getResponseFromUrl(searchURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                searchedProducts = JsonUtils.getSimpleProductDescription(jsonSearchResult);
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
