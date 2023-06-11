package com.celinski.weatherapp.data.gps

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.celinski.weatherapp.domain.gps.GpsTracker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class GpsTrackerImpl @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application,
) : GpsTracker {

    override suspend fun getCurrentLocation(): Location? {
        //sprawdzenie posiadanych uprawnien do gps przez aplikacje
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        //sprawdzenie czy gps jest wlaczony
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled) {
            return null
        }

        // asynchroniczne wywołanie locationClient.lastLocation w celu pobrania ostatniej znanej lokalizacji urządzenia
        //za pomoca korutyny
        return suspendCancellableCoroutine { cont ->
            val locationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                interval = 1000 // aktualizacja co 1 sekundę (można dostosować)
            }
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    locationResult.let {
                        if (!cont.isCancelled) {
                            cont.resume(it.lastLocation)
                        }
                        locationClient.removeLocationUpdates(this)
                    }
                }

                override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                    if (!locationAvailability.isLocationAvailable) {
                        cont.resume(null)
                    }
                }
            }
            locationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            cont.invokeOnCancellation {
                locationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}