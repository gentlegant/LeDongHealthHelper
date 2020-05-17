package com.nju.ledonghealthhelper;

import android.app.Application;

import com.nju.ledonghealthhelper.model.User;

public class App extends Application {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        App.user = user;
    }
}
