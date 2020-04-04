package com.example.pharma_user;

import android.app.NotificationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class yesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yes);
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationActivity.NOTIFICATION_ID);
    }
}
