package com.example.android.androidjoker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayJokeActivity extends AppCompatActivity {
    private TextView textview;
    private ImageView imageView;
    private String jooke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_joke);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textview = (TextView) findViewById(R.id.joke_text);
        imageView = (ImageView) findViewById(R.id.joke_image);
        if (getIntent().hasExtra(getString(R.string.joke_intent))) {
            jooke = getIntent().getStringExtra(getString(R.string.joke_intent));
        }
        boolean paid = getIntent().getBooleanExtra("version", false);

        if (jooke != null) {
            textview.setText(jooke);
            if(paid) {
                showPaidImage();
            }
        } else {
            textview.setText(R.string.joke_error);
        }


    }



    public void showPaidImage(){
        imageView.setVisibility(View.VISIBLE);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
