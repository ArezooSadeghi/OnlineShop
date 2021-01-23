package com.example.onlineshop.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;
import android.util.Log;

import com.example.onlineshop.model.Product;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.utilities.Preferences;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class PollService extends IntentService {
    private static final String TAG = PollService.class.getSimpleName();


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
            Log.d(TAG, "show notification");
            //TODO:show notification
        } else {
            Log.d(TAG, "nothing new");
        }

        Preferences.setLastId(this, serverId);
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

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }
}