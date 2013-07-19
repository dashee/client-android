package com.confusedbrowser.androneee_remote.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.preference.PreferenceFragment;
import com.confusedbrowser.androneee_remote.R;
 
/**
 * This will handle our Preference object
 * 
 * @author David Buttar
 * @author Shahmir Javaid
 */
public class MainFragment 
    extends PreferenceFragment 
    implements OnSharedPreferenceChangeListener
{
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
            
        SharedPreferences sharedPref = getPreferenceScreen().getSharedPreferences();
        sharedPref.registerOnSharedPreferenceChangeListener(this);

        this.setSharedPreferenceState(sharedPref);
    }

   // @Override
    public boolean onPreferenceTreeClick(PreferenceScreen prefScreen, Preference pref) 
    {

        if (pref.getKey().contains("pref_channel")) 
        {
            //Display the fragment as the main content.
        	Intent channelSettingsActivity = new Intent(getActivity(), ChannelActivity.class);
        	startActivity(channelSettingsActivity);
        	return true;
        }
        
        return super.onPreferenceTreeClick(prefScreen, pref);
    }
    
    /**
     * Set the Value of shared preferences given the last state.
     *
     * @param sharedPref - The sharedPref Object
     */
    private void setSharedPreferenceState(SharedPreferences sharedPref) 
    {
        EditTextPreference Ip = (EditTextPreference)findPreference("pref_server_ip");
        Ip.setSummary(sharedPref.getString("pref_server_ip", "192.168.1.11"));
        
        EditTextPreference Port = (EditTextPreference)findPreference("pref_server_port");
        Port.setSummary(sharedPref.getString("pref_server_port", "2047"));
    }

    /**
     * Change the value of the Textbox
     *
     * @param sharedPreference - The SharedPreference object
     * @param key - The preference changed
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) 
    {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) 
        {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());
        }
    }
}
