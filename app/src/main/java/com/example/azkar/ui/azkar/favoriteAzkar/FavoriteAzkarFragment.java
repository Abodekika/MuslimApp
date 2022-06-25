package com.example.azkar.ui.azkar.favoriteAzkar;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.azkar.R;
import com.example.azkar.databinding.FragmentAllazkarBinding;
import com.example.azkar.databinding.FragmentFavoriteAzkarBinding;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAzkarFragment extends Fragment {

    private FavoriteAzkarViewModel favoriteAzkarViewModel;
    private FragmentFavoriteAzkarBinding binding;
    FavoriteAdapter favAdapter;
    RecyclerView fav_rec;


    public static FavoriteAzkarFragment newInstance() {
        return new FavoriteAzkarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        favoriteAzkarViewModel =
                new ViewModelProvider(this).get(FavoriteAzkarViewModel.class);

        binding = FragmentFavoriteAzkarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fav_rec = binding.favRec;
        fav_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        favAdapter = new FavoriteAdapter(getContext(), favoriteAzkarViewModel.loadData(getContext()));

        fav_rec.setAdapter(favAdapter);

        favAdapter.notifyDataSetChanged();
    }
}