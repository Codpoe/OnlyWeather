package me.codpoe.onlyweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.model.entity.WeatherBean;

/**
 * Created by Codpoe on 2016/5/15.
 */
public class ManageRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String IMG_URL = "http://files.heweather.com/cond_icon/";

    private Context mContext;
    private List<WeatherBean> mWeatherDataList = new ArrayList<>();

    private OnItemClickListener mOnItemClickListener;

    // 构造方法
    public ManageRvAdapter(Context context, List<WeatherBean> weatherBeans) {
        mContext = context;
        mWeatherDataList = weatherBeans;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManageItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.manage_rv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Glide.with(mContext)
                .load(IMG_URL + mWeatherDataList.get(position).getHeWeatherDataService().get(0).getNow().getCond().getCode() + ".png")
                .into(((ManageItemViewHolder) holder).mItemImg);

        ((ManageItemViewHolder)holder).mItemCityText.setText(
                mWeatherDataList.get(position).getHeWeatherDataService().get(0).getBasic().getCity()
        );

        ((ManageItemViewHolder)holder).mItemTmpCondText.setText(
                String.format("%s ℃ %s",
                        mWeatherDataList.get(position).getHeWeatherDataService().get(0).getNow().getTmp(),
                        mWeatherDataList.get(position).getHeWeatherDataService().get(0).getNow().getCond().getTxt())
        );

        // 如果设置了回调，则设置点击事件
        if(mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mWeatherDataList.size();
    }

    /**
     * 更新数据
     * @param weatherDataList
     */
    public void updateData(List<WeatherBean> weatherDataList) {
        mWeatherDataList = weatherDataList;
        notifyDataSetChanged();
    }

    /**
     * 增加item
     * @param position
     * @param weatherBean
     */
    public void addItem(int position, WeatherBean weatherBean) {
        mWeatherDataList.add(weatherBean);
        notifyItemInserted(position);
    }

    /**
     * 移除 item
     * @param position
     */
    public void removeItem(int position) {
        mWeatherDataList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAllItem() {
        mWeatherDataList.clear();
        notifyDataSetChanged();
    }

    // 接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // 点击接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    // ViewHolder
    class ManageItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImg;
        private TextView mItemCityText;
        private TextView mItemTmpCondText;

        public ManageItemViewHolder(View view) {
            super(view);
            mItemImg = (ImageView) view.findViewById(R.id.manage_item_img);
            mItemCityText = (TextView) view.findViewById(R.id.manage_item_city_text);
            mItemTmpCondText = (TextView) view.findViewById(R.id.manage_item_tmp_cond_text);
        }
    }
}
