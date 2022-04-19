package com.example.finalproject2;

import static java.sql.DriverManager.println;

import androidx.appcompat.app.AppCompatActivity;

import android.media.ToneGenerator;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmClock extends AppCompatActivity {
    private Timer timer1 = new Timer();
    private Timer timer2 = new Timer();
    int counter;

    TextView tulost;

    TextView tuhour;

    int hour1;
    int hour2;
    int minute1;
    int minute2;
    int alm_hour;
    int alm_minute;
    int mode;
    int setmode;
    int setalarmmode;
    int setsnoozemode;

    ImageButton mode_btn;

    ImageButton hourup_but;

    ImageButton hourdown_but;

    ImageButton minute_up_button;

    ImageButton minute_down_button;

    ImageButton edit_alarm;

    ImageButton choose_alarm;
    Calendar rightNow = Calendar.getInstance();
    int currentHourIn24Format;
    int currentMinute;
    int currentsecond;

    TextView stat;

    ToneGenerator tf = new ToneGenerator(0,ToneGenerator.MAX_VOLUME);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_clock);
        tulost = findViewById(R.id.textViewMinute);
        tuhour = findViewById(R.id.textViewhour);
        stat = findViewById(R.id.textViewshowMode);

        mode_btn =findViewById(R.id.buttonMode) ;
        hourup_but = findViewById(R.id.fastUp);
        hourdown_but = findViewById(R.id.fastDown);
        minute_up_button = findViewById(R.id.Up);
        minute_down_button = findViewById(R.id.Down);
        edit_alarm = findViewById(R.id.buttonchooseAlarm);
        choose_alarm = findViewById(R.id.setAlarmMode);
        if (savedInstanceState !=null){
            counter= savedInstanceState.getInt("COUNTER");
            hour1= savedInstanceState.getInt("HOUR1");
            hour2= savedInstanceState.getInt("HOUR2");
            minute1 =savedInstanceState.getInt("MINUTE1");
            minute2 =savedInstanceState.getInt("MINUTE2");
            alm_hour = savedInstanceState.getInt("ALMHOUR");
            alm_minute = savedInstanceState.getInt("ALMMINUTE");
            mode = savedInstanceState.getInt("MODE");
            setmode = savedInstanceState.getInt("SETMODE");
            setalarmmode = savedInstanceState.getInt("SETALARMMODE");
            setsnoozemode =savedInstanceState.getInt("SETSNOOZEMODE");
//            currentHourIn24Format =savedInstanceState.getInt("CURRENTHOUR");
//            currentMinute = savedInstanceState.getInt("CURRENTMINUTE");
//            currentsecond =savedInstanceState.getInt("CURRENTSECOND");
        }

        //stat.setText("TIME MODE");
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                if (hour1 == currentHourIn24Format && minute1 == currentMinute) {
                    tf.startTone(ToneGenerator.TONE_PROP_BEEP, 2000);
                    stat.setText("ALARM 2 RUN");
                }
                if (hour2 == currentHourIn24Format && minute2 == currentMinute) {
                    tf.startTone(ToneGenerator.TONE_PROP_BEEP, 2000);
                    stat.setText("ALARM 1 RUN");
                }
                onTimerCheck();
            }
        },100,50);
        if(mode==0){
            mode =1;
            this.stat.setText("ALARM MODE");
            this.tulost.setText("00");
            this.tuhour.setText("00");


        }else if(mode==1){
            mode =0;
            this.stat.setText("TIME MODE");

        }
    }
    protected void onSaveInstanceState( Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("COUNTER",counter);
        savedInstanceState.putInt("HOUR1",hour1);
        savedInstanceState.putInt("HOUR2",hour2);
        savedInstanceState.putInt("MINUTE1",minute1);
        savedInstanceState.putInt("MINUTE2",minute2);
        savedInstanceState.putInt("ALMHOUR",alm_hour);
        savedInstanceState.putInt("ALMMINUTE",alm_minute);
        savedInstanceState.putInt("MODE",mode);
        savedInstanceState.putInt("SETMODE",setmode);
        savedInstanceState.putInt("SETALARMMODE",setalarmmode);
        savedInstanceState.putInt("SETSNOOZEMODE",setsnoozemode);
//        savedInstanceState.putInt("CURRENTHOUR",currentHourIn24Format);
//        savedInstanceState.putInt("CURRENTMINUTE",currentMinute);
//        savedInstanceState.putInt("CURRENTSECOND",currentsecond);
    }
//    protected void onRestoreInstanceState(Bundle savedInstanceState){
//        super.onRestoreInstanceState(savedInstanceState);
//
//        counter= savedInstanceState.getInt("COUNTER");
//        hour1= savedInstanceState.getInt("HOUR1");
//        hour2= savedInstanceState.getInt("HOUR2");
//        minute1 =savedInstanceState.getInt("MINUTE1");
//        minute2 =savedInstanceState.getInt("MINUTE2");
//        alm_hour = savedInstanceState.getInt("ALMHOUR");
//        alm_minute = savedInstanceState.getInt("ALMMINUTE");
//        mode = savedInstanceState.getInt("MODE");
//        setmode = savedInstanceState.getInt("SETMODE");
//        setalarmmode = savedInstanceState.getInt("SETALARMMODE");
//        setsnoozemode =savedInstanceState.getInt("SETSNOOZEMODE");
//        currentHourIn24Format =savedInstanceState.getInt("CURRENTHOUR");
//        currentMinute = savedInstanceState.getInt("CURRENTMINUTE");
//        currentsecond =savedInstanceState.getInt("CURRENTSECOND");
//    }
    private void onTimerCheck(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                rightNow = Calendar.getInstance();
                currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                currentMinute= rightNow.get(Calendar.MINUTE);
                currentsecond= rightNow.get(Calendar.SECOND);
            }
        });

    }

    public void changeMode(View view) {
        if(mode==0){
            mode =1;
            this.stat.setText("ALARM MODE");
            this.tulost.setText("00");
            this.tuhour.setText("00");
            timer2.cancel();
            timer1 = new Timer();

        }else{
            mode =0;
            this.stat.setText("TIME MODE");
            timer1.cancel();
            timer2 = new Timer();
            timer2.schedule(new TimerTask() {
                @Override
                public void run() {
                    println(" ++++Timer TICK ++++");
                    onTimerTick();
                }
            },100,50);

        }
    }
    private void onTimerTick(){
        println("+++++ on TimerTick ++++");
        counter++;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String m1 = String.format("%02d", counter);
                tulost.setText(m1);
                tuhour.setText("00");
            }
        });

    }

    public void chooseAlarm(View view) {
        if (mode==1){
            if(setalarmmode==0){
                stat.setText("ALARM MODE");
                setalarmmode =1;
            }
            if(setalarmmode==1){
                setalarmmode =2;
                stat.setText("ALARM 1");
                String m1 = String.format("%02d", hour2);
                tuhour.setText(m1);
                String m2 = String.format("%02d", minute2);
                tulost.setText(m2);

            }else{
                setalarmmode =0;
                stat.setText("ALARM 2");
                String m1 = String.format("%02d", hour1);
                tuhour.setText(m1);
                String m2 = String.format("%02d", minute1);
                tulost.setText(m2);
            }
        }
    }

    public void setAlarm(View view) {
        if (mode==1){
            if(setmode==0){
                this.stat.setText("SET ALARM MODE");
                setmode =1;
            }
            if(setmode==1){
                setmode =2;
                this.stat.setText("SET ALARM 1");
                minute1 = Integer.parseInt(this.tulost.getText().toString());
                hour1 = Integer.parseInt(this.tuhour.getText().toString());
                alm_minute =0;
                alm_hour =0;
                String m1 = String.format("%02d", alm_hour);
                this.tuhour.setText(m1);
                String m2 = String.format("%02d", alm_minute);
                this.tulost.setText(m2);

            }

            else{
                setmode =0;
                this.stat.setText("SET ALARM 2");
                minute2 = Integer.parseInt(this.tulost.getText().toString());
                hour2 = Integer.parseInt(this.tuhour.getText().toString());
                alm_minute =0;
                alm_hour =0;
                String m1 = String.format("%02d", alm_hour);
                this.tuhour.setText(m1);
                String m2 = String.format("%02d", alm_minute);
                this.tulost.setText(m2);
            }
        }
    }

    public void minutedown(View view) {
        if(mode==1){
            alm_minute = alm_minute-1;
            if(alm_minute<0){
                alm_minute =59;
            }
            String m1 = String.format("%02d", alm_minute);
            tulost.setText(m1);
        }else{

        }
    }
    public void minuteup(View view) {
        if(mode==1){
            alm_minute = alm_minute+1;
            if(alm_minute==59){
                alm_minute =0;
            }
            String m1 = String.format("%02d", alm_minute);
            tulost.setText(m1);
        }else{

        }
    }
    public void hourdown(View view) {
        if(mode==1){
            alm_hour = alm_hour -1;
            if(alm_hour<0){
                alm_hour =23;
            }
            String m1 = String.format("%02d", alm_hour);
            tuhour.setText(m1);
        }else{

        }
    }



    public void hourup(View view) {
        if(mode==1){
            alm_hour = alm_hour +1;
            if(alm_hour>=24){
                alm_hour =0;
            }
            String m1 = String.format("%02d", alm_hour);
            tuhour.setText(m1);
        }else{

        }
    }

    public void snoozingalarm(View view) {
        if (mode==1){

            if(setsnoozemode==1){
                setsnoozemode =2;
                this.stat.setText("SNOOZE ALM 1");
                minute1 = minute1 +2;
                //hour1 = Integer.parseInt(this.tuhour!!.text as String)
                //alm_minute =0
                //alm_hour =0
                String m1 = "+";
                this.tuhour.setText(m1);
                String m2 = String.format("%02d", 2);
                this.tulost.setText(m2);

            }

            else{
                setsnoozemode  =1;
                this.stat.setText("SNOOZE ALM 2");
                minute2 = minute2 +2;
//                hour2 = Integer.parseInt(this.tuhour!!.text as String)
//                alm_minute =0
//                alm_hour =0
                String m1 = "+";
                this.tuhour.setText(m1);
                String m2 = String.format("%02d", 2);
                this.tulost.setText(m2);
            }
        }
    }
}