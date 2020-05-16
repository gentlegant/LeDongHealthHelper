package com.nju.ledonghealthhelper.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nju.ledonghealthhelper.R;
import com.nju.ledonghealthhelper.model.SportEvent;
import com.nju.ledonghealthhelper.view.adapter.SportEventListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {
    @BindView(R.id.sport_event_rv)
    RecyclerView sportEventRV;

    private SportEventListAdapter sportEventListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportEventListAdapter = new SportEventListAdapter(this);
        sportEventRV.setLayoutManager(new LinearLayoutManager(this));
        sportEventRV.setAdapter(sportEventListAdapter);

        List<SportEvent> sportEvents = new ArrayList<>();
        SportEvent sportEvent = new SportEvent();
        sportEvent.setPubTime(1111);
        sportEvent.setUserName("Meanlay");
        sportEvent.setPubContent("我发布了一条篮球信息");
        sportEvent.setPubLocation("杭州市江干区");
        sportEvent.setSportType("篮球");
        sportEvents.add(sportEvent);
        sportEventListAdapter.setSportEvents(sportEvents);
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
}
