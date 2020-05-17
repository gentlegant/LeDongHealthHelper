package com.nju.ledonghealthhelper.api;

import com.nju.ledonghealthhelper.util.LogUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateSqlTask extends BaseSqlTask<String,Integer,Boolean> {
    private OnSqlExecutingListener onSqlExecutingListener;
    @Override
    protected Boolean doInBackground(String... strings) {
        String sql = strings[0];
        LogUtils.d("mysql","sql:"+sql);
        if (connection == null) {
            if (!connectMysql()) {
                return false;
            }
        }
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        if (onSqlExecutingListener != null) {
            if (b) {
                onSqlExecutingListener.onSuccess();
            } else {
                onSqlExecutingListener.onFailure();
            }
        }
    }

    public void setOnSqlExecutingListener(OnSqlExecutingListener onSqlExecutingListener) {
        this.onSqlExecutingListener = onSqlExecutingListener;
    }

    public interface OnSqlExecutingListener {
        void onSuccess();
        void onFailure();
    }
}
