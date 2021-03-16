package reghzy.atominacraft.client;

public class GameSettings {
    //public static final float MOUSE_SENSITIVITY = 0.002f;
    public static final float MOUSE_SENSITIVITY = 0.04805f;
    public static final float WALK_SPEED = 4.0f;
    public static final float FLY_SPEED = 4.5f;

    // how many times to update the worlds/game per second
    public static final long updatesPerSecond = 400;
    // how many times to render the game per second (you usually have to triple this value due to reasons..., if you want 60 fps, put 180 or so)
    public static final long framesPerSecond = 200;
    public static final long updatesMillis = 1000 / updatesPerSecond;
    public static final long renderMillis = 1000 / framesPerSecond;
}
