/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mytrips.mytripsbackend.beans;

import java.util.ArrayList;

/**
 *
 * @author rocke
 */

public class Trip {

    int id;
    int userId;
    String start;
    String startCoord;
    String end;
    String endCoord;
    String date;
    String time;
    String status;
    int done;       //represented as int in db
    String[] notes;

    public Trip(int id, int userId, String start, String startCoord, String end, String endCoord,
                String date, String time, String status, int done, String[] notes) {
        this.id = id;
        this.userId = userId;
        this.start = start;
        this.startCoord = startCoord;
        this.end = end;
        this.endCoord = endCoord;
        this.date = date;
        this.time = time;
        this.status = status;
        this.done = done;
        this.notes=notes;
    }

    public Trip() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String[] getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int isDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public String getStartCoord() {
        return startCoord;
    }

    public void setStartCoord(String startCoord) {
        this.startCoord = startCoord;
    }

    public String getEndCoord() {
        return endCoord;
    }

    public void setEndCoord(String endCoord) {
        this.endCoord = endCoord;
    }
}

