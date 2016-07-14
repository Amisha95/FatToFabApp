package com.amisha.fattofabapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyDialog extends DialogFragment implements View.OnClickListener
{
    Button Exercises,Foods;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.mydialog,null);
        Exercises=(Button)view.findViewById(R.id.button2);
        Exercises.setOnClickListener(this);
        Foods=(Button)view.findViewById(R.id.button3);
        Foods.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button2){
            Intent intent=new Intent(getContext(),ExercisesActivity.class);
            startActivity(intent);
        }

        else {
            Intent intent=new Intent(getContext(),FoodsActivity.class);
            startActivity(intent);
        }
    }
}
