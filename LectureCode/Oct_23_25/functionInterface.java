// Function interface is a little more general, takes something of one type and produces something of another type
// has methods, apply() and compose()

public static Function <Student, String> f = s-> s.getFirst() + " " + s.getMiddle() + " " + s.getLast();

// using the Function
for (Student st: studentList) {
    System.out.println(f.apply(st));
}

// Consumer interface, takes something of type something and returns nothing
// has single method accept()

Consumer <String> c = s-> System.out.println(s);

for (Student st: studentList) {
    c.accept (f.apply(st));
}

// 
Function <String, String> f1 = st-> st.toUpperCase();

Function <Student, String> compose = com-> f1.compose(f);
