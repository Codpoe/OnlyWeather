package me.codpoe.onlyweather.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.ui.fragment.SettingFragment;

/**
 * Created by Codpoe on 2016/5/23.
 */
public class SettingActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar = (Toolbar) findViewById(R.id.setting_toolbar);

        // Toolbar
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getFragmentManager().beginTransaction()
                .replace(R.id.frame_lay, new SettingFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
