package it.softlab.training.app_oco.sync;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by claudio on 5/10/17.
 */

public class ImageSyncTask {
    public static Bitmap fetchImage(String galleryUrlString) {
        URL url = null;
        try {
            url = new URL(galleryUrlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Bitmap bmp = null;
        try

        {
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
