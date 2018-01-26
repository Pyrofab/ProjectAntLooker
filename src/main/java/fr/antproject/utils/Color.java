package fr.antproject.utils;

import org.opencv.core.Scalar;

import java.util.Arrays;

/**
 * Class used to encapsulate colors as an array of integer values between 0 and 0xFF
 */
@SuppressWarnings("WeakerAccess")
public final class Color extends Scalar {

    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color ORANGE = new Color(255, 123, 0);

    /**
     * Overload for the main constructor defaulting alpha to 255
     *
     * @param r the red value of this color, between 0 and 255
     * @param g the green value of this color, between 0 and 255
     * @param b the blue value of this color, between 0 and 255
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 0xFF);
    }

    /**
     * Creates a color with the specified rgba values
     *
     * @param r the red value of this color, between 0 and 255
     * @param g the green value of this color, between 0 and 255
     * @param b the blue value of this color, between 0 and 255
     * @param a the alpha value of this color, between 0 and 255. A lesser value means more transparency.
     */
    public Color(int r, int g, int b, int a) {
        super(b, g, r, a);
        for (int param : Arrays.asList(r, g, b, a))
            if (param < 0 || param > 0xFF)
                throw new IllegalArgumentException("One of the provided value (" + param + ") is not a valid color component");
    }

    public int getAlpha() {
        return (int) this.getAlpha();
    }

    public int getRed() {
        return (int) this.getRed();
    }

    public int getGreen() {
        return (int) this.getGreen();
    }

    public int getBlue() {
        return (int) this.getBlue();
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + getRed() +
                "g=" + getGreen() +
                "b=" + getBlue() +
                "a=" + getAlpha() +
                '}';
    }
}
