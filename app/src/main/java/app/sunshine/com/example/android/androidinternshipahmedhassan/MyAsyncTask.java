package app.sunshine.com.example.android.androidinternshipahmedhassan;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyAsyncTask extends AsyncTask<String, Void, ArrayList<String>> {


    AsyncResponse delegate;

    public MyAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    @Override
    protected ArrayList<String> doInBackground(String... params) {

        ArrayList<String> countries = new ArrayList<>();

        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonstring;

        // Create the request to OpenWeatherMap, and open the connection
        try {
            url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a Str      ing
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            jsonstring = buffer.toString();
            Log.e("doInBackground: ", jsonstring); // logging

            JSONObject jsondata = new JSONObject(jsonstring);
            JSONArray arrayjson = jsondata.getJSONArray("Response");

            for (int i = 0; i < arrayjson.length(); i++) {
                ArrayList<String> countriesObj = new ArrayList<String>();
                JSONObject jsonObjIndex = arrayjson.getJSONObject(i);

                countriesObj.add(jsonObjIndex.getString("Name"));

                countries.addAll(countriesObj);
            }


        } catch (Throwable e1) {
            e1.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Error closing stream", e.toString());
                }
            }


            return countries;
        }

    }

    @Override
    protected void onPostExecute(ArrayList<String> countries) {
        if (countries != null)
            delegate.getCountries(countries);


    }

}


