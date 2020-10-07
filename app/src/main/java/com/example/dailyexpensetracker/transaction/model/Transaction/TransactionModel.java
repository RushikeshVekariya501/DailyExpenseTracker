package com.example.dailyexpensetracker.transaction.model.Transaction;

import java.util.Date;

public class TransactionModel {
    private Long id;
    private Date date;
    private String description;
    private float amount;
    private String image;

    public TransactionModel(){};

    public TransactionModel(Date date, String description, float amount, String image) {
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.image = image;
    }

    public TransactionModel(Long id, Date date, String description, float amount, String image) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.image = image;
    }

    @Override
    public String toString() {
        return "TrnsactionModel{" +
                "id=" + id +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", image='" + image + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
