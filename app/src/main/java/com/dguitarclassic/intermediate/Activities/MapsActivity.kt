package com.dguitarclassic.intermediate.Activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dguitarclassic.intermediate.Model.MainViewModel
import com.dguitarclassic.intermediate.R
import com.dguitarclassic.intermediate.Preferences.AuthPref
import com.dguitarclassic.intermediate.ViewModelFactory
import com.dguitarclassic.intermediate.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = getString(R.string.stories_location)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(AuthPref.getInstance(dataStore))
        )[MainViewModel::class.java]

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        var token =""
        mainViewModel.getUser().observe(
            this
        ){
            token = it.token
            getStoriesResponse(token)
        }

        mainViewModel.list.observe(this){
            if (it!= null) {
                it.listStory?.forEach { data ->
                    if (data != null) {
                        if (data.lat != null || data.lon != null){
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(LatLng(data.lat as Double, data.lon as Double ))
                                    .title("user : ${data.name}")
                            )
                        }
                    }
                }
            }
        }
    }

    fun getStoriesResponse(token:String){
        mainViewModel.getListStoriesWithLocation(token)
    }
}