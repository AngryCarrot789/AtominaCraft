package reghzy.atominacraft.maths;

import reghzy.atominacraft.utils.HashHelper;

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

    /**
     * sets this instances values to the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3 set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * sets this instances values to the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3 set(Vector3 v) {
        this.set(v.x, v.y, v.z);
        return this;
    }

    /**
     * multiplies this instances values by the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3 multiply(float x, float y, float z) {
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    /**
     * multiplies this instances values by the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3 multiply(Vector3 v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }

    /**
     * adds this instances values by the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3 add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    /**
     * adds this instances values by the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3 add(Vector3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    /**
     * subtracts this instances values by the given values and then returns this instance, not a copy
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Vector3 subtract(float x, float y, float z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    /**
     * subtracts this instances vaalues by the given vector's values and then returns this instance, not a copy
     * @param v
     * @return
     */
    public Vector3 subtract(Vector3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    /**
     * returns the squared magnitude of the x, y and z values
     * @return
     */
    public float magnitudeSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * returns the magnitude of the x, y and z values
     * @return
     */
    public float magnitude() {
        return (float) Math.sqrt(magnitudeSquared());
    }

    /**
     * returns a normalised copy of this vector, where the x, y and z values are never smaller than 0 or bigger than 1
     * @return
     */
    public Vector3 normalised() {
        float mag = magnitude();
        if (mag == 0)
            return new Vector3(0, 0, 0);
        return new Vector3(this.x / mag, this.y / mag, this.z / mag);
    }

    /**
     * returns the dot produce between this vector and another vector's value
     * @param v
     * @return
     */
    public float dot(Vector3 v) {
        return this.x * v.x +
               this.y * v.y +
               this.z * v.z;
    }

    /**
     * returns a new vector which is the cross product of this vector and another vector's values
     * @param v
     * @return
     */
    public Vector3 cross(Vector3 v) {
        return new Vector3(
                this.y * v.z - this.z * this.y,
                this.z * v.x - this.x * this.z,
                this.x * v.y - this.y * this.x);
    }

    /**
     * returns the angle between this vector and another vector
     * @param v
     * @return
     */
    public float angle(Vector3 v) {
        return (float) Math.acos(this.normalised().dot(v));
    }

    /**
     * returns whether this vector's x, y and z values are between -1 and 1
     * @return
     */
    public boolean isUnit() {
        return x >= -1.0f && x <= 1.0f &&
               y >= -1.0f && y <= 1.0f &&
               z >= -1.0f && z <= 1.0f;
    }

    /**
     * Clamps any negative number to 0, leaves positive numbers
     * as they are, and returns this vector instance (not a copy)
     * @return
     */
    public Vector3 clampPositive() {
        if (this.x < 0)
            this.x = 0;
        if (this.y < 0)
            this.y = 0;
        if (this.z < 0)
            this.z = 0;
        return this;
    }

    /**
     * If the X/Y/Z value is 0, it becomes 1. otherwise, if it's bigger than 0, it becomes 0
     * @return
     */
    public Vector3 invertUnit() {
        if (this.x == 0) {
            this.x = 1;
        }
        else {
            this.x = 0;
        }
        if (this.y == 0) {
            this.y = 1;
        }
        else {
            this.y = 0;
        }
        if (this.z == 0) {
            this.z = 1;
        }
        else {
            this.z = 0;
        }
        return this;
    }

    /**
     * returns a vector containing the values of a multiplication between the given matrix and the given vector
     * @param m
     * @param v
     * @return
     */
    public static Vector3 multiplyDirection(Matrix4 m, Vector3 v) {
        return new Vector3(m.m[0] * v.x + m.m[1] * v.y + m.m[2] * v.z,
                           m.m[4] * v.x + m.m[5] * v.y + m.m[6] * v.z,
                           m.m[8] * v.x + m.m[9] * v.y + m.m[10] * v.z);
    }

    /**
     * returns a clone of this vector, containing the same x, y and z values
     * @return
     */
    @Override
    public Vector3 clone() {
        return new Vector3(this.x, this.y, this.z);
    }

    /**
     * returns a hashcode of the x, y and z values all into one. this hashcode only has a precision of x, y and z values up to 1023
     * @return
     */
    @Override
    public int hashCode() {
        return HashHelper.getHash3f(this.x, this.y, this.z);
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
