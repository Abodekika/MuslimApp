package com.example.azkar.ui.azkar.AzkarDetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;


import com.example.azkar.R;
import com.example.azkar.data.Pojo.azkar.Content;
import com.example.azkar.databinding.FragmentAzkarDetailsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AzkarDetailsFragment extends Fragment {

    private AzkarDetailsViewModel mViewModel;
    List<Content> content;
    private com.example.azkar.ui.azkar.AzkarDetails.AzkarDetailsFragmentArgs args;
    private FragmentAzkarDetailsBinding binding;
    ViewPager2 viewPager;
    Toolbar toolbar;

    public static AzkarDetailsFragment newInstance() {
        return new AzkarDetailsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mViewModel = new ViewModelProvider(this).get(AzkarDetailsViewModel.class);
        binding = FragmentAzkarDetailsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewPager = binding.viewPager;
        toolbar = binding.toolbarIn;

        return root;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = com.example.azkar.ui.azkar.AzkarDetails.AzkarDetailsFragmentArgs.fromBundle(requireArguments());

        // content = (List<Content>) args.getAzkarContent();
        Gson gson = new Gson();
        String file = args.getAzkarContent();
        Type ak = new TypeToken<ArrayList<Content>>() {
        }.getType();
        content = gson.fromJson(file, ak);
        String title = args.getAzkarName();
        toolbar.setTitle(title);

        DetailPagerAdapter adapter = new DetailPagerAdapter(getContext(), content, title, viewPager);

        viewPager.setAdapter(adapter);

    }
}