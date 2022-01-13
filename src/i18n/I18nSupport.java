
package i18n;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

/**
 * Copyright (c) 2016 sothawo
 *
 * http://www.sothawo.com
 */
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * I18N utility class.
 */
public class I18nSupport {

    private static class LazyHolder {
        static final I18nSupport INSTANCE = new I18nSupport();
    }

    public static I18nSupport getInstance() {
        return LazyHolder.INSTANCE;
    }

    /** The current selected locale. */
    private final ObjectProperty<Locale> locale;

    private I18nSupport() {
        locale = new SimpleObjectProperty<>();
        setLocale(getDefaultLocale());
    }

    /**
     * Gets the supported locales.
     *
     * @return list of supported locales.
     */
    public List<Locale> getSupportedLocales() {
        return Arrays.asList(Locale.ENGLISH, Locale.GERMAN);
    }

    /**
     * Gets the default locale.
     *
     * <p>
     * This is the systems default if contained in the supported locales, english otherwise.
     *
     * @return the default locale
     */
    public Locale getDefaultLocale() {
        final Locale sysDefault = Locale.getDefault();
        return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
    }

    /**
     * Gets the current selected locale.
     *
     * @return the selected locale
     */
    public Locale getLocale() {
        return localeProperty().get();
    }

    /**
     * Sets a locale.
     *
     * @param locale the new locale
     */
    public void setLocale(final Locale locale) {
        Locale.setDefault(locale);
        localeProperty().set(locale);
    }

    /**
     * Gets the locale property.
     *
     * @return locale property
     */
    private ObjectProperty<Locale> localeProperty() {
        return locale;
    }

    /**
     * Gets a string by key with format replacements.
     *
     * The key is taken from the resource bundle for the current locale and uses it as first argument to
     * MessageFormat.format, passing in the optional args and returning the result.
     *
     * @param key message key
     * @param args optional arguments for the message
     * @return localized formatted string
     */
    public String get(final String key, final Object... args) {
        final ResourceBundle rscBundle = ResourceBundle.getBundle("messages", getLocale());
        return MessageFormat.format(rscBundle.getString(key), args);
    }

    /**
     * Creates a string binding to a localized string for the given message bundle key.
     *
     * @param key key
     * @return string binding
     */
    public StringBinding createStringBinding(final String key, final Object... args) {
        return Bindings.createStringBinding(() -> get(key, args), locale);
    }

    /**
     * Creates a string binding to a localized string that is computed by calling the given function.
     *
     * @param func function called on every change
     * @return string binding
     */
    public StringBinding createStringBinding(final Callable<String> func) {
        return Bindings.createStringBinding(func, locale);
    }

}