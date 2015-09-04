package com.winbusiness.timeout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;

public class ClockInOutDB extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = EmployeeDB.DATABASE_VERSION ;
    private static final String DATABASE_NAME = "SpotOn.db";

    public static final String TABLE_EMPLOYEES = "ClockInOut";
    public static final String COLUMN_EMPLOYEE_ID = "id";
    public ClockInOutDB(Context context, String name, CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void clockIn(Employee employee){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        // String sql = "insert into tbClockInOut (employeeid, timein) select "  + employee.getId() + ", '" + c.getTime().toString() + "' ";
        String sql = "insert into tbClockInOut (employeeid, timein) select "  + employee.getId() + ", '" + strDate + "' ";


		/*values.put("employeeid", employee.getId());
		values.put("timein" c.getTime().toString());*/
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        //db.insert("tbClockInOut", null, values);
        db.close();
    }

    public void clockOut(Employee employee){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        // String sql = "insert into tbClockInOut (employeeid, timein) select "  + employee.getId() + ", '" + c.getTime().toString() + "' ";
        //String sql = "insert into tbClockInOut (employeeid, timeout) select "  + employee.getId() + ", '" + c.getTime().toString() + "' ";
        //String sql = "update tbClockInOut set timeout = '" + strDate + "' where employeeid = " + employee.getId() + " and timeout is null and timein > date('now', '-1 day')";
        //String sql = "insert into tbClockInOut (employeeid, timeout) select "  + employee.getId() + ", '" + strDate + "' ";
        String sql = "update tbClockInOut set timeout = '" + strDate + "' where employeeid = " + employee.getId() + " and timeout is null/* and timein > date('now', '-1 day')*/";

		/*values.put("employeeid", employee.getId());
		values.put("timein" c.getTime().toString());*/
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sql);
        //db.insert("tbClockInOut", null, values);
        db.close();
    }

    public String getQuery(String where){
        String query;
        query = "SELECT Clockinoutkey, employeeid, " +
                "       case when adjTimeIn not null then adjTimeIn else TimeIn end as timein,  " +
                "       case when AdjTimeOut not null then adjTimeOut else TimeOut end as timeout, " +
                "       adjTimeIn, adjTimeOut, strftime('%s', timeout ) - strftime('%s', timein) AS sduration, " +
                "       case when (AdjTimeIn not null and adjtimeinapprovedby is null) " +
                              "              or (AdjTimeOut not null and adjtimeoutapprovedby is null) then 1 else 0 end as NeedsApproval " +
                "       FROM tbClockInOut "
                + where;

//         +


        return query;
    }

    public void populateClockInOutArray(ArrayList clockInOutList, int viewLevel) {
        SQLiteDatabase db = getWritableDatabase();
        String query;


        switch(viewLevel){
            //returns all
            case (0):
                query = getQuery("");
                break;
            //returns all requests
            case(1):
                query = getQuery(" where (adjtimein not null) or (adjtimeout not null )");
                break;
            //returns all requests needed approval
            case(2):
                query = getQuery(" where (adjtimein not null and adjtimeinapprovedby is null) or (adjtimeout not null and adjtimeoutapprovedby is null )");
                break;
            //returns all approved requests
            case(3):
                query = getQuery(" where (adjtimeinapprovedby not null) or (adjtimeoutapprovedby not null )");
                break;

            default:
                query = getQuery("");
                break;
        }

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            clockInOutList.add(0, new ClockInOut(c.getInt(c.getColumnIndex("Clockinoutkey")), c.getInt(c.getColumnIndex("employeeid")), c.getString(c.getColumnIndex("timein")), c.getString(c.getColumnIndex("timeout")), c.getInt(c.getColumnIndex("sduration")), c.getInt(c.getColumnIndex("NeedsApproval"))));
        }
        db.close();
    }


    public void populateClockInOutArray(ArrayList clockInOutList,  int viewLevel, Employee employee) {
        SQLiteDatabase db = getWritableDatabase();
        String query;
        String where = " where employeeid = " + employee.getId();
        switch(viewLevel){
            //returns all for specific employee
            case (0):
                query = getQuery(where);
                break;
            //returns all requests for specific employee
            case(1):
                query = getQuery(where + " and (adjtimein not null) or (adjtimeout not null )");
                break;
            //returns all requests needed approval for specific employee
            case(2):
                query = getQuery(where + " and (adjtimein not null and adjtimeinapprovedby Null) or (adjtimeout not null and adjtimeoutapprovedby null )");
                break;
            //returns all approved requests for specific employee
            case(3):
                query = getQuery(where + " and (adjtimeinapprovedby not null) or (adjtimeoutapprovedby not null )");
                break;
            default:
                query = getQuery("");
                break;
        }

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            clockInOutList.add(0, new ClockInOut(c.getInt(c.getColumnIndex("Clockinoutkey")), c.getInt(c.getColumnIndex("employeeid")), c.getString(c.getColumnIndex("timein")), c.getString(c.getColumnIndex("timeout")), c.getInt(c.getColumnIndex("sduration")), c.getInt(c.getColumnIndex("NeedsApproval"))));
        }
        db.close();
    }

    public void approveClockInOutUpdateRequest(ClockInOut clockInOut){

    }

    public String getClockInTime(Employee emp){
        String query = "SELECT  cast (timein  as  text) as timein  from tbclockinout where employeeid = " + emp.getId() + " and timeout is null";
        Cursor c = getWritableDatabase().rawQuery(query, null);
        c.moveToFirst();
        if (c.getCount() > 0 )   return c.getString(c.getColumnIndex("timein"));
        else return null;
    }

    public void updateClockInTime(ClockInOut c, String date){
        //update tbClockInOut set adjtimein time where clockinoutid = c.getid

        getWritableDatabase().execSQL("update tbClockInOut set adjtimein = '" + date + "' where Clockinoutkey = " + c.getClockInOutId());
      //set form value = the results
    }

    public void updateClockOutTime(ClockInOut c, String date){
        //update tbClockInOut set adjtimein time where clockinoutid = c.getid

        getWritableDatabase().execSQL("update tbClockInOut set adjtimeout = '" + date + "' where Clockinoutkey = " + c.getClockInOutId());
        //set form value = the results
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
     /////   String query = "Create table tbClockInOut (Clockinoutkey integer primary key autoincrement, employeeid integer, timein varchar(50), timeout varchar(50));";
        ////db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        String query = "drop table if exists tbClockInOut";
        db.execSQL(query);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS tbClockInOut";
        db.execSQL(query);
        onCreate(db);

    }
}
