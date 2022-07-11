package com.demo.courseworkbank.model.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.demo.courseworkbank.utils.Converters;

import java.util.Date;

@Entity
public class Operation implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public String billNumber;
    @ColumnInfo
    public String type;
    @TypeConverters({Converters.class})
    public Date createDate;
    @ColumnInfo
    public double money;
    @ColumnInfo
    public long cardId;

    @ColumnInfo
    @Nullable
    public String moneyReceiverBillNumber;

    public Operation(long id, String type, String billNumber, Date createDate, double money, @Nullable String moneyReceiverBillNumber, long cardId) {
        this.id = id;
        this.billNumber = billNumber;
        this.type = type;
        this.createDate = createDate;
        this.money = money;
        this.moneyReceiverBillNumber = moneyReceiverBillNumber;
        this.cardId = cardId;
    }

    @Ignore
    public Operation(String type, String billNumber, Date createDate, double money, @Nullable String moneyReceiverBillNumber, long cardId) {
        this.type = type;
        this.billNumber = billNumber;
        this.createDate = createDate;
        this.money = money;
        this.moneyReceiverBillNumber = moneyReceiverBillNumber;
        this.cardId = cardId;
    }

    @Ignore
    public Operation(){}

    protected Operation(Parcel in) {
        id = in.readLong();
        type = in.readString();
        billNumber = in.readString();
        money = in.readLong();
        moneyReceiverBillNumber = in.readString();
        cardId = in.readLong();
    }

    public static final Creator<Operation> CREATOR = new Creator<Operation>() {
        @Override
        public Operation createFromParcel(Parcel in) {
            return new Operation(in);
        }

        @Override
        public Operation[] newArray(int size) {
            return new Operation[size];
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    @Nullable
    public String getMoneyReceiverBillNumber() {
        return moneyReceiverBillNumber;
    }

    public void setMoneyReceiverBillNumber(@Nullable String moneyReceiverBillNumber) {
        this.moneyReceiverBillNumber = moneyReceiverBillNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(type);
        dest.writeString(billNumber);
        dest.writeDouble(money);
        dest.writeString(moneyReceiverBillNumber);
        dest.writeLong(cardId);
    }
}
