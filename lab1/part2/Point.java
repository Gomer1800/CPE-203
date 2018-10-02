public class Point
{
	private double xPos;
	private double yPos;
	private double radius;
	private double angle;

	public Point(double x, double y) {
		xPos = x;
		yPos = y;
		radius = Math.hypot(xPos,yPos);
		angle = Math.atan(yPos/xPos);
	}
	public double getX(){
		return xPos;
	}
	public double getY(){
		return yPos;
	}
	public double getRadius(){
		return radius;
	}
	public double getAngle(){
		return angle;
	}
	public Point rotate90(){
		double newX = xPos*Math.cos(angle + Math.PI/2)-yPos*Math.sin(angle + Math.PI/2);
		double newY = xPos*Math.sin(angle + Math.PI/2)+yPos*Math.cos(angle + Math.PI/2);
		return new Point (newX, newY);
	}
}
