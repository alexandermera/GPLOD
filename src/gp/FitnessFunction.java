package gp;

import gp.dados.MatchedData;
import gp.function.MatchingType;
import gp.similarity.Metrica;

import java.util.List;

import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

/**
 * Fitness function for evaluating the produced fomulas, represented as GP
 * programs. The fitness is computed by calculating the result...
 */
public class FitnessFunction  extends GPFitnessFunction {
     
	private static final long serialVersionUID = -1562435648001631509L;
	public MatchingType tipo ;
	public MatchedData data ;
		
	protected double evaluate( final IGPProgram ind ) 
	{
		double error = 0.0000f ;
		Object[ ] noargs = new Object[ 0 ] ;
		// Provide the variables with the input number.
		// See method create(), declaration of "nodeSets" for where Vars are
		// defined.
		List<Variable> varsA = MatchingVariables.getInstance( ).getSchemaA( ) ;
		List<Variable> varsB = MatchingVariables.getInstance( ).getSchemaB( ) ;
		
		for( int i = 0; i < varsA.size( ); i++ )
		{
			varsA.get( i ).set( 1.0 ) ;
		} /* for */
		
		for( int i = 0; i < varsB.size( ); i++ )
		{
			varsB.get( i ).set( 1.0 ) ;
		} /* for */
		    
		try 
		{
			// Execute the GP program representing the function to be evolved.
			// As in method create(), the return type is declared as float (see
			// declaration of array "types").
			@SuppressWarnings("unused")
			double result = ind.execute_double(0, noargs);                        
			// Sum up the error between actual and expected result to get a defect
			// rate.                       
                        //System.exit(0) ;
                       // System.out.println( ind.toStringNorm( 0 ) ) ;
//                        if(result==2)
//                        {    System.out.println(ind.toStringNorm(0));
//                        }

			error = gp.similarity.Similarity.metric( ind.toStringNorm( 0 ), Metrica.COSINE, tipo, data ) ;
                        //System.out.println("fitness: "+error);
			// If the error is too high, stop evaluation and return worst error
			// possible.
			if( Double.isInfinite( error ) ) 
			{
				return Double.MAX_VALUE ;
			} /* if */
		}
		catch( ArithmeticException ex ) 
		{
			// This should not happen, some illegal operation was executed.
			System.out.println( ind ) ;
		    throw ex;
		} /* try */
		
		// In case the error is small enough, consider it perfect.
//		if (error < 0.2)
//		{
//			// error = 0.0d;
//		} /* if */
		return error ;
	} /* method evaluate( ) */
}
