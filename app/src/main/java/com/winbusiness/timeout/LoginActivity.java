package com.winbusiness.timeout;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.*;

public class LoginActivity extends Activity implements View.OnClickListener {

    EmployeeDB handler;
    Button button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonBackspace, buttonEnter;
    EditText editText1;
    ArrayList<String> employeeCodes;
    ArrayList<Employee> employees;
    Employee emp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createViewObjects();
    }

    private void createViewObjects() {
        handler = new EmployeeDB(this, null, null, 6);

        employees = new ArrayList<Employee>();
        handler.populateEmployeeArray(employees);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button0 = (Button) findViewById(R.id.button0);
        buttonBackspace = (Button) findViewById(R.id.buttonBack);
        buttonEnter = (Button) findViewById(R.id.buttonEnter);
        editText1 = (EditText) findViewById(R.id.editText1);

        Button[] buttons = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonEnter, buttonBackspace};

        for (Button b : buttons) {
            b.setOnClickListener(this);
            b.setTextColor(getResources().getColor(R.color.numberColor));
        }
    }

    public class Java2MySql {
        public void dbsetup(String[] args) {
            String url = "";
            String dbName = "employees";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "TimeOut";
            String password = "tavern";
            try {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(url + dbName, userName, password);

                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.button1):
                editText1.append("1");
                break;
            case (R.id.button2):
                editText1.append("2");
                break;
            case (R.id.button3):
                editText1.append("3");
                break;
            case (R.id.button4):
                editText1.append("4");
                break;
            case (R.id.button5):
                editText1.append("5");
                break;
            case (R.id.button6):
                editText1.append("6");
                break;
            case (R.id.button7):
                editText1.append("7");
                break;
            case (R.id.button8):
                editText1.append("8");
                break;
            case (R.id.button9):
                editText1.append("9");
                break;
            case (R.id.button0):
                editText1.append("0");
                break;
            case (R.id.buttonEnter):
                Boolean correct = false;

                try {
                    if(editText1.getText().toString().equals("99")){
                        handler.addEmployee(new Employee("Manager", "Manager", 1, 1, "email", "adress", "", "", 1, 1, 2));
                        editText1.setText("");
                        return;
                    }
                    String query = "SELECT * FROM " + EmployeeDB.TABLE_EMPLOYEES + " WHERE accesscode = " + editText1.getText().toString();
                    SQLiteDatabase db = handler.getWritableDatabase();
                    Cursor c = db.rawQuery(query, null);

                    c.moveToFirst();
                    emp = new Employee();
                    emp.setFirstName(c.getString(c.getColumnIndex("firstname")));
                    emp.setLastName(c.getString(c.getColumnIndex("lastname")));
                    emp.setId(c.getInt(c.getColumnIndex("id")));
                    emp.setPosition(c.getInt(c.getColumnIndex("position")));
                    if (c.getCount() > 0) {
                        correct = true;
                    }

                } catch (Exception e) {

                }

                if (correct) {
                    Intent i;

                    switch(emp.getPosition()){
                        case 1:
                            i = new Intent(LoginActivity.this, EmployeeHubActivity.class);
                            i.putExtra("obj", emp);
                            editText1.setText("");
                            startActivity(i);
                            break;
                        case 2:
                            i = new Intent(LoginActivity.this, ManagerHubActivity.class);
                            i.putExtra("obj", emp);
                            editText1.setText("");
                            startActivity(i);
                            break;
                    }

                } else editText1.setText("");
                break;

            case (R.id.buttonBack):
                int length = editText1.getText().length();
                if (length > 0) {
                    editText1.getText().delete(length - 1, length);
                }
                break;
            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        editText1.setText("");
    }
}
