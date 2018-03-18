package com.example.tijana.actorapplication.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tijana.actorapplication.R;
import com.example.tijana.actorapplication.db.Actors;
import com.example.tijana.actorapplication.db.DatabaseHelper;
import com.example.tijana.actorapplication.db.Movies;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static android.R.layout.simple_list_item_1;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_ACTORNO = "actorNo";
    private Actors actor = null;
    private List<Movies> movies = null;
    private ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_second);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        int actorNo = (Integer) getIntent().getExtras().get(EXTRA_ACTORNO);

        DatabaseHelper helper = new DatabaseHelper(this);
        Dao<Actors, Integer> actorsDao = null;
        try {
            actorsDao = helper.getActorDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            actor = actorsDao.queryForId(actorNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ImageView actorImage = (ImageView) findViewById(R.id.actor_image);
        File imgFile = new File(actor.getmImage());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            actorImage.setImageBitmap(myBitmap);
        }

        TextView actorName = (TextView) findViewById(R.id.actor_name);
        actorName.setText(actor.getmName());

        TextView actorSurname = (TextView) findViewById(R.id.actor_surname);
        actorSurname.setText(actor.getmSurname());

        TextView actorBiography = (TextView) findViewById(R.id.actor_biography);
        actorBiography.setText(actor.getmBiography());

        RatingBar actorRating = (RatingBar) findViewById(R.id.actor_rating);
        actorRating.setRating(actor.getRating());

        TextView actorDate = (TextView) findViewById(R.id.actor_date_of_birth);
        actorDate.setText(actor.getmDateOfBirth());

        listView = (ListView) findViewById(R.id.list_movie);

        ArrayList<String> movieList = new ArrayList<>();
        for (Movies m : actor.getmMovies()) {
            movieList.add(m.getmTitle());
        }

        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(
                this,
                simple_list_item_1,
                movieList) {
        };
        listView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //! Add
        getMenuInflater().inflate(R.menu.action_bar_second, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        boolean allowToast = sharedPreferences.getBoolean(getString(R.string.preferences_toast_key), false);
        boolean allowNotification = sharedPreferences.getBoolean(getString(R.string.preferences_notification_key), false);

        switch (item.getItemId()) {
            case R.id.action_add_movie:

                if (allowToast == true) {
                    Toast.makeText(getApplicationContext(), "Data about movie will be added", Toast.LENGTH_LONG).show();
                }
                if (allowNotification == true) {

                    String channelId = "actor_channel_id";

                    android.support.v4.app.NotificationCompat.Builder mBuilder =
                            new android.support.v4.app.NotificationCompat.Builder(this, channelId);
                    mBuilder.setSmallIcon(R.drawable.ic_notification_add_movie);
                    mBuilder.setContentTitle(getString(R.string.notification_add_movie_title));
                    mBuilder.setContentText(getString(R.string.notification_add_movie_text));

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //! For Android Oreo add  this Notification Channel
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    mNotificationManager.createNotificationChannel(channel);

                    mNotificationManager.notify(001, mBuilder.build());
                }
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//                    builder.setContentTitle("Add Movie");
//                    builder.setSmallIcon(R.drawable.ic_notification_add_movie);
//                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
//                            R.drawable.ic_notification_add_movie));
//                    builder.setContentText("New movie has been added in the list");
//
//                    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    manager.notify(3, builder.build());
//                }
                Intent intent = new Intent(SecondActivity.this,
                        DialogMovieActivity.class);
                intent.putExtra(SecondActivity.EXTRA_ACTORNO, actor.getmId());
                startActivity(intent);
                return true;

            case R.id.action_edit:

                if (allowToast == true) {
                    Toast.makeText(getApplicationContext(), "Data about actor will be edited", Toast.LENGTH_LONG).show();
                }
                if (allowNotification == true) {

                    String channelId = "actor_channel_id";

                    android.support.v4.app.NotificationCompat.Builder mBuilder =
                            new android.support.v4.app.NotificationCompat.Builder(this, channelId);
                    mBuilder.setSmallIcon(R.drawable.ic_notification_edit);
                    mBuilder.setContentTitle(getString(R.string.notification_edit_title));
                    mBuilder.setContentText(getString(R.string.notification_edit_text));

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //! For Android Oreo add  this Notification Channel
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    mNotificationManager.createNotificationChannel(channel);

                    mNotificationManager.notify(003, mBuilder.build());
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//                    builder.setContentTitle("Edit Actor");
//                    builder.setSmallIcon(R.drawable.ic_notification_edit);
//                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
//                            R.drawable.ic_notification_edit));
//                    builder.setContentText("The data about the Actor are about to be editted");
//
//                    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    manager.notify(4, builder.build());

                }

                intent = new Intent(SecondActivity.this,
                        DialogActivity.class);
                intent.putExtra(SecondActivity.EXTRA_ACTORNO, actor.getmId());
                startActivity(intent);

                return true;
            case R.id.action_delete:

                if (allowToast == true) {
                    Toast.makeText(getApplicationContext(), "Data about actor will be deleted", Toast.LENGTH_LONG).show();
                }
                if (allowNotification == true) {

                    String channelId = "actor_channel_id";

                    android.support.v4.app.NotificationCompat.Builder mBuilder =
                            new android.support.v4.app.NotificationCompat.Builder(this, channelId);
                    mBuilder.setSmallIcon(R.drawable.ic_notification_delete);
                    mBuilder.setContentTitle(getString(R.string.notification_delete_title));
                    mBuilder.setContentText(getString(R.string.notification_delete_text));

                    NotificationManager mNotificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    //! For Android Oreo add  this Notification Channel
                    NotificationChannel channel = new NotificationChannel(channelId,
                            "Channel human readable title",
                            NotificationManager.IMPORTANCE_DEFAULT);

                    mNotificationManager.createNotificationChannel(channel);

                    mNotificationManager.notify(001, mBuilder.build());
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
//                    builder.setContentTitle("Delete Actor");
//                    builder.setSmallIcon(R.drawable.ic_notification_delete);
//                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),
//                            R.drawable.ic_notification_delete));
//                    builder.setContentText("The Actor has been deeted from the list");
//
//                    NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//                    manager.notify(5, builder.build());

                }

                int actorNo = (Integer) getIntent().getExtras().get(EXTRA_ACTORNO);
                DatabaseHelper helper = new DatabaseHelper(this);

                Dao<Actors, Integer> actorsDao = null;
                try {
                    actorsDao = helper.getActorDao();
                    actorsDao.deleteById(actorNo);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
