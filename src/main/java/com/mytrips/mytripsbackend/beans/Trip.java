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

    String id;
    int userId;
    String name;
    String start;
    double startX;
    double startY;
    String end;
    double endX;
    double endY;
    String date;
    String time;
    String status;
    int done;       //represented as int in db
    ArrayList<Note> notes; //ignore notes for now
    String image;
    long milliSeconds;
    int alarmId;

    public Trip(String id, int userId, String name, String start, double startX,
            double startY, String end, double endX, double endY, String date,
            String time, String status, int done, ArrayList<Note> notes,
            String image, int alarmId, long milliSeconds) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.start = start;
        this.startX = startX;
        this.startY = startY;
        this.end = end;
        this.endX = endX;
        this.endY = endY;
        this.date = date;
        this.time = time;
        this.status = status;
        this.done = done;
        this.notes = notes;
        this.image = image;
        this.alarmId = alarmId;
        this.milliSeconds = milliSeconds;
    }

    public Trip() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public double getStartX() {
        return startX;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public double getStartY() {
        return startY;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public double getEndX() {
        return endX;
    }

    public void setEndX(double endX) {
        this.endX = endX;
    }

    public double getEndY() {
        return endY;
    }

    public void setEndY(double endY) {
        this.endY = endY;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getMilliSeconds() {
        return milliSeconds;
    }

    public void setMilliSeconds(long milliSeconds) {
        this.milliSeconds = milliSeconds;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }
}
