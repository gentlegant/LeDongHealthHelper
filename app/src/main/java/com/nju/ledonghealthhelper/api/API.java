package com.nju.ledonghealthhelper.api;

import com.nju.ledonghealthhelper.model.SportEvent;
import com.nju.ledonghealthhelper.model.User;
import com.nju.ledonghealthhelper.util.LogUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class API {
    public static void updateSetting(int id,String username,String gender,String description,String hobby,String phone,
                                     OnRequestCallBack<Boolean> onRequestCallBack) {
        String sql = "update user set ";
        if (username != null && username.length() > 0) {
            sql += "username='%s',";
            sql = String.format(sql,username);
        }
        if (gender != null && gender.length() > 0) {
            sql += "gender='%s',";
            sql = String.format(sql,gender);
        }
        if (description != null && description.length() > 0) {
            sql += "description='%s',";
            sql = String.format(sql,description);
        }
        if (hobby != null && hobby.length() > 0) {
            sql += "hobby='%s',";
            sql = String.format(sql,hobby);
        }
        if (phone != null && phone.length() > 0) {
            sql += "phone= %s',";
            sql = String.format(sql,phone);
        }
        sql = sql.substring(0,sql.length()-1);
        sql += " where id='%s'";
        sql = String.format(sql,id);
        UpdateSqlTask updateSqlTask = new UpdateSqlTask();
        updateSqlTask.setOnSqlExecutingListener(new UpdateSqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess() {
                if (onRequestCallBack != null) {
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
        updateSqlTask.execute(sql);
    }
    public static void pubSportEvent(int creatorId, String creatorName, long time, String sportType, String location, String content,
                                     OnRequestCallBack<Boolean> onRequestCallBack) {
        final String rawSql = "insert into sport_event (creator_id,creator_name,time,sport_type,pub_location,pub_content) " +
                "values(%s,'%s',%s,'%s','%s','%s')";
        final String sql = String.format(rawSql, creatorId, creatorName, time, sportType, location, content);
        UpdateSqlTask updateSqlTask = new UpdateSqlTask();
        updateSqlTask.setOnSqlExecutingListener(new UpdateSqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess() {
                if (onRequestCallBack != null) {
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
        updateSqlTask.execute(sql);
    }

    public static void requestAllSportEvents(OnRequestCallBack<List<SportEvent>> onRequestCallBack) {
        final String rawSql = "select * from sport_event order by time desc";
        final String sql = rawSql;
        QuerySqlTask querySqlTask = new QuerySqlTask();
        querySqlTask.setOnSqlExecutingListener(new QuerySqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess(ResultSet resultSet) {
                List<SportEvent> sportEvents = new ArrayList<>();
                boolean parseAll;
                while (true) {
                    try {
                        if (resultSet.next()) {
                            SportEvent sportEvent = new SportEvent();
                            sportEvent.setId(resultSet.getInt(1));
                            sportEvent.setCreatorId(resultSet.getInt(2));
                            sportEvent.setCreatorName(resultSet.getString(3));
                            sportEvent.setPubTime(resultSet.getLong(4));
                            sportEvent.setSportType(resultSet.getString(5));
                            sportEvent.setPubLocation(resultSet.getString(6));
                            sportEvent.setPubContent(resultSet.getString(7));
                            sportEvents.add(sportEvent);
                        } else {
                            parseAll = true;
                            break;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        onRequestCallBack.onFailure();
                        parseAll = false;
                        break;
                    }
                }
                if (parseAll) {
                    onRequestCallBack.onSuccess(sportEvents);
                }
            }

            @Override
            public void onFailure() {
                if (onRequestCallBack != null) {
                    onRequestCallBack.onFailure();
                }
            }
        });
        querySqlTask.execute(sql);
    }

    public static void signIn(String account, String password, OnRequestCallBack<User> onRequestCallBack) {
        final String rawSql = "select * from user where account='%s' and password = '%s'";
        final String sql = String.format(rawSql, account, password);
        QuerySqlTask querySqlTask = new QuerySqlTask();
        querySqlTask.setOnSqlExecutingListener(new QuerySqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess(ResultSet resultSet) {
                if (onRequestCallBack != null) {
                    if (getResultNum(resultSet) == 1) {
                        User user = new User();
                        try {
                            resultSet.next();
                            user.setId(resultSet.getInt(1));
                            user.setAccount(resultSet.getString(2));
                            user.setPassword(resultSet.getString(3));
                            user.setUserName(resultSet.getString(4));
                            user.setDescription(resultSet.getString(5));
                            user.setHobby(resultSet.getString(6));
                            user.setPhone(resultSet.getString(7));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        LogUtils.d("user", user.toString());
                        onRequestCallBack.onSuccess(user);
                    } else {
                        onRequestCallBack.onFailure();
                    }
                }
            }

            @Override
            public void onFailure() {
                if (onRequestCallBack != null) {
                    onRequestCallBack.onFailure();
                }
            }
        });
        querySqlTask.execute(sql);

    }

    public static void signUp(String account, String password, String userName, OnRequestCallBack<Boolean> onRequestCallBack) {
        final String rawSql = "insert into user (account,password,username) values ('%s','%s','%s')";
        final String sql = String.format(rawSql, account, password, userName);
        UpdateSqlTask updateSqlTask = new UpdateSqlTask();
        updateSqlTask.setOnSqlExecutingListener(new UpdateSqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess() {
                if (onRequestCallBack != null) {
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
        updateSqlTask.execute(sql);

    }

    public static void hasUserExisted(String account, OnRequestCallBack<Boolean> onRequestCallBack) {
        final String rawSql = "select * from user where account = '%s'";
        final String sql = String.format(rawSql, account);
        QuerySqlTask querySqlTask = new QuerySqlTask();
        querySqlTask.setOnSqlExecutingListener(new QuerySqlTask.OnSqlExecutingListener() {
            @Override
            public void onSuccess(ResultSet resultSet) {
                if (onRequestCallBack != null) {
                    if (getResultNum(resultSet) == 1) {
                        onRequestCallBack.onSuccess(true);
                    } else {
                        onRequestCallBack.onSuccess(false);
                    }
                }
            }

            @Override
            public void onFailure() {
                if (onRequestCallBack != null) {
                    onRequestCallBack.onFailure();
                }
            }
        });
        querySqlTask.execute(sql);

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
