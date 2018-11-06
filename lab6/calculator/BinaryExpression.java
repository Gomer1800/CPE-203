/*
 * AddExpression(), DivideExpression(), MultiplyExpression(), SubtractExpresion()
 
X  * Create a new class named BinaryExpression. This class should inherit from Expression.
X * Each of the classes listed above must be modified to inherit from BinaryExpression.
X  * Now lift the common data out of these subclasses into the common parent class BinaryExpression. Make this data private.
X * With the common data in the parent, the common functionality will also be lifted. Move the evaluate and toString methods into the BinaryExpression class.
X  * Add an additional argument to the BinaryExpression constructor. Modify the subclasses to pass a String, representing the operator, to the parent class . Update BinaryExpression's toString to use this operator string.
X  * Modify the code in evaluate in the BinaryExpression class to evaluate each operand (i.e., lft and rht) and to pass the results to a new, protected method named _applyOperator. This new method will not be defined in BinaryExpression, but must be declared (what sort of method is this?). In each subclass, implement this new method as is appropriate for the subclass's operator.
 */

public abstract class BinaryExpression implements Expression
{
    protected final Expression lft;
    protected final Expression rht;
    protected final String operator;

    protected BinaryExpression (
            final Expression lft,
            final Expression rht,
            final String operator) {
        this.lft = lft;
        this.rht = rht;
        this.operator = operator;
            }

    // methods
    public String toString()  { return "(" + lft + operator + rht + ")" ; }

    public double evaluate( final Bindings bindings ) {
        double evaluations [] = new double[] { lft.evaluate( bindings ), rht.evaluate( bindings) } ;
        return _applyOperator( evaluations ) ;
    }

    protected abstract double _applyOperator( double [] evaluations ) ;
}
