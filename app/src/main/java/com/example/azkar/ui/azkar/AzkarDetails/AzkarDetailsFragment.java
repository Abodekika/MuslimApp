package com.example.azkar.ui.azkar.AzkarDetails;

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
import androidx.viewpager2.widget.ViewPager2;


import com.example.azkar.R;
import com.example.azkar.data.Pojo.azkar.Azker;
import com.example.azkar.data.Pojo.azkar.Content;
import com.example.azkar.data.database.azkardatabase.AzkarDataBase;
import com.example.azkar.databinding.FragmentAzkarDetailsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AzkarDetailsFragment extends Fragment {

    private AzkarDetailsViewModel mViewModel;
    List<Content> content;
    Azker azkar;
    private com.example.azkar.ui.azkar.AzkarDetails.AzkarDetailsFragmentArgs args;
    private FragmentAzkarDetailsBinding binding;
    ViewPager2 viewPager;
    Toolbar toolbar;
    private AzkarDataBase azkarDataBase;

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
        azkarDataBase = new AzkarDataBase(getContext());

        return root;


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        args = AzkarDetailsFragmentArgs.fromBundle(requireArguments());

        // content = (List<Content>) args.getAzkarContent();
        Gson gson = new Gson();


        String azkar_json = args.getAzkar();


        Type ak1 = new TypeToken<Azker>() {
        }.getType();
        azkar = gson.fromJson(azkar_json, ak1);


        toolbar.setTitle(azkar.getTitle());

        DetailPagerAdapter adapter = new DetailPagerAdapter(getContext(), azkar.getContent(), azkar.getTitle(), viewPager);


        if (azkar.getBookmark().equals("0")) {
            toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_baseline_favorite_border_24);

        } else {
            toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_24);

        }


        toolbar.getMenu().findItem(R.id.favorite).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if (azkar.getBookmark().equals("0")) {
                    azkar.setBookmark("1");
                    azkarDataBase.insertIntoDataBase(azkar.getTitle(),
                            azkar.getCount(), azkar.getBookmark(),
                            azkar.getId(), AzkarDetailsFragment.this.content);
                    toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_24);
                    Toast.makeText(getContext(), "تمت الاضافة", Toast.LENGTH_SHORT).show();
                }else {
                    azkar.setBookmark("0");
                    azkarDataBase.deleteFromD(azkar.getId());
                    toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_baseline_favorite_border_24);
                    Toast.makeText(getContext(), "تم الحذف", Toast.LENGTH_SHORT).show();
                }


                return true;
            }
        });

/*
         else {


            toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_24);
            toolbar.getMenu().findItem(R.id.favorite).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    azkar.setBookmark("1");
                    azkarDataBase.insertIntoDataBase(azkar.getTitle(),
                            azkar.getCount(), azkar.getBookmark(),
                            azkar.getId(), AzkarDetailsFragment.this.content);
                    toolbar.getMenu().findItem(R.id.favorite).setIcon(R.drawable.ic_favorite_24);
                    Toast.makeText(getContext(), "تم الحذف", Toast.LENGTH_SHORT).show();

                    return true;
                }
            });


        }


              if (azker.getBookmark().equals("0")) {

                azker.setBookmark("1");
                azkarDataBase.insertIntoDataBase(azker.getTitle(), azker.getCount(), azker.getBookmark(), azker.getId(), azker.content);
                holder.fave_ic.setImageResource(R.drawable.ic_bookmark_full);
                Toast.makeText(context, "تمت الاضافة", Toast.LENGTH_SHORT).show();


            } else {

                azker.setBookmark("0");
                azkarDataBase.deleteFromD(azker.getId());
                holder.fave_ic.setImageResource(R.drawable.ic_bookmark);
                Toast.makeText(context, "تم الحذف", Toast.LENGTH_SHORT).show();
            }

        });
         */

        viewPager.setAdapter(adapter);

    }
}