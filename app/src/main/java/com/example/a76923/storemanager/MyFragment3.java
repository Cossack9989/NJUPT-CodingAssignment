package com.example.a76923.storemanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a76923.storemanager.assistance.checkID;

public class MyFragment3 extends Fragment {
    public MainActivity mActivity;          //Context
    private String username;
    private String password;
    public MyFragment3() {                  //Constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (MainActivity)getActivity();
        View view = inflater.inflate(R.layout.loginpage,container,false);
        final EditText nameT = (EditText)view.findViewById(R.id.editText);
        final EditText pawdT = (EditText)view.findViewById(R.id.editText2);
        final Button lbtn = (Button)view.findViewById(R.id.button);
        ImageView xx = (ImageView)view.findViewById(R.id.imageView2);
        lbtn.setOnClickListener(new View.OnClickListener(){                                             //监听按钮事件
            @Override
            public void onClick(View view){                                                             //监听后实现功能
                username = nameT.getText().toString();
                password = pawdT.getText().toString();
                if(!MainActivity.isadmin) {
                    if (checkID.judge(username, password) == 1) {                                       //username + passwword 自写注册机
                        Toast.makeText(mActivity, "Login success", Toast.LENGTH_LONG).show();
                        nameT.setKeyListener(null);                                                     //禁用输入框
                        pawdT.setKeyListener(null);
                        //lbtn.setEnabled(false);                                                       //取消了禁用登录按钮
                        MainActivity.isadmin = true;
                    } else {
                        Toast.makeText(mActivity, "Login failed", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(mActivity,"Already login",Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
}