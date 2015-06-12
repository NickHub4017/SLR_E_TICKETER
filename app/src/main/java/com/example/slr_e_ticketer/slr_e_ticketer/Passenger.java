package com.example.slr_e_ticketer.slr_e_ticketer;

/**
 * Created by Shalika on 6/10/2015.
 */
public class Passenger{
    String firstName,lastName,nic,email,phoneNumber,userName,password;

    public Passenger(String firstName,String lastName,String nic,String email,String phoneNumber,String userName,String password){
        this.firstName=firstName;
        this.lastName=lastName;
        this.nic=nic;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.userName=userName;
        this.password=password;
    }
    public Passenger(String userName,String password){
        this.userName=userName;
        this.password=password;
    }

}
