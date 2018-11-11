package com.team15x3.caucse.takecareoftherefrigerator;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TabSettingFragment extends Fragment {

    TextView tvUserName;
    ImageButton ibLogout, ibAlarm, ibFriger;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab_setting_fragment,container,false);
        tvUserName = (TextView)view.findViewById(R.id.tvUserName);
        ibLogout = (ImageButton)view.findViewById(R.id.ibLogout);
        ibAlarm = (ImageButton) view.findViewById(R.id.ibAlarm);
        ibFriger = (ImageButton)view.findViewById(R.id.ibFriger);
        String userID = User.INSTANCE.getID();
        tvUserName.setText(userID);


        ibFriger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ManageFrigerActivity.class);
                startActivityForResult(intent,1);
            }
        });


        return view;
    }
}
