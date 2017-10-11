package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.widget.Toast;

import com.example.pepovpc.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

// from there https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
class EndpointAsyncTask extends AsyncTask<MainActivityFragment, Void, String> {
    private static MyApi myApiService = null;
    private MainActivityFragment mainActivityFragment;

    @Override
    protected String doInBackground(MainActivityFragment... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // I am running it on genymotion, please use string below if you use Android emulator
                    // String ipAdress = "http://10.0.2.2:8080/_ah/api/";
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

            mainActivityFragment = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        System.out.println(result);
        //call it though interface, not fragment (good for multiple usages)
        if(result == null)
            Toast.makeText(mainActivityFragment.getContext(), "Null error", Toast.LENGTH_LONG).show();
        else
            mainActivityFragment.displayJokeActivity(result);
    }


}