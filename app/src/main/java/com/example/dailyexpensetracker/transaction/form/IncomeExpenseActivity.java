package com.example.dailyexpensetracker.transaction.form;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.dailyexpensetracker.summary.SummaryActivity;
import com.example.dailyexpensetracker.transaction.TransactionActivity;

import com.example.dailyexpensetracker.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class IncomeExpenseActivity extends AppCompatActivity {


    final Calendar myCalendar = Calendar.getInstance();
    EditText idate;
    EditText iparty;
    public static final int REQUEST_CODE_PARTY=1;

    public static final String EX_ADD_ACTIVITY_TITLE = "com.example.dailyexpensetracker.transaction.form.ADD_ACTIVITY_TITLE";
    public static final String EX_PARTY_NAME = "com.example.dailyexpensetracker.transaction.form.PARTY_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_expense);

        Intent intent = getIntent();
        String groupType = intent.getStringExtra(TransactionActivity.EX_GROUP_TYPE);
        setTitle(groupType);

        setDateField();
        setPartyField();
    }

    private void setPartyField() {
        iparty = (EditText) findViewById(R.id.iparty);
        iparty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), PartyActivity.class);
                String a = getString(R.string.enter_party);
                intent.putExtra(EX_ADD_ACTIVITY_TITLE,getString(R.string.enter_party));
                startActivityForResult(intent, REQUEST_CODE_PARTY);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_PARTY){
            if(resultCode == RESULT_OK){
                int i =0;
            }
        }
    }

    private void setDateField() {
        idate= (EditText) findViewById(R.id.idate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        idate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(IncomeExpenseActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);
        idate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}