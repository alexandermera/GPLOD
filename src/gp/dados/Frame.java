/*
 * Frame.java
 */
package gp.dados;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Frame
 * Estrutura de dados que organiza informa��es
 * de acordo com seus atributos.
 * @author Bernardo
 */
public class Frame
{
    public String id ; //identificador do objeto
    public String name ; //
    public ArrayList < Slot > slot ;
    
    /**
     * Construtor Padr�o.
     * Inicializa um frame.
     */
    public Frame( ) 
    {
        name = "" ;
        slot = new ArrayList < Slot > ( ) ;
    } /* fim do construtor Frame( ) */   
     
    /**
     * M�todo que retorna o id de um frame.
     * @return id Identificador de um frame.
     */
    public String getId( ) 
    {
        return id ;
    } /* fim do m�todo getId( ) */

    /**
     * M�todo que atribui um identificador ao frame.
     * @param id Identificador a ser atribu�do ao frame.
     */
    public void setId( String id ) 
    {
        this.id = id ;
    }/* fim do m�todo setId( int id ) */
            
    /**
     * M�todo que retorna o nome do frame.
     * @return name Retorna o nome do frame.
     */
    public String getName( ) 
    {
        return name ;
    } /* fim do m�todo getName( ) */

    /**
     * M�todo que atribui o nome a um frame.
     * @param name Atribui um nome a um frame.
     */
    public void setName( String name ) 
    {
        this.name = name ;
    } /* fim do m�todo setName( String name ) */
    
    /**
     * M�todo que retorna um lista dos atributos/slots
     * de um frame.
     * @return Lista de atributos/slots.
     */
    public ArrayList < Slot > getSlot(  ) 
    {
        return this.slot ;
    } /* fim do m�todo getSlot(  ) */
        
    /**
     * M�todo que retorna um slot de uma lista de slots
     * em uma determinada posi��o.
     * @param i Indice do slot a ser retornado.
     * @return Retorna o slot do indice i.
     */
    public Slot getSlot( int i ) 
    {
        return this.slot.get( i ) ;
    } /* fim do m�todo getSlot( int i ) */
        
    /**
     * M�todo que retorna a lista de atributos.
     * @return Retorna uma lista de atributos.
     */
    public ArrayList< String > getAttributeList( ) 
    {
        if( slot == null )
        {
            throw new NullPointerException( ) ;
        } /* if */
        
        ArrayList< String > attributeList = new ArrayList< String >( ) ;
        
        for( int i = 0; i < this.getSlot( ).size( ); i++ )
        {
            attributeList.add( this.getSlot( ).get( i ).getAttribute( ) ) ;
        } /* for */
        return attributeList ;
    } /* fim do m�todo getAttributeList( ) */
    
    /**
     * M�todo que adiciona um slot ao frame.
     * @param attribute Nome do atributo do frame.
     * @param values Valores relacionados ao atributo.
     * @return Retorna se o slot foi adicionado com sucesso.
     */
    public boolean addSlot( String attribute, ArrayList < String > values )
    {
        Slot s = new Slot( attribute, values ) ;
        return this.slot.add( s ) ;
    } /* fim do m�todo addSlot( String attribute, ArrayList< String > values ) */
    
    /**
     * M�todo que remove um slot de um frame
     * em uma determinada posi��o.
     * @param i Indice do slot a ser removido.
     * @return slot Retorna o slot removido.
     */
    public Slot removeSlot( int i )
    {
        return this.slot.remove( i ) ;
    } /* fim do m�todo removeSlot( int i ) */    
    
        /**
         * Slot
         * Representa a estrutura de atributos dos frames.
         * Cada atributo pode conter n valores associados.
         */
   
        
/**
 * M�todo que sobrescreve toString.
 */
    @Override
 public String toString( )
 {
        String frame ;
        frame = "{idObjeto: " +  this.id + " - nomeObjeto: " + this.name ;
        
        for( Iterator < Slot > it = this.slot.iterator( ); it.hasNext( ); ) 
        {  
            Slot objSlot = ( Slot ) it.next( ) ;
            String attribute = objSlot.getAttribute( ) ;
            frame += "[" + attribute + "(" ;
            if ( objSlot.values != null )
            {
                ArrayList < String > objValue = objSlot.values ;
                for( Iterator < String > it1 = objValue.iterator( ); it1.hasNext( ); ) 
                {  
                    String value = (String) it1.next( ) ;
                    frame += value ;
                    if ( it1.hasNext( ) )
                    {
                        frame +=", " ;
                    }
                }
            }
            frame += ")]" ;
            if ( it.hasNext( ) )
            {
                frame +="; " ;
            }
        }
        frame += "}" ;
        
        return frame ;
    } /* fim do m�todo toString( ) */
 
 /**
  * M�todo que imprime os atributos.
  */
  public String printAttributes( )
  {
         String frame ;
         frame = "{" ;
         
         for( Iterator < Slot > it = this.slot.iterator( ); it.hasNext( ); ) 
         {  
             Slot objSlot = ( Slot ) it.next( ) ;
             String attribute = objSlot.getAttribute( ) ;
             frame += "[" + attribute + " (" ;
             if ( objSlot.values != null )
             {
                 ArrayList < String > objValue = objSlot.values ;
                 for( Iterator < String > it1 = objValue.iterator( ); it1.hasNext( ); ) 
                 {  
                     String value = (String) it1.next( ) ;
                     frame += value ;
                     if ( it1.hasNext( ) )
                     {
                         frame +=", " ;
                     }
                 }
             }
             frame += ") ]" ;
             if ( it.hasNext( ) )
             {
                 frame +="; " ;
             }
         }
         frame += "}" ;
         
         return frame ;
     } /* fim do m�todo printFrame( ) */
  

 	/**
 	 * M�todo que imprime no console a estrutura de um frame.
 	 */
    public void print( )
    {
        System.out.println( this.toString( ) ) ;
    }
} /* fim da classe frame */