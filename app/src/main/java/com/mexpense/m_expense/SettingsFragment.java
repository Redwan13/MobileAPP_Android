package com.mexpense.m_expense;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        //accesses the xml folder in the resource directory.
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}