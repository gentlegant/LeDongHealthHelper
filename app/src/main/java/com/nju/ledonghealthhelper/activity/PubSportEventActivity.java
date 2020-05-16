package com.nju.ledonghealthhelper.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.nju.ledonghealthhelper.R;
import com.nju.ledonghealthhelper.view.LDEditText;

import butterknife.BindView;

public class PubSportEventActivity extends BaseActivity {
    @BindView(R.id.sport_type_et)
    LDEditText sportTypeET;
    @BindView(R.id.sport_content_et)
    LDEditText sportContentET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("发布运动社交信息");
        enableBack();
        sportTypeET.setLeftText("运动项目");
        sportContentET.setLeftText("信息内容");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pub_sport_event;
    }
}
