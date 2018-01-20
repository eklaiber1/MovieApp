package com.sofforce.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity implements HelperAsync.AsyncTaskCallback {

    ArrayList<MovieStat> arrayList;
    GridView theGridView = (GridView) findViewById(R.id.movieListgrid);
    ConnectionDetector cd =  new ConnectionDetector(this);

    HelperAsync helperAsync = new HelperAsync();

    private static final String API_KEY = BuildConfig.API_KEY;



    //comment001 this is the onCreate method that initiate the program
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        arrayList = new ArrayList<>();


        if (cd.isConnected()) {
                    Toast.makeText(MovieListActivity.this, "You are connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieListActivity.this, "You are not connected", Toast.LENGTH_SHORT).show();

                }
        try {

            loadData("http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        //comment002 this will send you to the detailedview when you click on a image
        theGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MovieStat movie = arrayList.get(i);

                Intent mIntent =  new Intent(MovieListActivity.this, DetailedActivity.class);
                mIntent.putExtra("title", movie.getTitle());
                mIntent.putExtra("release_date", movie.getReleaseDate());
                mIntent.putExtra("vote_average", String.valueOf(movie.getVoteAverage()));
                mIntent.putExtra("overview", movie.getOverview());
                mIntent.putExtra("poster_path", movie.getPosterPath());
                mIntent.putExtra("id", String.valueOf(movie.getIdNumber()));

                startActivity(mIntent);

            }
        });

    }

    private void loadData(String string){

        HelperAsync helperAsync = new HelperAsync();
        // here `this` refers to the container class. Hence your Activity/Fragment needs to implement its methods.
        // So press alt+enter and choose `Make Activity implement methods`
        helperAsync.setAsyncTaskCallback(this);

        helperAsync.execute(string + API_KEY);

        Log.d("checkingExecution", this.helperAsync.execute().toString());
    }


    //comment003 this is to inflate the menu on the actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }




    //comment004 this is the method that runs when you click on an item in the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_mostPopular:

                if (cd.isConnected()) {
                    Toast.makeText(MovieListActivity.this, "You are connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieListActivity.this, "You are not connected", Toast.LENGTH_SHORT).show();

                }

                helperAsync.execute("http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY);

                return true;

            case R.id.action_toprated:

                if (cd.isConnected()) {
                    Toast.makeText(MovieListActivity.this, "You are connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MovieListActivity.this, "You are not connected", Toast.LENGTH_SHORT).show();

                }

                helperAsync.execute("http://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void
    onProgressUpdate(String... values) {
    }

    @Override
    public void onPostExecute(String s) {

            arrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject detailedObjects = jsonArray.getJSONObject(i);
                    arrayList.add(new MovieStat( detailedObjects.getString("title"),
                            detailedObjects.getString("poster_path"),
                            Double.valueOf(detailedObjects.getString("popularity")),
                            detailedObjects.getString("backdrop_path"),
                            Double.valueOf(detailedObjects.getString("vote_count")),
                            detailedObjects.getString("release_date"),
                            Double.valueOf(detailedObjects.getString("vote_average")),
                            detailedObjects.getString("overview"),
                            Integer.valueOf(detailedObjects.getString("id"))
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            CustomListAdapter adapter = new CustomListAdapter(
                    getApplicationContext(), R.id.movieListgrid, arrayList
            );
            theGridView.setAdapter(adapter);
    }

    @Override
    public void onPreExecute() {

    }




    //comment005 this is the JSON that will parse the data
//    class ReadJSON extends AsyncTask<String, Integer, String>{
//
//
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
//            arrayList.clear();
//
//            try {
//                JSONObject jsonObject = new JSONObject(content);
//                JSONArray jsonArray = jsonObject.getJSONArray("results");
//                for (int i=0; i<jsonArray.length(); i++) {
//                    JSONObject detailedObjects = jsonArray.getJSONObject(i);
//                    arrayList.add(new MovieStat( detailedObjects.getString("title"),
//                            detailedObjects.getString("poster_path"),
//                            Double.valueOf(detailedObjects.getString("popularity")),
//                            detailedObjects.getString("backdrop_path"),
//                            Double.valueOf(detailedObjects.getString("vote_count")),
//                            detailedObjects.getString("release_date"),
//                            Double.valueOf(detailedObjects.getString("vote_average")),
//                            detailedObjects.getString("overview"),
//                            Integer.valueOf(detailedObjects.getString("id"))
//                    ));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            CustomListAdapter adapter = new CustomListAdapter(
//                    getApplicationContext(), R.id.movieListgrid, arrayList
//            );
//            theGridview.setAdapter(adapter);
//        }
//
//    }


    //comment006 this is the uri builder and the connection to the URL
//    private static String readsURL(String theUrl){
//
//
//        StringBuilder content =  new StringBuilder();
//
//        try {
//            URL url  = new URL(theUrl);
//            URLConnection urlConnection =  url.openConnection();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            while((line = bufferedReader.readLine()) !=null) {
//                content.append(line + "\n");
//            }
//            bufferedReader.close();
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//         return content.toString();
//
//    }








}

