package com.sofforce.movieapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sofforce.movieapp.datafavorites.MovieContract;
import com.sofforce.movieapp.datafavorites.MovieDbHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by mac on 12/28/17.
 */

public class DetailedActivity extends AppCompatActivity {


    TextView txtName;
    TextView txtRating;
    TextView txtOverview;
    TextView txtYear;
    ImageView imageView;
    Button favButton;

    static LinearLayout linearBottom;
    ListView reviews;
    ListView videos;

    ArrayList<MovieReviews> reviewsArrayList;
    ArrayList<MovieVideos> videosArrayList;

    MovieDbHelper mMovieDbHelper = new MovieDbHelper( this ) ;


    String pref ="http://image.tmdb.org/t/p/w342";
    String url;


    private String imageUri;
    private int movieId;
    private String movieTitle;
    private String movieDate;
    private double movieRating;
    private static final String API_KEY = BuildConfig.API_KEY;

    private final String SAVED_LIST_REVIEW = "reviews";
    int savedPosition = 0;
    NestedScrollView scrollView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview_relative);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();

        reviewsArrayList =  new ArrayList<>();
        videosArrayList =  new ArrayList<>();


        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


         txtName = (TextView) findViewById(R.id.marquee_title);
         txtRating = (TextView) findViewById(R.id.movie_rating);
         txtOverview = (TextView) findViewById(R.id.movie_overview);
         txtYear = (TextView) findViewById(R.id.movie_year);
         imageView = (ImageView) findViewById(R.id.grid_item_image);

         //this is the view ID of the linearLayout
         linearBottom = (LinearLayout) findViewById( R.id.linearBottom );

         reviews = (ListView) findViewById(R.id.reviewsList);   //this is for the reviews List view
         //setListViewHeightBasedOnChildren( reviews );

         videos = (ListView) findViewById(R.id.videosList);   //this is for the videos List view
         //setListViewHeightBasedOnChildren( reviews );

        //this is the initailization of the scrollview
       // scrollView =  (ScrollView) findViewById( R.id.detailed_scrollview );
         scrollView = (NestedScrollView) findViewById( R.id.detailed_scrollview );


        MovieStat object = (MovieStat) getIntent().getParcelableExtra("parcel");
            txtName.setText(object.getTitle());
            txtRating.setText(object.getVoteAverage().toString());
            txtOverview.setText(object.getOverview());
            txtYear.setText(object.getReleaseDate());
            url = object.getPosterPath();

            movieId = object.getIdNumber();
            movieTitle = object.getTitle();
            movieDate = object.getReleaseDate();
            movieRating = object.getVoteAverage();
            imageUri = url;


        Picasso.with(this).load(pref+url ).into(imageView);

            favButton =  (Button) findViewById(R.id.favoriteButt);

        Log.d( "MOVIE_ID", "this is the movie ID " + movieId );



        //this is the url string for the reviews.
        String reviewsUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=";
        //this is the url string for the videos
        String videosUrl = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=";



        // MovieReviews object2 =  (MovieReviews) getParcelable("parcel");
        ReviewsAsync reviewsAsync = new ReviewsAsync();
        reviewsAsync.execute(reviewsUrl+API_KEY);

        VideosAsync videosAsync =  new VideosAsync();
        videosAsync.execute( videosUrl+API_KEY );

        //when the movie thumbnail gets clicked it will pass the Key from the MovieVideo class
        //that will when be passed to an intent to another activity that will load the url
        //and play the trailer for the movie
        videos.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                MovieVideos movieVideos =  videosArrayList.get(i);
                String urlMovie = "https://www.youtube.com/watch?v=" + movieVideos.getKey() ;
                Intent browswerIntent = new  Intent (Intent.ACTION_VIEW, Uri.parse( urlMovie ));
                startActivity( browswerIntent );


                Log.d( "VIDEO_KEY", "this is the video Key____________________________: " + movieVideos.getKey() );

            }

        });

        String isInDatabase =  String.valueOf(movieId);
        changeButtonFunct( Integer.valueOf(isInDatabase) );

      Log.d( "___IS_FAVORITE_METHOD","This is the movieID " + isInDatabase);
      Log.d( "___IS-IN-DATABASE",
              "This movie ID number is in database: " + mMovieDbHelper.getItemID( Integer.valueOf(isInDatabase) ) );



      if (savedInstanceState != null) {
          setAdapter( reviewsArrayList );
      } else {
          setAdapter2( videosArrayList );
      }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState( outState );
        outState.putParcelableArrayList( SAVED_LIST_REVIEW, videosArrayList );

        Log.d( "SAVED_INSTANCE", "this is what is saved " + outState.toString() );

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState( savedInstanceState );
        savedInstanceState.getParcelable( SAVED_LIST_REVIEW );

        Log.d( "RESTORED_INSTANCE", "this is what is restored " + savedInstanceState.toString() );

    }



    //this method queries to see if id is in the database and if it is it will change the
    //name of the button and the function of the "add favorite" button to "Delete favorite"
    public void changeButtonFunct( final Integer id) {

        final ContentResolver contentResolver = getContentResolver();

        String deleteFav = "Delete Favorite";

        mMovieDbHelper.isFavorite( String.valueOf(id) );
        if (mMovieDbHelper.isFavorite( String.valueOf(id) )) {
            favButton.setBackgroundColor( Color.parseColor( "#92200F" ) );
            favButton.setText(deleteFav);
            favButton.setTextColor( Color.parseColor( "#000000" ) );
            favButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v)
                {

                    contentResolver.delete( MovieContract.MovieEntry.CONTENT_URI, "_ID=?",  new String[] {String.valueOf( id )});
                    favButton.setText( "ADD FAVORITE" );
                    favButton.setBackgroundColor( Color.parseColor( "#E28413" ) );
                    favButton.setTextColor( Color.parseColor( "#FBF5F3" ) );



                }
            });
        }

    }




    //this is the addFavorite() for the favorite button
    public void addFavorite(View view) {

       /*in this method there are a few steps to follow
            step 1: grab the information that is displayed, the ID, the movieImage, and the name
            step 2: make a contentvalue object to pass theses values to it
            step 3: pass that information to be stored in the MovieDbHelper database
            step 4: finish the activity(this returns back to the mainActivity)
       * */


        ContentValues contentValues =  new ContentValues();

        contentValues.put(MovieContract.MovieEntry._ID, movieId);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_NAME, movieTitle);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTER, imageUri);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_DATE, movieDate);
        contentValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTE, movieRating);



        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);

        if (uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        finish();


    }



    //new method is to set the height in the listView for the thumbnail
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        View.MeasureSpec measureSpec =  new View.MeasureSpec();


        try {

            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter != null) {
                int desiredWidth = measureSpec.makeMeasureSpec( listView.getWidth(), View.MeasureSpec.UNSPECIFIED );
                int totalHeight = 0;
                for (int i = 0; i < listAdapter.getCount(); i++) {
                        View view = listAdapter.getView( i, null, listView );

                        view.setLayoutParams( new ViewGroup.LayoutParams( desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT ) );

                        view.measure( desiredWidth, View.MeasureSpec.UNSPECIFIED);


                        totalHeight += view.getMeasuredHeight();
                }
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = (int) totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
                listView.setLayoutParams( params );
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        return;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }


    //this class is to retrieve the reviews
    public class ReviewsAsync extends AsyncTask<String, String, String> {

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
        public void onProgressUpdate(String... values) {

        }

        @Override
        public void onPostExecute(String s) {

            reviewsArrayList.clear();

            try {
                JSONObject jsonObject = new JSONObject( s );
                JSONArray jsonArray = jsonObject.getJSONArray( "results" );
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject detailedObjects = jsonArray.getJSONObject( i );
                    reviewsArrayList.add( new MovieReviews( detailedObjects.getString( "id" ),
                            detailedObjects.getString( "author" ),
                            detailedObjects.getString( "content" ),
                            detailedObjects.getString( "url" )
                    ) );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setAdapter( reviewsArrayList );
        }

        @Override
        public void onPreExecute() {

        }

    }


        private void setAdapter(ArrayList<MovieReviews> movieReviews) {
            ReviewsListAdapter adapter = new ReviewsListAdapter(
                    getApplicationContext(), R.id.reviewsList, movieReviews
            );
            reviews.setAdapter( adapter );

            if (reviews != null ) {
                setListViewHeightBasedOnChildren( reviews );
            }
        }






        //this class is for retrieving displaying the videos in the detailedView
    public class VideosAsync extends AsyncTask<String, String, String> {


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
            public void onPostExecute(String s) {

                videosArrayList.clear();

                try {
                    JSONObject jsonObject = new JSONObject( s );
                    JSONArray jsonArray = jsonObject.getJSONArray( "results" );
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject detailedObjects = jsonArray.getJSONObject( i );
                        videosArrayList.add( new MovieVideos( detailedObjects.getString( "id" ),
                                detailedObjects.getString( "key" ),
                                detailedObjects.getString( "name" )
                        ) );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setAdapter2( videosArrayList );
            }


    }


    private void setAdapter2(ArrayList<MovieVideos> movieVideos) {
        VideosListAdapter adapter = new VideosListAdapter(
                getApplicationContext(), R.id.videosList, movieVideos
        );
        videos.setAdapter( adapter );

        if (videos != null ) {
            setListViewHeightBasedOnChildren( videos );
        }
    }

    //when the movie thumbnail gets clicked it will pass the Key from the MovieVideo class
    //that will when be passed to an intent to another activity that will load the url
    //and play the trailer for the movie
    //| -- authority  --     | --   query   --   |
    // http://www.youtube.com/watch?v=hFS9MrlJMXw

    //http://i.ytimg.com/vi/59ca73b7c3a36803c700bab9/hqdefault.jpg

    // this for the breaking point
   // https://www.learnhowtoprogram.com/android/user-interface-basics-637d41b1-35dc-400a-bcc3-65794760474d/debugging-breakpoints-and-the-android-debugger

    //this is for the height of the listviews
    //https://stackoverflow.com/questions/14020859/change-height-of-a-listview-dynamicallyandroid
}
