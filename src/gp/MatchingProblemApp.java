  package gp;

import gp.dados.MatchedData;
import gp.function.MatchingType;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;


public class MatchingProblemApp 
{
        GPGenotype gp ;
	/**
	 * @param args
	 * @throws InvalidConfigurationException
	 */
	public  MatchingProblemApp( MatchedData md, MatchingType tipo ) throws InvalidConfigurationException
	{		
        MatchingProblemConfiguration conf = new MatchingProblemConfiguration( ) ;
        GPProblem problem = new MatchingProblem( conf.getGonfig( md, tipo ) ) ;	    
        List< Variable > varsA = MatchingVariables.getInstance( ).getSchemaA( ) ;
        varsA.clear( ) ;
        
        ArrayList< Integer > Natr = new ArrayList< Integer >( ) ;
        
        switch( tipo )
        {
        	case A_PARA_B:
        		Natr = md.getAtributoB( ) ;
        		break ;
        	case B_PARA_A:
        		Natr = md.getAtributoA( ) ;
        		break ;
        	default:
        			return ;
        } /* switch */
        
        //Criacao dos terminais da programacao genetica, i.e., os atributos do esquema.
        for( int x = 0; x < Natr.size( ); x++ )
        {
            varsA.add( Variable.create( conf, "a" + Natr.get( x ) + "", CommandGene.FloatClass ) ) ;
        } /* for */
        
	    // --------------------------------------------------------------------	    
	    gp = problem.create( ) ;
	    gp.setVerboseOutput( true ) ;
	    // Start the computation with maximum 800 evolutions.
	    // if a satisfying result is found (fitness value almost 0), JGAP stops
	    // earlier automatically.
	    // --------------------------------------------------------------------
	    gp.evolve( 100 ) ;
	    // Print the best solution so far to the console.
	    // ----------------------------------------------
            // gp.outputSolution( gp.getAllTimeBest()) ;
	    // Create a graphical tree of the best solution's program and write it to
	    // a PNG file.
	    // ----------------------------------------------------------------------
        problem.showTree( gp.getAllTimeBest( ), "macthingProblemResult.png" ) ;
	}
	

    public MatchingProblemApp(MatchedData md, MatchingType tipo, int populationSize, int generation,
                double mutationProb, double reproductionProb, double crossoverProb)
    {
        try {
            MatchingProblemConfiguration conf = new MatchingProblemConfiguration();
            GPProblem problem = new MatchingProblem(conf.getGonfig(md, tipo, populationSize, mutationProb, reproductionProb, crossoverProb));
            List<Variable> varsA = MatchingVariables.getInstance().getSchemaA();
            varsA.clear();
            ArrayList<Integer> Natr = new ArrayList<Integer>();
            switch (tipo) {
                case A_PARA_B:
                    Natr = md.getAtributoB();
                    break;
                case B_PARA_A:
                    Natr = md.getAtributoA();
                    break;
                default:
                    return;
            } /* switch */
            //Criacao dos terminais da programacao genetica, i.e., os atributos do esquema.
            for (int x = 0; x < Natr.size(); x++) {
                varsA.add(Variable.create(conf, "a" + Natr.get(x) + "", CommandGene.FloatClass));
            } /* for */ /* for */
            // --------------------------------------------------------------------
            gp = problem.create();
            gp.setVerboseOutput(true);
            // Start the computation with maximum 800 evolutions.
            // if a satisfying result is found (fitness value almost 0), JGAP stops
            // earlier automatically.
            // --------------------------------------------------------------------
            gp.evolve(generation);
            // Print the best solution so far to the console.
            // ----------------------------------------------
            // gp.outputSolution( gp.getAllTimeBest()) ;
            // Create a graphical tree of the best solution's program and write it to
            // a PNG file.
            // ----------------------------------------------------------------------
            problem.showTree(gp.getAllTimeBest(), "macthingProblemResult.png");
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(MatchingProblemApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   
    public String getSolution( ) 
    {
        return gp.getAllTimeBest( ).toStringNorm( 0 ) ;
    }
    
    public double getFitness( ) 
    {
        return gp.getFittestProgramComputed( ).getFitnessValue( ) ;
    }
}