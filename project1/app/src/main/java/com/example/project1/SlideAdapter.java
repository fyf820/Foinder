package com.example.project1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ImageView;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import org.json.JSONObject;

import java.util.List;

//class that's used to change the slides left and right
public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    public Object object;
    public String TAG = "SlideAdapter";
    private ImageView imgslide;
    public  String URL ="https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
    public String name;
    public int userID;
    public int radius;
    public double lat;
    public double lon;
    private String Key;
    public int size =0;

    public SlideAdapter(Context context, String name, int userID, int radius, double lat, double lon, Object object, int size, String Key){
        this.context=context;
        this.name = name;
        this.userID=userID;
        this.radius=radius;
        this.lat=lat;
        this.lon=lon;
        this.object = object;
        this.size=size;
        this.Key=Key;
        Log.i(TAG, "count "+ object.getResultCount());

    }

    @Override
    public int getCount() {
        return size;
    }


    @Override
    public boolean isViewFromObject( View view,  java.lang.Object o) {
        return (view==(LinearLayout)o);
    }

    @Override
    public java.lang.Object instantiateItem(ViewGroup container, int position) {
        Log.i(TAG, "count "+ size);
        Log.i(TAG, "count "+ object.getResults(position).getName());
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide=(LinearLayout) view.findViewById(R.id.slidelinearlayout);
        imgslide = (ImageView) view.findViewById(R.id.slideimg);
        TextView txttitle=(TextView) view.findViewById(R.id.txttitle);
        TextView description=(TextView) view.findViewById(R.id.txtdescription);
        txttitle.setText(object.getResults(position).getName());


        String URLimage = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=350";
        String photoRef = "photoreference=" + object.getResults(position).getPhotos().getPhoto_reference();
        URLimage +="&" +photoRef+"&"+"key="+Key;
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        Log.i(TAG, "URLimage "+URLimage);
        ImageRequest imageRequest = new ImageRequest(URLimage, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imgslide.setImageBitmap(response);
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

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem( ViewGroup container, int position, java.lang.Object object) {
        container.removeView((LinearLayout)object);
    }

    public double getLat(int position){
        return object.getResults(position).getLat();
    }
    public double getLng(int position){
        return object.getResults(position).getLng();
    }

    public String getPlaceID(int position){
        return object.getResults(position).getPlace_id();
    }

    public String getName(int position) {
        return object.getResults(position).getName();
    }

    public String getPhotoRef (int position){return object.getResults(position).getPhotos().getPhoto_reference();}
}
