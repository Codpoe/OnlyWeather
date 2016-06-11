package me.codpoe.onlyweather.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.db.OnlyWeatherDB;
import me.codpoe.onlyweather.httpUtil.HttpMethods;
import me.codpoe.onlyweather.model.City;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.ui.adapter.ManageRvAdapter;
import me.codpoe.onlyweather.util.DialogUtils;
import me.codpoe.onlyweather.util.PrefsUtils;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ManageActivity extends AppCompatActivity implements View.OnClickListener,
        android.support.v7.widget.Toolbar.OnMenuItemClickListener {

    public static final String TAG = "ManageActivity";

    private CoordinatorLayout mCoordinatorLayout;
    private Toolbar mToolbar;
    private SwipeRefreshLayout mRefreshLay;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingBtn;
    private EditText mEditText;
    private ProgressDialog mProgressDialog;

    private OnlyWeatherDB db;

    private List<WeatherBean> mWeatherDataList = new ArrayList<>();
    private List<City> mCityList = new ArrayList<>();
    private WeatherBean mWeatherData;
    private ManageRvAdapter mRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        findViews();

        // Toolbar
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolbar.inflateMenu(R.menu.manage_help);
        mToolbar.setOnMenuItemClickListener(this);

        // RefreshLayout
        mRefreshLay.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_orange_dark,
                android.R.color.holo_purple,
                android.R.color.holo_green_dark);
        mRefreshLay.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(TAG, "refresh start");
                refreshData();
            }
        });

        // RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRvAdapter = new ManageRvAdapter(ManageActivity.this, mWeatherDataList);
        SlideInRightAnimationAdapter slideAdapter = new SlideInRightAnimationAdapter(mRvAdapter);
        slideAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(slideAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CodpoeItemTouchHelper());
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        mRecyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dx < 0) {
                    mFloatingBtn.show();
                } else if (dx > 0) {
                    mFloatingBtn.hide();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {


            }
        });


        // FloatingActionButton
        mFloatingBtn.setOnClickListener(this);

        // ProgressDialog
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("正在加载...");

        // 进入 ManageActivity 时做的事情
//        mProgressDialog.show();
        showWeatherFromPrefs();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mCityList.size() > 0) {
            saveShowCityToPrefs(mCityList.get(0).getCityName());
        } else {
            saveShowCityToPrefs("");
        }
        finish();
    }

    /**
     * 获取引用
     * 控件、数据
     */
    public void findViews() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.manage_coordinator_layout);
        mToolbar = (Toolbar) findViewById(R.id.manage_toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.manage_rv);
        mFloatingBtn = (FloatingActionButton) findViewById(R.id.manage_floating_btn);
        mRefreshLay = (SwipeRefreshLayout) findViewById(R.id.refresh_lay);

        db = OnlyWeatherDB.getInstance(this);

    }

    // 点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manage_floating_btn:
                // 唤起添加城市对话框
                View view = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog_add, null);
                mEditText = (EditText) view.findViewById(R.id.add_dialog_edit);
                new AlertDialog.Builder(ManageActivity.this)
                        .setView(view)
                        .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String string = mEditText.getText().toString();
                                if (!TextUtils.isEmpty(string)) {
                                    if (!isAdded(string)) {
                                        getWeather(string);
                                    }
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
        }
    }

    /**
     * 添加城市的流程 1
     * 判断输入的城市是否曾添加过
     * @param cityName
     * @return
     */
    public Boolean isAdded(String cityName) {
        if(mCityList != null && mCityList.size() > 0) {
            for(City c : mCityList) {
                if(c.getCityName().equals(cityName)) {
                    Snackbar.make(mCoordinatorLayout, "不必重复添加 " + cityName,
                            Snackbar.LENGTH_SHORT)
                            .show();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 添加城市的流程 2
     * 从网络获取单个城市的天气数据,并判断返回的 status 是否为 ok
     * @param cityName
     */
    public void getWeather(final String cityName) {
        mRefreshLay.setRefreshing(true);
        HttpMethods.getInstance().getWeatherData(new Subscriber<WeatherBean>() {
            @Override
            public void onCompleted() {
                mRefreshLay.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mRefreshLay.setRefreshing(false);
                Snackbar.make(mCoordinatorLayout, "添加 " + cityName + " 失败", Snackbar.LENGTH_SHORT)
                        .show();
                Log.d("ManageActivity", e.getMessage());
            }

            @Override
            public void onNext(WeatherBean weatherData) {
                mWeatherData = weatherData;
                String s = mWeatherData.getHeWeatherDataService().get(0).getStatus();
                if (s.equals("ok")) {
                    mWeatherDataList.add(mWeatherData);
                    mRvAdapter.notifyItemInserted(mWeatherDataList.size() - 1);
                    //mRvAdapter.addItem(mCityList.size(), mWeatherData);
                    City city = new City();
                    city.setCityName(cityName);
                    mCityList.add(city);
                    saveWeatherListToPrefs(mWeatherDataList);
                    Snackbar.make(mCoordinatorLayout, "添加 "
                                    + cityName + " 成功",
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else if (s.equals("unknown city")) {
                    Snackbar.make(mCoordinatorLayout, "未知城市: "
                                    + cityName,
                            Snackbar.LENGTH_SHORT)
                            .show();
                } else if (s.equals("anr")) {
                    Snackbar.make(mCoordinatorLayout, "服务无响应或超时",
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        }, cityName);
    }

    /**
     * 先从本地读取所有城市
     * 然后获取所有城市的天气数据
     * 再传进 Adapter
     */
    public void refreshData() {
        mWeatherDataList.clear();
        mRvAdapter.notifyDataSetChanged();
        Observable.from(mCityList)
                .concatMap(new Func1<City, Observable<WeatherBean>>() {
                    @Override
                    public Observable<WeatherBean> call(City city) {
                        return HttpMethods.getInstance().getWeatherData(city.getCityName());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherBean>() {
                    @Override
                    public void onCompleted() {
                        if (mWeatherDataList.size() > 0) {
                            saveWeatherListToPrefs(mWeatherDataList);
                            mRefreshLay.setRefreshing(false);
                            Snackbar.make(mCoordinatorLayout, "刷新成功", Snackbar.LENGTH_SHORT).show();
                        } else {
                            mRefreshLay.setRefreshing(false);
                            Snackbar.make(mCoordinatorLayout, "暂无数据，请先添加城市", Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRefreshLay.setRefreshing(false);
                        Snackbar.make(mCoordinatorLayout, "刷新失败，请检查网络配置", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeatherBean weatherBean) {
                        if (weatherBean != null) {
                            mWeatherDataList.add(weatherBean);
                            mRvAdapter.notifyItemInserted(mWeatherDataList.size() - 1);
                        }
                    }
                });
    }

    /**
     * 进入 ManageActivity 时
     * 先从本地 Prefs 读取天气数据，更新 weatherList 和 cityList
     * 再传进 Adaper
     * 并不是从网络刷新数据
     */
    public void showWeatherFromPrefs() {
        mCityList.clear();
        mWeatherDataList.clear();
        mRvAdapter.notifyDataSetChanged();

        Observable.create(new Observable.OnSubscribe<List<WeatherBean>>() {
            @Override
            public void call(Subscriber<? super List<WeatherBean>> subscriber) {
                Log.d(TAG, "showWeatherFromPrefs start");
                List<WeatherBean> list = getWeatherListFromPrefs();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        City city = new City();
                        city.setCityName(list.get(i).getHeWeatherDataService().get(0).getBasic().getCity());
                        mCityList.add(city);
                        Log.d(TAG, "show 0");
                    }
                }

                // 判断首页展示的天气是否已存在于【管理城市】中
                WeatherBean weatherBean = PrefsUtils.getShowWeatherFromPrefs(ManageActivity.this);
                City city = new City();
                city.setCityName(weatherBean.getHeWeatherDataService().get(0).getBasic().getCity());

                List<String> stringList = new ArrayList<String>();
                for (int i = 0; i < mCityList.size(); i ++) {
                    stringList.add(mCityList.get(i).getCityName());
                }

                if (list == null) {
                    list = new ArrayList<WeatherBean>();
                    list.add(weatherBean);
                    mCityList.add(city);
                } else if (!stringList.contains(city.getCityName())) {
                    list.add(PrefsUtils.getShowWeatherFromPrefs(ManageActivity.this));
                    mCityList.add(city);
                }
                subscriber.onNext(list);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<WeatherBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mProgressDialog.dismiss();
                        Snackbar.make(mCoordinatorLayout, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<WeatherBean> list) {
                        Log.d(TAG, "show 1");
                        if (list != null && list.size() > 0) {
                            mProgressDialog.dismiss();
                            mWeatherDataList.clear();
                            mRvAdapter.notifyDataSetChanged();
                            for (int i = 0; i < list.size(); i++) {
                                mWeatherDataList.add(list.get(i));
                                mRvAdapter.notifyItemInserted(mWeatherDataList.size() - 1);
                            }
                            Snackbar.make(mCoordinatorLayout, "加载完成", Snackbar.LENGTH_SHORT).show();
                        } else {
                            mProgressDialog.dismiss();
                            Snackbar.make(mCoordinatorLayout, "暂无数据，请先添加城市", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 把 WeatherList 对象转换成 JSON 形式的字符串，并存进 SharedPreferences
     */
    public void saveWeatherListToPrefs(List<WeatherBean> list) {
        Observable.just(list)
                .map(new Func1<List<WeatherBean>, Integer>() {
                    @Override
                    public Integer call(List<WeatherBean> weatherBeans) {
                        SharedPreferences.Editor editor = getSharedPreferences("weather_list_prefs", MODE_PRIVATE).edit();
                        String str = "";
                        Gson gson = new Gson();
                        str = gson.toJson(weatherBeans);
                        editor.putString("weather_list", str);
                        editor.commit();
                        return 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }
                });

    }

    /**
     * 从 SharedPreferences 读取字符串，并把字符串解析成 WeatherList 对象
     */
    public List<WeatherBean> getWeatherListFromPrefs() {
        Log.d(TAG, "get start");
        List<WeatherBean> list = new ArrayList<>();
        SharedPreferences prefs = getSharedPreferences("weather_list_prefs", MODE_PRIVATE);
        String str = prefs.getString("weather_list", "");
        if(str != null) {
            Gson gson = new Gson();
            list = gson.fromJson(str, new TypeToken<List<WeatherBean>>(){}.getType());
        }
        Log.d(TAG, "get end");
        return list;
    }

    /**
     * 保存首页要展示的城市到 Prefs
     * @param cityName
     */
    public void saveShowCityToPrefs(String cityName) {
        SharedPreferences.Editor editor = getSharedPreferences("show_city_prefs", MODE_PRIVATE).edit();
        editor.putString("show_city", cityName);
        editor.apply();
    }

    // Toolbar 上的菜单项 Toolbar menu item click
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manage_help:
                DialogUtils.showManageHelp(ManageActivity.this);
                break;
        }
        return false;
    }

    /**
     * 自定义 ItemTouchHelper.Callback
     * 长按 -> 拖拽
     * 左右滑动 -> 删除
     */
    class CodpoeItemTouchHelper extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlags;
            final int swipeFlags;

            if(recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                swipeFlags = 0;
            } else {
                dragFlags = ItemTouchHelper.UP |
                        ItemTouchHelper.DOWN;
                swipeFlags = ItemTouchHelper.START |
                        ItemTouchHelper.END;
            }
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int fromPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();

            if(fromPosition < toPosition) {
                for(int i = fromPosition; i < toPosition; i ++) {
                    Collections.swap(mWeatherDataList, i, i + 1);
                    Collections.swap(mCityList, i, i + 1);
                }
            } else {
                for(int i = fromPosition; i > toPosition; i --) {
                    Collections.swap(mWeatherDataList, i, i - 1);
                    Collections.swap(mCityList, i, i - 1);
                }
            }
            mRvAdapter.notifyItemMoved(fromPosition, toPosition);
            saveWeatherListToPrefs(mWeatherDataList);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            mWeatherDataList.remove(position);
            mRvAdapter.notifyItemRemoved(position);
            Snackbar.make(mCoordinatorLayout, "删除 " +mCityList.get(position).getCityName() + " 成功"
                            , Snackbar.LENGTH_SHORT)
                    .show();
            mCityList.remove(position);
            saveWeatherListToPrefs(mWeatherDataList);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if(actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    viewHolder.itemView.findViewById(R.id.manage_rv_item_card).setElevation(40f);
                } else {
                    viewHolder.itemView.findViewById(R.id.manage_rv_item_card).setBackgroundColor(Color.LTGRAY);
                }
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                viewHolder.itemView.findViewById(R.id.manage_rv_item_card).setElevation(6f);
            } else {
                viewHolder.itemView.findViewById(R.id.manage_rv_item_card).setBackgroundResource(R.color.cardview_light_background);
            }
        }
    }



}
