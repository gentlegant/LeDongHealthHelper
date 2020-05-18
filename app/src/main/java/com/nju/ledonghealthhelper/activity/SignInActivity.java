package com.nju.ledonghealthhelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
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

public class SignInActivity extends BaseActivity {
    @BindView(R.id.sign_in_btn)
    Button signInBtn;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;
    @BindView(R.id.account_et)
    LDEditText accountET;
    @BindView(R.id.password_et)
    LDEditText passwordET;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountET.setLeftText("账号");
        passwordET.setLeftText("密码");
        passwordET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_in;
    }

    @OnClick(R.id.sign_in_btn)
    void signIn(){
        final String account = accountET.getText();
        final String password = passwordET.getText();
        if (account.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(),"账号密码为空",Toast.LENGTH_SHORT).show();
            return;
        }
        showDefaultProgressBar();
        API.signIn(account, password, new OnRequestCallBack<User>() {
            @Override
            public void onSuccess(User user) {
                hideDefaultProgressBar();
                signInSuccess();
                App.setUser(user);
            }

            @Override
            public void onFailure() {
                hideDefaultProgressBar();
                Toast.makeText(getApplicationContext(),"账号密码错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.forgot_password_tv})
    void startRetrievePasswordActivity(){
        Intent intent = new Intent(this,RetrievePasswordActivity.class);
        startActivity(intent);
    }

    void signInSuccess(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        finish();
    }


    @OnClick({R.id.sign_up_btn})
    void startSignUpActivity(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}
