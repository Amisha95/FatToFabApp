package com.amisha.fattofabapp;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class DisplayListView extends AppCompatActivity {

    ArrayList<String> jsonString;
    ExercisesAdapter exercisesAdapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.displaylistview);
        jsonString = getIntent().getStringArrayListExtra("JSON_Data");
        exercisesAdapter=new ExercisesAdapter(this,R.layout.exerciseslayout);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(exercisesAdapter);
        exercisesAdapter.add(ExercisesActivity.exercisesList);
    }
}
