package com.example.async;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {
    private EditText url;
    private Button go;
    private ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url=findViewById(R.id.url);
        go=findViewById(R.id.go);


        image=findViewById(R.id.picture);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownImage().execute(url.getText().toString());
            }
        });
    }
    private class DownImage extends AsyncTask<String,Void, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bm=null;
            try {
                URL url=new URL(strings[0]);
                URLConnection connection=url.openConnection();
                connection.connect();
                InputStream is=connection.getInputStream();
                BufferedInputStream bis=new BufferedInputStream(is);
                bm= BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }
    }
}
