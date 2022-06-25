package com.example.azkar.ui.quran.soralist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.azkar.R;
import com.example.azkar.data.sharedpreferences.QuranPreferences;
import com.example.azkar.databinding.FragmentSoraListBinding;
import com.example.azkar.ui.azkar.AllAzkar.AllAzkarFragment;
import com.example.azkar.ui.azkar.AllAzkar.AllAzkarFragmentDirections;


public class SoraListFragment extends Fragment {

    private FragmentSoraListBinding binding;
    SoraListViewModel soraListViewModel;
    RecyclerView quran_list_Rec;
    Toolbar toolbar;
    QuranPreferences quranPreferences;


    public static SoraListFragment newInstance() {
        return new SoraListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        soraListViewModel =
                new ViewModelProvider(this).get(SoraListViewModel.class);

        binding = FragmentSoraListBinding.inflate(inflater, container, false);
        quran_list_Rec = binding.quranListRec;
        View root = binding.getRoot();
        toolbar = binding.soraListToolbar;
        quranPreferences = new QuranPreferences(requireContext());

        toolbar.setOnMenuItemClickListener(item -> {


            switch (item.getItemId()) {

                case R.id.last_page:
                    NavHostFragment.findNavController
                            (SoraListFragment.this).navigate(SoraListFragmentDirections.
                            actionSoraListFragmentToQuranFragment(quranPreferences.getLast_page()));

                    return true;
            }
            return true;
        });
        getActivity().getWindow().getDecorView().setLayoutDirection(
                View.LAYOUT_DIRECTION_RTL);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        quran_list_Rec.setAdapter(new SoraListAdapter(getContext(), soraListViewModel.getAllSora(getContext()), this));
        //   quran_list_Rec.setLayoutManager(new LinearLayoutManager(this));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        quran_list_Rec.setLayoutManager(gridLayoutManager);

        toolbar.setTitle("القران الكريم");
    }
}