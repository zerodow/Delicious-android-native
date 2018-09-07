package com.example.admin.appquanan;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Admin on 10/19/2017.
 */

public class FontManager {

    public static final String FONTAWESOME = "fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }

}
