package com.nju.ledonghealthhelper.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nju.ledonghealthhelper.App;
import com.nju.ledonghealthhelper.R;
import com.nju.ledonghealthhelper.api.API;
import com.nju.ledonghealthhelper.api.OnRequestCallBack;
import com.nju.ledonghealthhelper.model.User;
import com.nju.ledonghealthhelper.view.LDEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.account_et)
    LDEditText accountET;
    @BindView(R.id.user_name_et)
    LDEditText userNameET;
    @BindView(R.id.gender_et)
    LDEditText genderET;
    @BindView(R.id.description_et)
    LDEditText decriptionET;
    @BindView(R.id.hobby_et)
    LDEditText hobbyET;
    @BindView(R.id.phone_et)
    LDEditText phoneET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountET.setLeftText("账号");
        userNameET.setLeftText("昵称");
        genderET.setLeftText("性别");
        decriptionET.setLeftText("描述");
        hobbyET.setLeftText("爱好");
        phoneET.setLeftText("电话");
        User user = App.getUser();
        if (user != null) {
            accountET.setEditable(false);
            accountET.setText(user.getAccount());
            userNameET.setText(user.getUserName());
            genderET.setText(user.getGender());
            decriptionET.setText(user.getDescription());
            hobbyET.setText(user.getHobby());
            phoneET.setText(user.getPhone());
        }
        setToolbarTitle("用户信息");
        enableBack();
    }

    @OnClick({R.id.next_btn})
    void summitSetting() {
        User user = App.getUser();
        final String userName = userNameET.getText();
        final String gender = genderET.getText();
        final String description = decriptionET.getText();
        final String hobby = hobbyET.getText();
        final String phone = phoneET.getText();
        showDefaultProgressBar();
        API.updateSetting(user.getId(), userName, gender, description, hobby, phone, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                hideDefaultProgressBar();
                user.setUserName(userName);
                user.setGender(gender);
                user.setDescription(description);
                user.setHobby(hobby);
                user.setPhone(phone);
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                hideDefaultProgressBar();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }


}
