package com.example.project1;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.ExecutionException;
//holder to hold slider
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SlideAdapter myadapter;
    private int currentPosition=0;
    private String [] nameArray;
    private static final String TAG = "MainActivity";
    private String URL ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    private Object object;
    private String responseC;
    private String name;
    private int userID;
    private int radius;
    private double lon;
    private double lat;
    private String Key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        name = intent.getExtras().getString("name").toUpperCase();
        userID = intent.getExtras().getInt("userID");
        radius = intent.getExtras().getInt("radius");
        lon = intent.getExtras().getDouble("lon");
        lat = intent.getExtras().getDouble("lat");



        viewPager =(ViewPager) findViewById(R.id.viewpager);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        Key = "AIzaSyBBLuzRFdXTkGc0RvPxjqURBzLpNGG6MAE";
        String location = "location="+lat+","+lon;
        String rad = "radius="+radius;
        String type = "type=coffee";

        URL += location + "&" + rad + "&" +type + "&"+"key="+Key;
        Log.i(TAG, "Location URL "+ URL);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "Response "+ response.toString());
                        try {

                            Gson gson = new Gson();
                            final Object object= gson.fromJson(response.toString(),Object.class);
                            int size = object.getResultCount();
                            myadapter=new SlideAdapter(MainActivity.this, name, userID, radius, lat, lon, object, size,Key);
                            viewPager.setAdapter(myadapter);
                            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                                private static final float thresholdOffset = 0.5f;
                                private boolean scrollStarted, checkDirection;
                                @Override
                                public void onPageScrolled(int i, float v, int i1) {

                                }
                                @Override
                                public void onPageSelected(int position) {
                                    if(currentPosition < position) {
                                        // handle swipe LEFT

                                        Log.i(TAG, " LEFT name ");
                                    } else if(currentPosition > position){
                                        // handle swipe RIGHT

                                        //get latitude and longitude
                                        float latitude= (float) 59.915494;
                                        float longitude=(float) 30.409456;

                                        //String uri = String.format(Locale.ENGLISH, "geo:%f,%f", latitude, longitude);
                                        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                        //"https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393&query_place_id=ChIJKxjxuaNqkFQR3CK6O1HNNqY"
                                        String placeID= myadapter.getPlaceID(currentPosition);
                                        String photoRef = myadapter.getPhotoRef(currentPosition);
                                        String mapIntentURL ="https://www.google.com/maps/search/?api=1&query=" +lat + "," +lon + "&query_place_id=" + placeID;
                                        String placeName=myadapter.getName(currentPosition);

                                        String type = "insert history";
                                        BackgroundWorker backgroundWorker = new BackgroundWorker((MainActivity.this));
                                        try {
                                            Log.i(TAG, " Right "+ placeID + placeName);
                                            String valid =backgroundWorker.execute(type,Integer.toString(userID),placeID,placeName,photoRef).get();
                                            Log.i(TAG, "valid "+ valid);
                                            if (valid.equals("Already Exist")||valid.equals("Insert History Successed")){
                                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                                        Uri.parse(mapIntentURL));
                                                startActivity(intent);
                                            }
                                            else{
                                            Log.i(TAG, "Insert History Failed");
                                                Toast.makeText(MainActivity.this.getBaseContext(),"Insert History Failed",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }

                                        //Log.i(TAG, " Right "+ placeID + placeName);
                                    }
                                    currentPosition = position; // Update current position
                                    Log.i(TAG, " position "+currentPosition);
                                }

                                @Override
                                public void onPageScrollStateChanged(int i) {

                                }
                            });

                        }
                        catch (JsonParseException e) {
                            Log.e(TAG, "Error processing JSON results", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG, "ResponseError "+ error.toString());
                    }
                }
        );
        requestQueue.add(objectRequest);

    }
}
