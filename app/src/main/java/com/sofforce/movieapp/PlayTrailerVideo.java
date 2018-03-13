package com.sofforce.movieapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PlayTrailerVideo extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;

    //this is the youtube path needed to play the video
    private final String AUTHORITY = "https://www.youtube.com/watch?v=";

    //this is the key needed in conjunction with the AUTHORITY
    String videoKey;

    VideoToPlayAsync videoToPlayAsync =  new VideoToPlayAsync();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_play_trailer_video );

        videoView = (VideoView) findViewById( R.id.playVideo );

        MovieVideos movieVideos = (MovieVideos) getIntent().getParcelableExtra( "parcel" );
        videoKey = (String) movieVideos.getKey().toString();

        videoToPlayAsync.execute( AUTHORITY + videoKey );
        Log.d("PLAYVIDEOACTIVITY","you made to the PlayTrailerVide0_________________________||||||||||||||||" );
        Log.d( "VIDEOKEY", "the videoKey for PTV_activity is_______________________" + videoKey );

    }




    class VideoToPlayAsync extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {


            StringBuilder content = new StringBuilder();

            try {
                URL url = new URL( params[0] );
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader( urlConnection.getInputStream() ) );
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append( line + "\n" );
                }
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String videoString) {
            //I didnt  figure out how to put the data to play the video here
            videoView.setVideoURI( Uri.parse( videoString ) );

        }
    }



}
