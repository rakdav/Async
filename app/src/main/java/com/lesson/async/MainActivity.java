package com.lesson.async;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Button button;
    private String urlString="https://oir.mobi/uploads/posts/2021-05/1620357244_60-oir_mobi-p-zimorodok-ptichka-zhivotnie-krasivo-foto-63.jpg";
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.image);
        button=findViewById(R.id.load);
        handler=new Handler();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //new ImageDownloadTask(imageView).execute("https://oir.mobi/uploads/posts/2021-05/1620357244_60-oir_mobi-p-zimorodok-ptichka-zhivotnie-krasivo-foto-63.jpg");
               ImageDownloadThread();
            }
        });
    }
    private class ImageDownloadTask extends AsyncTask<String,Void, Bitmap>
    {
        private ImageView imageView;

        public ImageDownloadTask(ImageView imageView) {
            this.imageView = imageView;
        }
        @Override
        protected Bitmap doInBackground(String... strings)
        {
            String url=strings[0];
            Bitmap image=null;
            try {
                InputStream in=new URL(url).openStream();
                image= BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
    private void ImageDownloadThread()
    {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream in=new URL(urlString).openStream();
                    final Bitmap bitmap=BitmapFactory.decodeStream(in);
                    if(bitmap!=null)
                    {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}