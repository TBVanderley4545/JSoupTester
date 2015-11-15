package com.example.tyvanderley.jsouptester;

import java.io.IOException;
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
                // Connect to the Humane Society website
                Document document = Jsoup.connect(mURLAddress).get();

                // Create an ArrayList of Strings
                List<String> animals = new ArrayList<>();

                // Get the animal names through selecting the class and then it's children a elements
                Elements classSelector = document.select(".animal-listing__title > a");
                for (Element element:classSelector) {
                    String animalName = element.text();
                    animals.add(animalName);
                }

                // size the String[] animalArray to the size of our ArrayList
                animalArray = new String[animals.size()];
                // Parse the data from the ArrayList into animalArray
                animals.toArray(animalArray);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            String animalNames = "";
            TextView txtAnimal = (TextView) findViewById(R.id.animalList);
            for (String name : animalArray) {
                animalNames += name + ", ";
            }
            txtAnimal.setText(animalNames);
            mProgressDialog.dismiss();
        }
    }
}
