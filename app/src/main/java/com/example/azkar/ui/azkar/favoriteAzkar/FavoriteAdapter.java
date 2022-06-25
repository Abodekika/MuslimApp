package com.example.azkar.ui.azkar.favoriteAzkar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.azkar.R;

import com.example.azkar.data.Pojo.azkar.Content;
import com.example.azkar.data.Pojo.azkar.FavItem;
import com.example.azkar.data.database.azkardatabase.AzkarDataBase;


import java.io.Serializable;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private Context context;
    public List<FavItem> fav_azkar_list;

    private AzkarDataBase azkarDataBase;

    public FavoriteAdapter(Context context, List<FavItem> fav_azkar_list) {
        this.context = context;
        this.fav_azkar_list = fav_azkar_list;
    }


    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        azkarDataBase = new AzkarDataBase(context);

        holder.tvTitle.setText(fav_azkar_list.get(position).getTitle());

        holder.tvTitle.setOnClickListener(v -> {
/*

            Intent intent = new Intent(context, Datails.class);


            List<Content> contentList = fav_azkar_list.get(position).getContent();
            intent.putExtra("tec", (Serializable) contentList);
            intent.putExtra("count", fav_azkar_list.get(position).getTitle());


            //  Log.d("AllAzkarAdapter", String.valueOf(azker_list.get(position).getContent()));


            context.startActivity(intent);

*/
        });


    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return fav_azkar_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tv_count;
        TextView tv_bookmark;
        TextView az_con;
        ImageView fave_ic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.az_title);
            fave_ic = itemView.findViewById(R.id.fave_ic);

            fave_ic.setOnClickListener(v -> {
                int position = getAdapterPosition();
                final FavItem favItem = fav_azkar_list.get(position);
                azkarDataBase.deleteFromD(favItem.getId());
                removeItem(position);

            });


        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void removeItem(int position) {
        fav_azkar_list.remove(position);
        notifyDataSetChanged();
        notifyItemRangeChanged(position, fav_azkar_list.size());

    }

}
