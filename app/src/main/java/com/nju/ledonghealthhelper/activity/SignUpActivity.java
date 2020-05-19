package com.nju.ledonghealthhelper.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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
    @BindView(R.id.next_btn)
    Button nextBtn;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.container_1)
    LinearLayout container1;
    @BindView(R.id.container_2)
    LinearLayout container2;
    @BindView(R.id.question1_et)
    LDEditText question1ET;
    @BindView(R.id.question2_et)
    LDEditText question2ET;


    private String account, password, userName, answer1, answer2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountET.setLeftText("账号");
        passwordET.setLeftText("密码");
        userNameET.setLeftText("昵称");
        setToolbarTitle("用户注册");
        enableBack();

    }

    private void requestPermissions() {
        String[] perms = {Manifest.permission.INTERNET};
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_sign_up;
    }

    @OnClick(R.id.next_btn)
    void next() {
        account = accountET.getText();
        password = passwordET.getText();
        userName = userNameET.getText();
        if (account.length() == 0) {
            Toast.makeText(getApplicationContext(), "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.length() == 0) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userName.length() == 0) {
            Toast.makeText(getApplicationContext(), "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        API.hasUserExisted(account, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean b) {
                if (!b) {
                    nextBtn.setText("提交");
                    container2.setVisibility(View.VISIBLE);
                    container1.setVisibility(View.VISIBLE);
                    submitBtn.setVisibility(View.VISIBLE);
                    accountET.setVisibility(View.GONE);
                    passwordET.setVisibility(View.GONE);
                    userNameET.setVisibility(View.GONE);
                    nextBtn.setVisibility(View.GONE);
                } else {
                    Toast.makeText(getApplicationContext(), "账号已经存在", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.submit_btn})
    void submit() {
        answer1 = question1ET.getText();
        answer2 = question2ET.getText();
        if (answer1.length() == 0 || answer2.length() == 0) {
            Toast.makeText(getApplicationContext(), "请填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        API.signUp(account, password, userName, answer1, answer2, new OnRequestCallBack<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure() {
                Toast.makeText(getApplicationContext(), "注册失败，请重试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
