public class StudentAgeComparator implements Comparator <Student> {
    public int compare (Student s1, Student s2) { // MUST implement compare method for Comparator interface
        return s1.getAge()-s2.getAge(); // return of comparator determines order elements should be
    }
}

// How to declare one
StudentAgeComparator <Student> comp = new StudentAgeComparator<>();
// lambda expressions condenses the above code block into one line
// lambda expressions possible ONLY when interface has a SINGLE REQUIRED method
Comparator <Student> comp = (Student s1m , Student s2) -> s1.getAge()-s2.getAge();
Comparator <Student> comp = (s1, s2) -> s1.getAge()-s2.getAge();

// Collections
Collections.sort( studentList, comp); // collections has an overloaded sort method compatible with Comparator comp

// how to use our Comparator
if( comp.compare( st[i], st[i+1]) < 0) {
    System.out.print(st[i] + " is smaller");
}

// Use natural order, only possible for objects with defined compareTo method
// Double color is a key extraction
Comparator <Student> comp = Student::getAge;

// What if we wanted to order students by name, How? Last Name, First Name, Middle Name ...
// Use chain
Comparator <Student> comp1 = Comparator.comparing( Student::getLast); // compiler can figure out getLast is a method, () not needed
Comparator <Student> comp2 =  comp1.thenComparing( Student::getFirst);
Comparator <Student> comp3 = comp2.thenComparing( Student::getMiddle);
// or using one statement, we only define the most important comparator
Comparator <Student> comp = ((Comparator.comparing(Student::getLast)).thenComparing(Student::getFirst)).thenComparing(Student::getMiddle);
//
... = comp.compare(s5, s7);

// Predicate, is an interface with a required method test()
// test() takes an object and returns a boolean
Predicate <Student> p = (Student s)->s.getGPA() > 3.0;
for (...) { 
    if (p.test(st[i])) { System.out.println(st[i] + " is a good student"); }
}

// Predicate p, above, is defined generically so we can use it across different methods
public static List<Student> generateHonorRoll( List<Student> l, Predicate<Student> p) {
                      List<String> purchases = purchasedProducts;
    List<Student> honorRoll = new ArrayList<>();
    for(Student s : l) {
        if (p.test(s)) { honorRoll.add(s); }
    }
    return honorRoll;
}
