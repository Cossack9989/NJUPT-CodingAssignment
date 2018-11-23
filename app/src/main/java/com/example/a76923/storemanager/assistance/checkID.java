package com.example.a76923.storemanager.assistance;

import com.example.a76923.storemanager.iEnc.fHash;

public class checkID {
    public static int judge(String name, String pawd){
        String mid = fHash.MD5(name);
        String res = fHash.MD5(mid+pawd);
        if(res.equals("7e65b707fc0b03eeea7f955c1f019de0")) {
            return 1;
        }else{
            return 0;
        }
    }
}