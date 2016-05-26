package me.codpoe.onlyweather.httpUtil;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Codpoe on 2016/5/18.
 */
public class CodpoeOkHttpClient {
    private Interceptor mInterceptor;
    private static OkHttpClient mOkHttpClient;

    /**
     * 创建拦截器
     */
    private CodpoeOkHttpClient() {
        mInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

                String cacheControl = request.cacheControl().toString();
                if(TextUtils.isEmpty(cacheControl)) {
                    cacheControl = "public, max-age=60";
                }
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            }
        };

        File cacheDirectory = new File("cache");
        int cacheSize = 10 * 1024 *1024;
        Cache cache = new Cache(cacheDirectory, cacheSize);

        mOkHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(mInterceptor)
                .cache(cache)
                .build();
    }


}
