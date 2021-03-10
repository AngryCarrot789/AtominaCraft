package atominacraft.maths;

public class VectorSpeed {
    public static void multiplyVector3By(Vector3 a, Vector3 b) {
        a.set(a.x * b.x, a.y * b.y, a.z * b.z);
    }
}
