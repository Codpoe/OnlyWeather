package me.codpoe.onlyweather.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.model.entity.ConstellationBean;
import me.codpoe.onlyweather.model.entity.HuangLiBean;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.ui.adapter.BasicRvAdapter;

/**
 * Created by Codpoe on 2016/5/13.
 */
public class BasicFragment extends Fragment{

    private View view;

    private RecyclerView mBasicRecyclerView;
    private BasicRvAdapter mBasicRvAdapter;
    private WeatherBean mWeatherData;
    private HuangLiBean mHuangLiData;
    private ConstellationBean mConstellationData;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // 获取从 MainActivity 传来的 WeatherBean 数据，并给 RecyclerView 设置 Adapter
    public void setWeatherData(WeatherBean weatherData, HuangLiBean huangLiData, ConstellationBean constellationData) {
        mWeatherData = weatherData;
        mBasicRvAdapter = new BasicRvAdapter(getContext(), mWeatherData, huangLiData, constellationData);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mBasicRvAdapter);
        scaleAdapter.setFirstOnly(false);
        mBasicRecyclerView.setAdapter(scaleAdapter);
    }

}
