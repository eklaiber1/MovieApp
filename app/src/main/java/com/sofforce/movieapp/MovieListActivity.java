package com.sofforce.movieapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    ArrayList<MovieStat> arrayList;
    GridView theGridview;

    //comment001 this is the onCreate method that initiate the program
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        arrayList = new ArrayList<>();
        theGridview = (GridView) findViewById(R.id.movieListgrid);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new  ReadJSON().execute("http://api.themoviedb.org/3/movie/popular?api_key=");
            }
        });

        arrayList = new ArrayList<MovieStat>();
        theGridview.setAdapter(new ArrayAdapter<CustomListAdapter02>(
                this, R.layout.detailedview, arrayList
        ));

        //comment001.1 this will send you to the detailedview when you click on a image
        theGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedItem = adapterView.getItemAtPosition(i).toString();

                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                view = layoutInflater.inflate(R.layout.detailedview, null, true);

            }
        });





    }


    //comment002 this is to inflate the menu on the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //comment003 this is the method that runs when you click on an item in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mostPopular:

                new  ReadJSON().execute("http://api.themoviedb.org/3/movie/popular?api_key=");

                return true;

            case R.id.action_toprated:

                new  ReadJSON().execute("http://api.themoviedb.org/3/movie/top_rated?api_key=");

                return true;
        }
        return super.onOptionsItemSelected(item);
    }





    //comment004 this is the JSON that will parse the data
    class ReadJSON extends AsyncTask<String, Integer, String>{



        @Override
        protected String doInBackground(String... params) {
            return readsURL(params[0]);
        }


        @Override
        protected void onPostExecute(String content) {

            arrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("results");

                for (int i=0; i<jsonArray.length(); i++) {
                     JSONObject detailedObjects = jsonArray.getJSONObject(i);
                     arrayList.add(new MovieStat(detailedObjects.getString("poster_path")
                             ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapter adapter = new CustomListAdapter(
                    getApplicationContext(), R.id.movieListgrid, arrayList
            );
            theGridview.setAdapter(adapter);
        }

    }

    //comment005 this is the JSON that will parse the data
//    class ReadJSON02 extends AsyncTask<String, Integer, String>{
//
//        @Override
//        protected String doInBackground(String... params) {
//            return readsURL(params[0]);
//        }
//
//
//        @Override
//        protected void onPostExecute(String content) {
//
//            try {
//                JSONObject jsonObject = new JSONObject(content);
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//
//                for (int i=0; i<jsonArray.length(); i++) {
//                    JSONObject detailedObjects = jsonArray.getJSONObject(i);
//                    arrayList.add(new MovieStat(detailedObjects.getString("poster_path")
//                    ));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            CustomListAdapter02 adapter02 = new CustomListAdapter02(
//                    getApplicationContext(), R.id.movieListgrid, arrayList
//            );
//            theGridview.setAdapter(adapter02);
//        }
//
//    }




    //comment006 this is the uri builder and the connection to the URL
    private static String readsURL(String theUrl){
        StringBuilder content =  new StringBuilder();

        try {
            URL url  = new URL(theUrl);
            URLConnection urlConnection =  url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while((line = bufferedReader.readLine()) !=null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
         return content.toString();

    }








}