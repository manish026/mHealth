package com.demo.demo;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {

    TimePicker timePicker;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setTitle(R.string.alarm_activity);
        timePicker = findViewById(R.id.timePicker);
        editText = findViewById(R.id.alarmET);

        findViewById(R.id.alarmButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().isEmpty()) {
                    setAlarm();
                }else {
                    Utilty.showToast(getApplicationContext(),"Please enter a medicine name");
                }
            }
        });

    }

    void setAlarm(){

        Date date = new Date();
        date.setHours(timePicker.getCurrentHour());
        date.setMinutes(timePicker.getCurrentMinute());
        date.setSeconds(0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("title",editText.getText().toString());


        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,
                intent, PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, date.getTime(), pendingIntent);
        Toast.makeText(this, "Alarm set", Toast.LENGTH_LONG).show();

    }


}
