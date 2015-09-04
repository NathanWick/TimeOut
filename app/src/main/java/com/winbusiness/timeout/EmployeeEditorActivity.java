package com.winbusiness.timeout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class EmployeeEditorActivity extends AppCompatActivity implements android.view.View.OnFocusChangeListener, android.view.View.OnClickListener {
    Employee employee;
    Boolean showKeyboard;
    EmployeeDB handler;
    Intent intent;
    boolean newEmp;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
    EditText et1, et2, et3, et4, et5, et6, et7;
    ViewSwitcher switcher1, switcher2, switcher3, switcher4, switcher5, switcher6, switcher7;
    String firstname = "", lastname = "", email = "", home = "";
    int phone1 = 0, phone2 = 0, accesscode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_editor);

        instantiateVariables();
        setOnClickListeners();
    }

    private void instantiateVariables() {
        employee = new Employee();
        showKeyboard = false;
        handler = new EmployeeDB(this, null, null, 1);
        newEmp = false;
        intent = getIntent();
        newEmp = intent.getBooleanExtra("newEmp", false);

        tv1 = (TextView) findViewById(R.id.textView2);
        et1 = (EditText) findViewById(R.id.hidden_edit_view1);
        switcher1 = (ViewSwitcher) findViewById(R.id.viewSwitcher1);

        tv2 = (TextView) findViewById(R.id.textView4);
        et2 = (EditText) findViewById(R.id.hidden_edit_view2);
        switcher2 = (ViewSwitcher) findViewById(R.id.viewSwitcher2);

        tv3 = (TextView) findViewById(R.id.textView6);
        et3 = (EditText) findViewById(R.id.hidden_edit_view3);
        switcher3 = (ViewSwitcher) findViewById(R.id.viewSwitcher3);

        tv4 = (TextView) findViewById(R.id.textView8);
        et4 = (EditText) findViewById(R.id.hidden_edit_view4);
        switcher4 = (ViewSwitcher) findViewById(R.id.viewSwitcher4);

        tv5 = (TextView) findViewById(R.id.textView10);
        et5 = (EditText) findViewById(R.id.hidden_edit_view5);
        switcher5 = (ViewSwitcher) findViewById(R.id.viewSwitcher5);

        tv6 = (TextView) findViewById(R.id.textView12);
        et6 = (EditText) findViewById(R.id.hidden_edit_view6);
        switcher6 = (ViewSwitcher) findViewById(R.id.viewSwitcher6);

        tv7 = (TextView) findViewById(R.id.textView14);
        et7 = (EditText) findViewById(R.id.hidden_edit_view7);
        switcher7 = (ViewSwitcher) findViewById(R.id.viewSwitcher7);

        if (!newEmp) {
            employee.setFirstName(intent.getStringExtra("firstname"));
            employee.setLastName(intent.getStringExtra("lastname"));
            employee.setAccessCode(intent.getIntExtra("accesscode", 0));
            employee.setId(intent.getIntExtra("id", 0));
            tv1.setText(employee.getFirstName());
            tv2.setText(employee.getLastName());
            tv7.setText(String.valueOf(employee.getAccessCode()));
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
            switch (v.getId()) {
                case (R.id.hidden_edit_view1):
                    tv1.setText(et1.getText());
                    switcher1.showNext();
                    break;
                case (R.id.hidden_edit_view2):
                    switcher2.showNext();
                    tv2.setText(et2.getText());
                    break;
                case (R.id.hidden_edit_view3):
                    switcher3.showNext();
                    tv3.setText(et3.getText());
                    break;
                case (R.id.hidden_edit_view4):
                    switcher4.showNext();
                    tv4.setText(et4.getText());
                    break;
                case (R.id.hidden_edit_view5):
                    switcher5.showNext();
                    tv5.setText(et5.getText());
                    break;
                case (R.id.hidden_edit_view6):
                    switcher6.showNext();
                    tv6.setText(et6.getText());
                    break;
                case (R.id.hidden_edit_view7):
                    switcher7.showNext();
                    tv7.setText(et7.getText());
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.textView2):
                switcher1.showPrevious();
                et1.setText(tv1.getText());
                et1.setFocusable(true);
                et1.requestFocus();
                showKeyboard = true;
                break;
            case (R.id.textView4):
                switcher2.showPrevious();
                et2.setText(tv2.getText());
                et2.setFocusable(true);
                et2.requestFocus();
                showKeyboard = true;
                break;
            case (R.id.textView6):
                switcher3.showPrevious();
                et3.setText(tv3.getText());
                et3.setFocusable(true);
                et3.requestFocus();
                showKeyboard = true;
                break;
            case (R.id.textView8):
                switcher4.showPrevious();
                et4.setText(tv4.getText());
                et4.setFocusable(true);
                et4.requestFocus();
                showKeyboard = true;
                break;
            case (R.id.textView10):
                switcher5.showPrevious();
                et5.setText(tv5.getText());
                et5.setFocusable(true);
                et5.requestFocus();
                showKeyboard = true;
                break;
            case (R.id.textView12):
                switcher6.showPrevious();
                et6.setText(tv6.getText());
                et6.setFocusable(true);
                et6.requestFocus();
                showKeyboard = true;
                break;
            case (R.id.textView14):
                switcher7.showPrevious();
                et7.setText(tv7.getText());
                et7.setFocusable(true);
                et7.requestFocus();
                showKeyboard = true;
                break;
        }

        if (showKeyboard) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    private void setOnClickListeners() {
        tv1.setOnClickListener(this);
        et1.setOnFocusChangeListener(this);
        tv2.setOnClickListener(this);
        et2.setOnFocusChangeListener(this);
        tv3.setOnClickListener(this);
        et3.setOnFocusChangeListener(this);
        tv4.setOnClickListener(this);
        et4.setOnFocusChangeListener(this);
        tv5.setOnClickListener(this);
        et5.setOnFocusChangeListener(this);
        tv6.setOnClickListener(this);
        et6.setOnFocusChangeListener(this);
        tv7.setOnClickListener(this);
        et7.setOnFocusChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.activity_employee_editor, menu);
        return true;*/
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_employee_editor, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_employee) {
            saveEmployee();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveEmployee() {
        SQLiteDatabase db = handler.getWritableDatabase();

        RelativeLayout myLayout = (RelativeLayout) findViewById(R.id.RelLay1);
        myLayout.requestFocus();
        checkNullValues();

        setValues();

        //handler.addEmployee(new Employee(tv1.getText().toString(), tv2.getText().toString(), Integer.parseInt(tv3.getText().toString()), Integer.parseInt(tv4.getText().toString()), tv5.getText().toString(), tv6.getText().toString(), "", "", 0, Integer.parseInt(tv7.getText().toString())));
        if (newEmp) {
            handler.addEmployee(new Employee(firstname, lastname, phone1, phone2, email, home, "", "", 0, accesscode, 1));
        } else {
            employee.setFirstName(firstname);
            employee.setLastName(lastname);
            employee.setAccessCode(accesscode);
            handler.updateEmployee(employee);
        }


        finish();
    }

    private void setValues() {
        firstname = tv1.getText().toString();
        lastname = tv2.getText().toString();
        email = tv5.getText().toString();
        home = tv6.getText().toString();
        phone1 = Integer.parseInt(tv3.getText().toString());
        phone2 = Integer.parseInt(tv4.getText().toString());
        accesscode = Integer.parseInt(tv7.getText().toString());
    }

    private void checkNullValues() {
        /*if(tv1.equals("")){
            tv1.setText(" ");
        }
        if(tv2.equals("")){
            tv2.setText(" ");
        }*/
        if (!checkInteger(tv3.getText().toString())) {
            tv3.setText("0");
        }
        if (!checkInteger(tv4.getText().toString())) {
            tv4.setText("0");
        }
       /* if(tv5.equals("")){
            tv5.setText(" ");
        }
        if(tv6.equals("")){
            tv6.setText(" ");
        }*/
        if (!checkInteger(tv7.getText().toString())) {
            tv7.setText("0");
        }
    }

    private boolean checkInteger(String s) {
        boolean isInteger = true;
        try {
            Integer.parseInt(s);
        } catch (Exception ex) {
            isInteger = false;
        }
        return isInteger;
    }
}
