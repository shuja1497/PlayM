package com.shuja1497.olaplay;

import android.app.ProgressDialog;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    final String URL_GET_DATA = "http://starlord.hackerearth.com/studio";
    RecyclerView recyclerView;
    SongAdapter adapter;
    List<Song> songList;
//
    private String TAG = "MainActivity";
    private ProgressDialog pDialog;
//    private ListView lv;
//    ArrayList<HashMap<String, String>> songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        songList = new ArrayList<>();
        //lv = (ListView) findViewById(R.id.list);

        //new GetSongs().execute();
        adapter = new SongAdapter(songList, getApplicationContext());
        recyclerView.setAdapter(adapter);

        loadSongs();

    }

    private void loadSongs() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);

                                Song song = new Song(obj.getString("song"),
                                        obj.getString("url"),
                                        obj.getString("artists"),
                                        obj.getString("cover_image")
                                );

                                Log.d(TAG, "onResponse: ####"+ obj.getString("cover_image"));
                                songList.add(song);
                            }

                            adapter.notifyDataSetChanged();
//                            adapter = new SongAdapter(songList, getApplicationContext());
//                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //dismissing pDialog
                        pDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this,"ERROR:"+error.getMessage(), Toast.LENGTH_LONG).show();
                //dismissing pDialog
                pDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

        //pDialog started
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Songs");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setCancelable(true);
        pDialog.show();
    }

//    private class GetSongs extends AsyncTask<Void, Void, Void>{
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            Toast.makeText(MainActivity.this,"Fetching Songs",Toast.LENGTH_LONG).show();
//        }

//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            HttpHandler sh = new HttpHandler();
//            String url = "http://starlord.hackerearth.com/studio";
//            String jsonStr = sh.loadSongs(url);
//            Log.e(TAG, "Response from url: " + jsonStr);
//
//            if (jsonStr != null) {
//                try {
//                    //JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    JSONArray jsonArray = new JSONArray(jsonStr);
//
//                    // Getting JSON Array node
//                    //JSONArray contacts = jsonObj.getJSONArray(jsonObj);
//
//                    // looping through All Contacts
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        JSONObject c = jsonArray.getJSONObject(i);
//                        String song_name = c.getString("song");
//                        String artist_name = c.getString("artists");
//                        String song_url = c.getString("url");
//                        String cover_image = c.getString("cover_image");
//
//                        // tmp hash map for single contact
//                        HashMap<String, String> mySong = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        mySong.put("song_name", song_name);
//                        mySong.put("artist_name", artist_name);
//                        mySong.put("song_url", song_url);
//                        mySong.put("cover_image", cover_image);
//
//                        Log.d(TAG, "doInBackground: ****************#########"+ mySong);
//
//                        // adding contact to contact list
//                        songList.add(mySong);
//                    }
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//
//            }else {
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//            return null;
//        }

//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_GET_DATA,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            try {
//                                JSONArray jsonArray = new JSONArray(response);
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject obj = jsonArray.getJSONObject(i);
//
//                                    Song song = new Song(obj.getString("song"),
//                                            obj.getString("url"),
//                                            obj.getString("artists"),
//                                            obj.getString("cover_image")
//                                    );
//                                    songList.add(song);
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                    Toast.makeText(MainActivity.this,"ERROR:"+error.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            requestQueue.add(stringRequest);
//
//            return null
//
//        }
//
//
//        @Override
//        protected void onPostExecute(Void result) {
//            super.onPostExecute(result);
//            adapter = new SongAdapter(songList, getApplicationContext());
//            recyclerView.setAdapter(adapter);
//        }
//    }

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
