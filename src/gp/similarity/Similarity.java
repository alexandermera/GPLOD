package gp.similarity;





import gp.dados.Frame;
import gp.dados.MatchedData;
import gp.dados.Slot;
import gp.function.MatchingFrames;
import gp.function.MatchingType;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlEngine;




import uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;




/**
 * The concat operation.
 *
 * @author Alexander, Bernardo
 * @since 0.0
 */
/*
 * Classe para avaliar a similaridade entre as instancias dos dois esquemas do Source.
 */

public class Similarity 
{
    public static ArrayList <String> Source;
    public static Vector  aux ;
    public static HashSet atributos ;
    public static StringTokenizer TokenSolution ;
    public static Vector solution ;
    public static String solucaoParcial ;
    private static  float thresholdSimilarity 	= 0.8f ; //utilizada na metrica do cosine, levenshtein, ... se x e y sao >= 0.8 de similaridade entï¿½o eles sï¿½o iguais
    private static boolean NumericOperation 		= false ;
    private static boolean errorType				= false ;
    private static String auxSol ;
	private static int yy;    
    
    public static float metric( String sol, Metrica m, MatchingType tipo, MatchedData md )
	{   
    	/**Tiro da "(4 + 12)+ (4 + 5)" solucao partial os caracteres "(,), ", logo
         * Dividir a solucao partial "4+12+4+5", em partes(4,12,4,5) e testar
         * se tem atributos repetidos, neste caso 4 esta duas vezes
         * esta solucao partial nao serve, esta errada.
         * Tiro da solucao partial os caracteres "(,), "
         */
    	auxSol = sol ;
        solucaoParcial = sol ;
        solucaoParcial = solucaoParcial.replace( "(", "" ) ;
        solucaoParcial = solucaoParcial.replace( ")", "" ) ;
        solucaoParcial = solucaoParcial.replace( " ", "" ) ;
        TokenSolution = new StringTokenizer( solucaoParcial, "+-*/@" ) ;
        atributos = new  HashSet( ) ;
        solution = new  Vector() ;
        NumericOperation 		= false ;
        errorType = false ;
        
        Object a ;
        
        if (sol.contains("+") || sol.contains("-") || sol.contains("*") || sol.contains("/") )
        {
        	if( sol.contains("@") )
        	{
        		 return ( float ) 999999999 ; 
        	}
        	
        	NumericOperation 		= true ;
        	//thresholdSimilarity 	= 1 ;
        }
        
        
        while ( TokenSolution.hasMoreTokens( ) )
        {
            a = new Object( ) ;
            a = TokenSolution.nextElement( ) ;
            solution.add( a ) ;
            if( ! atributos.contains( a ) )
            {
                atributos.add( a ) ;
            }
            else
            {
                return ( float ) 999999999 ;
            } /* if */
        } /* for */

        Source = new ArrayList <String> ( ) ;
        List < Frame > Target ;
        Integer atributoFixado ;
        
        if( tipo == MatchingType.B_PARA_A )
        {   
        	//GetPartialMatching, prenche o vetor "Source", Source tem os atributos ja concatenados SOURCE
        	GetPartialMatching( MatchingFrames.getInstance( ).getframeA( ) ) ;
            Target = MatchingFrames.getInstance( ).getframeB( ) ;
            /*existe sempre um atributo que estï¿½ fixado para ser comparado com outros*/
            atributoFixado = md.getAtributoB( ).get( 0 ) ;
        }
        else
        {
        	GetPartialMatching( MatchingFrames.getInstance( ).getframeB( ) ) ;
            Target = MatchingFrames.getInstance( ).getframeA( ) ;
            /*existe sempre um atributo que estï¿½ fixado para ser comparado com outros*/
            atributoFixado = md.getAtributoA( ).get( 0 ) ;
        } /* if */
        
        if( errorType )
        	return ( float ) 999999999 ;
        	
		ArrayList <String> targetF = new ArrayList<String>( ) ;
		ArrayList <String> sourceF = new ArrayList<String>( ) ;
		
     
        /**Calcular a similaridade entra o SOURCE e TARGET, temos que ver
        */
			 
		 for(int r = 0 ; r < Target.size( ) ; r++ )
         {
             if( Target.get( 0 ).getSlot( ).size( ) == Target.get( r ).getSlot( ).size( ) )
             {
                 Slot objSlot = ( Slot )Target.get( r ).getSlot( atributoFixado ) ;
                 targetF.add(objSlot.getValue( 0 ).trim( ) ) ;
             }
         }
		 
		 int r = atributoFixado ;
		if (r == 5) 
		{
			if( sol.contains("a2 * a1") && sol.contains( "1.0 -" ) )
			{
				yy = 0;
			}
		}
		
		Set <String> tf = new LinkedHashSet <String> ( targetF ) ;
		Set <String> sf = new LinkedHashSet <String> ( Source ) ;
		
		float[ ] erro 	= new float[ sf.size( ) ] ;
		int[ ] cont 	= new int[ sf.size( ) ] ;
		float tmp 		= 0.00000f ;
		float erroFinal = 0.00000f ;
		float contFinal = 0.00000f ;
		int s			= 0 ;
		
        for(String str1 : sf )
        {
        	        	
            for (String str2 : tf) 
            {
                tmp = 0 ;  
                
                if( !NumericOperation )
                {
                    
	                if( !str2.toString( ).equalsIgnoreCase( "" ) && ! str1.toString().trim().equalsIgnoreCase( "" ) )
	                {
	                	switch( m )
	                	 {
	                		case COSINE:
	                			CosineSimilarity cos = new CosineSimilarity( ) ;
	                			tmp = cos.getSimilarity( str1.toString( ),str2.toString( ) ) ;
	                			break ;
	                		case LEVENSHTEIN:
	                			Levenshtein l = new Levenshtein( ) ;
	                			tmp = l.getSimilarity( str1.toString( ), str2.toString( ) ) ;
	                			break ;
	                		default:
	                			System.out.println( "Erro na escolha da mï¿½trica de similaridade" ) ;
	                			break ;
	                	 } /* switch */
	                	
	                   	
	                	if( tmp >= thresholdSimilarity )
	                    {
	                        cont[ s ] = sf.size( ) ;
	                        erro[ s ] = 0.0f ;
	                        break ;
	                    }
	                    else
	                    {
	                        cont[s] += 1 ;
	                        erro[s] += 1-tmp ;
	                    } /* if */
	                		
	                }
	                else
	                {
	                    cont[s] += 1 ;
	                    erro[s] += 1 ;
	                } /* if */
	                
                }
                else
                {
                	try
                	{
	                	if ( Double.parseDouble( str2) != Double .parseDouble(str1) )
	                	{                		
	                		cont[s] += 1 ;
	                		erro[s] += 1 ;
	                	}
	                	else
	                	{
	                        cont[ s ] = sf.size( ) ;
	                        erro[ s ] = 0.0f ;
	                	}
                	}
                	catch (NumberFormatException e) {
						// TODO: handle exception
                		return ( float ) 999999999 ;
					}
                	
                }
            } /* for */
            
            s++ ;
        } /* for */

        //Calculando o erro total das instÃ¢ncias
        for( int i = 0 ; i < erro.length ;i++ )
        {
            erroFinal += erro[i] ;
            contFinal += cont[i];
        } /* for */

        if( contFinal > 0 )
        {
            return ( float ) erroFinal /  contFinal  ;
        }
        else
        {
            return ( float ) 0 ;
        } /* if */
    }
        /**
         * @param solucaoParcial
         * @param frames
         */

    public static void GetPartialMatching(List<Frame> frames)
    {   
        String concat ;
        Object a ;
        ArrayList <String> cadena 		= new ArrayList<String>( ) ;
        ArrayList <String> cadenaTemp 	= new ArrayList<String>( ) ;       
        char[ ] sol 					= auxSol.replace(" ","").replace("  ", "").trim( ).toCharArray( );
        int  cont 						= 0 ;
        String temp						= "" ;
        boolean altered					= false ;

        //MatchingFrames.getInstance( ).getFrameAvaliados() trocar por frames.size()
        if ( NumericOperation )
        {

            for (char string : sol) 
            {
            	
            	if( string == '('  || string == ')'  || 
            				string == '-' || string == '+'  || 
            				string == '/' || string == '*' )
            	{
            		if( !temp.equalsIgnoreCase( "" ) || !temp.equalsIgnoreCase( "  " ))
            		cadena.add( temp ) ;
            		cadena.add( "" + string ) ;
            		temp = "" ;
            	}
            	else
            	{	temp += string ;
            	
            		if ( cont == sol.length - 1 && !temp.equalsIgnoreCase("") && !temp.equalsIgnoreCase( "  " ) )
            		{
            			cadena.add( temp ) ;
            		}
            		
            	}	
            	cont++ ;
    		}
            
            cadenaTemp = (ArrayList<String>) cadena.clone( ) ;
            
            for( int fa = 0; fa < frames.size( ); fa++ )
        	{
        		concat = "" ;
        		
        		
        		
        		for( int i = 0 ;i < cadena.size() ; i++ )
        		{
        			if( frames.get( fa ).getSlot( ).size( ) == frames.get( 0 ).getSlot( ).size( ) )
        			{
        				 if (!(cadena.get(i).equalsIgnoreCase("")||cadena.get(i).contains("(") ||cadena.get(i).contains(")") ||cadena.get(i).contains("+") || cadena.get(i).contains("-") || cadena.get(i).contains("*") || cadena.get(i).contains("/") || cadena.get(i) == null ))
        			        {   
        					 	if ( cadena.get( i ).trim( ).contains("a") )
        					 	{
			        				Slot objSlot = ( Slot )frames.get( fa ).getSlot( ).get( Integer.parseInt( cadena.get( i ).trim( ).replace("a", "") ) ) ;
			        				
			        				try
			        				{
			        					float number = Float.parseFloat(   objSlot.getValue( 0 ) ) ;
			        					cadena.set( i , "" + number ) ;
			        					altered = true ;
			        				}
			        				catch(NumberFormatException e)
			        				{
			        					errorType = true ;
			        					break ;
			        				}
        					 	}
        					 	else
        					 	{
        					 	 int bosss = 0 ;	
        					 	}
        			        }
        			} /* if */
        		} /* for */
        		
        		if( errorType )
        			break ;
        		
        		if ( altered )
        		{
		    		for (String s : cadena ) 
		    		{
		    			concat += s ;
					}
		    		
		    
		    	   	JexlEngine jexl = new JexlEngine();
		    	    Expression e = jexl.createExpression( concat );
		    	    String result = ((org.apache.commons.jexl2.Expression) e).evaluate( null).toString( );
		    	    
		    	    DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols( ) ;
		    	    otherSymbols.setDecimalSeparator('.') ;  
		    	    double number = Double.parseDouble( result) ;		    	    
		    	    NumberFormat formatter = new DecimalFormat("#0.0", otherSymbols) ;	    	    
		    		Source.add( ""+ formatter.format(number) ) ;
		    		
		    		cadena = (ArrayList<String>) cadenaTemp.clone( ) ;
		    		altered = false ;
        		}
        	} /* for */
        	
        }
        else
        {        	
        	for( int fa = 0; fa < frames.size( ); fa++ )
        	{
        		concat = "" ;
        		for( int i = 0 ;i < solution.size() ; i++ )
        		{
        			if( frames.get( fa ).getSlot( ).size( ) == frames.get( 0 ).getSlot( ).size( ) )
        			{
        				if (  solution.get( i ).toString( ).contains("a") )
        				{
        					Slot objSlot = ( Slot )frames.get( fa ).getSlot( ).get( Integer.parseInt( solution.get( i ).toString( ).replace("a", "") ) ) ;
        					concat = concat+" " + objSlot.getValue( 0 ) ;
        				}
        				else
        				{
        					errorType = true ;
        					break ;
        				}
        			} /* if */
        		} /* for */
        		
        		if( errorType )
        			break ;
        		concat = concat.trim( ) ;
        		Source.add( concat ) ;
        	} /* for */
        }	
    }
}