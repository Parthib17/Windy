package com.example.windy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DownloadManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView cityname;
    TextView temperature;
    EditText editText;
    TextView mintemperature;
    TextView maxtemperature;

    public void checkit(View view){
        String apikey="a5c2796881e38fa1c33c00c987aede95";
        String city=editText.getText().toString();
        String url="https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=a5c2796881e38fa1c33c00c987aede95";

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("main");
                    String temper=object.getString("temp");
                    String mintemper=object.getString("temp_min");
                    String maxtemper=object.getString("temp_max");

                    Double kelvintemp=Double.parseDouble(temper)-273.15;
                    Double kelvintempmin=Double.parseDouble(mintemper)-273.15;
                    Double kelvintempmax=Double.parseDouble(maxtemper)-273.15;

                    temperature.setText(kelvintemp.toString().substring(0,4)+"°"+"C");
                    mintemperature.setText("LOW = "+kelvintempmin.toString().substring(0,4)+"°"+"C");
                    maxtemperature.setText("HIGH  = "+kelvintempmax.toString().substring(0,4)+"°"+"C");

                    temperature.setVisibility(View.VISIBLE);
                    mintemperature.setVisibility(View.VISIBLE);
                    maxtemperature.setVisibility(View.VISIBLE);

                    cityname.setText(editText.getText().toString().toUpperCase(Locale.ROOT));
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please enter correct city name", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
        editText.onEditorAction(EditorInfo.IME_ACTION_DONE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this, androidx.cardview.R.color.cardview_dark_background));

        cityname= findViewById(R.id.cityname);
        temperature = findViewById(R.id.temperature);
        editText= findViewById(R.id.editText);
        mintemperature=findViewById(R.id.mintemperature);
        maxtemperature=findViewById(R.id.maxtemperature);
    }

}