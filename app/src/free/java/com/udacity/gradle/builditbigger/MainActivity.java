package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.projects.gerhardschoeman.jokeactivity.MainJokeActivity;
import com.projects.gerhardschoeman.jokeactivity.MainJokeActivityFragment;

public class MainActivity extends ActionBarActivity implements GCEListener {

    private InterstitialAd mInterstitialAd;
    private String joke;
    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingSpinner = (ProgressBar)findViewById(R.id.loading);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                getNewAdd();
                launchJokeActivity();
            }
        });
        getNewAdd();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        loadingSpinner.setVisibility(ProgressBar.VISIBLE);
        GCE gce = new GCE(this);
        gce.execute();
    }

    @Override
    public void callComplete(String returnValue) {
        joke = returnValue;
        loadingSpinner.setVisibility(ProgressBar.GONE);
        if(mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        }else{
            launchJokeActivity();
        }
    }

    private void getNewAdd(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void launchJokeActivity(){
        Intent i = new Intent(this, MainJokeActivity.class);
        i.putExtra(MainJokeActivityFragment.INTENT_EXTRA_JOKE,joke);
        startActivity(i);
    }
}
