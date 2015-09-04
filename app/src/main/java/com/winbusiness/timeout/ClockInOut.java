package com.winbusiness.timeout;

import android.app.Activity;

/**
 * Created by Nathan on 8/6/2015.
 */
public class ClockInOut{
    private int clockInOutId;
    private String timeIn;
    private String timeOut;
    private String timeInSeconds;
    private int employeeId;



    private String adjTimeIn;
    private String adjTimeOut;
    private int adjApprovedById;
    private int duration;
    private int needsAjd;

    public ClockInOut(int clockInOutId, int employeeId, String timeIn, String timeOut, int duration, int needsAjd){
        instanciateVariables();
        this.employeeId = employeeId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.duration = duration;
        this.clockInOutId = clockInOutId;
        this.needsAjd = needsAjd;
    }

    private void instanciateVariables(){

    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int shiftMinute) {
        this.duration = duration;
    }

    public void clockIn(int employeeId){

    }

    public void clockOut(int employeeId){

    }

    public void clockInAdjRequest(int clockInOutId, String adjTimeIn){

    }

    public void clockOutAdjRequest(int clockInOutId, String adjTimeOut){

    }

    public void clockInAdjApprove(int clockInOutId, int employeeId){

    }

    public void clockOutAdjApprove(int clockInOutId, int employeeId){

    }

    public void getInOutAdjRequests(){

    }

    public int getAdjApprovedById() {
        return adjApprovedById;
    }

    public void setAdjApprovedById(int adjApprovedById) {
        this.adjApprovedById = adjApprovedById;
    }

    public String getAdjTimeIn() {
        return adjTimeIn;
    }

    public void setAdjTimeIn(String adjTimeIn) {
        this.adjTimeIn = adjTimeIn;
    }

    public String getAdjTimeOut() {
        return adjTimeOut;
    }

    public void setAdjTimeOut(String adjTimeOut) {
        this.adjTimeOut = adjTimeOut;
    }

    public int getClockInOutId() {
        return clockInOutId;
    }

    public void setClockInOutId(int clockInOutId) {
        this.clockInOutId = clockInOutId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeInSeconds() {
        return timeInSeconds;
    }

    public void setTimeInSeconds(String timeInSeconds) {
        this.timeInSeconds = timeInSeconds;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }
    public int needsAjd() {
        return needsAjd;
    }
}
