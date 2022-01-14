package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ExecutionException;
//This is where the history will be displayed
public class History extends AppCompatActivity {
    private String name;
    private int userID;
    private double lon;
    private double lat;
    private String [] rname;
    private static final String TAG = "History";
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name").toUpperCase();
        userID = intent.getExtras().getInt("userID");
        lon = intent.getExtras().getDouble("lon");
        lat = intent.getExtras().getDouble("lat");
        Log.i(TAG, " " + name +  " " + userID +  " " + lon+  " " +  lat+  " ");
        String type = "getHistory";
        String type2 = "rname";
        BackgroundWorker backgroundWorker = new BackgroundWorker((History.this));
        try {
            String valid =backgroundWorker.execute(type,type2,Integer.toString(userID)).get();
            Log.i(TAG, valid);
            if ((valid.equals("No History"))) {

            }
            else {
                String[] arrValid = valid.split("\\*");
                String valid2 ="";
                int size = arrValid.length;
                Log.i(TAG, "YOYOo33yo3yo3o3o");
                Log.i(TAG, " " + size);
                String [] rname = new String[size];
                String [] photoref = new String[size];
                for (int i = 0; i< size; i++){
                    rname[i] = arrValid[i];
                    Log.i(TAG, "name " +i+ " " + rname[i]);

                    type2 = "photoref";

                    BackgroundWorker backgroundWorker2 = new BackgroundWorker((History.this));
                    valid2 =backgroundWorker2.execute(type,type2,Integer.toString(userID),rname[i]).get();
                    photoref[i] =valid2;
                    Log.i(TAG,"photoref "+ valid2);

                }
                listView = findViewById(R.id.listView);





                final MyAdapter adapter = new MyAdapter(this, rname,photoref, size);
                listView.setAdapter(adapter);
                Log.i(TAG,"show HDJFLKDJKS");
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String Rname = adapter.getRname(position);
                        String type = "getHistory";
                        String type2 = "placeID";
                        BackgroundWorker backgroundWorker = new BackgroundWorker((History.this));
                        try {
                            String valid =backgroundWorker.execute(type,type2,Integer.toString(userID),Rname).get();
                            if((valid.equals("No History"))){

                            }
                            else{
                                String mapIntentURL ="https://www.google.com/maps/search/?api=1&query=" +lat + "," +lon + "&query_place_id=" + valid;
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse(mapIntentURL));
                                startActivity(intent);
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    class MyAdapter extends ArrayAdapter<String>{
        Context context;
        String nameArray[];
        String photoREF[];
        int sizeh;
        MyAdapter (Context c, String nameArray[],String photoREF[], int sizeh){
            super(c, R.layout.row, R.id.TextViewName, nameArray);
            this.context = c;
            this.nameArray=nameArray;
            this.photoREF=photoREF;
            this.sizeh=sizeh;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row,parent,false);
            TextView nameTV = row.findViewById(R.id.TextViewName);
            final ImageView imageR = (ImageView) row.findViewById(R.id.RowImage);
            String Key = "AIzaSyBBLuzRFdXTkGc0RvPxjqURBzLpNGG6MAE";
            String URLimage = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=350";
            String photoRef = "photoreference=" + photoREF[position];
            URLimage +="&" +photoRef+"&"+"key="+Key;
            RequestQueue requestQueue= Volley.newRequestQueue(context);
            Log.i(TAG, "URLimage "+URLimage);
            ImageRequest imageRequest = new ImageRequest(URLimage, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    imageR.setImageBitmap(response);
                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Something Wrong", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            }
            );
            requestQueue.add(imageRequest);
            nameTV.setText(nameArray[position]);
            return row;
        }
        public String getRname(int position){
            return nameArray[position];
        }
    }
}
