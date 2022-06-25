package com.example.azkar.data.download.download_quan_image;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azkar.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class HTTP_ayat extends AsyncTask<String, String, String> {
    private ProgressDialog progressDialog;
    Dialog dialog;
    ProgressBar progressBar;
    TextView view;

    @SuppressLint("StaticFieldLeak")
    private final Context context;


    public HTTP_ayat(Context context) {
        this.context = context;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.download_progress_dialog);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressBar = dialog.findViewById(R.id.progress_down);
        view = dialog.findViewById(R.id.tv_down);

        progressBar.setMax(100);

        dialog.show();

//        progressDialog = new ProgressDialog(context);
//        progressDialog.setTitle("جاري التحميل ");
//        progressDialog.setMessage("برجاء الانتظار ");
//        progressDialog.setIndeterminate(false);
//        progressDialog.setMax(100);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//
//        progressDialog.show();


    }

    @SuppressLint("SdCardPath")
    @Override
    protected String doInBackground(String... strings) {

        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;


        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            int length_of_file = connection.getContentLength();


            input = new BufferedInputStream(url.openStream());
/*
                String directory = "/TestDownload";
                String root = Environment.getExternalStorageDirectory().toString();
                File dir = new File(root + "/saved_images");

                int id = 75;

                String fname = "" + id + ".mp3";

                if (!dir.exists())           //check if not created then create the firectory
                    dir.mkdirs();
                File file = new File(dir, fname);
*/
            //  OutputStream output = new FileOutputStream("/sdcard/some_photo_from_gdansk_poland.jpg");
            //    output = new FileOutputStream("/sdcard/some_255.mp3");

            //    int position = viewPager.getCurrentItem();
            // String filepath = "https://www.hisnmuslim.com/audio/ar/75.mp3";
            //    String filepath = content.get(postion).AUDIO;
            //   int id = contentList.get(position).getID();
            //   Log.d("ANDRO_ASYNC", "Lenght of file1: " + id);

            String fname = "images.zip";
            File root;
            //  root = context.getExternalFilesDir("");
            root = Environment.getExternalStoragePublicDirectory("");
            // root = new File(String.valueOf(Environment.getDataDirectory()));

            Log.d("Ahmed20", "Lenght of file2: " + root);

            File dir = new File(root + "/hisnmuslim/quranImage");


            if (!dir.exists())           //check if not created then create the firectory
                dir.mkdirs();

            File file = new File(dir, fname);
            Log.d("Ahmed50", "Lenght of file2: " + fname);


            output = new FileOutputStream(file);
            byte data[] = new byte[4096];
            long total = 0;
            int count;

            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                if (length_of_file > 0) // only if total length is known
                    publishProgress(String.valueOf((int) (total * 100 / length_of_file)));
                output.write(data, 0, count);
                publishProgress("" + (int) ((total * 100) / length_of_file));
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ANDRO_ASYNC", "Lenght of file2: " + e);
        } finally {
            try {
                if (output != null)
                    output.flush();
                output.close();
                input.close();

            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {

        // progressDialog.setProgress(Integer.parseInt(values[0]));

        progressBar.setProgress(Integer.parseInt(values[0]));
        view.setText(Integer.parseInt(values[0]) + "");

        //  new Thread(() -> download()).start();

        Log.e("Progress", values[0].toString());
        if (Integer.parseInt(values[0]) == 100) {
                /*
                LayoutInflater inflater = MainActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_layout, null);
                ImageView imageView = (ImageView) dialogView.findViewById(R.id.imageView);
                File file = new File(filepath + "/" + FILE_NAME);
                Glide.with(MainActivity.this).load(file).into(imageView);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                        .setTitle("Success!!")
                        .setMessage("Image Downloaded at ");

                alertDialog.show();

                 */
            download();
            Toast.makeText(context, "تم التحميل ", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if (s != null)
            Toast.makeText(context, "خطا في تحميل الملف " + s, Toast.LENGTH_LONG).show();
        else
            Toast.makeText(context, "تم التحميل ", Toast.LENGTH_SHORT).show();
    }

    public void download() {
        File root;


        root = Environment.getExternalStoragePublicDirectory("");


        File dir = new File(root + "/hisnmuslim/quranImage");

        String fname = "images.zip";
        File file = new File(dir, fname);
        String filename = "images.zip";
        if (file.exists()) {
            try {
                unzip(filename, String.valueOf(dir));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //  Toast.makeText(, "ha'h", Toast.LENGTH_SHORT).show();

        } else {
            // Toast.makeText(this, "لا لال لال الال لاللا", Toast.LENGTH_SHORT).show();
        }
    }

    private void unzip(String zipFileName, String destDirectory) throws IOException {
        File root;

        root = Environment.getExternalStoragePublicDirectory("");


        File dir1 = new File(root + "/hisnmuslim/quranImage");

        // File destDirectoryFolder = new File(dir);

        if (!dir1.exists()) {
            dir1.mkdir();
            // Toast.makeText(this, "جوه الفلدور", Toast.LENGTH_SHORT).show();
        }
        String filename = dir1 + "/images.zip";

        //Toast.makeText(this, "بره  الفلدور", Toast.LENGTH_SHORT).show();
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(filename));
        ZipEntry zipEntry = zis.getNextEntry();
        //  Toast.makeText(this, "55555" + zipEntry.getName(), Toast.LENGTH_SHORT).show();
        while (zipEntry != null) {
            String filePath = destDirectory + File.separator + zipEntry.getName();

            System.out.println("Unzipping " + filePath);
            if (!zipEntry.isDirectory()) {
                FileOutputStream fos = new FileOutputStream(filePath);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zis.closeEntry();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
        System.out.println("Unzipping complete");

    }

}

