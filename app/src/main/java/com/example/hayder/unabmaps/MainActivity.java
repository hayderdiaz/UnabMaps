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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiaHJvZHJpZ3VlejEyMCIsImEiOiJjamxucTU1eW8xbGp3M3FrbXBhMnI2Mm9sIn0.woHSauubBi9JFWsH4ZZy2w");
        setContentView(R.layout.activity_main);

        ArrayList<MarkerOptions> marcadores = new ArrayList<MarkerOptions>();
        marcadores.add(new MarkerOptions().position(new LatLng(7.117275,-73.105055)).title("Edificio N"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116919,-73.105435)).title("Banco de Bogota"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116212,-73.105396)).title("Edificio L"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.115847,-73.10561)).title("Edificio k"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.115831,-73.105481)).title("Edificio J"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.115847,-73.105266)).title("Edificio I"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.11585,-73.105105)).title("Edificio H"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116382,-73.104645)).title("Biblioteca"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116116,-73.105325)).title("Auditorio Gomez"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116484,-73.105209)).title("Edificio E"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116394,-73.105057)).title("Edificio F"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.117097,-73.104923)).title("Edificio D"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.117004,-73.104835)).title("Edificio Administrativo"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116857,-73.104834)).title("Auditorio Mayor"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.11617,-73.104913)).title("Edificio G"));
        marcadores.add(new MarkerOptions().position(new LatLng(7.116482,-73.105365)).title("Cafeteria"));


        //hellon word hola mundo asas

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

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);



        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {


                for (int i = 0; i<marcadores.size();i++) {
                    mapboxMap.addMarker(marcadores.get(i));
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}




