package controller;

/**
 * This abstract class is extended by Controllers
 */
public abstract class Controller {
    /**
     * Updates any component that is dependent on the main controller.
     */
    public abstract void update();
}
