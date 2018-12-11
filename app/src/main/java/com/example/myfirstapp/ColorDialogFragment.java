package com.example.myfirstapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by akiyamayumi on 2018/12/06.
 */

public class ColorDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.dialog_color_setting, null);

        builder.setView(content);
        builder.setMessage(Const.COLOR_DIALOG_MESSAGE);
        builder.setPositiveButton(Const.COLOR_DIALOG_OK, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // 保存ボタン押下時の処理
            }
        });
        builder.setNegativeButton(Const.COLOR_DIALOG_CLOSE, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // 閉じるボタン押下時の処理
                // 何もせず閉じる
            }
        });
        return builder.create();
    }
}
