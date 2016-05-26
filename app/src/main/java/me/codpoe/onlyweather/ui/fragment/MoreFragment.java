package me.codpoe.onlyweather.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.ui.adapter.MoreRvAdapter;
import me.codpoe.onlyweather.model.entity.WeatherBean;

/**
 * Created by Codpoe on 2016/5/13.
 */
public class MoreFragment extends Fragment {

    private View view;

    private RecyclerView mMoreRecyclerView;
    private MoreRvAdapter mMoreRvAdapter;

    private WeatherBean mWeatherData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_more, container, false);

        // 获取控件的引用
        mMoreRecyclerView = (RecyclerView) view.findViewById(R.id.main_more_rv);

        // RecyclerView
        mMoreRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    public void setWeatherData(WeatherBean weatherData) {
        if (mWeatherData == null) {
            mWeatherData = weatherData;
            mMoreRvAdapter = new MoreRvAdapter(getContext(), mWeatherData);
            mMoreRecyclerView.setAdapter(mMoreRvAdapter);
        } else {
            mWeatherData = weatherData;
            mMoreRvAdapter.notifyDataSetChanged();
        }
    }
}
