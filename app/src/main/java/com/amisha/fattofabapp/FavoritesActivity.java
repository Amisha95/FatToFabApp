package com.amisha.fattofabapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class FavoritesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment3, new FavoritesActivityFragment()).commit();
        }
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
    }
}
