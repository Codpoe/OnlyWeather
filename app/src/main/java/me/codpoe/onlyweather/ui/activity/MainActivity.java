package me.codpoe.onlyweather.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.base.BusProvider;
import me.codpoe.onlyweather.httpUtil.HttpMethods;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.service.AutoUpdateService;
import me.codpoe.onlyweather.ui.adapter.MainFragmentAdapter;
import me.codpoe.onlyweather.ui.fragment.BasicFragment;
import me.codpoe.onlyweather.ui.fragment.MoreFragment;
import me.codpoe.onlyweather.util.ScreenShotUtils;
import me.codpoe.onlyweather.util.VersionUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Codpoe on 2016/5/11.
 */
public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        ViewPager.OnTouchListener,
        Toolbar.OnMenuItemClickListener {

    private DrawerLayout mMainDrawerLayout;
    private SwipeRefreshLayout mRefreshLayout;
    private CoordinatorLayout mCoordinatorLayout;
    private NavigationView mNavigationView;
    private CollapsingToolbarLayout mMainCollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private Toolbar mMainToolbar;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ImageView mMainImg;
    private TextView mMainTmpText;
    private TextView mMainCondText;
    private FloatingActionButton mFloatingBtn;
    private TabLayout mMainTabLayout;
    private ViewPager mMainViewPager;
    private ProgressDialog mProgressDialog;

    private List<String> tabList;
    private List<Fragment> fragmentList;
    private BasicFragment mBasicFragment;
    private MoreFragment mMoreFragment;
    private MainFragmentAdapter mMainFragmentAdapter;

    private WeatherBean mWeatherData;

    private boolean mIsExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取引用
        findViews();

        // Otto
        BusProvider.getInstance().register(this);

        startService(new Intent(MainActivity.this, AutoUpdateService.class));

        // ActionBarDrawerToggle
        mActionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, mMainDrawerLayout, mMainToolbar,
                                                    R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mActionBarDrawerToggle.syncState();
        mMainDrawerLayout.addDrawerListener(mActionBarDrawerToggle);

        // Toolbar
        mMainToolbar.inflateMenu(R.menu.main_menu);
        mMainToolbar.setOnMenuItemClickListener(this);

        // TabLayout and ViewPager
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();

        tabList.add("一览");
        tabList.add("更多");
        fragmentList.add(mBasicFragment);
        fragmentList.add(mMoreFragment);

        mMainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), tabList, fragmentList);
        mMainViewPager.setAdapter(mMainFragmentAdapter);
        mMainTabLayout.addTab(mMainTabLayout.newTab().setText(tabList.get(0)));
        mMainTabLayout.addTab(mMainTabLayout.newTab().setText(tabList.get(1)));
        mMainTabLayout.setupWithViewPager(mMainViewPager);

        mMainViewPager.setOnTouchListener(this);

        // RefreshLayout
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark);
        mRefreshLayout.setProgressViewOffset(true, 20, 100);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeather();
            }
        });

        /**
         * CollapsingToolbar未折叠时，可刷新
         * 折叠后，不可刷新
         */
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                    mRefreshLayout.setEnabled(true);
                } else {
                    mRefreshLayout.setEnabled(false);
                }
            }
        });

        // FloatingActionButton
        mFloatingBtn.setOnClickListener(this);

        // NavigationView
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_home:
                        mMainDrawerLayout.closeDrawers();
                        break;
                    case R.id.drawer_manager:
                        Intent intent = new Intent(MainActivity.this, ManageActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.drawer_setting:
                        Intent intent2 = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.drawer_about:
                        Intent intent3 = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent3);
                }
                return false;
            }
        });

        firstShow();
        VersionUtils.checkVersionNo(MainActivity.this);

    }

    // 双击退出
    @Override
    public void onBackPressed() {
        if (mIsExit) {
            super.onBackPressed();
        } else {
            mIsExit = true;
            Snackbar.make(mCoordinatorLayout, "再按一次退出", Snackbar.LENGTH_SHORT).show();
            Observable.timer(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            mIsExit = false;
                        }
                    });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    // 获取引用
    public void findViews() {
        mMainDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_refresh_lay);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_lay);
        mNavigationView = (NavigationView) findViewById(R.id.drawer_nv);
        mMainCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.main_collapsing_toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.main_app_bar);
        mMainToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        mMainImg = (ImageView) findViewById(R.id.main_img);
        mMainTmpText = (TextView) findViewById(R.id.main_tmp_text);
        mMainCondText = (TextView) findViewById(R.id.main_cond_text);
        mFloatingBtn = (FloatingActionButton) findViewById(R.id.floating_btn);
        mMainTabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        mMainViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        mBasicFragment = new BasicFragment();
        mMoreFragment = new MoreFragment();
    }

    // menu 点击接口
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
//                share("", "我正在使用『仅仅天气』，下载地址:http://fir.im/onlyweather");
                share();
                break;
        }
        return false;
    }

    // 普通点击接口
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击 FloatingActionButton
            case R.id.floating_btn:
                share();
                break;
        }
    }

    /**
     * 从网络获取天气数据并更新 UI
     */
    public void getWeather() {
        String cityName = getShowCityFromPrefs();
        if(!cityName.equals("")) {
            HttpMethods.getInstance().getWeatherData(new Subscriber<WeatherBean>() {
                @Override
                public void onCompleted() {
                    mProgressDialog.dismiss();
                    mRefreshLayout.setRefreshing(false);
                    Snackbar.make(mCoordinatorLayout, " 刷新成功", Snackbar.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    mProgressDialog.dismiss();
                    mRefreshLayout.setRefreshing(false);
                    Snackbar.make(mCoordinatorLayout, "刷新失败，请检查网络是否通畅，或者重启应用", Snackbar.LENGTH_SHORT).show();
                    Log.d("MainActivity", e.getMessage());
                }

                @Override
                public void onNext(WeatherBean weatherData) {
                    mWeatherData = weatherData;
                    mMainCollapsingToolbarLayout.setTitle(mWeatherData.getHeWeatherDataService().get(0).getBasic().getCity());
                    mMainTmpText.setText(mWeatherData.getHeWeatherDataService().get(0).getNow().getTmp());
                    mMainCondText.setText(mWeatherData.getHeWeatherDataService().get(0).getNow().getCond().getTxt());
                    mBasicFragment.setWeatherData(mWeatherData);
                    mMoreFragment.setWeatherData(mWeatherData);
                    setBackgroud(mWeatherData);
                }
            }, cityName);
        } else {
            mRefreshLayout.setRefreshing(false);
            Snackbar.make(mCoordinatorLayout, "暂无数据，请先添加城市", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * 后台自动更新天气
     * @param weatherBean
     */
    @Subscribe
    public void getWeatherFromService(WeatherBean weatherBean) {
        Log.d("MainActivity", "auto update really work!");
        mMainCollapsingToolbarLayout.setTitle(weatherBean.getHeWeatherDataService().get(0).getBasic().getCity());
        mMainTmpText.setText(weatherBean.getHeWeatherDataService().get(0).getNow().getTmp());
        mMainCondText.setText(weatherBean.getHeWeatherDataService().get(0).getNow().getCond().getTxt());
        mBasicFragment.setWeatherData(weatherBean);
        mMoreFragment.setWeatherData(weatherBean);
        setBackgroud(weatherBean);
    }

    /**
     * 进入 MainActivity 时，从本地获取并展示天气数据
     */
    public void firstShow() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("正在加载...");
        mProgressDialog.show();

        if(!getShowCityFromPrefs().equals("")) {
            getWeather();
        } else {
            mProgressDialog.dismiss();
            Snackbar.make(mCoordinatorLayout, "暂无数据，请先添加城市", Snackbar.LENGTH_SHORT);
        }
    }

    /**
     * 判断当前时间和天气状况，并据此更换背景
     */
    public void setBackgroud(WeatherBean weatherData) {
        Calendar c = Calendar.getInstance();
        String cond = weatherData.getHeWeatherDataService().get(0).getNow().getCond().getTxt();

        if (cond.contains("雨")) {
            mMainImg.setImageResource(R.drawable.night_rain);
        } else if (cond.contains("雪")) {
            mMainImg.setImageResource(R.drawable.snow);
        } else if (c.get(Calendar.HOUR_OF_DAY) >= 18 || c.get(Calendar.HOUR_OF_DAY) < 6) {
            mMainImg.setImageResource(R.drawable.night_fine);
        } else {
            mMainImg.setImageResource(R.drawable.morning);
        }

    }

    /**
     * 获取已保存的首页展示的城市
     * @return
     */
    public String getShowCityFromPrefs() {
        SharedPreferences prefs = getSharedPreferences("show_city_prefs", MODE_PRIVATE);
        return prefs.getString("show_city", "");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mRefreshLayout.setEnabled(false);
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_CANCEL:
                mRefreshLayout.setEnabled(true);
                break;
        }
        return false;
    }

    /**
     * 截图，并分享
     */
    public void share() {
        Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File file = new File(ScreenShotUtils.shot(MainActivity.this));
                subscriber.onNext(file);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        if (file != null && file.exists() && file.isFile()) {
                            intent.setType("image/png");
                            Uri uri = Uri.fromFile(file);
                            intent.putExtra(Intent.EXTRA_STREAM, uri);
                            Log.d("MainActivity", "share_2");
                            startActivity(Intent.createChooser(intent, "分享"));
                        }
                    }
                    
                });
    }

}
