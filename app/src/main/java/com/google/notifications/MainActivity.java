package com.google.notifications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.google.notifications.App.CHANNEL_1_ID;
import static com.google.notifications.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {
    NotificationManagerCompat notificationManager;
    private EditText editTextTitle;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextMessage = findViewById(R.id.editTextMessage);
    }

    public void sendOnChannel(View view) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(message)) {
            Intent activityIntent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

            Intent broadcastIntent = new Intent(this, NotificationReciever.class);
            broadcastIntent.putExtra("toastMessage", message);
            PendingIntent actionIntent = PendingIntent
                    .getBroadcast(this, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_one);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.ic_one)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setLargeIcon(largeIcon)
                    /*.setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(getString(R.string.long_dummy_text))
                            .setBigContentTitle("Big Content Title")
                            .setSummaryText("This is summary text"))*/
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true)
                    .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
                    .setColor(Color.BLUE)
                    .build();
            notificationManager.notify(1, notification);
        } else {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendOnChanne2(View view) {
        String title = editTextTitle.getText().toString();
        String message = editTextMessage.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("This is line 1")
                        .addLine("this is line 2")
                        .addLine("this is line 3")
                        .addLine("this is line 4")
                        .addLine("this is line 5")
                        .setBigContentTitle("Big content title")
                        .setSummaryText("This is summary text"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(2, notification);
    }
}