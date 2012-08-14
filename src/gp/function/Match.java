package gp.function;

import gp.dados.MatchedData;

import java.util.ArrayList;
/*
 * Classe das funcoes de alinhamentos
 * 
 * @author Alexander, Bernardo
 */
public class Match 
{	
	private float thresholdEMI = 0.0f ; //indica o limite minimo para um matching ser considerado
	//private float thresholdFitness = 0.95f ;
	
	public ArrayList< MatchedData > EMIMatching( float[ ][ ] matrizEMI )
	{
		ArrayList< MatchedData > listOfMatches = new ArrayList< MatchedData >( ) ;//lista dos matches gerados
		
		for( int i = 0; i < matrizEMI.length; i++ )
		{
			ArrayList< Integer > set = new ArrayList< Integer >( ) ;
			//seleciona os n possiveis alinhamentos de uma linha com as colunas - seleciona as colunas
			for( int j = 0; j < matrizEMI[ 0 ].length; j++ )
			{
				if( matrizEMI[ i ][ j ] > thresholdEMI )
                {
                    set.add( j ) ;
                } /* if */
			} /* for */
			
			//EMI encontrou alinhamento 1 : 1
			if( set.size( ) == 1 )
			{
				boolean flagAlinhamento1para1 = true ;
				//verifica se o atributo encontrado é o maior da coluna
				for( int i_2 = 0; i_2 < matrizEMI.length; i_2++ )
				{
					if( ( i != i_2 ) && ( matrizEMI[ i_2 ][ set.get( 0 ) ] > thresholdEMI ) )
					{
						flagAlinhamento1para1 = false ;
	                    break;	
					} /* if */
				} /* for */
				
				if( flagAlinhamento1para1 )
				{
					MatchedData m = new MatchedData( ) ;
					m.getAtributoA( ).add( i ) ;
					m.getAtributoB( ).add( set.get( 0 ) ) ;
					listOfMatches.add( m ) ;
				} /* if */
			} /* if */
		} /* for */
		return listOfMatches ;
	}
}