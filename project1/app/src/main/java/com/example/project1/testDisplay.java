package com.example.project1;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;
//this is testing class
public class testDisplay extends AppCompatActivity {
    Context context;
    TextView rNameR;
    ImageView imageView;
    private static final String TAG = "testDisplay";
    //String URL = "";
    String URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=CnRtAAAATLZNl354RwP_9UKbQ_5Psy40texXePv4oAlgP4qNEkdIrkyse7rPXYGd9D_Uj1rVsQdWT4oRz4QrYAJNpFX7rzqqMlZw2h2E2y5IKMUZ7ouD_SlcHxYq1yL4KbKUv3qtWgTK0A6QbGh87GB3sscrHRIQiG2RrmU_jF4tENr9wGS_YxoUSSDrYjWmrNfeEHSGSc3FyhNLlBU&key=AIzaSyBBLuzRFdXTkGc0RvPxjqURBzLpNGG6MAE";
    //String URL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=&key=AIzaSyBBLuzRFdXTkGc0RvPxjqURBzLpNGG6MAE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_display);
        Log.i(TAG, "HERE");
        imageView = (ImageView) findViewById(R.id.imageV);
        rNameR  = (TextView) findViewById(R.id.rNameTV);

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        Log.i(TAG, "HERE2");
        ImageRequest imageRequest = new ImageRequest(URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(testDisplay.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }
        );
        requestQueue.add(imageRequest);
    }
}
