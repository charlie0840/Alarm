package com.example.stanley.alarm;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

/**
 * Created by yshui on 11/1/15.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    private Intent it = new Intent();
    private boolean bool = true;
    private MediaPlayer mplayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        //    SharedPreferences sharedPreferences = context.getSharedPreferences("alarmList", Activity.MODE_PRIVATE);
        Intent pop = new Intent(context, PopAlarm.class);
        context.startActivity(pop);

    //    bool = intent.getBooleanExtra("alarmBool", true);
    //    if (bool == true) {
    //        Toast.makeText(context, "time's up", Toast.LENGTH_LONG).show();

    //        Environment.getExternalStorageDirectory();
  //          mplayer = new MediaPlayer();
//            mplayer.setDataSource(context.getString(R.string.music));
 //           mplayer = MediaPlayer.create(this, R.)
//            LayoutInflater mInflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  //          LinearLayout mLayout = (LinearLayout) mInflate.inflate(R.layout.popup_window, null);

//            popWindow = new PopupWindow(mLayout, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            Button conform = (Button)mLayout.findViewById(R.id.conform_pop);
  //          Button cancel = (Button)mLayout.findViewById(R.id.cancel_pop);
    //        cancel.setOnClickListener(new ButtonListener());
      //      conform.setOnClickListener(new ButtonListener());

        }



    }
//    private class ButtonListener implements View.OnClickListener{
  //      public void onClick(View v){
    //        switch (v.getId()){
      //          case R.id.conform_pop:
        //           popWindow.dismiss();
         //           break;
           //     case R.id.cancel_pop:
             //       popWindow.dismiss();
               //     break;
            //}
//
  //      }

