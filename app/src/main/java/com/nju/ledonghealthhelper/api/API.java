package com.nju.ledonghealthhelper.api;

import com.nju.ledonghealthhelper.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class API {
    public static void signUp(String account,String password,String userName,OnRequestCallBack<Boolean> onRequestCallBack ) {
        final String rawSql = "insert into user";
        final String sql = "";
        SqlTask sqlTask= new SqlTask();
        sqlTask.execute(sql);
        sqlTask.setOnSqlExecutingListener(new SqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess(ResultSet resultSet) {
                if (onRequestCallBack != null ) {
                    onRequestCallBack.onSuccess(true);
                }
            }

            @Override
            public void onFailure() {
                if (onRequestCallBack != null) {
                    onRequestCallBack.onFailure();
                }
            }

        });
    }

    public static void hasUserExisted(String account,OnRequestCallBack<Boolean> onRequestCallBack) {
        final String rawSql = "select * from user where account = %s";
        final String sql = String.format(rawSql, account);
        SqlTask sqlTask = new SqlTask();
        sqlTask.execute(sql);
        sqlTask.setOnSqlExecutingListener(new SqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess(ResultSet resultSet) {
                if (onRequestCallBack != null) {
                    if (getResultNum(resultSet) == 1) {
                        onRequestCallBack.onSuccess(true);
                    }else {
                        onRequestCallBack.onSuccess(false);
                    }
                }
            }

            @Override
            public void onFailure() {
                if (onRequestCallBack != null ) {
                    onRequestCallBack.onFailure();
                }
            }


        });
    }

    private static int getResultNum(ResultSet resultSet) {
        try {
            resultSet.last();
            int num = resultSet.getRow();
            resultSet.beforeFirst();
            return num;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


}
