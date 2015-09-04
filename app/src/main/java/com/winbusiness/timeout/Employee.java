package com.winbusiness.timeout;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.sql.Date;

public class Employee implements Serializable
{
    private int id;
    private String firstName;
    private String lastName;
    private int phone1;
    private int phone2;
    private String emailAdress;
    private String homeAdress;
    private String hireDate;
    private String terminationDate;
    private double defaultRate;
    private int accessCode;
    private boolean isClockedIn;
    private int position;
    private ClockInOutDB handler;

    public Employee(){

    }

    public Employee(String firstName, String lastName, int phone1, int phone2, String emailAdress, String homeAdress, String hireDate, String terminationDate, double defaultRate, int accessCode, int position)
    {
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
        this.phone1 = phone1 != 0 ? phone1 : 0;
        this.phone2 = phone2 != 0 ? phone2 : 0;
        this.emailAdress = emailAdress != null ? emailAdress : "";
        this.homeAdress = homeAdress != null ? homeAdress : "";
        this.hireDate = hireDate != null ? hireDate : "";
        this.terminationDate = terminationDate != null ? terminationDate : "";
        this.defaultRate = defaultRate != 0.0 ? defaultRate : 0.0;
        this.accessCode = accessCode != 0 ? accessCode : 0;
        this.position = position;
    }

    public Employee(String firstName, String lastName, int accessCode, int id)
    {
        this.firstName = firstName != null ? firstName : "";
        this.lastName = lastName != null ? lastName : "";
        phone1 = 0;
        phone2 = 0;
        emailAdress = "";
        homeAdress = "";
        hireDate = null;
        terminationDate = null;
        defaultRate = 0;
        this.accessCode = accessCode != 0 ? accessCode : 0;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPhone1() {
        return phone1;
    }

    public void setPhone1(int phone1) {
        this.phone1 = phone1;
    }

    public int getPhone2() {
        return phone2;
    }

    public void setPhone2(int phone2) {
        this.phone2 = phone2;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public void setEmailAdress(String emailAdress) {
        this.emailAdress = emailAdress;
    }

    public String getHomeAdress() {
        return homeAdress;
    }

    public void setHomeAdress(String homeAdress) {
        this.homeAdress = homeAdress;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(String terminationDate) { this.terminationDate = terminationDate;}

    public double getDefaultRate() {
        return defaultRate;
    }

    public void setDefaultRate(double defaultRate) {
        this.defaultRate = defaultRate;
    }

    public int getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(int accessCode) {
        this.accessCode = accessCode;
    }

    public void setId(int id) {this.id = id; }

    public int getId()
    {
        return id;
    }

    public boolean isClockedIn() {
        return isClockedIn;
    }

    public void setIsClockedIn(boolean isClockedIn) {
        this.isClockedIn = isClockedIn;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
