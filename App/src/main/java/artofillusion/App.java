package artofillusion;


import artofillusion.api.ApplicationLifecycle;
import artofillusion.api.Tool;
import artofillusion.ui.LayoutWindow;
import groovy.lang.GroovyShell;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.pf4j.PluginManager;

import javax.swing.*;
import java.util.List;

@Slf4j
@NoArgsConstructor
public class App {

    private static final CompilerConfiguration config = new CompilerConfiguration();

    @Getter
    private static final GroovyShell shell = new GroovyShell(config);

    @Getter
    private final PluginManager manager = AppPluginManager.getInstance();

    static {
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) ->
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE)
        );
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
            AppPluginManager.getInstance().stopPlugins(), "Plugins shutdown thread"));
    }

    public static void main(String... args) {
        try {
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

        App app = new App();
        var manager = app.getManager();
        manager.loadPlugins();
        manager.startPlugins();
        manager.getPlugins().forEach(wrapper -> {
            // Register classes from plugins to be available in shell ???
        });



        ThemeManager.initThemes();

        List<ApplicationLifecycle> lps =  manager.getExtensions(artofillusion.api.ApplicationLifecycle.class);
        lps.forEach(cycle -> cycle.onApplicationStarting());


        SwingUtilities.invokeLater(() -> new LayoutWindow().setVisible(true));
    }
}

