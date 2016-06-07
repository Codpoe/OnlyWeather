package me.codpoe.onlyweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.util.Utils;

/**
 * Created by Codpoe on 2016/5/22.
 */
public class ForecastRvAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private WeatherBean mWeathrData;

    public ForecastRvAdapter(Context context, WeatherBean weatherBean) {
        mContext = context;
        mWeathrData = weatherBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ForecastViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.forecast_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            ((ForecastViewHolder) holder).mWeekText.setText(
                    String.format("%s",
                            Utils.dateToWeek(mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                                    .get(position).getDate()))
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        ((ForecastViewHolder) holder).mCondText.setText(
                String.format("%s", mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                        .get(position).getCond().getTxtD())
        );
        ((ForecastViewHolder) holder).mTmpText.setText(
                String.format("%s ~ %s", mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                                .get(position).getTmp().getMin(),
                        mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                                .get(position).getTmp().getMax())
        );

        ((ForecastViewHolder) holder).mHumText.setText(
                String.format("湿度: %s", mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                        .get(position).getHum())
        );
        ((ForecastViewHolder) holder).mWindText.setText(
                String.format("%s %s", mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                                .get(position).getWind().getDir(),
                        mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                                .get(position).getWind().getSc())
        );
        ((ForecastViewHolder) holder).mRainText.setText(
                String.format("降水概率: %s", mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                        .get(position).getPop())
        );
        ((ForecastViewHolder) holder).mVisText.setText(
                String.format("能见度: %s km", mWeathrData.getHeWeatherDataService().get(0).getDailyForecast()
                        .get(position).getVis())
        );
    }

    @Override
    public int getItemCount() {
        return mWeathrData.getHeWeatherDataService().get(0).getDailyForecast().size();
    }

    static class ForecastViewHolder extends RecyclerView.ViewHolder{

        private TextView mWeekText;
        private TextView mCondText;
        private TextView mTmpText;
        private TextView mHumText;
        private TextView mWindText;
        private TextView mRainText;
        private TextView mVisText;

        public ForecastViewHolder(View itemView) {
            super(itemView);
            mWeekText = (TextView) itemView.findViewById(R.id.forecast_date_text);
            mCondText = (TextView) itemView.findViewById(R.id.forecast_cond_text);
            mTmpText = (TextView) itemView.findViewById(R.id.forecast_tmp_text);
            mHumText = (TextView) itemView.findViewById(R.id.forecast_hum_text);
            mWindText = (TextView) itemView.findViewById(R.id.forecast_wind_text);
            mRainText = (TextView) itemView.findViewById(R.id.forecast_rain_text);
            mVisText = (TextView) itemView.findViewById(R.id.forecast_vis_text);
        }
    }

}
