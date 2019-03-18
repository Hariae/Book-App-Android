package com.example.aehar.bookapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends AppCompatActivity {
    private DatabaseHelper mDB;
    private Button mNotifyButton;

    private NotificationManager mNotifyManager;

    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        /*TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(message);
        */mDB = new DatabaseHelper(this);
        List<Book> books = mDB.query(message);

        List<String> bookNames = new ArrayList<String>();

        for(int i=0;i<books.size();i++){
            bookNames.add(books.get(i).getBook());
        }

        ListView listView = (ListView) findViewById(R.id.listView);
        TextView textView = (TextView) findViewById(R.id.textView);

        ArrayAdapter adapter = new  ArrayAdapter<String>(this, R.layout.activity_listview, bookNames);
        listView.setAdapter(adapter);

        mNotifyButton = (Button) findViewById(R.id.notifyBtn);
        mNotifyButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                sendNotification();
            }
        });


    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this).setContentTitle("Book App Notification!")
                .setContentText("Book App Notification!.")
                .setSmallIcon(R.drawable.notify);

        Notification myNotification = notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID, myNotification);

    }

    public void launchThirdActivity(View view){
        final Intent intent = new Intent(this, ThirdActivity.class);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are you sure?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        startActivity(intent);
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
        /*Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);*/
    }
}
