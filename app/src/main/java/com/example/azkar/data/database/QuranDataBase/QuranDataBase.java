package com.example.azkar.data.database.QuranDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.azkar.data.Pojo.quran.Aya;


@Database(entities = {Aya.class}, version = 1)
public abstract class QuranDataBase extends RoomDatabase {

    private static QuranDataBase instance = null;

    public abstract QuranDao quranDao();

    public static QuranDataBase getInstance(Context context) {

        if (instance == null) {
            synchronized (QuranDataBase.class) {
                if (instance == null) {
                    try {
                        String DATABASE_NAME = "quran.db";
                        instance = Room.databaseBuilder(context.getApplicationContext(),
                                QuranDataBase.class, DATABASE_NAME)
                                .createFromAsset("QuranDataBase/quran.db")
                                .allowMainThreadQueries()
                                .build();
                    } catch (Exception e) {
                        return null;
                    }
                }
            }
        }
        return instance;
    }
}
