package com.nju.ledonghealthhelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.nju.ledonghealthhelper.R;
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
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_in;
    }

    @OnClick(R.id.sign_in_btn)
    void signIn(){
        signInSuccess();
    }

    void signInSuccess(){
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }


    @OnClick({R.id.sign_up_btn})
    void startSignUpActivity(){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}
