package gp.dados;

/*
 * ReadXML.java
 */

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;

/**
 * ReadXML
 * Classe respons�vel pela leitura de dados e convers�o em frames.
 * @author Bernardo
 */
public class ReadXML
{    
    /**
     * Construtor Padr�o
     */
    public ReadXML( )
    {
    } /* fim do construtor padrao */
    
    /**
     * M�todo que abre o arquivo
     * @param xmlFile Caminho do arquivo
     * @return Document do arquivo
     * @throws ParserConfigurationException 
     * @throws SAXException 
     * @throws IOException 
     */
    private static Document openFile( String xmlFile ) throws ParserConfigurationException, SAXException, IOException
    {
        File file = new File( xmlFile ) ;
        Document doc = null ;
        if ( file.exists( ) )
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance( ) ;
            DocumentBuilder builder = null ;
            try
            {
                builder = factory.newDocumentBuilder( ) ;
            } 
            catch ( ParserConfigurationException ex )
            {
                throw new ParserConfigurationException( ) ;
            } /* try */

            try
            {
                doc = builder.parse( xmlFile ) ;
            } 
            catch ( SAXException ex )
            {
            	throw new SAXException( ) ;
            } 
            catch ( IOException ex )
            {
            	throw new IOException( ) ;
            } /* try */       
            doc.getDocumentElement( ).normalize( ) ;
        }
        return doc ;
    } /* fim do m�todo openFile( String xmlFile ) */
    
    /**
     * M�todo que faz o parse do arquivo em frames
     * @param xmlFile Caminho do arquivo
     * @return Lista de frames
     * @throws IOException 
     * @throws SAXException 
     * @throws ParserConfigurationException 
     */
    /**
     * @param xmlFile
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static List < Frame > parseToFrames( String xmlFile ) throws ParserConfigurationException, SAXException, IOException
    {
        Document doc = openFile( xmlFile ) ;

        ArrayList < Frame > frames = new ArrayList < Frame >( ) ;
        
        NodeList listOfFrames = doc.getElementsByTagName( "entry" ) ;
        int totalFrames = listOfFrames.getLength( ) ;
        int atributo=0 ;
        for( int i = 0; i < totalFrames; i++ )
        {
            Node frameNode = listOfFrames.item( i ) ;
            if( frameNode.getNodeType( ) == Node.ELEMENT_NODE )
            {
                Element frameElement = ( Element ) frameNode ;
                Frame f = new Frame( ) ;
                
                f.setName( "" ) ; //caso o frame venha  a possuir um nome, colocar aqui.
                
                f.setId( Integer.toString( i ) ) ; //Criando um id para cada registro
                
                NodeList listOfSlots = frameElement.getChildNodes( ) ; //recebe todos os filhos de frameNode
                int totalSlots = listOfSlots.getLength( ) ;              
                for( int j = 0; j < totalSlots; j++ )
                {
                    Node slotNode = listOfSlots.item( j ) ;
                    if( slotNode.getNodeType( ) == Node.ELEMENT_NODE )
                    {
                        Element slotElement = ( Element ) slotNode ;
                        ArrayList < String > values = new ArrayList < String > ( ) ;
                        values.add( slotElement.getTextContent( ) ) ;
                        f.addSlot( slotElement.getTagName( ), values ) ;
                        if(i==1)
                        {
                            System.out.println((atributo)+" "+slotElement.getTagName());
                            atributo++ ;
                        }

                    } /* if */
                } /* for */
                frames.add( f ) ;
            } /* if */
        } /* for */
        return frames ;
    } /* fim do m�todo parseToFrames( String xmlFile ) */
} /* Fim da classe ReadXML */