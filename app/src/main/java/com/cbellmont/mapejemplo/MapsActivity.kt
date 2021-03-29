package com.cbellmont.mapejemplo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cbellmont.mapejemplo.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // Sería el equivalente a poner
        // mapFragment.getMapAsync{
        // El código de onMapReady aquí
        //}
        if (BuildConfig.MAPS_API_KEY.isEmpty()){
            Snackbar.make(binding.root, "Debes poner tu MAPS_API_KEY en tu local.properties", Snackbar.LENGTH_INDEFINITE).show()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latLngMadrid = LatLng(40.4167, -3.70325)
        mMap.addMarker(MarkerOptions().position(latLngMadrid).title("Marker en Madrid"))
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLngMadrid)) Goes with default Zoom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngMadrid, 10F))

        mMap.setOnMarkerClickListener { marker ->
            Toast.makeText(this, "Se ha click sobre ${marker.title}", Toast.LENGTH_LONG).show()
            true
        }

        mMap.setOnMapClickListener { latLng ->
            // Esto hace que pongamos un marker en cualquier sitio que se pulse, normalmente se usaría junto a un popup de confirmación
            mMap.addMarker(MarkerOptions().position(latLng).title("Marker en ${latLng.latitude} ${latLng.longitude}"))
        }

    }
}