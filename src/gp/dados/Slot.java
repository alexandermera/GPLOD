package gp.dados;


import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bernardo
 */
     public class Slot
        {
            public String attribute ;
            public ArrayList < String > values ;

            /**
             * Construtor Padr�o.
             */
            public Slot( )
            {
                attribute = "" ;
                values = new ArrayList < String >( ) ;
            } /* fim do construtor Slot( ) */

            /**
             * Construtor que inicializa com o nome do atributo
             * e seus valores.
             * @param attribute Nome do atributo.
             * @param values Valores associados ao atributo.
             */
            public Slot( String attribute, ArrayList < String > values )
            {
                this.attribute = attribute ;
                this.values = values ;
            } /* fim do construto Slot( String attribute, ArrayList < String > values ) */

            /**
             * M�todo que retorna o nome do atributo.
             * @return attribute Retorna o nome do atributo.
             */
            public String getAttribute( )
            {
                return attribute ;
            } /* fim do m�todo getAttribute( ) */

            /**
             * M�tod que atribui um nome ao atributo.
             * @param attribute Nome do atributo.
             */
            public void setAttribute( String attribute )
            {
                this.attribute = attribute ;
            } /* fim do m�todo setAttribute( String attribute ) */

            /**
             * M�todo que retorna o valor do indice i do atributo.
             * @param i Indice do valor a ser retornado.
             * @return Retorna o valor do atributo.
             */
            public String getValue( int i )
            {
                return this.values.get( i ) ;
            } /* fim do m�todo getValue( int i ) */

            /**
             * M�todo que adiciona um valor ao atributo.
             * @param values Valor a ser adicionado ao atributo.
             */
            public void addValue( String values )
            {
                this.values.add( values ) ;
            } /* fim do m�todo addValue( String values ) */

            /**
             * M�todo que remove um determinado valor de um atributo.
             * @param i Indice do valor a ser removido.
             * @return Retorna o valor removido.
             */
            public String removeValue( int i )
            {
                return this.values.remove( i ) ;
            } /* fim do m�todo removeValue( int i ) */

            /**
             * M�todo que retorna uma lista dos valores do atributo.
             * @return Retorna a lista dos valores do atributo.
             */
            public ArrayList < String > getValues( )
            {
                return values ;
            } /* fim do m�todo getValues( ) */

            /**
             * M�todo que atribui uma lista de valores ao atributo.
             * @param values Lista de valores de um atributo.
             */
            public void setValues( ArrayList < String > values )
            {
                this.values = values ;
            } /* fim do m�todo setValues( ArrayList < String > values ) */
        } /* fim da calsse slot */
