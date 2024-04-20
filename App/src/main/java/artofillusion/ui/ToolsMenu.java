package artofillusion.ui;

import artofillusion.AppPluginManager;
import artofillusion.Translate;
import artofillusion.api.Tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ToolsMenu extends JMenu {
    public ToolsMenu() {
        super(Translate.text("DefaultResource:menu.tools"));
        List<Tool> tools = AppPluginManager.getInstance().getExtensions(artofillusion.api.Tool.class);
        for (Tool tool : tools) {
            this.add(new ToolsAction(tool));
        }
    }

    private class ToolsAction extends AbstractAction {
        private final Tool tool;
        public ToolsAction(Tool tool) {
            super(tool.getName());
            this.tool = tool;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            tool.execute(null);
        }
    }

}
