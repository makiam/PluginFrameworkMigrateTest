package artofillusion.ui;

import artofillusion.AppPluginManager;
import artofillusion.PluginRegistry;
import artofillusion.ThemeManager;
import artofillusion.api.ApplicationLifecycle;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Slf4j
public class LayoutWindow extends JFrame {

    public LayoutWindow() {
        super("Layout Window");
    }

    @Override
    protected void frameInit() {
        super.frameInit();
        this.setSize(1280,1024);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        // Background color is taken from theme Colorset applicationbackground value
        this.setBackground(ThemeManager.getAppBackgroundColor());


        this.setJMenuBar(new JMenuBar());
        JMenu menu = new JMenu("Actions");
        menu.add(new JMenuItem("Plugin Action")).addActionListener(this::pluginMethodCalled);
        menu.addSeparator();
        menu.add(new JMenuItem("Exit")).addActionListener(this::onExit);
        this.getJMenuBar().add(menu);
        this.getJMenuBar().add(new ToolsMenu());
    }

    @Override
    public void setVisible(boolean b) {
        List<ApplicationLifecycle> lps =  AppPluginManager.getInstance().getExtensions(artofillusion.api.ApplicationLifecycle.class);
        lps.forEach(cycle -> cycle.onViewCreated(this));
        super.setVisible(b);
    }

    void pluginMethodCalled(ActionEvent e) {
        try {
            log.info("Plugin method called");
            PluginRegistry.invokeExportedMethod("aoi.tools.LatheTool");  //method registered in plugin extensions.xml
        } catch (InvocationTargetException | NoSuchMethodException ex) {
            log.atError().setCause(ex).log("Error invoking plugin method with id: {} due to {}", "aoi.tools.LatheTool", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    void onExit(ActionEvent e) {
        this.dispose();
    }
}
