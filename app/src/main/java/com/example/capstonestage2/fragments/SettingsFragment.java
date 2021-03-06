package com.example.capstonestage2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.preference.CheckBoxPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import com.example.capstonestage2.R;
import com.example.capstonestage2.activities.MainActivity;

import java.util.Objects;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.licenses.EclipsePublicLicense10;
import de.psdev.licensesdialog.licenses.SILOpenFontLicense11;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

public class SettingsFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    private Preference openSourceLibraryPreference;
    private Preference otherLicensesPreference;
    private Preference cryptocompareApiPreference;
    private Preference developerPreference;

    private ApacheSoftwareLicense20 mApacheSoftwareLicense = new ApacheSoftwareLicense20();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_tothemoon);

        openSourceLibraryPreference = findPreference(getString(R.string.pref_open_source_licenses_key));
        otherLicensesPreference = findPreference(getString(R.string.pref_other_licenses_key));
        cryptocompareApiPreference = findPreference(getString(R.string.pref_cryptocompare_api_key));
        developerPreference = findPreference(getString(R.string.pref_developer_key));

        openSourceLibraryPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showOpenSourceLibraryLicences();
                return true;
            }
        });

        otherLicensesPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showOtherLicenses();
                return true;
            }
        });

        cryptocompareApiPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showCryptoCompareSite();
                return true;
            }
        });

        developerPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onPreferenceClick(Preference preference) {
                showDeveloperInformation();
                return true;
            }
        });

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();

        // Set default currency ListPreference
        PreferenceCategory preferenceCategory = (PreferenceCategory) prefScreen.getPreference(0);
        Preference listPreference = preferenceCategory.findPreference(getString(R.string.pref_currency_key));

        String value = sharedPreferences.getString(listPreference.getKey(), MainActivity.DEFAULT_CURRENCY);
        setPreferenceSummary(listPreference, value);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (null != preference) {
            // Updates the summary for the preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        return true;
    }

    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // For list preferences, figure out the label of the selected value
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Set the summary to that label
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showOpenSourceLibraryLicences() {
        final Notices notices = new Notices();
        notices.addNotice(new Notice("The Android Open Source Project", "", " Copyright (C) 2011 The Android Open Source Project", mApacheSoftwareLicense));
        notices.addNotice(new Notice("Timber", "https://github.com/JakeWharton/timber", "Copyright 2013 Jake Wharton", mApacheSoftwareLicense));
        notices.addNotice(new Notice("Butterknife", "https://github.com/JakeWharton/butterknife", "Copyright 2013 Jake Wharton", mApacheSoftwareLicense));
        notices.addNotice(new Notice("Picasso", "https://github.com/square/picasso", "Copyright 2013 Square, Inc.", mApacheSoftwareLicense));
        notices.addNotice(new Notice("RoundedImageView", "https://github.com/vinc3m1/RoundedImageView", "Copyright 2017 Vincent Mi", mApacheSoftwareLicense));
        notices.addNotice(new Notice("GSON", "https://github.com/google/gson", "Copyright 2008 Google Inc.", mApacheSoftwareLicense));
        notices.addNotice(new Notice("Retrofit", "https://github.com/square/retrofit", "Copyright 2013 Square, Inc.", mApacheSoftwareLicense));
        notices.addNotice(new Notice("Retrofit GSON Converter", "https://github.com/square/retrofit/tree/master/retrofit-converters/gson", "Copyright 2013 Square, Inc.", mApacheSoftwareLicense));
        notices.addNotice(new Notice("Retrofit RxJava2 Adapter", "https://github.com/square/retrofit/tree/master/retrofit-adapters/rxjava2", "Copyright 2013 Square, Inc.", mApacheSoftwareLicense));
        notices.addNotice(new Notice("RxJava", "https://github.com/ReactiveX/RxJava", "Copyright (c) 2016-present, RxJava Contributors.", mApacheSoftwareLicense));
        notices.addNotice(new Notice("RxAndroid", "https://github.com/ReactiveX/RxAndroid", "Copyright 2015 The RxAndroid authors", mApacheSoftwareLicense));
        notices.addNotice(new Notice("MPAndroidChart", "https://github.com/PhilJay/MPAndroidChart", "Copyright 2018 Philipp Jahoda", mApacheSoftwareLicense));
        notices.addNotice(new Notice("search-dialog", "https://github.com/mirrajabi/search-dialog", "Copyright 2018 Philipp Jahoda", mApacheSoftwareLicense));
        notices.addNotice(new Notice("MaterialStyledDialogs", "https://github.com/javiersantos/MaterialStyledDialogs", "Copyright 2016-2018 Javier Santos", mApacheSoftwareLicense));
        notices.addNotice(new Notice("JUnit", "https://junit.org/", "Copyright © 2002-2018 JUnit", new EclipsePublicLicense10()));

        new LicensesDialog.Builder(Objects.requireNonNull(getContext()))
                .setNotices(notices)
                .setDividerColorId(R.color.colorPrimary)
                .build()
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showOtherLicenses() {
        final Notices notices = new Notices();

        notices.addNotice(new Notice("Rocket Icon", "https://www.flaticon.com/free-icon/rocket_214337", "designed by Pixel Buddha from Flaticon", null));
        notices.addNotice(new Notice("Moon Icon", "https://www.flaticon.com/free-icon/moon_1137453", "designed by Smashicons from Flaticon", null));
        notices.addNotice(new Notice("Aldrich Font", "https://fonts.google.com/specimen/Aldrich?selection.family=Aldrich", "Copyright dMADType", new SILOpenFontLicense11()));

        new LicensesDialog.Builder(Objects.requireNonNull(getContext()))
                .setNotices(notices)
                .setDividerColorId(R.color.colorPrimary)
                .build()
                .show();
    }

    private void showCryptoCompareSite() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(getString(R.string.pref_cryptocompare_api_url)));
        startActivity(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showDeveloperInformation() {
        final Notices notices = new Notices();

        notices.addNotice(new Notice("", "https://github.com/Aman817", "Developed by Aman Gangwar", null));

        new LicensesDialog.Builder(Objects.requireNonNull(getContext()))
                .setNotices(notices)
                .setDividerColorId(R.color.colorPrimary)
                .build()
                .show();
    }
}
