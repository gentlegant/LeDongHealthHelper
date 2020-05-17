package com.nju.ledonghealthhelper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nju.ledonghealthhelper.R;
import com.nju.ledonghealthhelper.api.API;
import com.nju.ledonghealthhelper.api.OnRequestCallBack;
import com.nju.ledonghealthhelper.model.SportEvent;
import com.nju.ledonghealthhelper.view.SpacesItemDecoration;
import com.nju.ledonghealthhelper.view.adapter.SportEventListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.sport_event_rv)
    RecyclerView sportEventRV;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private SportEventListAdapter sportEventListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportEventListAdapter = new SportEventListAdapter(this);
        sportEventRV.setLayoutManager(new LinearLayoutManager(this));
        sportEventRV.setAdapter(sportEventListAdapter);
        sportEventRV.addItemDecoration(new SpacesItemDecoration(dp2Px(8)));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestAllSportEvents();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        requestAllSportEvents();
    }

    private void requestAllSportEvents() {
        swipeRefreshLayout.setRefreshing(true);
        API.requestAllSportEvents(new OnRequestCallBack<List<SportEvent>>() {
            @Override
            public void onSuccess(List<SportEvent> sportEvents) {
                sportEventListAdapter.setSportEvents(sportEvents);
                sportEventListAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                hideDefaultProgressBar();
                Toast.makeText(getApplicationContext(),"请求失败",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }


    @OnClick(R.id.pub_sport_event_btn)
    void startPubSportEventActivity(){
        Intent intent = new Intent(this,PubSportEventActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.setting_ibtn)
    void startSettingActivity() {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }
}
