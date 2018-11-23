package com.example.a76923.storemanager.assistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class dbOPP {
    private static dbOP dbHelper;
    private static String Store_table= "ISTORAGE";

    public dbOPP(Context ctx){
        dbHelper = new dbOP(ctx,"ISTORAGE.db",null,1);
    }
    private static dbOPP instance;

    public static synchronized dbOPP getInstance(Context ctx){
        if(instance == null){
            instance = new dbOPP(ctx);
        }
        return instance;
    }

    public void addN(String kind, int number, int price, int serialnum,int io){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("SERIALNUM",serialnum);
        values.put("KIND",kind);
        values.put("NUMBER",number);
        values.put("PRICE",price);
        values.put("IO",io);
        db.insert(Store_table,null,values);
    }
    public int delN(String kind){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(Store_table,"KIND = ?",new String[]{kind});
    }
    public int delN(int serialnum){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(Store_table,"SERIALNUM = ?",new String[]{String.valueOf(serialnum)});
    }
    public int generateCode(){
        Date date = new Date();
        long serial = date.getTime();
        serial = serial / 1000;
        return (int)serial;
    }
    public List<frecord> getAnalysis(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<frecord> anaList = new ArrayList<frecord>();
        Cursor cursor = db.query(Store_table,null,null,null,null,null,null);
        while(cursor.moveToNext()){
            String kind = cursor.getString(cursor.getColumnIndex("KIND"));
            int number = cursor.getInt(cursor.getColumnIndex("NUMBER"));
            int price = cursor.getInt(cursor.getColumnIndex("PRICE"));
            int serial = cursor.getInt(cursor.getColumnIndex("SERIALNUM"));
            boolean io = cursor.getInt(cursor.getColumnIndex("IO")) == 1;
            frecord xx = new frecord(kind,number,price,serial,io);
            anaList.add(xx);
        }
        cursor.close();
        return anaList;
    }
    public List<record> getAnalysis(List<String> kindSum){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<record> anaList = new ArrayList<record>();
        for(int i = 0;i<kindSum.size();i++){
            Cursor cursor = db.query(Store_table,null,"KIND = ?",new String[]{kindSum.get(i)},null,null,null);
            int stsum = 0;
            while(cursor.moveToNext()){
                int number = cursor.getInt(2);
                int io = cursor.getInt(4);
                if(io == 0) stsum+=number;
                else stsum-=number;
            }
            record xx = new record(kindSum.get(i),stsum);
            anaList.add(xx);
            cursor.close();
        }
        return anaList;
    }
    public String getWarn(List<record> KindList){
        String ilist = "";
        for(int i = 0;i<KindList.size();i++){
            if(KindList.get(i).number<=1)
                ilist+=KindList.get(i).kind;
                ilist+=" ";
        }
        return String.format("WARNING:%s in need",ilist);
    }
    public String getKindLine(String kind){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Store_table,null,"KIND = ?",new String[]{kind},null,null,null);
        int stsum = 0;
        while(cursor.moveToNext()){
            int number = cursor.getInt(2);
            int io = cursor.getInt(4);
            if(io == 0) stsum+=number;
            else stsum-=number;
        }
        return String.format("KIND:%s | SUM:%d",kind,stsum);
    }
    public String getSerialLine(int serial){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Store_table,null,"SERIAL = ?",new String[]{String.valueOf(serial)},null,null,null);
        return String.format("SERAIL:%d | KIND:%s | IO:%s | NUMBER:%d | PRICE:%d",
                cursor.getInt(0),
                cursor.getString(cursor.getColumnIndex("KIND")),
                cursor.getInt(4)==1?"OUT":"IN",
                cursor.getInt(2),
                cursor.getInt(3));
    }
    public boolean getAnalysis(String kind){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Store_table,null,"KIND = ?",new String[]{kind},null,null,null);
        int stsum = 0;
        while(cursor.moveToNext()){
            int number = cursor.getInt(2);
            int io = cursor.getInt(4);
            if(io == 0)stsum += number;
            else stsum-=number;
        }
        return stsum >= 0;
    }
    public List<String> getIoData(boolean io){
        String x = io?"1":"0";
        List<String> dataList = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(Store_table,null,"IO = ?",new String[]{x},null,null,null);
        while(cursor.moveToNext()){
            String y = String.format("KIND:%s | NUMBER:%d | PRICE:%d | SERIAL:%d",cursor.getString(cursor.getColumnIndex("KIND")),cursor.getInt(2),cursor.getInt(3),cursor.getInt(0));
            dataList.add(y);
        }
        return dataList;
    }
}