package com.example.tijana.actorapplication.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.tijana.actorapplication.R;
import com.example.tijana.actorapplication.db.Actors;
import com.example.tijana.actorapplication.db.DatabaseHelper;
import com.example.tijana.actorapplication.db.Movies;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import static com.example.tijana.actorapplication.activities.SecondActivity.EXTRA_ACTORNO;

public class DialogMovieActivity extends Activity {
    private Actors actor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_movie);
    }
    public void onClickOK(View v) {
        // dohvati sve UI komponente
        EditText movieTitle = (EditText) findViewById(R.id.edit_movie_title);
        EditText movieGenre = (EditText) findViewById(R.id.edit_movie_genre);
        EditText movieYearOfRelease = (EditText) findViewById(R.id.edit_movie_date);

        DatabaseHelper helper = new DatabaseHelper(this);

        Dao<Movies, Integer> movieDao = null;
        try {
            movieDao = helper.getMovieDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int actorNo = (Integer)getIntent().getExtras().get(EXTRA_ACTORNO);

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

        Movies movieDB = new Movies();
        movieDB.setmTitle(movieTitle.getText().toString());
        movieDB.setmGenre(movieGenre.getText().toString());
        movieDB.setmYearOfRelease(movieYearOfRelease.getText().toString());
        movieDB.setmActor(actor);

        try {
            helper.getMovieDao().create(movieDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finish();
    }

    public void onClickCancel(View v) {
        finish();
    }

}
