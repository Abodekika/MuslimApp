package com.example.azkar.ui.azkar.AllAzkar;

import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azkar.R;
import com.example.azkar.data.Pojo.azkar.Azker;
import com.example.azkar.databinding.FragmentAllazkarBinding;

import com.example.azkar.ui.splashscreen.Dashboard.DashboardFragment;
import com.example.azkar.ui.splashscreen.Dashboard.DashboardFragmentDirections;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AllAzkarFragment extends Fragment {

    private FragmentAllazkarBinding binding;
    public static List<Azker> azkerList;
    public static AllAzkarAdapter adapter;
    AllAzkarViewModel allAzkarViewModel;
    Toolbar toolbar;
    RecyclerView az_rec;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allAzkarViewModel =
                new ViewModelProvider(this).get(AllAzkarViewModel.class);

        binding = FragmentAllazkarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        toolbar = binding.toolbar;

        az_rec = binding.azRec;
        azkerList = new ArrayList<>();





        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {


                switch (item.getItemId()) {

                    case R.id.favorite:
                        NavHostFragment.findNavController(AllAzkarFragment.this
                        ).navigate(AllAzkarFragmentDirections.actionAzkarFragmentToFavoriteAzkarFragment());

                        return true;
                }
                return true;
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Gson gson = new Gson();
        String file = allAzkarViewModel.readAzkar(getContext());
        Type ak = new TypeToken<ArrayList<Azker>>() {
        }.getType();
        azkerList = gson.fromJson(file, ak);

        az_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AllAzkarAdapter(getContext(), azkerList, this);
            toolbar.setTitle("اذكار المسلم ");

        az_rec.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}