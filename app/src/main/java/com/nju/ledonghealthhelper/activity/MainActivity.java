package com.nju.ledonghealthhelper.activity;


import android.os.Bundle;

import com.nju.ledonghealthhelper.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int getContentView() {
        return 0;
    }
}
