package com.example.pharma_user;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationActivity extends AppCompatActivity {

    private final String CHANENEL_ID = "personal_notifications";
    public static final int NOTIFICATION_ID = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
    }

    public void displayNotification(View view) {
        Intent landingIntent = new Intent(this, LandingActivity.class);
        landingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent landingPendingIntent = PendingIntent.getActivity(this, 0, landingIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent yesIntent = new Intent(this, yesActivity.class);
        yesIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent YesPendingIntent = PendingIntent.getActivity(this, 0, yesIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent noIntent = new Intent(this, NoActivity.class);
        noIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent NoPendingIntent = PendingIntent.getActivity(this, 0, noIntent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANENEL_ID);
        builder.setSmallIcon(R.drawable.ic_sms_notification);
        builder.setContentTitle("Simple Notification");
        builder.setContentText("this is a fuckin simple notification");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setContentIntent(landingPendingIntent);
        builder.addAction(R.drawable.ic_sms_notification, "Yes", YesPendingIntent);
        builder.addAction(R.drawable.ic_sms_notification, "No", NoPendingIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

    }
}
