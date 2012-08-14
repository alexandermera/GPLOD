package gp.matriz;


import gp.dados.Frame;
import gp.dados.Slot;
import gp.similarity.Metrica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity;
import uk.ac.shef.wit.simmetrics.similaritymetrics.Levenshtein;

public class Coocorrencia 
{
	private int matrizCoocorrencia [ ][ ] ;
	private final float threshold = 1f ; //utilizada na metrica do cosine, levenshtein, ... se x e y sao >= 0.8 de similaridade ent�o eles s�o iguais

	public Coocorrencia( int tamanhoLinha, int tamanhoColuna )
	{
		matrizCoocorrencia = new int [ tamanhoLinha + 1 ][ tamanhoColuna + 1 ] ;
		
		/* Inicializa a matriz de coocorrencia com valores nulos */
		for ( int x = 0 ; x < matrizCoocorrencia.length ; x++ )
        {
            for ( int y = 0; y < matrizCoocorrencia[ x ].length ; y++ )
            {
                matrizCoocorrencia [ x ][ y ] = 0 ;
            } /* for */
        } /* for */
	}
	
	public int[ ][ ] execute( List< Frame > framesA, List< Frame > framesB, Metrica m )
	{
		int linha, coluna;
		
		System.out.println("---------------------------------------------------------------------------") ;
        System.out.println("****************** Matriz de Coocorrencia **********************") ;
        
        for( int fa = 0 ;fa < framesA.size( ); fa++ )
        {       
            linha = 0 ;
            for( Iterator < Slot > it = framesA.get( fa ).getSlot( ).iterator( ) ; it.hasNext( ) ; )
            {
                Slot objSlot = ( Slot ) it.next( ) ;
                if ( objSlot.values != null )
                {
                    ArrayList < String > objValue = objSlot.values ;

                    for( Iterator < String > it1 = objValue.iterator( ); it1.hasNext( ); )
                    {
                        String value = ( String ) it1.next( ) ;
                        value = value.trim( ) ;

                        if( !value.equalsIgnoreCase( "" ) )
                        {
                            for( int fb = 0 ; fb < framesB.size( ); fb++ )
                            {
                                coluna = 0 ;
                                for( Iterator < Slot > it2 = framesB.get( fb ).getSlot( ).iterator( ) ; it2.hasNext( ) ; )
                                {
                                    Slot objSlot1 = ( Slot ) it2.next( ) ;
                                    if ( objSlot1.values != null )
                                    {
                                        ArrayList < String > objValue1 = objSlot1.values ;
                                        for( Iterator < String > it3 = objValue1.iterator( ); it3.hasNext( ); )
                                        {
                                            String value1 = ( String ) it3.next( ) ;
                                            value1 = value1.trim( ) ;
                                            
                                            if( !value1.equalsIgnoreCase( "" ) ) 
                                            {
                                                String [ ] tk , tk1 ;
                                                tk = value.split( " " );
                                                tk1 = value1.split( " " );
                                                for (int f = 0 ; f < tk.length; f ++)
                                                {
                                                    for (int k = 0 ; k < tk1.length; k ++)
                                                    {
                                                        Levenshtein l = new Levenshtein( ) ;
                                                        if( l.getSimilarity( tk [ f ] , tk1 [ k ] ) >= threshold )
                                                        {
                                                            matrizCoocorrencia[ linha ][ coluna ] += 1 ;
                                                        }
                                                    }
                                                }
//                                            	switch( m )
//                                            	{
//                                            		case COSINE:
//                                            			CosineSimilarity cos = new CosineSimilarity( ) ;
//
//                                            			if( cos.getSimilarity( value, value1 ) >= threshold )
//                                                        {
//                                                            matrizCoocorrencia[ linha ][ coluna ] += 1 ;
//                                                        } /* if */
//                                            			break ;
//                                            		case LEVENSHTEIN:
//                                            			Levenshtein l = new Levenshtein( ) ;
//                                            			if( l.getSimilarity( value , value1 ) >= threshold )
//                                            			{
//                                            				matrizCoocorrencia[ linha ][ coluna ] += 1 ;
//                                            			} /* if */
//                                            			break ;
//                                            		default:
//                                            			System.out.println( "Erro na escolha da m�trica de similaridade" ) ;
//                                            			break ;
//                                            	} /* switch */
                                            } /* if */
                                        } /* for */
                                    } /* if */
                                    coluna++ ;
                                } /* for */
                            } /* for */
                        } /* if */
                    } /* for */
                } /* if */
                linha++ ;
            } /* for */
        } /* for */		
        
        int maxColuna = -1 ;
        int inxColuna = -1 ;
        int maxLinha = -1 ;
        int inxLinha = -1 ;

        //Soma das linhas e columnas da matriz de coocorrencia
        for ( int x = 0; x < matrizCoocorrencia.length; x++ )
        {
        	int somaLinha = 0 ;
             for ( int y = 0; y < matrizCoocorrencia[ x ].length; y++ )
             {
            	 if( y == matrizCoocorrencia[ x ].length - 1 )
            	 {
            		 matrizCoocorrencia[ x ][ y ] = somaLinha ;
            		 
            		 if( maxLinha < somaLinha && x != matrizCoocorrencia.length - 1 )
            		 {
            			 maxLinha = somaLinha ;
            			 inxLinha = x ;
            		 } /* if */
            	 }
            	 else
            	 {
            		 somaLinha += matrizCoocorrencia[ x ][ y ] ;
            	 } /* if */
            	 
            	 if( x != matrizCoocorrencia.length - 1 )
            	 {
            		 matrizCoocorrencia[ matrizCoocorrencia.length - 1 ][ y ] += matrizCoocorrencia[ x ][ y ] ;
            	 } /* if */
            	 
            	 if( maxColuna < matrizCoocorrencia[ x ][ y ] && y != matrizCoocorrencia[ x ].length - 1 )
        		 {
        			 maxColuna = matrizCoocorrencia[ x ][ y ] ;
        			inxColuna = y ;
        		 } /* if */                    	 
            	 
                 System.out.print( "       "+ matrizCoocorrencia[ x ][ y ]+"     " ) ;
             } /* for */
             
             System.out.println( ) ;
             
        } /* for */
        
        return matrizCoocorrencia ;
	}
	
	public int[ ][ ] getMatrizCoocorrencia( ) 
	{
		return matrizCoocorrencia;
	}

	public void setMatrizCoocorrencia( int[ ][ ] matrizCoOcorrencia ) 
	{
		matrizCoocorrencia = matrizCoOcorrencia ;
	}
}
