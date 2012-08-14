




import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.*;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class SparqlToXml {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        /////////////////////////////
        //Creating an empty XML Document

        //We need a Document
		ArrayList<Author> authors 	= new ArrayList<Author>( ) ;
		int instancesNumber			= 10 ; // number of retrieved entities from a sparql endpoint
		String givenName 			= "" ;
		String familyName 			= "" ;
		String fullName 			= "" ;
		String emailAddress 		= "" ;
		String affiliationUnit 		= "" ;
		String affiliation 			= "" ;
		String postalAddress 		= "" ;
		String telephoneNumber 		= "" ;
		
		/*String p					= "akt:given-name" ;
		String p1					= "akt:full-name" ;
		String p2					= "akt:family-name" ;		
		String p3					= "akt:has-email-address" ;
		String p4					= "akt:has-affiliation-to-unit" ;
		String p5					= "akt:has-affiliation" ;
		String p6					= "akt:has-postal-address" ;
		String p7					= "akt:has-telephone-number" ;*/
		
		String p					= "foaf:firstName" ;
		String p1					= "foaf:name" ;
		String p2					= "foaf:family_name" ;		
		String p3					= "foaf:givenname" ;
		String p4					= "foaf:surname" ;
		String p5					= "foaf:has-affiliation" ;
		String p6					= "foaf:has-postal-address" ;
		String p7					= "foaf:has-telephone-number" ;
		
		Author author ;
		ArrayList<String> objects		= 	new ArrayList<String>( ) ;
		ArrayList <String> sparqlList 	= 	new ArrayList <String> ( ) ;
		
	String namespaces					= 	"PREFIX id:   <http://southampton.rkbexplorer.com/id/> "+
											"PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
											"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
											"PREFIX akt:  <http://www.aktors.org/ontology/portal#> "+
											"PREFIX owl:  <http://www.w3.org/2002/07/owl#> "+
											"PREFIX foaf:    <http://xmlns.com/foaf/0.1/>"+
											"PREFIX akt:  <http://www.aktors.org/ontology/portal#> "+
											"PREFIX akts: <http://www.aktors.org/ontology/support#> ";
		String q						=	"SELECT * WHERE { ?s   akt:full-name  ?o }limit " + instancesNumber ;		
		//String q						=	"SELECT * WHERE { ?s   foaf:name  ?o }limit " + instancesNumber ;		
		String qtemp 					=	"SELECT * WHERE{ <%s> " ;
		String qtemp1 					=	"  ?o}" ;
		
		
		sparqlList.add( "http://citeseer.rkbexplorer.com/sparql/" ) ;
		sparqlList.add( "http://acm.rkbexplorer.com/sparql/" ) ;
		sparqlList.add( "http://dblp.rkbexplorer.com/sparql/" ) ;
		sparqlList.add( "http://eprints.rkbexplorer.com/sparql/" ) ;
		sparqlList.add( "http://southampton.rkbexplorer.com/sparql/" ) ;
		sparqlList.add( "http://oai.rkbexplorer.com/sparql/" ) ;	// change the property akt:full-name to foaf:name in variable q
		sparqlList.add( "http://ieee.rkbexplorer.com/sparql/" ) ; 	
		sparqlList.add( "http://rae2001.rkbexplorer.com/sparql/" ) ;
		sparqlList.add( "http://ibm.rkbexplorer.com/sparql/" ) ;
		
		String sparql 		= sparqlList.get( 6 ) ;
		String filename		= sparql.substring( sparql.indexOf("//") + 2 , sparql.indexOf(".") ) ;
		QueryExecution e 	=  QueryExecutionFactory.sparqlService( sparql  , namespaces + q ) ;
		ResultSet results 	= e.execSelect( ) ;
		QueryExecution e1 = null ;
		
		System.out.println( "################ EXTRACTING DATA ###############" );
		int band		 	= 0 ;
		
		while ( results.hasNext( ) )
		{
			QuerySolution s = results.nextSolution( ) ;
			objects.add( s.getResource( "s" ).toString( ) ) ;

		}
		e.close( ) ;
		
		System.out.println("Total extracted instances: " + objects.size( ) ) ;
		
		for( String object : objects )
		{	
			if (band++ % 50 == 0 )
			{
				System.out.println( band ) ;
			}
			givenName 			= "" ;
			familyName 		    = "" ;
			fullName 			= "" ;
			emailAddress 		= "" ;
			
			String q2 = String.format( qtemp, object ) + p + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			ResultSet results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				 QuerySolution s1 = results1.nextSolution( ) ;
				 givenName = s1.getLiteral( "o" ).toString( ) ;
				 break;
			}
			
			e1.close( ) ;
			
			q2 = String.format( qtemp, object ) + p1 + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				QuerySolution s1 = results1.nextSolution( ) ;
				fullName = s1.getLiteral( "o" ).toString( ) ;
				break;				
			}
			
			e1.close( ) ;
			
			q2 = String.format( qtemp, object ) + p2 + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				QuerySolution s1 = results1.nextSolution( ) ;
				familyName = s1.getLiteral( "o" ).toString( ) ;
				break;				
			}
			
			e1.close( ) ;
			
			
			q2 = String.format( qtemp, object ) + p3 + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				QuerySolution s1 = results1.nextSolution( ) ;
				emailAddress = s1.getLiteral( "o" ).toString( ) ;
				break;				
				
			}
			
			e1.close( ) ;
			
			q2 = String.format( qtemp, object ) + p4 + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				QuerySolution s1 = results1.nextSolution( ) ;
				affiliationUnit = s1.getLiteral("o" ).toString( ) ;
				break;				
				
			}
			
			e1.close( ) ;
			
			q2 = String.format( qtemp, object ) + p5 + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				QuerySolution s1 = results1.nextSolution( ) ;
				affiliation = s1.getResource("o" ).toString( ) ;
				break;				
				
			}
			
			e1.close( ) ;
			
			q2 = String.format( qtemp, object ) + p6 + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				QuerySolution s1 = results1.nextSolution( ) ;
				postalAddress = s1.getResource( "o" ).toString( ) ;
				break;				
				
			}
			
			e1.close( ) ;
			
			q2 = String.format( qtemp, object ) + p7 + qtemp1 ;
			e1 = QueryExecutionFactory.sparqlService( sparql , namespaces + q2 ) ;
			results1 = e1.execSelect( ) ;
			
			while( results1.hasNext( )  )
			{
				QuerySolution s1 = results1.nextSolution( ) ;
				telephoneNumber = s1.getLiteral( "o" ).toString( ) ;
				break;				
				
			}
			
			e1.close( ) ;
			
			author = new Author(givenName, familyName, fullName, emailAddress) ;
			author.setAffiliationUnit( affiliationUnit ) ;
			author.setAffiliation( affiliation ) ;
			author.setPostalAddress( postalAddress ) ;
			author.setTelephoneNumber( telephoneNumber ) ;			
			authors.add( author ) ;	
		
			
		}
		
		
       try {
            /////////////////////////////
            //Creating an empty XML Document

            //We need a Document
            DocumentBuilderFactory dbfac 	= DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder 		= dbfac.newDocumentBuilder();
            Document doc 					= docBuilder.newDocument();

            //We need a second Document
            DocumentBuilderFactory dbfac1	= DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder1 	= dbfac1.newDocumentBuilder();
            Document doc1 					= docBuilder1.newDocument();
            
            ////////////////////////
            //Creating the XML tree

            //create the root element and add it to the document
            Element root = doc.createElement("doc");
            doc.appendChild(root);
            //create the root element and add it to the document
            Element root1 = doc1.createElement("doc");
            doc1.appendChild(root1);

            for( Author autor : authors )
            {
                //create child element, add an attribute, and add to root
                Element child 				= doc.createElement("entry");
                Element child1 				= doc1.createElement("entry");
                
                Element EgivenName 			= doc.createElement("givenName");
                Element EfamilyName 		= doc.createElement("familyName");
                Element EfullName 			= doc.createElement("fullName");
                Element EemailAddress 		= doc.createElement("emailAddress");
                Element EaffiliationUnit 	= doc.createElement("affiliationUnit")  ;
                Element Eaffiliation 		= doc.createElement("affiliation") ;
                Element EpostalAddress 		= doc.createElement("postalAddress") ;
                Element EtelephoneNumber	= doc.createElement("telephoneNumber") ;
                
                Element EgivenName1 		= doc1.createElement("givenName");
                Element EfamilyName1 		= doc1.createElement("familyName");
                Element EfullName1 			= doc1.createElement("fullName");
                Element EemailAddress1 		= doc1.createElement("emailAddress");
                Element EaffiliationUnit1 	= doc1.createElement("affiliationUnit")  ;
                Element Eaffiliation1 		= doc1.createElement("affiliation") ;
                Element EpostalAddress1		= doc1.createElement("postalAddress") ;
                Element EtelephoneNumber1	= doc1.createElement("telephoneNumber") ;
                
                
                child.appendChild( EfullName ) ;
                child.appendChild( EemailAddress ) ;
                child.appendChild( EaffiliationUnit ) ;
                child.appendChild( Eaffiliation ) ;
                child.appendChild( EpostalAddress ) ;
                child.appendChild( EtelephoneNumber ) ;
                
                child1.appendChild( EgivenName1 ) ;
                child1.appendChild( EfamilyName1 ) ;
                child1.appendChild( EemailAddress1 ) ;
                child1.appendChild( EaffiliationUnit1 ) ;
                child1.appendChild( Eaffiliation1 ) ;
                child1.appendChild( EpostalAddress1 ) ;
                child1.appendChild( EtelephoneNumber1 ) ;
                
                //add a text element to the child
                Text TgivenName 		= doc.createTextNode( autor.getGivenName( ) ) ;
                Text TfamilyName		= doc.createTextNode( autor.getFamilyName( ) ) ;
                Text TfullName 			= doc.createTextNode( autor.getFullName( ) ) ;
                Text TemailAddress 		= doc.createTextNode( autor.getEmailAddress( ) ) ;
                Text TaffiliationUnit	= doc.createTextNode( autor.getAffiliationUnit( ) ) ;
                Text Taffiliation 		= doc.createTextNode( autor.getAffiliation( ) ) ;
                Text TpostalAddress  	= doc.createTextNode( autor.getPostalAddress( ) ) ;
                Text TtelephoneNumber 	= doc.createTextNode( autor.getTelephoneNumber( ) ) ;
                
                Text TgivenName1 		= doc1.createTextNode( autor.getGivenName( ) ) ;
                Text TfamilyName1		= doc1.createTextNode( autor.getFamilyName( ) ) ;
                Text TfullName1			= doc1.createTextNode( autor.getFullName( ) ) ;
                Text TemailAddress1 	= doc1.createTextNode( autor.getEmailAddress( ) ) ;
                Text TaffiliationUnit1	= doc1.createTextNode( autor.getAffiliationUnit( ) ) ;
                Text Taffiliation1 		= doc1.createTextNode( autor.getAffiliation( ) ) ;
                Text TpostalAddress1  	= doc1.createTextNode( autor.getPostalAddress( ) ) ;
                Text TtelephoneNumber1 	= doc1.createTextNode( autor.getTelephoneNumber( ) ) ;
                
                EgivenName.appendChild( TgivenName ) ;
                EfamilyName.appendChild( TfamilyName ) ;
                EfullName.appendChild( TfullName ) ;
                EemailAddress.appendChild( TemailAddress ) ;
                EaffiliationUnit.appendChild( TaffiliationUnit ) ;
                Eaffiliation.appendChild( Taffiliation ) ;
                EpostalAddress.appendChild( TpostalAddress ) ;
                EtelephoneNumber.appendChild( TtelephoneNumber ) ;
                
                EgivenName1.appendChild( TgivenName1 ) ;
                EfamilyName1.appendChild( TfamilyName1 ) ;
                EfullName1.appendChild( TfullName1 ) ;
                EemailAddress1.appendChild( TemailAddress1 ) ;
                EaffiliationUnit1.appendChild( TaffiliationUnit1 ) ;
                Eaffiliation1.appendChild( Taffiliation1 ) ;
                EpostalAddress1.appendChild( TpostalAddress1 ) ;
                EtelephoneNumber1.appendChild( TtelephoneNumber1 ) ;
                
                
                root.appendChild(child) ;            
                root1.appendChild(child1) ;            
             }
            
            /////////////////
            //Output the XML

            //set up a transformer
            TransformerFactory transfac = TransformerFactory.newInstance() ;
            Transformer trans = transfac.newTransformer() ;
            trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes") ;
            trans.setOutputProperty(OutputKeys.INDENT, "yes") ;

            StreamResult result = new StreamResult(new File("D:\\" + filename + "- fullname -" + instancesNumber + ".xml") ) ;
            DOMSource source = new DOMSource(doc) ;
            trans.transform(source, result) ;
            
            result = new StreamResult(new File("D:\\" + filename + "- familyGiven -" + instancesNumber + ".xml") ) ;
            source = new DOMSource(doc1) ;
            trans.transform(source, result) ;
            
            System.out.println("File saved!") ;

        } catch (Exception exception) {
            System.out.println( exception );
        }
	}	
}

