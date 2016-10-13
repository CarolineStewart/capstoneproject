package com.caroline.android.udacitycapstoneproject;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by carolinestewart on 6/8/16.
 */
public class DataUtil {
    private static final String COMMIT_ID = "e50bcf43d142b2397f815f5d529d232f944f23f0";
    private static final String BASE_URL = "https://raw.githubusercontent.com/MercuryIntermedia/Sample_Json_Movies/" + COMMIT_ID + "/top_movies.json";


    public static void getMovieData(GetMovieCallback movieCallback) {
        new MovieFetchTask(movieCallback).doInBackground(BASE_URL);

    }

    private static String fetch(String urlString) {

        Integer result = 0;

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String movieJsonStr = null;

        try {

            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                movieJsonStr = null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {

                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                movieJsonStr = null;
            }
            movieJsonStr = buffer.toString();


        } catch (IOException e) {
            Log.e("DataUtil", "Error ", e);
            movieJsonStr = null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("DataUtil", "Error closing stream", e);
                }
            }
        }
        return movieJsonStr;
    }

    public static class MovieFetchTask extends AsyncTask<String, Void, Integer> {
        private GetMovieCallback movieCallback;

        public MovieFetchTask(GetMovieCallback movieCallback) {
            MovieFetchTask.this.movieCallback = movieCallback;
        }
        @Override
        protected Integer doInBackground(String... params) {
            String urlString = params[0];
            movieCallback.onComplete(parseMovieItems(fetch(urlString)));
            return 1;

        }

        @Override
        protected void onPostExecute(Integer result) {



        }

        private List<MovieItem> parseMovieItems(String result) {
            List<MovieItem> items = null;
            try {
                JSONArray response = new JSONArray(result);

                {
                    items = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject post = response.getJSONObject(i);
                        items.add(parseSingleObject(post));
                        //callback.oncomplete(movieItems);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return items;
        }
        private MovieItem parseSingleObject(JSONObject post) {
            MovieItem item = new MovieItem();


            String title = post.optString("title");
            item.setTitle(title);

            String rank = post.optString("rank");
            item.setRank(rank);

            String year = post.optString("year");
            item.setYear(year);

            String poster = post.optString("poster");
            item.setPoster(poster);

            String imdbLink = post.optString("imdbLink");
            item.setImdbLink(imdbLink);

            String imdbRating = post.optString("imdbRating");
            item.setImdbRating(imdbRating);

            String imdbVotes = post.optString("imdbVotes");
            item.setImdbVotes(imdbVotes);

            String genre = post.optString("genre");
            item.setGenre(genre);


            return item;
        }

    }



    public interface GetMovieCallback {
        void onComplete(List<MovieItem> list);

    }


}
