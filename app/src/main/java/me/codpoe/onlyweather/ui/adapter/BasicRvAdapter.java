package me.codpoe.onlyweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.util.DateToWeek;

/**
 * Created by Codpoe on 2016/5/13.
 */
public class BasicRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_ZERO = 0;
    public static final int TYPE_ONE = 1;
    public static  final int TYPE_TWO = 2;
    public static final String IMG_URL = "http://files.heweather.com/cond_icon/";

    private Context mContext;
    private WeatherBean mWeatherData;

    // 构造方法
    public BasicRvAdapter(Context context, WeatherBean weatherData) {
        mContext = context;
        mWeatherData = weatherData;
    }

    /**
     * 获取 item 的类型
     * @param position
     * @return item type
     */
    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_ZERO;
        }
        if(position == 1) {
            return TYPE_ONE;
        }
        if(position == 2) {
            return TYPE_TWO;
        }
        return super.getItemViewType(position);
    }

    /**
     * 创建 ViewHolder
     * @param parent
     * @param viewType
     * @return ViewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ZERO) {
            return new FutureWeatherViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.basic_item_0, parent, false));
        }
        if(viewType == TYPE_ONE) {
            return new NowWeatherViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.basic_item_1, parent, false));
        }
        if(viewType == TYPE_TWO) {
            return new SuggestionViewHolder(LayoutInflater.from(mContext)
                    .inflate(R.layout.basic_item_2, parent, false));
        }
        return null;
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof FutureWeatherViewHolder) {
//            try {
//                for(int i = 0; i < 5; i ++) {
//                    ((FutureWeatherViewHolder) holder).mDailyDateTexts[i].setText(
//                            DateToWeek.dateToWeek(mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(i).getDate()));
//                }
//            }catch (Exception e) {
//                e.printStackTrace();
//            }
//            for(int j = 0; j < 5; j ++) {
//                ImageView imageView = ((FutureWeatherViewHolder) holder).mDailyImgs[j];
//                Glide.with(mContext)
//                        .load("http://files.heweather.com/cond_icon/100.png")
//                        .into(imageView);
//
//                ((FutureWeatherViewHolder) holder).mDailyTmpTexts[j].setText(
//                        String.format("%s ~ %s", mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(j).getTmp().getMin(),
//                                mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(j).getTmp().getMax()));
//            }

            try {
                ((FutureWeatherViewHolder) holder).mDailyWeek0Text.setText(
                        DateToWeek.dateToWeek(mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(0).getDate())
                );
                ((FutureWeatherViewHolder) holder).mDailyWeek1Text.setText(
                        DateToWeek.dateToWeek(mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(1).getDate())
                );
                ((FutureWeatherViewHolder) holder).mDailyWeek2Text.setText(
                        DateToWeek.dateToWeek(mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(2).getDate())
                );
                ((FutureWeatherViewHolder) holder).mDailyWeek3Text.setText(
                        DateToWeek.dateToWeek(mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(3).getDate())
                );
                ((FutureWeatherViewHolder) holder).mDailyWeek4Text.setText(
                        DateToWeek.dateToWeek(mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(4).getDate())
                );
            } catch (Exception e) {
                e.printStackTrace();
            }


            Glide.with(mContext).load(IMG_URL
                    + mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(0).getCond().getCodeD()
                    + ".png")
                    .into(((FutureWeatherViewHolder) holder).mDaily0Img);
            Glide.with(mContext).load(IMG_URL
                    + mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(1).getCond().getCodeD()
                    + ".png")
                    .into(((FutureWeatherViewHolder) holder).mDaily1Img);
            Glide.with(mContext).load(IMG_URL
                    + mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(2).getCond().getCodeD()
                    + ".png")
                    .into(((FutureWeatherViewHolder) holder).mDaily2Img);
            Glide.with(mContext).load(IMG_URL
                    + mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(3).getCond().getCodeD()
                    + ".png")
                    .into(((FutureWeatherViewHolder) holder).mDaily3Img);
            Glide.with(mContext).load(IMG_URL
                    + mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(4).getCond().getCodeD()
                    + ".png")
                    .into(((FutureWeatherViewHolder) holder).mDaily4Img);


            ((FutureWeatherViewHolder) holder).mDailyTmp0Text.setText(
                    String.format("%s ~ %s", mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(0).getTmp().getMin(),
                            mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(0).getTmp().getMax())
            );
            ((FutureWeatherViewHolder) holder).mDailyTmp1Text.setText(
                    String.format("%s ~ %s", mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(1).getTmp().getMin(),
                            mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(1).getTmp().getMax())
            );
            ((FutureWeatherViewHolder) holder).mDailyTmp2Text.setText(
                    String.format("%s ~ %s", mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(2).getTmp().getMin(),
                            mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(2).getTmp().getMax())
            );
            ((FutureWeatherViewHolder) holder).mDailyTmp3Text.setText(
                    String.format("%s ~ %s", mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(3).getTmp().getMin(),
                            mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(3).getTmp().getMax())
            );
            ((FutureWeatherViewHolder) holder).mDailyTmp4Text.setText(
                    String.format("%s ~ %s", mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(4).getTmp().getMin(),
                            mWeatherData.getHeWeatherDataService().get(0).getDailyForecast().get(4).getTmp().getMax())
            );

        }

        if(holder instanceof NowWeatherViewHolder) {
            ((NowWeatherViewHolder) holder).mFlText.setText(
                    String.format("体感温度: %s", mWeatherData.getHeWeatherDataService().get(0).getNow().getFl()));

            ((NowWeatherViewHolder) holder).mWindText.setText(
                    String.format("%s %s", mWeatherData.getHeWeatherDataService().get(0).getNow().getWind().getDir(),
                            mWeatherData.getHeWeatherDataService().get(0).getNow().getWind().getSc()));

            if(mWeatherData.getHeWeatherDataService().get(0).getAqi() != null) {
                if(mWeatherData.getHeWeatherDataService().get(0).getAqi().getCity().getQlty() != null) {
                    ((NowWeatherViewHolder) holder).mQltyText.setText(
                            String.format("%s PM2.5: %s", mWeatherData.getHeWeatherDataService().get(0).getAqi().getCity().getQlty(),
                                    mWeatherData.getHeWeatherDataService().get(0).getAqi().getCity().getPm25()));
                } else {
                    ((NowWeatherViewHolder) holder).mQltyText.setText(
                            String.format("PM2.5: %s", mWeatherData.getHeWeatherDataService().get(0).getAqi().getCity().getPm25()));
                }
            }

            ((NowWeatherViewHolder) holder).mHumText.setText(
                    String.format("湿度: %s", mWeatherData.getHeWeatherDataService().get(0).getNow().getHum()));

            ((NowWeatherViewHolder) holder).mPcpnText.setText(
                    String.format("降雨量: %s mm", mWeatherData.getHeWeatherDataService().get(0).getNow().getPcpn()));

            ((NowWeatherViewHolder) holder).mVisText.setText(
                    String.format("能见度: %s km", mWeatherData.getHeWeatherDataService().get(0).getNow().getVis()));
        }

        if(holder instanceof SuggestionViewHolder) {
            ((SuggestionViewHolder) holder).mDrsgText.setText(
                    String.format("穿衣: %s", mWeatherData.getHeWeatherDataService().get(0).getSuggestion().getDrsg().getBrf()));

            ((SuggestionViewHolder) holder).mFluText.setText(
                    String.format("感冒: %s", mWeatherData.getHeWeatherDataService().get(0).getSuggestion().getFlu().getBrf()));

            ((SuggestionViewHolder) holder).mSportText.setText(
                    String.format("运动: %s", mWeatherData.getHeWeatherDataService().get(0).getSuggestion().getSport().getBrf()));

            ((SuggestionViewHolder) holder).mTravText.setText(
                    String.format("旅游: %s", mWeatherData.getHeWeatherDataService().get(0).getSuggestion().getTrav().getBrf()));

            ((SuggestionViewHolder) holder).mComfText.setText(
                    String.format("舒适度: %s", mWeatherData.getHeWeatherDataService().get(0).getSuggestion().getComf().getBrf()));

            ((SuggestionViewHolder) holder).mUvText.setText(
                    String.format("紫外线: %s", mWeatherData.getHeWeatherDataService().get(0).getSuggestion().getUv().getBrf()));
        }

    }

    /**
     * 获取 item 的数目
     * 判断 status 是否为空
     * 非空则返回 3，否则返回 0
     * @return item 的数目
     */
    @Override
    public int getItemCount() {
        return 3;
    }

    public void updateData(WeatherBean weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }

    /**
     * ViewHolder
     * 简略的未来几天的天气
     */
    class FutureWeatherViewHolder extends RecyclerView.ViewHolder {

//        private LinearLayout mLinearLayout;
//        private TextView[] mDailyDateTexts = new TextView[5];
//        private ImageView[] mDailyImgs = new ImageView[5];
//        private TextView[] mDailyTmpTexts = new TextView[5];

        private TextView mDailyWeek0Text;
        private TextView mDailyWeek1Text;
        private TextView mDailyWeek2Text;
        private TextView mDailyWeek3Text;
        private TextView mDailyWeek4Text;

        private ImageView mDaily0Img;
        private ImageView mDaily1Img;
        private ImageView mDaily2Img;
        private ImageView mDaily3Img;
        private ImageView mDaily4Img;

        private TextView mDailyTmp0Text;
        private TextView mDailyTmp1Text;
        private TextView mDailyTmp2Text;
        private TextView mDailyTmp3Text;
        private TextView mDailyTmp4Text;

        public FutureWeatherViewHolder(View itemView) {
            super(itemView);

            mDailyWeek0Text = (TextView) itemView.findViewById(R.id.daily_week_0_text);
            mDailyWeek1Text = (TextView) itemView.findViewById(R.id.daily_week_1_text);
            mDailyWeek2Text = (TextView) itemView.findViewById(R.id.daily_week_2_text);
            mDailyWeek3Text = (TextView) itemView.findViewById(R.id.daily_week_3_text);
            mDailyWeek4Text = (TextView) itemView.findViewById(R.id.daily_week_4_text);

            mDaily0Img = (ImageView) itemView.findViewById(R.id.daily_0_img);
            mDaily1Img = (ImageView) itemView.findViewById(R.id.daily_1_img);
            mDaily2Img = (ImageView) itemView.findViewById(R.id.daily_2_img);
            mDaily3Img = (ImageView) itemView.findViewById(R.id.daily_3_img);
            mDaily4Img = (ImageView) itemView.findViewById(R.id.daily_4_img);

            mDailyTmp0Text = (TextView) itemView.findViewById(R.id.daily_tmp_0_text);
            mDailyTmp1Text = (TextView) itemView.findViewById(R.id.daily_tmp_1_text);
            mDailyTmp2Text = (TextView) itemView.findViewById(R.id.daily_tmp_2_text);
            mDailyTmp3Text = (TextView) itemView.findViewById(R.id.daily_tmp_3_text);
            mDailyTmp4Text = (TextView) itemView.findViewById(R.id.daily_tmp_4_text);



//            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.daily_linear_layout);
//            for(int i = 0; i < 5; i ++) {
//                View view = View.inflate(mContext, R.layout.basic_item_0_line, null);
//                mDailyDateTexts[i] = (TextView) itemView.findViewById(R.id.daily_date_text);
//                mDailyImgs[i] = (ImageView) itemView.findViewById(R.id.daily_img);
//                mDailyTmpTexts[i] = (TextView) itemView.findViewById(R.id.daily_tmp_text);
//                mLinearLayout.addView(view);
//            }
        }
    }

    /**
     * ViewHolder
     * 实时信息
     */
    class NowWeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView mFlText;
        private TextView mWindText;
        private TextView mQltyText;
        private TextView mHumText;
        private TextView mPcpnText;
        private TextView mVisText;

        public NowWeatherViewHolder(View itemView) {
            super(itemView);
            mFlText = (TextView) itemView.findViewById(R.id.fl_text);
            mWindText = (TextView) itemView.findViewById(R.id.wind_text);
            mQltyText = (TextView) itemView.findViewById(R.id.qlty_text);
            mHumText = (TextView) itemView.findViewById(R.id.hum_text);
            mPcpnText = (TextView) itemView.findViewById(R.id.pcpn_text);
            mVisText = (TextView) itemView.findViewById(R.id.vis_text);
        }
    }

    /**
     * ViewHolder
     * 生活指数
     */
    class SuggestionViewHolder extends RecyclerView.ViewHolder {

        private TextView mDrsgText;
        private TextView mFluText;
        private TextView mSportText;
        private TextView mTravText;
        private TextView mComfText;
        private TextView mUvText;

        public SuggestionViewHolder(View itemView) {
            super(itemView);
            mDrsgText = (TextView) itemView.findViewById(R.id.drsg_text);
            mFluText = (TextView) itemView.findViewById(R.id.flu_text);
            mSportText = (TextView) itemView.findViewById(R.id.sport_text);
            mTravText = (TextView) itemView.findViewById(R.id.trav_text);
            mComfText = (TextView) itemView.findViewById(R.id.comf_text);
            mUvText = (TextView) itemView.findViewById(R.id.uv_text);
        }
    }

    /**
     * 点击接口
     * 单击、长按
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
