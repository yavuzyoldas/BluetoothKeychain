package com.bluetoothkeychainapp54.bluetoothkeychain;


import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class PairActivity extends Activity {

    private ViewPager viewPager;
    private SlideAdapter myadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pair);
        viewPager = findViewById(R.id.viewpager);

        myadapter = new SlideAdapter(this);

        viewPager.setAdapter(myadapter);
    }
}
