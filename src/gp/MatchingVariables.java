package gp ;

import gp.dados.Frame;

import java.util.ArrayList ;
import java.util.List ;

import org.jgap.gp.terminal.Variable ;

public class MatchingVariables 
{
	  private static MatchingVariables instance = null ;
	  
	  private List<Variable> schemaA ;
	  private List<Variable> schemaB ;

	  /** A private Constructor prevents any other class from instantiating. */
	  private MatchingVariables( ) 
	  {
		  schemaA = new ArrayList<Variable>( ) ;
		  schemaB = new ArrayList<Variable>( ) ;
	  } /* Constructor MatchingVariables( ) */

	  /** Static 'instance' method */
	  public static MatchingVariables getInstance( ) 
	  {
		  if( null == instance ) 
		  {
			  instance = new MatchingVariables( ) ;
		  } /* if */
		  return instance ;
	 } /* Method getInstance( ) */
	  
	public List<Variable> getSchemaA( ) 
	{
		return schemaA ;
	} /* Method getSchema( ) */

	public void setSchemaA( List<Variable> schemaA ) 
	{
		this.schemaA = schemaA ;
	} /* Method setSchema( ) */
	
	public List<Variable> getSchemaB( ) 
	{
		return schemaB ;
	} /* Method getSchema( ) */

	public void setSchemaB( List<Variable> schemaB ) 
	{
		this.schemaB = schemaB ;
	} /* Method setSchema( ) */
	
	public void addVar( Variable v, List<Variable> schema ) 
	{
		schema.add( v ) ;
	} /* Method addVar( ) */
}