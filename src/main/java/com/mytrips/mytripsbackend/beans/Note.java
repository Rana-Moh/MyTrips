/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytrips.mytripsbackend.beans;

/**
 *
 * @author rocke
 */
public class Note {
    String id;
    String tripId;
    String note;

    public Note(String id, String tripId, String note) {
        this.id = id;
        this.tripId = tripId;
        this.note = note;
    }
    
    public Note(){
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
