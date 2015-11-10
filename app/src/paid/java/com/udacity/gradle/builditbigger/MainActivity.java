package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.projects.gerhardschoeman.jokeactivity.MainJokeActivity;
import com.projects.gerhardschoeman.jokeactivity.MainJokeActivityFragment;

public class MainActivity extends ActionBarActivity implements GCEListener {

    private ProgressBar loadingSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingSpinner = (ProgressBar)findViewById(R.id.loading);
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
        loadingSpinner.setVisibility(ProgressBar.GONE);
        Intent i = new Intent(this, MainJokeActivity.class);
        i.putExtra(MainJokeActivityFragment.INTENT_EXTRA_JOKE,returnValue);
        startActivity(i);
    }
}
