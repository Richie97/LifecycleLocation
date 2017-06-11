package com.richieapps.lifecyclelocation;

import android.arch.lifecycle.LiveData;
import android.location.Location;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

@SuppressWarnings("MissingPermission")
public class LocationLiveData extends LiveData<Location> {
    private FusedLocationProviderClient client;
    private LocationCallback callback;

    @Override
    protected void onActive() {
        super.onActive();
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                setValue(locationResult.getLastLocation());
            }
        };
        LocationRequest request = new LocationRequest();
        client = new FusedLocationProviderClient(App.getApp());
        client.requestLocationUpdates(request, callback, Looper.getMainLooper());
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        client.removeLocationUpdates(callback);
        client = null;
    }
}
