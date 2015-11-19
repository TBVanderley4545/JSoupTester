package com.example.tyvanderley.jsouptester;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new PullNameActivity(this, this).execute();
        new PullPictureActivity(this,this).execute();
    }

}
