package com.example.tyvanderley.jsouptester;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    String mURLAddress = "http://www.wataugahumanesociety.org/animals";
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Animal().execute();
    }

    private class Animal extends AsyncTask<Void, Void, Void> {
        String[] animalArray;
        String title;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Looking For Animals Now!");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect(mURLAddress).get();
                Elements classSelector = document.select("animal-listing_title");
                List<String> animals = new ArrayList<>();
                for (Element element:classSelector) {
                    String animalName = element.ownText();
                    animals.add(animalName);
                }

                animalArray = new String[animals.size()];
                animals.toArray(animalArray);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TextView txtAnimal = (TextView) findViewById(R.id.animalList);
            for (int i = 0; i < animalArray.length; i++) {
                txtAnimal.setText(animalArray[i]);
            }
            mProgressDialog.dismiss();
        }
    }
}
