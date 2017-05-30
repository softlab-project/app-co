package it.softlab.training.app_oco.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ConnectionBroadcastReceiver extends BroadcastReceiver {
private static final String TAG = "ConnectionBroadcastR";
    @Override
    public void onReceive(Context context, Intent intent) {

         Toast.makeText(context,"ConnectionBroadcastReceiver!!",Toast.LENGTH_LONG).show();
        Log.d(TAG, "onReceive: ");

    }
}
