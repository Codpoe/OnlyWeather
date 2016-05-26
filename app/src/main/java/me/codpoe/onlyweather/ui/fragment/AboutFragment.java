package me.codpoe.onlyweather.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import me.codpoe.onlyweather.R;
import me.codpoe.onlyweather.util.DialogUtils;
import me.codpoe.onlyweather.util.VersionUtils;

/**
 * Created by Codpoe on 2016/5/25.
 */
public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{

    private Preference mIntroductionPref;
    private Preference mCheckVersionPref;
    private Preference mStarPref;
    private Preference mMePref;
    private Preference mSupportPref;
    private Preference mFeedbackPref;
    private Preference mWeatherApiPref;
    private Preference mImagePref;
    private Preference mOpenSourcePref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        findPreferences();

        mIntroductionPref.setOnPreferenceClickListener(this);
        mCheckVersionPref.setOnPreferenceClickListener(this);
        mStarPref.setOnPreferenceClickListener(this);
        mMePref.setOnPreferenceClickListener(this);
        mSupportPref.setOnPreferenceClickListener(this);
        mFeedbackPref.setOnPreferenceClickListener(this);
        mWeatherApiPref.setOnPreferenceClickListener(this);
        mImagePref.setOnPreferenceClickListener(this);
        mOpenSourcePref.setOnPreferenceClickListener(this);

        mCheckVersionPref.setSummary("当前版本: " + VersionUtils.getCurVersion(getActivity()));

    }

    public void findPreferences() {
        mIntroductionPref = findPreference("introduction");
        mCheckVersionPref = findPreference("check_version");
        mStarPref = findPreference("star");
        mMePref = findPreference("me");
        mSupportPref = findPreference("support");
        mFeedbackPref = findPreference("feedback");
        mWeatherApiPref = findPreference("weather_api");
        mImagePref = findPreference("image");
        mOpenSourcePref = findPreference("open_source");
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        switch (preference.getKey()) {
            case "introduction":
                DialogUtils.showIntroduction(getActivity());
                break;
            case "check_version":
                VersionUtils.checkVersion(getActivity());
                break;
            case "star":
                DialogUtils.showStar(getActivity());
                break;
            case "me":
                DialogUtils.showMe(getActivity());
                break;
            case "support":
                DialogUtils.showSupport(getActivity());
                break;
            case "feedback":
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                clipboardManager.setPrimaryClip(ClipData.newPlainText(null, "codpoe.me@gmail.com"));
                Snackbar.make(getView(), "复制成功", Snackbar.LENGTH_SHORT).show();
                break;
            case "weather_api":
                Uri uri = Uri.parse("http://heweather.com/");
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(uri);
                getActivity().startActivity(intent);
                break;
            case "image":
                Uri uri1 = Uri.parse("http://www.zedge.net/");
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_VIEW);
                intent1.setData(uri1);
                getActivity().startActivity(intent1);
                break;
            case "open_source":
                DialogUtils.showOpenSource(getActivity());
                break;
        }
        return false;
    }
}
