package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.projects.gerhardschoeman.jokeactivity.MainJokeActivity;
import com.projects.gerhardschoeman.jokeactivity.MainJokeActivityFragment;
import com.udacity.gradle.backend.myApi.MyApi;

import java.io.IOException;

/**
 * Created by Gerhard on 09/11/2015.
 */
public class GCE extends AsyncTask<Void,Void,String> {
    private static MyApi myApiService = null;
    private GCEListener gceListener;

    public GCE(GCEListener listener) {
        gceListener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        Log.d("GCEBACKGROUND","querying GCE");
        if(myApiService==null){
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
                    // end options for devappserver
            myApiService = builder.build();
        }
        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            Log.e("GCETASK","Error calling GCE: " + e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("GCERETURN","Received response from GCE");
        super.onPostExecute(s);
        gceListener.callComplete(s);
    }
}
