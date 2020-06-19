package com.example.wallpaper_app;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    ImageView disp_image_area;
    Button button_next;
    Button button_download;
    int index=1;
    int [] images = {R.drawable.wallpaper1,R.drawable.wallpaper2,R.drawable.wallpaper3,R.drawable.wallpaper4,R.drawable.wallpaper5,R.drawable.wallpaper6,R.drawable.wallpaper7,R.drawable.wallpaper8,R.drawable.wallpaper9,R.drawable.wallpaper10,R.drawable.wallpaper11,R.drawable.wallpaper12,R.drawable.wallpaper13,R.drawable.wallpaper14,R.drawable.wallpaper15,R.drawable.wallpaper16,R.drawable.wallpaper17,R.drawable.wallpaper18,R.drawable.wallpaper18,R.drawable.wallpaper20,R.drawable.wallpaper21,R.drawable.wallpaper22,R.drawable.wallpaper23,R.drawable.wallpaper24,R.drawable.wallpaper25,R.drawable.wallpaper26,R.drawable.wallpaper27,R.drawable.wallpaper28,R.drawable.wallpaper29,R.drawable.wallpaper30,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        disp_image_area=findViewById(R.id.disp_image_area);
        button_next=findViewById(R.id.button_next);
        button_download=findViewById(R.id.button_download);
        disp_image_area.setImageResource(images[0]);
    }

    public void btnClicked(View view) throws IOException {
        int clicked_button = view.getId();

            if(clicked_button==R.id.button_next)
            {
                if(index<images.length){
                disp_image_area.setImageResource(images[index]);
                index++;
                }
                else
                {
                   index=0;
                }
            }
            else if(clicked_button==R.id.button_download)
            {

                    Bitmap bm = BitmapFactory.decodeResource(getResources(), images[index-1]);
                    saveBitmap(getApplicationContext(),bm,Bitmap.CompressFormat.JPEG,"image/jpeg","Downloaded");



            }



    }
    private void saveBitmap(@NonNull final Context context, @NonNull final Bitmap bitmap,
                            @NonNull final Bitmap.CompressFormat format, @NonNull final String mimeType,
                            @NonNull final String displayName) throws IOException
    {
        final String relativeLocation = Environment.DIRECTORY_PICTURES;

        final ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);

        final ContentResolver resolver = context.getContentResolver();

        OutputStream stream = null;
        Uri uri = null;

        try
        {
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            uri = resolver.insert(contentUri, contentValues);

            if (uri == null)
            {
                throw new IOException("Failed to create new MediaStore record.");
            }

            stream = resolver.openOutputStream(uri);

            if (stream == null)
            {
                throw new IOException("Failed to get output stream.");
            }

            if (bitmap.compress(format, 95, stream) == false)
            {
                throw new IOException("Failed to save bitmap.");
            }
        }
        catch (IOException e)
        {
            if (uri != null)
            {
                // Don't leave an orphan entry in the MediaStore
                resolver.delete(uri, null, null);
            }

            throw e;
        }
        finally
        {
            if (stream != null)
            {
                stream.close();
            }
        }
    }
}