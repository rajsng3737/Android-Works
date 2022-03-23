package com.example.weatherweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void getWeatherInfo(View view) {
        EditText editText = findViewById(R.id.city);
        String cityName = String.valueOf(editText.getText());
        new theBackgroundClass().execute(cityName);
        }
        class theBackgroundClass extends AsyncTask<String,String,String>{
            ProgressBar progressBar = findViewById(R.id.progressBar);
           @Override
           protected String doInBackground(String... strings) {
               try {
                   URL url = new URL("https://pro.openweathermap.org/data/2.5/weather?q="+strings[0]+"&APPID=b1b15e88fa797225412429c1c50c122a1");
                   HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                   InputStream in = url.openStream();
                   BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                   StringBuilder result = new StringBuilder();
                   String line;
                   while((line = reader.readLine()) != null) {
                       result.append(line);
                   }
                   JSONObject responseInJSON = new JSONObject(result.toString());
                   if(responseInJSON.get("cod").toString().equals("404"))
                        publishProgress( "CityNotFound");
                   else if(responseInJSON.get("cod").toString().equals("200"))
                   {
                       JSONObject main = responseInJSON.getJSONObject("main");
                       JSONObject wind = responseInJSON.getJSONObject("wind");
                       publishProgress( main.get("temp").toString(), main.get("humidity").toString() , wind.get("speed").toString());
                   }
               } catch (JSONException | IOException e) {
                   e.printStackTrace();
                   publishProgress("Error :\"\"(");
               }
              return "";
           }
           @Override
             protected void onProgressUpdate(String[] values) {
               progressBar.setVisibility(View.GONE);
               TextView weatherInfo = findViewById(R.id.weatherInfo);
               StringBuilder info = new StringBuilder();
               for(int i = 0; i<3; i++)
                   if(i < values.length )
                   {
                       info.append(values[i]).append("\n");
                   }
               weatherInfo.setText(info.toString());
             }
            @Override
            protected void onPreExecute() {
                progressBar.setVisibility(View.VISIBLE);
            }
        }
}