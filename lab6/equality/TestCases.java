import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.time.LocalTime;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

public class TestCases
{
   @Test
   public void testExercise1()
   {
       System.out.println("\nTESTING COURSESECTION EQUALS");
      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertTrue(one.equals(two));
      assertTrue(two.equals(one));
   }

   @Test
   public void testExercise2()
   {
       System.out.println("\nTESTING COURSESECTION EQUALS");

      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(1, 10), LocalTime.of(2, 0));

      assertFalse(one.equals(two));
      assertFalse(two.equals(one));
   }

   @Test
   public void testExercise3()
   {
       System.out.println("\nTESTING COURSESECTION HASHCODE");

      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));
      final CourseSection two = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 40), LocalTime.of(11, 0));

      assertEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExercise4()
   {
       System.out.println("\nTESTING COURSESECTION HASHCODE");

      final CourseSection one = new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0));
      final CourseSection two = new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0));

      assertNotEquals(one.hashCode(), two.hashCode());
   }

   @Test
   public void testExcercise5() {
       System.out.println("\nTESTING STUDENT EQUALS");
       // courses of student 1
       List<CourseSection> currentCourses1 =  new ArrayList<>();
       currentCourses1.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;
       currentCourses1.add(new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;
       // courses of student 2
       List<CourseSection> currentCourses2 =  new ArrayList<>();
       currentCourses2.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;
       currentCourses2.add(new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;

       Student student1 = new Student("Gomez", "Luis", 27, currentCourses1 ) ;
       Student student2 = new Student("Gomez", "Luis", 27, currentCourses2 ) ;
       
       assertTrue(student1.equals(student2));
       assertTrue(student2.equals(student1));

   }

   @Test
   public void testExcercise6() {
       System.out.println("\nTESTING STUDENT HASHCODE");
       // courses of student 1
       List<CourseSection> currentCourses1 =  new ArrayList<>();
       currentCourses1.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;
       currentCourses1.add(new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;
       // courses of student 2
       List<CourseSection> currentCourses2 =  new ArrayList<>();
       currentCourses2.add(new CourseSection("CSC", "203", 35,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;
       currentCourses2.add(new CourseSection("CSC", "203", 34,
         LocalTime.of(9, 10), LocalTime.of(10, 0))) ;

       Student student1 = new Student("Gomez", "Luis", 27, currentCourses1 ) ;
       Student student2 = new Student("Gomez", "Luis", 27, currentCourses2 ) ;
       
       assertEquals( student1.hashCode(), student2.hashCode());
   }

}
