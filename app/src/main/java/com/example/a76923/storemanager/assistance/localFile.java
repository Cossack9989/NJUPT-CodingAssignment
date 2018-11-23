package com.example.a76923.storemanager.assistance;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class localFile {
    public static boolean saveUserinfo(String username,String password,String FileName){
        try {
            String info =  username +"##"+ password;
            File file = new File(String.format("/data/data/com.example.a76923.storemanager/%s", FileName));
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(info.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Map<String,String> readUserinfo(String FileName){
        try {
            Map map = new HashMap<String,String>();
            File file = new File(String.format("/data/data/com.example.a76923.storemanager/%s", FileName));
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String info = bufferedReader.readLine();
            String[] userinfos = info.split("##");
            map.put("username", userinfos[0]);
            map.put("password", userinfos[1]);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean readIsAdmin() throws IOException {
        File file = new File("/data/data/com.example.a76923.storemanager/isadmin");
        if (!file.exists()) {
            file.mkdirs();
        }
        FileInputStream in = new FileInputStream(file);
        BufferedReader bR = new BufferedReader(new InputStreamReader(in));
        String info = bR.readLine();
        int i = Integer.parseInt(info);
        if(i != 1)return true;
        return false;
    }

    public static void recordIsAdmin() throws IOException {
        byte[] isadminyeah = new byte[]{'1'};
        File file = new File("/data/data/com.example.a76923.storemanager/isadmin");
        if (!file.exists()) {
            file.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(file);
        out.write(isadminyeah);
        out.close();
    }

    public static void newIsAdmin() throws IOException {
        byte[] isadminyeah = new byte[]{'0'};
        File file = new File("/data/data/com.example.a76923.storemanager/isadmin");
        if (!file.exists()) file.mkdirs();
        FileOutputStream out = new FileOutputStream(file);
        out.write(isadminyeah);
        out.close();
    }
}