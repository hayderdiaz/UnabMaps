package com.example.hayder.unabmaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;


// classes needed to initialize map

// classes needed to add location layer
import android.location.Location;
import android.support.annotation.NonNull;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationLayerPlugin;
    private LocationEngine locationEngine;
    private Location originLocation;

    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            // Create an instance of the plugin. Adding in LocationLayerOptions is also an optional
            // parameter
            LocationLayerPlugin locationLayerPlugin = new LocationLayerPlugin(mapView, mapboxMap);

            // Set the plugin's camera mode
            locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
            getLifecycle().addObserver(locationLayerPlugin);
        } else {
            permissionsManager = new PermissionsManager((PermissionsListener) this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void initializeLocationEngine() {
        LocationEngineProvider locationEngineProvider = new LocationEngineProvider(this);
        locationEngine = locationEngineProvider.obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
        } else {
            locationEngine.addLocationEngineListener((LocationEngineListener) this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiaHJvZHJpZ3VlejEyMCIsImEiOiJjamxucTU1eW8xbGp3M3FrbXBhMnI2Mm9sIn0.woHSauubBi9JFWsH4ZZy2w");
        setContentView(R.layout.activity_main);

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        //añadir marcadores al mapa
        ArrayList<MarkerOptions> marcadores = new ArrayList<MarkerOptions>();
        marcadores.add(new MarkerOptions().position(new LatLng(7.117275,-73.105055)).title("Edificio N"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116919,-73.105435)).title("Banco de Bogota"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116929,-73.105445)).title("Unab Creative"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116212,-73.105396)).title("Edificio L"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.115847,-73.10561)).title("Edificio k"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.115831,-73.105481)).title("Edificio J"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.115847,-73.105266)).title("Edificio I"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.11585,-73.105105)).title("Edificio H"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116382,-73.104645)).title("Biblioteca UNAB"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116116,-73.105325)).title("Auditorio Gomez"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116484,-73.105209)).title("Edificio E"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116394,-73.105057)).title("Edificio F"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.117097,-73.104923)).title("Edificio D"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.117004,-73.104835)).title("Edificio Administrativo"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116857,-73.104834)).title("Auditorio Mayor"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.11617,-73.104913)).title("Edificio G"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116482,-73.105365)).title("Cafeteria"));

       //Agrego los marcadores al mapa
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                for (int i = 0; i<marcadores.size();i++) {
                    mapboxMap.addMarker(marcadores.get(i));
                }
            }
        });

        //Buscador
        Button buscar = findViewById(R.id.buttonBuscar);
        EditText campo = findViewById(R.id.campoBusqueda);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cambio = new Intent(MainActivity.this,Otra.class);
                startActivity(cambio);

                for(int i =0;i<marcadores.size();i++){
                    if(marcadores.get(i).getTitle().equals(campo.getText().toString())){

                    }
                }
            }
        });

    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStart();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStart();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    //public void onConnected() {

    //}


    //public void onLocationChanged(Location location) {

    //}


   // public void onMapReady(MapboxMap mapboxMap) {
   //   this.mapboxMap = mapboxMap;
   //     enableLocationPlugin();
   // }
}






