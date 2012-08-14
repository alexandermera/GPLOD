package gp.dados;



import java.util.ArrayList;

/*
 * Classe que armazena os atributos alinhados
 * Por exemplo: atributoA (1,2) > atributoB(7)
 * 
 * @author Bernardo
 */
public class MatchedData 
{
	private ArrayList< Integer > atributoA ;
	private ArrayList< Integer > atributoB ;
	
	public MatchedData( )
	{
		atributoA = new ArrayList< Integer >( ) ;
		atributoB = new ArrayList< Integer >( ) ;
	}
	
	/* inicializa com todos os indices dos atributos */
	public MatchedData( int nAtributosA, int nAtributosB )
	{
		this( ) ;
		for( int i = 0; i < nAtributosA; i++ )
	    {
	    	atributoA.add( i ) ;
	    } /* for */
	    
	    for( int i = 0; i < nAtributosB; i++ )
	    {
	    	atributoB.add( i ) ;
	    } /* for */
	}
	
	public ArrayList< Integer > getAtributoA( ) 
	{
		return atributoA ;
	}
	
	public void setAtributoA( ArrayList< Integer > atributoA ) 
	{
		this.atributoA = atributoA ;
	}
	
	public ArrayList< Integer > getAtributoB( ) 
	{
		return atributoB ;
	}
	
	public void setAtributoB( ArrayList< Integer > atributoB ) 
	{
		this.atributoB = atributoB ;
	}
	
	public String toString( )
	{
		String result = "[" ;
		for( int i = 0; i < atributoA.size( ); i++ )
		{
			if( i > 0 )
			{
				result += " + " ;
			} /* if */
			result += atributoA.get( i ) ;
		} /* for */
		
		result += "] > [" ;
		
		for( int j = 0; j < atributoB.size( ); j++ )
		{
			if( j > 0 )
			{
				result += " + " ;
			} /* if */
			result += atributoB.get( j ) ;
		} /* for */
		
		result += "]" ;
		
		return result;
	}
}