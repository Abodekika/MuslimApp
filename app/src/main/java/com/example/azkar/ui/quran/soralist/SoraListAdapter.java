package com.example.azkar.ui.quran.soralist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.azkar.R;
import com.example.azkar.data.Pojo.quran.Sora;

import java.util.List;

public class SoraListAdapter extends RecyclerView.Adapter<SoraListAdapter.ViewHolder> {
    private Context context;
    private List<Sora> soraList;
    Fragment fragment;

    public SoraListAdapter(Context context, List<Sora> soraList, Fragment fragment) {
        this.context = context;
        this.soraList = soraList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sora_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sora_name.setText(soraList.get(position).getArabicName());

        holder.sora_number.setText(String.valueOf(soraList.get(position).getSoraNumber()));
        holder.sora_name.setOnClickListener(v -> {

            NavHostFragment.findNavController
                    (fragment).navigate(SoraListFragmentDirections.
                    actionSoraListFragmentToQuranFragment(soraList.get(position).getStartPage()));


        });

    }


    @Override
    public int getItemCount() {
        return soraList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sora_name;
        TextView sora_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sora_name = itemView.findViewById(R.id.sora_name);
            sora_number = itemView.findViewById(R.id.sora_number);
        }


    }
}
