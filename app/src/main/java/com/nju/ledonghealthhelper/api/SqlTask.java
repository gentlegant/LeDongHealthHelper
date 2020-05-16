package com.nju.ledonghealthhelper.api;

import android.os.AsyncTask;

import com.nju.ledonghealthhelper.util.LogUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlTask extends AsyncTask<String,Integer,ResultSet> {
    private String url = "jdbc:mysql://116.62.100.110:3306/newdb";
    private String user = "root";
    private String password = "thepassword";
    private static Connection connection;
    private OnSqlExecutingListener onSqlExecutingListener;
    @Override
    protected ResultSet doInBackground(String... strings) {
        String sql = strings[0];
        if (connection == null) {
            if (!connectMysql()) {
                return null;
            }
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setOnSqlExecutingListener(OnSqlExecutingListener onSqlExecutingListener) {
        this.onSqlExecutingListener = onSqlExecutingListener;
    }

    @Override
    protected void onPostExecute(ResultSet resultSet) {
        if (onSqlExecutingListener != null) {
            if (resultSet != null) {
                onSqlExecutingListener.onSuccess(resultSet);
            } else {
                onSqlExecutingListener.onFailure();
            }
        }
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private synchronized boolean connectMysql(){
        try {
            connection = DriverManager.getConnection(url,user,password);
            LogUtils.d("Mysql","connect successfully");
            return true;
        } catch (SQLException e) {
            LogUtils.d("Mysql","connect unsuccessfully");
            e.printStackTrace();
            return false;
        }
    }

    public interface OnSqlExecutingListener {
        void onSuccess(ResultSet resultSet);
        void onFailure();
    }
}
