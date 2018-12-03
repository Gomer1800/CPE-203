import java.lang.Exception;

public class ZeroRadiusException extends CircleException
{
    public ZeroRadiusException() {
        super("zero radius");
    }
}
