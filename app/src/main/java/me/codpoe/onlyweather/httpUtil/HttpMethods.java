package me.codpoe.onlyweather.httpUtil;

import android.content.Context;

import java.io.File;
import java.io.IOException;

import me.codpoe.onlyweather.base.BaseApplication;
import me.codpoe.onlyweather.model.entity.VersionBean;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import me.codpoe.onlyweather.util.NetUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Codpoe on 2016/5/12.
 */
public class HttpMethods {

    public static final String BASE_URL = "https://api.heweather.com/x3/";
    public static final String KEY = "08ee8103cc4d44a99ee8d0dc0dacd014";
    public static final String API_TOKEN = "9636021009f8a18fe1449dc7499ff2b4";

    private static HttpMethods sHttpMethods;
    private Context mContext;
    private Retrofit mRetrofit;
    private Api mApi;


    // 构造方法私有化
    private HttpMethods() {

        // 拦截器
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                if(!NetUtils.isConnected(BaseApplication.getAppContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }

                Response response = chain.proceed(request);

                if(NetUtils.isConnected(BaseApplication.getAppContext())) {
                    String cacheControl = request.cacheControl().toString();
                    return response.newBuilder()
                            .header("Cache-Control", cacheControl)
                            .removeHeader("Pragma")
                            .build();
                } else {
                    return response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                            .removeHeader("Pragma")
                            .build();
                }
            }
        };

        // Cache
        File cacheFile = new File(BaseApplication.getAppContext().getCacheDir(), "weatherCache");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(cacheFile, cacheSize);

        // OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(interceptor)
                .cache(cache)
                .build();


        // Retrofit
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mApi = mRetrofit.create(Api.class);
    }

    // 获取 HttpMethods 的单例
    public synchronized static HttpMethods getInstance() {
        if(sHttpMethods == null) {
            sHttpMethods = new HttpMethods();
        }
        return sHttpMethods;
    }

    // 获取天气数据
    public Observable<WeatherBean> getWeatherData(String cityName) {
        return mApi.getWeatherData(cityName, KEY);
    }

    // 获取天气数据
    public void getWeatherData(Subscriber<WeatherBean> subscriber, String city) {
        mApi.getWeatherData(city, KEY)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    // 获取版本号
    public void getVersionData(Subscriber<VersionBean> subscriber) {
        mApi.getVersionData(API_TOKEN)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
