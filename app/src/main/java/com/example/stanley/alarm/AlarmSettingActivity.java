package com.example.stanley.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.os.Parcelable;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import java.util.ArrayList;

public class AlarmSettingActivity extends AppCompatActivity {

    private Button confirmButton,cancelButton,startTimeButton,endTimeButton, showStartB,
    showEndB;
    private ToggleButton mon, tue, wed, thu, fri, sat, sun;
    private Switch weatherSwitch, calendarSwitch;
    private int current_total_items;
    private int current_alarm_index;
    private int requestCode = 0;
    private int hourTemp = 0, minuteTemp = 0;
    private int sHour=12, sMinute=0, eHour=12, eMinute=0, others=0 , weekday=0;

    private ArrayList<Alarm> alarms;
    private Context thisContext;

    private boolean b_add_clock = true;
    private boolean changeTime = false, startChanged = false, endChanged = false;

    private Intent getTime, alarmListIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_setting);

        thisContext = this;

        Bundle bundleObject = getIntent().getExtras();
        b_add_clock = bundleObject.getBoolean("b_add_clock");
        current_total_items = bundleObject.getInt("current_total_items");
        current_alarm_index = bundleObject.getInt("current_alarm_index");
        alarms = (ArrayList<Alarm>) bundleObject.getSerializable("alarm_array");
//        Intent totalSet = new Intent(AlarmSettingActivity.this, AlarmListActivity.class);

        confirmButton = (Button) findViewById(R.id.alarm_setting_confirm_button);
        cancelButton = (Button) findViewById(R.id.alarm_setting_cancel_button);
        startTimeButton = (Button) findViewById(R.id.start_time_button);
        endTimeButton = (Button) findViewById(R.id.end_time_button);
        showStartB = (Button) findViewById(R.id.show_Start_Button);
        showEndB = (Button) findViewById(R.id.show_End_Button);
        mon = (ToggleButton) findViewById(R.id.toggle_mon);
        tue = (ToggleButton) findViewById(R.id.toggle_tue);
        wed = (ToggleButton) findViewById(R.id.toggle_wed);
        thu = (ToggleButton) findViewById(R.id.toggle_thu);
        fri = (ToggleButton) findViewById(R.id.toggle_fri);
        sat = (ToggleButton) findViewById(R.id.toggle_sat);
        sun = (ToggleButton) findViewById(R.id.toggle_sun);
        weatherSwitch = (Switch) findViewById(R.id.weather_switch);
        calendarSwitch = (Switch) findViewById(R.id.calendar_switch);

        mon.setTextOn("Mon");
        mon.setTextOff("Mon");
        tue.setTextOff("Tue");
        tue.setTextOn("Tue");
        wed.setTextOff("Wed");
        wed.setTextOn("wed");
        thu.setTextOff("Thu");
        thu.setTextOn("Thu");
        fri.setTextOff("Fri");
        fri.setTextOn("Fri");
        sat.setTextOff("Sat");
        sat.setTextOn("Sat");
        sun.setTextOff("Sun");
        sun.setTextOn("Sun");

        setButtonText(showStartB, sHour, sMinute);
        setButtonText(showEndB, eHour, eMinute);

        confirmButton.setOnClickListener(new ButtonListener());
        cancelButton.setOnClickListener(new ButtonListener());
        showStartB.setOnClickListener(new ButtonListener());
        showEndB.setOnClickListener(new ButtonListener());
        mon.setOnCheckedChangeListener(new CheckListener());
        tue.setOnCheckedChangeListener(new CheckListener());
        wed.setOnCheckedChangeListener(new CheckListener());
        thu.setOnCheckedChangeListener(new CheckListener());
        fri.setOnCheckedChangeListener(new CheckListener());
        sat.setOnCheckedChangeListener(new CheckListener());
        sun.setOnCheckedChangeListener(new CheckListener());

        weatherSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    others = others + 1;
                else
                    others = others - 1;
            }
        });
        calendarSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    others = others + 2;
                else
                    others = others - 2;
            }
        });

        getTime = getIntent();
        changeTime = getTime.getBooleanExtra("changeBool", false);
        alarmListIntent = new Intent(getApplicationContext(),AlarmListActivity.class);
    }

    private class CheckListener implements OnCheckedChangeListener
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            switch(buttonView.getId())
            {
                case R.id.weather_switch:
                    if(isChecked)
                        others = others + 1;
                    else
                        others = others - 1;
                    break;
                case R.id.calendar_switch:
                    if(isChecked)
                        others = others + 2;
                    else
                        others = others - 2;
                    break;
            }
        }
    }

    private class ButtonListener implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            switch(v.getId())
            {
                case R.id.alarm_setting_confirm_button:

      //              alarmSet.putInt("weekday", weekday);
      //              alarmSet.putInt("others", others);
      //              alarmListIntent.putExtra("alarmSet",alarmSet);
                    alarmListIntent.putExtra("week",weekday);
                    alarmListIntent.putExtra("others",others);
                    if(b_add_clock)
                    {
                        current_total_items++;
                        alarmListIntent.putExtra("b_add_clock", true);
                    }
                    alarmListIntent.putExtra("update_current_total_items", current_total_items);
                    AlarmSettingActivity.this.setResult(1, alarmListIntent);
                    AlarmSettingActivity.this.finish();
                    break;
                case R.id.alarm_setting_cancel_button:
                    alarmListIntent = new Intent(getApplicationContext(),AlarmListActivity.class);
                    alarmListIntent.putExtra("b_add_clock", false);
                    alarmListIntent.putExtra("update_current_total_items", current_total_items);
                    AlarmSettingActivity.this.setResult(1, alarmListIntent);
                    AlarmSettingActivity.this.finish();
                    break;
                case R.id.show_Start_Button:
                    requestCode = 1;
                    Intent startTimeIntent = new Intent(AlarmSettingActivity.this,SetClockTime.class);
                    startTimeIntent.putExtra("startOrend",true);
                    startTimeIntent.putExtra("shour",sHour);
                    startTimeIntent.putExtra("sminute",sMinute);
                    startTimeIntent.putExtra("state", startChanged);
                    startActivityForResult(startTimeIntent, requestCode);
                    break;
                case R.id.show_End_Button:
                    requestCode = 1;
                    Intent endTimeIntent = new Intent(AlarmSettingActivity.this,SetClockTime.class);
                    endTimeIntent.putExtra("startOrend",false);
                    endTimeIntent.putExtra("state",endChanged);
                    endTimeIntent.putExtra("shour",sHour);
                    endTimeIntent.putExtra("sminute", sMinute);
                    startActivityForResult(endTimeIntent, requestCode);
                    break;
                case R.id.toggle_mon:
                    weekday = weekday + 1;
                    break;
                case R.id.toggle_tue:
                    weekday = weekday + 2;
                    break;
                case R.id.toggle_wed:
                    weekday = weekday + 4;
                    break;
                case R.id.toggle_thu:
                    weekday = weekday + 8;
                    break;
                case R.id.toggle_fri:
                    weekday = weekday + 16;
                    break;
                case R.id.toggle_sat:
                    weekday = weekday + 32;
                    break;
                case R.id.toggle_sun:
                    weekday = weekday + 64;
                    break;
            }
        }
    }

    public void setIntent(Intent intent, boolean bool)
    {
        boolean state;
        if(bool == true)
        {
            state = intent.getBooleanExtra("stateRtn", true);
            if(state == true)
            {
                alarmListIntent.putExtra("startHour", hourTemp);
                alarmListIntent.putExtra("startMinute", minuteTemp);
                if(hourTemp > 12)
                {
                    hourTemp = hourTemp - 12;
                    if(minuteTemp/10 != 0)
                        showStartB.setText(hourTemp+":"+minuteTemp+"  pm");
                    else
                        showStartB.setText(hourTemp+":0"+minuteTemp+"  pm");
                }
                else
                {
                    if(minuteTemp/10 != 0)
                        showStartB.setText(hourTemp+":"+minuteTemp+"  am");
                    else
                        showStartB.setText(hourTemp+":0"+minuteTemp+"  am");
                }
            }
            else
            {
                alarmListIntent.putExtra("endHour", hourTemp);
                alarmListIntent.putExtra("endMinute", minuteTemp);
                if(hourTemp > 12)
                {
                    hourTemp = hourTemp - 12;
                    if (minuteTemp / 10 != 0)
                        showEndB.setText(hourTemp + ":" + minuteTemp+"  pm");
                    else
                        showEndB.setText(hourTemp + ":0" + minuteTemp+"  pm");
                }
                else
                {
                    if(minuteTemp/10 != 0)
                        showEndB.setText(hourTemp+":"+minuteTemp+"  am");
                    else
                        showEndB.setText(hourTemp+":0"+minuteTemp+"  am");
                }
            }
        }
    }

    public void setButtonText(Button btn, int hour, int minute){
        if(minute/10 == 0)
            btn.setText(hour+":0"+minute);
        else
            btn.setText(hour+":"+minute);
    }
    // phone button "back" button handler - let it goes to home screen when pressed
    @Override
    public void onBackPressed() {
        Intent alarmListIntent = new Intent(getApplicationContext(),AlarmListActivity.class);
        alarmListIntent.putExtra("b_add_clock", false);
        alarmListIntent.putExtra("update_current_total_items", current_total_items);
        AlarmSettingActivity.this.setResult(1, alarmListIntent);
        AlarmSettingActivity.this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        Bundle bd = data.getBundleExtra("timeSet");
        hourTemp =  data.getIntExtra("hour",12);
        changeTime = data.getBooleanExtra("changeBool",false);
        startChanged = changeTime;
        endChanged = changeTime;
        minuteTemp = data.getIntExtra("minute",30);
        switch(resultCode){
            case 1:
                setIntent(data, changeTime);
                break;
            case 2:
                Toast.makeText(AlarmSettingActivity.this,"setting",Toast.LENGTH_SHORT).show();
            default:
                break;
        }
    }
}
