package com.nju.ledonghealthhelper.view.adapter;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nju.ledonghealthhelper.R;
import com.nju.ledonghealthhelper.model.SportEvent;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SportEventListAdapter extends RecyclerView.Adapter<SportEventListAdapter.SportEventItemViewHolder> {
    private List<SportEvent> sportEvents;
    private Context context;

    public SportEventListAdapter(Context context) {
        this.context = context;
    }

    public void setSportEvents(List<SportEvent> sportEvents) {
        this.sportEvents = sportEvents;
    }

    @NonNull
    @Override
    public SportEventItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_sport_event, null);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);
        SportEventItemViewHolder sportEventItemViewHolder = new SportEventItemViewHolder(v);
        return sportEventItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SportEventItemViewHolder holder, int position) {
        if (sportEvents == null || sportEvents.size() == 0){
            return;
        }
        SportEvent sportEvent = sportEvents.get(position);
        if (sportEvent != null) {
            holder.userNameTV.setText(sportEvent.getCreatorName());
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
            holder.pubTimeTV.setText( sdf.format(sportEvent.getPubTime()));
            holder.sportTypeTV.setText(sportEvent.getSportType());
            holder.pubLocationTV.setText(sportEvent.getPubLocation());
            holder.pubContentTV.setText(sportEvent.getPubContent());
        }
    }

    @Override
    public int getItemCount() {
        if (sportEvents == null) {
            return 0;
        }
        return sportEvents.size();
    }

    class SportEventItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_name_tv)
        TextView userNameTV;
        @BindView(R.id.pub_time_tv)
        TextView pubTimeTV;
        @BindView(R.id.sport_type_tv)
        TextView sportTypeTV;
        @BindView(R.id.pub_location_tv)
        TextView pubLocationTV;
        @BindView(R.id.pub_content_tv)
        TextView pubContentTV;
        @BindView(R.id.comment_et)
        EditText commentET;
        @BindView(R.id.send_ibtn)
        ImageButton sendIBtn;

        public SportEventItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            sendIBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (commentET.getText().length() == 0 ){
                        return;
                    }
                    commentET.setText("");
                    Toast.makeText(context,"发送成功",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
