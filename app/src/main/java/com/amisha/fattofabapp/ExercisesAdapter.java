package com.amisha.fattofabapp;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ExercisesAdapter extends ArrayAdapter {

    private List<Exercise> list=new ArrayList<>();


    public ExercisesAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(List<Exercise> object) {
        super.add(object);
        list.addAll(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        view=convertView;
        final ExerciseHolder exerciseHolder;
        if(view==null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.exerciseslayout,parent,false);
            exerciseHolder=new ExerciseHolder();
            exerciseHolder.textView1= (TextView) view.findViewById(R.id.exerciseTitle);
            exerciseHolder.textView2= (TextView) view.findViewById(R.id.exerciseDesc);
            exerciseHolder.button=(Button)view.findViewById(R.id.button4);
            view.setTag(exerciseHolder);
        } else {
            exerciseHolder= (ExerciseHolder) view.getTag();
        }
        final Exercise exercise= (Exercise) this.getItem(position);
        exerciseHolder.textView1.setText(exercise.getName());
        exerciseHolder.textView2.setText(Html.fromHtml(exercise.getDescription()));

        exerciseHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (exerciseHolder.button.getText().equals("FAVORITE")) {
                        exerciseHolder.button.setText("UNFAVORITE");
                        exerciseHolder.button.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

                        ContentValues contentValues = new ContentValues();
                        contentValues.put(ExerciseProvider.NAME, exercise.name);
                        contentValues.put(ExerciseProvider.DESCRIPTION, exercise.description);
                        getContext().getContentResolver().insert(ExerciseProvider.CONTENT_URI, contentValues);
                    } else {
                        exerciseHolder.button.setText("FAVORITE");
                        exerciseHolder.button.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY);
                        getContext().getContentResolver().delete(Uri.parse("content://com.example.provider.Exercises/exercises"), "name=?"
                                , new String[]{exercise.name});
                    }
                } catch(NullPointerException e){
                }
            }
        });
        return view;
    }

    static class ExerciseHolder{
        TextView textView1,textView2;
        Button button;
    }
}
