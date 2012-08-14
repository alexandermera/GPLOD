package gp;

import java.util.List;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class MatchingProblem extends GPProblem
{
	GPConfiguration conf ;
	
	public MatchingProblem( GPConfiguration a_conf ) throws InvalidConfigurationException
	{
		super( a_conf ) ;
		conf = a_conf ;
	}
/**
* This method is used for setting up the commands and terminals that can be
* used to solve the problem.
*
* @return GPGenotype
* @throws InvalidConfigurationException
*/
	@SuppressWarnings("unchecked")
	public GPGenotype create( ) throws InvalidConfigurationException 
	{
	    
		// At first, we define the return type of the GP program.
	    Class[] types = {
	        // Return type of result-producing chromosome
	        CommandGene.FloatClass      
	         };
	    // Then, we define the arguments of the GP parts. Normally, only for ADF's
	    // there is a specification here, otherwise it is empty as in first case.
	    // -----------------------------------------------------------------------
	    Class[][] argTypes = {
	        // Arguments of result-producing chromosome: none
	        {}
	    };
	    // Next, we define the set of available GP commands and terminals to use.
	    // Please see package org.jgap.gp.function and org.jgap.gp.terminal
	    // You can easily add commands and terminals of your own.
	    // ----------------------------------------------------------------------
	    
	    List<Variable> varsA = MatchingVariables.getInstance().getSchemaA( ) ;
	    List<Variable> varsB = MatchingVariables.getInstance().getSchemaB( ) ;
	    
	    
	    Integer max ;
	    if( varsA.size() > varsB.size( ) )
	    {
	    	max = varsA.size( ) ;
	    } 
	    else
	    {
	    	max = varsB.size( ) ;
	    } /* if */
	    
	    //numero de funcoes de GP usadas
	    Integer nGP = 6 ;
	    CommandGene[ ][ ] nodeSets = new CommandGene[ 1 ][ max + nGP ] ;
	    
	    //Atribuindo variaveis para os CommandGenes do esquema A
	    for( int i = 0; i < varsA.size( ); i++ )
	    {
	    	nodeSets[ 0 ][ i ] = varsA.get( i ) ;
	    } /* for */
	    
	    //Atribuindo funcoes para os CommandGenes do esquema A
	    nodeSets[ 0 ][ varsA.size( ) + nGP - 1 ] = new Add( conf, CommandGene.FloatClass ) ;
	    nodeSets[ 0 ][ varsA.size( ) + nGP - 2 ] = new org.jgap.gp.function.Subtract( conf, CommandGene.FloatClass ) ;
	    nodeSets[ 0 ][ varsA.size( ) + nGP - 3 ] = new org.jgap.gp.function.Multiply( conf, CommandGene.FloatClass ) ;	    
	    nodeSets[ 0 ][ varsA.size( ) + nGP - 4 ] = new Terminal(conf, CommandGene.FloatClass, 1d, 1d, false);	
	    nodeSets[ 0 ][ varsA.size( ) + nGP - 5 ] = new org.jgap.gp.function.Divide( conf, CommandGene.FloatClass ) ;
	    nodeSets[ 0 ][ varsA.size( ) + nGP - 6 ] = new gp.function.Concatenation( conf, CommandGene.FloatClass ) ;
	    
	    //Atribuindo variaveis para os CommandGenes do esquema B
	 /*   for( int i = 0; i < varsB.size( ); i++ )
	    {
	    	nodeSets[ 1 ][ i ] = varsB.get( i ) ;
	    } /* for */
	    
	    //Atribuindo funcoes para os CommandGenes do esquema B
	  //  nodeSets[ 1 ][ varsB.size( ) + nGP - 1 ] = new Add( conf, CommandGene.FloatClass ) ;
	   /* nodeSets[ 1 ][ varsB.size( ) + nGP - 2 ] = new org.jgap.gp.function.Subtract( conf, CommandGene.FloatClass ) ;
	    nodeSets[ 1 ][ varsB.size( ) + nGP - 3 ] = new org.jgap.gp.function.Multiply( conf, CommandGene.FloatClass ) ;	    
	    nodeSets[ 1 ][ varsB.size( ) + nGP - 4 ] = new Terminal(conf, CommandGene.FloatClass, 0d, 100.0d, false);	
	    nodeSets[ 1 ][ varsB.size( ) + nGP - 5 ] = new org.jgap.gp.function.Divide( conf, CommandGene.FloatClass ) ;*/
	    
	    // Create genotype with initial population. Here, we use the declarations
	    // made above:
	    // Use one result-producing chromosome (index 0) with return type float
	    // (see types[0]), no argument (argTypes[0]) and several valid commands and
	    // terminals (nodeSets[0]). Contained in the node set is an ADF at index 1
	    // in the node set (as declared with the second parameter during
	    // ADF-construction: new ADF(..,1,..)).
	    // The ADF has return type float (types[1]), three input parameters of type
	    // float (argTypes[1]) and exactly one function: Add3 (nodeSets[1]).
	    // ------------------------------------------------------------------------
	    return GPGenotype.randomInitialGenotype( conf, types, argTypes, nodeSets, 20, false ) ;
	}
}
