package fr.antproject.antlookerapp.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import fr.antproject.antlookercore.application.IConfiguration;
import fr.antproject.antlookercore.utils.wrappers.ContourApproxMethod;
import fr.antproject.antlookercore.utils.wrappers.ContourRetrievalMode;
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypes;
import fr.antproject.antlookercore.utils.wrappers.ThresholdTypesOptional;

/**
 * An implementation of IConfiguration bridging with the graphical interface to provide access to properties
 * TODO Implement using android stuff
 * Created by Fabien on 16/02/2018.
 */
public class Configuration implements IConfiguration {

    private Context context;

    public Configuration(Context context){
        this.context = context;
    }
    @Override
    public double getThreshold() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getInt(SettingsActivity.KEY_PREF_THRESHOLD, 0);
    }

    @Override
    public double getMaxValue() {
        return 0;
    }

    @NotNull
    @Override
    public ThresholdTypes getAlgorithm() {
        return null;
    }

    @Nullable
    @Override
    public ThresholdTypesOptional getOptional() {
        return null;
    }

    @NotNull
    @Override
    public ContourRetrievalMode getMode() {
        return null;
    }

    @NotNull
    @Override
    public ContourApproxMethod getMethod() {
        return null;
    }

    @Override
    public double getMaxFuseDistance() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getInt(SettingsActivity.KEY_PREF_MAX_FUSE_DISTANCE, 0);
    }

    //Must me divided by 10, see pref_general.xml
    @Override
    public double getMinAcceptedArea() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getInt(SettingsActivity.KEY_PREF_MIN_ACCEPTED_AREA, 0)/10;
    }
}
