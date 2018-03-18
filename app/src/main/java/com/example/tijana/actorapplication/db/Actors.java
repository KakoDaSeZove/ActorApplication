package com.example.tijana.actorapplication.db;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tijana on 10.3.18..
 */
@DatabaseTable(tableName = Actors.TABLE_NAME_ACTOR)
public class Actors {

        public static final String TABLE_NAME_ACTOR = "actors";

        public static final String FIELD_NAME_ID     = "_id";
        public static final String FIELD_NAME_NAME   = "name";
        public static final String FIELD_NAME_SURNAME   = "surname";
        public static final String FIELD_NAME_BIOGRAPHY   = "biography";
        public static final String FIELD_NAME_RATING   = "rating";
        public static final String FIELD_NAME_DATE_OF_BIRTH  = "date_of_birth";
        public static final String FIELD_NAME_MOVIES = "movies";
        public static final String FIELD_NAME_IMAGE = "image";

        @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
        private int mId;

        @DatabaseField(columnName = FIELD_NAME_NAME)
        private String mName;

        @DatabaseField(columnName = FIELD_NAME_SURNAME)
        private String mSurname;

        @DatabaseField(columnName = FIELD_NAME_BIOGRAPHY)
        private String mBiography;

        @DatabaseField(columnName = FIELD_NAME_RATING)
        private float rating;

        @DatabaseField(columnName = FIELD_NAME_DATE_OF_BIRTH)
        private String mDateOfBirth;

        @DatabaseField(columnName = FIELD_NAME_IMAGE)
        private String mImage;

        @ForeignCollectionField(columnName = FIELD_NAME_MOVIES, eager = true)
        private ForeignCollection<Movies> mMovies;

        //ORMLite zahteva prazan konstuktur u klasama koje opisuju tabele u bazi!
        public Actors() {

        }

        public int getmId() {
                return mId;
        }

        public void setmId(int mId) {
                this.mId = mId;
        }

        public String getmName() {
                return mName;
        }

        public void setmName(String mName) {
                this.mName = mName;
        }

        public String getmSurname() {
                return mSurname;
        }

        public void setmSurname(String mSurname) {
                this.mSurname = mSurname;
        }

        public String getmBiography() {
                return mBiography;
        }

        public void setmBiography(String mBiography) {
                this.mBiography = mBiography;
        }

        public float getRating() {
                return rating;
        }

        public void setRating(float rating) {
                this.rating = rating;
        }

        public String getmDateOfBirth() {
                return mDateOfBirth;
        }

        public void setmDateOfBirth(String mDateOfBirth) {
                this.mDateOfBirth = mDateOfBirth;
        }

        public ForeignCollection<Movies> getmMovies() {
                return mMovies;
        }

        public String getmImage() {
                return mImage;
        }

        public void setmImage(String mImage) {
                this.mImage = mImage;
        }

        @Override
        public String toString() {
                return  mName;
        }
}
