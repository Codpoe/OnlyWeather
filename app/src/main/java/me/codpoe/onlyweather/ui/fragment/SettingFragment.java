package me.codpoe.onlyweather.ui.fragment;

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

    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        Log.d("setting", "0");
        mIsAutoUpdate = (CheckBoxPreference) findPreference("is_auto_update");
        mChangeUpdate = (ListPreference) findPreference("auto_update");

        mIsAutoUpdate.setOnPreferenceChangeListener(this);

        mChangeUpdate.setSummary(SettingUtils.getInstance().getAutoUpdate() == 0 ? "从不自动更新"
                : "每 " + SettingUtils.getInstance().getAutoUpdate() + " 小时");
        Log.d("setting", "1");
        mChangeUpdate.setOnPreferenceChangeListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals("is_auto_update")) {
            SettingUtils.getInstance().setIsAutoUpdate(Boolean.parseBoolean(String.valueOf(newValue)));
            getActivity().startService(new Intent(getActivity(), AutoUpdateService.class));
        }
        if(preference.getKey().equals("auto_update")) {
            SettingUtils.getInstance().setAutoUpdate(Integer.parseInt(String.valueOf(newValue)));
            mChangeUpdate.setSummary(SettingUtils.getInstance().getAutoUpdate() == 0 ?
                    "从不自动更新" : "每 " + SettingUtils.getInstance().getAutoUpdate() + " 小时");
            getActivity().startService(new Intent(getActivity(), AutoUpdateService.class));
        }
        return true;
    }

}
