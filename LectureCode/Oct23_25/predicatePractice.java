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

public static List<Student> filterIt (List<Student> l, Predicate<STudent> p) {
    List<Student> results = new ArrayList<>();
    for (Student s : l) {
        if (p.test(s)) { results.add(s); }
    }
    return results;
}
