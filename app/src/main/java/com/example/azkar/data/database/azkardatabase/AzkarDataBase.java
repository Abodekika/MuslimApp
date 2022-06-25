package com.example.azkar.data.database.azkardatabase;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.azkar.data.Pojo.azkar.Azker;
import com.example.azkar.data.Pojo.azkar.Content;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AzkarDataBase extends SQLiteOpenHelper {

    String TITLE_COL = "title";

    public AzkarDataBase(Context context) {
        super(context, "Azkar", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE azkar(id TEXT, title TEXT, count TEXT,bookmark TEXT,content BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertEmpty(List<Azker> azker_list) {
        SQLiteDatabase db = this.getReadableDatabase();
        Gson gson = new Gson();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < azker_list.size(); i++) {
            contentValues.put("id", i);
            contentValues.put("title", azker_list.get(i).title);
            contentValues.put("count", azker_list.get(i).count);


            //   contentValues.put("title",azker_list.get(i).content.get(0).ID);

            if (azker_list.get(i).getBookmark().equals("1")) {


                contentValues.put("bookmark", "1");

            } else {
                contentValues.put("bookmark", "0");
            }
            contentValues.put("content", gson.toJson(azker_list.get(i).content).getBytes());

            db.insert("azkar", null, contentValues);
        }
    }

    public void insertIntoDataBase(String title, String count, String bookmark, String id, List<Content> content) {

        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();
        ContentValues contentValues = new ContentValues();

        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("count", count);

        contentValues.put("bookmark", bookmark);
        contentValues.put("content", gson.toJson(content).getBytes());
        db.insert("azkar", null, contentValues);


    }

    public Cursor readAllData(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM azkar Where id =" + id + "", null, null);
    }

    public void deleteFromD(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("UPDATE azkar SET bookmark ='0' where id =" + id + "");


    }

    public Cursor readAllFav() {


        SQLiteDatabase db = this.getReadableDatabase();

        return db.rawQuery("SELECT * FROM azkar  where bookmark = '1' ", null, null);
    }


    public List<Azker> searchFromD(String search_title) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM azkar  where " + TITLE_COL + " ='" + search_title + "'", null, null);

        List<Azker> search_azkerList = new ArrayList<>();

        while (cursor.moveToNext()) {


            @SuppressLint("Range") String bookmark = cursor.getString(cursor.getColumnIndex("bookmark"));
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
            @SuppressLint("Range") String count = cursor.getString(cursor.getColumnIndex("count"));
            @SuppressLint("Range") byte[] blob = cursor.getBlob(cursor.getColumnIndex("content"));
            String json = new String(blob);
            Gson gson = new Gson();
            List<Content> content = gson.fromJson(json, new TypeToken<List<Content>>() {
            }.getType());
            Azker azker = new Azker(id, title, count, bookmark, content);
            search_azkerList.add(azker);
        }

        return search_azkerList;
    }

    public int getFavorite(Context context, String title) {


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM azkar  Where title Like '" + title + "'", null);

        int count = cursor.getCount();

        return count;

    }
}
