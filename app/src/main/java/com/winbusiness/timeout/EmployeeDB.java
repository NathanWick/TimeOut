package com.winbusiness.timeout;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

public class EmployeeDB extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 19;
    private static final String DATABASE_NAME = "SpotOn.db";

    public static final String TABLE_EMPLOYEES = "Employees";
    public static final String COLUMN_EMPLOYEE_ID = "id";
    public static final String COLUMN_FIRST_NAME = "firstname";
    public static final String COLUMN_LAST_NAME = "lastname";
    public static final String COLUMN_PHONE_1 = "phone1";
    public static final String COLUMN_PHONE_2 = "phone2";
    public static final String COLUMN_EMAIL_ADRESS = "emailadress";
    public static final String COLUMN_HOME_ADRESS = "homeadress";
    public static final String COLUMN_HIRE_DATE = "hiredate";
    public static final String COLUMN_TERMINATION_DATE = "terminationdate";
    public static final String COLUMN_DEFAULT_RATE = "defaultrate";
    public static final String COLUMN_ACCESS_CODE = "accesscode";

    public EmployeeDB(Context context, String name, CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }



    public int addEmployee(Employee employee)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, employee.getFirstName());
        values.put(COLUMN_LAST_NAME, employee.getLastName());
        values.put(COLUMN_PHONE_1, employee.getPhone1());
        values.put(COLUMN_PHONE_2, employee.getPhone2());
        values.put(COLUMN_EMAIL_ADRESS, employee.getEmailAdress());
        values.put(COLUMN_HOME_ADRESS, employee.getHomeAdress());
        values.put(COLUMN_HIRE_DATE, employee.getHireDate());
        values.put(COLUMN_TERMINATION_DATE, employee.getTerminationDate());
        values.put(COLUMN_DEFAULT_RATE, employee.getDefaultRate());
        values.put(COLUMN_ACCESS_CODE, employee.getAccessCode());
        values.put("position", employee.getPosition());

        SQLiteDatabase db = getWritableDatabase();

        int id = (int) db.insert(TABLE_EMPLOYEES, null, values);
        employee.setId(id);
        db.close();

        return id;
    }

    public void deleteEmployee(String employeeName)
    {
        String query = "DELETE FROM " + TABLE_EMPLOYEES + " WHERE " + COLUMN_FIRST_NAME + "=\"" + employeeName + "=\";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }

    public Employee getEmployee(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE id = " + String.valueOf(id) + ";";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        Employee emp = null;
        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
        {
            emp = new Employee(c.getString(c.getColumnIndex("firstname")), (c.getString(c.getColumnIndex("lastname"))), (c.getInt(c.getColumnIndex("accesscode"))), c.getInt(c.getColumnIndex("id")));
        }
        db.close();
        return emp;
    }

    public void updateEmployee(Employee employee){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Update  " + TABLE_EMPLOYEES + " SET FirstName = '" + employee.getFirstName() + "', LastName = '" + employee.getLastName() + "', accesscode = '" + employee.getAccessCode() + "'  WHERE id = " + String.valueOf(employee.getId()) + ";";

        db.execSQL(query);
        db.close();
    }

    public void populateEmployeeArray(ArrayList employeeList)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EMPLOYEES + " WHERE 1;";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext() )
        {
            employeeList.add(0, new Employee(c.getString(c.getColumnIndex("firstname")), (c.getString(c.getColumnIndex("lastname"))), (c.getInt(c.getColumnIndex("accesscode"))), c.getInt(c.getColumnIndex("id"))));
        }
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE " + TABLE_EMPLOYEES + "(" + COLUMN_EMPLOYEE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_FIRST_NAME + " varchar(50), "  + COLUMN_LAST_NAME + " varchar(50), " + COLUMN_PHONE_1 + " INTEGER, " + COLUMN_PHONE_2 + " INTEGER, " + COLUMN_EMAIL_ADRESS + " varchar(100), " + COLUMN_HOME_ADRESS + " varchar(100), " + COLUMN_HIRE_DATE + " varchar(50), " + COLUMN_TERMINATION_DATE + " varchar(50), " + COLUMN_DEFAULT_RATE + " decimal(12,2), " + COLUMN_ACCESS_CODE + " INTEGER, position integer);";
        db.execSQL(query);
        query = "Create table tbClockInOut (Clockinoutkey integer primary key autoincrement, employeeid integer, timein datetime, timeout datetime, adjtimein datetime, adjtimeout datetime, adjtimeinapprovedby integer, adjtimeoutapprovedby integer);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEES;
        db.execSQL(query);
        query = "Drop table if exists tbclockinout";
        db.execSQL(query);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_EMPLOYEES;
        db.execSQL(query);
        onCreate(db);
    }
}
