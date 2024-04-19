package artofillusion;

import lombok.Getter;
import org.pf4j.DefaultPluginManager;
import org.pf4j.ExtensionFactory;
import org.pf4j.SingletonExtensionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.*;

public class AppPluginManager extends DefaultPluginManager {

    private final Map<String, Map<String, PluginResource>> resources = new HashMap<>();
    private static final Map<String, ExportInfo> exports = new HashMap<>();

    private AppPluginManager() {
        super(Paths.get(System.getProperty("user.dir"),"Plugins"));
    }

    @Override
    protected ExtensionFactory createExtensionFactory() {
        return new SingletonExtensionFactory(this);
    }

    private static class PluginManagerHolder {
        public static final AppPluginManager INSTANCE = new AppPluginManager();
    }


    public static AppPluginManager getInstance() {
        return PluginManagerHolder.INSTANCE;
    }

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
    public void registerResource(String type, String id, ClassLoader loader, String name, Locale locale) throws IllegalArgumentException {
        //Not implemented yet
    }

    /**
     * Get a list of all type identifiers for which there are PluginResources available.
     */
    public List<String> getResourceTypes() {
        return new ArrayList<>(resources.keySet());
    }

    /**
     * Get a list of all registered PluginResources of a particular type.
     */
    public List<PluginResource> getResources(String type) {
        Map<String, PluginResource> resourcesForType = resources.get(type);
        if (resourcesForType == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(resourcesForType.values());
    }

    /**
     * Get the PluginResource with a particular type and id, or null if there is no such resource.
     */
    public PluginResource getResource(String type, String id) {
        Map<String, PluginResource> resourcesForType = resources.get(type);
        if (resourcesForType == null) {
            return null;
        }
        return resourcesForType.get(id);
    }


    /**
     * Register a method which may be invoked on a plugin object. This allows external code to easily
     * use features of a plugin without needing to directly import that plugin or use reflection.
     * Use {@link #getExportedMethodIds()} to get a list of all exported methods that have been
     * registered, and {@link #invokeExportedMethod(String, Object[])} to invoke one.
     *
     * @param plugin the plugin object on which the method should be invoked
     * @param method the name of the method to invoke
     * @param id a unique identifier which may be passed to <code>invokeExportedMethod()</code>
     * to identify this method
     */
    void registerExportedMethod(Object plugin, String method, String id) throws IllegalArgumentException {
        ExportInfo info = new ExportInfo();
        info.plugin = plugin;
        info.method = method;
        info.id = id;
        registerExportedMethod(info);
    }

    // This method calls during processing extersions.xml files

    private static void registerExportedMethod(ExportInfo export) throws IllegalArgumentException {
        if (exports.containsKey(export.id)) {
            throw new IllegalArgumentException("Multiple exported methods with id=" + export.id);
        }
        exports.put(export.id, export);
    }

    /**
     * Get a list of the identifiers of all exported methods which have been registered.
     */
    List<String> getExportedMethodIds() {
        return new ArrayList<>(exports.keySet());
    }

    /**
     * Invoke an exported method of a plugin object.
     *
     * @param id the unique identifier of the method to invoke
     * @param args the list of arguments to pass to the method. If the method has no arguments,
     * this may be null.
     * @return the value returned by the method after it was invoked
     * @throws NoSuchMethodException if there is no exported method with the specified ID, or if there
     * is no form of the exported method whose arguments are compatible with the specified args array.
     * @throws InvocationTargetException if the method threw an exception when it was invoked.
     */
     Object invokeExportedMethod(String id, Object... args) throws NoSuchMethodException, InvocationTargetException {
        ExportInfo info = exports.get(id);
        if (info == null) {
            throw new NoSuchMethodException("There is no exported method with id=" + id);
        }

        // Try to find a method to invoke.
        for (Method method : info.plugin.getClass().getMethods()) {
            if (!method.getName().equals(info.method)) {
                continue;
            }
            try {
                return method.invoke(info.plugin, args);
            } catch (IllegalArgumentException ex) {
                // Possibly the wrong version of an overloaded method, so keep trying.
            } catch (IllegalAccessException ex) {
                // This should be impossible, since getMethods() only returns public methods.

                throw new InvocationTargetException(ex);
            }
        }
        throw new NoSuchMethodException("No method found which matches the specified name and argument types.");
    }


    public static class PluginResource {

        private PluginResource() {
        }

        public String type;
        @Getter public String id;
        public ClassLoader loader;
        @Getter public String name;
        public Locale locale;

        public ClassLoader getClassLoader() {
            return loader;
        }
    }

    /**
     * This class is used to store information about an "export" record in an XML file.
     */
    private static class ExportInfo {

        String method;
        String id;
        String className;
        Object plugin;
    }
}
