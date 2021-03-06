package com.hacksu.CommunicationLibrary;

import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.net.URI;

/**
 * Created by owner on 11/15/2014.
 */
public class SubmitApiCall extends AsyncTask<Void, Void, String> {
    private String urlname;

    public SubmitApiCall(String URL) {
        this.urlname = URL;}

    @Override
    protected String doInBackground(Void... params) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost request = new HttpPost(this.urlname);

            HttpResponse response = httpclient.execute(request);

            HttpEntity entity = response.getEntity();
            String responseStr = EntityUtils.toString(entity);
            return responseStr;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}