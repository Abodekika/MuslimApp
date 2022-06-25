package com.example.azkar.ui.azkar.AzkarDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;


import com.example.azkar.R;
import com.example.azkar.data.Pojo.azkar.Content;
import com.example.azkar.data.download.downloadazkaraduio.HttpDownloadAzkarAduio;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DetailPagerAdapter extends RecyclerView.Adapter<DetailPagerAdapter.ViewHolder> {


    ViewPager2 viewPager;

    Handler handler = new Handler();

    private boolean isPause = false;
    public static MediaPlayer mp = new MediaPlayer();
    final private Context context;
    final private List<Content> contentList;


    String title;


    public DetailPagerAdapter(Context context, List<Content> contentList, String title,
                              ViewPager2 viewPager) {
        this.context = context;
        this.contentList = contentList;
        this.title = title;
        this.viewPager = viewPager;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_pager_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"ResourceAsColor", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SharedPreferences preferences = context.getSharedPreferences("font_size", Context.MODE_PRIVATE);
        int font_size = preferences.getInt("font_size", 20);
        int font_style = preferences.getInt("font_style", 1);

        holder.pv_title.setText(contentList.get(position).getText());

        holder.pv_title.setTextSize(font_size);
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

        holder.pv_title.setTypeface(font_1);

        holder.pv_source.setText(contentList.get(position).getFadl());


        holder.pv_count.setText(String.valueOf(contentList.size()));
        holder.pv_position.setText(String.valueOf(position + 1));
        int count = Integer.parseInt(contentList.get(position).getCount());

        switch (count) {
            case 1:
                holder.tv_counter.setText("مرة واحدة");
                break;
            case 2:
                holder.tv_counter.setText("مرتين");
                break;
            case 3:
                holder.tv_counter.setText("ثلاث مرات ");
                break;
            case 4:
                holder.tv_counter.setText("اربع مرات");
                break;
            case 5:
                holder.tv_counter.setText("خمس مرات");
                break;
        }

        holder.ln50.setOnTouchListener((view, motionEvent) -> {
            holder.counterprogress(contentList, viewPager);
            return true;
        });


        holder.btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String filepath = contentList.get(position).getAUDIO();
                int id = contentList.get(position).getID();
                Log.d("ANDRO_ASYNC55", "Lenght of file3: " + id);
                String fname = "" + id + ".audio";
                // File root = context.getExternalFilesDir("");
                File root = Environment.getExternalStoragePublicDirectory("");
                // String root = Environment.getExternalStorageDirectory().toString();

                File dir = new File(root + "/hisnmuslim");

                File file = new File(dir, fname);
                if (isPause) {
                    mp.reset();
                    mp.stop();

                }
                if (!mp.isPlaying()) {
                    if (!file.exists()) {
                        if (isConnected()) {

                            final HttpDownloadAzkarAduio downloadTask = new HttpDownloadAzkarAduio(context, id);
                            downloadTask.execute(filepath);

                        } else {
                            Toast.makeText(context, "لا يوجد انترنت ", Toast.LENGTH_SHORT).show();
                        }
                    }


                    try {
                        mp.setDataSource(String.valueOf(file));
                        if (!file.exists()) {

                            mp.prepareAsync();

                        } else {
                            mp.prepare();
                            mp.start();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {

                    mp.stop();
                    mp.reset();
                }







                /*
                else {
                    Toast.makeText(context, "INTERNET NOT AVAILABLE", Toast.LENGTH_SHORT).show();
                }

                 */

            }


        });
        holder.btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mp.isPlaying()) {
                    mp.pause();
                    isPause = true;

                } else {
                    mp.start();
                    isPause = false;


                    //  Log.d("Ah","aa"+)
                }
            }
        });


    }

    public boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;

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

        return contentList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView pv_title;
        TextView pv_count;
        TextView tv_counter;
        TextView pv_position;
        ProgressBar progress_count;
        TextView tv_count;
        TextView pv_source;
        int currentProgress = 0;
        int P_counter = 0;
        RelativeLayout lo;
        ImageView ic_volume;
        TextView begin, end;
        SeekBar seek_bar;
        ImageView play_sound;
        // MediaPlayer mp = new MediaPlayer();
        CardView card_att;
        private boolean isActionShow = false;
        Button btnPlay;
        Button btnStop;
        RelativeLayout ln50;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pv_title = itemView.findViewById(R.id.pv_title);
            pv_source = itemView.findViewById(R.id.pv_source);
            tv_counter = itemView.findViewById(R.id.tv_counter);


            // card_att = itemView.findViewById(R.id.card_att);
            pv_count = itemView.findViewById(R.id.pv_count);
            pv_position = itemView.findViewById(R.id.pv_position);
            progress_count = itemView.findViewById(R.id.progress_count);
            tv_count = itemView.findViewById(R.id.tv_count);
            // begin = itemView.findViewById(R.id.begin);
            // end = itemView.findViewById(R.id.end);
            // seek_bar = itemView.findViewById(R.id.seek_bar);

            btnPlay = itemView.findViewById(R.id.btnPlay);
            btnStop = itemView.findViewById(R.id.btnStop);
            ln50 = itemView.findViewById(R.id.ln50);

            pv_source = itemView.findViewById(R.id.pv_source);


        }


        @SuppressLint("ResourceAsColor")
        private void counterprogress(List<Content> contentList, ViewPager2 viewPager2) {

            int position = viewPager2.getCurrentItem();

            int count = Integer.parseInt(contentList.get(position).getCount());

            progress_count.setMax(count);

            if (currentProgress < count && position >= P_counter) {
                currentProgress = currentProgress + 1;
                progress_count.setProgress(currentProgress);
                tv_count.setText("" + currentProgress);


            }
            if (currentProgress == count) {

                viewPager2.setCurrentItem(position + 1);

                currentProgress = 0;
                P_counter += 50;

                // attachment();

            }

        }

        public void attachment() {

            if (isActionShow) {
                //   card_att.setVisibility(View.GONE);
                isActionShow = false;

            } else {
                //  card_att.setVisibility(View.VISIBLE);
                isActionShow = true;
            }


        /*
        LinearLayout linearLayout;

        linearLayout = findViewById(R.id.ll3);

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        View popupView = inflater.inflate(R.layout.attachment_layout, null);
        int width = (LinearLayout.LayoutParams.WRAP_CONTENT);
        int height = (LinearLayout.LayoutParams.WRAP_CONTENT);

        PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

        popupWindow.showAtLocation(linearLayout, Gravity.NO_GRAVITY, 50, 2100);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

*/
        }


    }
}


