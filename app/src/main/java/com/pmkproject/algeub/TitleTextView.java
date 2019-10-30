package com.pmkproject.algeub;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TitleTextView extends AppCompatTextView {

    public TitleTextView(Context context) {
        super(context);

        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/titlefont.ttf");
        setTypeface(typeface);
    }

    public TitleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Typeface typeface=Typeface.createFromAsset(context.getAssets(), "fonts/titlefont.ttf");
        setTypeface(typeface);
    }
}
