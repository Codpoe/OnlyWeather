package me.codpoe.onlyweather.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import me.codpoe.onlyweather.base.BaseApplication;
import me.codpoe.onlyweather.httpUtil.HttpMethods;
import me.codpoe.onlyweather.model.entity.VersionBean;
import rx.Subscriber;

/**
 * Created by Codpoe on 2016/5/26.
 */
public class VersionUtils {

    // 获取当前的版本号
    public static String getCurVersion(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "找不到版本号";
        }
    }

    // 比较版本号
    public static void checkVersion(final Context context) {
        HttpMethods.getInstance().getVersionData(new Subscriber<VersionBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(BaseApplication.getAppContext(),
                                "出现问题了(╯﹏╰)",
                                Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onNext(VersionBean versionBean) {
                String newVersion = versionBean.getVersionShort();
                if (versionBean.getVersionShort().compareTo(getCurVersion(context)) > 0) {
                    showNewVersion(context, versionBean);
                } else {
                    Toast.makeText(BaseApplication.getAppContext(),
                                    "已经是最新版本",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    // 比较版本号，但如果已经是最新版本，则无反馈 Toast
    public static void checkVersionNo(final Context context) {
        HttpMethods.getInstance().getVersionData(new Subscriber<VersionBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(VersionBean versionBean) {
                String newVersion = versionBean.getVersionShort();
                if (versionBean.getVersionShort().compareTo(getCurVersion(context)) > 0) {
                    showNewVersion(context, versionBean);
                }
            }
        });
    }

    // 展示新版本的相关信息
    public static void showNewVersion(final Context context, final VersionBean versionBean) {
        new AlertDialog.Builder(context)
                .setTitle("发现新版本")
                .setMessage("最新版本: " + versionBean.getVersionShort() + "\n" +
                            "新版本大小: " + versionBean.getBinary().getFsize() / 1024 / 1024 + " M\n\n" +
                            versionBean.getChangelog())
                .setPositiveButton("下载", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Uri uri = Uri.parse(versionBean.getUpdateUrl());
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("以后再说", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
