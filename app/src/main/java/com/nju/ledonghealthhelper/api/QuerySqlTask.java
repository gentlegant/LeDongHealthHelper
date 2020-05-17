package com.nju.ledonghealthhelper.api;

import android.os.AsyncTask;

import com.nju.ledonghealthhelper.util.LogUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuerySqlTask extends BaseSqlTask<String,Integer,ResultSet> {
    private OnSqlExecutingListener onSqlExecutingListener;
    @Override
    protected ResultSet doInBackground(String... strings) {
        String sql = strings[0];
        LogUtils.d("mysql","sql:"+sql);
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
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public interface OnSqlExecutingListener {
        void onSuccess(ResultSet resultSet);
        void onFailure();
    }
}
