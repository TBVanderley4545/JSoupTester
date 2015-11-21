package com.example.tyvanderley.jsouptester;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    ValidationChecker vCheck = new ValidationChecker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If there is network connectivity, then pull data. If not, display an error toast.
        if (vCheck.isNetworkAvailable()) {
            new PullNameActivity(this, this).execute();
            new PullPictureActivity(this,this).execute();
        } else {
            Toast.makeText(this, R.string.networkUnavailable, Toast.LENGTH_LONG).show();
        }

    }
}
