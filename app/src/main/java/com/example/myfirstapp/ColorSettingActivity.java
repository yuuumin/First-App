package com.example.myfirstapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by akiyamayumi on 2018/11/30.
 */

public class ColorSettingActivity extends AppCompatActivity {

    private RadioGroup mRadioGroup;
    private int mCheckedID;
    private int mViewId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_setting);

        Intent intent = getIntent();
        mViewId = intent.getIntExtra("VIEW_ID", 0);

        mRadioGroup = (RadioGroup)findViewById(R.id.color_radio_group);
    }

    /**
     * メニュー表示
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.time_table_menu, menu);
        return true;
    }

    /**
     * 保存ボタン押下
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int mId = item.getItemId();

        if (mId == R.id.toolbar_storage) {
            mCheckedID = mRadioGroup.getCheckedRadioButtonId();

            if(mCheckedID != -1) {
                RadioButton mRadioButton = (RadioButton)findViewById(mCheckedID);
                mRadioButton.getBackground();
                int color = ((ColorDrawable)mRadioButton.getBackground()).getColor();
                Intent intent = new Intent(ColorSettingActivity.this, TimeTableSettingActivity.class);
                intent.putExtra("COLOR_VIEW_ID", color);
                intent.putExtra("VIEW_ID", mViewId);
                startActivity(intent);
            } else {
                Toast.makeText(this, Const.NOT_CHECK_COLOR, Toast.LENGTH_LONG).show();
            }
        }

        return true;
    }

}
