package artofillusion;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.*;
import java.util.List;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Slf4j
public class ThemeManager {

    public static Color getAppBackgroundColor() {

        //Should read color from selected theme/colorset applicationbackground value
        //Stub code for now...
        return Color.LIGHT_GRAY;
    }

    public static void initThemes() {
        //Load all the theme resources from all the plugins
        List<AppPluginManager.PluginResource> resources = PluginRegistry.getResources("UITheme");
        //Do something with the resources
        resources.forEach(r -> log.info("Theme: {}",r.getId()));

    }
    /**
     * Utility for parsing XML Documents: gets a named node from a node list. Returns null if the node does not
     * exist.
     *
     * @param nl The node list
     * @param nodeName The node name
     * @return The node named nodeName
     */
    private static Node getNodeFromNodeList(NodeList nl, String nodeName) {
        for (int i = 0; i < nl.getLength(); ++i) {
            Node n = nl.item(i);
            if (n.getNodeName().equals(nodeName)) {
                return n;
            }
        }
        return null;
    }

    /**
     * Utility for parsing XML Documents: gets a named attribute value from a node
     *
     * @param name The attribute name
     * @param node Description of the Parameter
     * @return The attribute value
     */
    private static String getAttribute(Node node, String name) {
        NamedNodeMap nm = node.getAttributes();
        if (nm == null) {
            return null;
        }
        Node nn = nm.getNamedItem(name);
        if (nn == null) {
            return null;
        }
        return nn.getNodeValue();
    }

    private static Color getColorFromNode(Node node) {
        if (node == null) {
            return new Color(0, 0, 0);
        }
        String s = getAttribute(node, "R");
        int r = 0;
        if (s != null) {
            r = Integer.parseInt(s);
        }
        int g = 0;
        s = getAttribute(node, "G");
        if (s != null) {
            g = Integer.parseInt(s);
        }
        int b = 0;
        s = getAttribute(node, "B");
        if (s != null) {
            b = Integer.parseInt(s);
        }
        return new Color(r, g, b);
    }

    /**
     * This class holds all the colors used by a theme. A theme can propose several color sets.
     *
     *
     */
    public static class ColorSet {

        public final Color appBackground;
        public final Color paletteBackground;
        public final Color viewerBackground;
        public final Color viewerLine;
        public final Color viewerHandle;
        public final Color viewerHighlight;
        public final Color viewerSpecialHighlight;
        public final Color viewerDisabled;
        public final Color viewerSurface;
        public final Color viewerTransparent;
        public final Color viewerLowValue;
        public final Color viewerHighValue;
        public final Color dockableBarColor1;
        public final Color dockableBarColor2;
        public final Color dockableTitleColor;
        public final Color textColor;
        private final String name;

        private ColorSet(Node node) {
            name = getAttribute(node, "name");
            NodeList list = node.getChildNodes();
            node = getNodeFromNodeList(list, "applicationbackground");
            appBackground = getColorFromNode(node);
            node = getNodeFromNodeList(list, "palettebackground");
            paletteBackground = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerbackground");
            viewerBackground = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerline");
            viewerLine = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerhandle");
            viewerHandle = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerhighlight");
            viewerHighlight = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerspecialhighlight");
            viewerSpecialHighlight = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerdisabled");
            viewerDisabled = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewersurface");
            viewerSurface = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerlowvalue");
            viewerLowValue = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewerhighvalue");
            viewerHighValue = getColorFromNode(node);
            node = getNodeFromNodeList(list, "viewertransparent");
            viewerTransparent = getColorFromNode(node);
            node = getNodeFromNodeList(list, "dockablebarcolor1");
            dockableBarColor1 = getColorFromNode(node);
            node = getNodeFromNodeList(list, "dockablebarcolor2");
            dockableBarColor2 = getColorFromNode(node);
            node = getNodeFromNodeList(list, "dockabletitlecolor");
            dockableTitleColor = getColorFromNode(node);
            node = getNodeFromNodeList(list, "textcolor");
            textColor = getColorFromNode(node);
        }

        public String getName() {
            return Translate.text(name);
        }
    }
}
