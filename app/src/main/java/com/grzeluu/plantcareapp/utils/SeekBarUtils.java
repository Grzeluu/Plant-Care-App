package com.grzeluu.plantcareapp.utils;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.grzeluu.plantcareapp.R;

public class SeekBarUtils {
    public static void initSeekBarGroupWithText(Context context, SeekBar seekBar,
                                                TextView textView, View buttonPlus,
                                                View buttonMinus, int initialProgress) {

        SeekBar.OnSeekBarChangeListener listener =
                createProgressBarChangeListener(context, textView);

        seekBar.setOnSeekBarChangeListener(listener);
        seekBar.setProgress(initialProgress);

        initButtons(seekBar, buttonPlus, buttonMinus);
    }

    private static void initButtons(SeekBar seekBar, View buttonPlus, View buttonMinus) {
        buttonMinus.setOnClickListener(v -> {
            seekBar.setProgress(seekBar.getProgress() - 1);
        });

        buttonPlus.setOnClickListener(v -> {
            seekBar.setProgress(seekBar.getProgress() + 1);
        });
    }

    private static SeekBar.OnSeekBarChangeListener createProgressBarChangeListener(Context context, TextView tv) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (progress == 0) {
                    tv.setText(context.getString(R.string.never));
                } else if (progress == 1) {
                    tv.setText(context.getString(R.string.one_day));
                } else if (progress > 1 && progress <= 30) {
                    tv.setText(context.getString(R.string.days, progress));
                } else if (progress > 30 && progress <= 35) {
                    int days = (30 + (progress - 30) * 5);
                    tv.setText(context.getString(R.string.days, days));
                } else {
                    int months = (progress - 34);
                    tv.setText(context.getString(R.string.months, months));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
    }
}
