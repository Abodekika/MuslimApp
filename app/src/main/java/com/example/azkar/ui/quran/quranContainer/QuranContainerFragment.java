package com.example.azkar.ui.quran.quranContainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.example.azkar.R;


public class QuranContainerFragment extends Fragment {

    private static final String TAG = "QuranContainer";

    private ViewPager2 pager;

    private QuranContainerFragmentArgs args;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        args = QuranContainerFragmentArgs.fromBundle(requireArguments());


        return inflater.inflate(R.layout.fragment_quran_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager = view.findViewById(R.id.quran_pager);
        pager.setAdapter(new QuranPagesAdapter(this));


        pager.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        pager.setCurrentItem(604 - args.getStartPage(), false);





    }
}