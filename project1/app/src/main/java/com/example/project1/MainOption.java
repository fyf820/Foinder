package com.example.project1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;
//This is where the menu is implemented
//on click button is where each button click execute
public class MainOption extends AppCompatActivity {
    private Button findRestaurantBtn;
    private Button profileBtn;
    private Button historyBtn;
    private Button logoutBtn;
    private TextView welcometv;
    private TextView nametv;
    private String name;
    private int userID;
    private String lat = "";
    private String lon = "";
    private static final String TAG = "MainOption";
    Context context;
    AlertDialog alertDialog;
    private String userEMAIl;


    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private boolean updateOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_option);
        Intent intent = getIntent();
        name = intent.getExtras().getString("name").toUpperCase();
        userID = intent.getExtras().getInt("userID");
        userEMAIl = intent.getExtras().getString("email");
        findRestaurantBtn = (Button) findViewById(R.id.findbutton);
        profileBtn = (Button) findViewById(R.id.profileButton);
        historyBtn = (Button) findViewById(R.id.historyButton);
        logoutBtn = (Button) findViewById(R.id.logoutButton);
        nametv = (TextView) findViewById(R.id.nametv);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(7500);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //gbs
        //locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY); // cell

        nametv.setText(name);
        Log.i(TAG, "here");
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "logout");
                Intent newActivity1 = new Intent(v.getContext(), LoginActivity.class);
                startActivity(newActivity1);
            }
        });
        findRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "find");
                updateOn = true;
                starLocationUpdates();
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "profile");
                startProfile();
            }
        });
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "history");
                startHistory();
            }
        });







        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        lat = (String.valueOf(location.getLatitude()));
                        lon = (String.valueOf(location.getLongitude()));
                        Log.i(TAG, "1long lat = " + lat + " " + lon);
                    }
                }
            });
        } else {
            //request permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }
        //update dynamically
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        lat = (String.valueOf(location.getLatitude()));
                        lon = (String.valueOf(location.getLongitude()));
                        Log.i(TAG, "2long lat = " + lat + " " + lon);
                        findOption();
                    }
                }
            }
        };
    }
    public void startProfile(){
        Integer userIDInt = Integer.valueOf(userID);
        Intent newActivity1 = new Intent(this, Profile.class);
        newActivity1.putExtra("name",name);
        newActivity1.putExtra("userID",userIDInt);
        newActivity1.putExtra("email",userEMAIl);
        startActivity(newActivity1);
    }
    public void findOption() {
        Integer userIDInt = Integer.valueOf(userID);
        Double lonDouble = Double.valueOf(lon);
        Double latDouble = Double.valueOf(lat);
        Intent newActivity1 = new Intent(this, FindOption.class);
        newActivity1.putExtra("name",name);
        newActivity1.putExtra("userID",userIDInt);
        newActivity1.putExtra("lon",lonDouble);
        newActivity1.putExtra("lat",latDouble);
        newActivity1.putExtra("email",userEMAIl);
        startActivity(newActivity1);
    }
    public void startHistory() {
        Integer userIDInt = Integer.valueOf(userID);
        Double lonDouble = Double.valueOf(lon);
        Double latDouble = Double.valueOf(lat);
        Intent newActivity1 = new Intent(this, History.class);
        newActivity1.putExtra("name",name);
        newActivity1.putExtra("userID",userIDInt);
        newActivity1.putExtra("lon",lonDouble);
        newActivity1.putExtra("lat",latDouble);
        newActivity1.putExtra("email",userEMAIl);
        startActivity(newActivity1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_FINE_LOCATION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getApplicationContext(), "This app require location permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (updateOn) starLocationUpdates();
    }

    private void starLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }
        else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_FINE_LOCATION);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
