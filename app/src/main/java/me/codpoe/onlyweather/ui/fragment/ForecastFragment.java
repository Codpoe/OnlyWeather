package me.codpoe.onlyweather.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.ui.adapter.ForecastRvAdapter;

/**
 * Created by Codpoe on 2016/5/13.
 */
public class ForecastFragment extends Fragment {

    private View view;

    private RecyclerView mMoreRecyclerView;
    private ForecastRvAdapter mForecastRvAdapter;

    private WeatherBean mWeatherData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_forecast, container, false);

        // 获取控件的引用
        mMoreRecyclerView = (RecyclerView) view.findViewById(R.id.main_forecast_rv);

        // RecyclerView
        mMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setWeatherData(WeatherBean weatherData) {
        mWeatherData = weatherData;
        mForecastRvAdapter = new ForecastRvAdapter(getContext(), mWeatherData);
        ScaleInAnimationAdapter scaleAdapter= new ScaleInAnimationAdapter(mForecastRvAdapter);
        scaleAdapter.setFirstOnly(false);
        mMoreRecyclerView.setAdapter(scaleAdapter);
    }
}
