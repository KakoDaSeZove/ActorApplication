package com.example.tijana.actorapplication.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.tijana.actorapplication.R;
import com.example.tijana.actorapplication.db.Actors;
import com.example.tijana.actorapplication.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static android.R.layout.simple_list_item_1;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


public class MainActivity extends AppCompatActivity {

    private ListView listView = null;
    private List<Actors> actors = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);


        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> listView,
                                            View v,
                                            int position,
                                            long id)

                    {
                        Intent intent = new Intent(MainActivity.this,
                                SecondActivity.class);
                        intent.putExtra(SecondActivity.EXTRA_ACTORNO, actors.get(position).getmId());
                        startActivity(intent);
                    }


                };
        listView = (ListView) findViewById(R.id.list_of_actors);
        listView.setOnItemClickListener(itemClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        DatabaseHelper helper = new DatabaseHelper(this);
        Dao<Actors, Integer> actorsDao = null;
        try {
            actorsDao = helper.getActorDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            actors = actorsDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<String> actorList = new ArrayList<>();
        for (Actors a : actors) {
            actorList.add(a.getmName() + " " + a.getmSurname());
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                this,
                simple_list_item_1,
                actorList) {
        };
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //! Add
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean allowToast = sharedPreferences.getBoolean(getString(R.string.preferences_toast_key), false);
        boolean allowNotification = sharedPreferences.getBoolean(getString(R.string.preferences_notification_key), false);


        switch (item.getItemId()) {
            case R.id.action_add:
                if (allowToast == true) {
                    Toast.makeText(getApplicationContext(), "Data about actor will be added", Toast.LENGTH_LONG).show();
                }
                if (allowNotification == true) {
                    String channelId = "actor_channel_id";

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(this, channelId);
                    mBuilder.setSmallIcon(R.drawable.ic_notification_add);
                    mBuilder.setContentTitle(getString(R.string.notification_add_title));
                    mBuilder.setContentText(getString(R.string.notification_add_text));

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //! For Android Oreo add  this Notification Channel
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    mNotificationManager.createNotificationChannel(channel);

                    mNotificationManager.notify(001, mBuilder.build());



                   /* android.support.v7.app.NotificationCompat.Builder builder = new android.support.v7.app.NotificationCompat.Builder(getApplicationContext());
                    builder.setContentTitle("Add Actor");
                    builder.setSmallIcon(R.drawable.ic_notification_add);
                    builder
                    builder.setContentText("New Actor has been added in the list");
                    builder.setPriority(1);

                    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(2, builder.build());*/
                }

                Intent intent = new Intent(MainActivity.this,
                        DialogActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:

                intent = new Intent(MainActivity.this,
                        PreferenceSettingsActivity.class);
                startActivity(intent);

                return true;

            case R.id.action_about:
                AlertDialog.Builder basicAlertDialogBuilder = new AlertDialog.Builder(this);
                basicAlertDialogBuilder.setMessage(R.string.alert_dialog);
                basicAlertDialogBuilder.setPositiveButton(R.string.alert_button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.main_activity), "sjajan autor", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
                basicAlertDialogBuilder.create().show();
                return true;

            case R.id.action_image:
                showRandomImage();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showRandomImage() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.band_layout);

        ImageView image = (ImageView) dialog.findViewById(R.id.band_image);

        Picasso.with(this).load("https://source.unsplash.com/random").into(image);


        Button close = (Button) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
