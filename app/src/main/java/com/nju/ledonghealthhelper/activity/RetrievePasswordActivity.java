package com.nju.ledonghealthhelper.activity;

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

public class RetrievePasswordActivity extends BaseActivity {

    @BindView(R.id.account_et)
    LDEditText accountET;
    @BindView(R.id.question1_et)
    LDEditText question1ET;
    @BindView(R.id.question2_et)
    LDEditText question2ET;
    @BindView(R.id.container_1)
    LinearLayout container1;
    @BindView(R.id.container_2)
    LinearLayout container2;
    @BindView(R.id.next_btn)
    Button nextBtn;

    private boolean stateNext = true;
    private String account = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("找回密码");
        enableBack();
        accountET.setLeftText("账号");
        question1ET.setLeftText("回答");
        question2ET.setLeftText("回答");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_retrieve_password;
    }

    @OnClick(R.id.next_btn)
    void next() {
        if (stateNext) {
            final String account = accountET.getText();
            final String answer1 = question1ET.getText();
            final String answer2 = question2ET.getText();
            if (account.length() == 0 || answer1.length() == 0 || answer2.length() == 0){
                Toast.makeText(getApplicationContext(),"请填写完整",Toast.LENGTH_SHORT).show();
                return;
            }
            showDefaultProgressBar();
            API.validateAnswer(account, answer1, answer2, new OnRequestCallBack<Boolean>() {
                @Override
                public void onSuccess(Boolean aBoolean) {
                    if (aBoolean) {
                        RetrievePasswordActivity.this.account = account;
                        stateNext = false;
                        nextBtn.setText("完成");
                        container1.setVisibility(View.GONE);
                        container2.setVisibility(View.GONE);
                        accountET.setLeftText("新的密码");
                        accountET.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(),"回答错误",Toast.LENGTH_SHORT).show();
                    }
                    hideDefaultProgressBar();
                }

                @Override
                public void onFailure() {
                    hideDefaultProgressBar();
                    Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            final String newPassword = accountET.getText();
            if (newPassword != null && newPassword.length() > 0) {
                showDefaultProgressBar();
                API.resetPassword(account, newPassword, new OnRequestCallBack<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        Toast.makeText(getApplicationContext(),"设置密码成功",Toast.LENGTH_SHORT).show();
                        hideDefaultProgressBar();
                        finish();
                    }

                    @Override
                    public void onFailure() {
                        hideDefaultProgressBar();
                        Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

}
