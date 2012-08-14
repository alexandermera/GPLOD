/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gp.function;

import gp.dados.Frame;
import java.util.ArrayList;
import java.util.List;

public class MatchingFrames
{
	private static MatchingFrames instance = null ;

	private List<Frame> frameA ;
	private List<Frame> frameB ;
     
	private MatchingFrames( )
	{
		frameA = new ArrayList < Frame >( ) ;
		frameB = new ArrayList< Frame >( ) ;
	}
	
	public static MatchingFrames getInstance( )
	{
		if( null == instance )
		{
			instance = new MatchingFrames( ) ;
		} /* if */
		return instance ;
	}

	public List< Frame > getframeA( )
	{
		return frameA ;
	}
	
	public void setframeA( List< Frame > frameA )
	{
		this.frameA = frameA ;
	}
	
	public List< Frame > getframeB( )
	{
		return frameB ;
	}
	
	public void setframeB( List< Frame > frameB )
	{
		this.frameB = frameB ;
	}
}