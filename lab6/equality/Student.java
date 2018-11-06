import java.util.List;

class Student
{
   private final String surname;
   private final String givenName;
   private final int age;
   private final List<CourseSection> currentCourses;

   public Student(final String surname, final String givenName, final int age,
      final List<CourseSection> currentCourses)
   {
      this.surname = surname;
      this.givenName = givenName;
      this.age = age;
      this.currentCourses = currentCourses;
   }

   public int hashCode() {
       int result = 
           this.surname.hashCode() +
           this.givenName.hashCode() +
           ((Integer)this.age).hashCode();
       for (CourseSection course : this.currentCourses) {
           result += course.hashCode();
       }

       return result;
   }

   public boolean equals( Object other ) {
       if (other == null) { return false; }

       if (this.getClass() != other.getClass()) { return false; }

       return this.hashCode() == ((Student) other).hashCode();
   }
}
