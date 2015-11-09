package com.projects.gerhardschoeman.jokeactivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainJokeActivityFragment extends Fragment {

    public static final String INTENT_EXTRA_JOKE = "INTENT_EXTRA_JOKE";

    public MainJokeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_joke, container, false);

        TextView tv = (TextView)rootView.findViewById(R.id.txtJoke);

        Intent i = getActivity().getIntent();
        if(i!=null){
            if(i.hasExtra(INTENT_EXTRA_JOKE)){
                tv.setText(i.getStringExtra(INTENT_EXTRA_JOKE));
            }
        }

        return rootView;
    }
}
