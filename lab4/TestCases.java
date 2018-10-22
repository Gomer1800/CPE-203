import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Point;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

public class TestCases
{
   public static final double DELTA = 0.00001;
   @Test
   public void ExamPractice() {

   }
   /* put your tests here */
   @Test
   public void testWorkspaceMethods () {
       System.out.println("\nTESTING WORKSPACE METHODS");
       List<Point> myPoints = new ArrayList<Point>();
       myPoints.add(new Point(0,0));
       myPoints.add(new Point(0,1));
       myPoints.add(new Point(0,2));
       myPoints.add(new Point(2,2));
       myPoints.add(new Point(2,0));

       List<Shape> myShapes = new ArrayList<Shape>();
       myShapes.add(new Circle(1.0,new Point(0,0),Color.green));
       myShapes.add(new Rectangle(2.5, 2.0, new Point(2,2), Color.blue));
       myShapes.add(new Triangle(new Point(0,0), new Point(-1,0), new Point(-1,1), Color.black));
       
       // Constructors
       System.out.println("Default constructor, empty list");
       Workspace empty = new Workspace();
       System.out.println("Size of default constructor list: "+empty.size());
       System.out.println("Overloaded constructor");
       Workspace notEmpty =  new Workspace(myShapes); 
       System.out.println("Size of overloaded constructor list: "+notEmpty.size()); 
       // add()
       notEmpty.add(new ConvexPolygon(myPoints, Color.green));
       System.out.println("Size of overloaded constructor list: "+notEmpty.size()); 
       // get()
       assertEquals(1.0, ((Circle)notEmpty.get(0)).getRadius(), DELTA);
       assertEquals(new Point(0,0), ((Circle)notEmpty.get(0)).getCenter());
       assertEquals(Color.green, notEmpty.get(0).getColor()); 
       // size()
       System.out.println("notEmpty.getSize() returns "+notEmpty.size()); 
       // getCircles()
       System.out.println("notEmpty.getCirlces().size() returns "+notEmpty.getCircles().size());
       // getRectangles()
       System.out.println("notEmpty.getRectangles().size() returns "+notEmpty.getRectangles().size());
       // getTriangles()
       System.out.println("notEmpty.getTriangles().size() returns "+notEmpty.getTriangles().size());
       // getConvexPolygons()
       System.out.println("notEmpty.getConvexPolygons().size() returns "+notEmpty.getConvexPolygons().size());
       // getShapesByColor()
       int i = 1;
       System.out.println("getShapesByColor tests");
       System.out.println("There are "+notEmpty.getShapesByColor(Color.red).size()+" red shapes in the Workspace"); 
       System.out.println("There are "+notEmpty.getShapesByColor(Color.blue).size()+" blue shapes in the Workspace"); 
       System.out.println("There are "+notEmpty.getShapesByColor(Color.green).size()+" green shapes in the Workspace"); 
       System.out.println("There are "+notEmpty.getShapesByColor(Color.black).size()+" red shapes in the Workspace"); 
       for(Shape these: notEmpty.getShapesByColor(Color.green)){
           if(these instanceof Circle){
               System.out.println("Shape #"+i+" is Circle of color "+((Circle)these).getColor());
           }
           else if(these instanceof Rectangle){
               System.out.println("Shape #"+i+" is Rectangle of color "+((Rectangle)these).getColor());
           }
           else if(these instanceof Triangle){
               System.out.println("Shape #"+i+" is Triangle of color "+((Triangle)these).getColor());
           }
           else if(these instanceof ConvexPolygon){
               System.out.println("Shape #"+i+" is ConvexPolygon of color "+these.getColor());
           }
           i++;
       }
       // getAreaofAllShapes
       assertEquals(4.0 + Math.PI + 0.5 + 5.0 ,notEmpty.getAreaOfAllShapes(), DELTA);
       // getPerimeterOfAllShapes()
       assertEquals(8.0 + 2.0*Math.PI + 2+Math.sqrt(2.0) + 9.0 ,notEmpty.getPerimeterOfAllShapes(), DELTA);
       //
   }
   @Test
   public void testConvexPolygonMethods() {
       List<Point> myPoints = new ArrayList<Point>();
       myPoints.add(new Point(0,0));
       myPoints.add(new Point(0,1));
       myPoints.add(new Point(1,1));
       myPoints.add(new Point(1,0));

       ConvexPolygon myCP = new ConvexPolygon(myPoints, Color.yellow);
       System.out.println("\nTESTING CONVEX POLYGON METHODS");
       System.out.println("Interface Methods");
       // getColor()
       assertEquals(Color.yellow, myCP.getColor());
       // setColor()
       myCP.setColor(Color.red);
       assertEquals(Color.red, myCP.getColor());
       // getArea()
       assertEquals(1.0, myCP.getArea(),DELTA);
       // getPerimeter
       assertEquals(4.0, myCP.getPerimeter(), DELTA);
       // translate & getVertex()
       myCP.translate(new Point(-200,-200));
       assertEquals(new Point(-200,-200), myCP.getVertex(0));
       assertEquals(new Point(-200,-199), myCP.getVertex(1));
       assertEquals(new Point(-199,-199), myCP.getVertex(2));
       assertEquals(new Point(-199,-200), myCP.getVertex(3));
       
       System.out.println("Overwritten Methods");
   }
   
   @Test
   public void testTriangleMethods() {
       Triangle myT = new Triangle(new Point(0,1), new Point(0,0), new Point(1,0), Color.blue);
       System.out.println("\nTESTING TRIANGLE METHODS");
       System.out.println("Interface Methods");
       // getColor()
       assertEquals(Color.blue, myT.getColor());
       // setColor()
       myT.setColor(Color.red);
       assertEquals(Color.red, myT.getColor());
       // getArea()
       assertEquals(0.5, myT.getArea(),DELTA);
       // getPerimeter
       assertEquals(2.0+Math.sqrt(2), myT.getPerimeter(), DELTA);
       // translate & getVertex
       myT.translate(new Point(100,100));
       assertEquals(new Point(100,101), myT.getVertexA());
       assertEquals(new Point(100,100), myT.getVertexB());
       assertEquals(new Point(101,100), myT.getVertexC());
       
       System.out.println("Overwritten Methods");
   }

   @Test
   public void testCircleMethods() {
       Circle myCircle = new Circle (1.0, new Point(-1,2), Color.green);
       System.out.println("\nTESTING CIRCLE METHODS");
       assertEquals(Color.green, myCircle.getColor());
       System.out.print("Changed color from "+myCircle.getColor().toString());
       myCircle.setColor(Color.black);
       System.out.println(" to "+myCircle.getColor().toString());
       assertEquals(Math.PI*Math.sqrt(myCircle.getRadius()), myCircle.getArea(), DELTA);
       assertEquals(2*Math.PI*myCircle.getRadius(), myCircle.getPerimeter(), DELTA);
       myCircle.translate( new Point(2,5));
       System.out.println("Translating by (2, 5), new x: " +
               myCircle.getCenter().getX()+ " new y : " +
               myCircle.getCenter().getY() );
       assertEquals(1.0, myCircle.getRadius(), DELTA);
       myCircle.setRadius(3.0);
       System.out.println("Changed radius from 1.0 to "+myCircle.getRadius());
       assertEquals(new Point(1,7), myCircle.getCenter());
   }
   
   @Test
   public void testRectangleMethods() {
       Rectangle myR = new Rectangle(2.0, 4.0, new Point(-1,-1), Color.black);
       System.out.println("\nTESTING RECTANGLE METHODS");
       System.out.println("Interface Methods");
       // getColor()
       assertEquals(Color.black, myR.getColor());
       // setColor()
       myR.setColor(Color.red);
       assertEquals(Color.red, myR.getColor());
       // getArea()
       assertEquals(8.0, myR.getArea(),DELTA);
       // getPerimeter
       assertEquals(12.0, myR.getPerimeter(), DELTA);
       // translate
       myR.translate(new Point(1,1));
       assertEquals(new Point(0,0), myR.getTopLeft());
       
       System.out.println("Overwritten Methods");
       // getWidth()
       assertEquals(2.0, myR.getWidth(), DELTA);
       // setWidth()
       myR.setWidth(Math.PI);
       assertEquals(Math.PI, myR.getWidth(), DELTA); 
       // getHeight
       assertEquals(4.0, myR.getHeight(), DELTA);
       // setHeight 
       myR.setHeight(Math.PI);
       assertEquals(Math.PI, myR.getHeight(), DELTA); 
       // getTopLeft()
       assertEquals(new Point(0,0), myR.getTopLeft()); 
   }
   /* HINT - comment out implementation tests for the classes that you have not yet implemented */
   @Test
   public void testCircleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getRadius", "setRadius", "getCenter");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Circle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testRectangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getWidth", "setWidth", "getHeight", "setHeight", "getTopLeft");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         double.class, void.class, double.class, void.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[] {double.class}, new Class[0], new Class[] {double.class}, new Class[0]);

      verifyImplSpecifics(Rectangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testTriangleImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertexA", "getVertexB", "getVertexC");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class, Point.class, Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[0], new Class[0], new Class[0]);

      verifyImplSpecifics(Triangle.class, expectedMethodNames,
         expectedMethodReturns, expectedMethodParameters);
   }

   @Test
   public void testConvexPolygonImplSpecifics()
      throws NoSuchMethodException
   {
      final List<String> expectedMethodNames = Arrays.asList(
         "getColor", "setColor", "getArea", "getPerimeter", "translate",
         "getVertex");

      final List<Class> expectedMethodReturns = Arrays.asList(
         Color.class, void.class, double.class, double.class, void.class,
         Point.class);

      final List<Class[]> expectedMethodParameters = Arrays.asList(
         new Class[0], new Class[] {Color.class}, new Class[0], new Class[0], new Class[] {Point.class},
         new Class[] {int.class});

      verifyImplSpecifics(ConvexPolygon.class, expectedMethodNames,
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
         0, clazz.getFields().length);

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
