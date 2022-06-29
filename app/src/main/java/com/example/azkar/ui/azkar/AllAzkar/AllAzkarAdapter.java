package com.example.azkar.ui.azkar.AllAzkar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;


import com.example.azkar.R;
import com.example.azkar.data.Pojo.azkar.Azker;
import com.example.azkar.data.Pojo.azkar.Content;
import com.example.azkar.data.database.azkardatabase.AzkarDataBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AllAzkarAdapter extends RecyclerView.Adapter<AllAzkarAdapter.ViewHolder>
        implements Filterable {

    final private Context context;
    private final List<Azker> azker_list;
    List<Azker> search_azker_list;
    private AzkarDataBase azkarDataBase;
    Fragment fragment;


    public AllAzkarAdapter(Context context, List<Azker> azker_list, Fragment fragment) {
        this.context = context;
        this.azker_list = azker_list;
        search_azker_list = new ArrayList<>(azker_list);
        this.fragment = fragment;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.one_azkar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        azkarDataBase = new AzkarDataBase(context);

        SharedPreferences preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences contextSharedPreferences = context.getSharedPreferences("font_size", Context.MODE_PRIVATE);

        boolean firstStart = preferences.getBoolean("firstStart", true);

        if (firstStart) {
            createTAbleONFirsteStart();
        }

        int font_size = contextSharedPreferences.getInt("font_size", 20);
        int font_style = contextSharedPreferences.getInt("font_style", 1);

        holder.tvTitle.setText(azker_list.get(position).getTitle());

        holder.tvTitle.setTextSize(font_size);
        Typeface font_1;
        switch (font_style) {
            case 0:
                font_1 = Typeface.createFromAsset(context.getAssets(), "fonts/AlHurra Font Bold.ttf");
                break;
            case 1:
                font_1 = Typeface.createFromAsset(context.getAssets(), "fonts/Hacen Beirut Hd.ttf");
                break;
            case 2:
                font_1 = Typeface.createFromAsset(context.getAssets(), "fonts/Alarabiya Font.ttf");
                break;
            case 3:

                font_1 = Typeface.createFromAsset(context.getAssets(), "fonts/Shoroq Font.ttf");
                break;

            default:
                font_1 = Typeface.createFromAsset(context.getAssets(), "fonts/BSEPIDEH.TTF");
                break;
        }

        holder.tvTitle.setTypeface(font_1);

        Azker azker = azker_list.get(position);
        readCursorData(azker, holder);

        holder.tvTitle.setText(azker.title);
        holder.fave_ic.setOnClickListener(v -> {
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

        holder.tvTitle.setOnClickListener(v -> {

            //  List<Content> content = azker_list.get(position).getContent();
            Azker azkera = azker_list.get(position);


            //    Bundle bundle = new Bundle();

            //    bundle.putSerializable("ahmed", (Serializable) content);


            Gson gson = new Gson();


            String azkar_json = gson.toJson(azkera);


            NavHostFragment.findNavController(fragment).navigate(com.example.azkar.ui.azkar.AllAzkar.AllAzkarFragmentDirections.
                    actionAzkarFragmentToAzkarDetailsFragment2(azkar_json));


//
//            Intent intent = new Intent(context, Datails.class);
//
//            List<Content> content = azker_list.get(position).getContent();
//            intent.putExtra("tec", (Serializable) content);
//            intent.putExtra("count", azker_list.get(position).title);


            //  Log.d("AllAzkarAdapter", String.valueOf(azker_list.get(position).getContent()));
//
//
//            context.startActivity(intent);


        });

    }

    private void createTAbleONFirsteStart() {

        azkarDataBase = new AzkarDataBase(context);
        azkarDataBase.insertEmpty(azker_list);
        SharedPreferences preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }

    private void readCursorData(Azker azker, ViewHolder viewHolder) {
        Cursor cursor = azkarDataBase.readAllData(azker.getId());
        SQLiteDatabase db = azkarDataBase.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {

                @SuppressLint("Range") String bookmark = cursor.getString(cursor.getColumnIndex("bookmark"));
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
                @SuppressLint("Range") String count = cursor.getString(cursor.getColumnIndex("count"));
                @SuppressLint("Range") byte[] blob = cursor.getBlob(cursor.getColumnIndex("content"));

                String json = new String(blob);
                Gson gson = new Gson();
                List<Content> persons = gson.fromJson(json, new TypeToken<List<Content>>() {
                }.getType());

                azker.setBookmark(bookmark);

                if (bookmark != null && bookmark.equals("1")) {

                    viewHolder.fave_ic.setImageResource(R.drawable.ic_bookmark_full);

                } else if (bookmark != null && bookmark.equals("0")) {
                    viewHolder.fave_ic.setImageResource(R.drawable.ic_bookmark);
                }


            }
        } finally {
            if (cursor != null && cursor.isClosed()) {
                cursor.close();
                db.close();
            }
        }
    }


    @Override
    public int getItemCount() {
        return azker_list.size();
    }

    /*
        private void saveNote(int position, Context context) {
            String title = azker_list.get(position).getTitle();
            String count = azker_list.get(position).getCount();
            String bookmark = azker_list.get(position).getBookmark();

            ContentValues contentValues = new ContentValues();
            contentValues.put("title", title);
            contentValues.put("count", count);
            contentValues.put("bookmark", bookmark);


            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            long id = db.insert("azkar", null, contentValues);
            if (id != -1) {
                Toast.makeText(context, "Note saved successfully ", Toast.LENGTH_SHORT).show();

            }
        }

        private void deleteFromDB(int position) {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String[] args = {Azker_db.get(position).getTitle()};
            int deleteRow = db.delete("azkar", "title==?", args);


        }
        private void deleteFromD(int position, String title) {
            DBHelper dbHelper = new DBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.execSQL("UPDATE azkar SET bookmark ='0' where title =" + title + "");

        }
    */
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Azker> filter_azkerList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filter_azkerList.addAll(search_azker_list);

            } else {
                String filterpattern = constraint.toString().trim();
                for (Azker azker : search_azker_list) {
                    if (azker.title.contains(filterpattern))
                        filter_azkerList.add(azker);

                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filter_azkerList;
            filterResults.count = filter_azkerList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            azker_list.clear();
            azker_list.addAll((Collection<? extends Azker>) results.values);
            notifyDataSetChanged();
        }
    };

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

            // az_con = itemView.findViewById(R.id.az_con);


        }
    }
}
