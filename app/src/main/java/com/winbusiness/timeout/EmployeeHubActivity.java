package com.winbusiness.timeout;

import java.net.PasswordAuthentication;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInstaller;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmployeeHubActivity extends Activity implements
        OnClickListener, TimePickerDialog.OnTimeSetListener {

    Button clockIn;
    Button clockOut;
    TextView welcomeText;
    Intent thisIntent;
    Bundle bundle;
    ClockInOutDB handler;
    EmployeeDB empHandler;
    ClockInOutDB clockInOutDB;
    Employee emp;
    ViewSwitcher vs;
    ListView ls;
    MyListAdapter mls;
    ArrayList<ClockInOut> clockInOutList;
    private Boolean clockedIn;Button tv3, tv2;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_hub);

        createViewObjects();
       //setWelcomeText();
    }

    private void setWelcomeText() {
        /*String query = "SELECT * FROM " + EmployeeDB.TABLE_EMPLOYEES + " WHERE id = " + emp.getId();
        SQLiteDatabase db = handler.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();*/
        welcomeText.setText(emp.getFirstName() + " " + emp.getLastName());
    }

    private void createViewObjects() {
        handler = new ClockInOutDB(this, null, null, 1);
        empHandler = new EmployeeDB(this, null, null, 1);
        clockInOutList = new ArrayList<ClockInOut>();
        thisIntent = getIntent();
        bundle = thisIntent.getExtras();
        clockIn = (Button) findViewById(R.id.buttonClockIn);
        clockOut = (Button) findViewById(R.id.buttonClockOut);
        welcomeText = (TextView) findViewById(R.id.textView1);
        emp = (Employee) thisIntent.getSerializableExtra("obj");
        handler.populateClockInOutArray(clockInOutList, 0, emp);
        Button[] buttons = {clockIn, clockOut};
        vs = (ViewSwitcher) findViewById(R.id.viewSwitchterEmpHub);
        ls = (ListView) findViewById(R.id.listView2);
        mls = new MyListAdapter();
        ls.setAdapter(mls);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();

        if(handler.getClockInTime(emp) == null){
            emp.setIsClockedIn(true);
            vs.setDisplayedChild(0);
        }else{
            emp.setIsClockedIn(false);
            vs.setDisplayedChild(1);
        }

        for (Button b : buttons) {
            b.setOnClickListener(this);
            b.setTextColor(getResources().getColor(R.color.numberColor));
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }

    private class MyListAdapter extends ArrayAdapter<ClockInOut>
    {
        public MyListAdapter()
        {
            super(EmployeeHubActivity.this, R.layout.row_employee_hub, clockInOutList);
        }

        public View getView(final int position, View convertView, ViewGroup parent)
        {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.row_employee_hub, parent, false);

            }

            ClockInOut c = clockInOutList.get(position);
           // itemView.setTag(5, c.getClockInOutId());
            TextView tv1 = (TextView) itemView.findViewById(R.id.tv6);
            tv2 = (Button) itemView.findViewById(R.id.tv7);
            tv3 = (Button) itemView.findViewById(R.id.tv8);
            tv2.setTag(c);

            //onclick for the clockin button
            tv2.setOnClickListener(new OnClickListener() {
                Calendar c = Calendar.getInstance();

                @Override
                public void onClick(View v) {
                    final Button b = (Button) v;
                    TimePickerDialog tp = new TimePickerDialog(EmployeeHubActivity.this, new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                            Date now = new Date();
                            now.setHours(hourOfDay);
                            now.setMinutes(minute);
                            String strDate = sdfDate.format(now);
                            c.setTime(now);


                            ClockInOut cu = (ClockInOut) b.getTag();
                            Toast.makeText(getApplicationContext(), "   ", Toast.LENGTH_SHORT).show();
                            b.setText(strDate);

                            handler.updateClockInTime(clockInOutList.get(position), strDate);


                        }
                    }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), false);
                    tp.setButton(DialogInterface.BUTTON_POSITIVE, "Request", tp);
                    tp.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", tp);
                    tp.show();

                }

            });





            //On click for the clockout button
            tv3.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Button b = (Button) v;
                    TimePickerDialog tp = new TimePickerDialog(EmployeeHubActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                            Date now = new Date();
                            now.setHours(hourOfDay);
                            now.setMinutes(minute);
                            String strDate = sdfDate.format(now);
                            c.setTime(now);


                            ClockInOut cu = (ClockInOut) b.getTag();
                            Toast.makeText(getApplicationContext(), "   ", Toast.LENGTH_SHORT).show();
                            b.setText(strDate);

                            handler.updateClockOutTime(clockInOutList.get(position), strDate);
                        }
                    }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), false);
                    tp.setButton(DialogInterface.BUTTON_POSITIVE, "Request", tp);
                    tp.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", tp);
                    tp.show();
                }
            });

            TextView tv4 = (TextView) itemView.findViewById(R.id.tv9);
            TextView tv5 = (TextView) itemView.findViewById(R.id.tv10);
            Employee emp = empHandler.getEmployee(c.getEmployeeId());
            tv1.setText(emp.getId() + " " + emp.getFirstName());
            tv1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "TEST", Toast.LENGTH_SHORT).show();
                }
            });
            //tv2.setTag(Integer.parseInt("clockinoutid"), c.getClockInOutId());

            tv2.setText(c.getTimeIn());
            tv3.setText(c.getTimeOut());
            tv4.setText(c.getDuration() + "");
            tv5.setText(c.getClockInOutId() + "");






            return itemView;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.buttonClockIn):
                handler.clockIn(emp);
                Toast.makeText(getApplicationContext(), "Clocked In",

                        Toast.LENGTH_SHORT).show();
                vs.showNext();
                finish();
                startActivity(getIntent());
                break;
            case (R.id.buttonClockOut):
                handler.clockOut(emp);
                Toast.makeText(getApplicationContext(), "Clocked Out",
                        Toast.LENGTH_SHORT).show();
                vs.showNext();
                finish();
                break;
            default:
                break;
        }
    }
}
