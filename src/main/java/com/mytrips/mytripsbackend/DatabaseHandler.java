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
 
        for (Trip trip : trips) {
            if(tripExists(trip.getId(), trip.getUserId()))
                updateTrip(trip);
            else
                insertTrip(trip);
        }

        success = true;
        return success;
    }
    
    public boolean tripExists(int tripId, int userId){
        boolean exists = false;
        
        try {            
            PreparedStatement pst = con.prepareStatement("select id from "
                    + "trips where id = ? and userId = ?");
            pst.setInt(1, tripId);
            pst.setInt(2, userId);
            ResultSet resultset = pst.executeQuery();
            
            //TODO: compare b tare2a tanya 3shan el id ta7t hayb2a 8er el id fo2
            if (resultset.next()) {
                if(tripId == resultset.getInt("id")){
                    exists = true;
                }
            }
            
        }   catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
    }
        
    public void insertTrip(Trip trip){
        try {
            //ignore notes for now
            String query = "insert into trips(userId, start, startCoord, end,"
                    + " endCoord, date, time, status, done) values (?,?,?,?,?,?,?,?,?);";
            
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setInt(1, trip.getUserId());
                pst.setString(2, trip.getStart());
                pst.setString(3, trip.getStartCoord());
                pst.setString(4, trip.getEnd());
                pst.setString(5, trip.getEndCoord());
                pst.setString(6, trip.getDate());
                pst.setString(7, trip.getTime());
                pst.setString(8, trip.getStatus());
                pst.setInt(9, trip.isDone());
                
                pst.executeUpdate();
                System.out.println("Trip inserted.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTrip(Trip trip){
        //ignore notes for now
        String query = "update trips set start = ?, startCoord = ?, end = ?,"
                + " endCoord = ?, date = ?, time = ?, status = ?, done = ? where id = ?;";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, trip.getStart());
            pst.setString(2, trip.getStartCoord());
            pst.setString(3, trip.getEnd());
            pst.setString(4, trip.getEndCoord());
            pst.setString(5, trip.getDate());
            pst.setString(6, trip.getTime());
            pst.setString(7, trip.getStatus());
            pst.setInt(8, trip.isDone());
            pst.setInt(9, trip.getId());
            
            pst.executeUpdate();
            System.out.println("Trip updated.");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
