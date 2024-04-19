package artofillusion.api;

import org.pf4j.ExtensionPoint;

public interface Tool extends ExtensionPoint {

    default String getName() {
        return this.getClass().getSimpleName();
    }

    default void execute(Model model) {
        System.out.println("************************   Execute Tool " + this.getClass().getSimpleName());
    }
}