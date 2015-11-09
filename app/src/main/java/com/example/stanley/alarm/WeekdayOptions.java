package com.example.stanley.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class WeekdayOptions extends AppCompatActivity {

    private Button confirmButton,cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weekday_options);

        confirmButton = (Button) findViewById(R.id.weekday_options_confirm_button);
        cancelButton = (Button) findViewById(R.id.weekday_options_cancel_button);

        confirmButton.setOnClickListener(new ButtonListener());
        cancelButton.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent AlarmSettingIntent;
            switch(v.getId()){
                case R.id.weekday_options_confirm_button:
                    AlarmSettingIntent = new Intent();
                    AlarmSettingIntent.setClass(WeekdayOptions.this,AlarmSettingActivity.class);
                    startActivity(AlarmSettingIntent);
                    break;
                case R.id.weekday_options_cancel_button:
                    AlarmSettingIntent = new Intent();
                    AlarmSettingIntent.setClass(WeekdayOptions.this,AlarmSettingActivity.class);
                    startActivity(AlarmSettingIntent);
                    break;
            }
        }
    }

    // phone button "back" button handler - let it goes to home screen when pressed
    @Override
    public void onBackPressed() {
        Intent backHomeScreen = new Intent(Intent.ACTION_MAIN);
        backHomeScreen.addCategory(Intent.CATEGORY_HOME);
        backHomeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backHomeScreen);
    }
}
