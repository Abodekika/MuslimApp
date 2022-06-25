package com.example.azkar.ui.quran.quranpage;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.azkar.data.sharedpreferences.QuranPreferences;
import com.example.azkar.databinding.FragmentQuranPageBinding;

import java.util.Objects;


public class QuranPageFragment extends Fragment {

    private QuranPageViewModel mViewModel;

    private FragmentQuranPageBinding binding;
    QuranPreferences quranPreferences;

    private final int pageNumber;
    ImageView imageView;
    String url = "https://firebasestorage.googleapis.com/v0/b/myimage-60acc.appspot.com/o/quran_images.zip?alt=media&token=171d1bd3-7601-4963-8001-1d606ce84a64";

    public QuranPageFragment(int pageNumber) {
        this.pageNumber = pageNumber;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentQuranPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mViewModel = new ViewModelProvider(this).get(QuranPageViewModel.class);
        imageView = binding.quranImage;
        quranPreferences = new QuranPreferences(requireContext());
        mViewModel.downloadQuranPage(getContext(), url);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bitmap quran = mViewModel.getQuranImage(pageNumber);
        imageView.setImageBitmap(quran);

        quranPreferences.setLast_page(pageNumber);


        //  adapter = new ViewPagerAdapter(getContext(), quran);
        // final RecyclerView pageRec = binding.pageRec;

        //  pageRec.setAdapter(adapter);
        // pageRec.setLayoutManager(new LinearLayoutManager(getContext()));

    }

}