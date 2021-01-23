package com.example.onlineshop.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.navigation.NavDeepLinkBuilder;

import com.example.onlineshop.R;
import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.utilities.Preferences;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PollService extends IntentService {
    private static final String TAG = PollService.class.getSimpleName();
    public static final String BUNDLE_LAST_ID = "lastId";


    public PollService() {
        super("PollService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: " + intent);

        if (!isNetworkAvailableAndConnected()) {
            Log.d(TAG, "Network not available");
            return;
        }

        ProductRepository repository = ProductRepository.getInstance(this);
        List<Product> products = repository.getProducts();

        if (products == null || products.size() == 0) {
            Log.d(TAG, "Products from server not fetched");
            return;
        }

        int serverId = products.get(0).getId();
        int lastSavedId = Preferences.getLastId(this);

        if (serverId != lastSavedId) {
            Preferences.setLastId(this, serverId);
            Log.d(TAG, "show notification");
            createAndShowNotification();
        } else {
            Preferences.setLastId(this, serverId);
            Log.d(TAG, "nothing new");
        }
    }


    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() != null &
                connectivityManager.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    public static void scheduleAlarm(Context context, boolean isOn) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent operation = getAlarmPendingIntent(context, 0);

        if (isOn) {
            alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(),
                    TimeUnit.MINUTES.toMillis(1),
                    operation);
        } else {
            alarmManager.cancel(operation);
            operation.cancel();
        }
    }


    public static boolean isAlarmSet(Context context) {
        PendingIntent operation = getAlarmPendingIntent(context, PendingIntent.FLAG_NO_CREATE);
        return operation != null;
    }

    private static PendingIntent getAlarmPendingIntent(Context context, int flags) {
        Intent intent = newIntent(context);
        return PendingIntent.getService(context, 0, intent, flags);
    }

    private void createAndShowNotification() {
        Bundle bundle = new Bundle();
        bundle.putInt(BUNDLE_LAST_ID, Preferences.getLastId(this));
        PendingIntent pendingIntent = new NavDeepLinkBuilder(this)
                .setGraph(R.navigation.mobile_navigation)
                .setDestination(R.id.detailFragment)
                .setArguments(bundle)
                .createPendingIntent();

        String channelId = getString(R.string.channel_id);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(getString(R.string.new_product_text))
                .setContentText(getString(R.string.new_product_text))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, notification);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }
}