package com.sofforce.movieapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
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

                new  ReadJSON().execute("http://api.themoviedb.org/3/movie/popular?api_key="");
            }
        });



    }




    //this is to inflate the menu on the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //this is the JSON that will parse the data
    class ReadJSON extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            return readsURL(params[0]);
        }


        @Override
        protected void onPostExecute(String content) {

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
            CustomListAdapter02 adapter = new CustomListAdapter02(
                    getApplicationContext(), R.id.movieListgrid, arrayList
            );
            theGridview.setAdapter(adapter);
        }

    }




    //this is the uri builder and the connection to the URL
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