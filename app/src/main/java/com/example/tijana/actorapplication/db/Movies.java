package com.example.tijana.actorapplication.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tijana on 10.3.18..
 */
@DatabaseTable(tableName = Movies.TABLE_NAME_MOVIES)
public class Movies {

    public static final String TABLE_NAME_MOVIES = "movies";

    public static final String FIELD_NAME_ID = "_id";
    public static final String FIELD_NAME_TITLE = "title";
    public static final String FIELD_NAME_GENRE = "genre";
    public static final String FIELD_NAME_YEAR_OF_RELEASE = "year of release";
    public static final String FIELD_NAME_ACTOR = "actor";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = FIELD_NAME_TITLE)
    private String mTitle;

    @DatabaseField(columnName = FIELD_NAME_GENRE)
    private String mGenre;

    @DatabaseField(columnName = FIELD_NAME_YEAR_OF_RELEASE)
    private String mYearOfRelease;

    @DatabaseField(columnName = FIELD_NAME_ACTOR, foreign = true, foreignAutoRefresh =
            true)
    private Actors mActor;

    //ORMLite zahteva prazan konstuktur u klasama koje opisuju tabele u bazi!
    public Movies() {

    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmGenre() {
        return mGenre;
    }

    public void setmGenre(String mGenre) {
        this.mGenre = mGenre;
    }

    public String getmYearOfRelease() {
        return mYearOfRelease;
    }

    public void setmYearOfRelease(String mYearOfRelease) {
        this.mYearOfRelease = mYearOfRelease;
    }

    public Actors getmActor() {
        return mActor;
    }

    public void setmActor(Actors mActor) {
        this.mActor = mActor;
    }

    @Override
    public String toString() {
        return mTitle;
    }
}