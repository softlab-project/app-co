package it.softlab.app_oco.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import it.softlab.app_oco.DetailActivity;
import it.softlab.app_oco.R;
import it.softlab.app_oco.model.Product;

/**
 * Created by claudio on 5/11/17.
 */

public class NotificationUtils {

    private static int PRICE_CHANGED_PENDING_INTENT_ID = 1983;
    private static int PRICE_CHANGED_NOTIFICATION_ID = 2017;

    public static void priceChangedNotification(Context context, Product product) {

        Intent intent = new Intent(context,DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailActivity.KEY_DATA,product);
        intent.putExtras(bundle);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                PRICE_CHANGED_PENDING_INTENT_ID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

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
