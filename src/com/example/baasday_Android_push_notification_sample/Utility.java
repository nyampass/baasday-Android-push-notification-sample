package com.example.baasday_Android_push_notification_sample;

import com.baasday.AuthenticatedUser;
import com.baasday.BaasdayException;
import com.baasday.Device;

public class Utility {
    public static final String senderId = "1068616019150";

    public static void sendRegistrationId(final String registrationId) {
        try {
            final AuthenticatedUser user = AuthenticatedUser.fetch();
            final Device device = user.getCurrentDevice();
            device.setRegistrationId(registrationId);
            user.updateDevice(device);
        } catch (final BaasdayException exception) {
            exception.printStackTrace();
        }
    }
}
