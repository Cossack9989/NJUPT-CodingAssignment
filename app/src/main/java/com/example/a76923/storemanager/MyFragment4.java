package com.example.a76923.storemanager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyFragment4 extends Fragment {

    public MyFragment4() {
    }
    public void callPhone(String phoneNum){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.briefview,container,false);
        Button sbtn = (Button)view.findViewById(R.id.sbtn);
        sbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                callPhone("17768108581");
            }
        });
        return view;
    }
}
