package com.example.a76923.storemanager.assistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class dbKindOP {
    private static dbKind dbHelper;
    private static String kindDB= "ALLKIND";

    public dbKindOP(Context ctx){
        dbHelper = new dbKind(ctx,"ALLKIND.db",null,1);
    }
    private static dbKindOP instance;

    public static synchronized dbKindOP getInstance(Context ctx){
        if(instance == null){
            instance = new dbKindOP(ctx);
        }
        return instance;
    }
    public int addK(String kind){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("KIND",kind);
        return (int)db.insert(kindDB,null,values);
    }
    public boolean findK(String kind){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(kindDB,null,"KIND = ?",new String[]{kind},null,null,null);
        if(cursor.moveToNext()) return true;
        else return false;
    }
    public List<String> getKindList(){
        List<String> kindList = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(kindDB,null,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String kind = cursor.getString(cursor.getColumnIndex("KIND"));
            kindList.add(kind);
        }
        cursor.close();
        return kindList;
    }
}