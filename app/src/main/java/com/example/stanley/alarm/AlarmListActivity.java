package com.example.stanley.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AlarmListActivity extends AppCompatActivity {

    private final int MAX_CLOCKS = 10;
    private int current_total_items = 2;
    private int current_alarm_index;
    private int sHour = 12, sMinute = 0, eHour = 12, eMinute = 0, weekDay = 0;

    private Alarm [] alarm = new Alarm[MAX_CLOCKS];
    private ArrayList<Alarm> alarms = new ArrayList<Alarm>(MAX_CLOCKS);
    private ArrayList<HashMap<String,Integer>> list_for_ListView = new ArrayList<HashMap<String, Integer>>();

    private ListView listView;
    private Context thisContext;
    private MySimpleAdapter simpleAdapter;


    AlarmManager alarmManager = null;
    private PendingIntent[] pendingIntent = new PendingIntent[MAX_CLOCKS];
    private PopupWindow popWindow;
//    private Intent[] alarmSet = new Intent[MAX_CLOCKS];

    private ImageButton btn_add_clock, btn_delete_clock;
    private Button btn_conform_delete;

    private boolean b_show_delete_checkbox;
    private boolean [] b_enable_clock = new boolean[MAX_CLOCKS];
    private boolean [] b_delete_list = new boolean[MAX_CLOCKS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) this.findViewById(R.id.listView);

        thisContext = this;

<<<<<<< HEAD


        alarmManager = (AlarmManager) AlarmListActivity.this.getSystemService(Context.ALARM_SERVICE);

=======
        alarmManager = (AlarmManager) AlarmListActivity.this.getSystemService(Context.ALARM_SERVICE);

//        for(int i=0; i < MAX_CLOCKS; i++) {
  //          alarms.add(new Alarm());
    //    }

>>>>>>> 7715bb6ada20f42626697b71e63ba11ea0f871ce
        simpleAdapter = new MySimpleAdapter(this, list_for_ListView);
        listView.setAdapter(simpleAdapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        View viewActionBar = getLayoutInflater().inflate(R.layout.actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT);
        actionBar.setCustomView(viewActionBar, params);

        btn_add_clock = (ImageButton) findViewById(R.id.btn_add_clock);
        btn_delete_clock = (ImageButton) findViewById(R.id.btn_delete_clock);
        btn_conform_delete = (Button) findViewById(R.id.btn_confirm_delete);

        btn_add_clock.setOnClickListener(new ButtonListener());
        btn_delete_clock.setOnClickListener(new ButtonListener());
        btn_conform_delete.setOnClickListener(new ButtonListener());
    }

    private class ButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            switch(v.getId())
            {
                case R.id.btn_add_clock:
                    if(current_total_items >= MAX_CLOCKS)
                    {
                        Toast.makeText(thisContext, "Cannot add more clocks!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), AlarmSettingActivity.class);
                        Bundle bundleObject = new Bundle();
                        bundleObject.putBoolean("b_add_clock", false);
                        bundleObject.putInt("current_total_items", current_total_items);
                        bundleObject.putInt("current_alarm_index", current_alarm_index);
                        bundleObject.putSerializable("alarm_array", alarms);
                        intent.putExtras(bundleObject);
                        startActivityForResult(intent, 1);
                    }
                    break;
                case R.id.btn_delete_clock:
                    if(current_total_items == 0)
                    {
                        Toast.makeText(thisContext, "No alarm built yet!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        btn_add_clock.setVisibility(Button.INVISIBLE);
                        btn_delete_clock.setVisibility(Button.INVISIBLE);
                        btn_conform_delete.setVisibility(Button.VISIBLE);
                        b_show_delete_checkbox = true;
                        simpleAdapter.notifyDataSetChanged();
                    }
                    break;
                case R.id.btn_confirm_delete:
                    btn_add_clock.setVisibility(Button.VISIBLE);
                    btn_delete_clock.setVisibility(Button.VISIBLE);
                    btn_conform_delete.setVisibility(Button.INVISIBLE);
                    b_show_delete_checkbox = false;
                    for(int i = current_total_items - 1; i >= 0; i--)
                    {
                        if(b_delete_list[i])
                        {
                            alarms.remove(i);
                            list_for_ListView.remove(i);
                            current_total_items--;
                        }
                    }
                    for(int i = 0; i < MAX_CLOCKS; i++)
                    {
                        b_delete_list[i] = false;
                    }
                    simpleAdapter.notifyDataSetChanged();
                    break;

            }
        }
    }
    private ArrayList<HashMap<String, Integer>> mData;

    // list of buttons and switches creater
    private class MySimpleAdapter extends BaseAdapter {

        LayoutInflater inflater = null;

        public MySimpleAdapter(Context context, ArrayList<HashMap<String, Integer>> data) {
            mData = data;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            current_alarm_index = position;

            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.simple_adapter, null);
<<<<<<< HEAD
          //      convertView.setTag("time" + position + "  ");
=======
     //           convertView.setTag("time" + position + "  ");
>>>>>>> 7715bb6ada20f42626697b71e63ba11ea0f871ce
            }
            final View switch_convertView = convertView;

            if(b_show_delete_checkbox)
                ((CheckBox) convertView.findViewById(R.id.list_checkbox)).setVisibility(Button.VISIBLE);
            else
                ((CheckBox) convertView.findViewById(R.id.list_checkbox)).setVisibility(Button.INVISIBLE);
            ((CheckBox) convertView.findViewById(R.id.list_checkbox)).setChecked(false);

            HashMap<String, Integer> time = mData.get(position);
            int hour = time.get("hour");
            int minute = time.get("minute");
            if(minute/10 == 0)
            {
                ((Button) convertView.findViewById(R.id.list_button)).setText(hour+":0"+minute);
            }
            else
            {
                ((Button) convertView.findViewById(R.id.list_button)).setText(hour + ":" + minute);
            }
            ((Button) convertView.findViewById(R.id.list_button)).setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    current_alarm_index = position;
                    Intent intent = new Intent(getApplicationContext(), AlarmSettingActivity.class);
                    Bundle bundleObject = new Bundle();
                    bundleObject.putBoolean("b_add_clock", false);
                    bundleObject.putInt("current_total_items", current_total_items);
                    bundleObject.putInt("current_alarm_index", current_alarm_index);
                    bundleObject.putSerializable("alarm_array", alarms);
                    intent.putExtras(bundleObject);
                    startActivityForResult(intent, 1);
                }
            });

            ((Switch) convertView.findViewById(R.id.list_switch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked)
                        switch_convertView.findViewById(R.id.list_button).setEnabled(true);
                    else
                        switch_convertView.findViewById(R.id.list_button).setEnabled(false);
                }
            });

            ((CheckBox) convertView.findViewById(R.id.list_checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        b_delete_list[position] = true;
                    else
                        b_delete_list[position] = false;
                }
            });

            return convertView;
        }
    }

    protected void buildAlarm(Alarm[] alarm, int count, Intent data ){
        Intent intent = new Intent(AlarmListActivity.this, AlarmReceiver.class);
        intent.putExtra("alarmBool", true);
        intent.putExtra("startHour", sHour);
        intent.putExtra("startMinute", sMinute);
        intent.putExtra("endHour", eHour);
        intent.putExtra("endMinute", eMinute);
        intent.putExtra("weekDay", weekDay);
        Calendar ca = Calendar.getInstance();

        ca.set(Calendar.HOUR_OF_DAY, sHour);
        ca.set(Calendar.MINUTE, sMinute);
        ca.set(Calendar.SECOND, 0);
        pendingIntent[count] = PendingIntent.getBroadcast(thisContext, count, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, ca.getTimeInMillis(), pendingIntent[count]);

    }
    // update the result for the changing data from Alarm_setting activity
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //判断结果码是否与回传的结果码相同
        if (resultCode == 1)
        {
            //获取回传数据
            Bundle bundleObject = intent.getExtras();
            sHour = intent.getIntExtra("startHour",12);
            sMinute = intent.getIntExtra("startMinute", 0);
            eHour = intent.getIntExtra("endHour", 12);
            eMinute = intent.getIntExtra("endMinute", 0);
            current_total_items = bundleObject.getInt("update_current_total_items");
            current_alarm_index = bundleObject.getInt("current_alarm_index");
            Alarm newAlarm = new Alarm();
            newAlarm.start_hour = sHour;
            newAlarm.start_min = sMinute;
            newAlarm.end_min = eMinute;
            newAlarm.end_hour = eHour;
            alarms.add(newAlarm);
            buildAlarm(alarm, current_alarm_index, intent);

            boolean b_add_clock = bundleObject.getBoolean("b_add_clock");

            if(b_add_clock)
            {
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                hashMap.put("sHour", sHour);
                hashMap.put("sMinute", sMinute);
                hashMap.put("endHour",eHour);
                hashMap.put("endMinute",eMinute);
                list_for_ListView.add(hashMap);
            }
            simpleAdapter.notifyDataSetChanged();
        }

    }

/*    // Create the buttons on action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/*
    // Rightmost screen button handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == R.id.action_add_clock) {
            if (current_total_items >= MAX_CLOCKS) {
                Toast.makeText(thisContext, "Cannot add more clock!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), AlarmSettingActivity.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putBoolean("b_add_clock", true);
                bundleObject.putInt("current_total_items", current_total_items);
                bundleObject.putInt("current_alarm_index", current_alarm_index);
                bundleObject.putSerializable("alarm_array", alarms);
                intent.putExtras(bundleObject);
                startActivityForResult(intent, 1);
            }
        }
        return super.onOptionsItemSelected(item);
    }
*/
    // phone button "back" button handler - let it goes to home screen when pressed
    @Override
    public void onBackPressed() {
        Intent backHomeScreen = new Intent(Intent.ACTION_MAIN);
        backHomeScreen.addCategory(Intent.CATEGORY_HOME);
        backHomeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backHomeScreen);
    }
}
