package com.nju.ledonghealthhelper.api;

import android.os.AsyncTask;

import com.nju.ledonghealthhelper.util.LogUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseSqlTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private String url = "jdbc:mysql://116.62.100.110:3306/test2?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8";
//    private String user = "root";
//    private String password = "a412793689";
    private String user = "user";
    private String password = "123456";
    protected static Connection connection;
    protected synchronized boolean connectMysql(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,password);
            LogUtils.d("Mysql","connect successfully");
            return true;
        } catch (SQLException e) {
            LogUtils.d("Mysql","connect unsuccessfully");
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtils.d("Mysql","loading driver failed");
            return false;
        }
    }

}
