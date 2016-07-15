package com.amisha.fattofabapp;


/*public class DisplayListView extends AppCompatActivity {

    ArrayList<String> jsonString;
    ExercisesAdapter exercisesAdapter;
    JSONObject jsonObject;
    ListView listView;
    JSONArray jsonArray;

    //different in github
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.displaylistview);
        jsonString = getIntent().getStringArrayListExtra("JSON_Data");
        try{
            jsonObject = new JSONObject(String.valueOf(jsonString));
            jsonArray=jsonObject.getJSONArray("results");
            int c=0;
            String name,desc;
            while (c<jsonObject.length()) {

                JSONObject jsonObject1=jsonArray.getJSONObject(c);
                name=jsonObject1.getString("name");
                desc=jsonObject1.getString("description");
                Exercise exercise=new Exercise(name,desc);
                exercisesAdapter.add(exercise);
                c++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        exercisesAdapter=new ExercisesAdapter(this,R.layout.exerciseslayout);
        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(exercisesAdapter);
        exercisesAdapter.add(ExercisesActivity.exercisesList);
    }
} */
