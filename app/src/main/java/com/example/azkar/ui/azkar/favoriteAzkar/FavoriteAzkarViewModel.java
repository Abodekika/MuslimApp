package com.example.azkar.ui.azkar.favoriteAzkar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ViewModel;


import com.example.azkar.data.Pojo.azkar.Content;
import com.example.azkar.data.Pojo.azkar.FavItem;
import com.example.azkar.data.database.azkardatabase.AzkarDataBase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAzkarViewModel extends ViewModel {


    private AzkarDataBase azkarDataBase;

    public List<FavItem> loadData(Context context) {
        List<FavItem> fav_azkar_list = new ArrayList<>();
        azkarDataBase = new AzkarDataBase(context);

        if (fav_azkar_list != null) {
            fav_azkar_list.clear();
        }

        SQLiteDatabase db = azkarDataBase.getReadableDatabase();
        Cursor cursor = azkarDataBase.readAllFav();

        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String count = cursor.getString(cursor.getColumnIndex("count"));
                String bookmark = cursor.getString(cursor.getColumnIndex("bookmark"));
                byte[] blob = cursor.getBlob(cursor.getColumnIndex("content"));
                String json = new String(blob);
                Gson gson = new Gson();
                List<Content> content = gson.fromJson(json, new TypeToken<List<Content>>() {
                }.getType());

                FavItem favItem = new FavItem(id, title, count, bookmark, content);

                fav_azkar_list.add(favItem);

            }
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
                db.close();
            }

        }
        return fav_azkar_list;
    }
}