package com.example.dailyexpensetracker.groups.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GroupsModel extends ViewModel {

    private int id;
    private String name;

    public GroupsModel() {}

    public GroupsModel(String name) {
        this.name = name;
    }

    public GroupsModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GroupsModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}