package com.example.gabriel_sanchez_s5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gabriel_sanchez_s5.model.PersonalModel;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DB_NAME = "GSANCHEZ_S5";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Personal";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String CHARGE_TYPE = "charge_type";
    private static final String IMAGE_DATA = "image_data";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + CHARGE_TYPE + " TEXT,"
                + IMAGE_DATA + " BLOB)";
        db.execSQL(query);
    }

    public void addNewPersonal(String name, String charge_type, byte[] image_data) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME_COL, name);
        values.put(CHARGE_TYPE, charge_type);
        values.put(IMAGE_DATA, image_data);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<PersonalModel> getAllPersonal() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<PersonalModel> courseModalArrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                courseModalArrayList.add(new PersonalModel(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getBlob(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return courseModalArrayList;
    }

    public void clearDataBase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
