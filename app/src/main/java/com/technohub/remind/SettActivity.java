package com.technohub.remind;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

public class SettActivity extends AppCompatActivity {
    AudioManager am;
    SwitchCompat s1,s2,s3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sett);
        s1=(SwitchCompat)findViewById(R.id.simpleSwitch);
        s2=(SwitchCompat)findViewById(R.id.simpleSwitch1);
        s3=(SwitchCompat)findViewById(R.id.simpleSwitch2);
        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                am.setRingerMode(1);
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                am.setRingerMode(2);
            }
        });
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                am.setRingerMode(3);
            }
        });

    }
}
