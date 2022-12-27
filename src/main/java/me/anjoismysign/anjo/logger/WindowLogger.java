package me.anjoismysign.anjo.logger;

import me.anjoismysign.anjo.libraries.PanelLib;

/**
 * @author anjoismysign
 */
public class WindowLogger implements Logger {
    /**
     * Shows a message in a window.
     *
     * @param message the message to show
     */
    public void log(String message) {
        PanelLib.showMessage(message);
    }

    @Override
    public void debug(String message) {
        PanelLib.showMessage("DEBUG", message);
    }

    @Override
    public void error(String message) {
        PanelLib.showMessage("ERROR", message + "\n" + message + "\n" + message);
    }

    @Override
    public void singleError(String message) {
        PanelLib.showMessage("ERROR", message);
    }
}
