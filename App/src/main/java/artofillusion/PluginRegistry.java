package artofillusion;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Locale;

@Slf4j
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PluginRegistry {

    /**
     * Register a new resource. You can then call {@link #getResource(String, String)} to look up
     * a particular resource, or {@link #getResources(String)} to find all registered resources of
     * a particular type.
     *
     * @param type the type of resource being registered
     * @param id the id of this resource
     * @param loader the ClassLoader with which to load the resource
     * @param name the fully qualified name of the resource, that should be passed to
     * <code>loader.getResource()</code> to load it
     * @param locale the locale this resource represents (maybe null)
     * @throws IllegalArgumentException if there is already a registered resource with the same type, id, and locale
     */
    public static void registerResource(String type, String id, ClassLoader loader, String name, Locale locale) throws IllegalArgumentException {
        AppPluginManager.getInstance().registerResource(type, id, loader, name, locale);
    }

    /**
     * Get a list of all type identifiers for which there are PluginResources available.
     */
    public static List<String> getResourceTypes() {
        return AppPluginManager.getInstance().getResourceTypes();
    }

    /**
     * Get a list of all registered PluginResources of a particular type.
     */
    public static List<AppPluginManager.PluginResource> getResources(String type) {
        return AppPluginManager.getInstance().getResources(type);
    }

    /**
     * Get the PluginResource with a particular type and id, or null if there is no such resource.
     */
    public static AppPluginManager.PluginResource getResource(String type, String id) {
        return AppPluginManager.getInstance().getResource(type, id);
    }

    public static void registerExportedMethod(Object plugin, String method, String id) throws IllegalArgumentException {
        AppPluginManager.getInstance().registerExportedMethod(plugin, method, id);
    }

    public static Object invokeExportedMethod(String id, Object... args) throws NoSuchMethodException, InvocationTargetException {
        return AppPluginManager.getInstance().invokeExportedMethod(id, args);
    }
}
