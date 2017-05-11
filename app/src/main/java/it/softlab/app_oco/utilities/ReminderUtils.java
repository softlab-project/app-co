package it.softlab.app_oco.utilities;

import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import it.softlab.app_oco.sync.PriceCheckJobService;

/**
 * Created by claudio on 5/11/17.
 */

public class ReminderUtils {

    private static final int PRICE_CHECK_INTERVAL_TIME = 5;
    private static final int PRICE_CHECK_FLEX_TIME = PRICE_CHECK_INTERVAL_TIME;

    private static final String PRICE_CHECK_JOB_TAG = "price_check_job";

    private static FirebaseJobDispatcher mDispatcher;

    private static boolean sInitialized = false;

    synchronized public static void schedulePriceCheckReminder(Context context) {
        if (sInitialized) { return; }
        Driver driver = new GooglePlayDriver(context);
        mDispatcher = new FirebaseJobDispatcher(driver);
        Job job = mDispatcher.newJobBuilder()
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setRecurring(true)
                .setService(PriceCheckJobService.class)
                .setTag(PRICE_CHECK_JOB_TAG)
                .setTrigger(Trigger.executionWindow(PRICE_CHECK_INTERVAL_TIME,
                        PRICE_CHECK_INTERVAL_TIME+PRICE_CHECK_FLEX_TIME))
                .build();
        mDispatcher.schedule(job);
        sInitialized = true;
    }

    synchronized public static void cancelPriceCheckReminder() {
        mDispatcher.cancel(PRICE_CHECK_JOB_TAG);
    }

    synchronized public static void cancelAllReminders() {
        mDispatcher.cancelAll();
    }

}
