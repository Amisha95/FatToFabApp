package com.amisha.fattofabapp;


import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ExercisesAdapter extends ArrayAdapter {

    private List<Exercise> list=new List<Exercise>() {
        @Override
        public void add(int location, Exercise object) {
            list.add(object);
        }

        @Override
        public boolean add(Exercise object) {
            return true;
        }

        @Override
        public boolean addAll(int location, Collection<? extends Exercise> collection) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends Exercise> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean contains(Object object) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Exercise get(int location) {
            return null;
        }

        @Override
        public int indexOf(Object object) {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @NonNull
        @Override
        public Iterator<Exercise> iterator() {
            return null;
        }

        @Override
        public int lastIndexOf(Object object) {
            return 0;
        }

        @Override
        public ListIterator<Exercise> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<Exercise> listIterator(int location) {
            return null;
        }

        @Override
        public Exercise remove(int location) {
            return null;
        }

        @Override
        public boolean remove(Object object) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return false;
        }

        @Override
        public Exercise set(int location, Exercise object) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @NonNull
        @Override
        public List<Exercise> subList(int start, int end) {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(T[] array) {
            return null;
        }
    };


    public ExercisesAdapter(Context context, int resource) {
        super(context, resource);
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
