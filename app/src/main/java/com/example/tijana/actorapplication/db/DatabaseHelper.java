package com.example.tijana.actorapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;


/**
 * Created by milossimic on 11/4/16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static String DATABASE_NAME = "my.db";
    public static int DATABASE_VERSION = 5;

    private Dao<Actors, Integer> mActorDao = null;
    private Dao<Movies, Integer> mMovieDao = null;


    //Potrebno je dodati konstruktor zbog pravilne inicijalizacije biblioteke
    public DatabaseHelper(Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    //Prilikom kreiranja baze potrebno je da pozovemo odgovarajuce metode biblioteke
    //prilikom kreiranja moramo pozvati TableUtils.createTable za svaku tabelu koju imamo
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Actors.class);
            TableUtils.createTable(connectionSource, Movies.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //kada zelimo da izmenomo tabele, moramo pozvati TableUtils.dropTable za sve tabele koje imamo
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Actors.class, true);
            TableUtils.dropTable(connectionSource, Movies.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //jedan Dao objekat sa kojim komuniciramo. Ukoliko imamo vise tabela
    //potrebno je napraviti Dao objekat za svaku tabelu
    public Dao<Actors, Integer> getActorDao() throws SQLException {
        if (mActorDao == null) {
            mActorDao = getDao(Actors.class);
        }

        return mActorDao;
    }

    public Dao<Movies, Integer> getMovieDao() throws SQLException {
        if (mMovieDao == null) {
            mMovieDao = getDao(Movies.class);
        }

        return mMovieDao;
    }

    //obavezno prilikom zatvarnaj rada sa bazom osloboditi resurse
    @Override
    public void close() {
        mActorDao = null;
        mMovieDao = null;

        super.close();
    }
}
