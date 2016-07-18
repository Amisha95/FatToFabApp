package com.amisha.fattofabapp;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class ExercisesActivity extends AppCompatActivity {

    static ArrayList<String> nameFavorites,descFavorites;
    ListView listView;

    static ArrayList<Boolean> favorite;
    ExercisesAdapter exercisesAdapter;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        exercisesAdapter=new ExercisesAdapter(this,R.layout.exerciseslayout);
        listView=(ListView)findViewById(R.id.listView);

        ExerciseItemsLoadTask exerciseItemsLoadTask=new ExerciseItemsLoadTask();
        exerciseItemsLoadTask.execute();

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
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
            loadFavorites();
            favorite=bindExercisesToFavorites();

            Intent intent=new Intent(this,FavoritesActivity.class).putExtra("favorite",favorite)
                    .putExtra("name",nameFavorites).putExtra("desc",descFavorites);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Boolean> bindExercisesToFavorites(){
        ArrayList<Boolean> result=new ArrayList<Boolean>();
        for(int i=0;i<exerciseList.size();i++){
            result.add(false);
        }
        for(String favoriteNames: nameFavorites){
            for(int i=0;i<exerciseList.size();i++){
                if(favoriteNames.equals(exerciseList.get(i))){
                    result.set(i,true);
                }
            }
        }
        return result;
    }

    public void loadFavorites(){
        Cursor cursor=null;
        try{
            String URL="content://com.example.provider.Exercises/exercises";
            Uri favorites=Uri.parse(URL);
            cursor=getApplicationContext().getContentResolver().query(favorites,null,null,null,"name");
            nameFavorites=new ArrayList<String>();
            descFavorites=new ArrayList<String>();
            favorite=new ArrayList<Boolean>();
            if(cursor==null)
                return;
            while(cursor.moveToNext()){
                nameFavorites.add(cursor.getString(cursor.getColumnIndex(ExerciseProvider.NAME)));
                descFavorites.add(cursor.getString(cursor.getColumnIndex(ExerciseProvider.DESCRIPTION)));
                favorite.add(true);
            }
        } finally {
            cursor.close();
        }
    }

    public static ArrayList<Exercise> exercisesList;

    public class ExerciseItemsLoadTask extends AsyncTask<Void, Void, ArrayList<Exercise>> {

        @Override
        protected ArrayList<Exercise> doInBackground(Void... params) {
            try {
                exercisesList = new ArrayList<>(getExercisesFromAPI());
            } catch (Exception e) {
            }
            return exercisesList;
        }

        protected void onPostExecute(ArrayList<Exercise> exercisesList) {

                listView.setAdapter(exercisesAdapter);
                exercisesAdapter.add(exercisesList);
                exercisesAdapter.notifyDataSetChanged();
        }
    }

    public ArrayList<Exercise> getExercisesFromAPI() throws IOException {


        if(getApplicationContext()!= null){
            HttpURLConnection connection=null;
            BufferedReader bufferedReader=null;
            String JSONResult;
            try{
                String URLString=null;
                URLString="https://wger.de/api/v2/exercise/?language=2&status=2";
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
                    return getExerciseNamesFromJSON(JSONResult);
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

    ArrayList<Exercise> exerciseList=new ArrayList<>();

    public ArrayList<Exercise> getExerciseNamesFromJSON(String JSONStringParam) throws JSONException {
        JSONObject JSONString=new JSONObject(JSONStringParam);
        JSONArray exerciseArray=JSONString.getJSONArray("results");

        for (int i = 0; i < exerciseArray.length(); i++) {
            JSONObject results = exerciseArray.getJSONObject(i);
            String nameItems=results.getString("name");
            String descriptionItems=results.getString("description");
            exerciseList.add(new Exercise(nameItems,descriptionItems));
        }

        return exerciseList;
    }
}

