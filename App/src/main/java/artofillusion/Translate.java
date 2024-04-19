package artofillusion;

import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public final class Translate {
    private static final Locale locale = Locale.getDefault(); // The default Locale

    private static final Map<String, ResourceBundle> bundles = new HashMap<>();

    /**
     * Look up the value corresponding to a resource key.
     *
     * @param key the key specified by the user
     * @param prefix an optional prefix to prepend to the key
     * @param suffix an optional suffix to append to the key
     */
    private static String getValue(String key, String prefix, String suffix) throws MissingResourceException {
        String bundle;
        int colon = key.indexOf(':');
        if (colon == -1) {
            bundle = "artofillusion";
        } else {
            bundle = key.substring(0, colon);
            key = key.substring(colon + 1);
        }
        if (prefix != null && suffix != null) {
            key = prefix + key + suffix;
        } else if (prefix != null) {
            key = prefix + key;
        } else if (suffix != null) {
            key = key + suffix;
        }
        ResourceBundle resources = bundles.get(bundle);
        if (resources == null) {
            AppPluginManager.PluginResource plugin = PluginRegistry.getResource("TranslateBundle", bundle);
            if (plugin == null) {
                throw new MissingResourceException("No TranslateBundle defined", bundle, key);
            }
            resources = ResourceBundle.getBundle(plugin.getName(), locale, plugin.getClassLoader());
            bundles.put(bundle, resources);
        }
        return resources.getString(key);
    }

    /**
     * Get the text given by the property "name". If the property is not
     * found, this simply returns name.
     */
    public static String text(String name) {
        try {
            return getValue(name, null, null);
        } catch (MissingResourceException ex) {
            return name;
        }
    }
}
