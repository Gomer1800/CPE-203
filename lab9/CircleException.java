import java.lang.Exception.*;

public class CircleException extends RuntimeException 
// This is an unchecked Exception. Does not require throw clause in method
// Java community suggests that we only use declare unchecked exceptions for errors
// we do not expect the program to recover from
{
    public CircleException (String message) {
        super(message);
    }
}
