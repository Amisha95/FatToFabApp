package com.amisha.fattofabapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


public class FoodsActivity extends AppCompatActivity
{
    ListView listView;
    FoodsAdapter foodAdapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foods);

        foodAdapter=new FoodsAdapter(this,R.layout.foodslayout);
        listView=(ListView)findViewById(R.id.listView1);

        FoodItemsLoadTask foodItemsLoadTask=new FoodItemsLoadTask();
        foodItemsLoadTask.execute();

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
    }


    public static ArrayList<Food> foodsList;
    String API_KEY = "vSexWLPRk0sks64ocUXcl60gxxf3UPkkzZEVnYby";

    public class FoodItemsLoadTask extends AsyncTask<Void, Void, ArrayList<Food>> {

        @Override
        protected ArrayList<Food> doInBackground(Void... params) {
            try {
                foodsList = new ArrayList<>(getFoodNamesFromAPI());
            } catch (Exception e) {
            }
            return foodsList;
        }

        protected void onPostExecute(ArrayList<Food> foodsList) {
            listView.setAdapter(foodAdapter);
            foodAdapter.add(foodsList);
            foodAdapter.notifyDataSetChanged();
        }
    }

    public ArrayList<Food> getFoodNamesFromAPI() throws IOException {


        if(getApplicationContext()!= null){
            HttpURLConnection connection=null;
            BufferedReader bufferedReader=null;
            String JSONResult;
            try{
                String URLString=null;
                URLString="http://api.nal.usda.gov/ndb/list?format=json&lt=f&offset=1000&sort=id&api_key="+API_KEY;
                URL url=new URL(URLString);
                connection= (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                InputStream inputStream=connection.getInputStream();
                if(inputStream==null){
                    return null;
                }
                bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuffer stringBuffer=new StringBuffer();
                String line;
                while((line=bufferedReader.readLine())!=null){
                    stringBuffer.append(line+"\n");
                }
                JSONResult=stringBuffer.toString();

                try{
                    return getFoodNamesFromJSON(JSONResult);
                } catch (JSONException e){
                    return null;
                }
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection!=null){
                    connection.disconnect();
                } if(bufferedReader!=null){
                    bufferedReader.close();
                }
            }
        }
        return null;
    }

    ArrayList<Food> foodList=new ArrayList<>();

    public ArrayList<Food> getFoodNamesFromJSON(String JSONStringParam) throws JSONException {
        JSONObject JSONString=new JSONObject(JSONStringParam);
        JSONArray foodsArray=JSONString.getJSONObject("list").getJSONArray("item");

        for (int i = 0; i < foodsArray.length(); i++) {
            JSONObject results = foodsArray.getJSONObject(i);
            String foodNames=results.getString("name");
            foodList.add(new Food(foodNames));
        }

        return foodList;
    }
}
