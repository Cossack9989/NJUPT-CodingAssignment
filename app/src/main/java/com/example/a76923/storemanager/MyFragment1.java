package com.example.a76923.storemanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a76923.storemanager.assistance.dbKindOP;
import com.example.a76923.storemanager.assistance.dbOPP;

public class MyFragment1 extends Fragment {

    public MainActivity Mactivity;
    public static String kind;
    public static int sum;
    public static int prc;
    private static int serial = 0;
    public MyFragment1() {
    }
    private boolean kindOK(String kind){
        dbKindOP instance = dbKindOP.getInstance(Mactivity);
        return instance.findK(kind);
    }
    private boolean validate(String a,int b,int c){
        return !(a.equals("") || b<=0 || c<=0 || !kindOK(a));
    }
    private void cleanText(EditText a,EditText b,EditText c){
        a.setText("");
        b.setText("");
        c.setText("");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Mactivity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.currency, container, false);
        final EditText kindT = (EditText)view.findViewById(R.id.kindText);
        final EditText numbT = (EditText)view.findViewById(R.id.numText);
        final EditText pricT = (EditText)view.findViewById(R.id.priceText);
        Button in = (Button)view.findViewById(R.id.button2);
        Button out = (Button)view.findViewById(R.id.button3);
        Button wd = (Button)view.findViewById(R.id.backinblack);
        Button nk = (Button)view.findViewById(R.id.addkind);
        nk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                kind = kindT.getText().toString();
                if(kindOK(kind)){
                    Toast.makeText(Mactivity,"Already include this kind",Toast.LENGTH_LONG).show();
                }else if(!kindOK(kind)||!kind.equals("")){
                    dbKindOP instance = dbKindOP.getInstance(Mactivity);
                    instance.addK(kind);
                }
            }
        });
        in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                kind = kindT.getText().toString();
                String s_sum = numbT.getText().toString();
                String s_prc = pricT.getText().toString();
                if(s_prc.equals("")||s_sum.equals("")){
                    Toast.makeText(Mactivity,"CAN NOT INPUT NULL CONTENT",Toast.LENGTH_LONG).show();
                }else {
                    sum = Integer.parseInt(s_sum);
                    prc = Integer.parseInt(s_prc);
                    if (!MainActivity.isadmin) {
                        Toast.makeText(Mactivity, "Not admin, please login", Toast.LENGTH_LONG).show();
                    } else {
                        if (!validate(kind, sum, prc)) {
                            Toast.makeText(Mactivity, "Invalid data", Toast.LENGTH_LONG).show();
                        } else {
                            dbOPP instance = dbOPP.getInstance(Mactivity);
                            serial = instance.generateCode();
                            instance.addN(kind,sum,prc,serial,0);
                            cleanText(kindT,numbT,pricT);
                            Toast.makeText(Mactivity,String.format("%s:%d","Your serial is : ",serial),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
        out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                kind = kindT.getText().toString();
                String s_sum = numbT.getText().toString();
                String s_prc = pricT.getText().toString();
                if(s_prc.equals("")||s_sum.equals("")){
                    Toast.makeText(Mactivity,"CAN NOT INPUT NULL CONTENT",Toast.LENGTH_LONG).show();
                }else {
                    sum = Integer.parseInt(s_sum);
                    prc = Integer.parseInt(s_prc);
                    if (!MainActivity.isadmin) {
                        Toast.makeText(Mactivity, "Not admin, please login", Toast.LENGTH_LONG).show();
                    } else {
                        if (!validate(kind, sum, prc)) {
                            Toast.makeText(Mactivity, "Invalid data", Toast.LENGTH_LONG).show();
                        } else {
                            dbOPP instance = dbOPP.getInstance(Mactivity);
                            serial = instance.generateCode();
                            instance.addN(kind,sum,prc,serial,1);
                            cleanText(kindT,numbT,pricT);
                            if(!instance.getAnalysis(kind)) {
                                instance.delN(serial);
                                Toast.makeText(Mactivity,"STOP OUT",Toast.LENGTH_LONG).show();
                                serial = 0;
                            }else{
                                Toast.makeText(Mactivity,String.format("%s:%d","Your serial is : ",serial),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
        wd.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                if(serial != 0){
                    dbOPP instance = dbOPP.getInstance(Mactivity);
                    if(instance.delN(serial)>-1) {
                        Toast.makeText(Mactivity, "Withdraw the before record", Toast.LENGTH_LONG).show();
                        serial = 0;
                        return true;
                    }
                    else
                        Toast.makeText(Mactivity,"Failure occured during `delete`",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(Mactivity,"no record found",Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
        /*
        wd.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent e){
                Vibrator vb = (Vibrator)Mactivity.getSystemService(Context.VIBRATOR_SERVICE);
                if(e.getAction()==MotionEvent.ACTION_DOWN)
                    vb.vibrate(1000);
                else if(e.getAction()==MotionEvent.ACTION_UP)
                    vb.cancel();
                return true;
            }
        });
        */
        serial = 0;
        return view;
    }
}