package com.example.tijana.actorapplication.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.tijana.actorapplication.R;
import com.example.tijana.actorapplication.db.Actors;
import com.example.tijana.actorapplication.db.DatabaseHelper;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;

import static com.example.tijana.actorapplication.activities.SecondActivity.EXTRA_ACTORNO;


public class DialogActivity extends Activity {

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    private static final int SELECT_PICTURE = 1;
    private Actors actor = null;
    private ImageView preview;
    private String imagePath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        //! Clean saved preferences
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.remove("dialogName");
        editor.remove("dialogSurname");
        editor.remove("dialogBio");
        editor.remove("dialogRating");
        editor.remove("dialogDate");
        editor.commit();
    }

    public void onClickOK(View v) {
        // dohvati sve UI komponente
        EditText dialogName = (EditText) findViewById(R.id.edit_name);
        EditText dialogSurname = (EditText) findViewById(R.id.edit_surname);
        EditText dialogBio = (EditText) findViewById(R.id.edit_bio);
        RatingBar dialogRating = (RatingBar) findViewById(R.id.edit_rating);
        EditText dialogDate = (EditText) findViewById(R.id.edit_date);

        DatabaseHelper helper = new DatabaseHelper(this);

        Dao<Actors, Integer> actorDao = null;
        try {
            actorDao = helper.getActorDao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (getIntent().getExtras() == null) {

            Actors actorDB = new Actors();
            actorDB.setmName(dialogName.getText().toString());
            actorDB.setmSurname(dialogSurname.getText().toString());
            actorDB.setmBiography(dialogBio.getText().toString());
            actorDB.setRating(dialogRating.getRating());
            actorDB.setmDateOfBirth(dialogDate.getText().toString());
            actorDB.setmImage(imagePath);

            try {
                actorDao.create(actorDB);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {

            Integer actorNo = (Integer) getIntent().getExtras().get(EXTRA_ACTORNO);

            Actors actorDB = new Actors();
            actorDB.setmId(actorNo);
            actorDB.setmName(dialogName.getText().toString());
            actorDB.setmSurname(dialogSurname.getText().toString());
            actorDB.setmBiography(dialogBio.getText().toString());
            actorDB.setRating(dialogRating.getRating());
            actorDB.setmDateOfBirth(dialogDate.getText().toString());
            actorDB.setmImage(imagePath);

            try {
                actorDao.update(actorDB);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    public void onClickCancel(View v) {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }

        preview = (ImageView) findViewById(R.id.dialog_image);


        EditText dialogName = (EditText) findViewById(R.id.edit_name);
        EditText dialogSurname = (EditText) findViewById(R.id.edit_surname);
        EditText dialogBio = (EditText) findViewById(R.id.edit_bio);
        RatingBar dialogRating = (RatingBar) findViewById(R.id.edit_rating);
        EditText dialogDate = (EditText) findViewById(R.id.edit_date);

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String actorNameStr = sharedPref.getString("dialogName", null);
        if (actorNameStr != null) {
            dialogName.setText(actorNameStr);
        }

        String actorSurnameStr = sharedPref.getString("dialogSurname", null);
        if (actorSurnameStr != null) {
            dialogSurname.setText(actorSurnameStr);
        }

        String actorBioStr = sharedPref.getString("dialogBio", null);
        if (actorBioStr != null) {
            dialogBio.setText(actorBioStr);
        }

        Float actorRatingFl = sharedPref.getFloat("dialogRating", 0);
        if (actorRatingFl != 0) {
            dialogRating.setRating(actorRatingFl);
        }

        String actorDateStr = sharedPref.getString("dialogDate", null);
        if (actorDateStr != null) {
            dialogDate.setText(actorDateStr);
        }


        if (getIntent().getExtras() != null) {
            Integer actorNo = (Integer) getIntent().getExtras().get(EXTRA_ACTORNO);

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

            //! Use value from database instead from previous dialog
            if(actorNameStr == null){
                dialogName.setText(actor.getmName());
            }

            if(actorSurnameStr == null){
                dialogSurname.setText(actor.getmSurname());
            }

            if(actorBioStr == null){
                dialogBio.setText(actor.getmBiography());
            }

            if(actorRatingFl == 0){
                dialogRating.setRating(actor.getRating());
            }

            if(actorDateStr == null){
                dialogDate.setText(actor.getmDateOfBirth());
            }


            //! Must, must, must!!!
            if (imagePath == null) {

                imagePath = actor.getmImage();
            }

            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    preview.setImageBitmap(myBitmap);
                }
            }
        } else {

            if (imagePath != null) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    preview.setImageBitmap(myBitmap);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText dialogName = (EditText) findViewById(R.id.edit_name);
        EditText dialogSurname = (EditText) findViewById(R.id.edit_surname);
        EditText dialogBio = (EditText) findViewById(R.id.edit_bio);
        RatingBar dialogRating = (RatingBar) findViewById(R.id.edit_rating);
        EditText dialogDate = (EditText) findViewById(R.id.edit_date);

        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();

        editor.putString("dialogName", dialogName.getText().toString());
        editor.putString("dialogSurname", dialogSurname.getText().toString());
        editor.putString("dialogBio", dialogBio.getText().toString());
        editor.putFloat("dialogRating", dialogRating.getRating());
        editor.putString("dialogDate", dialogDate.getText().toString());
        editor.commit();
    }

    public void onSelectImage(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    /**
     * Sismtemska metoda koja se automatksi poziva ako se
     * aktivnost startuje u startActivityForResult rezimu
     * <p>
     * Ako je ti slucaj i ako je sve proslo ok, mozemo da izvucemo
     * sadrzaj i to da prikazemo. Rezultat NIJE sliak nego URI do te slike.
     * Na osnovu toga mozemo dobiti tacnu putnaju do slike ali i samu sliku
     */

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                imagePath = getRealPathFromURI(getApplicationContext(), selectedImageUri);

                if (preview != null) {
                    File imgFile = new File(imagePath);

                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        preview.setImageBitmap(myBitmap);
                    }
                }
            }
        }
    }

    public static String getRealPathFromURI(Context context, Uri uri) {

        String filePath = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && uri.getHost().contains("com.android.providers.media")) {
            // Image pick from recent

            String wholeID = DocumentsContract.getDocumentId(uri);

            // Split at colon, use second item in the array
            String id = wholeID.split(":")[1];

            String[] column = {MediaStore.Images.Media.DATA};

            // where id is equal to
            String sel = MediaStore.Images.Media._ID + "=?";

            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    column, sel, new String[]{id}, null);

            int columnIndex = cursor.getColumnIndex(column[0]);

            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
            return filePath;
        } else {
            // image pick from gallery
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    return cursor.getString(columnIndex);
                }
                cursor.close();
            }
            return null;
        }

    }


}
