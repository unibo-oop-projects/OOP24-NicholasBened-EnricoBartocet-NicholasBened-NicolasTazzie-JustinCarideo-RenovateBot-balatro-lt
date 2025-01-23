package it.unibo.balatrolt;

import it.unibo.balatrolt.view.impl.GUI;

public final class LaunchApp {

    public static final int APP_SIZE = 20;

    private LaunchApp() { }

    public static void main(final String[] args) throws java.io.IOException {
        new GUI(APP_SIZE);
    }
}
