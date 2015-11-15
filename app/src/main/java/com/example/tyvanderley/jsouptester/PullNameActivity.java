package com.example.tyvanderley.jsouptester;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class PullNameActivity extends AsyncTask<Void, Void, Void> {
    String mURLAddress = "http://www.wataugahumanesociety.org/animals";
    ProgressDialog mProgressDialog;
    String[] animalArray;
    private Context context;
    Activity mActivity;

    public PullNameActivity(Context cxt, Activity activity) {
        context = cxt;
        mProgressDialog = new ProgressDialog(context);
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
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
        TextView txtAnimal = (TextView) mActivity.findViewById(R.id.animalList);
        for (String name : animalArray) {
            animalNames += name + ", ";
        }
        txtAnimal.setText(animalNames);
        mProgressDialog.dismiss();
    }
}
