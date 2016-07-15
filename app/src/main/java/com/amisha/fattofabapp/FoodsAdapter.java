package com.amisha.fattofabapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodsAdapter extends ArrayAdapter {

    private List<Food> list=new ArrayList<>();


    public FoodsAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(List<Food> object) {
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
        FoodHolder foodHolder;
        if(view==null){
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.foodslayout,parent,false);
            foodHolder=new FoodHolder();
            foodHolder.textView1= (TextView) view.findViewById(R.id.foodTitle);
            view.setTag(foodHolder);
        } else {
            foodHolder= (FoodHolder) view.getTag();
        }
        Food food= (Food) this.getItem(position);
        foodHolder.textView1.setText(food.getName());
        return view;
    }

    static class FoodHolder{
        TextView textView1;
    }
}
