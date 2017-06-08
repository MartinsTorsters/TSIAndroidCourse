package com.example.martins.tsiapplication;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by Martins on 08/06/2017.
 */

public final class Utils {
    public static Drawable LoadImageFromWebOperations(final String url) {
        try {
            final InputStream is = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(is, "src name");
        } catch (Exception e) {
            Log.w("Couldn't convert image", e);
            return null;
        }
    }
}
