package com.example.slr_e_ticketer.slr_e_ticketer;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SignActivity extends ActionBarActivity implements View.OnClickListener{
    PassengerLocalStore passengerLocalStore;
    EditText etUsername,etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        etUsername=(EditText)findViewById(R.id.editTxtUserName);
        etPassword=(EditText)findViewById(R.id.editTxtPassword);


        Button bSignIn = (Button) findViewById(R.id.btnSignIn);
        bSignIn.setOnClickListener(this);

        Button bNewAccount = (Button) findViewById(R.id.btnNewAccount);
        bNewAccount.setOnClickListener(this);

        Button bResetPassword = (Button) findViewById(R.id.btnResetPassword);
        bResetPassword.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignIn:
                String username = etUsername.getText().toString();
                String password = etUsername.getText().toString();

                Passenger passenger = new Passenger(username,password);
                authenticate(passenger);
                //startActivity(new Intent(SignActivity.this,ServiceActivity.class));
                break;

            case R.id.btnNewAccount:
                Intent intentNewAccount = new Intent(SignActivity.this,RegistrationActivity.class);
                startActivity(intentNewAccount);
                break;
            case R.id.btnResetPassword:
                //Intent intentForgetPassword = new Intent(SignActivity.this,ForgetPasswordActivity.class);
                //startActivity(intentForgetPassword);
                //break;
        }
    }
    private void authenticate(Passenger passenger){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchPassengerDataInBackGround(passenger,new GetPassengerCallback() {
            @Override
            public void done(Passenger returnedPassenger) {
                if(returnedPassenger == null){
                    showErrorMessage();
                }else {
                    logPassengerIn(returnedPassenger);
                }
            }
        });
    }

    private  void showErrorMessage(){
        AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(SignActivity.this);
        dialogBuilder.setMessage("Incorrect User Details");
        dialogBuilder.setPositiveButton("ok",null);
        dialogBuilder.show();
    }

    private void logPassengerIn(Passenger returnedPassenger){
        passengerLocalStore.storePassengerData(returnedPassenger);
        passengerLocalStore.setPassengerLoggedIn(true);
        startActivity(new Intent(this,ServiceActivity.class));
    }

    class SiginLink extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {

            return null;
        }
    }
}
