package com.example.slr_e_ticketer.slr_e_ticketer;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ServiceActivity extends ActionBarActivity implements View.OnClickListener{
    Button bPurchaseTicket,bReserveTicket,bCancellation,bMySchedules,bMyTickets,bSettings;
    EditText etFirstName,etLastName,etNic,etEmail,etPhoneNumber,etUserName,etPassword;
    PassengerLocalStore passengerLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        etFirstName =(EditText)findViewById(R.id.editTxtFirstName);
        etLastName =(EditText)findViewById(R.id.editTxtLastName);
        etNic =(EditText)findViewById(R.id.editTxtNic);
        etEmail =(EditText)findViewById(R.id.editTxtEmail);
        etPhoneNumber =(EditText)findViewById(R.id.editTxtPhoneNumber);
        etUserName =(EditText)findViewById(R.id.editTxtUserName);
        etPassword =(EditText)findViewById(R.id.editTxtPassword);

        bPurchaseTicket = (Button)findViewById(R.id.btnPurchaseTicket);
        bReserveTicket = (Button)findViewById(R.id.btnReceiveSeat);
        bCancellation = (Button)findViewById(R.id.btnCancellation);
        bMySchedules = (Button)findViewById(R.id.btnMySchedules);
        bMyTickets = (Button)findViewById(R.id.btnMyTicket);
        bSettings=(Button)findViewById(R.id.btnSettings);

        passengerLocalStore = new PassengerLocalStore(this);
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPurchaseTicket:
                //passengerLocalStore.clearPassengerData();
                //passengerLocalStore.setPassengerLoggedIn(false);
                Intent myIntent = new Intent(ServiceActivity.this, PurchaseTicket.class);
                startActivity(myIntent);

            case R.id.btnReceiveSeat:
            case R.id.btnCancellation:
            case R.id.btnMySchedules:
            case R.id.btnMyTicket:
            case R.id.btnSettings:
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(authenticate()==true){
           displayPassengerDetails();
        }else{
            startActivity(new Intent(ServiceActivity.this,SignActivity.class));
        }
    }

    private boolean authenticate(){
        return passengerLocalStore.getPassengerLoggedIn();
    }

    private void displayPassengerDetails(){
        Passenger passenger=passengerLocalStore.getLoggedInPassenger();

        etFirstName.setText(passenger.firstName);
        etLastName.setText(passenger.lastName);
        etNic.setText(passenger.nic);
        etEmail.setText(passenger.phoneNumber);
        etUserName.setText(passenger.userName);
        etPassword.setText(passenger.password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
