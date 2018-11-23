package com.example.a76923.storemanager.assistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbOP extends SQLiteOpenHelper{
    public dbOP(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ISTORAGE(SERIALNUM INT PRIMARY KEY NOT NULL,KIND TEXT NOT NULL,NUMBER INT NOT NULL,PRICE INT NOY NULL,IO INT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}