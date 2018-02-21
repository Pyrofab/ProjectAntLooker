package fr.antproject.antlookerapp.configuration;

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
    @Override
    public double getThreshold() {
        return 0;
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
        return 0;
    }

    @Override
    public double getMinAcceptedArea() {
        return 0;
    }
}
