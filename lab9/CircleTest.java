public class CircleTest 
{
    public static void main(String[] args)
    {
        // Circle c1 = new Circle(-50);
        try {
            Circle c2 = new Circle(-50);
            System.out.println(c2);
        }
        /*
        catch (NegativeRadiusException e) {
            System.out.println(e.getMessage()+": "+e.radius());
        }
        catch (ZeroRadiusException e) {
            System.out.println(e.getMessage());
        }
        */
        catch (CircleException e) {
            System.out.println(e.getMessage());
            return;
        }
        finally {
            System.out.println("In finally.");
        }
        System.out.println("Done.");
    }
}
