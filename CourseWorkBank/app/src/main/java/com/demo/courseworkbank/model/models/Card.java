package com.demo.courseworkbank.model.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.demo.courseworkbank.utils.Converters;

import java.util.Date;

@Entity
public class Card implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo
    public int cvv;
    @ColumnInfo
    public String codeNumber;
    @TypeConverters({Converters.class})
    public Date expirationDate;
    @ColumnInfo
    public String userUid;

    public Card(long id, int cvv, String codeNumber, Date expirationDate, String userUid) {
        this.id = id;
        this.cvv = cvv;
        this.expirationDate = expirationDate;
        this.codeNumber = codeNumber;
        this.userUid = userUid;
    }

    @Ignore
    public Card(int cvv, String codeNumber, Date expirationDate, String userUid) {
        this.cvv = cvv;
        this.codeNumber = codeNumber;
        this.expirationDate = expirationDate;
        this.userUid = userUid;
    }

    @Ignore
    public Card(){}

    protected Card(Parcel in) {
        id = in.readLong();
        cvv = in.readInt();
        codeNumber = in.readString();
        userUid = in.readString();
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel in) {
            return new Card(in);
        }

        @Override
        public Card[] newArray(int size) {
            return new Card[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(String codeNumber) {
        this.codeNumber = codeNumber;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(cvv);
        dest.writeString(codeNumber);
        dest.writeString(userUid);
    }
}
