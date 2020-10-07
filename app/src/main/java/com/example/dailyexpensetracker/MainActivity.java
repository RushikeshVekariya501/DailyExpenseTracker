package com.example.dailyexpensetracker;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.dailyexpensetracker.groups.model.GroupHelper;
import com.example.dailyexpensetracker.groups.model.GroupModel;
import com.example.dailyexpensetracker.groups.model.GroupsModel;
import com.example.dailyexpensetracker.summary.SummaryActivity;
import com.example.dailyexpensetracker.transaction.TransactionActivity;
import com.example.dailyexpensetracker.utility.DatabaseHelper;
import com.example.dailyexpensetracker.utility.DimBehind;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;

    private AppBarConfiguration mAppBarConfiguration;

    PopupWindow addGroupForm;

    LayoutInflater inflater;
    View popupView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper = new DatabaseHelper(MainActivity.this);
        mDatabaseHelper.open();

        //addDummyGroups();
        addGroupForm = new PopupWindow(this);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_group, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void addGroup(MenuItem item) {

        // inflate the layout of the popup window
        inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.group_add_form, null);

        // create the popup window
        int width = 800;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        addGroupForm = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        addGroupForm.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        //this.dimBehind(addGroupForm);
        new DimBehind(addGroupForm);

        final EditText groupNameInput = popupView.findViewById(R.id.agf_input_name);
        final Button addGroupButton = popupView.findViewById(R.id.agf_button_add);
        groupNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if(groupNameInput.getText().toString().length() <= 0){
                    addGroupButton.setEnabled(false);
                }else{
                    addGroupButton.setEnabled(true);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
        });

    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    public void dismissAgf(View view) {
        addGroupForm.dismiss();
    }

    public void addGroupData(View view) {
        EditText groupName = popupView.findViewById(R.id.agf_input_name);
        /*GroupsModel group = new GroupsModel(groupName.getText().toString());
        GroupHelper groupHelper = new GroupHelper(MainActivity.this);
        boolean success = groupHelper.add(group);*/
        GroupModel group = new GroupModel(groupName.getText().toString());
        long success = mDatabaseHelper.insertGroup(group);

        if(success > 0){
            //REFRESH MAIN ACTIVITY
            Intent intent = new Intent(this.getBaseContext(), MainActivity.class);
            startActivity(intent);

            Toast.makeText(MainActivity.this, "Group added Successfully!", Toast.LENGTH_LONG).show();
            addGroupForm.dismiss();

        }
        else
            Toast.makeText(MainActivity.this, "Failed to add data in DB!", Toast.LENGTH_LONG).show();
    }

    public void addDummyGroups(){
        GroupsModel student1 = new GroupsModel("AUM");
        GroupsModel student2 = new GroupsModel( "Rushi");
        GroupsModel student3 = new GroupsModel( "Radhu");
        GroupHelper groupHelper = new GroupHelper(MainActivity.this);
        groupHelper.add(student1);
        groupHelper.add(student2);
        groupHelper.add(student3);
    }

    public void viewSummary(MenuItem item) {
        Intent intent = new Intent(this.getBaseContext(), SummaryActivity.class);
        startActivity(intent);
    }
}