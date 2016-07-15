package com.amisha.fattofabapp;


import android.content.Context;
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
        ExerciseHolder exerciseHolder;
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
        Exercise exercise= (Exercise) this.getItem(position);
        exerciseHolder.textView1.setText(exercise.getName());
        exerciseHolder.textView2.setText(Html.fromHtml(exercise.getDescription()));
        return view;
    }

    static class ExerciseHolder{
        TextView textView1,textView2;
        Button button;
    }
}
