package it.softlab.app_oco.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import it.softlab.app_oco.sync.CancelReminderIntentService;
import it.softlab.app_oco.DetailActivity;
import it.softlab.app_oco.R;
import it.softlab.app_oco.model.Product;

/**
 * Created by claudio on 5/11/17.
 */

public class NotificationUtils {

    private static int PRICE_CHANGED_PENDING_INTENT_ID = 1983;
    private static int CANCEL_PENDING_INTENT_ID = 2980;
    private static int PRICE_CHANGED_NOTIFICATION_ID = 2017;

    public static void priceChangedNotification(Context context, Product product) {

        Intent checkPriceIntent = new Intent(context,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailActivity.KEY_DATA,product);
        checkPriceIntent.putExtras(bundle);

        PendingIntent checkPricePendingIntent = PendingIntent.getActivity(
                context,
                PRICE_CHANGED_PENDING_INTENT_ID,
                checkPriceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent cancelIntent = new Intent(context,CancelReminderIntentService.class);

        PendingIntent cancelPendingIntent = PendingIntent.getService(
                context,
                CANCEL_PENDING_INTENT_ID,
                cancelIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Action action = new Action(android.R.drawable.ic_delete,
                context.getString(R.string.notification_action_cancel),
                cancelPendingIntent);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(checkPricePendingIntent)
                .setAutoCancel(true)
                .addAction(action);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);

        notificationManagerCompat.notify(
                PRICE_CHANGED_NOTIFICATION_ID,
                notificationBuilder.build());

    }
}
