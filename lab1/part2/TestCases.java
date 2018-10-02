import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;

   /*
    * This test is just to get you started.
    */
   @Test
   public void testGetX1()
   {
      assertEquals(1.0, new Point(1.0, 2.0).getX(), DELTA);
      // Point pointObj = new Point(1.0,1.0);
      // Point newPoint = pointObj.rotate90();
      // System.out.println(newPoint.getX());
      // System.out.println(newPoint.getY());
   }
   @Test
   public void testGetX2()
   {
      assertEquals(-5.5, new Point(-5.5, 1.0).getX(), DELTA);
   }
   @Test
   public void testGetX3()
   {
      assertEquals(100.5, new Point(100.5, -3.5).getX(), DELTA);
   }
/*/////////////////////////////////////////////////////////////////*/
   @Test
   public void testGetY1(){
      assertEquals(11.0, new Point(1.0, 11.0).getY(), DELTA);
   }
   @Test
   public void testGetY2(){
      assertEquals(-9.1, new Point(3.2, -9.1).getY(), DELTA);
   }
   @Test
   public void testGetY3(){
      assertEquals(3.14, new Point(1.0, 3.14).getY(), DELTA);
   }
/*/////////////////////////////////////////////////////////////////*/
   @Test
   public void testGetRadius1(){
      assertEquals(Math.sqrt(8), new Point(2.0,2.0).getRadius(), DELTA);
   }
   @Test
   public void testGetRadius2(){
      assertEquals(Math.sqrt(2), new Point(1.0,1.0).getRadius(), DELTA);
   }
   @Test
   public void testGetRadius3(){
      assertEquals(5, new Point(3.0,4.0).getRadius(), DELTA);
   }
/*/////////////////////////////////////////////////////////////////*/
   @Test
   public void testGetAngle1(){
      assertEquals(Math.PI/4, new Point(2.0,2.0).getAngle(), DELTA);
   }
   @Test
   public void testGetAngle2(){
      assertEquals(Math.PI/3, new Point(1,Math.sqrt(3)).getAngle(), DELTA);
   }
   @Test
   public void testGetAngle3(){
      assertEquals(Math.atan(-1.0/2.0), new Point(2.0,-1.0).getAngle(), DELTA);
   }
/*/////////////////////////////////////////////////////////////////*/
   @Test
   public void testRotate90_1(){
      Point pointObj = new Point(1.0,1.0);
      Point newPoint = pointObj.rotate90();
      System.out.println();
      System.out.println("old X: "+pointObj.getX()+
                         "old Y: "+pointObj.getY());
      System.out.println("new X: "+newPoint.getX()+
                         "new Y: "+newPoint.getY());
   }
   @Test
   public void testRotate9_2(){
      Point pointObj = new Point(-1.0,1.0);
      Point newPoint = pointObj.rotate90();
      System.out.println();
      System.out.println("old X: "+pointObj.getX()+
                         "old Y: "+pointObj.getY());
      System.out.println("new X: "+newPoint.getX()+
                         "new Y: "+newPoint.getY());
   }
   @Test
   public void testRotate90_3(){
      Point pointObj = new Point(1.0,-1.0);
      Point newPoint = pointObj.rotate90();
      System.out.println();
      System.out.println("old X: "+pointObj.getX()+
                         "old Y: "+pointObj.getY());
      System.out.println("new X: "+newPoint.getX()+
                         "new Y: "+newPoint.getY());
   }
/*/////////////////////////////////////////////////////////////////*/
   /*
    * The tests below here are to verify the basic requirements regarding
    * the "design" of your class.  These are to remain unchanged.
    */

   @Test
   public void testImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getX",
         "getY",
         "getRadius",
         "getAngle",
         "rotate90"
         );

      final List<Class> expectedMethodReturns = Arrays.asList(
         double.class,
         double.class,
         double.class,
         double.class,
         Point.class
         );

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0],
         new Class[0],
         new Class[0],
         new Class[0],
         new Class[0]
         );

      verifyImplSpecifics(Point.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   private static void verifyImplSpecifics(
      final Class<?> clazz,
      final List<String> expectedMethodNames,
      final List<Class> expectedMethodReturns,
      final List<Class[]> expectedMethodParameters)
      throws NoSuchMethodException
   {
      assertEquals("Unexpected number of public fields",
         0, Point.class.getFields().length);

      final List<Method> publicMethods = Arrays.stream(
         clazz.getDeclaredMethods())
            .filter(m -> Modifier.isPublic(m.getModifiers()))
            .collect(Collectors.toList());

      assertEquals("Unexpected number of public methods",
         expectedMethodNames.size(), publicMethods.size());

      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodReturns.size());
      assertTrue("Invalid test configuration",
         expectedMethodNames.size() == expectedMethodParameters.size());

      for (int i = 0; i < expectedMethodNames.size(); i++)
      {
         Method method = clazz.getDeclaredMethod(expectedMethodNames.get(i),
            expectedMethodParameters.get(i));
         assertEquals(expectedMethodReturns.get(i), method.getReturnType());
      }
   }
}
