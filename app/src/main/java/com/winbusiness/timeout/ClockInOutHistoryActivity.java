package com.winbusiness.timeout;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nathan on 8/5/2015.
 */
public class ClockInOutHistoryActivity extends AppCompatActivity{
    ListView ls;
    ArrayList<ClockInOut> clockInOutList;

    ClockInOutDB handler;
    EmployeeDB empHandler;

    Intent intent;
    Bundle bundle;
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clockinout_history);

        instanciateVariables();
    }

    private void instanciateVariables(){
        ls = (ListView) findViewById(R.id.listView);
        clockInOutList = new ArrayList<ClockInOut>();
        handler = new ClockInOutDB(this, null, null, 1);
        empHandler = new EmployeeDB(this, null, null, 1);

        handler.populateClockInOutArray(clockInOutList, 0);
        ListViewAdapter arrayAdapter = new ListViewAdapter();
        ls.setAdapter(arrayAdapter);

        intent = getIntent();
        bundle = intent.getExtras();
        employee = (Employee) intent.getSerializableExtra("obj");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_clockinout_history, menu);
        return true;
    }

    public class ListViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return clockInOutList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getViewTypeCount(){
            return 2;
        }

        @Override
        public int getItemViewType(int position){
            ClockInOut c = clockInOutList.get(position);
            int b = 0;
            if(c.needsAjd() == 0){
                b = 0;
            }else if(c.needsAjd() == 1){
                b = 1;
            }
            return b;
        }

        public ListViewAdapter()
        {
            super();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView = convertView;
            int type = getItemViewType(position);

            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.row_clockinout_history, parent, false);
            }

            final ClockInOut c = clockInOutList.get(position);
            final Employee emp = empHandler.getEmployee(c.getEmployeeId());

            switch (type){
                case 0:
                    itemView = getLayoutInflater().inflate(R.layout.row_clockinout_history, parent, false);
                    TextView tv1 = (TextView) itemView.findViewById(R.id.tv1);
                    TextView tv2 = (TextView) itemView.findViewById(R.id.tv2);
                    TextView tv3 = (TextView) itemView.findViewById(R.id.tv3);
                    TextView tv4 = (TextView) itemView.findViewById(R.id.tv4);
                    TextView tv5 = (TextView) itemView.findViewById(R.id.tv5);
                    tv1.setText(emp.getId() + " " + emp.getFirstName());
                    tv2.setText(c.getTimeIn());
                    tv3.setText(c.getTimeOut());
                    tv4.setText(c.getDuration() + "");
                    tv5.setText(String.valueOf(emp.isClockedIn()));
                    break;
                case 1:
                    itemView = getLayoutInflater().inflate(R.layout.row_needs_approval, parent, false);
                    TextView tv6 = (TextView) itemView.findViewById(R.id.tv12);
                    TextView tv7 = (TextView) itemView.findViewById(R.id.tv13);
                    TextView tv8 = (TextView) itemView.findViewById(R.id.tv14);
                    Button tv9 = (Button) itemView.findViewById(R.id.tv15);
                    Button tv10 = (Button) itemView.findViewById(R.id.tv16);
                    tv6.setText(emp.getId() + " " + emp.getFirstName());
                    tv7.setText(c.getTimeIn());
                    tv8.setText(c.getTimeOut());
                    tv9.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplication(), emp.getFirstName() + " APPROVED BY " + employee.getFirstName(), Toast.LENGTH_SHORT).show();
                            handler.getWritableDatabase().execSQL("update tbclockinout set adjtimeinapprovedby = " + employee.getId() + " where clockinoutkey = " + c.getClockInOutId());
                            finish();
                            startActivity(getIntent());
                        }
                    });

                    tv10.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplication(), emp.getFirstName() + " DENIED BY " + employee.getFirstName(), Toast.LENGTH_SHORT).show();
                            handler.getWritableDatabase().execSQL("update tbclockinout set adjtimeinapprovedby = " + employee.getId() + " where clockinoutkey = " + c.getClockInOutId());
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    break;
            }
            return itemView;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSortAll:
                clockInOutList.clear();
                handler.populateClockInOutArray(clockInOutList, 0);
                ls.setAdapter(new ListViewAdapter());
                return true;
            case R.id.menuSortNeedsApproval:
                clockInOutList.clear();
                handler.populateClockInOutArray(clockInOutList, 2);
                ls.setAdapter(new ListViewAdapter());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
