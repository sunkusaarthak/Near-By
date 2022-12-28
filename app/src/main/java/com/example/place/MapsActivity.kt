package com.example.place

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.place.Common.Common
import com.example.place.Model.MyPlace
import com.example.place.Remote.IGoogleAPIService
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.color.DynamicColors
import retrofit2.Call
import retrofit2.Response

@Suppress("DEPRECATION")
class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private lateinit var mServices: IGoogleAPIService
    internal lateinit var currentPlace: MyPlace
    private lateinit var button: Button
    private lateinit var placeText : TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var noSignal : Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DynamicColors.applyToActivitiesIfAvailable(application)
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }
        setContentView(R.layout.activity_maps)
        supportActionBar!!.hide()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mServices = Common.googleAPIService

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        button = findViewById(R.id.findButton)
        button.visibility = View.VISIBLE
        placeText = findViewById(R.id.searchKeyEditText)
        progressBar = findViewById(R.id.progressBar)
        button.setOnClickListener {
            if(lastKnownLocation == null)
            {
                Toast.makeText(baseContext, "Enable GPS", Toast.LENGTH_SHORT).show()
                Toast.makeText(baseContext,"Trying to Acquire Gps Signal", Toast.LENGTH_SHORT).show()
                noSignal = Toast.makeText(baseContext, "Failed to Acquire GPS Signal", Toast.LENGTH_SHORT)
                Thread {
                    Thread.sleep(5000)
                    getDeviceLocation()
                    callbackToast()
                }.start()
            }
            else if(placeText.text != null && placeText.text.toString() != "")
            {
               progressBar.visibility = View.VISIBLE
               button.visibility = View.GONE
               val place = placeText.text.toString()
               nearByPlace(place)
               placeText.onEditorAction(EditorInfo.IME_ACTION_DONE)
                placeText.clearFocus()
            }
            else
            {
                Toast.makeText(baseContext, "Enter Place Name", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun callbackToast() {
            Thread.sleep(3000)
            if(lastKnownLocation == null) {
                noSignal.show()
            }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        getLocationPermission()
    }

    private fun getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                val locationResult = fusedLocationProviderClient.lastLocation
                locationResult.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        lastKnownLocation = task.result
                        if (lastKnownLocation != null) {
                            map?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                LatLng(lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude), 15F))
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.")
                        Log.e(TAG, "Exception: %s", task.exception)
                        map?.moveCamera(
                            CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, 15F))
                        nearByPlace("restaurant")
                        map?.uiSettings?.isMyLocationButtonEnabled = false
                    }
                }
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
            updateLocationUI()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                    updateLocationUI()
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
                getDeviceLocation()
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun nearByPlace(typePlace: String) {
        if (locationPermissionGranted && lastKnownLocation!=null) {
            map?.clear()
            val url = getUrl(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude, typePlace)
            mServices.getNearByPlaces(url)
                .enqueue(object : retrofit2.Callback<MyPlace> {
                    override fun onResponse(call: Call<MyPlace>, response: Response<MyPlace>) {
                        currentPlace = response.body()!!
                        if (response.isSuccessful) {
                            val markerOptions = MarkerOptions()
                            for (i in 0 until response.body()!!.results!!.size) {
                                val googlePlace = response.body()!!.results!![i]
                                val lat = googlePlace.geometry!!.location!!.lat
                                val lan = googlePlace.geometry!!.location!!.lng
                                val placeName = googlePlace.name
                                val latLng = LatLng(lat!!, lan!!)
                                markerOptions.position(latLng)
                                markerOptions.title(placeName)
                                map!!.addMarker(markerOptions)
                            }
                            button.visibility = View.VISIBLE
                            progressBar.visibility = View.GONE
                            map!!.moveCamera(
                                CameraUpdateFactory.newLatLng(
                                    LatLng(
                                        lastKnownLocation!!.latitude,
                                        lastKnownLocation!!.longitude
                                    )
                                )
                            )
                            map!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
                        }
                    }

                    override fun onFailure(call: Call<MyPlace>, t: Throwable) {
                        Toast.makeText(baseContext, t.message, Toast.LENGTH_SHORT).show()
                        Log.d("Message", t.message.toString())
                        button.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                })
        }
    }

    private fun getUrl(latitide : Double, longitude : Double, typePlace: String) : String
    {
        val code = BuildConfig.API_KEY
        val googleUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googleUrl.append("?location=$latitide,$longitude")
        googleUrl.append("&radius=5000")
        googleUrl.append("&type=$typePlace")
        googleUrl.append("&keyword=cruise")
        googleUrl.append("&key=")
        googleUrl.append(code)
        return googleUrl.toString()
    }

    companion object {
        private val TAG = MapsActivity::class.java.simpleName
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            placeText.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onMarkerClick(p0: Marker): Boolean = false
}
