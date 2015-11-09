package com.example.stanley.alarm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Set;

public class SetClockTime extends AppCompatActivity
{

    private Button confirmButton,cancelButton;
    private Intent getTime= new Intent(), state = new Intent();
    private int hourDefault = 12;
    private int minuteDefault = 0;
    private Bundle timeSet = new Bundle();
    private boolean stateBool = false, startOrend = true;
    private int resultCode = 0;
    private int fhour = 10, fminute = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_clock_time_pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        double width = dm.widthPixels * 0.8;
        double height = dm.heightPixels * 0.6;

        getWindow().setLayout((int) width, (int) height);

 //       View viewer = findViewById(android.R.id.content);

        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.dimAmount = (float) 0.5;
        getWindow().setAttributes(wlp);
     //   viewer.getBackground().setAlpha(120);



        confirmButton = (Button) findViewById(R.id.set_clock_confirm_button);
        cancelButton = (Button) findViewById(R.id.set_clock_cancel_button);

        confirmButton.setOnClickListener(new ButtonListener());
        cancelButton.setOnClickListener(new ButtonListener());

        state = this.getIntent();
        stateBool = state.getBooleanExtra("state", true);
        getTime = new Intent(SetClockTime.this, AlarmSettingActivity.class);
        startOrend = state.getBooleanExtra("startOrend", true);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(false);
        if(stateBool == false)
        {
            getTime.putExtra("stateRtn",true);
            timePicker.setCurrentHour(10);
            timePicker.setCurrentMinute(0);
        }
        else
        {
            hourDefault = getTime.getIntExtra("shour",10);
            minuteDefault = getTime.getIntExtra("sminute",5);
//            getTime.putExtra("stateRtn",false);
            timePicker.setCurrentMinute(minuteDefault);
            timePicker.setCurrentHour(hourDefault);
        }
        getTime.putExtra("stateRtn", startOrend);



        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
               // getTime = new Intent(SetClockTime.this,AlarmSettingActivity.class);
                timeSet.putInt("hour", hourOfDay);
                timeSet.putInt("minute", minute);
                fhour = hourOfDay;
                fminute = minute;
//                Toast.makeText(SetClockTime.this, hourOfDay+":"+minute+stateBool,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.set_clock_confirm_button:
                    resultCode = 1;
                    getTime.putExtras(timeSet);
                    getTime.putExtra("changeBool", true);
                    getTime.putExtra("hour",fhour);
                    getTime.putExtra("minute",fminute);
                    SetClockTime.this.setResult(resultCode, getTime);
                    SetClockTime.this.finish();
                    break;
                case R.id.set_clock_cancel_button:
                    resultCode = 1;
                    getTime.putExtra("changeBool", false);
                    SetClockTime.this.setResult(resultCode, getTime);
                    SetClockTime.this.finish();
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
