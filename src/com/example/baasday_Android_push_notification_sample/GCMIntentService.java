package com.example.baasday_Android_push_notification_sample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

    public GCMIntentService() {
        super(Utility.senderId);
    }

    protected void onRegistered(final Context context, final String registrationId) {
        Log.i("baasday", "registered");
        Utility.sendRegistrationId(registrationId);
    }

    protected void onUnregistered(Context context, String registrationId) {
    }

    protected void onMessage(Context context, Intent intent) {
        Log.i("baasday", "received: " + intent.getExtras().getString("message"));
    }

    @Override
    protected void onError(Context context, String s) {
        Log.e("baasday", s);
    }
}
