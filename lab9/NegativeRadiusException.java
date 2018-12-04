import java.lang.Exception;

public class NegativeRadiusException extends CircleException
{
    private double radius;

    public NegativeRadiusException( double r) {
        super("negative radius");
        this.radius = r;
    }

    public double radius() {
        return this.radius;
    }
}
