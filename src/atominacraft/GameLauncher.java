package atominacraft;

import java.io.InputStream;

public class GameLauncher implements Runnable {
    public static void main(String[] args) {
        GameLauncher game = new GameLauncher();
        game.start();
    }

    private Thread thread;

    public void start() {
        thread = new Thread(this, "AtominaCraft");
        thread.start();
    }

    @Override
    public void run() {
        AtominaCraft atominaCraft = new AtominaCraft();
        atominaCraft.run();
    }
}
