package com.winbusiness.timeout;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ManagerHubActivity extends AppCompatActivity {
    ArrayList<Employee> employeeList;
    EmployeeDB handler;
    EditText sqlText;
    Button executeSql;
    Bundle bundle;
    Intent intent;
    Employee emp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_viewer);

        instantiateVariables();
        populateEmployeeListView();
    }

    private void instantiateVariables() {
        employeeList = new ArrayList<Employee>();
        handler = new EmployeeDB(this, null, null, 1);

        handler.populateEmployeeArray(employeeList);

        sqlText = (EditText) findViewById(R.id.editText);
        sqlText.setText("insert into employees (firstname, lastname, accesscode, position) select 'casey', 'wickland', 4661, 2 union all select 'john', 'wickland', 48, 1 union all select 'nathan', 'wickland', 21, 2 union all select 'Tracy', 'Loney', 30, 1 union all select 'Rambo', 'Orth', 87, 1 union all select 'Jessica', '', 96, 1 union all select 'T.J.', '', 303, 1 union all select 'Alissa', '', 44, 1 union all select 'Mike', '', 578, 1 union all select 'johnny', 'neal', 420, 1");
        executeSql = (Button) findViewById(R.id.Executebutton);
        intent = getIntent();
        bundle = intent.getExtras();
        emp = (Employee) intent.getSerializableExtra("obj");


        executeSql.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = handler.getWritableDatabase();
                db.execSQL(sqlText.getText().toString());

                finish();
                startActivity(getIntent());
            }
        });
    }

    private void populateEmployeeListView() {
        ArrayAdapter<Employee> adapter = new MyListAdapter();
        ListView listview = (ListView) findViewById(R.id.listView1);
        listview.setAdapter(adapter);







        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                TextView tv = (TextView) viewClicked;

                Employee emp = handler.getEmployee((int) tv.getTag());
                Toast.makeText(getApplicationContext(), position + " " + tv.getTag(), Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();
                bundle.putString("firstname", emp.getFirstName());
                bundle.putString("lastname", emp.getLastName());
                bundle.putInt("accesscode", emp.getAccessCode());
                bundle.putInt("id", emp.getId());
                startActivity(new Intent(ManagerHubActivity.this, EmployeeEditorActivity.class).putExtra("newEmp", false).putExtras(bundle));
            }
        });
    }
    private class MyListAdapter extends ArrayAdapter<Employee>
    {
        public MyListAdapter()
        {
            super(ManagerHubActivity.this, R.layout.row_employee_viewer, employeeList);
        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.row_employee_viewer, parent, false);

            }

            Employee emp = employeeList.get(position);

            TextView tv = (TextView) itemView.findViewById(R.id.tev1);
            tv.setText(emp.getFirstName());
            tv.setTag(emp.getId());




            return itemView;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_employee_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_employee:
                startActivity(new Intent(ManagerHubActivity.this, EmployeeEditorActivity.class).putExtra("newEmp", true));
                return true;
            case R.id.clockinout_history:
                startActivity(new Intent(ManagerHubActivity.this, ClockInOutHistoryActivity.class).putExtra("obj", emp));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        instantiateVariables();
        populateEmployeeListView();
    }
}
