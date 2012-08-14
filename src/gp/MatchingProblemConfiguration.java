package gp;

import gp.dados.MatchedData;
import gp.function.MatchingType;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;

public class MatchingProblemConfiguration extends GPConfiguration 
{
	private static final long serialVersionUID = 8200151418255400641L;
	
	public MatchingProblemConfiguration( ) throws InvalidConfigurationException 
	{}
	
	public GPConfiguration getGonfig( MatchedData md, MatchingType tipo ) throws InvalidConfigurationException
	{
		GPConfiguration conf = new GPConfiguration( ) ;
                conf.reset( ) ;
	    // We use a delta fitness evaluator because we compute a defect rate, not
	    // a point score!
		conf.setGPFitnessEvaluator( new DeltaGPFitnessEvaluator( ) ) ;
		conf.setMinInitDepth( 5 ) ;
		conf.setPopulationSize( 100000 ) ;
		conf.setMaxCrossoverDepth( 2 ) ;
        conf.setMutationProb( ( float ) 0.8 ) ;
        conf.setReproductionProb((float) 0.8);
        conf.setCrossoverProb((float)0.6);
        FitnessFunction ff = new FitnessFunction( ) ;
        ff.tipo = tipo ;
        ff.data = md ;
		conf.setFitnessFunction( ff ) ;
		conf.setStrictProgramCreation( true ) ;
		return conf ;
	}
	public GPConfiguration getGonfig( MatchedData md, MatchingType tipo, int populationSize,
                double mutationProb, double reproductionProb, double crossoverProb ) throws InvalidConfigurationException
	{
		GPConfiguration conf = new GPConfiguration( ) ;
                conf.reset( ) ;
	    // We use a delta fitness evaluator because we compute a defect rate, not
	    // a point score!
		conf.setGPFitnessEvaluator( new DeltaGPFitnessEvaluator( ) ) ;
		conf.setMinInitDepth( 5 ) ;
        conf.setMaxCrossoverDepth( 2 ) ;
		conf.setPopulationSize( populationSize ) ;		
        conf.setMutationProb( ( float ) mutationProb ) ;
        conf.setReproductionProb((float) reproductionProb);
        conf.setCrossoverProb((float)crossoverProb);
        FitnessFunction ff = new FitnessFunction( ) ;
        ff.tipo = tipo ;
        ff.data = md ;
		conf.setFitnessFunction( ff ) ;
		conf.setStrictProgramCreation( true ) ;
		return conf ;
	}
}
