package com.example.slr_e_ticketer.slr_e_ticketer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Shalika on 6/10/2015.
 */
public class PassengerLocalStore {
    public static final String SP_NAME="passengerDetails";
    SharedPreferences passengerLocalDatabase;


    public PassengerLocalStore(Context context){
        passengerLocalDatabase =context.getSharedPreferences(SP_NAME,0);
    }
    public void storePassengerData(Passenger passenger){
        SharedPreferences.Editor spEditor=passengerLocalDatabase.edit();
        spEditor.putString("firstName",passenger.firstName);
        spEditor.putString("lastName",passenger.lastName);
        spEditor.putString("nic",passenger.nic);
        spEditor.putString("email",passenger.email);
        spEditor.putString("phoneNumber",passenger.phoneNumber);
        spEditor.putString("username",passenger.userName);
        spEditor.putString("password",passenger.password);
        spEditor.commit();
    }

    public Passenger getLoggedInPassenger(){
        String firstName=passengerLocalDatabase.getString("firstName","");
        String lastName=passengerLocalDatabase.getString("lastName","");
        String nic=passengerLocalDatabase.getString("nic","");
        String email=passengerLocalDatabase.getString("email","");
        String phoneNumber=passengerLocalDatabase.getString("phoneNumber","");
        String username=passengerLocalDatabase.getString("username","");
        String password=passengerLocalDatabase.getString("password","");

        Passenger storedPassenger = new Passenger(firstName,lastName,nic,email,phoneNumber,username,password);
        return storedPassenger;
    }

    public void setPassengerLoggedIn(boolean LoggedIn){
        SharedPreferences.Editor spEditor = passengerLocalDatabase.edit();
        spEditor.putBoolean("LoggedIn",LoggedIn);
        spEditor.commit();
    }

    public boolean getPassengerLoggedIn(){
        if(passengerLocalDatabase.getBoolean("LoggedIn",false) && true){
            return true;
        }else{
            return false;
        }
    }
    public void clearPassengerData(){
        SharedPreferences.Editor spEditor=passengerLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }
}
