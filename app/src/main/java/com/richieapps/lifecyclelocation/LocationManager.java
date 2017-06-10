package com.richieapps.lifecyclelocation;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.location.Location;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

@SuppressWarnings("MissingPermission")
public class LocationManager implements LifecycleObserver {
    private MutableLiveData<Location> location = new MutableLiveData<>();
    private FusedLocationProviderClient client;
    private LocationCallback callback;

    public LocationManager(LifecycleOwner owner, Observer<Location> observer) {
        owner.getLifecycle().addObserver(this);
        location.observe(owner, observer);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        callback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                location.setValue(locationResult.getLastLocation());
            }
        };
        LocationRequest request = new LocationRequest();
        client = new FusedLocationProviderClient(App.getApp());
        client.requestLocationUpdates(request, callback, Looper.getMainLooper());
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        client.removeLocationUpdates(callback);
        client = null;
    }


}
