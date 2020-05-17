package com.nju.ledonghealthhelper.activity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.nju.ledonghealthhelper.R;
import com.nju.ledonghealthhelper.api.API;
import com.nju.ledonghealthhelper.api.OnRequestCallBack;
import com.nju.ledonghealthhelper.view.LDEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {

    @BindView(R.id.account_et)
    LDEditText accountET;
    @BindView(R.id.password_et)
    LDEditText passwordET;
    @BindView(R.id.user_name_et)
    LDEditText userNameET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountET.setLeftText("账号");
        passwordET.setLeftText("密码");
        userNameET.setLeftText("昵称");
        setToolbarTitle("用户注册");
        enableBack();

    }

    private void requestPermissions(){
        String[] perms = {Manifest.permission.INTERNET};
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_up;
    }

    @OnClick(R.id.summit_btn)
    void summit() {
        final String account = accountET.getText();
        final String password = passwordET.getText();
        final String userName = userNameET.getText();
        if (account.length() == 0 ) {
            Toast.makeText(getApplicationContext(),"账号不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0 ) {
            Toast.makeText(getApplicationContext(),"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (userName.length() == 0 ) {
            Toast.makeText(getApplicationContext(),"昵称不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        API.hasUserExisted(account, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean b) {
                if (!b) {
                    API.signUp(account, password, userName, new OnRequestCallBack<Boolean>() {
                        @Override
                        public void onSuccess(Boolean aBoolean) {
                            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(getApplicationContext(),"注册失败，请重试",Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(),"账号已经存在",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
