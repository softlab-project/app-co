package it.softlab.app_oco.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.preference.PreferenceManager;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import it.softlab.app_oco.model.Product;
import it.softlab.app_oco.utilities.JsonUtils;
import it.softlab.app_oco.utilities.NotificationUtils;

/**
 * Created by claudio on 5/11/17.
 */

public class PriceCheckJobService extends JobService {
    AsyncTask mBackgroundTask;

    @Override
    public boolean onStartJob(final JobParameters job) {
        mBackgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = PriceCheckJobService.this;
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                Product product = new Product("iPhone","Roma","100", JsonUtils.COUNTRY_IT,"http://thumbs4.ebaystatic.com/m/mNTFKaH9UkE_OlLXo1mbljA/140.jpg");

                NotificationUtils.priceChangedNotification(context,product);
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
