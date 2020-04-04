package com.example.pharma_user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import static com.example.pharma_user.NotificationActivity.NOTIFICATION_ID;
import static com.example.pharma_user.NotificationActivity.NOTIFICATION_ID;

public class NoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.cancel(NOTIFICATION_ID);
    }
}