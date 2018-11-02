package com.example.hayder.unabmaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.location.Location;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

// classes needed to add location layer
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener, PermissionsListener {

    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationLayerPlugin locationLayerPlugin;
    private LocationEngine locationEngine;
    private Location originLocation;

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
        marcadores.add(new MarkerOptions().position(new LatLng(7.117097,-73.105351)).title("Unab Creative"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116212,-73.105396)).title("Edificio L"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.115847,-73.10561)).title("Edificio K"));
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

        mapView.getMapAsync(this);
        //IconFactory iconFactory= IconFactory.getInstance(MainActivity.this);
        //Icon icon = iconFactory.fromResource(R.drawable.map_marker_light);

        //Agrego los marcadores al mapa
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                for (int i = 0; i<marcadores.size();i++) {
                    map.addMarker(marcadores.get(i));
                }
                    mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(@NonNull Marker marker) {
                            Toast.makeText(MainActivity.this,"Marcador pulsado:\n" +
                                    marker.getTitle(),Toast.LENGTH_LONG).show();
                            Intent cambio = new Intent(MainActivity.this,Otra.class);
                            cambio.putExtra("nombre",marker.getTitle());
                            startActivity(cambio);
                            return false;
                        }
                    });

            }
        });

         //Buscador
        Button buscar = findViewById(R.id.buttonBuscar);
        EditText campo = findViewById(R.id.campoBusqueda);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean encontro =false;
                for(int i =0;i<marcadores.size();i++){
                    if(marcadores.get(i).getTitle().equals(campo.getText().toString())){
                        map.addMarker(marcadores.get(i).icon(IconFactory.getInstance(getApplicationContext()).fromResource(R.drawable.map_marker_light)));
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marcadores.get(i).getPosition().getLatitude(),
                                marcadores.get(i).getPosition().getLongitude()),50.0));
                    encontro=true;
                    }
                }
                if(!encontro){
                    Toast.makeText(MainActivity.this,"no encontrado",Toast.LENGTH_SHORT).show();
                }
            }
        });


        //Buscador Avanzado
        //Button buscar1 = findViewById(R.id.buttonBuscar1);
        //final AutoCompleteTextView campoBusqAvan = findViewById(R.id.campoBusqAvan);
        //campoBusqAvan.setThreshold(2);
        //ArrayAdapter<MarkerOptions> adapter = new ArrayAdapter<MarkerOptions>(this, android.R.layout.simple_dropdown_item_1line, marcadores);
        //campoBusqAvan.setAdapter(adapter);
    }

//-----------------------------------------------------------------------------------------------------------------------------
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        enableLocation();
    }

    private void enableLocation(){
        if (PermissionsManager.areLocationPermissionsGranted(this)){
            initializeLocationEngine();
            initializeLocationLayer();
        }else{
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void initializeLocationEngine() {

        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
            setCameraPosition(lastLocation);
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    @SuppressWarnings( {"MissingPermission"})
    private void initializeLocationLayer(){
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }

    private void setCameraPosition(Location location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()),15.0));
    }

    @Override
    @SuppressWarnings( {"MissingPermission"})
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location!= null){
            originLocation = location;
            setCameraPosition(location);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        //present toast or dialog

    }

    @Override
    public void onPermissionResult(boolean granted) {
        if(granted){
        enableLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    @SuppressWarnings( {"MissingPermission"})
    @Override
    protected void onStart() {
        super.onStart();
        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStart();
        }
        mapView.onStart();

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
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }

}






