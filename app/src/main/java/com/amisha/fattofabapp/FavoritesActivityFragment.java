package com.amisha.fattofabapp;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FavoritesActivityFragment extends Fragment {

    private boolean favorite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceBundle){
        View rootView=inflater.inflate(R.layout.exerciseslayout,container,false);

        Intent intent=getActivity().getIntent();
        getActivity().setTitle("Favorites");

        if(intent!=null && intent.hasExtra("favorite")){
            favorite=intent.getBooleanExtra("favorite",false);
            Button b=(Button)rootView.findViewById(R.id.button4);
            if(favorite)
            {
                b.setText("FAVORITE");
            }
            else if(!favorite)
            {
                b.setText("UNFAVORITE");
            }

            if(b.getText().equals("FAVORITE")) {
                b.setText("UNFAVORITE");
                b.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            }
            else {
                b.setText("FAVORITE");
                b.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
            }
        }

        if(intent!=null && intent.hasExtra("name")){
            String name = intent.getStringExtra("name");
            TextView textView=(TextView)rootView.findViewById(R.id.exerciseTitle);
            textView.setText(name);

        }
        return rootView;
    }
}
