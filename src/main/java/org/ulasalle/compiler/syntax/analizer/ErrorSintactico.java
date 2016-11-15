package org.ulasalle.compiler.syntax.analizer;

import org.ulasalle.compiler.util.Error;
import org.ulasalle.compiler.util.Token;
/**
 *
 * @author francisco
 */
public class ErrorSintactico implements Error
{
    
    private TipoError tipoError;
    private Token token;

    public ErrorSintactico()
    {
    }
    
    public ErrorSintactico(TipoError tipoError, Token token)
    {
        this.tipoError = tipoError;
        this.token = token;
    }
    
    @Override
    public String getDescripcion()
    {
        String  detalles = "";
        if( this.tipoError == TipoError.NOTERMINAL_IRRECONOCIBLE) 
            detalles= "No se econtró una regla para el no terminal";
        if(this.tipoError == TipoError.TOKEN_IRRECONOCIBLE) 
            detalles = "No se econtró un terminal para el token";
           if(this.tipoError == TipoError.TOKENS_SIN_LEER) 
            detalles = "La pila esta vacia pero todavia existen tokens para leer";
        return "Error sintactico en el token '" + (this.token.getLexema()==null?this.token.getTipoToken():this.token.getLexema())+"'\n detalle: ["+detalles+"]";
    }
    
}
