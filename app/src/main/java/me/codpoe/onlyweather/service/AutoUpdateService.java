package me.codpoe.onlyweather.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.base.BusProvider;
import me.codpoe.onlyweather.httpUtil.HttpMethods;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.ui.activity.MainActivity;
import me.codpoe.onlyweather.util.SettingUtils;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Codpoe on 2016/5/24.
 */
public class AutoUpdateService extends Service{

    private CompositeSubscription mCompositeSubscription;
    private Subscription mSubscription;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        synchronized (this) {
            mCompositeSubscription.remove(mSubscription);
            Log.d("AutoUpdateService", "work?");
            if(SettingUtils.getInstance().getIsAutoUpdate()) {
                Log.d("AutoUpdateService", "what?");
                if(SettingUtils.getInstance().getAutoUpdate() != 0) {
                    Log.d("AutoUpdateService", "really work?");
                    mSubscription = Observable.interval(SettingUtils.getInstance().getAutoUpdate(), TimeUnit.HOURS)
                            .subscribe(new Action1<Long>() {
                                @Override
                                public void call(Long aLong) {
                                    Log.d("AutoUpdateService", "work!");
                                    getWeather();
                                }
                            });
                    mCompositeSubscription.add(mSubscription);
                }
            }
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 获取天气数据
     */
    public void getWeather() {
        SharedPreferences prefs = getSharedPreferences("show_city_prefs", MODE_PRIVATE);
        String cityName = prefs.getString("show_city", "");
        if(!cityName.equals("")) {
            HttpMethods.getInstance().getWeatherData(new Subscriber<WeatherBean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.d("AutoUpdateService", e.getMessage());
                }

                @Override
                public void onNext(WeatherBean weatherBean) {
                    BusProvider.getInstance().post(weatherBean);
                }
            }, cityName);
        }
    }

    /**
     * 通知栏天气
     */
    public void showNotification(WeatherBean weatherBean) {
        Intent intent = new Intent(AutoUpdateService.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(AutoUpdateService.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(AutoUpdateService.this);
        Notification notification = builder.setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(weatherBean.getHeWeatherDataService().get(0).getBasic().getCity())
                .setContentText(String.format("%s  体感温度: %s ℃  PM2.5: %s",
                        weatherBean.getHeWeatherDataService().get(0).getNow().getCond().getTxt(),
                        weatherBean.getHeWeatherDataService().get(0).getNow().getFl(),
                        weatherBean.getHeWeatherDataService().get(0).getAqi().getCity().getPm25()))
                .build();
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, notification);
    }


}
