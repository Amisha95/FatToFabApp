package com.amisha.fattofabapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.util.Arrays;

public class FoodsActivityFragment extends Fragment {


    TextView textView;
    public static ArrayList<String> foodNames=new ArrayList<>();
    String API_KEY = "vSexWLPRk0sks64ocUXcl60gxxf3UPkkzZEVnYby";

    public FoodsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.foodslayout, container, false);

        FoodItemsLoadTask foodItemsLoadTask=new FoodItemsLoadTask();
        foodItemsLoadTask.execute();
        return rootView;
    }

    public class FoodItemsLoadTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
               foodNames = new ArrayList<>(Arrays.asList(getFoodNamesFromAPI()));
            } catch (Exception e) {
            }
            return foodNames;
        }

        protected void onPostExecute(ArrayList<String> foodNames) {

            for(int i=0;i<foodNames.size();i++) {
                textView = (TextView) getView().findViewById(R.id.foodTitle);
                textView.append(foodNames.get(i) + "\n");
            }
        }
    }

    public String[] getFoodNamesFromAPI() throws IOException {


        if(getActivity()!= null){
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


    public String[] getFoodNamesFromJSON(String JSONStringParam) throws JSONException {
        JSONObject JSONString=new JSONObject(JSONStringParam);
        JSONArray foodsArray=JSONString.getJSONObject("list").getJSONArray("item");
        String[] result=new String[foodsArray.length()];

            for (int i = 0; i < foodsArray.length(); i++) {
                JSONObject results = foodsArray.getJSONObject(i);
                String foodItems=results.getString("name");
                result[i]=foodItems;
            }

        return result;
    }

}