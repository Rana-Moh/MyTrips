package com.mytrips.mytripsbackend;

import com.mytrips.mytripsbackend.beans.Note;
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
    
    //***************USER OPERATIONS**************//

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
            ArrayList<Note> notes = trip.getNotes();
            
            if(tripExists(trip.getId())){
                updateTrip(trip);
                for(int j = 0; j < notes.size(); j++){
                    if(noteExists(notes.get(j).getId())){
                        updateNote(notes.get(j));
                    }
                    else{
                        insertNote(notes.get(j));
                    }
                }
            }
            else{
                insertTrip(trip);
            }
        }

        success = true;
        return success;
    }
    
    //***************TRIP OPERATIONS**************//
    
    public boolean tripExists(String tripId){
        boolean exists = false;
        
        try {          
            PreparedStatement pst = con.prepareStatement("select id from "
                    + "trips where id = ?");
            pst.setString(1, tripId);
            ResultSet resultset = pst.executeQuery();
            
            if (resultset.next()) {
                exists = true;
            }
            
        }   catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
    }
        
    public void insertTrip(Trip trip){
        try {
            String query = "insert into trips(id, userId, name, start, startX, startY, end,"
                    + " endX, endY, date, time, status, done, image, alarmId) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, trip.getId());
                pst.setInt(2, trip.getUserId());
                pst.setString(3, trip.getName());
                pst.setString(4, trip.getStart());
                pst.setDouble(5, trip.getStartX());
                pst.setDouble(6, trip.getStartY());
                pst.setString(7, trip.getEnd());
                pst.setDouble(8, trip.getEndX());
                pst.setDouble(9, trip.getEndY());
                pst.setString(10, trip.getDate());
                pst.setString(11, trip.getTime());
                pst.setString(12, trip.getStatus());
                pst.setInt(13, trip.getDone());
                pst.setString(14, trip.getImage());
                pst.setString(15, trip.getAlarmId());
                
                ArrayList<Note> notes = trip.getNotes();
                for(int i = 0; i < notes.size(); i++){
                    insertNote(notes.get(i));
                }
                
                pst.executeUpdate();
                System.out.println("Trip inserted.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateTrip(Trip trip){
        String query = "update trips set name = ?, start = ?, startX = ?, startY = ?, end = ?,"
                + " endX = ?, endY = ?, date = ?, time = ?, status = ?, done = ?, image = ?, alarmId = ? where id = ?;";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, trip.getName());
            pst.setString(2, trip.getStart());
            pst.setDouble(3, trip.getStartX());
            pst.setDouble(4, trip.getStartY());
            pst.setString(5, trip.getEnd());
            pst.setDouble(6, trip.getEndX());
            pst.setDouble(7, trip.getEndY());
            pst.setString(8, trip.getDate());
            pst.setString(9, trip.getTime());
            pst.setString(10, trip.getStatus());
            pst.setInt(11, trip.getDone());
            pst.setString(12, trip.getImage());
            pst.setString(13, trip.getAlarmId());
            pst.setString(14, trip.getId());
            
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
                trip.setStartX(rs.getDouble("startX"));
                trip.setStartY(rs.getDouble("startY"));
                trip.setEnd(rs.getString("end"));
                trip.setEndX(rs.getDouble("endX"));
                trip.setEndY(rs.getDouble("endY"));
                trip.setDate(rs.getString("date"));
                trip.setTime(rs.getString("time"));
                trip.setStatus(rs.getString("status"));
                trip.setDone(rs.getInt("done"));
                ArrayList<Note> notes = getTripNotes(trip.getId());
                trip.setNotes(notes);
                trip.setImage(rs.getString("image"));
                trip.setAlarmId("alarmId");
                
                trips.add(trip);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return trips;
    }
    
    //***************NOTES OPERATIONS**************//
    
    public ArrayList<Note> getTripNotes(String tripId){
        ArrayList<Note> notes = new ArrayList<>();
        
        try (PreparedStatement pst = con.prepareStatement("select * from "
                + "notes where tripId = ?")) {
            pst.setString(1, tripId);
            ResultSet rs = pst.executeQuery();
            
            Note note;
            while(rs.next()) {
                note = new Note();
                note.setId(rs.getString("id"));
                note.setTripId(rs.getString("tripId"));
                note.setNote(rs.getString("note"));
                
                notes.add(note);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return notes;
    }
    
    public boolean noteExists(String noteId){
        boolean exists = false;
        
        try {          
            PreparedStatement pst = con.prepareStatement("select id from "
                    + "notes where id = ?");
            pst.setString(1, noteId);
            ResultSet resultset = pst.executeQuery();
            
            if (resultset.next()) {
                exists = true;
            }
            
        }   catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return exists;
    }
    
    public void insertNote(Note note){
        try {
            String query = "insert into notes(id, tripId, note) values (?,?,?);";
            
            try (PreparedStatement pst = con.prepareStatement(query)) {
                pst.setString(1, note.getId());
                pst.setString(2, note.getTripId());
                pst.setString(3, note.getNote());
                
                pst.executeUpdate();
                System.out.println("Note inserted.");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateNote(Note note){
        String query = "update notes set note = ? where id = ?;";

        try (PreparedStatement pst = con.prepareStatement(query)) {
            pst.setString(1, note.getNote());
            pst.setString(2, note.getId());
            
            pst.executeUpdate();
            System.out.println("Note updated.");
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
