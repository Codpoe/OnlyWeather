package me.codpoe.onlyweather.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.ui.fragment.AboutFragment;

/**
 * Created by Codpoe on 2016/5/23.
 */
public class AboutActivity extends AppCompatActivity{

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViews();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getFragmentManager().beginTransaction()
                .replace(R.id.about_frame, new AboutFragment())
                .commit();

    }

    /**
     * 获取控件引用
     */
    public void findViews() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.about_coor_lay);
        mToolbar = (Toolbar) findViewById(R.id.about_toolbar);
    }
}
