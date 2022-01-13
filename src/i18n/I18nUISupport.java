
package i18n;

import java.util.concurrent.Callable;
import java.util.function.Function;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;

public class I18nUISupport {

    private static class LazyHolder {
        static final I18nUISupport INSTANCE = new I18nUISupport(I18nSupport.getInstance());
    }

    public static I18nUISupport getInstance() {
        return LazyHolder.INSTANCE;
    }

    private final I18nSupport i18n;

    public I18nUISupport(final I18nSupport i18n) {
        this.i18n = i18n;
    }

    public I18nSupport i18n() {
        return i18n;
    }

    /**
     * Creates a bound label whose value is computed on language change.
     *
     * @param func the function to compute the value
     * @return label
     */
    public Label newLabel(final String key, final Object... args) {
        return bind(new Label(), Label::textProperty, i18n.createStringBinding(key, args));
    }

    /**
     * Creates a bound label whose value is computed on language change.
     *
     * @param func the function to compute the value
     * @return label
     */
    public Label newLabel(final Callable<String> func) {
        return bind(new Label(), Label::textProperty, i18n.createStringBinding(func));
    }

    /**
     * Creates a bound button for the given resource bundle key.
     *
     * @param key resource bundle key
     * @param args optional arguments for the message
     * @return button
     */
    public Button newButton(final String key, final Object... args) {
        return bind(new Button(), Button::textProperty, i18n.createStringBinding(key, args));
    }

    /**
     * Creates a bound tooltip for the given resource bundle key.
     *
     * @param key resource bundle key
     * @param args optional arguments for the message
     * @return label
     */
    public Tooltip newTooltip(final String key, final Object... args) {
        return bind(new Tooltip(), Tooltip::textProperty, i18n.createStringBinding(key, args));
    }

    public Text newText(final String key, final Object... args) {
        return bind(new Text(), Text::textProperty, i18n.createStringBinding(key, args));
    }

    public <S, T> TableColumn<S, T> newTableColumn(final String key, final Object... args) {
        return bind(new TableColumn<>(), TableColumn::textProperty, i18n.createStringBinding(key, args));
    }

    private static <T> T bind(final T obj, final Function<T, StringProperty> spr, final StringBinding sb) {
        spr.apply(obj).bind(sb);
        return obj;
    }

}
