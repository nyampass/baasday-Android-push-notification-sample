package com.example.baasday_Android_push_notification_sample;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import com.baasday.AuthenticatedUser;
import com.baasday.Baasday;
import com.baasday.BaasdayException;
import com.baasday.Device;
import com.google.android.gcm.GCMRegistrar;

public class MyActivity extends Activity {
    private void setupBaasday() {
        try {
            Baasday.setup("APPLICATION ID", "API KEY");
            final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String userAuthenticationKey = preferences.getString("baasdayUserAuthenticationKey", null);
            if (userAuthenticationKey == null) {
                final AuthenticatedUser user = AuthenticatedUser.create();
                preferences.edit().putString("baasdayUserAuthenticationKey", user.getAuthenticationKey()).commit();
                userAuthenticationKey = user.getAuthenticationKey();
            }
            Baasday.setUserAuthenticationKey(userAuthenticationKey);
            String deviceId = preferences.getString("baasdayDeviceId", null);
            if (deviceId == null) {
                deviceId = Device.generateDeviceid();
                preferences.edit().putString("baasdayDeviceId", deviceId).commit();
            }
            Baasday.setDeviceId(deviceId);
        } catch (final BaasdayException exception) {
            Log.e("baasday", "exception in setupBaasday", exception);
        }
    }

    private void setupGCM() {
        final String senderId = "1068616019150";
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        String registrationId = GCMRegistrar.getRegistrationId(this);
        if (registrationId == null || "".equals(registrationId)) {
            GCMRegistrar.register(this, senderId);
        } else {
            Utility.sendRegistrationId(registrationId);
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        new Thread(new Runnable() {
            public void run() {
                MyActivity.this.setupBaasday();
                MyActivity.this.setupGCM();
            }
        }).start();
    }
}
