package com.amisha.fattofabapp;


import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ExercisesAdapter extends ArrayAdapter {

    private List<Exercise> list;


    public ExercisesAdapter(Context context, int resource,List<Exercise> list) {
        super(context, resource,list);
        this.list=list;
    }


    public void add(List<Exercise> object) {
        super.add(object);
        list.add((Exercise) object);
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
    }
}
