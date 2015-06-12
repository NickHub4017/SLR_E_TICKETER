package com.example.slr_e_ticketer.slr_e_ticketer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends ActionBarActivity implements View.OnClickListener{
    Button bRegister;
    EditText etFirstName,etLastName,etNic,etEmail,etPhoneNumber,etUserName,etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etFirstName = (EditText) findViewById(R.id.editTxtFirstName);
        etLastName = (EditText) findViewById(R.id.editTxtLastName);
        etNic = (EditText) findViewById(R.id.editTxtNic);
        etEmail = (EditText) findViewById(R.id.editTxtEmail);
        etPhoneNumber = (EditText) findViewById(R.id.editTxtPhoneNumber);
        etUserName = (EditText) findViewById(R.id.editTxtUserName);
        etPassword = (EditText) findViewById(R.id.editTxtPassword);

        bRegister=(Button)findViewById(R.id.btnCreateMyAccount);
        bRegister.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCreateMyAccount:
                String firstName = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String nic = etNic.getText().toString();
                String email = etEmail.getText().toString();
                String phoneNumber = etPhoneNumber.getText().toString();
                String username = etUserName.getText().toString();
                String password = etPassword.getText().toString();

                Passenger passenger = new Passenger(firstName,lastName,nic,email,phoneNumber,username,password);
                registerPassenger(passenger);
                break;
        }
    }
    private void registerPassenger(Passenger passenger){
        //ServerRequests serverRequests = new ServerRequests(this);
        new SendData().execute("");
        /*serverRequests.storePassengerDataInBackground(passenger,new GetPassengerCallback() {
            @Override
            public void done(Passenger returnedPassenger) {
                startActivity(new Intent(RegistrationActivity.this,ServiceActivity.class));
            }
        });*/
    }

    class SendData extends AsyncTask<String,Void,String>{
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
           return postData("");
            //return "b";
        }

        public String postData(String valueIWantToSend) {
            HttpClient httpclient = new DefaultHttpClient();
            // specify the URL you want to post to
            HttpPost httppost = new HttpPost("http://192.168.150.1:81/Register.php");
            try {
                // create a list to store HTTP variables and their values
                List nameValuePairs = new ArrayList();
                // add an HTTP variable and value pair
                nameValuePairs.add(new BasicNameValuePair("firstname", "1"));
                nameValuePairs.add(new BasicNameValuePair("lastname", "2"));
                nameValuePairs.add(new BasicNameValuePair("nic", "3"));
                nameValuePairs.add(new BasicNameValuePair("email", "4"));
                nameValuePairs.add(new BasicNameValuePair("phonenumber", "5"));
                nameValuePairs.add(new BasicNameValuePair("username", "6"));
                nameValuePairs.add(new BasicNameValuePair("password", "7"));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                // send the variable and value, in other words post, to the URL
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();
                return convertInputStreamToString(inputStream);
            } catch (ClientProtocolException e) {
                // process execption
            } catch (IOException e) {
                // process execption
            }
            return "lll";
        }
        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while((line = bufferedReader.readLine()) != null)
                result += line;


            return result;

        }

    }

}
