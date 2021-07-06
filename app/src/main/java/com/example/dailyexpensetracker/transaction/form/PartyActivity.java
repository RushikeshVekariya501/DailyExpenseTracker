package com.example.dailyexpensetracker.transaction.form;

import android.content.Intent;
import android.os.Bundle;

import com.example.dailyexpensetracker.MainActivity;
import com.example.dailyexpensetracker.groups.model.GroupHelper;
import com.example.dailyexpensetracker.groups.model.GroupModel;
import com.example.dailyexpensetracker.groups.model.GroupsModel;
import com.example.dailyexpensetracker.summary.ui.main.SectionsPagerAdapter;
import com.example.dailyexpensetracker.transaction.TransactionActivity;
import com.example.dailyexpensetracker.transaction.model.party.PartyHelper;
import com.example.dailyexpensetracker.transaction.model.party.PartyModel;
import com.example.dailyexpensetracker.utility.DatabaseHelper;
import com.example.dailyexpensetracker.utility.DimBehind;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailyexpensetracker.R;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class PartyActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;

    TextView title;
    PopupWindow addPartyForm;
    LayoutInflater inflater;
    View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party);

        setActivityTitle();
        setHeader();

        mDatabaseHelper = new DatabaseHelper(PartyActivity.this);
        mDatabaseHelper.open();

        setListView();
    }


    private void setActivityTitle() {
        title = (TextView) findViewById(R.id.activity_party_title);
        Intent intent = getIntent();
        title.setText(intent.getStringExtra(IncomeExpenseActivity.EX_ADD_ACTIVITY_TITLE));
    }
    private void setHeader() {

        ImageView imgBack = (ImageView) findViewById(R.id.party_imgBack);
        ImageView imgAddPartyName = (ImageView) findViewById(R.id.add_Party_Name);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imgAddPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // inflate the layout of the popup window
                inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                popupView = inflater.inflate(R.layout.party_add_form, null);

                // create the popup window
                int width = 800;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                addPartyForm = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                // which view you pass in doesn't matter, it is only used for the window tolken
                addPartyForm.showAtLocation(popupView, Gravity.CENTER, 0, 0);
                //this.dimBehind(addPartyForm);
                new DimBehind(addPartyForm);

                final EditText partyNameInput = popupView.findViewById(R.id.apf_input_name);
                final Button addPartyButton = popupView.findViewById(R.id.apf_button_add);
                partyNameInput.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(partyNameInput.getText().toString().length() <= 0){
                            addPartyButton.setEnabled(false);
                        }else{
                            addPartyButton.setEnabled(true);
                        }
                    }
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                });

            }
        });
    }
    //inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    private void setListView() {
        List<PartyModel> parties = mDatabaseHelper.getPartyList();
        String[] partyNames = new String[parties.size()];
        for(int i=0; i<parties.size(); i++){
            partyNames[i] = parties.get(i).getName();
        }


        ListView list = (ListView)findViewById(R.id.view_party_list);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.parties_list_view, R.id.view_party_list_label, partyNames);
        list.setAdapter(arrayAdapter);



        /*ListView list = (ListView) findViewById(R.id.view_party_list);

        TextView labels = (TextView) findViewById(R.id.view_party_list_label);

        ArrayAdapter adapter = new ArrayAdapter<>(getBaseContext(),R.layout.parties_list_view, R.id.view_party_list_label, partyNames);
        list.setAdapter(adapter);*/

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = ((TextView) view.findViewById(R.id.view_party_list_label)).getText().toString();
                //Toast.makeText(getActivity().getBaseContext(),str, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getBaseContext(), IncomeExpenseActivity.class);
                intent.putExtra(IncomeExpenseActivity.EX_PARTY_NAME,((TextView) view).getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public void addPartyData(View view) {
        EditText partyName = popupView.findViewById(R.id.apf_input_name);
        PartyModel party = new PartyModel(partyName.getText().toString());
        long success = mDatabaseHelper.insertParty(party);
        if(success > 0){
            //REFRESH PARTY ACTIVITY
            Intent intent = new Intent(this.getBaseContext(), PartyActivity.class);
            intent.putExtra(IncomeExpenseActivity.EX_ADD_ACTIVITY_TITLE,getString(R.string.enter_party));
            startActivity(intent);

            Toast.makeText(PartyActivity.this, "Party added Successfully!", Toast.LENGTH_LONG).show();
            addPartyForm.dismiss();

        }
        else
            Toast.makeText(PartyActivity.this, "Failed to add data in DB!", Toast.LENGTH_LONG).show();
    }

    public void dismissApf(View view) {
        addPartyForm.dismiss();
    }
}