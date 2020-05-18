package com.nju.ledonghealthhelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.nju.ledonghealthhelper.App;
import com.nju.ledonghealthhelper.R;
import com.nju.ledonghealthhelper.api.API;
import com.nju.ledonghealthhelper.api.OnRequestCallBack;
import com.nju.ledonghealthhelper.model.User;
import com.nju.ledonghealthhelper.view.LDEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class PubSportEventActivity extends BaseLocationActivity {
    @BindView(R.id.sport_type_et)
    LDEditText sportTypeET;
    @BindView(R.id.sport_content_et)
    LDEditText sportContentET;
    @BindView(R.id.location_et)
    LDEditText locationET;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("发布运动社交信息");
        enableBack();
        sportTypeET.setLeftText("运动项目");
        sportContentET.setLeftText("信息内容");
        locationET.setLeftText("所处位置");
    }

    @Override
    protected void onResume() {
        super.onResume();
        startLocation();
    }

    @Override
    void onReceiveLocation(BDLocation location) {
        String city = location.getCity();    //获取城市
        String district = location.getDistrict();    //获取区县
        locationET.setText(city+district);
        stopLocation();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pub_sport_event;
    }

    @OnClick(R.id.pub_btn)
    void pubSportEvent(){
        if (App.getUser() == null) {
            return;
        }
        final String sportType = sportTypeET.getText();
        final String sportContent = sportContentET.getText();
        final String location = locationET.getText();
        if (sportType == null) {
            Toast.makeText(getApplicationContext(),"运动类型不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (sportContent == null) {
            Toast.makeText(getApplicationContext(),"描述不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (location == null) {
            Toast.makeText(getApplicationContext(),"位置不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        User user = App.getUser();
        showDefaultProgressBar();
        API.pubSportEvent(user.getId(),user.getUserName(),System.currentTimeMillis(),sportType,location,sportContent,new OnRequestCallBack<Boolean>(){

            @Override
            public void onSuccess(Boolean aBoolean) {
                hideDefaultProgressBar();
                finish();
                Toast.makeText(getApplicationContext(),"发布成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                hideDefaultProgressBar();
                Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
