package com.rizal.pegawaique.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.rizal.pegawaique.SetMapsActivity;
import com.rizal.pegawaique.helper.NotificationHelper;

import java.util.List;
import java.util.Locale;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "GeofenceBroadcastReceiv";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence Triggered...", Toast.LENGTH_SHORT).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()){
            Log.d(TAG, "onReceive: Error Receiving Geofence Event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence: geofenceList){
            Log.d(TAG, "onReceive: "+ geofence.getRequestId());
        }
//        Location location = geofencingEvent.getTriggeringLocation();
        int transitionType = geofencingEvent.getGeofenceTransition();

        switch (transitionType){
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                Toast.makeText(context, "Anda Sudah Memasuki Zona Kantor", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Anda Sudah Memasuki Zona Kantor", "", SetMapsActivity.class);
            case Geofence.GEOFENCE_TRANSITION_DWELL:
                Toast.makeText(context, "Anda Berada Di Zona Kantor", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Anda Berada Di Zona Kantor", "", SetMapsActivity.class);
            case Geofence.GEOFENCE_TRANSITION_EXIT:
                Toast.makeText(context, "Anda Sudah Keluar Dari Zona Kantor", Toast.LENGTH_SHORT).show();
                notificationHelper.sendHighPriorityNotification("Anda Sudah Keluar Dari Zona Kantor", "", SetMapsActivity.class);
        }
    }
}
