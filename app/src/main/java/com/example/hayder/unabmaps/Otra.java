package com.example.hayder.unabmaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Otra extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        ImageView img = findViewById(R.id.imageView);
        String llego = getIntent().getStringExtra("nombre");

        if(llego.equals("Edificio L")){
            img.setImageResource(R.drawable.img1);
        }
        if(llego.equals("Edificio N")){
            img.setImageResource(R.drawable.img2);
        }
        if(llego.equals("Biblioteca UNAB")){
            img.setImageResource(R.drawable.biblioteca);
        }
        if(llego.equals("Unab Creative")){
            img.setImageResource(R.drawable.unabcreative);
        }
        if(llego.equals("Edificio J")){
            img.setImageResource(R.drawable.img1);
        }
        if(llego.equals("Edificio F")){
            img.setImageResource(R.drawable.img1);
        }
        if(llego.equals("Edificio E")){
            img.setImageResource(R.drawable.img1);
        }
    }
}
