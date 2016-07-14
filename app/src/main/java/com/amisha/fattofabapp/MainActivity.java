package com.amisha.fattofabapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    String Age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Age=parent.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager=getSupportFragmentManager();
                MyDialog myDialog=new MyDialog();
                myDialog.show(fragmentManager,"MyDialog");
            }
        });
    }



    boolean Gender;
    public void onRadioButtonClick(View view)
    {
        boolean checked=((RadioButton)view).isChecked();
        switch (view.getId()){
            case R.id.radioButton1:
                if(checked)
                {
                  Gender=true;
                }
                break;
            case R.id.radioButton2:
                if(checked)
                {
                    Gender=false;
                }
                break;
        }
    }


    RelativeLayout relativeLayout;
    public void onButtonClick(View view)
    {
       try {
           EditText editText1 = (EditText) findViewById(R.id.editText1);
           EditText editText2 = (EditText) findViewById(R.id.editText2);
           EditText editText3 = (EditText) findViewById(R.id.editText3);

           //Hiding Virtual Keyboard
           relativeLayout=(RelativeLayout)findViewById(R.id.relativeLayout);
           try {
               InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
           } catch (Exception e){
               System.out.println(e);
           }


           TextView textView = (TextView) findViewById(R.id.resultTextView);
           float num1 = (float) Double.parseDouble(editText1.getText().toString());
           float num2 = (float) Double.parseDouble(editText2.getText().toString());
           float num4 = (float) Double.parseDouble(editText3.getText().toString());
           float result1 = (num2 / (num1 * num1));
           double result2 = (num4 / (num1 * Math.sqrt(num1))) - 18;
           DecimalFormat formatter=new DecimalFormat("0.00");
           String formatted1=formatter.format(result1);
           String formatted2=formatter.format(result2);
           textView.setText("Your BMI is:" + " " + formatted1 + " " + "and" + " " + "Your BAI is:" + " " + formatted2);

           TextView textView1 = (TextView) findViewById(R.id.resultTextVieww);

           if(Gender) {
               if (Age.equals("20-39")) {
                   if (result2 < 8)
                       textView1.setText(R.string.underweight);
                   else if (result2 >= 8 && result2 < 21)
                       textView1.setText(R.string.healthy);
                   else if (result2 >= 21 && result2 < 26)
                       textView1.setText(R.string.overweight);
                   else if (result2 >= 26)
                       textView1.setText(R.string.obese);
               } else if (Age.equals("40-59")) {
                   if (result2 < 11)
                       textView1.setText(R.string.underweight);
                   else if (result2 >= 11 && result2 < 23)
                       textView1.setText(R.string.healthy);
                   else if (result2 >= 23 && result2 < 29)
                       textView1.setText(R.string.overweight);
                   else if (result2 >= 29)
                       textView1.setText(R.string.obese);
               } else if (Age.equals("60-79")) {
                   if (result2 < 13)
                       textView1.setText(R.string.underweight);
                   else if (result2 >= 13 && result2 < 25)
                       textView1.setText(R.string.healthy);
                   else if (result2 >= 25 && result2 < 31)
                       textView1.setText(R.string.overweight);
                   else if (result2 >= 31)
                       textView1.setText(R.string.obese);
               }
           }
               else  {
                   if (Age.equals("20-39")) {
                       if (result2 < 21)
                           textView1.setText(R.string.underweight);
                       else if (result2 >= 21 && result2 < 33)
                           textView1.setText(R.string.healthy);
                       else if (result2 >= 33 && result2 < 39)
                           textView1.setText(R.string.overweight);
                       else if (result2 >= 39)
                           textView1.setText(R.string.obese);
                   }
                   else if (Age.equals("40-59")) {
                       if (result2 < 23)
                           textView1.setText(R.string.underweight);
                       else if (result2 >= 23 && result2 < 35)
                           textView1.setText(R.string.healthy);
                       else if (result2 >= 35 && result2 < 41)
                           textView1.setText(R.string.overweight);
                       else if (result2 >= 41)
                           textView1.setText(R.string.obese);
                   }
                   else if (Age.equals("60-79")) {
                       if (result2 < 25)
                           textView1.setText(R.string.underweight);
                       else if (result2 >= 25 && result2 < 38)
                           textView1.setText(R.string.healthy);
                       else if (result2 >= 38 && result2 < 43)
                           textView1.setText(R.string.overweight);
                       else if (result2 >= 43)
                           textView1.setText(R.string.obese);
                   }
               }
       } catch (NumberFormatException n){
           System.out.print(n);
       }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
