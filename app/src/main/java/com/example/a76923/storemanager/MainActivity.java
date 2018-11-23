package com.example.a76923.storemanager;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.a76923.storemanager.assistance.dbKindOP;
import com.example.a76923.storemanager.assistance.dbOPP;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    //UI Objects
    private TextView txt_topbar;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_quiry;
    private RadioButton rb_mine;
    private RadioButton rb_view;
    private ViewPager vpager;

    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    public static boolean isadmin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        rb_channel.setChecked(true);
    }

    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_quiry = (RadioButton) findViewById(R.id.rb_quiry);
        rb_mine = (RadioButton) findViewById(R.id.rb_mine);
        rb_view = (RadioButton) findViewById(R.id.rb_view);
        rg_tab_bar.setOnCheckedChangeListener(this);
        dbOPP x = dbOPP.getInstance(this);
        dbKindOP y = dbKindOP.getInstance(this);
        txt_topbar.setText(x.getWarn(x.getAnalysis(y.getKindList())));
        vpager = (ViewPager) findViewById(R.id.vpager);
        assert vpager != null;
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_channel:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_quiry:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_mine:
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.rb_view:
                vpager.setCurrentItem(PAGE_FOUR);
                break;
        }
    }


    //重写ViewPager页面切换的处理方法
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_channel.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_quiry.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_mine.setChecked(true);
                    break;
                case PAGE_FOUR:
                    rb_view.setChecked(true);
                    break;
            }
        }
    }
}