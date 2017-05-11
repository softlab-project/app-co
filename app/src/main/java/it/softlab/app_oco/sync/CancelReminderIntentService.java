package it.softlab.app_oco.sync;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import it.softlab.app_oco.utilities.ReminderUtils;

public class CancelReminderIntentService extends IntentService {

    private static final String TAG = "CancelReminderIntentS";

    public CancelReminderIntentService() {
        super("CancelReminderIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: cancel");

        Context context = CancelReminderIntentService.this;
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();


        ReminderUtils.cancelPriceCheckReminder();

    }
}
