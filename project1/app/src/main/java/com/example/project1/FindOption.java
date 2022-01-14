package com.example.project1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
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
//this is used to get the radius and open to find restaurant
public class FindOption extends AppCompatActivity {
    private SeekBar radiusBar;
    private TextView radius;
    private Button findButton;
    private Button menuButton;
    private int seekValue;
    private static final String TAG = "FindOption";
    private String name="";
    private int userID;
    private double lon;
    private double lat;
    private String userEMAIl;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_find_option);
        radiusBar = (SeekBar) findViewById(R.id.distance_bar);
        radius = (TextView) findViewById(R.id.radiustv);
        findButton = (Button) findViewById(R.id.findButton);
        menuButton = (Button) findViewById(R.id.mainMenuBtt);
        //maxValue = radiusBar.getMax();
        seekValue = radiusBar.getProgress();
        Intent intent = getIntent();
        userEMAIl = intent.getExtras().getString("email");
        name = intent.getExtras().getString("name").toUpperCase();
        userID = intent.getExtras().getInt("userID");
        lon = intent.getExtras().getDouble("lon");
        lat = intent.getExtras().getDouble("lat");

        Log.i(TAG,"STUFFF "+ name + " "+ userID + " "+ lon + " "+ lat + " " + seekValue);

        radiusBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekValue = progress;
                radius.setText(String.valueOf(seekValue)+" meters");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(seekBar.getContext(), "Seek bar progress is :" + seekValue + " " + name + " "+ userID + " "+ lon + " "+ lat + " " , Toast.LENGTH_SHORT).show();
            }
        });
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findRestaurant(v.getContext());
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMenu(v.getContext());
            }
        });
    }
    public void startMenu(Context ctx){
        Log.i(TAG,"startMenu "+ name + " "+ userID + " "+ lon + " "+ lat + " " + seekValue);
        Intent newActivity1 = new Intent(ctx, MainOption.class);
        newActivity1.putExtra("name",name);
        newActivity1.putExtra("userID",userID);
        newActivity1.putExtra("lon",lon);
        newActivity1.putExtra("lat",lat);
        newActivity1.putExtra("email",userEMAIl);
        startActivity(newActivity1);
        //*/
    }

    public void findRestaurant(Context ctx){
        String type = "getRestaurant";
        Log.i(TAG,"Find "+ name + " "+ userID + " "+ lon + " "+ lat + " " + seekValue);
        BackgroundWorker backgroundWorker = new BackgroundWorker((this));
        backgroundWorker.execute(type);
        ///*
        Intent newActivity1 = new Intent(ctx, MainActivity.class);
        newActivity1.putExtra("name",name);
        newActivity1.putExtra("userID",userID);
        newActivity1.putExtra("lon",lon);
        newActivity1.putExtra("lat",lat);
        newActivity1.putExtra("radius",seekValue);
        startActivity(newActivity1);
       //*/
    }
}
