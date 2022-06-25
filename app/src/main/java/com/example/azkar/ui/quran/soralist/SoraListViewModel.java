package com.example.azkar.ui.quran.soralist;

import android.content.Context;

import androidx.lifecycle.ViewModel;


import com.example.azkar.data.Pojo.quran.Sora;
import com.example.azkar.data.database.QuranDataBase.QuranDao;
import com.example.azkar.data.database.QuranDataBase.QuranDataBase;

import java.util.ArrayList;

public class SoraListViewModel extends ViewModel {
    public ArrayList<Sora> getAllSora(Context context) {

        QuranDao dao = QuranDataBase.getInstance(context).quranDao();

        ArrayList<Sora> soras = new ArrayList<>();

        for (int i = 1; i <= 114; i++) {
            soras.add(dao.getSoraByNumber(i));

        }

        return soras;
    }
}