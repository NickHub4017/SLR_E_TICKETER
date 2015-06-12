package com.example.slr_e_ticketer.slr_e_ticketer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shalika on 6/11/2015.
 */
public class ServerRequests {
    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "192.168.56.1:81";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait");
    }

    public void storePassengerDataInBackground(Passenger passenger, GetPassengerCallback passengerCallback) {
        progressDialog.show();
        new StorePassengerDataAsyncTask(passenger, passengerCallback).execute();
    }

    public void fetchPassengerDataInBackGround(Passenger passenger, GetPassengerCallback callback) {
        progressDialog.show();
        new fetchPassengerDataAsyncTask(passenger, callback).execute();
    }

    public class StorePassengerDataAsyncTask extends AsyncTask<Void, Void, Void> {
        Passenger passenger;
        GetPassengerCallback passengerCallback;

        public StorePassengerDataAsyncTask(Passenger passenger, GetPassengerCallback passengerCallback) {
            this.passenger = passenger;
            this.passengerCallback = passengerCallback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("firstname", passenger.firstName));
            dataToSend.add(new BasicNameValuePair("lastname", passenger.lastName));
            dataToSend.add(new BasicNameValuePair("nic", passenger.nic));
            dataToSend.add(new BasicNameValuePair("email", passenger.email));
            dataToSend.add(new BasicNameValuePair("phonenumber", passenger.phoneNumber));
            dataToSend.add(new BasicNameValuePair("username", passenger.userName));
            dataToSend.add(new BasicNameValuePair("password", passenger.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "/Register.php");
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            passengerCallback.done(null);
            super.onPostExecute(aVoid);
        }
    }

    public class fetchPassengerDataAsyncTask extends AsyncTask<Void, Void, Passenger> {
        Passenger passenger;
        GetPassengerCallback passengerCallback;

        public fetchPassengerDataAsyncTask(Passenger passenger, GetPassengerCallback passengerCallback) {
            this.passenger = passenger;
            this.passengerCallback = passengerCallback;
        }

        @Override
        protected Passenger doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", passenger.userName));
            dataToSend.add(new BasicNameValuePair("password", passenger.password));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchPassengerData.php");

            Passenger returnedPassenger = null;
            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);
                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() == 0) {
                    passenger = null;
                } else {
                    String firstname = jObject.getString("firstname");
                    String lastname = jObject.getString("lastname");
                    String nic = jObject.getString("nic");
                    String email = jObject.getString("email");
                    String phonenumber = jObject.getString("phonenumber");

                    returnedPassenger = new Passenger(firstname, lastname, nic, email, phonenumber, passenger.userName, passenger.password);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return returnedPassenger;
        }

        @Override
        protected void onPostExecute(Passenger returnedPassenger) {
            progressDialog.dismiss();
            passengerCallback.done(passenger);
            super.onPostExecute(returnedPassenger);
        }
    }
}
