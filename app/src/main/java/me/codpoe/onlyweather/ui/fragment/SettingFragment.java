package me.codpoe.onlyweather.ui.fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.service.AutoUpdateService;
import me.codpoe.onlyweather.util.SettingUtils;

/**
 * Created by Codpoe on 2016/5/23.
 */
public class SettingFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    private CheckBoxPreference mIsAutoUpdate;
    private ListPreference mChangeUpdate;
    private CheckBoxPreference mIsShowNotification;
    private CheckBoxPreference mIsShowHuangLi;
    private CheckBoxPreference mIsShowCons;
    private ListPreference mSelectedCons;

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        Log.d("setting", "0");
        mIsAutoUpdate = (CheckBoxPreference) findPreference("is_auto_update");
        mChangeUpdate = (ListPreference) findPreference("auto_update");
        mIsShowNotification = (CheckBoxPreference) findPreference("is_show_notification");
        mIsShowHuangLi = (CheckBoxPreference) findPreference("is_show_huang_li");
        mIsShowCons = (CheckBoxPreference) findPreference("is_show_cons");
        mSelectedCons = (ListPreference) findPreference("selected_cons");

        mIsAutoUpdate.setOnPreferenceChangeListener(this);

        mChangeUpdate.setSummary(SettingUtils.getInstance().getAutoUpdate() == 0 ? "从不自动更新"
                : "每 " + SettingUtils.getInstance().getAutoUpdate() + " 小时");
        Log.d("setting", "1");
        mChangeUpdate.setOnPreferenceChangeListener(this);

        mIsShowNotification.setOnPreferenceChangeListener(this);

        mIsShowHuangLi.setOnPreferenceChangeListener(this);

        mIsShowCons.setOnPreferenceChangeListener(this);
        mSelectedCons.setSummary(SettingUtils.getInstance().getSelectedCons());
        mSelectedCons.setOnPreferenceChangeListener(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        switch (preference.getKey()) {
            case "is_auto_update":
                SettingUtils.getInstance().setIsAutoUpdate(Boolean.parseBoolean(String.valueOf(newValue)));
                getActivity().startService(new Intent(getActivity(), AutoUpdateService.class));
                break;
            case "auto_update":
                SettingUtils.getInstance().setAutoUpdate(Integer.parseInt(String.valueOf(newValue)));
                mChangeUpdate.setSummary(SettingUtils.getInstance().getAutoUpdate() == 0 ?
                        "从不自动更新" : "每 " + SettingUtils.getInstance().getAutoUpdate() + " 小时");
                getActivity().startService(new Intent(getActivity(), AutoUpdateService.class));
                break;
            case "is_show_notification":
                SettingUtils.getInstance().setIsShowNotification(Boolean.parseBoolean(String.valueOf(newValue)));
                if (!Boolean.parseBoolean(String.valueOf(newValue))) {
                    Log.d("setting", "notification");
                    NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.cancel(1);
                }
                getActivity().startService(new Intent(getActivity(), AutoUpdateService.class));
                break;
            case "is_show_huang_li":
                SettingUtils.getInstance().setIsShowHuangLi(Boolean.parseBoolean(String.valueOf(newValue)));
                break;
            case "is_show_cons":
                SettingUtils.getInstance().setIsShowCons(Boolean.parseBoolean(String.valueOf(newValue)));
                break;
            case "selected_cons":
                mSelectedCons.setSummary(String.valueOf(newValue));
                SettingUtils.getInstance().setSelectedCons(String.valueOf(newValue));
                break;
        }

        return true;
    }


}
