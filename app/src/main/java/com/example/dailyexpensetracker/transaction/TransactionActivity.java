package com.example.dailyexpensetracker.transaction;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dailyexpensetracker.groups.GroupsFragment;
import com.example.dailyexpensetracker.R;
import com.example.dailyexpensetracker.transaction.form.IncomeExpenseActivity;

public class TransactionActivity extends AppCompatActivity {

    String groupName;
    Button btnIncome;
    Button btnExpense;
    Button filter;

    public static final String EX_GROUP_TYPE = "com.example.dailyexpensetracker.ui.groups.GROUP_TYPE";
    public static final int REQUEST_CODE=1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        Intent intent = getIntent();
        groupName = intent.getStringExtra(GroupsFragment.EX_GROUP_NAME);
        setTitle(groupName);

        setTransactionRecyclerView();
        setFooterClickListener();

    }

    private void setTransactionRecyclerView() {
        /*TransactionHelper transactionHelper = new TransactionHelper(this);
        List<TransactionModel> transactions = transactionHelper.getEveryOne();*/
    }

    private void setFooterClickListener() {
        btnIncome = findViewById(R.id.btn_income);
        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),((Button) view).getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), IncomeExpenseActivity.class);
                intent.putExtra(EX_GROUP_TYPE, ((Button) view).getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        btnExpense = findViewById(R.id.btn_expense);
        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),((Button) view).getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(),IncomeExpenseActivity.class);
                intent.putExtra(EX_GROUP_TYPE, ((Button) view).getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        filter = findViewById(R.id.btn_filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),((Button) view).getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), FilterActivity.class);
                intent.putExtra(EX_GROUP_TYPE, ((Button) view).getText().toString());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}