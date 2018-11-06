/*
 * long from implementation of hashCode()
 * implement equals()
 */

import java.time.LocalTime;

class CourseSection
{
   private final String prefix;
   private final String number;
   private final int enrollment;
   private final LocalTime startTime;
   private final LocalTime endTime;

   public CourseSection(final String prefix, final String number,
      final int enrollment, final LocalTime startTime, final LocalTime endTime)
   {
      this.prefix = prefix;
      this.number = number;
      this.enrollment = enrollment;
      this.startTime = startTime;
      this.endTime = endTime;
   }

   public boolean equals( Object other ) {
       if ( other == null ) { return false; }

       if ( this.getClass() != other.getClass() ) { return false; }

       return this.hashCode() == ((CourseSection) other).hashCode();
   }

   public int hashCode() {
       int result =
           this.prefix.hashCode() + 
           this.number.hashCode() +
           ((Integer)this.enrollment).hashCode() +
           this.startTime.hashCode() +
           this.endTime.hashCode() ;
       return result;
   }

   // additional likely methods not defined since they are not needed for testing
}
