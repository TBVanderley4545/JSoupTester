package com.example.tyvanderley.jsouptester;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class PullPictureActivity extends AsyncTask<Void, Void, Void> {
    String mURLAddress = "http://www.wataugahumanesociety.org/animals";
    ProgressDialog mProgressDialog;
    Bitmap[] animalPicsArray;
    private Context context;
    Activity mActivity;
    Bitmap bitmap;

    public PullPictureActivity(Context cxt, Activity activity) {
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
            List<Bitmap> animalPics = new ArrayList<>();

            // Get the animal names through selecting the class and then it's children a elements
            Elements classSelector = document.select(".animal-listing__image > img");
            for (Element element:classSelector) {
                String imgSrc = element.attr("src");
                InputStream input = new java.net.URL(imgSrc).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                animalPics.add(bitmap);
            }

            // size the String[] animalArray to the size of our ArrayList
            animalPicsArray = new Bitmap[animalPics.size()];
            // Parse the data from the ArrayList into animalArray
            animalPics.toArray(animalPicsArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        LinearLayout layout = (LinearLayout) mActivity.findViewById(R.id.imageLinearLayout);

        for (Bitmap img : animalPicsArray) {
            ImageView imageView = new ImageView(mActivity);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            imageView.setMaxHeight(20);
            imageView.setMaxWidth(20);
            imageView.setImageBitmap(img);
            layout.addView(imageView);
        }
        mProgressDialog.dismiss();
    }
}

