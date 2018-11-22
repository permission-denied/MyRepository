package com.may.base.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * Created by may on 2018/8/15.
 */

public class SpannableStringUtils {
    public static SpannableStringBuilder changeTextPartColor(String text, int startIndex, int endIndex, int color) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder("");
        }
        return changeTextPartColor(new SpannableStringBuilder(text), startIndex, endIndex, color);
    }

    public static SpannableStringBuilder changeTextPartColor(SpannableStringBuilder builder, int startIndex, int endIndex, int color) {
        if (builder == null || builder.length() == 0) {
            return new SpannableStringBuilder("");
        }
        if (startIndex < 0 || endIndex < 0) {
            return new SpannableStringBuilder("");
        }
        if (startIndex > builder.length() - 1 || endIndex > builder.length()) {
            return builder;
        }
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        builder.setSpan(foregroundColorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder changeTextPartSize( SpannableStringBuilder builder, int startIndex, int endIndex, float textSize) {
        if (startIndex < 0 || endIndex < 0) {
            return builder;
        }

        if (startIndex > builder.length() - 1 || endIndex > builder.length()) {
            return builder;
        }

        AbsoluteSizeSpan sizeSpan = new AbsoluteSizeSpan(ScreenUtils.dpToPxInt(textSize));
        builder.setSpan(sizeSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static SpannableStringBuilder changeTextPartSize(Context context, String text, int sizeStart, int sizeEnd, float textSize) {
        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder("");
        }
        return new SpannableStringBuilder(text);
    }


    public static SpannableStringBuilder changeTextPartSize(Context context, String text, int firstStartIndex, int firstEndIndex, int secondStartIndex, int secondEndIndex, float textSize) {
        return changeTextPartSize(context, text, firstStartIndex, firstEndIndex, secondStartIndex, secondEndIndex, textSize, textSize);
    }


    public static SpannableStringBuilder changeTextPartSize(Context context, String text, int firstStartIndex, int firstEndIndex, int secondStartIndex, int secondEndIndex, float firstTextSize, float secondTextSize) {

        if (TextUtils.isEmpty(text)) {
            return new SpannableStringBuilder("");
        }

        if (firstStartIndex < 0 || firstEndIndex < 0 || firstStartIndex > text.length() - 1 || firstEndIndex > text.length()) {
            return new SpannableStringBuilder(text);
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        AbsoluteSizeSpan firstSizeSpan = new AbsoluteSizeSpan(ScreenUtils.dpToPxInt( firstTextSize));
        builder.setSpan(firstSizeSpan, firstStartIndex, firstEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        if (secondEndIndex <= 0 || secondStartIndex <= 0 || secondEndIndex > text.length() || secondStartIndex > text.length() - 1 || secondStartIndex < firstEndIndex) {
            return builder;
        }
        AbsoluteSizeSpan secondSizeSpan = new AbsoluteSizeSpan(ScreenUtils.dpToPxInt( secondTextSize));
        builder.setSpan(secondSizeSpan, secondStartIndex, secondEndIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
