package com.mytrips.mytripsbackend;


import com.mytrips.mytripsbackend.beans.Trip;
import com.mytrips.mytripsbackend.beans.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rocke
 */
public class DatabaseHandler {

    private Connection con;
        
    public DatabaseHandler(){
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/trips"
                    + "?autoReconnect=true&useSSL=false", "root", "");
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    User login(String email, String password) {
        User user = null;
        
        try {
            PreparedStatement pst = con.prepareStatement("select * from "
                    + "users where email = ?");
            pst.setString(1, email);
            ResultSet resultset = pst.executeQuery();
            
            if (resultset.next()) {
                if(password.equalsIgnoreCase(resultset.getString("password"))){
                    user = new User(resultset.getInt("id"),  resultset.getString("email"),
                            resultset.getString("password"), resultset.getString("fullName"));
                }
                else{
                    System.out.println("Wrong Password!");
                }
            }
            else{
                System.out.println("This email doesn't exist!");
            }
            pst.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }

    boolean register(String email, String password, String fullName) {
        boolean success = false;
        
        try {
            PreparedStatement pst = con.prepareStatement("insert into users(email, "
                    + "password, fullName) values (?, ?, ?)");
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3, fullName);
            int affected = pst.executeUpdate();
            if(affected == 1)
                success = true;
            
            pst.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }

    boolean synchronize(ArrayList<Trip> trips) {
        boolean success = false;
        String query = "select * from trips where userId = " + trips.get(0).
                getUserId();
        
        for (Trip trip : trips) {
            
        }
        
        return success;
    }  
}
