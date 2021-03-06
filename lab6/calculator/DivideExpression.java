class DivideExpression
    extends BinaryExpression
{
    private static final String OPERATOR = " / " ;

   public DivideExpression(final Expression lft, final Expression rht)
   {
       super(
               lft,
               rht,
               OPERATOR );
   }

   protected double _applyOperator( double [] evaluations ) {
       return evaluations[0] / evaluations[1];
   }
}

