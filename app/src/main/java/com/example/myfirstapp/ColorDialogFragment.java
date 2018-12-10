package com.example.myfirstapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by akiyamayumi on 2018/12/06.
 */

public class ColorDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        int mColor = 0;

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.dialog_color_setting, null);
        final SeekBar mRedSeekBar = content.findViewById(R.id.color_bar_R);
        SeekBar mGreenSeekBar = content.findViewById(R.id.color_bar_G);
        SeekBar mBlueSeekBar = content.findViewById(R.id.color_bar_B);

        mRedSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int R = seekBar.getProgress();
                    }
                }
        );

        mGreenSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int G = seekBar.getProgress();
                    }
                }
        );

        mBlueSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        int B = seekBar.getProgress();
                    }
                }
        );


        builder.setView(content);

        builder.setMessage(Const.COLOR_DIALOG_MESSAGE);
        builder.setPositiveButton(Const.COLOR_DIALOG_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
//                int mColor = Color.rgb(R, G, B);
            }
        });
        builder.setNegativeButton(Const.COLOR_DIALOG_CLOSE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
