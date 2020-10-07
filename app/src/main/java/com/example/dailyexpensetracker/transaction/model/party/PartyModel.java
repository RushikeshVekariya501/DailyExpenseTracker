package com.example.dailyexpensetracker.transaction.model.party;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PartyModel extends ViewModel {

    private int id;
    private String name;

    public PartyModel() {}

    public PartyModel(String name) {
        this.name = name;
    }

    public PartyModel(int id, String name) {
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
        return "PartyModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}