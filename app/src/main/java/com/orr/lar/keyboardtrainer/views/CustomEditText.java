package com.orr.lar.keyboardtrainer.views;

import android.content.Context;
import android.util.AttributeSet;

public class CustomEditText extends android.support.v7.widget.AppCompatEditText {


    public CustomEditText(Context context) {
        super(context);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onSelectionChanged(int start, int end) {
        //! Disables cursor positioning and text selection in an EditText? (Android)
        CharSequence text = getText();
        if (text != null) {
            if (start != text.length() || end != text.length()) {
                setSelection(text.length(), text.length());
                return;
            }
        }

        super.onSelectionChanged(start, end);
    }
}
