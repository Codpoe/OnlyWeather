package me.codpoe.onlyweather.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.ui.adapter.BasicRvAdapter;
import me.codpoe.onlyweather.model.entity.WeatherBean;

/**
 * Created by Codpoe on 2016/5/13.
 */
public class BasicFragment extends Fragment{

    private View view;

    private RecyclerView mBasicRecyclerView;
    private BasicRvAdapter mBasicRvAdapter;
    private WeatherBean mWeatherData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_basic, container, false);

        // 获取控件的引用
        mBasicRecyclerView = (RecyclerView) view.findViewById(R.id.main_basic_rv);

        // RecyclerView
        mBasicRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    // 获取从 MainActivity 传来的 WeatherBean 数据，并给 RecyclerView 设置 Adapter
    public void setWeatherData(WeatherBean weatherData) {
        if (mWeatherData == null) {
            mWeatherData = weatherData;
            mBasicRvAdapter = new BasicRvAdapter(getContext(), mWeatherData);
            mBasicRecyclerView.setAdapter(mBasicRvAdapter);
        } else {
            mWeatherData = weatherData;
            mBasicRvAdapter.notifyDataSetChanged();
        }
    }

}
