package me.codpoe.onlyweather.httpUtil;

import me.codpoe.onlyweather.model.entity.VersionBean;
import me.codpoe.onlyweather.model.entity.WeatherBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Codpoe on 2016/5/12.
 */
public interface Api {
    @GET("weather")
    Observable<WeatherBean> getWeatherData(@Query("city") String city, @Query("key") String key);

    @GET("weather")
    Observable<WeatherBean> getWeatherData2(@Query("city") String city, @Query("key") String key);

    @GET("http://api.fir.im/apps/latest/57470049e75e2d632c000022")
    Observable<VersionBean> getVersionData(@Query("api_token") String apiToken);
}
