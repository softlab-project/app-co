package it.softlab.app_oco.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.renderscript.Double2;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import it.softlab.app_oco.R;
import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.utilities.JsonUtils;
import it.softlab.app_oco.utilities.NetworkUtils;
import it.softlab.app_oco.utilities.NotificationUtils;

/**
 * Created by claudio on 5/11/17.
 */

public class PriceCheckJobService extends JobService {
    private static final String TAG = "PriceCheckJobService";
    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = PriceCheckJobService.this;
                Log.d(TAG, "doInBackground: start");

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String queryString = sharedPreferences.getString(context.getString(R.string.pref_search_string_key),"");
                String priceString = sharedPreferences.getString(context.getString(R.string.pref_lower_price_key),"");
                String[] siteIdArray = {NetworkUtils.SITEID_US,NetworkUtils.SITEID_IT};
                boolean showUSA = sharedPreferences.getBoolean(
                        getString(R.string.pref_show_us_key),
                        getResources().getBoolean(R.bool.pref_show_us_default));
                boolean showITA = sharedPreferences.getBoolean(
                        getString(R.string.pref_show_it_key),
                        getResources().getBoolean(R.bool.pref_show_it_default));
                boolean[] showSiteIdArray = {showUSA,showITA};

                Log.d(TAG, "doInBackground: " +
                        queryString + " " + priceString);

                Product[] searchedProducts = QuerySyncTask.queryProducts(queryString,
                        siteIdArray,
                        showSiteIdArray);

                if (searchedProducts!=null && searchedProducts.length>0) {
                    String newPrice = searchedProducts[0].getPrice();
                    // TODO debug only: remove equal!
//                    if (Double.valueOf(newPrice) < Double.valueOf(priceString)) {
                        if (Double.valueOf(newPrice) <= Double.valueOf(priceString)) {
                        Log.d(TAG, "doInBackground: new item!!! " + newPrice);
                        NotificationUtils.priceChangedNotification(context, searchedProducts[0]);
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job,false);
            }

        };
        mBackgroundTask.execute();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if (mBackgroundTask!=null) {
            mBackgroundTask.cancel(true);
        }
        return false;
    }
}
