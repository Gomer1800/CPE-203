import java.util.List;
import java.util.ArrayList;

public class ListPractice1 {
    public static void main (String[] args)
    {
        List<Sphere> mySpheres = new ArrayList<Sphere>();
        mySpheres.add(new Sphere(1.0));
        mySpheres.add(new Sphere(2.0));
        mySpheres.add(new Sphere(3.0));
        mySpheres.add(new Sphere(4.0));

        for (Sphere s: mySpheres) {
            System.out.println("This sphere has a radius of "+s.getRadius());
        }


        for (int i=0; i<mySpheres.size(); i++) {
            System.out.println("This sphere has a radius of "+mySpheres.get(i).getRadius());
        }

        String [] coolNames = {"Ron", "Harry", "Hermione"};
        
        for (String name: coolNames) {
            System.out.println("Hello my name is "+name);
        }
    }
}
