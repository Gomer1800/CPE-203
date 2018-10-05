import java.awt.*; // awt: window manager library

public class Sphere implements Comparable <Sphere> { // public indicates this will inherit the methods from object
    //ATTRIBUTES
    private double radius; // instance variable, each new object has a copy of this. Private: outside world cant
    // access this, users forced to use our methods which might have input validation.
    private Color tint;

    // CONSTRUCTORS
    public Sphere() { // constructor: this method returns a pointer to the object
        radius = 1.0;
        tint = Color.GREEN;
    }

    public Sphere(double r) {
        if (r > 0) { // Input validation for input r value
            radius = r;
        } else {
            radius = 1;
        }
        tint = Color.GREEN;
    }

    // METHODS
    public double getRadius() { // get method
        return radius;
    }
    public Color getColor() {
        return tint;
    }
    public boolean setRadius(double r) { // returns bool for input validation
        if (r > 0) {
            radius = r;
            return true;
        } else {
            return false;
        }
    }
    public void setColor(Color c) {
        tint = c;
    }
    public boolean scale(double scaleFactor) {
        if (scaleFactor > 0) {
            radius = radius * scaleFactor; // DONT DO radius *= scaleFactor, POINTS!
            return true;
        } else {
            return false;
        }
    }
    public double surfaceArea() {
        return 4 * Math.PI * Math.pow(radius, 2);
    }
    // Overriding a built-in function, toString()
    public String toString() { // Java knows this is inheriting from object class
        String s = new String(
                "Sphere with radius " + radius +
                        " and color " + tint.toString());
        return s;
    }
    //.equals is a class method not instance method, because the references are passed in we should use static method
    // using static is the OO way for this case.
    public static boolean isSame(Sphere s1, Sphere s2) {
        if (s1.radius == s2.radius && s1.tint.equals(s2.tint)) {
            return true;
        } else {
            return false;
        }
    }
    // we want one .equals method, overwriting requires same parameter as Object.equals method
    public boolean equals(Object o) { // if we had passed a Sphere reference, we would be overloading not overwriting
        if (o == null) {
            return false;
        }
        // We need to check what the class of o is... using instanceof
        if (!(o instanceof Sphere)) {
            return false;
        }
        Sphere other = (Sphere) o; // we're going to point this to o object, because the compiler doesnt know any better
        return this.radius == other.radius && this.tint.equals(other.tint);
    }
    /*
    COMPARABLE
    This sphere class has the added feature of being Comparable.
    -int,0,+int are the standard returns of the compareTo method within a Comparable class.
    */
    public int compareTo(Sphere other) {
        if (this.radius < other.radius) {
            return -1;
        } else if (this.radius == other.radius) {
            return 0;
        } else return 1;
        // return (int)(this.radius-other.radius); // efficient and functionally equivalent
    }
}