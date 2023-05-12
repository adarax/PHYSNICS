package edu.vanier.physnics.stackedblock;

/**
 * A class to ensure the user's settings are kept, even when switching between
 * scenes.
 * 
 * @author adam8
 */
public class Settings {
    private static boolean isDark = true;

    /**
     * Returns the boolean value of isDark. If true, the application
     * is in dark mode. If false, the application is in light mode.
     * 
     * @return whether the application is in dark mode or not
     */
    public static boolean isDark()
    {
        return isDark;
    }

    /**
     * Sets the boolean value of isDark.
     * 
     * @param isDark the boolean value to set isDark to
     */
    public static void setIsDark(boolean isDark)
    {
        Settings.isDark = isDark;
    }
}
