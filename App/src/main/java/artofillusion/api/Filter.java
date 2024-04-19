package artofillusion.api;

import org.pf4j.ExtensionPoint;

public interface Filter extends ExtensionPoint {

    default String getName() {
        return this.getClass().getSimpleName();
    }


}
