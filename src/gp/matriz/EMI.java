package gp.matriz;

/*
 * Matriz EMI
 * 
 * @author Alexander, Bernardo
 */
public class EMI 
{
	private float EMI[ ][ ] ;
	private int matrizCoocorrencia[ ][ ] ;
	                
	public EMI( int[ ][ ] mCoocorrencia )
	{
		matrizCoocorrencia = mCoocorrencia ;
		
		EMI = new float [ matrizCoocorrencia.length - 1 ] [ matrizCoocorrencia[ 0 ].length - 1 ] ;
		
		//Inicializando matriz EMI
        for( int x = 0; x < EMI.length; x++ )
        {
        	for( int y = 0; y < EMI[ 0 ].length; y++ )
            {
        		EMI[ x ][ y ] =  0.0f ;
            } /* for */
        } /* for */
	}
	
	public float[ ][ ] execute( )
	{
		float mij , mrs , mis , mrj ;
        mij = mrs = mis = mrj = 0.000000f ;
        mij = matrizCoocorrencia[ matrizCoocorrencia.length - 1 ][ matrizCoocorrencia[ 0 ].length - 1 ] ;
        
        System.out.println("---------------------------------------------------------------------------") ;
        System.out.println("****************** Matriz EMI **********************") ;
        
        for ( int r = 0; r < EMI.length; r++ )
        {
            for ( int s = 0 ; s < EMI[ r ].length; s++ )
            {
                mrs = matrizCoocorrencia[ r ] [ s ] ;
                mis = matrizCoocorrencia[ r ] [ matrizCoocorrencia[ 0 ].length - 1 ] ;
                mrj = matrizCoocorrencia[ matrizCoocorrencia.length - 1 ] [ s ] ;
             
                try 
                {
                	float log = mij * ( mrs /( mrj * mis ) ) ;
                
	                if( mij == 0 )
	            	{
	            		EMI[ r ][ s ] = 0.0f ;
	            	}
	                else if( mrj == 0 || mis == 0 )
                	{
                		EMI[ r ][ s ] = 0.0f ;
                	}
	                else if( log == 0 )
                	{
                		EMI[ r ][ s ] = 0.0f ;
                	}
                	else
                	{
                		EMI[ r ][ s ] = ( float ) ( ( mrs / mij ) * Math.log( mij * ( mrs /( mrj * mis ) ) ) ) ;
                	} /* if */
                } 
                catch ( ArithmeticException e ) 
                {
                    EMI[ r ][ s ] = 0.0f ;
                } /* try */
                
                System.out.print( "   " + EMI[ r ][ s ] + "    " ) ;
             } /* for */
            System.out.println( ) ;
        }
        return EMI ;
	}
	
	public float[ ][ ] getEMI( ) 
	{
		return EMI ;
	}

	public void setEMI( float[ ][ ] EMI ) 
	{
		this.EMI = EMI;
	}
}