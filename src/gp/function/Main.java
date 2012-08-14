package gp.function;




import gp.MatchingProblemApp;
import gp.dados.MatchedData;
import gp.dados.ReadXML;
import gp.matriz.Coocorrencia;
import gp.matriz.EMI;
import gp.similarity.Metrica;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.jgap.InvalidConfigurationException;
import org.xml.sax.SAXException;

public class Main 
{
	
       
	/**
	 * @param args
	 */
	/**
	 * @param args
	 * @throws InvalidConfigurationException
	 */
	public static void main( String[ ] args ) throws InvalidConfigurationException
    {		
                /* Arquivos de entrada */
		ArrayList< String > files = new ArrayList< String >( ) ;
		
		files.add( "D:\\temp\\southampton- familyGiven -1000.xml" ) ;
		files.add( "D:\\temp\\southampton- fullname -1000.xml" ) ;
		files.add( "D:\\temp\\citeseer- fullname -1000.xml" ) ;
		files.add( "D:\\temp\\a100.xml" ) ;
		files.add( "D:\\temp\\b100.xml" ) ;
		
		files.add( "D:\\temp\\eprints-100.xml" ) ;
		files.add( "D:\\temp\\eprints- fullname -1000.xml" ) ;
		files.add( "D:\\temp\\acm- fullname -500.xml" ) ;
		files.add( "D:\\temp\\oai- familyGiven -10.xml" ) ;
		files.add( "D:\\temp\\oai- fullname -10.xml" ) ;
		
		files.add( "C:\\Users\\alex\\Dropbox\\GPLOD\\datasets\\inventory\\source1-data.xml" ) ;
		files.add( "C:\\Users\\alex\\Dropbox\\GPLOD\\datasets\\inventory\\source1-dataM.xml" ) ;
		files.add( "D:\\temp\\dblp- fullname -500.xml" ) ;
		files.add( "C:\\Users\\alex\\Dropbox\\GPLOD\\datasets\\inventory\\source2-data-Order-Info-v1.xml" ) ;
		files.add( "C:\\Users\\alex\\Dropbox\\GPLOD\\datasets\\inventory\\source2-data-Order-Info-v2.xml" ) ;
		files.add( System.getProperty("user.dir") + "//datasets//sinteticos//xmlAA.xml" ) ;
		
		files.add( System.getProperty("user.dir") + "//datasets//sinteticos//xmlbb.xml" ) ;
		files.add( System.getProperty("user.dir") + "//datasets//inventory//source1-data.xml" ) ;
		files.add( System.getProperty("user.dir") + "//datasets//inventory//source1-dataM.xml" ) ;
		files.add( System.getProperty("user.dir") + "//datasets//inventory//source2-data-Employee-Info.xml" ) ;		
		files.add( System.getProperty("user.dir") + "//datasets//inventory//source2-data-Order-Info-v1.xml" ) ;	
		files.add( System.getProperty("user.dir") + "//datasets//inventory//source2-data-Order-Info-v2.xml" ) ;
		
		String pathFile1 = files.get( 17 ) ;
		String pathFile2 = files.get( 18 ) ;
		
                
        System.out.println( "************           Alinhamento Genetico          **************************" ) ;
		
        /* Leitura dos arquivos de entrada */
        try 
        {
            System.out.println("Arquivo de entrada A: " + pathFile1 ) ;
            System.out.println( "Atributos: " ) ;
            
            /* Lista de dados no formato de frames */
            MatchingFrames.getInstance( ).setframeA( ReadXML.parseToFrames( pathFile1 ) ) ;
            System.out.println( "------------------------------------------------------------------------------------------");
            MatchingFrames.getInstance( ).setframeB( ReadXML.parseToFrames( pathFile2 ) ) ;

            
            System.out.println( "Arquivo de entrada B: " +  pathFile2 ) ;
            System.out.println( "Atributos:" ) ;
		} 
        catch ( ParserConfigurationException e ) 
        {
        	e.printStackTrace( ) ;
		} 
        catch ( SAXException e ) 
        {
        	e.printStackTrace( ) ;
		} 
        catch (IOException e ) 
        {
        	e.printStackTrace( ) ;
		} /* try */
        
        /* Verifica o numero de atributos de cada base (de cada arquivo de entrada) */
        int NatrA = MatchingFrames.getInstance( ).getframeA( ).get( 1 ).getAttributeList( ).size( ) ;
        int NatrB = MatchingFrames.getInstance( ).getframeB( ).get( 1 ).getAttributeList( ).size( ) ;
        
        System.out.println("Numero de Atributos encontrados do Esquema A: "+ NatrA +"\nNumero de Atributos encontrados do Esquema B: "+ NatrB ) ;
        System.out.println("Numero de registros do Esquema A: "+ MatchingFrames.getInstance( ).getframeA( ).size()+"\nNumero de registros do Esquema B: "+ MatchingFrames.getInstance( ).getframeB( ).size( ) ) ;
        
        /* Calcula a matriz de coocorrencia */
        Coocorrencia matrizCoocorrencia = new Coocorrencia( NatrA, NatrB ) ;
        int[ ][ ] mco = matrizCoocorrencia.execute( MatchingFrames.getInstance( ).getframeA( ), MatchingFrames.getInstance( ).getframeB( ), Metrica.LEVENSHTEIN ) ;
        
        //Calculando a matriz EMI
        EMI emi = new EMI( mco ) ;
        float[ ][ ] mEmi = emi.execute( ) ;
        //reusando a EMI pra agilizar o calculo do Crossover validation
//        CrossValidation crossV= new CrossValidation(mEmi, NatrA, NatrB);
////        crossV.executeGeneration() ;
////        crossV.executePopulation() ;
////        crossV.executeMutation() ;
//        crossV.executeReproductionCrossover() ;
//        System.exit( 0 ) ;
        
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
        
    /*    for( MatchedData md : matchedEMI )
        {
        	notMatched.getAtributoA( ).remove( md.getAtributoA( ).get( 0 ) ) ;
        	notMatched.getAtributoB( ).remove( md.getAtributoB( ).get( 0 ) ) ;
        } /* for */
        //System.err.println( notMatched.toString( ) );
        
        //escolha dos matchings a serem submetidos a PG
        float threshold = -1.0f ;
        
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
        	System.out.println( "Nao existem matches a serem testados!" ) ;
        } /* if */
        
        /* imprime possiveis matchings de B para A */
        System.out.println( "/*   POSSIVEIS MATCHES DE B PARA A   */" ) ;
        for( MatchedData md : possiveisMatchesB2A )
        {
        	System.out.println( md.toString( ) ) ;
        } /* for */
        if( possiveisMatchesB2A.size( ) == 0 )
        {
        	System.out.println( "Nao existem matches a serem testados!" ) ;
        } /* if */
        
        /* Aqui temos as listas de possiveis matches de A para B e de B para A 
         * Basta submeter essas listas para a PG */
        MatchingProblemApp pg ;
        float thresholdFitness = 0.99f ;
        
        System.out.println( "/*   REAL MATCHES DE A PARA B   */" ) ;
        for( MatchedData realMatch : possiveisMatchesA2B )
        {
        	if (realMatch.getAtributoA().get(0)== 5)
        	{
	        	pg = new MatchingProblemApp( realMatch, MatchingType.A_PARA_B ) ;
	        	if( pg.getFitness( ) < thresholdFitness )
	            {
	                System.out.println( "[" + realMatch.getAtributoA( ).toString( ) + "] > [" + pg.getSolution( ) + "]" ) ;
	            } /* if */
        	}
        } /* for */
        
       // System.out.println( "/*   REAL MATCHES DE B PARA A   */" ) ;
      /*  for( MatchedData realMatch : possiveisMatchesB2A )
        {
        	pg = new MatchingProblemApp( realMatch, MatchingType.B_PARA_A ) ;
        	if( pg.getFitness( ) < thresholdFitness )
            {
                System.out.println( realMatch.getAtributoB( ).toString( ) + " > [" + pg.getSolution( ) + "]" ) ;
            } /* if */
      //  } /* for */
	} /* End main */
}