package com.confusedbrowser.androneee_remote.preferences;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.MenuItem;
import java.util.List;

import com.confusedbrowser.androneee_remote.R;
 
/**
 * This will handle our Preference object
 * 
 * @author David Buttar
 * @author Shahmir Javaid
 */
public class PreferencesActivity 
    extends PreferenceActivity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        ActionBar ab = getActionBar();
        ab.setSubtitle(R.string.pref_subtitle);
        //ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);

        getFragmentManager()
            .beginTransaction()
            .replace(android.R.id.content, new MainFragment())
            .commit();
    }

    /**
     * Handler of the Action bar selected Option
     *
     * @param item - The item clicked
     *
     * @return boolean - true if selected other wise see parent
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) 
        {
            //Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}