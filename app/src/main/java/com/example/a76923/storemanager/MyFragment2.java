package com.example.a76923.storemanager;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a76923.storemanager.assistance.dbKindOP;
import com.example.a76923.storemanager.assistance.dbOPP;
import com.example.a76923.storemanager.assistance.frecord;
import com.example.a76923.storemanager.assistance.record;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MyFragment2 extends Fragment {
    public MainActivity mActivity;
    public MyFragment2() {
    }
    private List<String> getKindListHere(){
        dbKindOP y = dbKindOP.getInstance(mActivity);
        return y.getKindList();
    }
    private List<record> getDbContent(){
        dbOPP x = dbOPP.getInstance(mActivity);
        return x.getAnalysis(getKindListHere());
    }
    private List<frecord> getDbContent(int i){
        dbOPP x = dbOPP.getInstance(mActivity);
        return x.getAnalysis();
    }
    private String calcTime(int getT){
        Date date = new Date();
        int diff = (int)(date.getTime()/1000) - getT;
        if(diff <=0){
            return "ERROR IN `calcTime`";
        }else if(diff >0 && diff <60){
            return "Just Now";
        }else if(diff >=60 && diff < 60*60){
            int mins = (diff/60);
            return Integer.toString(mins)+" Minites ago";
        }else if(diff >= 60*60 && diff < 60*60*24){
            int hours = (diff/(60*60));
            return Integer.toString(hours)+" Hours ago";
        }else if(diff >=60*60*24 && diff <60*60*24*7){
            int days = (diff/(60*60*24));
            return Integer.toString(days)+" Days ago";
        }else if(diff >=60*60*24*7 && diff <60*60*24*7*4){
            int weeks = (diff/(60*60*24*7));
            return Integer.toString(weeks)+" Weeks ago";
        }else{
            return "Before a month";
        }
    }
    private void print(EditText et,List<record> sth){
        String output = "";
        for(int i = 0;i<sth.size();i++){
            String buf = String.format("%d : %s : %d\n",i,sth.get(i).kind,sth.get(i).number);
            output+=buf;
        }
        et.setText(output);
    }
    private void __print(EditText et,List<String> sth){
        String output = "";
        for(int i = 0;i<sth.size();i++){
            String buf = sth.get(i);
        }
        et.setText(output);
    }
    private void _print(List<frecord> sth,EditText et){
        String output = "";
//        int latedTime = sth.get(sth.size()-1).serial;
        for(int i = 0;i<sth.size();i++){
            String ftime = calcTime(sth.get(i).serial);
            String buf = String.format("%d | %s | %s : %s : %d : %d\n",i,ftime,sth.get(i).io == true?"OUT":"IN",sth.get(i).kind,sth.get(i).number,sth.get(i).price);
            output+=buf;
        }
        et.setText(output);
    }
    private boolean checkadimin(Context ctx) {
        if (!MainActivity.isadmin) {
            Toast.makeText(ctx, "Please login first", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.quiry,container,false);
        TextView sbt = (TextView)view.findViewById(R.id.sbt);
        TextView sbk = (TextView)view.findViewById(R.id.sbk);
        TextView sbs = (TextView)view.findViewById(R.id.sbs);
        final EditText showMe = (EditText)view.findViewById(R.id.show);
        final EditText qbk = (EditText)view.findViewById(R.id.qbk);
        final EditText qbs = (EditText)view.findViewById(R.id.qbs);
        final EditText qbi = (EditText)view.findViewById(R.id.qbi);
        final Button qbkB = (Button)view.findViewById(R.id.qbkB);
        final Button qbsB = (Button)view.findViewById(R.id.qbsB);
        final Button qbiB = (Button)view.findViewById(R.id.qbiB);
        if(checkadimin(mActivity)){
            qbkB.setEnabled(true);
            qbiB.setEnabled(true);
            qbsB.setEnabled(true);
        }
        qbkB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                qbkB.setEnabled(true);
                if(checkadimin(mActivity)) {
                    dbOPP x = dbOPP.getInstance(mActivity);
                    String getK = qbk.getText().toString();
                    if (getK.equals("")) {
                        Toast.makeText(mActivity, "INVALID DATA", Toast.LENGTH_LONG).show();
                    } else {
                        showMe.setText(x.getKindLine(getK));
                    }
                }else{
                    qbkB.setEnabled(false);
                }
                qbkB.setEnabled(true);
            }
        });
        qbsB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                qbsB.setEnabled(true);
                if(checkadimin(mActivity)) {
                    dbOPP x = dbOPP.getInstance(mActivity);
                    Date date = new Date();
                    int getS = Integer.parseInt(qbs.getText().toString());
                    if (getS > (date.getTime() / 1000) || getS == 0) {
                        Toast.makeText(mActivity, "INVALID DATA", Toast.LENGTH_LONG).show();
                    } else {
                        showMe.setText(x.getSerialLine(getS));
                    }
                }else{
                    qbsB.setEnabled(false);
                }
                qbsB.setEnabled(true);
            }
        });
        qbiB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                qbiB.setEnabled(true);
                if(checkadimin(mActivity)) {
                    dbOPP x = dbOPP.getInstance(mActivity);
                    String getI = qbi.getText().toString();
                    if (getI.equals("IN") || getI.equals("OUT")) {
                        __print(showMe, x.getIoData(getI.equals("OUT")));
                    } else {
                        Toast.makeText(mActivity, "INVALID DATA", Toast.LENGTH_LONG).show();
                    }
                }else{
                    qbiB.setEnabled(false);
                }
                qbiB.setEnabled(true);
            }
        });
        sbt.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                List<frecord> anares = getDbContent(0);
                Collections.sort(anares, new Comparator<frecord>() {
                    @Override
                    public int compare(frecord lhs, frecord rhs) {
                        if(lhs.serial<rhs.serial){
                            return 1;
                        }
                        if(lhs.serial==rhs.serial){
                            return 0;
                        }
                        return -1;
                    }
                });
                _print(anares,showMe);
                return false;
            }
        });
        sbs.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                List<record> anares = getDbContent();
                Collections.sort(anares, new Comparator<record>() {
                    @Override
                    public int compare(record lhs, record rhs) {
                        if(lhs.number>rhs.number) {
                            return 1;
                        }
                        if(lhs.number==rhs.number){
                            return 0;
                        }
                        return -1;
                    }
                });
                print(showMe,anares);
                return false;
            }
        });
        sbk.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                List<record> anares = getDbContent();
                Collections.sort(anares, new Comparator<record>() {
                    @Override
                    public int compare(record lhs, record rhs) {
                        char chl[] = lhs.kind.toCharArray();
                        char chr[] = rhs.kind.toCharArray();
                        if(chl[0]>chr[0]){
                            return 1;
                        }
                        if(chl[0]==chr[0]){
                            return 0;
                        }
                        return -1;
                    }
                });
                print(showMe,anares);
                return false;
            }
        });

        return view;
    }
}