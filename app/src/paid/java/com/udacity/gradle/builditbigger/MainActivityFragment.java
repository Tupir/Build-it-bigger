package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.android.androidjoker.DisplayJokeActivity;


public class MainActivityFragment extends Fragment {

    private ProgressBar progressBar;
    private Button button;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = rootView.findViewById(R.id.joke_progressbar);
        button = rootView.findViewById(R.id.joke_btn);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new EndpointAsyncTask().execute(MainActivityFragment.this);
            }
        });

        return rootView;
    }


    public void displayJokeActivity(String joke){
        Intent intent = new Intent(getActivity(), DisplayJokeActivity.class);
        intent.putExtra(getActivity().getString(R.string.joke_intent), joke);
        // add extra intent for paid version
        intent.putExtra("version", BuildConfig.FLAVOR.equals("paid"));
        getActivity().startActivity(intent);
        progressBar.setVisibility(View.GONE);
    }

}