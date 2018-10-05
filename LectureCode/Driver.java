import javafx.scene.shape.SphereComparable;
import java.awt.*;
//javafx: package
//scene: namespace
//shape:
//Sphere: actual class

/*
*/
public class Driver {

    public static void main(String[] args) { // command line parameters can be passed in via String
        // static means there is only one of this line for the whole class, not one for each instance
        Sphere rSphere; // pointer to an object reference, currently NULL
        rSphere = new Sphere(5.0); // pointer assigned
        Sphere s1 = new Sphere(); // s1 pointer assigned new Sphere reference
        Sphere s2 =  new Sphere(5.0);
        Sphere [] spheres = new Sphere [10]; // sphere is pointing to an array of sphere references
        // spheres[0] is NULL etc.. Have not been initialized yet
        Sphere [] arr = new Sphere [10];

        for(int i=0;i<10;i++){
            spheres[i] = new Sphere();
        }
        /* if(rSphere.equals(s2)) {} // .equals inherited from object class, everything inherits from object class
        * */
        // we must override toString(), see Sphere.java
        if(rSphere.equals(s2)){
            System.out.println("TRUE");
        }
        else {
            System.out.println("FALSE");
        }
        public Sphere getBiggest (Sphere [] arr) {
            Sphere biggest = arr[0];
            for (int i=1; i<arr.length; i++) {
                if (biggest.compareTo(arr[i]) < 0) {
                    biggest = arr[i];
            }
        }
        return biggest;
    }
}
