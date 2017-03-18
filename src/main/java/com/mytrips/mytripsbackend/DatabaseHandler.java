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
        
        try (PreparedStatement pst = con.prepareStatement("select * from "
                + "users where email = ?")) {
            pst.setString(1, email);
            ResultSet resultset = pst.executeQuery();
            
            if (resultset.next()) {
                if(password.equalsIgnoreCase(resultset.getString("password"))){
                    user = new User(resultset.getInt("id"),  resultset.getString("email"),
                            resultset.getString("password"), resultset.getString("fullName"));
                    System.out.println("Successful Login!");
                }
                else{
                    System.out.println("Wrong Password!");
                }
            }
            else{
                System.out.println("This user doesn't exist!");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }

    boolean register(String email, String password, String fullName) {
        boolean success = false;
        int affected;
        
        try {

            if(!userExists(email)){
                PreparedStatement pst = con.prepareStatement("insert into users(email, "
                + "password, fullName) values (?, ?, ?)");
                pst.setString(1, email);
                pst.setString(2, password);
                pst.setString(3, fullName);
                
                affected = pst.executeUpdate();
                if(affected == 1)
                    success = true;

                pst.close();
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return success;
    }
    
    public boolean userExists(String email){
        boolean exists = false;
        
                try {            
            PreparedStatement pst = con.prepareStatement("select email from "
                    + "users where email = ?");
            pst.setString(1, email);
            ResultSet resultset = pst.executeQuery();
            
            if (resultset.next()) {
                if(email.equals(resultset.getString("email"))){
                    exists = true;
                }
            }
            
        }   catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return exists;
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
    
    public boolean tripExists(String tripId, int userId){
        boolean exists = false;
        
        try {          
        
            PreparedStatement pst = con.prepareStatement("select id from "
                    + "trips where id = ? and userId = ?");
            pst.setString(1, tripId);
            pst.setInt(2, userId);
            ResultSet resultset = pst.executeQuery();
            
            //TODO: compare b tare2a tanya 3shan el id ta7t hayb2a 8er el id fo2
            if (resultset.next()) {
                if(tripId.equals(resultset.getString("id"))){
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
            String query = "insert into trips(id, userId, start, startCoord, end,"
                    + " endCoord, date, time, status, done) values (?,?,?,?,?,?,?,?,?,?);";
            
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, trip.getId());
                pst.setInt(2, trip.getUserId());
                pst.setString(3, trip.getStart());
                pst.setString(4, trip.getStartCoord());
                pst.setString(5, trip.getEnd());
                pst.setString(6, trip.getEndCoord());
                pst.setString(7, trip.getDate());
                pst.setString(8, trip.getTime());
                pst.setString(9, trip.getStatus());
                pst.setInt(10, trip.isDone());
                
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
            pst.setString(9, trip.getId());
            
            pst.executeUpdate();
            System.out.println("Trip updated.");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Trip> getUserTrips(int userId) {
        ArrayList<Trip> trips = new ArrayList<>();
        
        try (PreparedStatement pst = con.prepareStatement("select * from "
                + "trips where userId = ?")) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            
            Trip trip;
            while(rs.next()) {
                trip = new Trip();
                trip.setId(rs.getString("id"));
                trip.setUserId(Integer.parseInt(rs.getString("userId")));
                trip.setStart(rs.getString("start"));
                trip.setStartCoord(rs.getString("startCoord"));
                trip.setEnd(rs.getString("end"));
                trip.setEndCoord(rs.getString("endCoord"));
                trip.setDate(rs.getString("date"));
                trip.setTime(rs.getString("time"));
                trip.setStatus(rs.getString("status"));
                trip.setDone(rs.getInt("done"));
                
                trips.add(trip);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return trips;
    }
}
