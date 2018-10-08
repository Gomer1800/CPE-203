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
		double newX = xPos*Math.cos(Math.PI/2)-yPos*Math.sin(Math.PI/2);
		double newY = xPos*Math.sin(Math.PI/2)+yPos*Math.cos(Math.PI/2);
		//System.out.println("x: " + newX + "y: " + newY);
		return new Point (newX, newY);
	}
}
