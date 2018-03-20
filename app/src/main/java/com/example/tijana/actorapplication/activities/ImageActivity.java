package com.example.tijana.actorapplication.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.tijana.actorapplication.R;
import com.example.tijana.actorapplication.db.Actors;
import com.example.tijana.actorapplication.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;

public class ImageActivity extends AppCompatActivity {

    public static final String EXTRA_ACTORIMAGE = "actorImage";

    private Actors actor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        int actorNo = (Integer) getIntent().getExtras().get(EXTRA_ACTORIMAGE);

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
        ImageView im = (ImageView)findViewById(R.id.large_image);
        if (actor.getmImage() != null) {
            File imgFile = new File(actor.getmImage());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                im.setImageBitmap(myBitmap);
            }
        }
    }
}
