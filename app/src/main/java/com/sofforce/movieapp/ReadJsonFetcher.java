package com.sofforce.movieapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mac on 1/14/18.
 */

public class ReadJsonFetcher extends AsyncTask<String, Integer, Object> {

    MovieListActivity mlistActivity = new MovieListActivity();

    @Override
    protected String doInBackground(String... params) {
        return mlistActivity.readsURL(params[0]);
    }


    @Override
    protected void onPostExecute(String content) {

        arrayList.clear();

        try {
            JSONObject jsonObject = new JSONObject(content);
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
        theGridview.setAdapter(adapter);
    }

}
