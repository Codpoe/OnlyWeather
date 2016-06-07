package me.codpoe.onlyweather.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import me.codpoe.onlyweather.R;

/**
 * Created by Codpoe on 2016/5/26.
 */
public class DialogUtils {

    // 帮助 ManageActivity help
    public static void showManageHelp(Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("帮助")
                .setMessage("- 长按 可以拖拽天气卡片，并改变其位置。\n\n" +
                        "- 左右滑动 可以删除天气卡片。\n\n" +
                        "- 排在第一位的城市，将会在首页中展示。")
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    public static void showSettingHelp(Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("帮助")
                .setMessage("- 显示通知栏天气\n" +
                        "- 添加或关闭卡片\n\n" +
                        "以上操作都需要在首页下拉刷新才能看到效果")
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    // 介绍 introduction dialog
    public static void showIntroduction(Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("仅仅天气")
                .setMessage("顾名思义，这款天气应用只能看天气，不能看广告（逃...\n\n" +
                        "写这个小小小的天气应用，只是为了学习、练手 -_-|| 但由于本人目前的开发姿势不高，" +
                        "所以投入了大量的课余时间，" +
                        "希望能有喜欢它的人。\n\n" +
                        "当然，如果你有任何意见或者发现 BUG，欢迎在 Github 项目地址提 Issue，或者通过微博和邮件联系我。\n\n" +
                        "最后，感谢你的下载 :-D")
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    // 点赞 star dialog
    public static void showStar(final Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("点赞")
                .setMessage("点击 确定，将会跳转到 仅仅天气 的项目地址。")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // do something here
                        Uri uri = Uri.parse("https://github.com/Codpoe/OnlyWeather");
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    // 关于我 me
    public static void showMe(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.about_me, null);
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    // 支持 support dialog
    public static void showSupport(final Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("支持")
                .setMessage("如果你觉得这款小小的 App 不错、还行、甚至不太差的话，可以请我喝杯茶>_<（即系打赏）")
                .setPositiveButton("复制账号并打开支付宝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 复制账号
                        ClipboardManager clipboardManager = (ClipboardManager)
                                context.getSystemService(Context.CLIPBOARD_SERVICE);
                        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, "847234974@qq.com"));
                        // 打开支付宝
                        try {
                            Intent intent = context.getPackageManager()
                                    .getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                            context.startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(context, "可能你没有安装支付宝，或者出现了别的问题，但还是谢谢你的好意^_^",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    // 数据来源
    public static void showDataSource(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_source, null);
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    // 开源库 open source dialog
    public static void showOpenSource(Context context) {
        new AlertDialog.Builder(context)
                .setCancelable(true)
                .setTitle("感谢以下开源项目的支持")
                .setMessage("Google Design Support Library\n" +
                        "RxJava\n" +
                        "RxAndroid\n" +
                        "Retrofit\n" +
                        "Glide\n" +
                        "Otto\n" +
                        "AndroidViewAnimations\n" +
                        "recyclerview-animators\n")
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

}
