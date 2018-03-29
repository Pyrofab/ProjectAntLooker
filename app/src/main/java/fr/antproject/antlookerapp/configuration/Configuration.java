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
        int val = Integer.parseInt(sharedPref.getString(SettingsActivity.KEY_PREF_THRESHOLD, "0"));
        if(val > 255) {
            return 255;
        }else{
            return val;
        }
    }

    @Override
    public double getMaxValue() {
        return 0;
    }

    @NotNull
    @Override
    public ThresholdTypes getAlgorithm() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return ThresholdTypes.valueOf(sharedPref.getString(SettingsActivity.KEY_PREF_THRESHOLD_TYPES, "BINARY_INVERTED"));
    }

    @Nullable
    @Override
    public ThresholdTypesOptional getOptional() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String type = sharedPref.getString(SettingsActivity.KEY_PREF_THRESHOLD_TYPES_OPTIONAL, "NONE");
        if (type == "NONE"){
            return null;
        } else {
            return ThresholdTypesOptional.valueOf(type);
        }
    }

    @NotNull
    @Override
    public ContourRetrievalMode getMode() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return ContourRetrievalMode.valueOf(sharedPref.getString(SettingsActivity.KEY_PREF_CONTOUR_RETRIEVAL_MODE, "LIST"));
    }

    @NotNull
    @Override
    public ContourApproxMethod getMethod() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return ContourApproxMethod.valueOf(sharedPref.getString(SettingsActivity.KEY_PREF_CONTOUR_APPROX_METHOD, "TC89_KCOS"));
    }

    @Override
    public double getMaxFuseDistance() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        double val = Integer.parseInt(sharedPref.getString(SettingsActivity.KEY_PREF_MAX_FUSE_DISTANCE, "0"));
        if(val > 50) {
            return 50;
        }else{
            return val;
        }
    }

    //Must me divided by 10, see pref_general.xml
    @Override
    public double getMinAcceptedArea() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        double val = Integer.parseInt(sharedPref.getString(SettingsActivity.KEY_PREF_MIN_ACCEPTED_AREA, "0"));
        if(val > 10) {
            return 1;
        }else{
            return val/10;
        }
    }
}
