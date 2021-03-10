package atominacraft.maths;

import atominacraft.utils.HashHelper;

public class Vector3 {
    public float x;
    public float y;
    public float z;

    public static Vector3 One   = new Vector3(1, 1, 1);
    public static Vector3 Zero  = new Vector3(0, 0, 0);
    public static Vector3 UnitX = new Vector3(1, 0, 0);
    public static Vector3 UnitY = new Vector3(0, 1, 0);
    public static Vector3 UnitZ = new Vector3(0, 0, 1);
    public static Vector3 Up       = new Vector3( 0,  1,  0);
    public static Vector3 Down     = new Vector3( 0, -1,  0);
    public static Vector3 Left     = new Vector3( 1,  0,  0);
    public static Vector3 Right    = new Vector3(-1,  0,  0);
    public static Vector3 Backward = new Vector3( 0,  0,  1);
    public static Vector3 Forward  = new Vector3( 0,  0, -1);

    public Vector3() {
        set(0, 0, 0);
    }

    public Vector3(float x, float y, float z) {
        set(x, y, z);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vector3 v) {
        this.set(v.x, v.y, v.z);
    }

    public float magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    public float magnitude() {
        return (float) Math.sqrt(magnitudeSquared());
    }

    public Vector3 normalised() {
        float mag = magnitude();
        if (mag == 0)
            return new Vector3(0, 0, 0);
        return new Vector3(this.x / mag, this.y / mag, this.z / mag);
    }

    public float dot(Vector3 v) {
        return this.x * v.x +
               this.y * v.y +
               this.z * v.z;
    }

    public Vector3 cross(Vector3 v) {
        return new Vector3(
                this.y * v.z - this.z * this.y,
                this.z * v.x - this.x * this.z,
                this.x * v.y - this.y * this.x);
    }

    public float angle(Vector3 v) {
        return (float) Math.acos(this.normalised().dot(v));
    }

    public boolean isUnit() {
        return x >= -1.0f && x <= 1.0f &&
               y >= -1.0f && y <= 1.0f &&
               z >= -1.0f && z <= 1.0f;
    }

    public static Vector3 multiply(Vector3 a, Vector3 b) {
        return multiply(a, b.x, b.y, b.z);
    }

    public static Vector3 multiply(Vector3 a, float x, float y, float z) {
        return new Vector3(a.x * x, a.y * y, a.z * z);
    }

    public static Vector3 multiply(Vector3 a, float b) {
        return multiply(a, b, b, b);
    }

    public static Vector3 multiplyDirection(Matrix4 m, Vector3 v) {
        return new Vector3(m.m[0] * v.x + m.m[1] * v.y + m.m[2] * v.z,
                           m.m[4] * v.x + m.m[5] * v.y + m.m[6] * v.z,
                           m.m[8] * v.x + m.m[9] * v.y + m.m[10] * v.z);
    }

    @Override
    protected Object clone() {
        return new Vector3(this.x, this.y, this.z);
    }

    @Override
    public int hashCode() {
        return HashHelper.getHashf(this.x, this.y, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vector3)) {
            return false;
        }
        Vector3 v = (Vector3) obj;
        return v.x == this.x &&
               v.y == this.y &&
               v.z == this.z;
    }

    @Override
    public String toString() {
        return "Vector3{" + this.x + "," + this.y + "," + this.z + "}";
    }
}
