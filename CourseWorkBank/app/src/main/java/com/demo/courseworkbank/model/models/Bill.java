package com.demo.courseworkbank.model.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Bill implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public String billNumber;
    @ColumnInfo
    public double money;
    @ColumnInfo
    public long cardId;

    public Bill(long id, String billNumber, double money, long cardId) {
        this.id = id;
        this.billNumber = billNumber;
        this.money = money;
        this.cardId = cardId;
    }

    @Ignore
    public Bill(String billNumber, double money, long cardId) {
        this.billNumber = billNumber;
        this.money = money;
        this.cardId = cardId;
    }

    @Ignore
    public Bill(){}

    protected Bill(Parcel in) {
        id = in.readLong();
        billNumber = in.readString();
        money = in.readDouble();
        cardId = in.readLong();
    }

    public static final Creator<Bill> CREATOR = new Creator<Bill>() {
        @Override
        public Bill createFromParcel(Parcel in) {
            return new Bill(in);
        }

        @Override
        public Bill[] newArray(int size) {
            return new Bill[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public long getCardId() {
        return cardId;
    }

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    @Ignore
    public boolean canDecreaseMoney(double money) {
        return getMoney() - money > 0;
    }

    @Ignore
    public void decreaseMoney(double money) {
        this.money -= money;
    }

    @Ignore
    public void IncreaseMoney(double money) {
        this.money += money;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(billNumber);
        dest.writeDouble(money);
        dest.writeLong(cardId);
    }
}