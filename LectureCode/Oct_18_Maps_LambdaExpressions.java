public <List <string> highEnrollmentStudents( Map <String , List<Course>> courseListByStudent, int unitThreshold) {
    List <String> overEnrolledStudents = new LinkedList<>(); // Linked list takes more time & memory than arraylist, "natures perfect data structure' is the array typically
   for(Map.Entry<String, List<Course>> entry : Map.CourseListByStudent.entrySet()) { // Map.Entry is a subtype
       if(entrolledUnits(entry.getValue()) >= unitThreshold) {
           overEnrolledStudents.add(entry.getKey());
       }
   }
   return overEnrolledStudents;
}

// within which map?
private int enrolledUnits( List<Course> courseList ) {
    int units = 0;
    for (Course c : courseList) {
        units = units + c.getUnits();
    }
    return units;
}

// Lambda Expressions, assume we have the following:
// Students and their age, GPA, name
// how do we sort them? We can do so by any of the three attributes ascending/descending. 6 methods total
// it would be nice if we had ONE method that takes a selector parameter
//
// Lets consider comparator interface, not to be confused with comparable interface
// has a compare method

public class StudentGPAComparator implements Comparator <Student> {
    public int compare( student s1, student s2) {
        if (s1.getGPA() < s2.getGPA()) { return -1; }
        else if (s1.getGPA() > s2.getGPA()) { return 1; }
        return 0; 
    }
}

Collection.sort(Student students, new StudentGPAComparator() );
