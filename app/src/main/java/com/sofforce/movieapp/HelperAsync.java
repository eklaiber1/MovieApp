package com.sofforce.movieapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



/**
 *
 * Created by mac on 1/16/18.
 */

public class HelperAsync extends AsyncTask<String, String, String> {

    private AsyncTaskCallback asyncTaskCallback;

    public void setAsyncTaskCallback(AsyncTaskCallback asyncTaskCallback) {
        this.asyncTaskCallback = asyncTaskCallback;
    }

    @Override
    protected String doInBackground(String... params) {

        StringBuilder content =  new StringBuilder();

        try {
            URL url  = new URL(params[0]);
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(asyncTaskCallback!=null){
            asyncTaskCallback.onPreExecute();
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(asyncTaskCallback!=null){
            asyncTaskCallback.onPostExecute(s);
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if(asyncTaskCallback!=null){
            asyncTaskCallback.onProgressUpdate(values);
        }
    }

    public interface AsyncTaskCallback{
        void onProgressUpdate(String... values);
        void onPostExecute(String s);
        void onPreExecute();
    }


}

