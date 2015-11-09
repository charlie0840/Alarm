package com.example.stanley.alarm;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class AlarmListActivity extends AppCompatActivity {

    private final int MAX_CLOCKS = 10;
    private int current_total_items = 2;
    private int current_alarm_index;
    private Alarm [] alarm = new Alarm[MAX_CLOCKS];
    private ArrayList<Alarm> alarms = new ArrayList<Alarm>(MAX_CLOCKS);
    private ListView listView;
    private Context thisContext;
    private MySimpleAdapter simpleAdapter;
    private ArrayList<HashMap<String,Integer>> list = new ArrayList<HashMap<String, Integer>>();
    private int testing_variable = 111;
    private int sHour = 12, sMinute = 0, eHour = 12, eMinute = 0;
    AlarmManager alarmManager = null;
    private PendingIntent[] pendingIntent = new PendingIntent[MAX_CLOCKS];
    private String preferenceName = "alarmList";
    private PopupWindow popWindow;
//    private Intent[] alarmSet = new Intent[MAX_CLOCKS];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) this.findViewById(R.id.listView);

//        Calendar cal = Calendar.getInstance();
  //      cal.set(Calendar.YEAR, 2015);
    //    cal.set(Calendar.MONTH, Calendar.NOVEMBER);
      //  cal.set(Calendar.DAY_OF_MONTH, 1);
    //    cal.set(Calendar.HOUR_OF_DAY, 22);
  //      cal.set(Calendar.MINUTE, 47);
//        cal.set(Calendar.SECOND, 0);

    //    Intent intent = new Intent(AlarmListActivity.this, AlarmReceiver.class);
        alarmManager = (AlarmManager) AlarmListActivity.this.getSystemService(Context.ALARM_SERVICE);
  //      PendingIntent pi = PendingIntent.getBroadcast(AlarmListActivity.this, 1, intent, 0);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
/*        current_total_items = 4;

        for (int i = 0; i < current_total_items; i++) {
            HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
            hashMap.put(12, 00);
            list.add(hashMap);
        }
*/
        for(int i=0; i < MAX_CLOCKS; i++) {
            alarms.add(new Alarm());
        }

        simpleAdapter = new MySimpleAdapter(this, list);
        listView.setAdapter(simpleAdapter);

        thisContext = this;

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

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.simple_adapter, null);
                convertView.setTag("time" + position + "  ");
            }
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
                public void onClick(View v) {
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
                    // do something, the isChecked will be
                    // true if the switch is in the On position
                    Toast.makeText(thisContext, "Switch " + current_total_items, Toast.LENGTH_SHORT).show();
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

        Calendar ca = Calendar.getInstance();
//        ca.set(Calendar.YEAR, 2015);
  //      ca.set(Calendar.MONTH, Calendar.NOVEMBER);
    //    ca.set(Calendar.DAY_OF_MONTH, 1);
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
        if (resultCode == 1) {
            //获取回传数据
            Bundle bundleObject = intent.getExtras();
            sHour = intent.getIntExtra("startHour",12);
            sMinute = intent.getIntExtra("startMinute", 0);
            eHour = intent.getIntExtra("endHour", 12);
            eMinute = intent.getIntExtra("endMinute", 0);
            current_total_items = bundleObject.getInt("update_current_total_items");
            current_alarm_index++;
            buildAlarm(alarm, current_alarm_index, intent);
            Toast.makeText(thisContext, "" + current_total_items, Toast.LENGTH_SHORT).show();
            boolean b_add_clock = bundleObject.getBoolean("b_add_clock");

            if(b_add_clock)
            {
                HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
                hashMap.put("hour", sHour);
                hashMap.put("minute", sMinute);
                list.add(hashMap);
            }
            simpleAdapter.notifyDataSetChanged();
        }
    }

    // Create the buttons on action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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

    // phone button "back" button handler - let it goes to home screen when pressed
    @Override
    public void onBackPressed() {
        Intent backHomeScreen = new Intent(Intent.ACTION_MAIN);
        backHomeScreen.addCategory(Intent.CATEGORY_HOME);
        backHomeScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(backHomeScreen);
    }
}
