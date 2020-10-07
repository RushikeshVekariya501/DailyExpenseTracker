package com.example.dailyexpensetracker.groups;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.dailyexpensetracker.groups.model.GroupModel;
import com.example.dailyexpensetracker.groups.model.GroupsModel;
import com.example.dailyexpensetracker.groups.model.GroupHelper;
import com.example.dailyexpensetracker.transaction.TransactionActivity;
import com.example.dailyexpensetracker.R;
import com.example.dailyexpensetracker.utility.DatabaseHelper;

import java.util.List;

public class GroupsFragment extends Fragment {

    private DatabaseHelper mDatabaseHelper;
    private GroupModel groupsModel;

    public static final String EX_GROUP_NAME = "com.example.dailyexpensetracker.ui.groups.GROUP_NAME";

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        groupsModel =
                ViewModelProviders.of(this).get(GroupModel.class);
        View root = inflater.inflate(R.layout.fragment_groups, container, false);

        /*TextView demo = root.findViewById(R.id.textView2);

        GroupHelper groupHelper = new GroupHelper(getActivity());
        List<GroupsModel> groups = groupHelper.getEveryOne();
        demo.setText(groups.get(0).toString());*/

        /*GroupHelper groupHelper = new GroupHelper(getActivity());
        List<GroupsModel> groups = groupHelper.getEveryOne();*/
        mDatabaseHelper = new DatabaseHelper(getActivity());
        mDatabaseHelper.open();

        List<GroupModel> groups = mDatabaseHelper.getGroupList();
        String[] groupNames = new String[groups.size()];
        for(int i=0; i<groups.size(); i++){
            groupNames[i] = groups.get(i).getName();
        }

        ListView list = (ListView)root.findViewById(R.id.view_groups_list);
        //SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), groups, R.layout.groups_list_view);

        TextView labels = (TextView)root.findViewById(R.id.view_groups_list_label);

        ArrayAdapter adapter = new ArrayAdapter<>(getActivity().getBaseContext(),R.layout.groups_list_view, R.id.view_groups_list_label, groupNames);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = ((TextView) view.findViewById(R.id.view_groups_list_label)).getText().toString();
                //Toast.makeText(getActivity().getBaseContext(),str, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity().getBaseContext(), TransactionActivity.class);
                intent.putExtra(EX_GROUP_NAME, str);
                startActivity(intent);
            }
        });

        return root;
    }

}