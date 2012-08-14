/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.function;

import gp.MatchingProblemApp;
import gp.dados.MatchedData;
import java.util.ArrayList;
import org.jgap.InvalidConfigurationException;

/**
 *
 * @author Usuario
 */
public class CrossValidation {
    float[ ][ ] mEmi;
    int NatrA, NatrB ;
    CrossValidation(float[ ][ ] mEmi,int NatrA,int NatrB)
    {
        this.mEmi = mEmi ;
        this.NatrA = NatrA ;
        this.NatrB = NatrB ;
    }
    public void executePopulation( ) throws InvalidConfigurationException
    {
        for(int p = 10 ; p <=100 ; p = p + 10 )
        {

            System.out.println("population: "+p) ;
            //Calculando matches encontrados na EMI
            Match emiMatch = new Match( ) ;
            ArrayList< MatchedData > matchedEMI = emiMatch.EMIMatching( mEmi ) ;

            //imprime mapeamentos encontrados
            System.out.println( "Mapeamentos 1:1 do esquema" ) ;
            for( MatchedData md : matchedEMI )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */

            System.out.println( "FIM ALINHAMENTOS FEITOS PELA EMI" ) ;

            System.out.println( "************* ALINHAMENTOS FEITOS PELA PG ***************" ) ;

            //Inicia processo genetico com os atributos n�o encontrados

            //descarta os alinhamentos 1:1 j� encontrados
            //verifica apenas os atributos que n�o tiveram nenhum matching
            MatchedData notMatched = new MatchedData( NatrA, NatrB ) ;

            for( MatchedData md : matchedEMI )
            {
                    notMatched.getAtributoA( ).remove( md.getAtributoA( ).get( 0 ) ) ;
                    notMatched.getAtributoB( ).remove( md.getAtributoB( ).get( 0 ) ) ;
            } /* for */
            //System.err.println( notMatched.toString( ) );

            //escolha dos matchings a serem submetidos a PG
            float threshold = 0.0f ;

            /* Seleciona os poss�veis matches de A para B */
            ArrayList< MatchedData > possiveisMatchesA2B = new ArrayList< MatchedData >( ) ;
            for( Integer atribA : notMatched.getAtributoA( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoA( ).add( atribA ) ;
                    for( Integer atribB : notMatched.getAtributoB( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoB( ).add( atribB ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoB( ).size() > 1 )
                    {
                            possiveisMatchesA2B.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* Seleciona os poss�veis matches de B para A */
            ArrayList< MatchedData > possiveisMatchesB2A = new ArrayList< MatchedData >( ) ;
            for( Integer atribB : notMatched.getAtributoB( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoB( ).add( atribB ) ;
                    for( Integer atribA : notMatched.getAtributoA( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoA( ).add( atribA ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoA( ).size() > 1 )
                    {
                            possiveisMatchesB2A.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* imprime possiveis matchings de A para B */
            System.out.println( "/*   POSSIVEIS MATCHES DE A PARA B   */" ) ;
            for( MatchedData md : possiveisMatchesA2B )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesA2B.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* imprime possiveis matchings de B para A */
            System.out.println( "/*   POSSIVEIS MATCHES DE B PARA A   */" ) ;
            for( MatchedData md : possiveisMatchesB2A )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesB2A.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* Aqui temos as listas de possiveis matches de A para B e de B para A
             * Basta submeter essas listas para a PG */
            MatchingProblemApp pg ;
            float thresholdFitness = 0.95f ;

            System.out.println( "/*   REAL MATCHES DE A PARA B   */" ) ;
            for( MatchedData realMatch : possiveisMatchesA2B )
            {
                    pg = new MatchingProblemApp(realMatch, MatchingType.A_PARA_B, p , 30 , 0.07 , 0.3, 0.7);
                    if( pg.getFitness( ) < thresholdFitness )
                {
                    System.out.println( "[" + realMatch.getAtributoA( ).toString( ) + "] > [" + pg.getSolution( ) + "]" ) ;
                } /* if */
            } /* for */
        } /* for */
    }
    public void executeReproductionCrossover( ) throws InvalidConfigurationException
    {
        double reproduction =0.2 ;
        double crossover =0.8 ;
        for(int p = 0 ; p < 5 ; p ++ )
        {

            System.out.println("reproduction - crossover: "+reproduction+"  -  "+crossover ) ;
            //Calculando matches encontrados na EMI
            Match emiMatch = new Match( ) ;
            ArrayList< MatchedData > matchedEMI = emiMatch.EMIMatching( mEmi ) ;

            //imprime mapeamentos encontrados
            System.out.println( "Mapeamentos 1:1 do esquema" ) ;
            for( MatchedData md : matchedEMI )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */

            System.out.println( "FIM ALINHAMENTOS FEITOS PELA EMI" ) ;

            System.out.println( "************* ALINHAMENTOS FEITOS PELA PG ***************" ) ;

            //Inicia processo genetico com os atributos n�o encontrados

            //descarta os alinhamentos 1:1 j� encontrados
            //verifica apenas os atributos que n�o tiveram nenhum matching
            MatchedData notMatched = new MatchedData( NatrA, NatrB ) ;

            for( MatchedData md : matchedEMI )
            {
                    notMatched.getAtributoA( ).remove( md.getAtributoA( ).get( 0 ) ) ;
                    notMatched.getAtributoB( ).remove( md.getAtributoB( ).get( 0 ) ) ;
            } /* for */
            //System.err.println( notMatched.toString( ) );

            //escolha dos matchings a serem submetidos a PG
            float threshold = 0.0f ;

            /* Seleciona os poss�veis matches de A para B */
            ArrayList< MatchedData > possiveisMatchesA2B = new ArrayList< MatchedData >( ) ;
            for( Integer atribA : notMatched.getAtributoA( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoA( ).add( atribA ) ;
                    for( Integer atribB : notMatched.getAtributoB( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoB( ).add( atribB ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoB( ).size() > 1 )
                    {
                            possiveisMatchesA2B.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* Seleciona os poss�veis matches de B para A */
            ArrayList< MatchedData > possiveisMatchesB2A = new ArrayList< MatchedData >( ) ;
            for( Integer atribB : notMatched.getAtributoB( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoB( ).add( atribB ) ;
                    for( Integer atribA : notMatched.getAtributoA( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoA( ).add( atribA ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoA( ).size() > 1 )
                    {
                            possiveisMatchesB2A.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* imprime possiveis matchings de A para B */
            System.out.println( "/*   POSSIVEIS MATCHES DE A PARA B   */" ) ;
            for( MatchedData md : possiveisMatchesA2B )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesA2B.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* imprime possiveis matchings de B para A */
            System.out.println( "/*   POSSIVEIS MATCHES DE B PARA A   */" ) ;
            for( MatchedData md : possiveisMatchesB2A )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesB2A.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* Aqui temos as listas de possiveis matches de A para B e de B para A
             * Basta submeter essas listas para a PG */
            MatchingProblemApp pg ;
            float thresholdFitness = 0.95f ;

            System.out.println( "/*   REAL MATCHES DE A PARA B   */" ) ;
            for( MatchedData realMatch : possiveisMatchesA2B )
            {
                    pg = new MatchingProblemApp(realMatch, MatchingType.A_PARA_B, 50 , 30 , 0.07 , reproduction, crossover);
                    if( pg.getFitness( ) < thresholdFitness )
                {
                    System.out.println( "[" + realMatch.getAtributoA( ).toString( ) + "] > [" + pg.getSolution( ) + "]" ) ;
                } /* if */
            } /* for */
            reproduction = reproduction + 0.1 ;
            crossover = crossover - 0.1 ;
        } /* for */
    }
    public void executeGeneration( ) throws InvalidConfigurationException
    {

        for(int generation = 10 ; generation <= 50 ; generation = generation + 10 )
        {

            System.out.println("Generation: " + generation ) ;
            //Calculando matches encontrados na EMI
            Match emiMatch = new Match( ) ;
            ArrayList< MatchedData > matchedEMI = emiMatch.EMIMatching( mEmi ) ;

            //imprime mapeamentos encontrados
            System.out.println( "Mapeamentos 1:1 do esquema" ) ;
            for( MatchedData md : matchedEMI )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */

            System.out.println( "FIM ALINHAMENTOS FEITOS PELA EMI" ) ;

            System.out.println( "************* ALINHAMENTOS FEITOS PELA PG ***************" ) ;

            //Inicia processo genetico com os atributos n�o encontrados

            //descarta os alinhamentos 1:1 j� encontrados
            //verifica apenas os atributos que n�o tiveram nenhum matching
            MatchedData notMatched = new MatchedData( NatrA, NatrB ) ;

            for( MatchedData md : matchedEMI )
            {
                    notMatched.getAtributoA( ).remove( md.getAtributoA( ).get( 0 ) ) ;
                    notMatched.getAtributoB( ).remove( md.getAtributoB( ).get( 0 ) ) ;
            } /* for */
            //System.err.println( notMatched.toString( ) );

            //escolha dos matchings a serem submetidos a PG
            float threshold = 0.0f ;

            /* Seleciona os poss�veis matches de A para B */
            ArrayList< MatchedData > possiveisMatchesA2B = new ArrayList< MatchedData >( ) ;
            for( Integer atribA : notMatched.getAtributoA( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoA( ).add( atribA ) ;
                    for( Integer atribB : notMatched.getAtributoB( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoB( ).add( atribB ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoB( ).size() > 1 )
                    {
                            possiveisMatchesA2B.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* Seleciona os poss�veis matches de B para A */
            ArrayList< MatchedData > possiveisMatchesB2A = new ArrayList< MatchedData >( ) ;
            for( Integer atribB : notMatched.getAtributoB( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoB( ).add( atribB ) ;
                    for( Integer atribA : notMatched.getAtributoA( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoA( ).add( atribA ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoA( ).size() > 1 )
                    {
                            possiveisMatchesB2A.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* imprime possiveis matchings de A para B */
            System.out.println( "/*   POSSIVEIS MATCHES DE A PARA B   */" ) ;
            for( MatchedData md : possiveisMatchesA2B )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesA2B.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* imprime possiveis matchings de B para A */
            System.out.println( "/*   POSSIVEIS MATCHES DE B PARA A   */" ) ;
            for( MatchedData md : possiveisMatchesB2A )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesB2A.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* Aqui temos as listas de possiveis matches de A para B e de B para A
             * Basta submeter essas listas para a PG */
            MatchingProblemApp pg ;
            float thresholdFitness = 0.95f ;

            System.out.println( "/*   REAL MATCHES DE A PARA B   */" ) ;
            for( MatchedData realMatch : possiveisMatchesA2B )
            {
                    pg = new MatchingProblemApp(realMatch, MatchingType.A_PARA_B, 50 , generation , 0.07 , 0.3, 0.7 ) ;
                    if( pg.getFitness( ) < thresholdFitness )
                {
                    System.out.println( "[" + realMatch.getAtributoA( ).toString( ) + "] > [" + pg.getSolution( ) + "]" ) ;
                } /* if */
            } /* for */
        } /* for */
    }
    public void executeMutation( ) throws InvalidConfigurationException
    {
        double mutation =0.02 ;
        for(int muta = 0 ; muta <= 3 ; muta++ )
        {

            System.out.println("Mutation: " + mutation ) ;
            //Calculando matches encontrados na EMI
            Match emiMatch = new Match( ) ;
            ArrayList< MatchedData > matchedEMI = emiMatch.EMIMatching( mEmi ) ;

            //imprime mapeamentos encontrados
            System.out.println( "Mapeamentos 1:1 do esquema" ) ;
            for( MatchedData md : matchedEMI )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */

            System.out.println( "FIM ALINHAMENTOS FEITOS PELA EMI" ) ;

            System.out.println( "************* ALINHAMENTOS FEITOS PELA PG ***************" ) ;

            //Inicia processo genetico com os atributos n�o encontrados

            //descarta os alinhamentos 1:1 j� encontrados
            //verifica apenas os atributos que n�o tiveram nenhum matching
            MatchedData notMatched = new MatchedData( NatrA, NatrB ) ;

            for( MatchedData md : matchedEMI )
            {
                    notMatched.getAtributoA( ).remove( md.getAtributoA( ).get( 0 ) ) ;
                    notMatched.getAtributoB( ).remove( md.getAtributoB( ).get( 0 ) ) ;
            } /* for */
            //System.err.println( notMatched.toString( ) );

            //escolha dos matchings a serem submetidos a PG
            float threshold = 0.0f ;

            /* Seleciona os poss�veis matches de A para B */
            ArrayList< MatchedData > possiveisMatchesA2B = new ArrayList< MatchedData >( ) ;
            for( Integer atribA : notMatched.getAtributoA( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoA( ).add( atribA ) ;
                    for( Integer atribB : notMatched.getAtributoB( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoB( ).add( atribB ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoB( ).size() > 1 )
                    {
                            possiveisMatchesA2B.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* Seleciona os poss�veis matches de B para A */
            ArrayList< MatchedData > possiveisMatchesB2A = new ArrayList< MatchedData >( ) ;
            for( Integer atribB : notMatched.getAtributoB( ) )
            {
                    MatchedData mdPossivel = new MatchedData( ) ;
                    mdPossivel.getAtributoB( ).add( atribB ) ;
                    for( Integer atribA : notMatched.getAtributoA( ) )
                {
                            /* coloca os atributos que podem possuir rela��o com outro (info retirada da EMI)*/
                    if( mEmi[ atribA ][ atribB ] > threshold )
                    {
                            mdPossivel.getAtributoA( ).add( atribA ) ;
                    } /* */
                } /* for */
                    //verifica se existe algum possivel matching, se existe adiciona para teste posterior
                    if( mdPossivel.getAtributoA( ).size() > 1 )
                    {
                            possiveisMatchesB2A.add( mdPossivel ) ;
                    } /* if */
            } /* for */

            /* imprime possiveis matchings de A para B */
            System.out.println( "/*   POSSIVEIS MATCHES DE A PARA B   */" ) ;
            for( MatchedData md : possiveisMatchesA2B )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesA2B.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* imprime possiveis matchings de B para A */
            System.out.println( "/*   POSSIVEIS MATCHES DE B PARA A   */" ) ;
            for( MatchedData md : possiveisMatchesB2A )
            {
                    System.out.println( md.toString( ) ) ;
            } /* for */
            if( possiveisMatchesB2A.size( ) == 0 )
            {
                    System.out.println( "N�o existem matches a serem testados!" ) ;
            } /* if */

            /* Aqui temos as listas de possiveis matches de A para B e de B para A
             * Basta submeter essas listas para a PG */
            MatchingProblemApp pg ;
            float thresholdFitness = 0.95f ;

            System.out.println( "/*   REAL MATCHES DE A PARA B   */" ) ;
            for( MatchedData realMatch : possiveisMatchesA2B )
            {
                    pg = new MatchingProblemApp(realMatch, MatchingType.A_PARA_B, 50 , 30 , mutation , 0.3, 0.7 ) ;
                    if( pg.getFitness( ) < thresholdFitness )
                {
                    System.out.println( "[" + realMatch.getAtributoA( ).toString( ) + "] > [" + pg.getSolution( ) + "]" ) ;
                } /* if */
            } /* for */
            mutation=mutation + 0.03 ;
            if(mutation > 0.1)
            {
                mutation = 0.1 ; 
            }
        } /* for */
    }
}
